/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import se.kth.ict.iv1201.recsys.model.CompetenceListing;
import se.kth.ict.iv1201.recsys.model.entities.Application;
import se.kth.ict.iv1201.recsys.model.entities.Availability;
import se.kth.ict.iv1201.recsys.model.entities.Competence;
import se.kth.ict.iv1201.recsys.model.entities.CompetenceProfile;
import se.kth.ict.iv1201.recsys.model.entities.Person;

/**
 * Dao implementation for Application entity class
 * @author jronn
 */
@Stateless
public class ApplicationDaoImpl extends GenericJpaDao<Application,Long> implements ApplicationDao {
    
    public ApplicationDaoImpl() {
        super(Application.class);
    }
    
    public Application findByPerson(Person person) {
        return (Application)em.createQuery("SELECT a FROM Application a WHERE a.person = :person")
                .setParameter("person", person).getSingleResult();
    }

    public List<Application> findBySearchCriterias(String name, CompetenceListing competenceIn, Date fromDate, Date toDate, Date regDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Query for a List of objects.
        CriteriaQuery cq = cb.createQuery();
        Root<Application> a = cq.from(Application.class);
        
        Join avail = a.join("availabilityCollection");
        Join comp = a.join("competenceProfileCollection");
        Join person = a.join("person");
        
        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Adding predicates in case of parameter not being null
        if (name != null) {
            predicates.add(
                    cb.like(person.get("name"), "%"+name+"%"));
        }
        
        if (competenceIn != null) {
            predicates.add(
                    cb.and(cb.equal(comp.get("competence"), competenceIn.competence),
                            cb.ge(comp.get("yearsOfExperience"), competenceIn.yearsOfExperience)));
        }
        
        if (fromDate != null && toDate != null) {
            predicates.add(
                    cb.or(cb.or(cb.between(avail.get("fromDate"), fromDate, toDate),
                            cb.between(avail.get("toDate"), fromDate, toDate)),
                          cb.and(cb.lessThanOrEqualTo(avail.get("fromDate"), fromDate),
                                  cb.greaterThanOrEqualTo(avail.get("toDate"), toDate))));
        }      
        if(regDate != null) {
            predicates.add(
                    cb.equal(a.get("submitDate"), regDate));
        }
        
        cq.select(a)
                .where(predicates.toArray(new Predicate[]{}));
        
        return em.createQuery(cq).getResultList();
    }
  
}
