package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.ServerResponse;
import com.yoni.javaworkshopprojectclient.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.ErrorCodes;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.LoginPopup;

import retrofit2.Call;

public class SplashFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentActivity().setTabBarVisibility(false);

        if(TokenStore.getInstance().hasToken()){
            attemptLogin();
        }
        else{
            new LoginPopup(getParentActivity()).show();
        }
    }

    private void attemptLogin() {
        RemoteServiceManager.getInstance().getUsersService().login(
                (call, response, result) -> getParentActivity().loginUser(result),
                new StandardResponseErrorCallback<LoginResponse>(getParentActivity(), SplashFragment.this::attemptLogin) {
                    @Override
                    public void onUnhandledResponseError(@NonNull Call<ServerResponse<LoginResponse>> call, ServerResponse.ServerResponseError responseError) {
                        String errorMessage;
                        switch (responseError.getCode()){
                            case ErrorCodes.USERS_NO_SUCH_USER:
                                errorMessage = getParentActivity().getString(R.string.error_user_doesnt_exist);
                                break;
                            default:
                                super.onUnhandledResponseError(call, responseError);
                                return;
                        }
                        ErrorPopup.createGenericOneOff(getParentActivity(), errorMessage).show();
                    }
                },
                getParentActivity().getLoader());
    }


}
