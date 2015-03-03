/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.io.Serializable;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.MANDATORY;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Abstract JPA Dao implementation of the GenericDao interface, intended to
 * be extended by more specific implementations for specific entities.
 * 
 * @author jronn
 */
public abstract class GenericJpaDao<E, ID extends Serializable> implements GenericDao<E,ID> {
    
    protected Class<E> entityClass;
    
    @PersistenceContext(unitName = "MyPersistenceUnit")
    public EntityManager em;
    
    public GenericJpaDao(Class entityClass) {
        this.entityClass = entityClass;
    }
    
    @TransactionAttribute(MANDATORY)
    public void persist(E entity) {
        em.persist(entity);
    }
    
    @TransactionAttribute(MANDATORY)
    public void remove(E entity) {
        em.remove(entity);
    }
    
    @TransactionAttribute(MANDATORY)
    public E findById(Serializable id) {
        return (E) em.find(entityClass, id);
    }
    
    @TransactionAttribute(MANDATORY)
    public void flush () {
        em.flush();
    }
}
