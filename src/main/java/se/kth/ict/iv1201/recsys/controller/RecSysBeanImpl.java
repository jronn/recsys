/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.OptimisticLockException;
import se.kth.ict.iv1201.recsys.integration.*;
import se.kth.ict.iv1201.recsys.model.*;
import se.kth.ict.iv1201.recsys.model.entities.*;

/**
 * EJB containing the business logic of the application
 * 
 * @author jronn
 */
@Stateless
public class RecSysBeanImpl implements RecSysBean {
    
    @EJB
    PersonDao personDao;
    @EJB
    UserGroupDao userGroupDao;
    @EJB
    RoleDao roleDao;
    @EJB
    CompetenceDao competenceDao;
    @EJB
    CompetenceProfileDao competenceProfileDao;
    @EJB
    AvailabilityDao availabilityDao;
    @EJB
    ApplicationDao applicationDao;

    private static final Logger log = Logger.getLogger(RecSysBeanImpl.class.getName());
    
    @TransactionAttribute(REQUIRED)
    public void registerUser(String name, String surname, String email, String username, String password) 
        throws BadInputException, ExistingUserException, RecsysException {
            try {
            // Validate input
            if(!RecSysUtil.validateString(name, 2, 20, true) ||
                    !RecSysUtil.validateString(surname, 2, 20, true) ||
                    !RecSysUtil.validateEmail(email) ||
                    !RecSysUtil.validateString(username, 2, 20, false) ||
                    !RecSysUtil.validateString(password, 2, 20, false)) {
                throw new BadInputException("Invalid user input");
            }
            
            Person existingUser = personDao.findById(username);
            if(existingUser != null) 
                throw new ExistingUserException("User already exists");

            List personList = personDao.findByEmail(email);
            if(!personList.isEmpty()){ 
                throw new ExistingUserException("Specified email is already in use");
            }

            Person person = new Person(username, name, surname, email, RecSysUtil.hashText(password));                   
            Role role = roleDao.findById("applicant");
            
            Collection<UserGroup> roles = person.getUserGroupCollection();
            
            if(roles == null)
                roles = new ArrayList<UserGroup>();
            
            roles.add(new UserGroup(person,role));
            person.setUserGroupCollection(roles);
            
            personDao.persist(person);
            // Flushes all changes, done within clause to catch JPA exceptions
            personDao.flush();           
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Error with hashing algorithm or encoding "
                    + "when hashing password in registerUser function", e);
            throw new RecsysException("Unexpected error has occurred.");
        } catch (EJBTransactionRolledbackException e) {
            log.log(Level.SEVERE, "A unkown database error occurred in "
                    + "registerUser function", e);
            throw new RecsysException("Database error occurred.");
        } 
    }
    
    @TransactionAttribute(REQUIRED)
    public List<String> getCompetenceList() throws RecsysException {
        
        List<String> list = new ArrayList();
        try {
            List<Competence> clist = competenceDao.findAll();
            for(Competence c : clist)
                list.add(c.getName());
        } catch (Exception e) {
            log.log(Level.SEVERE, "getCompetenceList function encountered "
                    + "an unexpected error", e);
            throw new RecsysException("Unexpected error has occurred.");
        }
        return list;
    }
    
    @TransactionAttribute(REQUIRED)
    public void registerApplication(String username, ApplicationDTO applicationDto) 
            throws NotLoggedInException, RecsysException, BadInputException {
        try {
            if(username == null)
                throw new NotLoggedInException("You need to be logged in.");
            
            List<CompetenceListing> competences = applicationDto.getCompetences();
            List<AvailabilityListing> availabilities = applicationDto.getAvailabilities();

            // Get current date
            Date date = new Date();           
                
            Person person = personDao.findById(username);

            Application application = person.getApplication();
            if(application == null)
                application = new Application(person,date);
            
            application.setSubmitDate(new Date());
            application.setApproved(false);
            
            // Go through competenceProfiles, add new competences
            Collection<CompetenceProfile> clist = new ArrayList<>();
            
            for(CompetenceListing c : competences) {
                Competence comp = competenceDao.findById(c.competence);
                
                if(comp == null)
                    throw new BadInputException("Invalid competence types detected.");

                
                //CompetenceProfile cp;
                boolean containsCompetence = false;
                for(CompetenceProfile cp : clist) {
                    if(cp.getCompetence().equals(comp)) {
                        cp.setYearsOfExperience(new BigDecimal(c.getYearsOfExperience()));
                        containsCompetence = true;
                        break;
                    }
                }
                
                // If its a new competence, add it to the competenceProfile list
                if(!containsCompetence) {
                    clist.add(new CompetenceProfile(application, comp, new BigDecimal(c.yearsOfExperience)));   
                }
            }       
            application.setCompetenceProfileCollection(clist);

            
            // Go throuh availabilities and add them to the database
            Collection<Availability> availList = new ArrayList<>();
            
            for(AvailabilityListing a : availabilities) {
                if(!a.fromDate.before(a.toDate))
                    throw new BadInputException("Invalid availablility date. fromDate is after toDate");
                             
                if(!availList.contains(a)) {
                    availList.add(new Availability(application, a.fromDate, a.toDate));
                }
            }
            application.setAvailabilityCollection(availList);
            
            
            person.setApplication(application);
            personDao.persist(person);
            
            // Called inside try block so we can catch JPA exceptions
            personDao.flush();
            
        } catch(EJBTransactionRolledbackException e) {
            log.log(Level.SEVERE, "RegisterApplication encountered an unexpected"
                    + " error, possibly linked to the database", e);
            throw new RecsysException("Unexpected error occurred.");
        }
    }
    
    @TransactionAttribute(REQUIRED)
    public List<ApplicationDTO> getApplications(String name, String surname, CompetenceListing competence,
                    Date fromDate, Date toDate, Date regDate) throws RecsysException, BadInputException {
        
        List<ApplicationDTO> returnList = new ArrayList<>();
        
        // Validate competence
        if(competence != null) {
            Competence comp = competenceDao.findById(competence.competence);
            if(comp == null)
                throw new BadInputException("Invalid competence type");
        }
        
        // Validate dates
        if(fromDate != null && toDate != null && fromDate.after(toDate))
            throw new BadInputException("Invalid dates. fromDate is after toDate");
        
        try {
            List<Application> applications = 
                    applicationDao.findBySearchCriterias(name, surname, competence, fromDate, toDate, regDate);
        
            for(Application a : applications) {
                ApplicationDTO aDTO = new ApplicationDTO();
                aDTO.setApproved(a.getApproved());
                aDTO.setApplicantFirstName(a.getPerson().getName());
                aDTO.setApplicantLastName(a.getPerson().getSurname());
                aDTO.setUsername(a.getPerson().getUsername());
                aDTO.setSubmitDate(a.getSubmitDate());
                returnList.add(aDTO);
            } 
        }catch(Exception e) {
            log.log(Level.SEVERE, "An unexpected error occurred in "
                    + "getApplication function", e);
            throw new RecsysException("Unexpected error occurred.");
        }
        return returnList;
    }
    
    @TransactionAttribute(REQUIRED)
    public ApplicationDTO getSpecificApplication(String username) throws BadInputException {   
        Person person = null;
        
        if(username != null)
            person = personDao.findById(username);
        
        if(username == null || person == null)
            throw new BadInputException("Invalid username");
        
        
        Application application = person.getApplication();
        if(application == null)
            throw new BadInputException("Application for that user does not exist");
            
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setApplicantFirstName(application.getPerson().getName());
        applicationDTO.setApplicantLastName(application.getPerson().getSurname());
        applicationDTO.setSubmitDate(application.getSubmitDate());
        applicationDTO.setApproved(application.getApproved());
        
        Collection<Availability> availabilities = application.getAvailabilityCollection();
        Collection<CompetenceProfile> competences = application.getCompetenceProfileCollection();
        
        for(Availability a : availabilities) {
            applicationDTO.addAvailability(new AvailabilityListing(a.getFromDate(), a.getToDate()));
        }
        
        for(CompetenceProfile cp : competences) {
            applicationDTO.addCompetence(new CompetenceListing(cp.getCompetence().getName(),cp.getYearsOfExperience().intValue()));
        }
        
        return applicationDTO;
    }
    
    @TransactionAttribute(REQUIRED)
    public void setApproved(String username, boolean status) throws RecsysException,BadInputException{
        Person person = null;
        System.out.println("setApproved called with username " + username + ""
                + "and status " + status);
        if(username != null)
            person = personDao.findById(username);
        
        if(username == null || person == null)
            throw new BadInputException("User does not exist");
        
        Application app = person.getApplication();
        if(app == null)
            throw new BadInputException("User does not have an application");           
        
        try {
            app.setApproved(status);
            System.out.println("Sätter status till " + status);
            person.setApplication(app);
            personDao.persist(person);
            personDao.flush();
        } catch (OptimisticLockException e) {
            throw new RecsysException("Someone else has modified the application.");
        } 
        catch (EJBTransactionRolledbackException e) {
            log.log(Level.SEVERE, "setApproved encountered unexpected error, "
                    + "possibly linked to the database",e);
            throw new RecsysException("Unexpected error occurred");
        }
    }
}
