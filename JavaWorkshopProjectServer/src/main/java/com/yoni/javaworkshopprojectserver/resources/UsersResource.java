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
public class UsersResource extends BaseAuthenticatedResource{

    private static final String TAG = "UsersResource";

    @EJB
    private UsersService usersService;
    @EJB
    private ProductsService productsService;


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
            switch (errorCode){
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
        return ResponseLogger.loggedResponse(authenticateEncapsulated(token, true, (u, t) ->
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
            Result<User, Integer> result = usersService.credentialLogin(email, pass);
            if(result.isValid()){
                User user = result.getValue();
                String token = usersService.createToken(user);
                return Response
                        .status(Response.Status.OK)
                        .entity(JsonUtils.createResponseJson(token, getLoginResponseJson(user)))
                        .build();
            }

            int errorCode = result.getError();
            Response.Status status;
            String errorMsg;
            switch (errorCode){
                case ErrorCodes.USERS_NO_SUCH_USER:
                    errorMsg = "provided email doesn't exist";
                    status = Response.Status.UNAUTHORIZED;
                    break;
                    case ErrorCodes.USERS_PASSWORD_MISSMATCH:
                    errorMsg = "login failed";
                    status = Response.Status.FORBIDDEN;
                    break;
                default:
                    status = Response.Status.INTERNAL_SERVER_ERROR;
                    errorMsg = ErrorCodes.UNKNOWN_ERROR_MSG;

            }
            return Response
                    .status(status)
                    .entity(JsonUtils.createResponseJson(errorMsg, errorCode))
                    .build();
        }));
    }
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login( 
            @HeaderParam("Authorization") String token) {

        Logger.logFormat(TAG, "<login>\nAuthorization: %s", token);
        return ResponseLogger.loggedResponse(authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> Response
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
        return ResponseLogger.loggedResponse(authenticateEncapsulated(token, true, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            Result<Void, Integer> result = usersService.invalidateToken(userId);
            if(result.isValid()){
                String returnToken = t;
                if(userId == u.getId()){ // the the user invalidated their own token
                    returnToken = usersService.createToken(u);
                }
                return Response
                        .status(Response.Status.OK)
                        .entity(JsonUtils.createResponseJson(returnToken, null))
                        .build();
            }

            int errorCode = result.getError();
            Response.Status status;
            String errorMsg;
            switch (errorCode){
                case ErrorCodes.USERS_NO_SUCH_USER:
                    errorMsg = "user id not found";
                    status = Response.Status.NOT_FOUND;
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
        return ResponseLogger.loggedResponse(authenticateEncapsulated(token, userId, (u, t) -> ResponseUtils.respondSafe(t, () -> {

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
            switch (errorCode){
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


    private JsonElement getLoginResponseJson(User user){
        JsonObject root = new JsonObject();
        root.add("user",  JsonUtils.convertToJson(user));
        List<Category> categories = productsService.getAllCategories();
        root.add("categories",  JsonUtils.convertToJson(categories));
        return root;
    }
}
