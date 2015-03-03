/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.MANDATORY;
import javax.persistence.Query;
import se.kth.ict.iv1201.recsys.model.entities.Application;
import se.kth.ict.iv1201.recsys.model.entities.Availability;

/**
 * Dao implementation for entity class Availability
 * @author jronn
 */
@Stateless
public class AvailabilityDaoImpl extends GenericJpaDao<Availability,Long>
                                    implements AvailabilityDao {
    
    public AvailabilityDaoImpl() {
        super(Availability.class);
    }

    @TransactionAttribute(MANDATORY)
    public List<Availability> findByApplicationAndDates(Application application, Date start, Date end) {
        Query query = em.createQuery("SELECT av FROM Availability av WHERE av.application = :app AND"
                + " av.fromDate = :start AND av.toDate = :end");
        query.setParameter("app", application);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }
    
    @TransactionAttribute(MANDATORY)
    public List<Availability> findByApplication(Application application) {
        Query query = em.createQuery("SELECT av FROM Availability av WHERE av.application = :app");
        query.setParameter("app", application);
        return query.getResultList();
    }
}
