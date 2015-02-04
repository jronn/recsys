package se.kth.ict.iv1201.recsys.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.kth.ict.iv1201.recsys.controller.RecSysBean;

/**
 * Backing bean for the register.xhtml page
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

    public RegisterBean() {
        errorMessage = "";
        successful = false;
    }
    
    /**
     * Attempts to register a new user in the system with the given details.
     * 
     * @return 
     */
    public void register() {
        successful = recSysEJB.registerUser(name, surname, email, username, password);
        if(!successful) {
            errorMessage = "Could not register user, user already exists";
        }
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public boolean isSuccessful() {
        return successful;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 
}