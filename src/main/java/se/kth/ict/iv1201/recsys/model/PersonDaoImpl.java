/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.model.entities.Person;

/**
 *
 * @author jronn
 */
@Stateless
public class PersonDaoImpl extends GenericJpaDao<Person,Long> implements PersonDao{

    public PersonDaoImpl(){
        super(Person.class);
    }
}
