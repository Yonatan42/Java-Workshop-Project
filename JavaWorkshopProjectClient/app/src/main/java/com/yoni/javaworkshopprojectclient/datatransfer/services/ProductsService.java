package com.yoni.javaworkshopprojectclient.datatransfer.services;


import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductsService extends BaseRemoveService {

    String URL = "products";

//    @GET(URL)
//    Call<ServerResponse<TokennedResult<List<Product>>>> getAllProducts(
//            @Header("Authorization") String token
//    );

    @GET(URL+"/page/{pageNum}")
    Call<ServerResponse<TokennedResult<List<Product>>>> getPagedProducts(
            @Header("Authorization") String token,
            @Path("pageNum") int pageNum,
            @Query("filterText") String filterText,
            @Query("filterCategoryId") Integer filterCategoryId
    );

    // when creating a new product we need to create a new stock row as well for it
    @POST(URL)
    @FormUrlEncoded
    Call<ServerResponse<TokennedResult<Product>>> insertProduct(
            @Header("Authorization") String token,
            @Field("title") String title,
            @Field("description") String desc,
            @Field("imageData") String imageData,
            @Field("categories") List<ProductCategory> categories,
            @Field("price") float price,
            @Field("stockQuantity") int stockQuantity
    );

    @PUT(URL+"/{productId}")
    @FormUrlEncoded
    Call<ServerResponse<TokennedResult<Product>>> updateProduct(
            @Header("Authorization") String token,
            @Path("productId") int productId,
            @Field("product") Product product
    );


    @PATCH(URL+"/{productId}/enabled")
    @FormUrlEncoded
    Call<ServerResponse<TokennedResult<Integer>>> setProductEnabled(
            @Header("Authorization") String token,
            @Path("productId") int productId,
            @Field("isEnabled") boolean isEnabled
    );


    // example of sql syntax for get all entities that are in a group of ids:
    // ->  SELECT * FROM java_workshop_db.products WHERE id IN (1,2,3,4);
    // for the cart page

    @GET(URL)
    Call<ServerResponse<TokennedResult<List<Product>>>> getProductsByIds(
            @Header("Authorization") String token,
            @Query("productIds") List<Integer> productIds
    );

    /*

    @GET(URL)
    Call<ServerResponse<TokennedResult<List<Product>>>> getProductsByIds(
            @Header("Authorization") String token,
            @Query(encoded=true, value="productIds") String encodedProductIdsString
    );

     */

    @POST(URL+"/categories")
    @FormUrlEncoded
    Call<ServerResponse<TokennedResult<ProductCategory>>> createCategory(
            @Header("Authorization") String token,
            @Field("title") String title
    );

}