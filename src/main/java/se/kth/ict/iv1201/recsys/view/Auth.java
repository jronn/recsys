package se.kth.ict.iv1201.recsys.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import se.kth.ict.iv1201.recsys.controller.RecSysBeanImpl;

/**
 * Backing bean for authentication when logging in / out
 *
 * @author jronn
 */
@Named("auth")
@ViewScoped
public class Auth implements Serializable {

    private String username;
    private String password;
    private String originalURL;

    private static final Logger log = Logger.getLogger(Auth.class.getName());
    
    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath();
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
    }

    /**
     * Gets the current user's role
     *
     * @return String of user role
     */
    public String getRole() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        if (request.isUserInRole("applicant")) {
            return "applicant";
        }
        if (request.isUserInRole("recruiter")) {
            return "recruiter";
        } else {
            return null;
        }
    }

    /**
     * Logs the user in and redirects to previous searched page
     *
     * @throws IOException
     */
    public void login() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.login(username, password);
            
            if (request.isUserInRole("recruiter")) {
                log.log(Level.INFO, "Login completed by " + username + " as role recruiter");
                originalURL = externalContext.getRequestContextPath() + "/faces/recruiter/recruiter.xhtml";
            } else if (request.isUserInRole("applicant")) {
                log.log(Level.INFO, "Login completed by " + username + " as role applicant");
                originalURL = externalContext.getRequestContextPath() + "/faces/user/user.xhtml";
            }

            externalContext.redirect(originalURL);
        } catch (ServletException e) {
            log.log(Level.INFO, "Failed login attempt by " + username);
            externalContext.redirect(externalContext.getRequestContextPath() + "/faces/login_error.xhtml");
        }
    }

    /**
     * Logs the user out by invalidating the session and redirects to login page
     *
     * @throws IOException
     */
    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/login.xhtml");
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
