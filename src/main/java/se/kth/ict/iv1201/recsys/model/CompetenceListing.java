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
    
    public CompetenceListing(String competence, int yearsOfExperience) {
        this.competence = competence;
        this.yearsOfExperience = yearsOfExperience;
    }
}
