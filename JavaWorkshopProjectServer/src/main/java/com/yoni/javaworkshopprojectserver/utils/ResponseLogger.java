package com.yoni.javaworkshopprojectserver.utils;

import javax.ws.rs.core.Response;

public class ResponseLogger {
    private static final String TAG = "ResponseLogger";

    public static Response loggedResponse(Response response){
        return loggedResponse(TAG, response);
    }

    public static Response loggedResponse(String tag, Response response){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Response Status:")
                .append(response.getStatus())
                .append(" ")
                .append(response.getStatusInfo().getReasonPhrase());
        if(response.hasEntity()){
            stringBuilder
                    .append(", Response Entity:")
                    .append('\n')
                    .append(response.getEntity());
        }

        Logger.log(tag, stringBuilder.toString());
        return response;
    }
}
