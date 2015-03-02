/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.ict.iv1201.recsys.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.kth.ict.iv1201.recsys.controller.RecSysBean;
import se.kth.ict.iv1201.recsys.model.*;

/**
 *
 * @author Christoffer
 */
@Named("findAppBean")
@SessionScoped
public class FindAppBean implements Serializable {

    @EJB
    RecSysBean recSysEJB;

    private Date dateFrom;
    private Date dateTo;
    private Date dateReg;
    private List<String> comp;
    private String name;
    private String surname;
    private String competence;
    private List<ApplicationDTO> apps;
    private boolean searched;

    @PostConstruct
    public void init() {
        try {
            searched = false;
            comp = recSysEJB.getCompetenceList();
        } catch (RecsysException ex) {
            Logger.getLogger(FindAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void search() {
        try {
            searched = true;
            if (name.isEmpty()) {
                name = null;
            }
            if (surname.isEmpty()) {
                surname = null;
            }

            CompetenceListing compListing = new CompetenceListing(competence, 0);
            if(competence.isEmpty()){
                compListing = null;
            }
            apps = recSysEJB.getApplications(name, surname, compListing, dateFrom, dateTo, dateReg);
        } catch (RecsysException | BadInputException ex) {
            Logger.getLogger(FindAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cancel() {
        nullify();
        searched = false;
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

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public List<String> getComp() {
        return comp;
    }

    public void setComp(List<String> comp) {
        this.comp = comp;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApplicationDTO> getApps() {
        return apps;
    }

    public void setApps(List<ApplicationDTO> apps) {
        this.apps = apps;
    }

    public boolean isSearched() {
        return searched;
    }

    public void setSearched(boolean searched) {
        this.searched = searched;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private void nullify() {
        name = null;
        surname = null;
        dateReg = null;
        dateFrom = null;
        dateTo = null;
        competence = null;
    }

}