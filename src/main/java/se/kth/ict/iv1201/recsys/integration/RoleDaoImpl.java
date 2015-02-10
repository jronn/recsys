/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.integration;

import javax.ejb.Stateless;
import se.kth.ict.iv1201.recsys.model.entities.Role;

/**
 * Dao implementation for the role entity
 *  
 * @author jronn
 */
@Stateless
public class RoleDaoImpl extends GenericJpaDao<Role,String> implements RoleDao {
    
    public RoleDaoImpl(){
        super(Role.class);
    }
}
