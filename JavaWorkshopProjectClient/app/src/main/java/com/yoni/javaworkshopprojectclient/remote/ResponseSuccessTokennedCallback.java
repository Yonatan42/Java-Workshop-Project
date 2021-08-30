package com.yoni.javaworkshopprojectclient.remote;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;

import retrofit2.Call;
import retrofit2.Response;

public interface ResponseSuccessTokennedCallback<T> {
    void onResponseSuccessTokenned(@NonNull Call<ServerResponse<TokennedResult<T>>> call, Response<ServerResponse<TokennedResult<T>>> response, T result);
}
