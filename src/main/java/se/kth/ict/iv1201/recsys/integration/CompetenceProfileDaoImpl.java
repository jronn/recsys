/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.MANDATORY;
import javax.persistence.Query;
import se.kth.ict.iv1201.recsys.model.entities.Application;
import se.kth.ict.iv1201.recsys.model.entities.Competence;
import se.kth.ict.iv1201.recsys.model.entities.CompetenceProfile;

/**
 * Dao implementation for CompetenceProfile entity
 * @author jronn
 */
@Stateless
public class CompetenceProfileDaoImpl extends GenericJpaDao<CompetenceProfile,Long> implements CompetenceProfileDao {
    
    public CompetenceProfileDaoImpl() {
        super(CompetenceProfile.class);
    }
    
    @TransactionAttribute(MANDATORY)
    public List<CompetenceProfile> findByApplicationAndCompetence(Application application, Competence competence) {
        Query query = em.createQuery("SELECT cp FROM CompetenceProfile cp WHERE cp.application = :application AND"
                + " cp.competence = :competence");
        query.setParameter("application", application);
        query.setParameter("competence", competence);
        return query.getResultList();
    }
    
    @TransactionAttribute(MANDATORY)
    public List<CompetenceProfile> findByApplication(Application application) {
        Query query = em.createQuery("SELECT cp FROM CompetenceProfile cp WHERE cp.application = :app");
        query.setParameter("app", application);
        return query.getResultList();
    }
}
