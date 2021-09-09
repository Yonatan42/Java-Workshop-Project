package com.yoni.javaworkshopprojectclient.datatransfer.services;


import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessCallback;

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
        getPagedOrderSummaries(null, pageNum, onSuccess, onError);
    }

    public void getPagedOrderSummaries(Integer userId,
                                       int pageNum,
                                       ResponseSuccessCallback<List<OrderSummary>> onSuccess,
                                       ResponseErrorCallback<List<OrderSummary>> onError){

        if(userId == null){
            userId = DataSets.getInstance().getCurrentUser().getId();
        }
        enqueue(service.getPagedOrderSummaries(getToken(), userId, pageNum), onSuccess, onError);


        // todo - remove this testing code once we are connected to server
        /*
        List<OrderSummary> orders = new ArrayList<>();
        int offset = pageNum * 20;
        for (int i = 1 + offset; i <= 20 + offset; i++ ) {
            orders.add(new OrderSummary(
                    i,
                    1,
                    "fname"+i+" "+"lname"+i,
                    "email"+i+"@mail.mail",
                    String.format("%d%d%d%d%d%d%d%d%d", i, i, i, i, i, i, i, i, i),
                    "the place "+i+"\nsome more place no."+i,
                    4.59f*i,
                    new Date(new Date().getTime()+(i*1000L*60*60*24))));
        }

        onSuccess.onResponseSuccessTokenned(null, null, orders);
     */
    }


    public void getOrderDetails(int id,
                                ResponseSuccessCallback<OrderDetails> onSuccess,
                                ResponseErrorCallback<OrderDetails> onError){
        enqueue(service.getOrderDetails(getToken(), id), onSuccess, onError);

        // todo - remove this once we are connected to server
        /*
        OrderDetails order = new OrderDetails();
        order.setOrderId(orderId);
        order.setUserId(1);
        List<OrderDetailsProduct> products = new ArrayList<>();
        float total = 0;
        for(int i=1; i<=10; i++){
            OrderDetailsProduct product = new OrderDetailsProduct();
            product.setProductId(i);
            product.setPrice(i);
            product.setQuantity(i);
            total += i*i;
            product.setProductTitle("product "+i);
            products.add(product);
        }
        order.setProducts(products);
        order.setTotalPrice(total);
        onSuccess.onResponseSuccessTokenned(null, null, order);

         */
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
        List<Integer> productIds = new ArrayList<>();
        List<Integer> productQuantities = new ArrayList<>();
        for (CartProduct product: cartProducts) {
            productIds.add(product.getProductId());
            productQuantities.add(product.getQuantity());
        }
        enqueue(service.createOrder(getToken(), userId, email, firstName, lastName, phone, address, productIds, productQuantities, creditCard, cardExpiration.getTime(), cardCVV), onSuccess, onError);

        // todo - remove this once we are connected to server
        /*
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(42);
        onSuccess.onResponseSuccessTokenned(null, null, orderDetails);

         */
    }


}
