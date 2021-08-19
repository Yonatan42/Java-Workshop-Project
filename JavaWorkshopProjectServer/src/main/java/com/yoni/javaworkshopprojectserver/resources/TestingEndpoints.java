/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    
    
    @GET
    @Path("users/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginTest() throws IOException {
        return readFileContents("login.json");
    }
    
    @GET
    @Path("current-dir")
    @Produces(MediaType.TEXT_PLAIN)
    public String currentDir() {
        return System.getProperty("user.dir");
    }
    
}


