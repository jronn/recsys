/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.util.Date;
import se.kth.ict.iv1201.recsys.model.entities.Application;
import se.kth.ict.iv1201.recsys.model.entities.Availability;

/**
 * Dao for the availability entity
 * @author jronn
 */
public interface AvailabilityDao extends GenericDao<Availability, Long> {
    
    public Availability findByApplicationAndDates(Application application, Date start, Date end);
}
