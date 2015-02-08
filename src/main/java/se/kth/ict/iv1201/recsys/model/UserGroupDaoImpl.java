/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.model.entities.UserGroup;

/**
 * Dao implementation for the Person entity
 * 
 * @author jronn
 */
@Stateless
public class UserGroupDaoImpl extends GenericJpaDao<UserGroup,Long> implements UserGroupDao{

    public UserGroupDaoImpl(){
        super(UserGroup.class);
    }
}
