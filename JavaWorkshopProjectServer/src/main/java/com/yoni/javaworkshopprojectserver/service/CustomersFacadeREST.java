/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.Customers;
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
@Path("com.yoni.javaworkshopprojectserver.customers")
public class CustomersFacadeREST extends AbstractFacade<Customers> {

    // @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public CustomersFacadeREST() {
        super(Customers.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Customers entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customers find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customers> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
        c.setEmail("mail@mail.mail");
        c.setAddress("ze place");
        c.setPass(new byte[]{0,1,0,1});
        c.setCreated(new Date());
        c.setModified(new Date());
        super.create(c);
    }
    
    @GET
    @Path("makeitagain")
    public void makeSomethingAgain() {
        em.getTransaction().begin();
//        em.createNativeQuery("INSERT INTO customers (email, pass, first_name, last_name) VALUES ('s@s.s', ?, 'Steve', 'Anderson')").setParameter(1, new byte[]{0,0,0,1,1,0}).executeUpdate();
        em.createNativeQuery("INSERT INTO customers (email, pass, first_name, last_name) VALUES ('test@test.test', 'superpass', 'Steve', 'Anderson')").executeUpdate();
        em.getTransaction().commit();
    }
    
    @GET
    @Path("selectcount")
    public long selectcount() {
        return (long)em.createNativeQuery("SELECT COUNT(id) FROM customers").getSingleResult();
    }


    @Override
    protected EntityManager getEntityManager() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(CustomersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }

        em = Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
        return em;
    }
    
}
