/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.Customers;
import com.yoni.javaworkshopprojectserver.utils.BcryptUtil;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Yoni
 */
@Stateless
@Path("customers")
public class CustomersFacadeREST extends AbstractFacade<Customers> {

//    @PersistenceContext(unitName = "my_persistence_unit")
//    private EntityManager em;

    public CustomersFacadeREST() {
        super(Customers.class);
    }

    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Customers entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, Customers entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customers find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customers> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customers> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    
    @GET
    @Path("makeit")
    public void makeSomething() {
        Customers c = new Customers();
        c.setFirstName("fn1");
        c.setLastName("ln1");
        c.setPhone("0522020202");
        c.setEmail("makeit@mail.mail");
        c.setAddress("ze place");
        c.setPass("$2a$10$zl0b5Lry7kJSZLPA5HHkc.gdDjVfgFkuOx2NlaRXNV5IgVWHvt6E6");
        c.setCreated(new Date());
        c.setModified(new Date());
        super.create(c);
    }
    
    @GET
    @Path("makeitagain")
    public void makeSomethingAgain() {
        getEntityManager().getTransaction().begin();
//        em.createNativeQuery("INSERT INTO customers (email, pass, first_name, last_name) VALUES ('s@s.s', ?, 'Steve', 'Anderson')").setParameter(1, new byte[]{0,0,0,1,1,0}).executeUpdate();
        getEntityManager().createNativeQuery("INSERT INTO customers (email, pass, first_name, last_name) VALUES ('test@test.test', '$2a$10$i32v4vzRQrFvtKHjzwyd/u./BzQaxB4LeEPmYxe34UuOexlJhr9ou', 'Steve', 'Anderson')").executeUpdate();
        getEntityManager().getTransaction().commit();
    }
    
    @GET
    @Path("selectcount")
    public long selectcount() {
        return (long)getEntityManager().createNativeQuery("SELECT COUNT(id) FROM customers").getSingleResult();
    }
    

//    
    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void register(
            @FormParam("email") String email, 
            @FormParam("pass") String pass, 
            @FormParam("firstName") String firstName, 
            @FormParam("lastName") String lastName, 
            @FormParam("phone") String phone, 
            @FormParam("address") String address) {
        getEntityManager().getTransaction().begin();
        getEntityManager().createNativeQuery("INSERT INTO customers (email, pass, first_name, last_name, phone, address) VALUES (?, ?, ?, ?, ?, ?)")
                .setParameter(1, email)
                .setParameter(2, BcryptUtil.encrypt(pass))
                .setParameter(3, firstName)
                .setParameter(4, lastName)
                .setParameter(5, phone)
                .setParameter(6, address)
                .executeUpdate();
        getEntityManager().getTransaction().commit();
    }

    
    @POST
    @Path("register2")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void register2(
            @FormParam("email") String email, 
            @FormParam("pass") String pass, 
            @FormParam("firstName") String firstName, 
            @FormParam("lastName") String lastName, 
            @FormParam("phone") String phone, 
            @FormParam("address") String address) {
        Customers c = new Customers();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);
        c.setPass(BcryptUtil.encrypt(pass));
        getEntityManager().getTransaction().begin();
        super.create(c);
        getEntityManager().getTransaction().commit();
    }
    
    @POST
    @Path("register3")
    @Consumes(MediaType.APPLICATION_JSON)
    public void register3(Customers entity) {
        entity.setPass(BcryptUtil.encrypt(entity.getPass()));
        getEntityManager().getTransaction().begin();
        super.create(entity);
        getEntityManager().getTransaction().commit();
    }

//    @Override
//    protected EntityManager getEntityManager() {
////        if(em == null){
////            em = Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
////        }
//        return em;
//    }
//    
}
