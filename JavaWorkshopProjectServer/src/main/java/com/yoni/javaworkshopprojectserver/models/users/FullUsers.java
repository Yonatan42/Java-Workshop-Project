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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Yoni
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "FullUsers.findAll", query = "SELECT c FROM FullUsers c"),
    @NamedQuery(name = "FullUsers.findById", query = "SELECT c FROM FullUsers c WHERE c.id = :id"),
    @NamedQuery(name = "FullUsers.findByEmail", query = "SELECT c FROM FullUsers c WHERE c.email = :email"),
    @NamedQuery(name = "FullUsers.findByFirstName", query = "SELECT c FROM FullUsers c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "FullUsers.findByLastName", query = "SELECT c FROM FullUsers c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "FullUsers.findByPhone", query = "SELECT c FROM FullUsers c WHERE c.phone = :phone"),
    @NamedQuery(name = "FullUsers.findByCreated", query = "SELECT c FROM FullUsers c WHERE c.created = :created"),
    @NamedQuery(name = "FullUsers.findByModified", query = "SELECT c FROM FullUsers c WHERE c.modified = :modified"),
    @NamedQuery(name = "FullUsers.findByAdmin", query = "SELECT c FROM FullUsers c WHERE c.isAdmin = :isAdmin")})
public class FullUsers extends AbstractUsers implements Serializable {
 
    
private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "is_admin")
    @Expose
    private boolean isAdmin;

    

    public FullUsers() {
    }

    public FullUsers(Integer id) {
        super(id);
    }

    public FullUsers(Integer id, String email, String pass, String firstName, String lastName, Date created, Date modified, boolean isAdmin) {
        super(id, email, pass, firstName, lastName, created, modified);
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
        if (!(object instanceof FullUsers)) {
            return false;
        }
        FullUsers other = (FullUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", created=" + created + ", modified=" + modified + ", email=" + email + ", pass=" + pass + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", address=" + address + ", isAdmin=" + isAdmin + '}';
    }

    
    
}
