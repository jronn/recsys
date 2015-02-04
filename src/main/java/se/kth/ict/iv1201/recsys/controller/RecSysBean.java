/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.model.PersonDao;
import se.kth.ict.iv1201.recsys.model.entities.Person;

/**
 * EJB containing the business logic of the application
 * 
 * @author jronn
 */
@Stateless
public class RecSysBean {
    
    @EJB
    PersonDao personDao;
    
    /**
     * Registers the user in the system. Returns true if successful or false
     * if unsuccessful (user already exists)
     * 
     * @param name 
     * @param surname
     * @param email
     * @param username
     * @param password
     * @return 
     */
    public boolean registerUser(String name, String surname, String email, String username, String password) {
        
        /*Person prevPerson = personDao.findById(Long.MIN_VALUE)
        if(prevPerson != null) {
            return false;
        } */
        
        /*Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setEmail(email);
        person.setUsername(username);
        person.setPassword(password);
        
        personDao.persist(person);*/
        return true;
    }
    
    /**
     * Checks if the given username already exists in the system. Returns true
     * if it does.
     * @param username
     * @return 
     */
    private boolean isRegistered(String username) {
        //Person person = personDao.findById(Long.MIN_VALUE);
        //if(person != null)
        //  return true;
        //else
        //  return false;
        return false;
    }
}
