/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models.users;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 *
 * @author Yoni
 */
@Entity(name = "ExtendedUsers")
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "ExtendedUsers.findAll", query = "SELECT e FROM ExtendedUsers e"),
    @NamedQuery(name = "ExtendedUsers.findById", query = "SELECT e FROM ExtendedUsers e WHERE e.id = :id"),
    @NamedQuery(name = "ExtendedUsers.findByEmail", query = "SELECT e FROM ExtendedUsers e WHERE e.email = :email"),
    @NamedQuery(name = "ExtendedUsers.findByFirstName", query = "SELECT e FROM ExtendedUsers e WHERE e.firstName = :firstName"),
    @NamedQuery(name = "ExtendedUsers.findByLastName", query = "SELECT e FROM ExtendedUsers e WHERE e.lastName = :lastName"),
    @NamedQuery(name = "ExtendedUsers.findByPhone", query = "SELECT e FROM ExtendedUsers e WHERE e.phone = :phone"),
    @NamedQuery(name = "ExtendedUsers.findByCreated", query = "SELECT e FROM ExtendedUsers e WHERE e.created = :created"),
    @NamedQuery(name = "ExtendedUsers.findByModified", query = "SELECT e FROM ExtendedUsers e WHERE e.modified = :modified"),
    @NamedQuery(name = "ExtendedUsers.findByAdmin", query = "SELECT e FROM ExtendedUsers e WHERE e.isAdmin = :isAdmin")})
@NamedStoredProcedureQuery(name = "ExtendedUsers.refreshSecretKey", 
  procedureName = "refresh_secret_key", parameters = {
    @StoredProcedureParameter(mode = ParameterMode.IN, name = "email", type = String.class)})
public class ExtendedUser extends AbstractUser implements Serializable {
 
    
private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "is_admin")
    @Expose
    private boolean isAdmin;

    

    public ExtendedUser() {
    }

    public ExtendedUser(Integer id) {
        super(id);
    }

    public ExtendedUser(Integer id, String email, String pass, String secretKey, String firstName, String lastName, Date created, Date modified, boolean isAdmin) {
        super(id, email, pass, secretKey, firstName, lastName, created, modified);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExtendedUser)) {
            return false;
        }
        ExtendedUser other = (ExtendedUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExtendedUser{" + "id=" + id + ", created=" + created + ", modified=" + modified + ", email=" + email + ", pass=" + pass + ", secretKey=" + secretKey + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", address=" + address + ", isAdmin=" + isAdmin + '}';
    }

    
    
}
