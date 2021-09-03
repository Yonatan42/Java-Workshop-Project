package com.yoni.javaworkshopprojectclient.datatransfer.services;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
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

    public void remoteRegister(String email,
                         String pass,
                         String firstName,
                         String lastName,
                         String phone,
                         String address,
                         boolean isAdmin,
                         ResponseSuccessTokennedCallback<User> onSuccess,
                         ResponseErrorCallback<TokennedResult<User>> onError){
        enqueueTokenned(service.remoteRegister(getToken(), email, pass, firstName, lastName, phone, address, isAdmin), onSuccess, onError);
    }

    // todo - on server, since password is potentially changed, we need to create a new token
    public void updateInfo(int userId,
                           String email,
                           String pass,
                           String firstName,
                           String lastName,
                           String phone,
                           String address,
                           ResponseSuccessTokennedCallback<User> onSuccess,
                           ResponseErrorCallback<TokennedResult<User>> onError){
        /* // todo - uncomment this once we are connected to the server
        enqueueTokenned(service.updateInfo(getToken(), userId, email, pass, firstName, lastName, phone, address), onSuccess, onError);
         */
        // todo - remove this once we're connected to the server
        User currentUser = DataSets.getInstance().getCurrentUser();
        currentUser.setEmail(email);
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        onSuccess.onResponseSuccessTokenned(null, null, currentUser);
    }

    public void invalidateToken(int userId, ResponseSuccessTokennedCallback<Void> onSuccess, ResponseErrorCallback<TokennedResult<Void>> onError){
        /* // todo - uncomment this once we are connected to the server
        enqueueTokenned(service.invalidateToken(getToken(), userId), onSuccess, onError);
        */
        // todo - remove this once we're connected to the server
        onSuccess.onResponseSuccessTokenned(null, null ,null);
    }
}
