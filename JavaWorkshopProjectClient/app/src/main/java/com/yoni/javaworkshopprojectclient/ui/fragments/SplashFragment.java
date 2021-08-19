package com.yoni.javaworkshopprojectclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.Customer;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteService;
import com.yoni.javaworkshopprojectclient.remote.TokennedServerCallback;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.LoginPopup;

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

        if(TokenStore.getInstance().hasToken()){
            // todo - try to login
            RemoteService.getInstance().getCustomersService().login(TokenStore.getInstance().getToken()).enqueue(new TokennedServerCallback<Customer>() {
                @Override
                public void onResponseSuccessTokenned(Call<ServerResponse<TokennedResult<Customer>>> call, Response<ServerResponse<TokennedResult<Customer>>> response, Customer result) {
                    // todo - perhaps have the different fragments saved in the parent
                    getParentActivity().makeFragmentTransition(new ProductsFragment(), false);
                }

                @Override
                public void onResponseError(Call<ServerResponse<TokennedResult<Customer>>> call, ServerResponse.ServerResponseError responseError) {
                    new LoginPopup(getParentActivity()).show();
                }

                @Override
                public void onFailure(Call<ServerResponse<TokennedResult<Customer>>> call, Throwable t) {
                    // todo - change this
                    new ErrorPopup(getContext(), "more death").show();
                }
            });
        }
        else{
            // todo - possibly delay to show splash page
            new LoginPopup(getParentActivity()).show();
        }
    }


}
