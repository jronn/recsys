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
 *
 * @author jronn
 */
@Stateless
public class RecSysBean {
    
    @EJB
    PersonDao personDao;
    
    public String register(String name, String surname, String email, String username, String password) {
        
        /*Person prevPerson = personDao.findById(Long.MIN_VALUE)
        if(prevPerson != null) {
            return null;
        } */
        
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setEmail(email);
        person.setUsername(username);
        person.setPassword(password);
        
        personDao.persist(person);
        return "success";
    }
}
