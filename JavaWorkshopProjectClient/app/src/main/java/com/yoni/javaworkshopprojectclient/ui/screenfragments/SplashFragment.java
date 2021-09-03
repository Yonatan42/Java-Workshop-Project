package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.LoginPopup;
import com.yoni.javaworkshopprojectclient.utils.AppScreen;

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
            // todo - possibly delay to show splash page
            new LoginPopup(getParentActivity()).show();
        }
    }

    private void attemptLogin() {
        RemoteServiceManager.getInstance().getUsersService().login(
                (call, response, result) -> {
            DataSets.getInstance().setCurrentUser(result.getUser());
            DataSets.getInstance().setCategories(result.getCategories());
            getParentActivity().setSelectedTab(ParentActivity.INITIAL_TAB);
        },
                new StandardResponseErrorCallback<TokennedResult<LoginResponse>>(getParentActivity(), SplashFragment.this::attemptLogin) {
                    @Override
                    public void onUnhandledResponseError(@NonNull Call<ServerResponse<TokennedResult<LoginResponse>>> call, ServerResponse.ServerResponseError responseError) {
                        new LoginPopup(getParentActivity()).show();
                    }
                });
    }


}
