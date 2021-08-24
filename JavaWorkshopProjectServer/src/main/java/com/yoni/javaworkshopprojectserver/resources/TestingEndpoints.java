/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;  
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Yoni
 */
@Stateless
@Path("testing")
public class TestingEndpoints{

    
    
    public TestingEndpoints() {
    }
    
    
    
    private String readFileContents(String path) throws IOException{
        final String pathFolder = "/Users/Yoni/OpenUni/Java-Workshop-Project/test_jsons/";
        return String.join("\n", Files.readAllLines(Paths.get(pathFolder, path), StandardCharsets.UTF_8));
    }
    
//    @GET
//    @Path("users/login")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String loginTest() {
//        return 
//"{\n" +
//"    \"hasError\": false,\n" +
//"    \"result\": {\n" +
//"        \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJteWVtYWlsN0BteS5lbWFpbGQ0OTBjODdhMjYyZDVkODhlMzRmNTY5ZWM2NGMxZjMxYjg0ZDg4ODIyM2UxZjQwYzg1NWU0MGU3YmEyYjE3N2MiLCJpYXQiOjE2MjkzODQ2OTIsImV4cCI6MTYyOTM4NDk0Mn0.6G2peVjWbA6XQalp6jQgg1yA6tPIBqv7F6FkEanThFY\",\n" +
//"        \"user\": {\n" +
//"            \"isAdmin\": false,\n" +
//"            \"id\": 19,\n" +
//"            \"created\": \"Aug 8, 2021 6:40:13 PM\",\n" +
//"            \"modified\": \"Aug 19, 2021 5:51:32 PM\",\n" +
//"            \"email\": \"myemail7@my.email\",\n" +
//"            \"firstName\": \"fn42\",\n" +
//"            \"lastName\": \"ln42\",\n" +
//"            \"phone\": \"0547989315\",\n" +
//"            \"address\": \"man of the post\"\n" +
//"        }\n" +
//"    }\n" +
//"}";
//    }
    
    
    @POST
    @Path("users/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginTest(
            @HeaderParam("Authorization") String token
        ) throws IOException {
        return readFileContents("login.json");
    }
    
    @POST
    @Path("users/login-auth")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginAuthTest(
            @FormParam("email") String email, 
            @FormParam("pass") String pass
        ) throws IOException {
        return readFileContents("login.json");
    }
    
            
//    @GET
//    @Path("products/")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String allProductsTest(    
//            @HeaderParam("Authorization") String token
//        ) throws IOException {
//        return readFileContents("products_catalog.json");
//    }
    
    @GET
    @Path("products/page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public String pagedProductsTest(    
            @HeaderParam("Authorization") String token,
            @PathParam("pageNum") int pageNum,
            @QueryParam("filterText") String filterText,
            @QueryParam("filterCategoryId") Integer filterCategoryId
        ) throws IOException {
        if(filterText == null && filterCategoryId == null){
            return readFileContents(String.format("products_catalog_page_%d.json", pageNum));
        }
        else{
            Map<String, Object> retMap = new Gson().fromJson(readFileContents(String.format("products_catalog_page_%d.json", pageNum)), new TypeToken<HashMap<String, Object>>() {}.getType());
            List<Map<String,Object>> productMaps = ((List<Map<String,Object>>)((Map<String,Object>)retMap.get("result")).get("data"));
             List<Map<String,Object>> filtered = productMaps.stream().filter(map -> {
                 if(filterCategoryId != null){
                     if(!((List<Map<String,Object>>)map.get("categories")).stream().anyMatch(catMap -> ((double)catMap.get("id")) == filterCategoryId)) return false;
                 }
                 if(!((String)map.get("title")).contains(filterText))return false;
                 return true;
                }).collect(Collectors.toList());
             ((Map<String,Object>)retMap.get("result")).put("data", filtered);
             return new Gson().toJson(retMap);
        }
    }
    
}


