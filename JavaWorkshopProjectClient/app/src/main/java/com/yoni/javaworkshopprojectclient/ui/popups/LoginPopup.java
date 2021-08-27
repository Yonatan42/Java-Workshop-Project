package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.RemoteService;
import com.yoni.javaworkshopprojectclient.remote.TokennedServerCallback;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.utils.AppScreen;

import retrofit2.Call;
import retrofit2.Response;

public class LoginPopup extends AlertDialog {

    public LoginPopup(ParentActivity parentActivity){
        super(parentActivity);

        View layout = LayoutInflater.from(parentActivity).inflate(R.layout.popup_login, null, false);
        TextView txtEmail = layout.findViewById(R.id.login_txt_email);
        TextView txtPass = layout.findViewById(R.id.login_txt_pass);
        Button btnSignin = layout.findViewById(R.id.login_btn_signin);
        Button btnReg = layout.findViewById(R.id.login_btn_register);


        btnSignin.setOnClickListener(v -> {
            // todo - validate forms - make validation utils class
            String email = txtEmail.getText().toString().toLowerCase().trim();
            String pass = txtPass.getText().toString();

            RemoteService.getInstance().getUsersService().login(email, pass).enqueue(new TokennedServerCallback<User>() {
                @Override
                public void onResponseSuccessTokenned(Call<ServerResponse<TokennedResult<User>>> call, Response<ServerResponse<TokennedResult<User>>> response, User result) {
                    dismiss();
                    DataSets.getInstance().userLiveData.postValue(result);
                    parentActivity.makeFragmentTransition(AppScreen.PRODUCTS.getFragment(), false);
                }

                @Override
                public void onResponseError(Call<ServerResponse<TokennedResult<User>>> call, ServerResponse.ServerResponseError responseError) {
                    // todo - change this
                    new ErrorPopup(parentActivity, "some death").show();
                }

                @Override
                public void onFailure(Call<ServerResponse<TokennedResult<User>>> call, Throwable t) {
                    // todo - change this
                    new ErrorPopup(parentActivity, "some more death").show();
                }
            });
        });

        btnReg.setOnClickListener(v -> {
            dismiss();
            parentActivity.makeFragmentTransition(AppScreen.REGISTER.getFragment());
        });



        setView(layout);
        setCancelable(false);
    }


}
