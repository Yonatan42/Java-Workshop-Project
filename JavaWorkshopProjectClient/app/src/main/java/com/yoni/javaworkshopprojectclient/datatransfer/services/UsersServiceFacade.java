package com.yoni.javaworkshopprojectclient.datatransfer.services;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessTokennedCallback;
import com.yoni.javaworkshopprojectclient.remote.TokennedServerCallback;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;

public class UsersServiceFacade extends BaseRemoteServiceFacade<UsersService> {


    public UsersServiceFacade(UsersService service) {
        super(service);
    }

    public void login(String email, String pass, ResponseSuccessTokennedCallback<LoginResponse> onSuccess, ResponseErrorCallback<TokennedResult<LoginResponse>> onError){
        enqueueTokenned(service.login(email, pass), onSuccess, onError);
    }

    public void login(ResponseSuccessTokennedCallback<LoginResponse> onSuccess, ResponseErrorCallback<TokennedResult<LoginResponse>> onError){
        enqueueTokenned(service.login(getToken()), onSuccess, onError);
    }

    public void register(String email,
                         String pass,
                         String firstName,
                         String lastName,
                         String phone,
                         String address,
                         ResponseSuccessTokennedCallback<LoginResponse> onSuccess,
                         ResponseErrorCallback<TokennedResult<LoginResponse>> onError){
        enqueueTokenned(service.register(email, pass, firstName, lastName, phone, address), onSuccess, onError);
    }

    public void updateInfo(User user,
                           ResponseSuccessTokennedCallback<User> onSuccess,
                           ResponseErrorCallback<TokennedResult<User>> onError){
        enqueueTokenned(service.updateInfo(getToken(), user.getId(), user), onSuccess, onError);
    }
}
