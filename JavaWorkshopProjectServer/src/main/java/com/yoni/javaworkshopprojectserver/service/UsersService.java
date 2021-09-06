/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.models.User;
import com.yoni.javaworkshopprojectserver.utils.*;
import io.jsonwebtoken.JwtException;
import java.util.function.BiFunction;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoni
 */
@Singleton
@LocalBean
public class UsersService extends BaseService {

    private static final String TAG = "UsersService";
    
    public Result<User, Integer> authenticate(String token){
        return authenticate(token, false);
    }

    
    public Result<User, Integer> authenticate(String token, boolean requiredAdmin){

        if(token == null){
            return Result.MakeError(ErrorCodes.TOKEN_INVALID);
        }

        boolean needsRefresh;
        String email;
        String secret;
        try{
            needsRefresh = JwtUtils.isExpired(token);
            email = JwtUtils.getEmail(token);
            secret = JwtUtils.getSecurityString(token);
        }
        catch(JwtException e){
            Logger.logError(TAG, e);
            return Result.MakeError(ErrorCodes.TOKEN_INVALID);
        }
        
        User user = findByEmail(email);

        if(user == null){
            return Result.MakeError(ErrorCodes.TOKEN_INVALID);
        }
        if(!user.getSecretKey().equals(secret)){
            return Result.MakeError(ErrorCodes.TOKEN_INVALID);
        }
        if(requiredAdmin && !user.isAdmin()){
            return Result.MakeError(ErrorCodes.USERS_UNAUTHORIZED);
        }
        
        if(needsRefresh){
            refreshSecretKey(email);
            getEntityManager().refresh(user);
        }

        return Result.MakeValue(user);
    }
    
    
    public void refreshSecretKey(String email){
        getEntityManager()
                .createNamedStoredProcedureQuery("Users.refreshSecretKey")
                .setParameter("email", email)
                .execute();

    }
    
        
    public User findByEmail(String email){
            return getEntityManager()
                    .createNamedQuery("Users.findByEmail", User.class)
                    .setParameter("email", email)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);
    }

    public User findById(int id){
        return getEntityManager()
                .createNamedQuery("Users.findById", User.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Response authenticateEncapsulated(String token, BiFunction<User, String, Response> action) {
        return authenticateEncapsulated(token, false, action);
    }
    public Response authenticateEncapsulated(String token, boolean requiresAdmin, BiFunction<User, String, Response> action){
        final String tokenFailureErrorMsg = "access denied";
        Result<User, Integer> authRes = authenticate(token, requiresAdmin);
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
