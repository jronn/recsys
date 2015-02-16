/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO used to send to controller when registering an application
 * @author jronn
 */
public class ApplicationDTO {
    
    final private List<CompetenceListing> competences;
    final private List<AvailabilityListing> availabilities;
    
    public ApplicationDTO() {
       competences = new ArrayList(); 
       availabilities = new ArrayList();
    }
    
    public void addCompetence(CompetenceListing competence) {
        competences.add(competence);
    }
    
    public void addAvailability(AvailabilityListing availability) {
        availabilities.add(availability);
    }
    
    public List<AvailabilityListing> getAvailabilities() {
        return availabilities;
    }
    
    public List<CompetenceListing> getCompetences() {
        return competences;
    }
}
