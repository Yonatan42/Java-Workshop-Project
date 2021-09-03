/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yoni.javaworkshopprojectserver.models.users.User;
import com.yoni.javaworkshopprojectserver.utils.*;
import com.yoni.javaworkshopprojectserver.utils.JsonUtils;
import com.yoni.javaworkshopprojectserver.service.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoni
 */
@Stateless
@Path("users")
public class UsersResource extends AbstractRestResource<User> {

    @EJB
    private UserService userService;
    
    
    public UsersResource() {
        super(User.class);
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
    public void edit(@PathParam("id") Integer id, User entity) {
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
        return ResponseUtils.respondSafe(() -> {
            try{
                User c = super.find(id);
                return Response
                        .status(Response.Status.OK)
                        .entity(JsonUtils.createResponseJson(JsonUtils.convertToJson(c)))
                        .build();
            }
            catch(IllegalArgumentException e){
                e.printStackTrace(System.err);
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(JsonUtils.createResponseJson("no customer found to provided id", ErrorCodes.USERS_NO_SUCH_USER))
                        .build();
            }
        });
    }

    // todo - we probably don't want this since we are talking about customers
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllRes() {
        return ResponseUtils.respondSafe(() -> {
            return Response
                    .status(Response.Status.OK)
                    .entity(JsonUtils.createResponseJson(JsonUtils.convertToJson(super.findAll())))
                    .build();
        });
    }


    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countRes() {
        return ResponseUtils.respondSafe(() -> {
            return Response
                    .status(Response.Status.OK)
                    .entity(JsonUtils.createResponseJson(JsonUtils.createSimpleMessageObject(String.valueOf(super.count()))))
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
        
        return ResponseUtils.respondSafe(() -> {
                if(userService.findByEmail(email) != null){
                    return Response
                            .status(Response.Status.CONFLICT)
                            .entity(JsonUtils.createResponseJson("user already exists", ErrorCodes.USERS_ALREADY_EXISTS))
                            .build();
                }
                getEntityManager().getTransaction().begin();
                
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhone(phone);
                user.setEmail(email);
                user.setAddress(address);
                user.setPass(BcryptUtils.encrypt(pass));
                user = super.edit(user);

                getEntityManager().getTransaction().commit();
                
                getEntityManager().refresh(user);
                
                System.out.println("registered customer: "+user);
                
                String token = JwtUtils.create(email, user.getSecretKey());
                return Response
                    .status(Response.Status.CREATED)
                    .entity(JsonUtils.createResponseJson(getLoginResponseJson(user, token)))
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

        return ResponseUtils.respondSafe(() -> {
            
            User u = userService.findByEmail(email);
            if(u == null){
                return Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity(JsonUtils.createResponseJson("provided email doesn't exist", ErrorCodes.USERS_NO_SUCH_USER))
                        .build();
            }
            else{
                if(!BcryptUtils.checkEq(pass, u.getPass())){
                    return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(JsonUtils.createResponseJson("login failed", ErrorCodes.USERS_PASSWORD_MISSMATCH))
                        .build();
                }
                else{
                    String token = JwtUtils.create(email,u.getSecretKey());
                    return Response
                        .status(Response.Status.OK)
                        .entity(JsonUtils.createResponseJson(getLoginResponseJson(u, token)))
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
            @HeaderParam("Authorization") String token) {
        return ResponseUtils.respondSafe(() -> {
            return userService.authenticateEncapsulated(token, (u, t) -> {
                return Response
                    .status(Response.Status.OK)
                    .entity(JsonUtils.createResponseJson(getLoginResponseJson(u, t)))
                    .build();
            });
        });
    }
    
    private JsonElement getLoginResponseJson(User user, String token){
        // todo - get categories from
        JsonObject root = new JsonObject();
        root.add("user",  JsonUtils.convertToJson(user));
//        root.add("categories",  JsonUtils.convertToJson(categories));
        return JsonUtils.createStandardTokennedJson(token,root);
    } 
    
    
    
    @GET
    @Path("token/refresh/{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public String refreshToken(@PathParam("email") String email) {
        User u = userService.findByEmail(email);
        String before = u.getSecretKey();
        getEntityManager().createNamedStoredProcedureQuery("Users.refreshSecretKey").setParameter("email", email).execute();
        getEntityManager().refresh(u);
//         u = findByEmail(email);
         String after = u.getSecretKey();
         return "before:\n"+before+"\nafter:\n"+after;
    }
    // todo - need to make a refresh endpoint that takes a token that of an admin in the header along wth the customer's email as a param
    // first authenticates the admin before refreshing the customer's token
    
    
    @GET
    @Path("token/create/{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public String testToken(@PathParam("email") String email) {
        return JwtUtils.create(email,""); // will update the second value
    }
    
//    @GET
//    @Path("token/validate/{email}/{token}")
//    @Produces(MediaType.TEXT_PLAIN)
//    public boolean testToken(@PathParam("email") String email, @PathParam("token") String token) {
//        return JwtUtils.isValid(token, email);
//    }
    



//    private boolean checkAuth(
//            /*@HeaderParam("authorization") */String token) {
//        if(JwtUtils.isExpired(token)){
//            return false;
//        }
//        String email = JwtUtils.getEmail(token);
//    }
}
