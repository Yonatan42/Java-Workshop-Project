/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.utils;

import java.util.function.Supplier;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Yoni
 */
public class ResponseUtils {
    
    private ResponseUtils(){}
    

    private static final String TAG = "ResponseUtils";

    public static Response respondSafe(Supplier<Response> action) {
        return respondSafe(null, action);
    }

    public static Response respondSafe(String token, Supplier<Response> action){
        try{
            return action.get();
        }
        catch (ConstraintViolationException e){
            Logger.logError(TAG, e);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(JsonUtils.createResponseJson(token, "a constraint violation occurred", ErrorCodes.PERSISTENCE_CONSTRAINT_VIOLATION))
                    .build();
        }
        catch(PersistenceException e){
            Logger.logError(TAG, e);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(JsonUtils.createResponseJson(token, "a persistence error occurred", ErrorCodes.PERSISTENCE_GENERAL))
                    .build();
        }
        catch(Exception e){
            Logger.logError(TAG, e);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(JsonUtils.createResponseJson(token, "unexpected error occurred", ErrorCodes.UNKNOWN))
                    .build();
        }
    }

    public static Response createSimpleErrorResponse(String message, Status status, int errorCode) {
        return createSimpleErrorResponse(null, message, status, errorCode);
    }

    public static Response createSimpleErrorResponse(String token, String message, Status status, int errorCode){
        return createSimpleErrorResponse(token, message, status.getStatusCode(), errorCode);
    }

    public static Response createSimpleErrorResponse(String message, int status, int errorCode) {
        return createSimpleErrorResponse(null, message, status, errorCode);
    }
    
    public static Response createSimpleErrorResponse(String token, String message, int status, int errorCode){
        return Response
                    .status(status)
                    .entity(JsonUtils.createResponseJson(token, message, errorCode))
                    .build();
    }
    
}
