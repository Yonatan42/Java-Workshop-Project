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

    private static final String TAG = "UsersResource";

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
//                Logger.logError(TAG, e);
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

        Logger.logFormat(TAG, "<register>\nemail: %s\npass: %s\nfirstName: %s\nlastName: %s\nphone: %s\naddress: %s", email, pass, firstName, lastName, phone, address);
        return registerInternal(null, email, pass, firstName, lastName, phone, address, false);
    }

    private Response registerInternal(String senderToken,
                                      String email,
                                      String pass,
                                      String firstName,
                                      String lastName,
                                      String phone,
                                      String address,
                                      boolean isAdmin) {
        
        return ResponseLogger.loggedResponse(ResponseUtils.respondSafe(senderToken, () -> {
                if(userService.findByEmail(email) != null){
                    return Response
                            .status(Response.Status.CONFLICT)
                            .entity(JsonUtils.createResponseJson(senderToken, "user already exists", ErrorCodes.USERS_ALREADY_EXISTS))
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
                
                Logger.log(TAG, "registered user: "+user);
                
                String token = JwtUtils.create(email, user.getSecretKey());
                return Response
                    .status(Response.Status.CREATED)
                    .entity(JsonUtils.createResponseJson(senderToken != null ? senderToken : token, getLoginResponseJson(user)))
                    .build();

        }));
        

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

        Logger.logFormat(TAG, "<remoteRegister>\nAuthorization: %s\nemail: %s\npass: %s\nfirstName: %s\nlastName: %s\nphone: %s\naddress: %s\nisAdmin: %b", token, email, pass, firstName, lastName, phone, address, isAdmin);
        return ResponseLogger.loggedResponse(userService.authenticateEncapsulated(token, true, (u, t) ->
                ResponseUtils.respondSafe(t, () ->
                        registerInternal(t, email, pass, firstName, lastName, phone, address, isAdmin))));
    }



    @POST
    @Path("login-auth")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginAuth( 
            @FormParam("email") String email, 
            @FormParam("pass") String pass) {

        Logger.logFormat(TAG, "<loginAuth>\nemail: %s\npass: %s", email, pass);
        return ResponseLogger.loggedResponse(ResponseUtils.respondSafe(() -> {
            
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
                        .entity(JsonUtils.createResponseJson(token, getLoginResponseJson(u)))
                        .build();
                }
            }
        }));
    }
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login( 
            @HeaderParam("Authorization") String token) {

        Logger.logFormat(TAG, "<login>\nAuthorization: %s", token);
        return ResponseLogger.loggedResponse(userService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> Response
            .status(Response.Status.OK)
            .entity(JsonUtils.createResponseJson(t, getLoginResponseJson(u)))
            .build())));
    }
    
    private JsonElement getLoginResponseJson(User user){
        // todo - get categories from
        JsonObject root = new JsonObject();
        root.add("user",  JsonUtils.convertToJson(user));
//        root.add("categories",  JsonUtils.convertToJson(categories));
        return root;
    } 
    

    
    @PUT
    @Path("{userId}/invalidate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response invalidateToken(
            @HeaderParam("Authorization") String token,
            @PathParam("userId") int userId) {

        Logger.logFormat(TAG, "<invalidateToken>\nAuthorization: %s\nuserId: %d", token, userId);
        return ResponseLogger.loggedResponse(userService.authenticateEncapsulated(token, true, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            User targetUser = userService.findById(userId);
            if(targetUser == null){
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(JsonUtils.createResponseJson(t, "user id not found", ErrorCodes.USERS_NO_SUCH_USER))
                        .build();
            }
            userService.refreshSecretKey(targetUser.getEmail());
            getEntityManager().refresh(targetUser);
            return Response
                .status(Response.Status.OK)
                .entity(JsonUtils.createResponseJson(t, null))
                .build();
        })));
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
        Logger.logFormat(TAG, "<updateInfo>\nAuthorization: %s\nuserId: %d\nemail: %s\npass: %s\nfirstName: %s\nlastName: %s\nphone: %s\naddress: %s", token, userId, email, pass, firstName, lastName, phone, address);
        return ResponseLogger.loggedResponse(userService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            User user = userService.findById(userId);
            if(user == null){
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(JsonUtils.createResponseJson(t, "user id not found", ErrorCodes.USERS_NO_SUCH_USER))
                        .build();
            }

            // check if email is in use
            User emailCheckUser = userService.findByEmail(email);
            if(emailCheckUser != null && !emailCheckUser.getId().equals(user.getId())){
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity(JsonUtils.createResponseJson(t, "user already exists", ErrorCodes.USERS_ALREADY_EXISTS))
                        .build();
            }

            getEntityManager().getTransaction().begin();

            user.setEmail(email);
            user.setPass(BcryptUtils.encrypt(pass));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setAddress(address);
            user = super.edit(user);

            getEntityManager().getTransaction().commit();
            getEntityManager().refresh(user);

            Logger.log(TAG, "updated user: "+user);

            String newToken = JwtUtils.create(email, user.getSecretKey());
            return Response
                    .status(Response.Status.OK)
                    .entity(JsonUtils.createResponseJson(newToken, JsonUtils.convertToJson(user)))
                    .build();

        })));
    }
    // todo - encapsulate a smaller part of the register/edit logic and use the same main function for register, register-remote, and update info
}
