/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import javax.ejb.ApplicationException;

/**
 * Exception thrown when not logged in and a login is required
 * @author jronn
 */
// Notation needed to rollback transaction
@ApplicationException(rollback = true)
public class NotLoggedInException extends Exception {
    
    public NotLoggedInException(String message) {
        super(message);
    }
}
