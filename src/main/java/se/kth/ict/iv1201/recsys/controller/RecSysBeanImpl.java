/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import java.security.MessageDigest;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.integration.PersonDao;
import se.kth.ict.iv1201.recsys.integration.RoleDao;
import se.kth.ict.iv1201.recsys.integration.UserGroupDao;
import se.kth.ict.iv1201.recsys.model.RecSysUtil;
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
    

    @Override
    public void registerUser(String name, String surname, String email, String username, String password) {
        try {
            // Validate input
            if(!RecSysUtil.validateString(name, 2, 20, true) ||
                    !RecSysUtil.validateString(surname, 2, 20, true) ||
                    !RecSysUtil.validateEmail(email) ||
                    !RecSysUtil.validateString(username, 2, 20, false) ||
                    !RecSysUtil.validateString(password, 2, 20, false)) 
                throw new ControllerException("invalidinput"); 
            
            Person existingUser = personDao.findById(username);
            if(existingUser != null)
                throw new ControllerException("existinguser");
            
            Person person = new Person(username, name, surname, email, RecSysUtil.hashText(password));
            personDao.persist(person);
                
            Role role = roleDao.findById("applicant");
            if(role == null) {
                role = new Role("applicant");
                roleDao.persist(role);
            }
            
            UserGroup userGroup = new UserGroup(person,role);
            userGroupDao.persist(userGroup);
            
            // Flushes all changes, done within clause to catch JPA exceptions
            personDao.flush();           
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } 
    } 
}
