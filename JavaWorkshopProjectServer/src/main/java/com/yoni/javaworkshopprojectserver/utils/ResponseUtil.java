/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.utils;

import java.util.function.Supplier;
import javax.persistence.PersistenceException;

/**
 *
 * @author Yoni
 */
public class ResponseUtil {
    
    public static String RespondSafe(Supplier<String> action){
        try{
            return action.get();
        }
        catch(PersistenceException e){
            e.printStackTrace(System.err);
            return JsonUtil.createResponseJson("a persistence error occurred", ResponseErrorCodes.PERSISTENCE_GENERAL);
        }
        catch(Exception e){
            e.printStackTrace(System.err);
            return JsonUtil.createResponseJson("unexpected error occurred", ResponseErrorCodes.UNKNOWN);
        }
    }
    
}
