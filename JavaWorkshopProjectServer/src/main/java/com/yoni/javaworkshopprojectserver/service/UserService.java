/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.EntityManagerSingleton;
import com.yoni.javaworkshopprojectserver.models.users.AbstractUser;
import com.yoni.javaworkshopprojectserver.models.users.ExtendedUser;
import com.yoni.javaworkshopprojectserver.utils.ErrorCodes;
import com.yoni.javaworkshopprojectserver.utils.JwtUtil;
import com.yoni.javaworkshopprojectserver.utils.ResponseUtil;
import com.yoni.javaworkshopprojectserver.utils.Result;
import io.jsonwebtoken.JwtException;
import java.util.function.BiFunction;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoni
 */
@Singleton
@LocalBean
public class UserService {
       
    @EJB
    private EntityManagerSingleton entityManagerBean;
   
    
    public Result<AbstractUser, Integer> authenticate(String token){
        return authenticate(token, false);
    }

    
    public Result<AbstractUser, Integer> authenticate(String token, boolean requiredAdmin){
        boolean needsRefresh;
        String email;
        String secret;
        try{
            needsRefresh = JwtUtil.isExpired(token);
            email = JwtUtil.getEmail(token);
            secret = JwtUtil.getSecurityString(token);
        }
        catch(JwtException e){
            e.printStackTrace(System.err);
            return Result.MakeError(ErrorCodes.TOKEN_INVALID);
        }
        
        ExtendedUser u = findByEmail(email); 
        
        if(u == null){
            return Result.MakeError(ErrorCodes.TOKEN_INVALID);
        }
        if(!u.getSecretKey().equals(secret)){
            return Result.MakeError(ErrorCodes.TOKEN_INVALID);
        }
        if(requiredAdmin && !u.isAdmin()){
            return Result.MakeError(ErrorCodes.USERS_UNAUTHORIZED);
        }
        
        if(needsRefresh){
            refreshSecretKey(email);
            getEntityManager().refresh(u);
        }
        
        return Result.MakeValue(u);
    }
    
    
    private boolean refreshSecretKey(String email){
        return getEntityManager()
                .createNamedStoredProcedureQuery("ExtendedUsers.refreshSecretKey")
                .setParameter("email", email)
                .execute();

    }
    
        
    public ExtendedUser findByEmail(String email){
            return getEntityManager()
                    .createNamedQuery("ExtendedUsers.findByEmail", ExtendedUser.class)
                    .setParameter("email", email)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);
    } 
    
    public Response authenticateEncapsulated(String token, BiFunction<AbstractUser, String, Response> action){
        Result<AbstractUser, Integer> authRes = authenticate(token);
        if(!authRes.isValid()){
            switch(authRes.getError()){
                case ErrorCodes.TOKEN_INVALID:
                    return ResponseUtil.createSimpleErrorResponse("login failed", Response.Status.FORBIDDEN, ErrorCodes.TOKEN_INVALID);
                case ErrorCodes.USERS_UNAUTHORIZED:
                    return ResponseUtil.createSimpleErrorResponse("login failed", Response.Status.FORBIDDEN, ErrorCodes.USERS_UNAUTHORIZED);
            }
        }
        AbstractUser u = authRes.getValue();
        String t = token;
        String oldSecret = JwtUtil.getSecurityString(token);
        String newSecret = u.getSecretKey();
        if(!oldSecret.equals(newSecret)){
            t = JwtUtil.create(u.getEmail(), newSecret);
        }
        return action.apply(u, t);   
    }
    
    private EntityManager getEntityManager(){
        return entityManagerBean.getEntityManager();
    }
}
