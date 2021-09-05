package com.yoni.javaworkshopprojectclient.datatransfer.services;


import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsersService extends BaseRemoveService {

    String URL = "users";


    @POST(URL+"/register")
    @FormUrlEncoded
    Call<ServerResponse<LoginResponse>> register(@Field("email") String email,
                                                                 @Field("pass") String pass,
                                                                 @Field("firstName") String firstName,
                                                                 @Field("lastName") String lastName,
                                                                 @Field("phone") String phone,
                                                                 @Field("address") String address);

    @POST(URL+"/login")
    Call<ServerResponse<LoginResponse>> login(@Header("Authorization") String token);

    @POST(URL+"/login-auth")
    @FormUrlEncoded
    Call<ServerResponse<LoginResponse>> login(@Field("email") String email,
                                                              @Field("pass") String pass);


    @PUT(URL+"/{userId}")
    @FormUrlEncoded
    Call<ServerResponse<User>> updateInfo(@Header("Authorization") String token,
                                                           @Path("userId") int userId,
                                                           @Field("email") String email,
                                                           @Field("pass") String pass,
                                                           @Field("firstName") String firstName,
                                                           @Field("lastName") String lastName,
                                                           @Field("phone") String phone,
                                                           @Field("address") String address
    );

    @PUT(URL+"/{userId}/invalidate")
    Call<ServerResponse<Void>> invalidateToken(@Header("Authorization") String token,
                                                               @Path("userId") int userId);
    @POST(URL+"/remote-register")
    @FormUrlEncoded
    Call<ServerResponse<User>> remoteRegister(@Header("Authorization") String token,
                                                                       @Field("email") String email,
                                                                       @Field("pass") String pass,
                                                                       @Field("firstName") String firstName,
                                                                       @Field("lastName") String lastName,
                                                                       @Field("phone") String phone,
                                                                       @Field("address") String address,
                                                                       @Field("isAdmin") boolean isAdmin);

}