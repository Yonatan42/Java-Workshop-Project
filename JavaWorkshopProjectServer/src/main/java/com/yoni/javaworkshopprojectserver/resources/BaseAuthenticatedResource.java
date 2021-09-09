package com.yoni.javaworkshopprojectserver.resources;


import com.yoni.javaworkshopprojectserver.models.User;
import com.yoni.javaworkshopprojectserver.service.UsersService;
import com.yoni.javaworkshopprojectserver.utils.ErrorCodes;
import com.yoni.javaworkshopprojectserver.utils.JwtUtils;
import com.yoni.javaworkshopprojectserver.utils.ResponseUtils;
import com.yoni.javaworkshopprojectserver.utils.Result;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import java.util.function.BiFunction;

public abstract class BaseAuthenticatedResource {

    @EJB
    private UsersService usersService;

    // admin is not required
    public Response authenticateEncapsulated(String token, BiFunction<User, String, Response> action) {
        return authenticateEncapsulated(token, false, action);
    }

    // admin is required only if the user has not requested for themselves
    public Response authenticateEncapsulated(String token, int userId, BiFunction<User, String, Response> action) {
        User user = usersService.findById(userId);
        boolean requiresAdmin = user == null || !user.getEmail().equals(JwtUtils.getEmail(token));
        return authenticateEncapsulated(token, requiresAdmin, action);
    }

    // admin is designated as required or not
    public Response authenticateEncapsulated(String token, boolean requiresAdmin, BiFunction<User, String, Response> action){
        final String tokenFailureErrorMsg = "access denied";
        Result<User, Integer> authRes = usersService.authenticate(token, requiresAdmin);
        if(!authRes.isValid()){
            switch(authRes.getError()){
                case ErrorCodes.TOKEN_INVALID:
                    return ResponseUtils.createSimpleErrorResponse(tokenFailureErrorMsg, Response.Status.FORBIDDEN, ErrorCodes.TOKEN_INVALID);
                case ErrorCodes.USERS_UNAUTHORIZED:
                    return ResponseUtils.createSimpleErrorResponse(tokenFailureErrorMsg, Response.Status.FORBIDDEN, ErrorCodes.USERS_UNAUTHORIZED);
            }
        }
        User user = authRes.getValue();
        String returnToken = token;
        String oldSecret = JwtUtils.getSecurityString(token);
        String newSecret = user.getSecretKey();
        if(!oldSecret.equals(newSecret)){
            returnToken = JwtUtils.create(user.getEmail(), newSecret);
        }
        return action.apply(user, returnToken);
    }

}
