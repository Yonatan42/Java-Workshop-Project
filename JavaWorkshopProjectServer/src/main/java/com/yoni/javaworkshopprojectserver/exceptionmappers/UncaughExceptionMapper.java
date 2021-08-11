/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.exceptionmappers;

import com.yoni.javaworkshopprojectserver.utils.JsonUtil;
import com.yoni.javaworkshopprojectserver.utils.ErrorCodes;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Yoni
 */
@Provider
public class UncaughExceptionMapper implements ExceptionMapper<Throwable> {

     // if we reached here, then the fallback didn't catch the exception, in other words, there was an attempt to access a page that doesn't exist. 
    
    @Override
    public Response toResponse(Throwable t) {
        t.printStackTrace(System.err);
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(JsonUtil.createResponseJson("page not found", ErrorCodes.UNKNOWN))
                .build();
    }
    
}
