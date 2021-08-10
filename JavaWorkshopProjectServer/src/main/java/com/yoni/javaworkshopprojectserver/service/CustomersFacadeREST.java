/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yoni.javaworkshopprojectserver.models.Customers;
import com.yoni.javaworkshopprojectserver.utils.BcryptUtil;
import com.yoni.javaworkshopprojectserver.utils.JsonUtil;
import com.yoni.javaworkshopprojectserver.utils.ResponseErrorCodes;
import com.yoni.javaworkshopprojectserver.utils.ResponseUtil;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
import javax.ws.rs.core.Response;

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
    
    // todo - delete. here we have register instead
//
//    @POST
//    @Override
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void create(Customers entity) {
//        super.create(entity);
//    }

    // todo - convert to response
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, Customers entity) {
        super.edit(entity);
    }

    // todo - convert to response
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    
    // todo - we probably don't want this since we are talking about customers
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
        return ResponseUtil.RespondSafe(() -> {
            try{
                Customers c = super.find(id);
                return Response
                        .status(Response.Status.OK)
                        .entity(JsonUtil.createResponseJson(JsonUtil.convertToJson(c)))
                        .build();
            }
            catch(IllegalArgumentException e){
                e.printStackTrace(System.err);
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(JsonUtil.createResponseJson("no customer found to provided id", ResponseErrorCodes.USERS_NO_SUCH_USER))
                        .build();
            }
        });
    }

    // todo - we probably don't want this since we are talking about customers
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllRes() {
        return ResponseUtil.RespondSafe(() -> {
            return Response
                    .status(Response.Status.OK)
                    .entity(JsonUtil.createResponseJson(JsonUtil.convertToJson(super.findAll())))
                    .build();
        });
    }


    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countRes() {
        return ResponseUtil.RespondSafe(() -> {
            return Response
                    .status(Response.Status.OK)
                    .entity(JsonUtil.createResponseJson(JsonUtil.createSimpleMessageObject(String.valueOf(super.count()))))
                    .build();
        });
    }
    

    
    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(
            @FormParam("email") String email, 
            @FormParam("pass") String pass, 
            @FormParam("firstName") String firstName, 
            @FormParam("lastName") String lastName, 
            @FormParam("phone") String phone, 
            @FormParam("address") String address) {
        
        return ResponseUtil.RespondSafe(() -> {
                getEntityManager().getTransaction().begin();
                
                try{
                    
                    Customers c = new Customers();
                    c.setFirstName(firstName);
                    c.setLastName(lastName);
                    c.setPhone(phone);
                    c.setEmail(email);
                    c.setAddress(address);
                    c.setPass(BcryptUtil.encrypt(pass));
                    super.create(c);

                }
                catch(EntityExistsException e){// java.sql.SQLIntegrityConstraintViolationException
                    e.printStackTrace(System.err);
                    
                    return Response
                        .status(Response.Status.CONFLICT)
                        .entity(JsonUtil.createResponseJson("customer already exists", ResponseErrorCodes.USERS_USER_ALREADY_EXISTS))
                        .build();
                }
                
                getEntityManager().getTransaction().commit();
                                   
                return Response
                    .status(Response.Status.CREATED)
                    .entity(JsonUtil.createResponseJson(JsonUtil.createSimpleMessageObject("customer created")))
                    .build();

        });
        

    }
    
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login( 
            @FormParam("email") String email, 
            @FormParam("pass") String pass) {

        return ResponseUtil.RespondSafe(() -> {
            
            List<Customers> resutls = getEntityManager().createNamedQuery("Customers.findByEmail", Customers.class).setParameter("email", email).getResultList();
            if(resutls.isEmpty()){
                return Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity(JsonUtil.createResponseJson("provided email doesn't exist", ResponseErrorCodes.USERS_NO_SUCH_USER))
                        .build();
            }
            else{
                if(!BcryptUtil.checkEq(pass, resutls.get(0).getPass())){
                    return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(JsonUtil.createResponseJson("login failed", ResponseErrorCodes.USERS_PASSWORD_MISSMATCH))
                        .build();
                }
                else{
                    return Response
                        .status(Response.Status.OK)
                        .entity(JsonUtil.createResponseJson(JsonUtil.createSimpleMessageObject("login successful")))
                        .build();
                }
            }
        });
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
