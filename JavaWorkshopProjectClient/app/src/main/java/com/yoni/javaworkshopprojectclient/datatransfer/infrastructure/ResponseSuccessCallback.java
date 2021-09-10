package com.yoni.javaworkshopprojectclient.datatransfer.infrastructure;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Response;

public interface ResponseSuccessCallback<T> {
    void onResponseSuccess(@NonNull Call<ServerResponse<T>> call, Response<ServerResponse<T>> response, T result);
}
