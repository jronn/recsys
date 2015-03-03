/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.ict.iv1201.recsys.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import se.kth.ict.iv1201.recsys.controller.RecSysBean;
import se.kth.ict.iv1201.recsys.model.ApplicationDTO;
import se.kth.ict.iv1201.recsys.model.AvailabilityListing;
import se.kth.ict.iv1201.recsys.model.BadInputException;
import se.kth.ict.iv1201.recsys.model.CompetenceListing;

/**
 *
 * @author Christoffer
 */
@Named("getAppBean")
@SessionScoped
public class GetAppBean implements Serializable {

    @EJB
    RecSysBean recSysEJB;

    ApplicationDTO app;

    private String firstName;
    private String lastName;
    private Date submitDate;
    private List<CompetenceListing> competences;
    private List<AvailabilityListing> availabilities;
    private boolean approved;

    Map<String, String> params;
    String user;

    /**
     * Called upon every time the page gets loaded up.
     */
    public void onPageLoad() {
        try {
            params = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap();
            user = params.get("username");
            app = recSysEJB.getSpecificApplication(user);
            firstName = app.getApplicantFirstName();
            lastName = app.getApplicantLastName();
            submitDate = app.getSubmitDate();
            competences = app.getCompetences();
            availabilities = app.getAvailabilities();
            approved = app.isApproved();
        } catch (BadInputException ex) {
            Logger.getLogger(GetAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the application to being approved.
     */
    public void approve() {
        approved = true;
        app.setApproved(approved);
    }

    /**
     * Sets the application to being unapproved.
     */
    public void unapprove() {
        approved = false;
        app.setApproved(approved);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public List<CompetenceListing> getCompetences() {
        return competences;
    }

    public void setCompetences(List<CompetenceListing> competences) {
        this.competences = competences;
    }

    public List<AvailabilityListing> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityListing> availabilities) {
        this.availabilities = availabilities;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}
