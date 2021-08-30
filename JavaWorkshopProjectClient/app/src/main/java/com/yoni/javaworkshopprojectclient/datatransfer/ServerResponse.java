package com.yoni.javaworkshopprojectclient.datatransfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerResponse<T> {
    @SerializedName("hasError")
    @Expose
    private boolean hasError;
    @SerializedName("error")
    @Expose
    private ServerResponseError error;
    @SerializedName("result")
    @Expose
    private T result;

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
        return "Response{" +
                "hasError=" + hasError +
                ", error=" + error +
                ", result=" + result +
                '}';
    }

    public static class ServerResponseError {
        public static final int UNKNOWN_ERROR_CODE = 0;

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
