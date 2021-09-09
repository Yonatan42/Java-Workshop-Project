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

    public Response authenticateEncapsulated(String token, BiFunction<User, String, Response> action) {
        return authenticateEncapsulated(token, false, action);
    }
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
        User u = authRes.getValue();
        String t = token;
        String oldSecret = JwtUtils.getSecurityString(token);
        String newSecret = u.getSecretKey();
        if(!oldSecret.equals(newSecret)){
            t = JwtUtils.create(u.getEmail(), newSecret);
        }
        return action.apply(u, t);
    }

}
