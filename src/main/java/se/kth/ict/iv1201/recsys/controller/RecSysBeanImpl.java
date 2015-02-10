/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.integration.PersonDao;
import se.kth.ict.iv1201.recsys.integration.UserGroupDao;
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
    

    public int registerUser(String name, String surname, String email, String username, String password) {
        
        // Check if user already exists
        if(isRegistered(username)) {
            return 0;
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
            
            return 1;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return 2;
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
