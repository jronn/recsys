/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import java.security.MessageDigest;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.model.PersonDao;
import se.kth.ict.iv1201.recsys.model.UserGroupDao;
import se.kth.ict.iv1201.recsys.model.entities.Person;
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
    

    public boolean registerUser(String name, String surname, String email, String username, String password) {
        
        // Check if user already exists
        if(isRegistered(username)) {
            return false;
        }
        
        try {         
            //Hash password with SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            
            //To hex form
            StringBuilder hashedPw = new StringBuilder();
            for (int i=0;i<hash.length;i++) {
    		String hex = Integer.toHexString(0xff & hash[i]);
   	     	if(hex.length()==1) 
                    hashedPw.append('0');
   	     	hashedPw.append(hex);
            }
            
            Person person = new Person(username, name, surname, email, hashedPw.toString());
            personDao.persist(person);
            
            // Assign applicant role to the new user
            UserGroup userGroup = new UserGroup();
            userGroup.setPerson(username);
            userGroup.setRole("applicant");
            userGroupDao.persist(userGroup);
            
            return true;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return false;
        }
    }
    
    private boolean isRegistered(String username) {
        Person person = personDao.findById(username);
        if(person != null)
          return true;
        else
          return false;
    }
}
