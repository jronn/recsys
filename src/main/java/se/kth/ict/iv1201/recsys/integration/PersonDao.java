/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.util.List;
import se.kth.ict.iv1201.recsys.model.entities.Person;

/**
 * Dao interface for the Person entity
 * 
 * @author jronn
 */
public interface PersonDao extends GenericDao<Person, String> {
    
    /**
     * Searches DB for person with specified email, returns list of results
     * @param email
     * @return List of persons with matching email
     */
    public List findByEmail(String email);

}
