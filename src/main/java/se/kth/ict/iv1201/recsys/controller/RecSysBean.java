/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import java.util.List;
import javax.ejb.Local;
import se.kth.ict.iv1201.recsys.model.ExistingUserException;
import se.kth.ict.iv1201.recsys.model.RecsysException;

/**
 *
 * @author jronn
 */
@Local
public interface RecSysBean {
    
    /**
     * Registers a new user in the database and sets a role for the user
     * @param name
     * @param surname
     * @param email
     * @param username
     * @param password
     * @throws IllegalArgumentException Thrown when input data does not match
     * validation requirements
     * @throws ExistingUserException Thrown when a user with the same username 
     * or email already exists
     * @throws RecsysException Thrown when database errors or unexpected errors
     * occur
     */
    public void registerUser(String name, String surname, String email, String username, String password)
            throws IllegalArgumentException, ExistingUserException, RecsysException;
    
   
    /**
     * Finds and returns all a list of strings, containing the names of all competence types
     * @return List of strings, containing name of all competence types
     * @throws RecsysException Thrown when an unexpected error occurs.
     */
    public List<String> getCompetenceList() throws RecsysException;
}
