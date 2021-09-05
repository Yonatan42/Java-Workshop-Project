/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.exceptionmappers;

import com.yoni.javaworkshopprojectserver.utils.JsonUtils;
import com.yoni.javaworkshopprojectserver.utils.ErrorCodes;
import com.yoni.javaworkshopprojectserver.utils.Logger;
import com.yoni.javaworkshopprojectserver.utils.ResponseLogger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Yoni
 */
@Provider
public class UncaughtExceptionMapper implements ExceptionMapper<Throwable> {

    private static final String TAG = "UncaughtExceptionMapper";

     // if we reached here, then the fallback didn't catch the exception,
    // in other words, there was an attempt to access a page that doesn't exist
    // or an unexpected error occurred while checking authentication
    
    @Override
    public Response toResponse(Throwable t) {
        Logger.logError(TAG, t);
        return ResponseLogger.loggedResponse(Response
                .status(Response.Status.NOT_FOUND)
                .entity(JsonUtils.createResponseJson("page not found", ErrorCodes.UNKNOWN))
                .build());
    }
    
}
