package com.yoni.javaworkshopprojectserver.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("oing")
public class PingTestResource {
    
    @GET
    public Response ping(){
        return Response
                .ok("pong")
                .build();
    }
}
