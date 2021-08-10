package com.yoni.javaworkshopprojectclient.remote;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ServerCallback<T> implements Callback<ServerResponse<T>> {
    @Override
    public void onResponse(Call<ServerResponse<T>> call, Response<ServerResponse<T>> response) {
        if(!response.isSuccessful()) {
            try {
                String errorBodyContent = response.errorBody().string();
                ServerResponse res = ApiUtils.GSON.fromJson(errorBodyContent, ServerResponse.class);
                onResponseError(call, res.getError());
                return;
            }
            catch (Exception e) {
                try {
                    onFailure(call, new Exception("server error with status "+response.code()+" occurred,'\nresponse:"+response.errorBody().string()));
                } catch (IOException ioException) {
                    onFailure(call, new Exception("server error with status "+response.code()+" occurred"));
                }
                return;
            }
        }

        ServerResponse<T> res = response.body();
        if(res.hasError()){
            onResponseError(call, res.getError());
        }
        else{
            onResponseSuccess(call, response, res.getResult());
        }
    }

    public abstract void onResponseSuccess(Call<ServerResponse<T>> call, Response<ServerResponse<T>> response, T result);
    public abstract void onResponseError(Call<ServerResponse<T>> call, ServerResponse.ServerResponseError responseError);
    @Override
    public abstract void onFailure(Call<ServerResponse<T>> call, Throwable t);
}
