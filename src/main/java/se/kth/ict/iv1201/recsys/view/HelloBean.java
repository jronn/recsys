package se.kth.ict.iv1201.recsys.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.kth.ict.iv1201.recsys.controller.HelloEJB;

/**
 *  Backing bean 
 * 
 * @author Christoffer
 */
@Named("backingbean")
@SessionScoped
public class HelloBean implements Serializable {
    
    @EJB
    HelloEJB helloEjb;
    
    private String username;
    private String password;
    
    public void test() {
        password = helloEjb.retrieveMessage();
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}