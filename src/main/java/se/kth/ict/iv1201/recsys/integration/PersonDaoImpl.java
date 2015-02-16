/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.util.List;
import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.model.entities.Person;

/**
 * Dao implementation for the Person entity
 * 
 * @author jronn
 */
@Stateless
public class PersonDaoImpl extends GenericJpaDao<Person,String> implements PersonDao{

    public PersonDaoImpl(){
        super(Person.class);
    }
    
    public List findByEmail(String email) {
        return em.createQuery("SELECT p FROM Person p WHERE p.email LIKE :persEmail")
                .setParameter("persEmail", email).getResultList();
    }
}
