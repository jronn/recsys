/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import javax.ejb.ApplicationException;

/**
 * Used as a general exception for errors without specific individual exceptions
 * @author jronn
 */
// Notation needed to rollback transaction
@ApplicationException(rollback = true)
public class RecsysException extends Exception {
    
    public RecsysException(String message) {
        super(message);
    }
}
