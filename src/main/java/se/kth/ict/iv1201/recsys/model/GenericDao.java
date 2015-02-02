/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import java.io.Serializable;

/**
 *
 * @author jronn
 */
public interface GenericDao<E,ID extends Serializable> {
    public void persist(E entity);
    public void remove(E entity);
    public E findById(ID id);
}
