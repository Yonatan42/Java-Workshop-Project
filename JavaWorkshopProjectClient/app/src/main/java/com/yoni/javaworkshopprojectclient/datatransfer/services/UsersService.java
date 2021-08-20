package com.yoni.javaworkshopprojectclient.datatransfer.services;


import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UsersService {

    String URL = "users/";

    @GET(URL)
    Call<ServerResponse<List<User>>> getAllCustomers();

//    @POST(URL+"register3/")
//    Call<Void> register3(@Body Customer customer);

    @POST(URL+"register/")
    @FormUrlEncoded
    Call<ServerResponse<TokennedResult<User>>> register(@Field("email") String email,
                                                        @Field("pass") String pass,
                                                        @Field("firstName") String firstName,
                                                        @Field("lastName") String lastName,
                                                        @Field("phone") String phone,
                                                        @Field("address") String address);

    @POST(URL+"login/")
    Call<ServerResponse<TokennedResult<User>>> login(@Header("Authorization") String token);

    @POST(URL+"login-auth/")
    @FormUrlEncoded
    Call<ServerResponse<TokennedResult<User>>> login(@Field("email") String email,
                                                     @Field("pass") String pass);



}