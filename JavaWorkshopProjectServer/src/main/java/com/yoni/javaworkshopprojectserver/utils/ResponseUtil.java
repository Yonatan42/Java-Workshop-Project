/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.utils;

import java.util.function.Supplier;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoni
 */
public class ResponseUtil {
    
    public static Response RespondSafe(Supplier<Response> action){
        try{
            return action.get();
        }
        catch(PersistenceException e){
            e.printStackTrace(System.err);
            return Response
                    .status(500)
                    .entity(JsonUtil.createResponseJson("a persistence error occurred", ResponseErrorCodes.PERSISTENCE_GENERAL))
                    .build();
        }
        catch(Exception e){
            e.printStackTrace(System.err);
            return Response
                    .status(500)
                    .entity(JsonUtil.createResponseJson("unexpected error occurred", ResponseErrorCodes.UNKNOWN))
                    .build();
        }
    }
    
}
