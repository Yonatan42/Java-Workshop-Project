/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yoni.javaworkshopprojectserver.models.users.AbstractUser;
import com.yoni.javaworkshopprojectserver.models.users.Customer;
import com.yoni.javaworkshopprojectserver.models.users.ExtendedUser;
import com.yoni.javaworkshopprojectserver.utils.BcryptUtil;
import com.yoni.javaworkshopprojectserver.utils.JsonUtil;
import com.yoni.javaworkshopprojectserver.utils.JwtUtil;
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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoni
 */
@Stateless
@Path("users")
public class UsersFacadeREST extends AbstractFacade<AbstractUser> {

    public UsersFacadeREST() {
        super(AbstractUser.class);
    }
    
    // todo - delete. here we have register instead
//
//    @POST
//    @Override
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void create(Customer entity) {
//        super.create(entity);
//    }

    // todo - convert to response
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, AbstractUser entity) {
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
                AbstractUser c = super.find(id);
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
                if(findByEmail(email) != null){
                    return Response
                            .status(Response.Status.CONFLICT)
                            .entity(JsonUtil.createResponseJson("user already exists", ResponseErrorCodes.USERS_USER_ALREADY_EXISTS))
                            .build();
                }
                getEntityManager().getTransaction().begin();
                
                Customer c = new Customer();
                c.setFirstName(firstName);
                c.setLastName(lastName);
                c.setPhone(phone);
                c.setEmail(email);
                c.setAddress(address);
                c.setPass(BcryptUtil.encrypt(pass));
                super.create(c);

                getEntityManager().getTransaction().commit();
                
                String token = JwtUtil.create(email, ""); // will update the second value
                return Response
                    .status(Response.Status.CREATED)
                    .entity(JsonUtil.createResponseJson(getLoginResponseJson(c, token)))
                    .build();

        });
        

    }
    
    
    @POST
    @Path("login-auth")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginAuth( 
            @FormParam("email") String email, 
            @FormParam("pass") String pass) {

        return ResponseUtil.RespondSafe(() -> {
            
            AbstractUser u = findByEmail(email);
            if(u == null){
                return Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity(JsonUtil.createResponseJson("provided email doesn't exist", ResponseErrorCodes.USERS_NO_SUCH_USER))
                        .build();
            }
            else{
                if(!BcryptUtil.checkEq(pass, u.getPass())){
                    return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(JsonUtil.createResponseJson("login failed", ResponseErrorCodes.USERS_PASSWORD_MISSMATCH))
                        .build();
                }
                else{
                    String token = JwtUtil.create(email,""); // will update the second value
                    return Response
                        .status(Response.Status.OK)
                        .entity(JsonUtil.createResponseJson(getLoginResponseJson(u, token)))
                        .build();
                }
            }
        });
    }
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login( 
            @HeaderParam("authorization") String token) {
        // todo - make auth check fucntion and use here
        return ResponseUtil.RespondSafe(() -> {
            if(JwtUtil.isExpired(token)){
                // do refresh token thing
                return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(JsonUtil.createResponseJson("login failed", ResponseErrorCodes.TOKEN_EXPIRED))
                        .build();
            }
            String email = JwtUtil.getEmail(token);
            AbstractUser u = findByEmail(email);
            if(u == null){
                return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(JsonUtil.createResponseJson("provided email doesn't exist", ResponseErrorCodes.TOKEN_INVALID))
                        .build();
            }
            else{
                return Response
                    .status(Response.Status.OK)
                    .entity(JsonUtil.createResponseJson(getLoginResponseJson(u, token)))
                    .build();
            }
        });
    }
    
    private JsonElement getLoginResponseJson(AbstractUser u, String token){
//        return JsonUtil.createSimpleMessageObject("login successful");
        JsonObject root = new JsonObject();
        root.addProperty("token", token);
        root.add("user", JsonUtil.convertToJson(u));
        // add any other data we may need later
        return root;
    } 
    
    private AbstractUser findByEmail(String email){
            List<ExtendedUser> results = getEntityManager().createNamedQuery("ExtendedUsers.findByEmail", ExtendedUser.class).setParameter("email", email).getResultList();
            if(results.isEmpty()){
                return null;
            }
            return results.get(0);
    } 
    
    
    @GET
    @Path("token/create/{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public String testToken(@PathParam("email") String email) {
        return JwtUtil.create(email,""); // will update the second value
    }
    
    @GET
    @Path("token/validate/{email}/{token}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean testToken(@PathParam("email") String email, @PathParam("token") String token) {
        return JwtUtil.isValid(token, email);
    }
    
    

//    private boolean checkAuth(
//            /*@HeaderParam("authorization") */String token) {
//        if(JwtUtil.isExpired(token)){
//            return false;
//        }
//        String email = JwtUtil.getEmail(token);
//    }
}


/*
todo - 
I have renamed this to UsersFacadeREST
the intention is to have the entire user service here including customers, admins, both together (users), as well as token endpoints 
*/