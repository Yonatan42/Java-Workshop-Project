/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yoni.javaworkshopprojectserver.models.Category;
import com.yoni.javaworkshopprojectserver.models.User;
import com.yoni.javaworkshopprojectserver.service.ProductsService;
import com.yoni.javaworkshopprojectserver.service.UsersService;
import com.yoni.javaworkshopprojectserver.utils.*;
import com.yoni.javaworkshopprojectserver.utils.JsonUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Yoni
 */
@Stateless
@Path("users")
public class UsersResource{

    private static final String TAG = "UsersResource";

    @EJB
    private UsersService usersService;

    @EJB
    private ProductsService productsService;
    


    // todo - move all db stuff to service and return Result that can hold error code



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

            Result<User, Integer> result = usersService.register(email, pass, firstName, lastName, phone, address, isAdmin);
            if(result.isValid()) {
                User user = result.getValue();
                String token = senderToken != null ? senderToken : usersService.createToken(user);
                return Response
                        .status(Response.Status.CREATED)
                        .entity(JsonUtils.createResponseJson(token, getLoginResponseJson(user)))
                        .build();
            }

            int errorCode = result.getError();
            Response.Status status;
            String errorMsg;
            switch (result.getError()){
                case ErrorCodes.USERS_ALREADY_EXISTS:
                    status = Response.Status.CONFLICT;
                    errorMsg = "user already exists";
                    break;
                default:
                    status = Response.Status.INTERNAL_SERVER_ERROR;
                    errorMsg = ErrorCodes.UNKNOWN_ERROR_MSG;

            }
            return Response
                    .status(status)
                    .entity(JsonUtils.createResponseJson(senderToken, errorMsg, errorCode))
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
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, true, (u, t) ->
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
            // todo - move to service and return result
            User u = usersService.findByEmail(email);
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
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> Response
            .status(Response.Status.OK)
            .entity(JsonUtils.createResponseJson(t, getLoginResponseJson(u)))
            .build())));
    }
    
    @PUT
    @Path("{userId}/invalidate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response invalidateToken(
            @HeaderParam("Authorization") String token,
            @PathParam("userId") int userId) {

        Logger.logFormat(TAG, "<invalidateToken>\nAuthorization: %s\nuserId: %d", token, userId);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, true, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - restructure this to make the thing a Result
            User targetUser = usersService.findById(userId);
            if(targetUser == null){
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(JsonUtils.createResponseJson(t, "user id not found", ErrorCodes.USERS_NO_SUCH_USER))
                        .build();
            }
            usersService.refreshSecretKey(targetUser);
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
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {

            if(userId != u.getId()){
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity(JsonUtils.createResponseJson(t, "token does not match user id", ErrorCodes.USERS_INCONSISTANT))
                        .build();
            }

            Result<User, Integer> result = usersService.updateInfo(userId, email, pass, firstName, lastName, phone, address);
            if(result.isValid()){
                User user = result.getValue();
                String newToken = usersService.createToken(user);
                return Response
                        .status(Response.Status.OK)
                        .entity(JsonUtils.createResponseJson(newToken, JsonUtils.convertToJson(user)))
                        .build();
            }

            int errorCode = result.getError();
            Response.Status status;
            String errorMsg;
            switch (result.getError()){
                case ErrorCodes.USERS_NO_SUCH_USER:
                    errorMsg = "user id not found";
                    status = Response.Status.NOT_FOUND;
                    break;
                case ErrorCodes.USERS_ALREADY_EXISTS:
                    status = Response.Status.CONFLICT;
                    errorMsg = "user already exists";
                    break;
                default:
                    status = Response.Status.INTERNAL_SERVER_ERROR;
                    errorMsg = ErrorCodes.UNKNOWN_ERROR_MSG;

            }
            return Response
                    .status(status)
                    .entity(JsonUtils.createResponseJson(t, errorMsg, errorCode))
                    .build();


        })));
    }
    // todo - encapsulate a smaller part of the register/edit logic and use the same main function for register, register-remote, and update info


    private JsonElement getLoginResponseJson(User user){
        JsonObject root = new JsonObject();
        root.add("user",  JsonUtils.convertToJson(user));
        List<Category> categories = productsService.getAllCategories();
        root.add("categories",  JsonUtils.convertToJson(categories));
        return root;
    }
}
