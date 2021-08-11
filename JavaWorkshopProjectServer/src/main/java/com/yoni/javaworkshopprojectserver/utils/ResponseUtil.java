/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.utils;

import com.yoni.javaworkshopprojectserver.models.users.AbstractUser;
import com.yoni.javaworkshopprojectserver.models.users.ExtendedUser;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Yoni
 */
public class ResponseUtil {
    
    public static Response respondSafe(Supplier<Response> action){
        try{
            return action.get();
        }
        catch(PersistenceException e){
            e.printStackTrace(System.err);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(JsonUtil.createResponseJson("a persistence error occurred", ErrorCodes.PERSISTENCE_GENERAL))
                    .build();
        }
        catch(Exception e){
            e.printStackTrace(System.err);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(JsonUtil.createResponseJson("unexpected error occurred", ErrorCodes.UNKNOWN))
                    .build();
        }
    }
    
    public static Response createSimpleErrorResponse(String message, Status status, int errorCode){
        return createSimpleErrorResponse(message, status.getStatusCode(), errorCode);
    }
    
    public static Response createSimpleErrorResponse(String message, int status, int errorCode){
        return Response
                    .status(status)
                    .entity(JsonUtil.createResponseJson(message, errorCode))
                    .build();
    }
    
}
