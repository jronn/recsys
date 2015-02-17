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
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import se.kth.ict.iv1201.recsys.integration.ApplicationDao;
import se.kth.ict.iv1201.recsys.integration.AvailabilityDao;
import se.kth.ict.iv1201.recsys.integration.CompetenceDao;
import se.kth.ict.iv1201.recsys.integration.CompetenceProfileDao;
import se.kth.ict.iv1201.recsys.integration.PersonDao;
import se.kth.ict.iv1201.recsys.integration.RoleDao;
import se.kth.ict.iv1201.recsys.integration.UserGroupDao;
import se.kth.ict.iv1201.recsys.model.ApplicationDTO;
import se.kth.ict.iv1201.recsys.model.AvailabilityListing;
import se.kth.ict.iv1201.recsys.model.CompetenceListing;
import se.kth.ict.iv1201.recsys.model.ExistingUserException;
import se.kth.ict.iv1201.recsys.model.NotLoggedInException;
import se.kth.ict.iv1201.recsys.model.RecSysUtil;
import se.kth.ict.iv1201.recsys.model.RecsysException;
import se.kth.ict.iv1201.recsys.model.entities.Application;
import se.kth.ict.iv1201.recsys.model.entities.Availability;
import se.kth.ict.iv1201.recsys.model.entities.Competence;
import se.kth.ict.iv1201.recsys.model.entities.CompetenceProfile;
import se.kth.ict.iv1201.recsys.model.entities.Person;
import se.kth.ict.iv1201.recsys.model.entities.Role;
import se.kth.ict.iv1201.recsys.model.entities.UserGroup;

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

    public void registerUser(String name, String surname, String email, String username, String password) 
        throws IllegalArgumentException, ExistingUserException, RecsysException {
            try {
            // Validate input
            if(!RecSysUtil.validateString(name, 2, 20, true) ||
                    !RecSysUtil.validateString(surname, 2, 20, true) ||
                    !RecSysUtil.validateEmail(email) ||
                    !RecSysUtil.validateString(username, 2, 20, false) ||
                    !RecSysUtil.validateString(password, 2, 20, false)) 
                throw new IllegalArgumentException("Invalid user input"); 
            
            Person existingUser = personDao.findById(username);
            if(existingUser != null) 
                throw new ExistingUserException("User already exists");
            
            List personList = personDao.findByEmail(email);
            if(!personList.isEmpty()) 
                throw new ExistingUserException("Specified email is already in use");
            
            Person person = new Person(username, name, surname, email, RecSysUtil.hashText(password));
            personDao.persist(person);
                
            Role role = roleDao.findById("applicant");
            
            UserGroup userGroup = new UserGroup(person,role);
            userGroupDao.persist(userGroup);
            
            // Flushes all changes, done within clause to catch JPA exceptions
            personDao.flush();           
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RecsysException("Unexpected error has occurred.");
        } catch (EJBTransactionRolledbackException e) {
            throw new RecsysException("Database error occurred.");
        }
    }
    
    
    public List<String> getCompetenceList() throws RecsysException {
        
        List<String> list = new ArrayList();
        try {
            List<Competence> clist = competenceDao.findAll();
            for(Competence c : clist)
                list.add(c.getName());
        } catch (Exception e) {
            throw new RecsysException("Unexpected error has occurred.");
        }
        return list;
    }
    
    
    public void registerApplication(ApplicationDTO applicationDto) 
            throws NotLoggedInException, RecsysException {
        try {
            List<CompetenceListing> competences = applicationDto.getCompetences();
            List<AvailabilityListing> availabilities = applicationDto.getAvailabilities();

            // Get current date
            Date date = new Date();
            
            // Get logged in username
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
            String username = request.getRemoteUser();

            if(username == null)
                throw new NotLoggedInException("You need to be logged in.");
                
            Person person = personDao.findById(username);

            Application application = applicationDao.findByPerson(person);
            
            if(application == null)
                application = new Application(person,date);
            
            // Update submitdate to current date
            application.setSubmitDate(new Date());
            applicationDao.persist(application);

            // Go through competenceProfiles, if already exists update yearsOfExperience
            for(CompetenceListing c : competences) {
                Competence comp = competenceDao.findById(c.competence);
                
                CompetenceProfile cp = competenceProfileDao.findByApplicationAndCompetence(application,comp);
                if(cp == null)
                    cp = new CompetenceProfile(application, comp, new BigDecimal(c.yearsOfExperience));
                else
                    cp.setYearsOfExperience(new BigDecimal(c.yearsOfExperience));
                
                competenceProfileDao.persist(cp);
            }

            // Check to see if same availability already exists
            for(AvailabilityListing a : availabilities) {
                Availability avail = availabilityDao.findByApplicationAndDates(application, a.fromDate, a.toDate);
                if(avail == null)
                    avail = new Availability(application, a.fromDate, a.toDate);
                availabilityDao.persist(avail);
            }
            
            // Called inside try block so we can catch JPA exceptions
            personDao.flush();
            
        } catch(EJBTransactionRolledbackException e) {
            throw new RecsysException("Unexpected error occurred.");
        }
    }
    
    
    public List<ApplicationDTO> getApplications(String name, CompetenceListing competence,
                    Date fromDate, Date toDate, Date regDate) {
        List<Application> applications = applicationDao.findBySearchCriterias(name, competence, fromDate, toDate, regDate);
        List<ApplicationDTO> returnList = new ArrayList<>();
        
        for(Application a : applications) {
            ApplicationDTO aDTO = new ApplicationDTO();
            aDTO.setApproved(a.getApproved());
            aDTO.setApplicantFirstName(a.getPerson().getName());
            aDTO.setApplicantLastName(a.getPerson().getSurname());
            aDTO.setSubmitDate(a.getSubmitDate());
            returnList.add(aDTO);
        }
        
        return returnList;
    }
}
