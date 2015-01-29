/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.kth.ict.iv1201.recsys.model.HelloEntity;

/**
 *
 * @author jronn
 */
@Stateless
public class HelloEJB {

    @PersistenceContext(unitName = "se.kth.ict.iv1201_recsys_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public String retrieveMessage() {
        HelloEntity test = new HelloEntity();
        em.persist(test);
        return "A message from the E-J-B";
    }
}
