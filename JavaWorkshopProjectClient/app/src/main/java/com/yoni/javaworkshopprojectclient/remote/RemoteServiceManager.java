package com.yoni.javaworkshopprojectclient.remote;

import com.google.gson.Gson;
import com.yoni.javaworkshopprojectclient.datatransfer.services.OrdersService;
import com.yoni.javaworkshopprojectclient.datatransfer.services.OrdersServiceFacade;
import com.yoni.javaworkshopprojectclient.datatransfer.services.ProductsServiceFacade;
import com.yoni.javaworkshopprojectclient.datatransfer.services.UsersService;
import com.yoni.javaworkshopprojectclient.datatransfer.services.ProductsService;
import com.yoni.javaworkshopprojectclient.datatransfer.services.UsersServiceFacade;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RemoteServiceManager {

    private static RemoteServiceManager instance;

    public static RemoteServiceManager getInstance(){
        if(instance == null){
            synchronized (RemoteServiceManager.class) {
                if(instance == null) {
                    instance = new RemoteServiceManager();
                }
            }
        }
        return instance;
    }

    private RemoteServiceManager() {}


//    public static final String BASE_URL = "http://10.0.2.2:8080/JavaWorkshopProjectServer/resources/";
    public static final String BASE_URL = "http://10.0.2.2:8080/JavaWorkshopProjectServer/resources/testing/";

    public final Gson gson = new Gson();
    private UsersServiceFacade usersServiceFacade;
    private ProductsServiceFacade productsServiceFacade;
    private OrdersServiceFacade ordersServiceFacade;



    // todo - to remove
//    public ProductsRemoteService getProductsAPIService() {
//        return RetrofitClient.getClient(BASE_URL).create(ProductsRemoteService.class);
//    }

    public UsersServiceFacade getUsersService() {
        if(usersServiceFacade == null){
            synchronized (this){
                if(usersServiceFacade == null) {
                    usersServiceFacade = new UsersServiceFacade(RetrofitClient.getClient(BASE_URL).create(UsersService.class));
                }
            }
        }
        return usersServiceFacade;
    }

    public ProductsServiceFacade getProductsService() {
        if(productsServiceFacade == null){
            synchronized (this){
                if(productsServiceFacade == null) {
                    productsServiceFacade = new ProductsServiceFacade(RetrofitClient.getClient(BASE_URL).create(ProductsService.class));
                }
            }
        }
        return productsServiceFacade;
    }

    public OrdersServiceFacade getOrdersService() {
        if(ordersServiceFacade == null){
            synchronized (this){
                if(ordersServiceFacade == null) {
                    ordersServiceFacade = new OrdersServiceFacade(RetrofitClient.getClient(BASE_URL).create(OrdersService.class));
                }
            }
        }
        return ordersServiceFacade;
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