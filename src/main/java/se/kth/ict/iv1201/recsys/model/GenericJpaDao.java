/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
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
    
    public void persist(E entity) {
        em.persist(entity);
    }
    
    public void remove(E entity) {
        em.remove(entity);
    }
    
    public E findById(Serializable id) {
        return (E) em.find(entityClass, id);
    }
}
