package com.yoni.javaworkshopprojectclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessTokennedCallback;
import com.yoni.javaworkshopprojectclient.remote.TokennedServerCallback;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.LoginPopup;
import com.yoni.javaworkshopprojectclient.utils.AppScreen;

import retrofit2.Call;
import retrofit2.Response;

public class SplashFragment extends BaseFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_splash, container, false);
        return view;
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
        RemoteServiceManager.getInstance().getUsersService().login(TokenStore.getInstance().getToken(),
                (call, response, result) -> {
            DataSets.getInstance().setCurrentUser(result.getUser());
            DataSets.getInstance().setCategories(result.getCategories());
            getParentActivity().makeFragmentTransition(AppScreen.PRODUCTS.getFragment(), false);
        },
                (call, responseError) -> {
            if (responseError.getCode() == ServerResponse.ServerResponseError.UNKNOWN_ERROR_CODE) {
                new ErrorPopup(getContext(), getString(R.string.error_check_internet), SplashFragment.this::attemptLogin).show();
            }
            new LoginPopup(getParentActivity()).show();
        });
    }


}
