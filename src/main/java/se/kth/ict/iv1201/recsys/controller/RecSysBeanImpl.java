/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.integration.CompetenceDao;
import se.kth.ict.iv1201.recsys.integration.PersonDao;
import se.kth.ict.iv1201.recsys.integration.RoleDao;
import se.kth.ict.iv1201.recsys.integration.UserGroupDao;
import se.kth.ict.iv1201.recsys.model.ExistingUserException;
import se.kth.ict.iv1201.recsys.model.RecSysUtil;
import se.kth.ict.iv1201.recsys.model.RecsysException;
import se.kth.ict.iv1201.recsys.model.entities.Competence;
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
                
            Role role = new Role("applicant");
            roleDao.persist(role);
            
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
            e.printStackTrace();
            throw new RecsysException("Unexpected error has occurred.");
            
        }
        return list;
    }
}
