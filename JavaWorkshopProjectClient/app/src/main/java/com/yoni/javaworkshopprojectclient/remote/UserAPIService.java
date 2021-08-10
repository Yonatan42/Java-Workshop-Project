package com.yoni.javaworkshopprojectclient.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserAPIService{

    String URL = "user/";

    @POST(URL+"signup")
    @FormUrlEncoded
    Call<SignupResponse> signup(@Field("email") String email,
                            @Field("password") String password);

    @POST(URL+"login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("email") String email,
                            @Field("password") String password);

    class SignupResponse{
        @SerializedName("message")
        @Expose
        private String message;
    }

    class LoginResponse{
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("userId")
        @Expose
        private String userId;


    }

}