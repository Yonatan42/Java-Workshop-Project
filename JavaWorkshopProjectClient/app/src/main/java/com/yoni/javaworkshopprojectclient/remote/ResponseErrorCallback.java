package com.yoni.javaworkshopprojectclient.remote;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;

import retrofit2.Call;
import retrofit2.Response;

public interface ResponseErrorCallback<T> {
    void onResponseError(@NonNull Call<ServerResponse<T>> call, ServerResponse.ServerResponseError responseError);
}
