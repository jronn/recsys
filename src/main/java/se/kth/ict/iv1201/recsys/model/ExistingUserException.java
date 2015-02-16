
package se.kth.ict.iv1201.recsys.model;

import javax.ejb.ApplicationException;

/**
 * Custom exception, mainly thrown from controller when a user already exists
 * in the database
 * @author jronn
 */
// Notation needed to rollback transaction
@ApplicationException(rollback = true)
public class ExistingUserException extends Exception{
    
    public ExistingUserException(String message) {
        super(message);
    }
}
