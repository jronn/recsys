/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.util.Date;
import java.util.List;
import se.kth.ict.iv1201.recsys.model.CompetenceListing;
import se.kth.ict.iv1201.recsys.model.entities.Application;
import se.kth.ict.iv1201.recsys.model.entities.Person;

/**
 * Dao for Application entity class
 * @author jronn
 */
public interface ApplicationDao extends GenericDao<Application,Long>{
    
    /**
     * Finds applications based on person
     * @param person
     * @return Application entity
     */
    public List<Application> findByPerson(Person person);
    
    /**
     * Finds a list of applications based on the search criteria
     * 
     * @param name Name of applicant. May be null
     * @param surname Surname of applicant. May be null
     * @param competence Competence name and minimum years of experience. May be null
     * @param fromAvailDate Start date of time period search. May be null
     * @param toAvailDate End date of time period search. May be null
     * @param regDate Date of application registration. May be null
     * @return List of applications matching criteria
     */
    public List<Application> findBySearchCriterias(String name, String surname, 
            CompetenceListing competence, Date fromAvailDate, Date toAvailDate, Date regDate);
}
