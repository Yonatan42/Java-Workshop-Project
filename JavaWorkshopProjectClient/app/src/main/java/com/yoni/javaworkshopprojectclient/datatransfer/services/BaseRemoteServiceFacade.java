package com.yoni.javaworkshopprojectclient.datatransfer.services;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessCallback;
import com.yoni.javaworkshopprojectclient.remote.ServerCallback;

import retrofit2.Call;
import retrofit2.Response;

public abstract class BaseRemoteServiceFacade<T extends BaseRemoveService> {

    protected T service;

    public BaseRemoteServiceFacade(T service){
        this.service = service;
    }

    protected <U> void enqueue(Call<ServerResponse<U>> call, ResponseSuccessCallback<U> onSuccess, ResponseErrorCallback<U> onError){
        call.enqueue(new ServerCallback<U>() {
            @Override
            public void onResponseSuccess(@NonNull Call<ServerResponse<U>> call, Response<ServerResponse<U>> response, U result) {
                if(onSuccess != null){
                    onSuccess.onResponseSuccess(call, response, result);
                }
            }
            @Override
            public void onResponseError(@NonNull Call<ServerResponse<U>> call, ServerResponse.ServerResponseError responseError) {
                if(onError != null){
                    onError.onResponseError(call, responseError);
                }
            }
        });
    }

    protected String getToken(){
        return TokenStore.getInstance().getToken();
    }

}
