/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yoni.javaworkshopprojectserver.models.User;
import com.yoni.javaworkshopprojectserver.utils.*;
import com.yoni.javaworkshopprojectserver.utils.JsonUtils;
import com.yoni.javaworkshopprojectserver.service.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
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
    
    // todo - remove all these later
//    @POST
//    @Override
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void create(Customer entity) {
//        super.create(entity);
//    }
//
//
//    // todo - convert to response
//    @PUT
//    @Path("{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void edit(@PathParam("id") Integer id, User entity) {
//        super.edit(entity);
//    }
//
//    // todo - convert to response
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }
//
//
//    // todo - we probably don't want this since we are talking about customers
//    @GET
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response find(@PathParam("id") Integer id) {
//        return ResponseUtils.respondSafe(() -> {
//            try{
//                User c = super.find(id);
//                return Response
//                        .status(Response.Status.OK)
//                        .entity(JsonUtils.createResponseJson(JsonUtils.convertToJson(c)))
//                        .build();
//            }
//            catch(IllegalArgumentException e){
//                e.printStackTrace(System.err);
//                return Response
//                        .status(Response.Status.NOT_FOUND)
//                        .entity(JsonUtils.createResponseJson("no customer found to provided id", ErrorCodes.USERS_NO_SUCH_USER))
//                        .build();
//            }
//        });
//    }
//
//    // todo - we probably don't want this since we are talking about customers
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response findAllRes() {
//        return ResponseUtils.respondSafe(() -> {
//            return Response
//                    .status(Response.Status.OK)
//                    .entity(JsonUtils.createResponseJson(JsonUtils.convertToJson(super.findAll())))
//                    .build();
//        });
//    }
//
//
//    @GET
//    @Path("count")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response countRes() {
//        return ResponseUtils.respondSafe(() -> {
//            return Response
//                    .status(Response.Status.OK)
//                    .entity(JsonUtils.createResponseJson(JsonUtils.createSimpleMessageObject(String.valueOf(super.count()))))
//                    .build();
//        });
//    }
//

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(
            @FormParam("email") String email,
            @FormParam("pass") String pass,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("phone") String phone,
            @FormParam("address") String address){

        return registerInternal(email, pass, firstName, lastName, phone, address, false);
    }

    private Response registerInternal(String email,
                             String pass,
                             String firstName,
                             String lastName,
                             String phone,
                             String address,
                             boolean isAdmin) {
        
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
                user.setAdmin(isAdmin);
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
    @Path("remote-register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remoteRegister(
              @HeaderParam("Authorization") String token,
              @FormParam("email") String email,
              @FormParam("pass") String pass,
              @FormParam("firstName") String firstName,
              @FormParam("lastName") String lastName,
              @FormParam("phone") String phone,
              @FormParam("address") String address,
              @FormParam("isAdmin") boolean isAdmin) {

        return ResponseUtils.respondSafe(() ->
                userService.authenticateEncapsulated(token, true, (u, t) ->
                        registerInternal(email, pass, firstName, lastName, phone, address, isAdmin)));
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
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, (u, t) -> Response
            .status(Response.Status.OK)
            .entity(JsonUtils.createResponseJson(getLoginResponseJson(u, t)))
            .build()));
    }
    
    private JsonElement getLoginResponseJson(User user, String token){
        // todo - get categories from
        JsonObject root = new JsonObject();
        root.add("user",  JsonUtils.convertToJson(user));
//        root.add("categories",  JsonUtils.convertToJson(categories));
        return JsonUtils.createStandardTokennedJson(token,root);
    } 
    

    
    @PUT
    @Path("{userId}/invalidate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response invalidateToken(
            @HeaderParam("Authorization") String token,
            @PathParam("userId") int userId) {
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, true, (u, t) -> {
            User targetUser = userService.findById(userId);
            if(targetUser == null){
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(JsonUtils.createResponseJson("user id not found", ErrorCodes.USERS_NO_SUCH_USER))
                        .build();
            }
            userService.refreshSecretKey(targetUser.getEmail());
            getEntityManager().refresh(targetUser);
            return Response
                .status(Response.Status.OK)
                .entity(JsonUtils.createResponseJson(JsonUtils.createStandardTokennedJson(t, null)))
                .build();
        }));
    }

    @PUT
    @Path("{userId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateInfo(
            @HeaderParam("Authorization") String token,
            @PathParam("userId") int userId,
            @FormParam("email") String email,
            @FormParam("pass") String pass,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("phone") String phone,
            @FormParam("address") String address
    ){
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, true, (u, t) -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        }));
    }
}
