package se.kth.ict.iv1201.recsys.view;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Backing bean for authentication
 * 
 * @author jronn
 */
@Named("auth")
@RequestScoped
public class Auth implements Serializable {
    
   public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/faces/register.xhtml");
   }   
}