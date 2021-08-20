package com.yoni.javaworkshopprojectclient.datatransfer.services;


import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductsService {

    String URL = "products/";

    @GET(URL)
    Call<ServerResponse<TokennedResult<List<Product>>>> getAllProducts(
            @Header("Authorization") String token
    );

    @GET(URL+"filter/")
    @FormUrlEncoded
    Call<ServerResponse<TokennedResult<List<Product>>>> getFilteredProducts(
            @Header("Authorization") String token,
            @Field("categoryId") int categoryId,
            @Field("text") String text
    );



}