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
public class JsonUtils {
    
    private JsonUtils(){}
    
 
    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    
    private static String createResponseJson(String token, JsonElement result, String errorMessage, int errorCode){
        JsonObject jsonRoot = new JsonObject();
        if(token != null) {
            jsonRoot.addProperty("token", token);
        }
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

    private static String createResponseJson(JsonElement result, String errorMessage, int errorCode) {
        return createResponseJson(null, result, errorMessage, errorCode);
    }

    public static String createResponseJson(JsonElement result){
        return createResponseJson(null, result, null, 0);
    }

    public static String createResponseJson(String token, JsonElement result){
        return createResponseJson(token, result, null, 0);
    }

    public static String createResponseJson(String errorMessage, int errorCode){
        return createResponseJson(null, null, errorMessage, errorCode);
    }
    
    public static String createResponseJson(String token, String errorMessage, int errorCode){
        return createResponseJson(token, null, errorMessage, errorCode);
    }
    
    public static JsonElement createSimpleMessageObject(String message){
        return new JsonPrimitive(message);
    }
    
    public static JsonElement convertToJson(Object entity){
        return GSON.toJsonTree(entity);
    }
    
    public static String convertToJsonString(Object entity){
        return GSON.toJson(entity);
    }
    
    public static String convertJsonToString(JsonElement json){
        return GSON.toJson(json);
    }

    
    
}
