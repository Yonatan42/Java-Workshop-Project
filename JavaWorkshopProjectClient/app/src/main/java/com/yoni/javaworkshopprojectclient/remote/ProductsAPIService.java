// todo - remove later

package com.yoni.javaworkshopprojectclient.remote;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
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

public interface ProductsAPIService {

    String URL = "products/";

    @GET(URL)
    Call<MultiProductResponse> getAllProducts();

    @GET(URL+"/search/{text}")
    Call<MultiProductResponse> searchByName(@Path("text") String text);

    @POST(URL)
    @FormUrlEncoded
    Call<SaveResponse> saveProduct(@Header("Authorization") String token,
                        @Field("name") String name,
                        @Field("price") double price);

    @POST(URL)
    @Multipart
    Call<SaveResponse> saveProduct(@Header("Authorization") String token,
                                   @Part("name") RequestBody name,
                                   @Part("price") RequestBody price,
                                   @Part MultipartBody.Part imageFile);

    @PATCH(URL+"{id}")
    Call<UpdateResponse> updateProduct(@Header("Authorization") String token,
                                     @Path("id") String id,
                                     @Body String updates);



    class Product{
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private Double price;
        @SerializedName("productId")
        @Expose
        private String productId;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;


        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", productId='" + productId + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    '}';
        }
    }

    class MultiProductResponse {
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("products")
        @Expose
        private List<Product> products;

        public Integer getCount() {
            return count;
        }

        public List<Product> getProducts() {
            return products;
        }
    }

    class SaveResponse {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("product")
        @Expose
        private Product product;
    }

    class UpdateResponse{
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("updatedProductId")
        @Expose
        private String updatedProductId;
    }

    class UpdateRequestBody {

        private List<Param> params;

        public UpdateRequestBody(Param... params){
            this.params = new ArrayList<>();
            this.params.addAll(Arrays.asList(params));
        }

        @Override
        public String toString() {
            StringBuilder st = new StringBuilder();
            st.append('[');
            for(Param param: this.params){
                st.append(param.toString());
                st.append(',');
            }
            st.setCharAt(st.length()-1, ']');
            return st.toString();
        }

        public static class Param {
            public Param(String propName, String value) {
                this.propName = propName;
                this.value = value;
            }

            private String propName;
            private String value;

            @Override
            public String toString() {
                return "{\'propName\' :\'" + propName + "\'," +
                        "\'value\': \'" + value + "\'}";
            }
        }
    }

}