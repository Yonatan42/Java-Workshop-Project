package com.yoni.javaworkshopprojectclient.datatransfer.infrastructure;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://10.0.2.2:8080/JavaWorkshopProjectServer/resources/";

    private static Retrofit client = null;

    public static Retrofit getClient() {
        if (client == null) {
            synchronized (RetrofitClient.class) {
                if (client == null) {
                    client = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return client;
    }

    private RetrofitClient(){}
}