/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.util.List;
import se.kth.ict.iv1201.recsys.model.entities.Competence;

/**
 * Dao for the Competence entity
 * @author jronn
 */
public interface CompetenceDao extends GenericDao<Competence,String> {

    /**
     * Finds and returns all entities
     * @return List of Competence entities
     */
    public List<Competence> findAll();
}
