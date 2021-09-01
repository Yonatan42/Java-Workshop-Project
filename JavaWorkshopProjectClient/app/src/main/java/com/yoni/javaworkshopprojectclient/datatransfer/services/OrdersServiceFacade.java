package com.yoni.javaworkshopprojectclient.datatransfer.services;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessTokennedCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
        if(userId == null){
            userId = DataSets.getInstance().getCurrentUser().getId();
        }
        enqueueTokenned(service.getPagedOrderSummaries(getToken(), userId, pageNum), onSuccess, onError);
    }

//    public void createOrder(
//            String token,
//            // todo - other params here
//            ResponseSuccessTokennedCallback<OrderDetails> onSuccess,
//            ResponseErrorCallback<TokennedResult<OrderDetails>> onError){
//        enqueueTokenned(service.createOrder(getToken(), userId, pageNum), onSuccess, onError);
//    }

}
