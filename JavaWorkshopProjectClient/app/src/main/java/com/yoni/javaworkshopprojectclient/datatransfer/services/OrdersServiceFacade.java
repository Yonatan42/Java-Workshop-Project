package com.yoni.javaworkshopprojectclient.datatransfer.services;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.datatransfer.models.uimodels.ExpandableOrder;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessTokennedCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersServiceFacade extends BaseRemoteServiceFacade<OrdersService> {

    public OrdersServiceFacade(OrdersService service) {
        super(service);
    }

    public void getPagedOrderSummaries(int pageNum,
                                       ResponseSuccessTokennedCallback<List<OrderSummary>> onSuccess,
                                       ResponseErrorCallback<TokennedResult<List<OrderSummary>>> onError){
        getPagedOrderSummaries(null, pageNum, onSuccess, onError);
    }

    public void getPagedOrderSummaries(Integer userId,
                                       int pageNum,
                                       ResponseSuccessTokennedCallback<List<OrderSummary>> onSuccess,
                                       ResponseErrorCallback<TokennedResult<List<OrderSummary>>> onError){
        /* // todo - uncomment this
        if(userId == null){
            userId = DataSets.getInstance().getCurrentUser().getId();
        }
        enqueueTokenned(service.getPagedOrderSummaries(getToken(), userId, pageNum), onSuccess, onError);
         */

        // todo - remove this testing code once we are connected to server
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
    }

//    public void createOrder(
//            String token,
//            // todo - other params here
//            ResponseSuccessTokennedCallback<OrderDetails> onSuccess,
//            ResponseErrorCallback<TokennedResult<OrderDetails>> onError){
//        enqueueTokenned(service.createOrder(getToken(), userId, pageNum), onSuccess, onError);
//    }

}
