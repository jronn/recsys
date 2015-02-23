package se.kth.ict.iv1201.recsys.view;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.kth.ict.iv1201.recsys.controller.RecSysBean;
import se.kth.ict.iv1201.recsys.model.BadInputException;
import se.kth.ict.iv1201.recsys.model.ExistingUserException;
import se.kth.ict.iv1201.recsys.model.RecsysException;

/**
 * Backing bean for register.xhtml
 * 
 * @author jronn
 */
@Named("registerBean")
@SessionScoped
public class RegisterBean implements Serializable {
    
    @EJB
    RecSysBean recSysEJB;

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    
    private String errorMessage;
    private boolean successful;
    /**
     * Called upon creation. Initializes the variables.
     */
    public RegisterBean() {
        errorMessage = "";
        successful = false;
    }
   /**
    * Attempt to register a new user through the controller bean
    */

    public void register() {
        try {
            recSysEJB.registerUser(name, surname, email, username, password);
            successful = true;
        } catch (BadInputException ex) {
            successful = false;
            errorMessage = "Invalid input. Make sure your user information is valid.";
        } catch (ExistingUserException ex) {
            successful = false;
            errorMessage = ex.getMessage();
        } catch (RecsysException ex) {
            successful = false;
            errorMessage = "An unexpected error has occurred. Make sure"
                    + " your input information is valid.";
        }
    }
    
    /**
     * Used to get the error message if registration fails.
     * 
     * @return String containing information about the error that has occured.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    /**
     * A method that checks if registration was successful
     * 
     * @return boolean that is only true if registration was successful
     */
    public boolean isSuccessful() {
        return successful;
    }
    
    /**
     *
     * @return string that contains the name of the registered user
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name sets the string name for the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return string that contains the surname of the registered user
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname sets the string surname for the user
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return a string containing the email address of the registered user
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email sets the string parameter email for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the string username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username sets the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return the password in string format
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password sets the password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
}