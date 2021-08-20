package com.yoni.javaworkshopprojectclient.remote;

import com.google.gson.Gson;
import com.yoni.javaworkshopprojectclient.datatransfer.services.UsersService;
import com.yoni.javaworkshopprojectclient.datatransfer.services.ProductsService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RemoteService {

    private static RemoteService instance;

    public static RemoteService getInstance(){
        if(instance == null){
            synchronized (RemoteService.class) {
                if(instance == null) {
                    instance = new RemoteService();
                }
            }
        }
        return instance;
    }

    private RemoteService() {}


//    public static final String BASE_URL = "http://10.0.2.2:8080/JavaWorkshopProjectServer/resources/";
    public static final String BASE_URL = "http://10.0.2.2:8080/JavaWorkshopProjectServer/resources/testing/";

    public final Gson gson = new Gson();
    public UsersService usersService;
    public ProductsService productsService;



    // todo - to remove
//    public ProductsRemoteService getProductsAPIService() {
//        return RetrofitClient.getClient(BASE_URL).create(ProductsRemoteService.class);
//    }

    public UsersService getUsersService() {
        if(usersService == null){
            synchronized (this){
                if(usersService == null) {
                    usersService = RetrofitClient.getClient(BASE_URL).create(UsersService.class);
                }
            }
        }
        return usersService;
    }

    public ProductsService getProductsService() {
        if(productsService == null){
            synchronized (this){
                if(productsService == null) {
                    productsService = RetrofitClient.getClient(BASE_URL).create(ProductsService.class);
                }
            }
        }
        return productsService;
    }

    public Gson getGson(){
        return gson;
    }

    public static RequestBody makeTextPart(String text) {
        return RequestBody.create(MediaType.parse("text/plain"), text);
    }

    public static MultipartBody.Part makeFilePart(File file, String field, String mime) {
        return MultipartBody.Part.createFormData(field, file.getName()+"."+mime.substring(mime.indexOf('/')+1), RequestBody.create(MediaType.parse(mime), file));
    }


}