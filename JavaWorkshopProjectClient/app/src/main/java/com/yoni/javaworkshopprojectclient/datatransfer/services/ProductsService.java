package com.yoni.javaworkshopprojectclient.datatransfer.services;


import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductsService {

    String URL = "products/";

//    @GET(URL)
//    Call<ServerResponse<TokennedResult<List<Product>>>> getAllProducts(
//            @Header("Authorization") String token
//    );

    // example of sql syntax for get all entities that are in a group of ids:
    // ->  SELECT * FROM java_workshop_db.products WHERE id IN (1,2,3,4);
    // for the cart page

    @GET(URL+"page/{pageNum}")
    Call<ServerResponse<TokennedResult<List<Product>>>> getPagedProducts(
            @Header("Authorization") String token,
            @Path("pageNum") int pageNum
    );

    @GET(URL+"filter/")
    @FormUrlEncoded
    Call<ServerResponse<TokennedResult<List<Product>>>> getFilteredProducts(
            @Header("Authorization") String token,
            @Field("categoryId") int categoryId,
            @Field("text") String text
    );



}