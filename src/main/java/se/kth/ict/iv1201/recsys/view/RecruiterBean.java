package se.kth.ict.iv1201.recsys.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.kth.ict.iv1201.recsys.controller.RecSysBean;

/**
 * Backing bean for addapplication.xhtml
 * 
 * @author christoffer
 */
@Named("recruiterBean")
@SessionScoped
public class RecruiterBean implements Serializable {
    
    @EJB
    RecSysBean recSysEJB;
    
}