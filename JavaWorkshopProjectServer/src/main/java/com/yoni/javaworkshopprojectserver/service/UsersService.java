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
            return Result.makeError(ErrorCodes.TOKEN_INVALID);
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
            return Result.makeError(ErrorCodes.TOKEN_INVALID);
        }
        
        User user = findByEmail(email);

        if(user == null){
            return Result.makeError(ErrorCodes.TOKEN_INVALID);
        }
        if(!user.getSecretKey().equals(secret)){
            return Result.makeError(ErrorCodes.TOKEN_INVALID);
        }
        if(requiredAdmin && !user.isAdmin()){
            return Result.makeError(ErrorCodes.USERS_UNAUTHORIZED);
        }
        
        if(needsRefresh){
            refreshSecretKey(email);
            getEntityManager().refresh(user);
        }

        return Result.makeValue(user);
    }
    
    
    public void refreshSecretKey(String email){
        getEntityManager()
                .createNamedStoredProcedureQuery("Users.refreshSecretKey")
                .setParameter("email", email)
                .execute();

    }

    public void refreshSecretKey(User user){
        refreshSecretKey(user.getEmail());
        getEntityManager().refresh(user);

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


    public Result<User, Integer> updateInfo(int userId, String email, String pass, String firstName, String lastName, String phone, String address){
        User user = findById(userId);
        if(user == null){
            return Result.makeError(ErrorCodes.USERS_NO_SUCH_USER);
        }

        // check if email is in use
        User emailCheckUser = findByEmail(email);
        if(emailCheckUser != null && !emailCheckUser.getId().equals(user.getId())){
            Result.makeError(ErrorCodes.USERS_ALREADY_EXISTS);
        }

        user.setEmail(email);
        user.setPass(BcryptUtils.encrypt(pass));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setAddress(address);

        withTransaction(() -> getEntityManager().merge(user));

        getEntityManager().refresh(user);

        Logger.log(TAG, "updated user: "+user);

        return Result.makeValue(user);
    }


    public Result<User, Integer> register(String email, String pass, String firstName, String lastName, String phone, String address, boolean isAdmin){
        if(findByEmail(email) != null) {
            return Result.makeError(ErrorCodes.USERS_ALREADY_EXISTS);
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setPass(BcryptUtils.encrypt(pass));
        user.setAdmin(isAdmin);

        withTransaction(() -> getEntityManager().merge(user));

        getEntityManager().refresh(user);

        Logger.log(TAG, "registered user: "+user);

        return Result.makeValue(user);
    }

    public String createToken(User user){
        return JwtUtils.create(user.getEmail(), user.getSecretKey());
    }


}
