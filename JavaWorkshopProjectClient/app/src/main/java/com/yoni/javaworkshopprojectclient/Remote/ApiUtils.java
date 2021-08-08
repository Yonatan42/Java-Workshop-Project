package com.yoni.javaworkshopprojectclient.Remote;

import com.google.gson.Gson;
import com.yoni.javaworkshopprojectclient.DataTransfer.CustomersAPIService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ApiUtils {

    private ApiUtils() {
    }

//    public static final String BASE_URL = "https://yoni-rest-api-test.herokuapp.com/";
    public static final String BASE_URL = "http://10.0.2.2:8080/JavaWorkshopProjectServer/resources/";

    public static final Gson GSON = new Gson();


    // todo - to remove
    public static UserAPIService getUserAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(UserAPIService.class);
    }

    // todo - to remove
    public static ProductsAPIService getProductsAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ProductsAPIService.class);
    }

    public static CustomersAPIService getCustomersAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(CustomersAPIService.class);
    }

    public static RequestBody makeTextPart(String text) {
        return RequestBody.create(MediaType.parse("text/plain"), text);
    }

    public static MultipartBody.Part makeFilePart(File file, String field, String mime) {
        return MultipartBody.Part.createFormData(field, file.getName()+"."+mime.substring(mime.indexOf('/')+1), RequestBody.create(MediaType.parse(mime), file));
    }


}