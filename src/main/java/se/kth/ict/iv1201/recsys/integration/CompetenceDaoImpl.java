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
import se.kth.ict.iv1201.recsys.model.entities.Competence;

/**
 * Dao implementation for Competence entity 
 * @author jronn
 */
@Stateless
public class CompetenceDaoImpl extends GenericJpaDao<Competence,String> implements CompetenceDao{
    
    public CompetenceDaoImpl(){
        super(Competence.class);
    }
    
    @TransactionAttribute(MANDATORY)
    public List<Competence> findAll() {
        return em.createQuery("SELECT c FROM Competence c").getResultList();
    }
}
