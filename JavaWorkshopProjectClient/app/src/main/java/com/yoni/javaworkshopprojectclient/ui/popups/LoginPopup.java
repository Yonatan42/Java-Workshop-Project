package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.ErrorCodes;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.utils.AppScreen;

import retrofit2.Call;

public class LoginPopup extends AlertDialog {

    private ParentActivity parentActivity;

    public LoginPopup(ParentActivity parentActivity){
        super(parentActivity);
        this.parentActivity = parentActivity;

        View layout = LayoutInflater.from(parentActivity).inflate(R.layout.popup_login, null, false);
        TextView txtEmail = layout.findViewById(R.id.login_txt_email);
        TextView txtPass = layout.findViewById(R.id.login_txt_pass);
        Button btnSignin = layout.findViewById(R.id.login_btn_signin);
        Button btnReg = layout.findViewById(R.id.login_btn_register);


        btnSignin.setOnClickListener(v -> {
            // todo - validate forms - make validation utils class
            String email = txtEmail.getText().toString().toLowerCase().trim();
            String pass = txtPass.getText().toString();

            RemoteServiceManager.getInstance().getUsersService().login(email, pass,
                    (call, response, result) -> {
                        parentActivity.loginUser(result);
                        dismiss();
                    },
                    new StandardResponseErrorCallback<LoginResponse>(parentActivity){
                        @Override
                        public void onUnhandledResponseError(@NonNull Call<ServerResponse<LoginResponse>> call, ServerResponse.ServerResponseError responseError) {
                            String errorMessage;
                            switch (responseError.getCode()){
                                case ErrorCodes.USERS_NO_SUCH_USER:
                                    errorMessage = parentActivity.getString(R.string.error_user_doesnt_exist);
                                    break;
                                case ErrorCodes.USERS_PASSWORD_MISMATCH:
                                    errorMessage = parentActivity.getString(R.string.error_user_credential_mismatch);
                                    break;
                                default:
                                    super.onUnhandledResponseError(call, responseError);
                                    return;
                            }
                            ErrorPopup.createGenericOneOff(parentActivity, errorMessage).show();
                        }
                    });
        });

        btnReg.setOnClickListener(v -> {
            dismiss();
            parentActivity.makeFragmentTransition(AppScreen.REGISTER.getFragment(), true);
        });



        setView(layout);
        setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        parentActivity.finish();
    }
}
