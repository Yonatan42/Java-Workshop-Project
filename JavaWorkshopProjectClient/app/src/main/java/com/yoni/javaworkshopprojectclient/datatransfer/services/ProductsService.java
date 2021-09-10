package com.yoni.javaworkshopprojectclient.datatransfer.services;


import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.ServerResponse;
import com.yoni.javaworkshopprojectclient.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.models.entitymodels.ProductCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductsService extends BaseRemoveService {

    String URL = "products";

    @GET(URL+"/page/{pageNum}")
    Call<ServerResponse<List<Product>>> getPagedProducts(
            @Header("Authorization") String token,
            @Path("pageNum") int pageNum,
            @Query("filterText") String filterText,
            @Query("filterCategoryId") Integer filterCategoryId
    );

    // when creating a new product we need to create a new stock row as well for it
    @POST(URL)
    @FormUrlEncoded
    Call<ServerResponse<Product>> insertProduct(
            @Header("Authorization") String token,
            @Field("title") String title,
            @Field("description") String desc,
            @Field("imageData") String imageData,
            @Field("categoryIds") List<Integer> categoryIds,
            @Field("price") float price,
            @Field("stockQuantity") int stockQuantity
    );

    @PUT(URL+"/{id}")
    @FormUrlEncoded
    Call<ServerResponse<Product>> updateProduct(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("title") String title,
            @Field("description") String desc,
            @Field("imageData") String imageData,
            @Field("categoryIds") List<Integer> categoryIds,
            @Field("price") float price,
            @Field("stockQuantity") int stockQuantity
    );


    @PUT(URL+"/{id}/enabled")
    @FormUrlEncoded
    Call<ServerResponse<Integer>> setProductEnabled(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("isEnabled") boolean isEnabled
    );

    @GET(URL)
    Call<ServerResponse<List<Product>>> getByIds(
            @Header("Authorization") String token,
            @Query("ids") List<Integer> ids
    );

    @POST(URL+"/categories")
    @FormUrlEncoded
    Call<ServerResponse<ProductCategory>> createCategory(
            @Header("Authorization") String token,
            @Field("title") String title
    );

}