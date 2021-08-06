package com.yoni.javaworkshopprojectclient.Remote;

import com.yoni.javaworkshopprojectclient.Models.Post;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

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