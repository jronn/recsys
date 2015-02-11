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
    

    public int registerUser(String name, String surname, String email, String username, String password) {
        
        try {
            // Check if user already exists
            Person existingUser = personDao.findById(username);
            if(existingUser != null)
                return 0;
            
            // Create new user
            Person person = new Person(username, name, surname, email, RecSysUtil.hashText(password));
            personDao.persist(person);
                
            // Get applicant role, or create it if it doesnt exist in the db
            Role role = roleDao.findById("applicant");
            if(role == null) {
                role = new Role("applicant");
                roleDao.persist(role);
            }
            
            // Create new usergroup (user and role mapping) in db
            UserGroup userGroup = new UserGroup(person,role);
            userGroupDao.persist(userGroup);
            
            // Flushes all changes, must be done within try clause to catch
            // JPA exceptions, or exceptions will be thrown outside clause
            personDao.flush();
            
            return 1;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return 2;
        } 
    } 
}
