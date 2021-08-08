package com.yoni.javaworkshopprojectclient.DataTransfer;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yoni.javaworkshopprojectclient.DataTransfer.Models.Customer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CustomersAPIService {

    String URL = "customers/";

    @GET(URL)
    Call<ServerResponse<List<Customer>>> getAllCustomers();

//    @POST(URL+"register3/")
//    Call<Void> register3(@Body Customer customer);

    @POST(URL+"register/")
    @FormUrlEncoded
    Call<ServerResponse<String>> register(@Field("email") String email,
                                          @Field("pass") String pass,
                                          @Field("firstName") String firstName,
                                          @Field("lastName") String lastName,
                                          @Field("phone") String phone,
                                          @Field("address") String address);



}