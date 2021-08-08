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

    String URL = "customers/";

    @GET(URL)
    Call<List<Customer>> getAllCustomers();

    @POST(URL+"register3/")
    Call<Void> register3(@Body Customer customer);

    @POST(URL+"register/")
    @FormUrlEncoded
    Call<Void> register(@Field("email") String email,
                        @Field("pass") byte[] pass,
                        @Field("firstName") String firstName,
                        @Field("lastName") String lastName,
                        @Field("phone") String phone,
                        @Field("address") String address);


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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public byte[] getPass() {
            return pass;
        }

        public void setPass(byte[] pass) {
            this.pass = pass;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public Date getModified() {
            return modified;
        }

        public void setModified(Date modified) {
            this.modified = modified;
        }

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