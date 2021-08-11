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

/**
 *
 * @author Yoni
 */
@Entity(name = "Customers")
@Table(name = "customers")
@NamedQueries({
    @NamedQuery(name = "Customers.findAll", query = "SELECT e FROM Customers e"),
    @NamedQuery(name = "Customers.findById", query = "SELECT e FROM Customers e WHERE e.id = :id"),
    @NamedQuery(name = "Customers.findByEmail", query = "SELECT e FROM Customers e WHERE e.email = :email"),
    @NamedQuery(name = "Customers.findByFirstName", query = "SELECT e FROM Customers e WHERE e.firstName = :firstName"),
    @NamedQuery(name = "Customers.findByLastName", query = "SELECT e FROM Customers e WHERE e.lastName = :lastName"),
    @NamedQuery(name = "Customers.findByPhone", query = "SELECT e FROM Customers e WHERE e.phone = :phone"),
    @NamedQuery(name = "Customers.findByCreated", query = "SELECT e FROM Customers e WHERE e.created = :created"),
    @NamedQuery(name = "Customers.findByModified", query = "SELECT e FROM Customers e WHERE e.modified = :modified")})
public class Customer extends AbstractUser implements Serializable {   

    public Customer() {
    }

    public Customer(Integer id) {
        super(id);
    }

    public Customer(Integer id, String email, String pass, String secretKey, String firstName, String lastName, Date created, Date modified) {
        super(id, email, pass, firstName, secretKey, lastName, created, modified);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", created=" + created + ", modified=" + modified + ", email=" + email + ", pass=" + pass + ", secretKey=" + secretKey + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", address=" + address + '}';
    }
    
    

}
