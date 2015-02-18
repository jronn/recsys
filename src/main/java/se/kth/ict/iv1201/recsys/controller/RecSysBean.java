/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import javax.ejb.Local;

/**
 *
 * @author jronn
 */
@Local
public interface RecSysBean {
    
    /**
     * Registers a new user in the system
     * 
     * @param name Users first name
     * @param surname Users last name
     * @param email Users email
     * @param username Users desired username
     * @param password Users desired password
     * @return int code representing status of the operation. 
     *  0 = Registration successful
     *  1 = Registration failed. Invalid input.
     *  2 = Registration failed. User exists.
     *  3 = Registration failed. Unexpected error.
     */
    public void registerUser(String name, String surname, String email, String username, String password) throws ControllerException;
}
