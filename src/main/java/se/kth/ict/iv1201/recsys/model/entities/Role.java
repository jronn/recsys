/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author jronn
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "role_id")
    private Long roleId;
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    public Role() {
    }

    public Role(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if (!this.roleId.equals(other.roleId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.kth.ict.iv1201.recsys.model.Role[ roleId=" + roleId + " ]";
    }
    
}
