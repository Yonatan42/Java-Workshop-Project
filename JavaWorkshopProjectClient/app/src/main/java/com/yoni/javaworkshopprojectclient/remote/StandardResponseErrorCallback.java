package com.yoni.javaworkshopprojectclient.remote;

import android.content.Context;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;

import retrofit2.Call;

public class StandardResponseErrorCallback<T> implements ResponseErrorCallback<T> {
    private ParentActivity parentActivity;
    private Runnable retryBlock;

    public StandardResponseErrorCallback(ParentActivity parentActivity){
        this.parentActivity = parentActivity;
    }

    public StandardResponseErrorCallback(ParentActivity parentActivity, Runnable retryBlock){
        this(parentActivity);
        this.retryBlock = retryBlock;
    }

    public void onResponseError(@NonNull Call<ServerResponse<T>> call, ServerResponse.ServerResponseError responseError){
        onPreErrorResponse();
        switch (responseError.getCode()){
            case ServerResponse.ServerResponseError.UNKNOWN_ERROR_CODE:
            case ServerResponse.ServerResponseError.PAGE_NOT_FOUND_CODE:
                if(retryBlock != null) {
                    new ErrorPopup(parentActivity, parentActivity.getString(R.string.error_check_internet), retryBlock).show();
                }
                else{
                    new ErrorPopup(parentActivity, parentActivity.getString(R.string.error_check_internet)).show();
                }
                return;
            case ServerResponse.ServerResponseError.INVALID_TOKEN_CODE:
                parentActivity.logoutUser();
                return;

        }
        onUnhandledResponseError(call, responseError);
    }

    public void onPreErrorResponse(){}
    public void onUnhandledResponseError(@NonNull Call<ServerResponse<T>> call, ServerResponse.ServerResponseError responseError){}
}
