/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import se.kth.ict.iv1201.recsys.model.ApplicationDTO;
import se.kth.ict.iv1201.recsys.model.CompetenceListing;
import se.kth.ict.iv1201.recsys.model.ExistingUserException;
import se.kth.ict.iv1201.recsys.model.NotLoggedInException;
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
    
    /**
     * Registers a new application with the information supplied in the 
     * applicationDTO
     * @param application ApplicationDTO containing competences and availabilities
     * @throws NotLoggedInException Thrown when user is not logged in
     * @throws RecsysException Thrown on unexpected errors
     */
    public void registerApplication(ApplicationDTO application)
            throws NotLoggedInException, RecsysException;
            
    /**
     * Returns a list of applications based on input search arguments.
     * 
     * @param name Name of applicant. May be null.
     * @param competence CompetenceListing search parameter, with name of competence
     * and yearsOfExperience. May be null.
     * @param fromDate Availability fromDate. May be null.
     * @param toDate Availability toDate. May be null.
     * @param regDate Application registration date. May be null.
     * @throws RecsysException Thrown on unexpected errors
     * @throws IllegalArgumentException Thrown when input is invalid.
     * @return List of applicationDTOs based on search parameters. Include information
     * regarding approvance, submission date and first/last name
     */
    public List<ApplicationDTO> getApplications(String name, CompetenceListing competence,
                    Date fromDate, Date toDate, Date regDate) throws RecsysException, IllegalArgumentException;
    
    /**
     * Gets a specific application based on username
     * @param username
     * @return ApplicationDTO
     * @throws IllegalArgumentException Thrown when username is null or doesnt exist
     */
    public ApplicationDTO getSpecificApplication(String username) throws IllegalArgumentException;
    
    /**
     * Sets the approved value in the application related to user
     * @param username
     * @param status Status of approval. True = approved.
     * @throws RecsysException Thrown on unexpected errors
     * @throws IllegalArgumentException Thrown when username is invalid or doesnt have an application
     */
    public void setApproved(String username, boolean status)throws RecsysException,IllegalArgumentException;
}
