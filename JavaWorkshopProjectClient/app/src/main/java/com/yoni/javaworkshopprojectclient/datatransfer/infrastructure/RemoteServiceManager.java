package com.yoni.javaworkshopprojectclient.datatransfer.infrastructure;

import com.google.gson.Gson;
import com.yoni.javaworkshopprojectclient.datatransfer.services.OrdersService;
import com.yoni.javaworkshopprojectclient.datatransfer.services.OrdersServiceFacade;
import com.yoni.javaworkshopprojectclient.datatransfer.services.ProductsServiceFacade;
import com.yoni.javaworkshopprojectclient.datatransfer.services.UsersService;
import com.yoni.javaworkshopprojectclient.datatransfer.services.ProductsService;
import com.yoni.javaworkshopprojectclient.datatransfer.services.UsersServiceFacade;

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

    public final Gson gson = new Gson();
    private UsersServiceFacade usersServiceFacade;
    private ProductsServiceFacade productsServiceFacade;
    private OrdersServiceFacade ordersServiceFacade;


    public UsersServiceFacade getUsersService() {
        if(usersServiceFacade == null){
            synchronized (this){
                if(usersServiceFacade == null) {
                    usersServiceFacade = new UsersServiceFacade(RetrofitClient.getClient().create(UsersService.class));
                }
            }
        }
        return usersServiceFacade;
    }

    public ProductsServiceFacade getProductsService() {
        if(productsServiceFacade == null){
            synchronized (this){
                if(productsServiceFacade == null) {
                    productsServiceFacade = new ProductsServiceFacade(RetrofitClient.getClient().create(ProductsService.class));
                }
            }
        }
        return productsServiceFacade;
    }

    public OrdersServiceFacade getOrdersService() {
        if(ordersServiceFacade == null){
            synchronized (this){
                if(ordersServiceFacade == null) {
                    ordersServiceFacade = new OrdersServiceFacade(RetrofitClient.getClient().create(OrdersService.class));
                }
            }
        }
        return ordersServiceFacade;
    }

    public Gson getGson(){
        return gson;
    }

}