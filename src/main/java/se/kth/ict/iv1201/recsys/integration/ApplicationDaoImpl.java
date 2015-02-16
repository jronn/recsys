/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.model.entities.Application;
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
}
