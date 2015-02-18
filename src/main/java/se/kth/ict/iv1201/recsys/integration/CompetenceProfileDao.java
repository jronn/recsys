/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import se.kth.ict.iv1201.recsys.model.entities.Application;
import se.kth.ict.iv1201.recsys.model.entities.Competence;
import se.kth.ict.iv1201.recsys.model.entities.CompetenceProfile;

/**
 * Dao for the CompetenceProfile entity
 * @author jronn
 */
public interface CompetenceProfileDao extends GenericDao<CompetenceProfile,Long> {
    
    /**
     * Returns a competenceProfile linked to specified application and competence
     * @param application
     * @param competence
     * @return CompetenceProfile matching input values
     */
    public CompetenceProfile findByApplicationAndCompetence(Application application, Competence competence);
    
    /**
     * Find all competenceProfiles based on application
     * @param application
     * @return List of all competenceProfiles tied to specified application
     */
    public List<CompetenceProfile> findByApplication(Application application);
}
