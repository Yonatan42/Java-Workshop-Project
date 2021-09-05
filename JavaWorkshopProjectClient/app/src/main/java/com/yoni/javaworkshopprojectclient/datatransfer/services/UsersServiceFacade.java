package com.yoni.javaworkshopprojectclient.datatransfer.services;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessCallback;

public class UsersServiceFacade extends BaseRemoteServiceFacade<UsersService> {


    public UsersServiceFacade(UsersService service) {
        super(service);
    }

    public void login(String email, String pass, ResponseSuccessCallback<LoginResponse> onSuccess, ResponseErrorCallback<LoginResponse> onError){
        enqueue(service.login(email, pass), onSuccess, onError);
    }

    public void login(ResponseSuccessCallback<LoginResponse> onSuccess, ResponseErrorCallback<LoginResponse> onError){
        enqueue(service.login(getToken()), onSuccess, onError);
    }

    public void register(String email,
                         String pass,
                         String firstName,
                         String lastName,
                         String phone,
                         String address,
                         ResponseSuccessCallback<LoginResponse> onSuccess,
                         ResponseErrorCallback<LoginResponse> onError){
        enqueue(service.register(email, pass, firstName, lastName, phone, address), onSuccess, onError);
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
        enqueue(service.remoteRegister(getToken(), email, pass, firstName, lastName, phone, address, isAdmin), onSuccess, onError);
    }

    // todo - on server, since password is potentially changed, we need to create a new token
    public void updateInfo(int userId,
                           String email,
                           String pass,
                           String firstName,
                           String lastName,
                           String phone,
                           String address,
                           ResponseSuccessCallback<User> onSuccess,
                           ResponseErrorCallback<User> onError){
        enqueue(service.updateInfo(getToken(), userId, email, pass, firstName, lastName, phone, address), onSuccess, onError);
        // todo - remove this once we're connected to the server
        /*
        User currentUser = DataSets.getInstance().getCurrentUser();
        currentUser.setEmail(email);
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        onSuccess.onResponseSuccessTokenned(null, null, currentUser);
         */
    }

    public void invalidateToken(int userId, ResponseSuccessCallback<Void> onSuccess, ResponseErrorCallback<Void> onError){
        enqueue(service.invalidateToken(getToken(), userId), onSuccess, onError);
        // todo - remove this once we're connected to the server
        // onSuccess.onResponseSuccessTokenned(null, null ,null);
    }
}
