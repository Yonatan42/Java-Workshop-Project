package com.yoni.javaworkshopprojectclient.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;

import retrofit2.Call;

public class StandardResponseErrorCallback<T> implements ResponseErrorCallback<T> {
    private static final String TAG = "ServerErrorCallback";
    private final ParentActivity parentActivity;
    private Runnable retryBlock;

    public StandardResponseErrorCallback(ParentActivity parentActivity){
        this.parentActivity = parentActivity;
    }

    public StandardResponseErrorCallback(ParentActivity parentActivity, Runnable retryBlock){
        this(parentActivity);
        this.retryBlock = retryBlock;
    }

    public void onResponseError(@NonNull Call<ServerResponse<T>> call, ServerResponse.ServerResponseError responseError){
        Log.e(TAG, "error received from server -  call: "+call.request().method()+" "+call.request().url().url() + ", error " + responseError.getCode() + " - " + responseError.getMessage());
        onPreErrorResponse();
        switch (responseError.getCode()){
            case ErrorCodes.UNKNOWN_ERROR:
            case ErrorCodes.SERVER_UNKNOWN_ERROR:
                if(retryBlock != null) {
                    new ErrorPopup(parentActivity, parentActivity.getString(R.string.error_check_internet), retryBlock).show();
                }
                else{
                    ErrorPopup.createGenericOneOff(parentActivity, parentActivity.getString(R.string.error_check_internet)).show();
                }
                return;
            case ErrorCodes.USERS_UNAUTHORIZED:
                ErrorPopup.createGenericOneOff(parentActivity, parentActivity.getString(R.string.error_access_denied)).show();
                return;
            case ErrorCodes.TOKEN_INVALID:
                parentActivity.logoutUser();
                return;

        }
        onUnhandledResponseError(call, responseError);
    }

    public void onPreErrorResponse(){}
    public void onUnhandledResponseError(@NonNull Call<ServerResponse<T>> call, ServerResponse.ServerResponseError responseError){
        ErrorPopup.createGenericOneOff(parentActivity).show();
    }
}
