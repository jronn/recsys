package se.kth.ict.iv1201.recsys.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.kth.ict.iv1201.recsys.controller.RecSysBean;
import se.kth.ict.iv1201.recsys.model.ApplicationDTO;
import se.kth.ict.iv1201.recsys.model.AvailabilityListing;
import se.kth.ict.iv1201.recsys.model.CompetenceListing;
import se.kth.ict.iv1201.recsys.model.NotLoggedInException;
import se.kth.ict.iv1201.recsys.model.RecsysException;

/**
 * Backing bean for addapplication.xhtml
 * 
 * @author christoffer
 */
@Named("AddAppBean")
@SessionScoped
public class AddAppBean implements Serializable {
    
    @EJB
    RecSysBean recSysEJB;

    private Date date_from;
    private Date date_to;
    private int years_experience;
    private String expertise;
    private boolean clicked_next = false;
    private boolean clicked_sent = false;
    private List<CompetenceListing> expertise_list;
    private List<AvailabilityListing> availability_list;
    private ApplicationDTO myApp;
    
    public AddAppBean(){
        expertise_list = new ArrayList<>();
        availability_list = new ArrayList<>();
    }
    
    @PostConstruct
    public void init(){
        try {
            myApp = new ApplicationDTO();
            recSysEJB.registerApplication(myApp);
        } catch (NotLoggedInException | RecsysException ex) {
            Logger.getLogger(AddAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clickNext(){
        clicked_next = true;
    }
    
    public void addExpertise(){
        expertise_list.add(new CompetenceListing(expertise, years_experience));
    }
    
    public void addAvailability(){
        availability_list.add(new AvailabilityListing(date_from, date_to));
    }
    
    public void cancel(){
        date_to = null;
        date_from = null;
        expertise = "Animals";
        years_experience = 0;
        expertise_list = null;
        availability_list = null;
        clicked_next = false;
    }
    
    public void send() {
        clicked_next = false;
        for(int i = 0; i < availability_list.size(); i++){
            myApp.addAvailability(availability_list.get(i));
        }
        for(int i = 0; i < expertise_list.size(); i++){
            myApp.addCompetence(expertise_list.get(i));
        }
        clicked_sent = true;
    }
    
    public void goBack(){
        clicked_sent = false;
        cancel();
    }
    
    public RecSysBean getRecSysEJB() {
        return recSysEJB;
    }

    public void setRecSysEJB(RecSysBean recSysEJB) {
        this.recSysEJB = recSysEJB;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public int getYears_experience() {
        return years_experience;
    }

    public void setYears_experience(int years_experience) {
        this.years_experience = years_experience;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
    
    public boolean isClicked_next() {
        return clicked_next;
    }

    public void setClicked_next(boolean clicked_next) {
        this.clicked_next = clicked_next;
    }
    
    public boolean isClicked_sent() {
        return clicked_sent;
    }

    public void setClicked_sent(boolean clicked_sent) {
        this.clicked_sent = clicked_sent;
    }
    
    public List<CompetenceListing> getExpertise_list() {
        return expertise_list;
    }

    public void setExpertise_list(ArrayList<CompetenceListing> expertise_list) {
        this.expertise_list = expertise_list;
    }

    public List<AvailabilityListing> getAvailability_list() {
        return availability_list;
    }

    public void setAvailability_list(ArrayList<AvailabilityListing> availability_list) {
        this.availability_list = availability_list;
    }

    public ApplicationDTO getMyApp() {
        return myApp;
    }

    public void setMyApp(ApplicationDTO myApp) {
        this.myApp = myApp;
    }
    
}