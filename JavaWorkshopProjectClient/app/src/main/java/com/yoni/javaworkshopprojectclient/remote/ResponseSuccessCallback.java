package com.yoni.javaworkshopprojectclient.remote;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;

import retrofit2.Call;
import retrofit2.Response;

public interface ResponseSuccessCallback<T> {
    void onResponseSuccess(@NonNull Call<ServerResponse<T>> call, Response<ServerResponse<T>> response, T result);
}
