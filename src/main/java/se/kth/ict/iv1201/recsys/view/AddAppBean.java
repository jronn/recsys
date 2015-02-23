package se.kth.ict.iv1201.recsys.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.kth.ict.iv1201.recsys.controller.RecSysBean;
import se.kth.ict.iv1201.recsys.model.ApplicationDTO;
import se.kth.ict.iv1201.recsys.model.AvailabilityListing;
import se.kth.ict.iv1201.recsys.model.BadInputException;
import se.kth.ict.iv1201.recsys.model.CompetenceListing;
import se.kth.ict.iv1201.recsys.model.NotLoggedInException;
import se.kth.ict.iv1201.recsys.model.RecsysException;

/**
 * Backing bean for addapplication.xhtml
 *
 * @author christoffer
 */
@Named("addAppBean")
@SessionScoped
public class AddAppBean implements Serializable {

    @EJB
    RecSysBean recSysEJB;

    private Date dateFrom;
    private Date dateTo;
    private int yearsExperience;
    private String expertise;
    private boolean clickedSent = false;
    private List<CompetenceListing> expertiseList;
    private List<AvailabilityListing> availabilityList;
    private ApplicationDTO myApp;
    private List<String> comp;
    private String errorMessage;

    /**
     * Called upon at first, to populate the comp list that is containing the
     * fields of expertise available.
     */
    @PostConstruct
    public void init() {
        try {
            comp = recSysEJB.getCompetenceList();
        } catch (RecsysException ex) {
            Logger.getLogger(AddAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Called upon on start.
     */
    public AddAppBean() {
        expertiseList = new ArrayList<>();
        availabilityList = new ArrayList<>();
    }

    /**
     * Add a new CompetenceListing to our competenceList.
     */
    public void addExpertise() {
        expertiseList.add(new CompetenceListing(expertise, yearsExperience));
    }

    /**
     * Add a new AvailabilityListing to our availabiltyList.
     */
    public void addAvailability() {
        availabilityList.add(new AvailabilityListing(dateFrom, dateTo));
    }

    /**
     * Cancel, sets everything back to where it started.
     */
    public void cancel() {
        dateTo = null;
        dateFrom = null;
        expertise = "";
        yearsExperience = 0;
        expertiseList = new ArrayList<>();
        availabilityList = new ArrayList<>();
        errorMessage = null;
    }

    /**
     * Registers the application and adds the competence/availability to the
     * application. Sets clickedSent to true which triggers the "application
     * sent" message in the page.
     */
    public void send() {
        myApp = new ApplicationDTO();
        for (int i = 0; i < availabilityList.size(); i++) {
            myApp.addAvailability(availabilityList.get(i));
        }
        for (int i = 0; i < expertiseList.size(); i++) {
            myApp.addCompetence(expertiseList.get(i));
        }
        try {
            clickedSent = true;
            recSysEJB.registerApplication(myApp);
        } catch (NotLoggedInException ex) {
            clickedSent = false;
            errorMessage = "Error: NotLoggedIn";
        } catch (RecsysException ex) {
            clickedSent = false;
            errorMessage = "Error: Some Error";
        } catch (BadInputException ex) {
            clickedSent = false;
            errorMessage = "Error: SomeOtherError";
        }
    }

    /**
     * Calls upon the cancel() method and sets the clickedSent parameter to
     * false. Used for the go back button after clicking "send".
     */
    public void goBack() {
        clickedSent = false;
        cancel();
    }

    public RecSysBean getRecSysEJB() {
        return recSysEJB;
    }

    public void setRecSysEJB(RecSysBean recSysEJB) {
        this.recSysEJB = recSysEJB;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public boolean isClickedSent() {
        return clickedSent;
    }

    public void setClickedSent(boolean clickedSent) {
        this.clickedSent = clickedSent;
    }

    public List<CompetenceListing> getExpertiseList() {
        return expertiseList;
    }

    public void setExpertise_list(List<CompetenceListing> expertiseList) {
        this.expertiseList = expertiseList;
    }

    public List<AvailabilityListing> getAvailabilityList() {
        return availabilityList;
    }

    public void setAvailabilityList(List<AvailabilityListing> availabilityList) {
        this.availabilityList = availabilityList;
    }

    public ApplicationDTO getMyApp() {
        return myApp;
    }

    public void setMyApp(ApplicationDTO myApp) {
        this.myApp = myApp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getComp() {
        return comp;
    }

    public void setComp(List<String> comp) {
        this.comp = comp;
    }

}
