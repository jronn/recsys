/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import java.io.Serializable;
import java.util.List;

/**
 * A generic Dao interface containing base functions, intended to be extended
 * by more specific interfaces for specific entities.
 * 
 * @author jronn
 */
public interface GenericDao<E,ID extends Serializable> {
     /**
     * Persists the entity in the database
     * 
     * @param entity 
     */
    public void persist(E entity);
    
    /**
     * Removes the entity from the database
     * @param entity 
     */
    public void remove(E entity);
    
     /**
     * Searches for the entity in the database, based on given primary key
     * 
     * @param id Primary key of the entity
     * @return Entity with primary key matching the given id
     */
    public E findById(ID id);
  
    
    /**
     * Flushes all changes (for all entities, not just the related entity)
     */
    public void flush();
}
