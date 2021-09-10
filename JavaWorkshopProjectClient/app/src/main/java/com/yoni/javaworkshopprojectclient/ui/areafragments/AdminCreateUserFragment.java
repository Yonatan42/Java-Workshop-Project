package com.yoni.javaworkshopprojectclient.ui.areafragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.remote.ErrorCodes;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.SimpleMessagePopup;

import retrofit2.Call;

public class AdminCreateUserFragment extends Fragment {

    private Button btnCreate;
    private Button btnClear;
    private UserInfoFragment userInfoFragment;
    private CheckBox cbIsAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_admin_create_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCreate = view.findViewById(R.id.admin_create_user_create);
        btnClear = view.findViewById(R.id.admin_create_user_clear);
        userInfoFragment = (UserInfoFragment) getChildFragmentManager().findFragmentById(R.id.admin_create_user_details_fragment);
        cbIsAdmin = view.findViewById(R.id.admin_create_user_cb_is_admin);

        btnCreate.setOnClickListener(v -> {
            if(userInfoFragment.validate(true)) {
                createNewUser();
            }
        });

        btnClear.setOnClickListener(v -> clear());
    }


    private void createNewUser() {
        setEditable(false);
        RemoteServiceManager.getInstance().getUsersService().remoteRegister(
                userInfoFragment.getEmail(),
                userInfoFragment.getPassword(),
                userInfoFragment.getFirstName(),
                userInfoFragment.getLastName(),
                userInfoFragment.getPhone(),
                userInfoFragment.getAddress(),
                cbIsAdmin.isChecked(),
                (call, response, result) -> {
                    setEditable(true);
                    SimpleMessagePopup.createGenericTimed(getContext(), getString(R.string.admin_create_user_success)).show();
                    clear();
                },
                new StandardResponseErrorCallback<User>((ParentActivity) getActivity()) {
                    @Override
                    public void onPreErrorResponse() {
                        setEditable(true);
                    }

                    @Override
                    public void onUnhandledResponseError(@NonNull Call<ServerResponse<User>> call, ServerResponse.ServerResponseError responseError) {
                        String errorMessage;
                        switch (responseError.getCode()){
                            case ErrorCodes.USERS_ALREADY_EXISTS:
                                errorMessage = getString(R.string.error_user_already_exists);
                                break;
                            default:
                                super.onUnhandledResponseError(call, responseError);
                                return;
                        }
                        ErrorPopup.createGenericOneOff(getContext(), errorMessage).show();

                    }
                },
                ((ParentActivity) getActivity()).getLoader()
        );
    }

    private void clear() {
        userInfoFragment.clear();
        cbIsAdmin.setChecked(false);
    }


    private void setEditable(boolean editable){
        userInfoFragment.setEditable(editable);
        cbIsAdmin.setEnabled(editable);
    }
}
