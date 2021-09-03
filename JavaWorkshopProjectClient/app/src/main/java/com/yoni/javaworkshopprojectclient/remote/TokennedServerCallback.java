package com.yoni.javaworkshopprojectclient.remote;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class TokennedServerCallback<T> extends ServerCallback<TokennedResult<T>> implements ResponseSuccessTokennedCallback<T> {

    @Override
    public void onResponseSuccess(@NonNull Call<ServerResponse<TokennedResult<T>>> call, Response<ServerResponse<TokennedResult<T>>> response, TokennedResult<T> result) {
        String token = result.getToken();
        TokenStore.getInstance().storeToken(token);
        onResponseSuccessTokenned(call, response, result.getData());
    }
}