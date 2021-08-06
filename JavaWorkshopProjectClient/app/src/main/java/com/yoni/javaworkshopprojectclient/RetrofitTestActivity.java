package com.yoni.javaworkshopprojectclient;


import android.content.ContentResolver;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yoni.javaworkshopprojectclient.DataTransfer.CustomersAPIService;
import com.yoni.javaworkshopprojectclient.Remote.ApiUtils;
import com.yoni.javaworkshopprojectclient.Remote.ProductsAPIService;
import com.yoni.javaworkshopprojectclient.Remote.UserAPIService;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitTestActivity extends AppCompatActivity {

    private static final String TAG = "RetrofitTest";

    private static final String TEST_EMAIL = "textRRRRRR@text.text";
    private static final String TEST_PASS = "thepassssss";
    private static final String TEST_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRleHRSUlJSUlJAdGV4dC50ZXh0IiwidXNlcklkIjoiNWM5MjNmNTA0YjMyYmYwMDE2NWQ3MDQ5IiwiaWF0IjoxNTUzMDkzNjA4LCJleHAiOjE1NTMwOTcyMDh9.x8KH0Tvecjq_GeK5FdsuAolzQAmvq-SSpIFnrbxCgp8";
    private static final String TEST_PRODUCT = "5c924e4894f5b40016f8e504";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_tester);

        CustomersAPIService productsAPIService = ApiUtils.getCustomersAPIService();
        productsAPIService.getAllProducts().enqueue(new Callback<List<CustomersAPIService.Customer>>() {

            @Override
            public void onResponse(Call<List<CustomersAPIService.Customer>> call, Response<List<CustomersAPIService.Customer>> response) {
                Log.i("RetrofitTester", "Got "+response.body().size()+" Products: \n"+response.body());
            }

            @Override
            public void onFailure(Call<List<CustomersAPIService.Customer>> call, Throwable t) {
                Log.e("RetrofitTester", "Got error", t);
            }
        });









//        getDrawableFile();

//        UserAPIService userAPIService = ApiUtils.getUserAPIService();
//        userAPIService.signup("textRRRRRdfhdgR@text.text","thepassssss").enqueue(new Callback<UserAPIService.SignupResponse>() {
//            @Override
//            public void onResponse(Call<UserAPIService.SignupResponse> call, Response<UserAPIService.SignupResponse> response) {
//                int x = 3;
//            }
//
//            @Override
//            public void onFailure(Call<UserAPIService.SignupResponse> call, Throwable t) {
//                int x = 3;
//            }
//        });

//        userAPIService.login(TEST_EMAIL, TEST_PASS).enqueue(new Callback<UserAPIService.LoginResponse>() {
//            @Override
//            public void onResponse(Call<UserAPIService.LoginResponse> call, Response<UserAPIService.LoginResponse> response) {
//                int x = 3;
//            }
//
//            @Override
//            public void onFailure(Call<UserAPIService.LoginResponse> call, Throwable t) {
//                int x = 3;
//            }
//        });


//        ProductsAPIService productsAPIService = ApiUtils.getProductsAPIService();
//        productsAPIService.getAllProducts().enqueue(new Callback<ProductsAPIService.MultiProductResponse>() {
//        productsAPIService.searchByName("a").enqueue(new Callback<ProductsAPIService.MultiProductResponse>() {
//            @Override
//            public void onResponse(Call<ProductsAPIService.MultiProductResponse> call, Response<ProductsAPIService.MultiProductResponse> response) {
//                int x = 3;
//                Log.i("RetrofitTester", "Got "+response.body().getCount()+" Products: \n"+response.body().getProducts());
//            }
//
//            @Override
//            public void onFailure(Call<ProductsAPIService.MultiProductResponse> call, Throwable t) {
//                int x = 3;
//            }
//        });
//        productsAPIService.saveProduct(TEST_TOKEN, "socket",30.64).enqueue(new Callback<ProductsAPIService.SaveResponse>() {
//            @Override
//            public void onResponse(Call<ProductsAPIService.SaveResponse> call, Response<ProductsAPIService.SaveResponse> response) {
//                int x = 3;
//            }
//
//            @Override
//            public void onFailure(Call<ProductsAPIService.SaveResponse> call, Throwable t) {
//                int x = 3;
//            }
//        });
//            productsAPIService.saveProduct(TEST_TOKEN, ApiUtils.makeTextPart("heart"),ApiUtils.makeTextPart("0"), ApiUtils.makeFilePart(getDrawableFile(), "productImage","image/png")).enqueue(new Callback<ProductsAPIService.SaveResponse>() {
//                @Override
//                public void onResponse(Call<ProductsAPIService.SaveResponse> call, Response<ProductsAPIService.SaveResponse> response) {
//                    int x = 3;
//                }
//
//                @Override
//                public void onFailure(Call<ProductsAPIService.SaveResponse> call, Throwable t) {
//                    int x = 3;
//                }
//            });

//        productsAPIService.updateProduct(TEST_TOKEN, TEST_PRODUCT,"[{'propName':'price', 'value': 90.90}]").enqueue(new Callback<ProductsAPIService.UpdateResponse>() {
//            @Override
//            public void onResponse(Call<ProductsAPIService.UpdateResponse> call, Response<ProductsAPIService.UpdateResponse> response) {
//                int x = 3;
//            }
//
//            @Override
//            public void onFailure(Call<ProductsAPIService.UpdateResponse> call, Throwable t) {
//                int x = 3;
//            }
//        });

    }


    public File getDrawableFile() {
        Uri uri = (new Uri.Builder())
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(R.mipmap.ic_launcher))
                .appendPath(getResources().getResourceTypeName(R.mipmap.ic_launcher))
                .appendPath(getResources().getResourceEntryName(R.mipmap.ic_launcher))
                .build();
        try {
            return FileUtil.from(RetrofitTestActivity.this, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
