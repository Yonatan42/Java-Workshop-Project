package com.yoni.javaworkshopprojectclient.datatransfer.infrastructure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerResponse<T> {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("hasError")
    @Expose
    private boolean hasError;
    @SerializedName("error")
    @Expose
    private ServerResponseError error;
    @SerializedName("result")
    @Expose
    private T result;

    public String getToken() {
        return token;
    }

    public boolean hasError() {
        return hasError;
    }

    public ServerResponseError getError() {
        return error;
    }

    public T getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "token='" + token + '\'' +
                ", hasError=" + hasError +
                ", error=" + error +
                ", result=" + result +
                '}';
    }

    public static class ServerResponseError {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("code")
        @Expose
        private int code;

        public ServerResponseError(){}

        public ServerResponseError(String message, int code){
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return "ResponseError{" +
                    "message='" + message + '\'' +
                    ", code=" + code +
                    '}';
        }
    }
}
