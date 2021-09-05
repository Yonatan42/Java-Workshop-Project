package com.yoni.javaworkshopprojectclient.remote;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ServerCallback<T> implements Callback<ServerResponse<T>>, ResponseSuccessCallback<T>, ResponseErrorCallback<T> {
    @Override
    public final void onResponse(@NonNull Call<ServerResponse<T>> call, Response<ServerResponse<T>> response) {
        if(!response.isSuccessful()) {
            try {
                String errorBodyContent = response.errorBody().string();
                ServerResponse res = RemoteServiceManager.getInstance().getGson().fromJson(errorBodyContent, ServerResponse.class);
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
        String token = res.getToken();
        TokenStore.getInstance().storeToken(token);
        if(res.hasError()){
            onResponseError(call, res.getError());
        }
        else{
            onResponseSuccess(call, response, res.getResult());
        }
    }
    public final void onFailure(@NonNull Call<ServerResponse<T>> call, Throwable t){
        onResponseError(call, new ServerResponse.ServerResponseError(t.getMessage(), ServerResponse.ServerResponseError.UNKNOWN_ERROR_CODE));
    }
}
