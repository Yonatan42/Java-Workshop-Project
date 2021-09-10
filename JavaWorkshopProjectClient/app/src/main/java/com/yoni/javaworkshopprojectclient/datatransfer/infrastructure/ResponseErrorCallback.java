package com.yoni.javaworkshopprojectclient.datatransfer.infrastructure;

import androidx.annotation.NonNull;

import retrofit2.Call;

public interface ResponseErrorCallback<T> {
    void onResponseError(@NonNull Call<ServerResponse<T>> call, ServerResponse.ServerResponseError responseError);
}
