/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.models.User;
import com.yoni.javaworkshopprojectserver.utils.*;
import io.jsonwebtoken.JwtException;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

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
            return firstOrNull(getEntityManager()
                    .createNamedQuery("Users.findByEmail", User.class)
                    .setParameter("email", email)
                    .getResultList());
    }

    public User findById(int id){
        return firstOrNull(getEntityManager()
                .createNamedQuery("Users.findById", User.class)
                .setParameter("id", id)
                .getResultList());
    }


    public Result<User, Integer> updateInfo(int userId, String email, String pass, String firstName, String lastName, String phone, String address){
        User user = findById(userId);
        if(user == null){
            return Result.makeError(ErrorCodes.USERS_NO_SUCH_USER);
        }

        // check if email is in use
        User emailCheckUser = findByEmail(email);
        if(emailCheckUser != null && !emailCheckUser.getId().equals(user.getId())){
            return Result.makeError(ErrorCodes.USERS_ALREADY_EXISTS);
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

        withTransaction(() -> getEntityManager().persist(user));

        getEntityManager().refresh(user);

        Logger.log(TAG, "registered user: "+user);

        return Result.makeValue(user);
    }

    public String createToken(User user){
        return JwtUtils.create(user.getEmail(), user.getSecretKey());
    }

    public Result<Void, Integer> invalidateToken(int userId){
        User targetUser = findById(userId);
        if(targetUser == null){
            return Result.makeError(ErrorCodes.USERS_NO_SUCH_USER);
        }
        refreshSecretKey(targetUser);
        return Result.makeValue(null);
    }

    public Result<User, Integer> credentialLogin(String email, String pass){
        User user = findByEmail(email);
        if(user == null){
            return Result.makeError(ErrorCodes.USERS_NO_SUCH_USER);
        }

        if(!BcryptUtils.checkEq(pass, user.getPass())){
            return Result.makeError(ErrorCodes.USERS_PASSWORD_MISMATCH);
        }

        return Result.makeValue(user);

    }


}
