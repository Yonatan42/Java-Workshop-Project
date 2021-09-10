package com.yoni.javaworkshopprojectclient.datatransfer.services;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessCallback;
import com.yoni.javaworkshopprojectclient.ui.popups.Loader;

public class UsersServiceFacade extends BaseRemoteServiceFacade<UsersService> {


    public UsersServiceFacade(UsersService service) {
        super(service);
    }

    public void login(String email, String pass, ResponseSuccessCallback<LoginResponse> onSuccess, ResponseErrorCallback<LoginResponse> onError) {
        login(email, pass, onSuccess, onError, null);
    }

    public void login(String email, String pass, ResponseSuccessCallback<LoginResponse> onSuccess, ResponseErrorCallback<LoginResponse> onError, Loader loader){
        enqueue(service.login(email, pass), onSuccess, onError, loader);
    }

    public void login(ResponseSuccessCallback<LoginResponse> onSuccess, ResponseErrorCallback<LoginResponse> onError) {
        login(onSuccess, onError, null);
    }
    public void login(ResponseSuccessCallback<LoginResponse> onSuccess, ResponseErrorCallback<LoginResponse> onError, Loader loader){
        enqueue(service.login(getToken()), onSuccess, onError, loader);
    }
    public void register(String email,
                         String pass,
                         String firstName,
                         String lastName,
                         String phone,
                         String address,
                         ResponseSuccessCallback<LoginResponse> onSuccess,
                         ResponseErrorCallback<LoginResponse> onError){
        register(email, pass, firstName, lastName, phone, address, onSuccess, onError, null);
    }

    public void register(String email,
                         String pass,
                         String firstName,
                         String lastName,
                         String phone,
                         String address,
                         ResponseSuccessCallback<LoginResponse> onSuccess,
                         ResponseErrorCallback<LoginResponse> onError,
                         Loader loader){
        enqueue(service.register(email, pass, firstName, lastName, phone, address), onSuccess, onError, loader);
    }

    public void remoteRegister(String email,
                               String pass,
                               String firstName,
                               String lastName,
                               String phone,
                               String address,
                               boolean isAdmin,
                               ResponseSuccessCallback<User> onSuccess,
                               ResponseErrorCallback<User> onError){
        remoteRegister(email, pass, firstName, lastName, phone, address, isAdmin, onSuccess, onError, null);
    }

    public void remoteRegister(String email,
                               String pass,
                               String firstName,
                               String lastName,
                               String phone,
                               String address,
                               boolean isAdmin,
                               ResponseSuccessCallback<User> onSuccess,
                               ResponseErrorCallback<User> onError,
                               Loader loader){
        enqueue(service.remoteRegister(getToken(), email, pass, firstName, lastName, phone, address, isAdmin), onSuccess, onError, loader);
    }

    public void updateInfo(int userId,
                           String email,
                           String pass,
                           String firstName,
                           String lastName,
                           String phone,
                           String address,
                           ResponseSuccessCallback<User> onSuccess,
                           ResponseErrorCallback<User> onError){
        updateInfo(userId, email, pass, firstName, lastName, phone, address, onSuccess, onError, null);
    }

    public void updateInfo(int userId,
                           String email,
                           String pass,
                           String firstName,
                           String lastName,
                           String phone,
                           String address,
                           ResponseSuccessCallback<User> onSuccess,
                           ResponseErrorCallback<User> onError,
                           Loader loader){
        enqueue(service.updateInfo(getToken(), userId, email, pass, firstName, lastName, phone, address), onSuccess, onError, loader);
    }

    public void invalidateToken(int userId, ResponseSuccessCallback<Void> onSuccess, ResponseErrorCallback<Void> onError) {
        invalidateToken(userId, onSuccess, onError, null);
    }

    public void invalidateToken(int userId, ResponseSuccessCallback<Void> onSuccess, ResponseErrorCallback<Void> onError, Loader loader){
        enqueue(service.invalidateToken(getToken(), userId), onSuccess, onError, loader);
    }
}
