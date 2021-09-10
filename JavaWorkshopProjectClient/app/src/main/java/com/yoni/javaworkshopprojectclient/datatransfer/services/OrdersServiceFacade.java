package com.yoni.javaworkshopprojectclient.datatransfer.services;


import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessCallback;
import com.yoni.javaworkshopprojectclient.ui.popups.Loader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersServiceFacade extends BaseRemoteServiceFacade<OrdersService> {

    public OrdersServiceFacade(OrdersService service) {
        super(service);
    }

    public void getPagedOrderSummaries(int pageNum,
                                       ResponseSuccessCallback<List<OrderSummary>> onSuccess,
                                       ResponseErrorCallback<List<OrderSummary>> onError){
        getPagedOrderSummaries(pageNum, onSuccess, onError, null);
    }

    public void getPagedOrderSummaries(int pageNum,
                                       ResponseSuccessCallback<List<OrderSummary>> onSuccess,
                                       ResponseErrorCallback<List<OrderSummary>> onError,
                                       Loader loader){
        getPagedOrderSummaries(null, pageNum, onSuccess, onError, loader);
    }

    public void getPagedOrderSummaries(Integer userId,
                                       int pageNum,
                                       ResponseSuccessCallback<List<OrderSummary>> onSuccess,
                                       ResponseErrorCallback<List<OrderSummary>> onError){
        getPagedOrderSummaries(userId, pageNum, onSuccess, onError, null);
    }

    public void getPagedOrderSummaries(Integer userId,
                                       int pageNum,
                                       ResponseSuccessCallback<List<OrderSummary>> onSuccess,
                                       ResponseErrorCallback<List<OrderSummary>> onError,
                                       Loader loader){

        if(userId == null){
            userId = DataSets.getInstance().getCurrentUser().getId();
        }
        enqueue(service.getPagedOrderSummaries(getToken(), userId, pageNum), onSuccess, onError, loader);
    }

    public void getOrderDetails(int id,
                                ResponseSuccessCallback<OrderDetails> onSuccess,
                                ResponseErrorCallback<OrderDetails> onError) {
        getOrderDetails(id, onSuccess, onError, null);
    }
    public void getOrderDetails(int id,
                                ResponseSuccessCallback<OrderDetails> onSuccess,
                                ResponseErrorCallback<OrderDetails> onError,
                                Loader loader) {
        enqueue(service.getOrderDetails(getToken(), id), onSuccess, onError, loader);
    }

    public void createOrder(
            int userId,
            String email,
            String firstName,
            String lastName,
            String phone,
            String address,
            List<CartProduct> cartProducts,
            String creditCard,
            Date cardExpiration,
            String cardCVV,
            ResponseSuccessCallback<Integer> onSuccess,
            ResponseErrorCallback<Integer> onError){
        createOrder(userId, email, firstName, lastName, phone, address, cartProducts, creditCard, cardExpiration, cardCVV, onSuccess, onError, null);
    }
    public void createOrder(
            int userId,
            String email,
            String firstName,
            String lastName,
            String phone,
            String address,
            List<CartProduct> cartProducts,
            String creditCard,
            Date cardExpiration,
            String cardCVV,
            ResponseSuccessCallback<Integer> onSuccess,
            ResponseErrorCallback<Integer> onError,
            Loader loader){
        List<Integer> productIds = new ArrayList<>();
        List<Integer> productQuantities = new ArrayList<>();
        for (CartProduct product: cartProducts) {
            productIds.add(product.getProductId());
            productQuantities.add(product.getQuantity());
        }
        enqueue(service.createOrder(getToken(), userId, email, firstName, lastName, phone, address, productIds, productQuantities, creditCard, cardExpiration.getTime(), cardCVV), onSuccess, onError, loader);
    }


}
