package se.kth.ict.iv1201.recsys.view;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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
    private final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    private final String username = request.getRemoteUser();
    private final Timer timer = new Timer();

    /**
     * Called upon at first, to populate the comp list that is containing the
     * fields of expertise available.
     */
    @PostConstruct
    public void init() {
        try {
            comp = recSysEJB.getCompetenceList();
            availabilityList = recSysEJB.getSpecificApplication(username).getAvailabilities();
            expertiseList = recSysEJB.getSpecificApplication(username).getCompetences();
        } catch (RecsysException | BadInputException ex) {
            Logger.getLogger(AddAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void reset() {
        try {
            availabilityList = recSysEJB.getSpecificApplication(username).getAvailabilities();
            expertiseList = recSysEJB.getSpecificApplication(username).getCompetences();
        } catch (BadInputException ex) {
            Logger.getLogger(AddAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            clickedSend();
            recSysEJB.registerApplication(myApp);
        } catch (NotLoggedInException ex) {
            clickedSent = false;
            errorMessage = ex.getMessage();
        } catch (RecsysException ex) {
            clickedSent = false;
            errorMessage = ex.getMessage();
        } catch (BadInputException ex) {
            clickedSent = false;
            errorMessage = ex.getMessage();
        }
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

    public void setExpertiseList(List<CompetenceListing> expertiseList) {
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

    private void clickedSend() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clickedSent = false;
            }
        }, 2000);
    }

}
