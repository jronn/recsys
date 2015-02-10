/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.io.Serializable;
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
    
    /**
     * Persists the entity in the database
     * 
     * @param entity 
     */
    public void persist(E entity) {
        em.persist(entity);
    }
    
    /**
     * Removes the entity from the database
     * 
     * @param entity 
     */
    public void remove(E entity) {
        em.remove(entity);
    }
    
    /**
     * Searches for the entity in the database, based on given primary key
     * 
     * @param id Primary key of the entity
     * @return Entity with primary key matching the given id
     */
    public E findById(Serializable id) {
        return (E) em.find(entityClass, id);
    }
}
