/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import se.kth.ict.iv1201.recsys.model.entities.Application;
import se.kth.ict.iv1201.recsys.model.entities.Person;

/**
 * Dao for Application entity class
 * @author jronn
 */
public interface ApplicationDao extends GenericDao<Application,Long>{
    
    /**
     * Finds an application based on person
     * @param person
     * @return Application entity
     */
    public Application findByPerson(Person person);
}
