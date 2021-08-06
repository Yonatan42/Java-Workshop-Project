package com.yoni.javaworkshopprojectclient.DataTransfer;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface CustomersAPIService {

    String URL = "com.yoni.javaworkshopprojectserver.customers/";

    @GET(URL)
    Call<List<Customer>> getAllProducts();


    public class Customer {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("pass")
        @Expose
        private byte[] pass;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("created")
        @Expose
        private Date created;
        @SerializedName("modified")
        @Expose
        private Date modified;


        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", pass=" + Arrays.toString(pass) +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    ", created=" + created +
                    ", modified=" + modified +
                    '}';
        }
    }
}