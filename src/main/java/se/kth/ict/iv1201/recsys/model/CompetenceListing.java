/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

/**
 * Models a competenceProfile entity, but excludes JPA and database specific
 * code
 * @author jronn
 */
public class CompetenceListing {
    
    public String competence;
    public int yearsOfExperience;
    
    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    
    @Override
    public String toString(){
        return "Expertise: " + competence + " Years of experience: " + yearsOfExperience;
        
    }
    
    public CompetenceListing(String competence, int yearsOfExperience) {
        this.competence = competence;
        this.yearsOfExperience = yearsOfExperience;
    }
}
