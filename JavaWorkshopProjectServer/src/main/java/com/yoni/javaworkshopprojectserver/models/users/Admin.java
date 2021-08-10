/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models.users;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoni
 */
@Entity(name = "Admins")
@Table(name = "admins")
@NamedQueries({
    @NamedQuery(name = "Admins.findAll", query = "SELECT e FROM Admins e"),
    @NamedQuery(name = "Admins.findById", query = "SELECT e FROM Admins e WHERE e.id = :id"),
    @NamedQuery(name = "Admins.findByEmail", query = "SELECT e FROM Admins e WHERE e.email = :email"),
    @NamedQuery(name = "Admins.findByFirstName", query = "SELECT e FROM Admins e WHERE e.firstName = :firstName"),
    @NamedQuery(name = "Admins.findByLastName", query = "SELECT e FROM Admins e WHERE e.lastName = :lastName"),
    @NamedQuery(name = "Admins.findByPhone", query = "SELECT e FROM Admins e WHERE e.phone = :phone"),
    @NamedQuery(name = "Admins.findByCreated", query = "SELECT e FROM Admins e WHERE e.created = :created"),
    @NamedQuery(name = "Admins.findByModified", query = "SELECT e FROM Admins e WHERE e.modified = :modified")})
public class Admin extends AbstractUser implements Serializable {   

    public Admin() {
    }

    public Admin(Integer id) {
        super(id);
    }

    public Admin(Integer id, String email, String pass, String firstName, String lastName, Date created, Date modified) {
        super(id, email, pass, firstName, lastName, created, modified);
    }
    
   @Override
    public String toString() {
        return "Admin{" + "id=" + id + ", created=" + created + ", modified=" + modified + ", email=" + email + ", pass=" + pass + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", address=" + address + '}';
    }
}
