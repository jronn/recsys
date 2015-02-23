/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import javax.ejb.ApplicationException;

/**
 * Thrown when user input is not valid
 * @author jronn
 */
@ApplicationException(rollback = true)
public class BadInputException extends Exception{
    
    public BadInputException(String message) {
        super(message);
    }
}
