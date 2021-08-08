/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 *
 * @author Yoni
 */
public class JsonUtil {
 
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    private static String createResponseJson(JsonElement result, String errorMessage, int errorCode){
        JsonObject jsonRoot = new JsonObject();
        boolean hasError = errorCode > 0;
        jsonRoot.addProperty("hasError", hasError);

        if(hasError){
            JsonObject errorRoot = new JsonObject();
            errorRoot.addProperty("code", errorCode);
            errorRoot.addProperty("message", errorMessage);
            jsonRoot.add("error", errorRoot);
        }
        else{
            jsonRoot.add("result", result);
        }
        
        return GSON.toJson(jsonRoot);
    }
    
    public static String createResponseJson(JsonElement result){
        return createResponseJson(result, null, 0);
    }
    
    public static String createResponseJson(String errorMessage, int errorCode){
        return createResponseJson(null, errorMessage, errorCode);
    }
    
    public static JsonElement createSimpleMessageObject(String message){
        return new JsonPrimitive(message);
    }
    
    public static JsonElement convertToJson(Object entity){
        return GSON.toJsonTree(entity);
    }

    
    
}
