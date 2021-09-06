package com.yoni.javaworkshopprojectclient.datatransfer.services;


import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrdersService extends BaseRemoveService {

    String URL = "orders";

    @GET(URL+"/{userId}/summaries/page/{pageNum}")
    Call<ServerResponse<List<OrderSummary>>> getPagedOrderSummaries(
            @Header("Authorization") String token,
            @Path("userId") int userId,
            @Path("pageNum") int pageNum
    );

    @GET(URL+"/details/{id}")
    Call<ServerResponse<OrderDetails>> getOrderDetails(
            @Header("Authorization") String token,
            @Path("id") int orderId
    );



    @POST(URL)
    @FormUrlEncoded
    Call<ServerResponse<OrderDetails>> createOrder(
            @Header("Authorization") String token,
            @Field("userId") int userId,
            @Field("email") String email,
            @Field("firstName") String fname,
            @Field("lastName") String lname,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("creditCard") String creditCard,
            @Field("cardExpiration") Date cardExpiration,
            @Field("cardCVV") String cardCVV
    );

}