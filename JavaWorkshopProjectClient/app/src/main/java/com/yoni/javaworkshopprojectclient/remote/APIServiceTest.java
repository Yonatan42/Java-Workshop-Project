package com.yoni.javaworkshopprojectclient.remote;

import com.yoni.javaworkshopprojectclient.datatransfer.Post;

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

public interface APIServiceTest {

    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId);

    // for text not using the content header
//    @POST("/posts")
//    @FormUrlEncoded
//    Call<Post> savePost(@Body Post post);


    @PUT("/posts/{id}")
    @FormUrlEncoded
    Call<Post> updatePost(@Path("id") long id,
                          @Field("title") String title,
                          @Field("body") String body,
                          @Field("userId") long userId);

    @DELETE("/posts/{id}")
    Call<Post> deletePost(@Path("id") long id);

    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/posts/{id}")
    Call<Post> getPost(@Path("id") long id);


    // for auth header: @Header("Authorization") String authorization

    @Multipart
    @POST("uploadAttachment")
    Call<Post> uploadAttachment(@Part MultipartBody.Part filePart, @Part("firstname") RequestBody fname, @Part("lastname") RequestBody lname);
    // use like :
    /*

    RequestBody name = RequestBody.create(MediaType.parse("text/plain"), firstNameField.getText().toString());
 // --
    * File file = // initialize file here

MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

Call<MyResponse> call = api.uploadAttachment(filePart);*/
}