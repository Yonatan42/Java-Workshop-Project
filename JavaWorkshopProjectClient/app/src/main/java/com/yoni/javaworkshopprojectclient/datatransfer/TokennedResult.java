package com.yoni.javaworkshopprojectclient.datatransfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokennedResult<T> {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("data")
    @Expose
    private T data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TokennedResponse{" +
                "token='" + token + '\'' +
                ", data=" + data +
                '}';
    }
}
