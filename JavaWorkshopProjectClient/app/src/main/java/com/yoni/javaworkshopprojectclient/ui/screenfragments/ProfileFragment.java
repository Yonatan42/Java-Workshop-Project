package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.ServerResponse;
import com.yoni.javaworkshopprojectclient.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.ErrorCodes;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.areafragments.UserInfoFragment;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.SimpleMessagePopup;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import retrofit2.Call;

public class ProfileFragment extends BaseFragment {

    private UserInfoFragment userInfoFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SwitchCompat switchAdminMode = view.findViewById(R.id.profile_switch_admin_mode);
        Button btnLogout = view.findViewById(R.id.profile_btn_logout);
        Button btnEdit = view.findViewById(R.id.profile_btn_edit);
        Button btnSave = view.findViewById(R.id.profile_btn_save);
        Button btnCancel = view.findViewById(R.id.profile_btn_cancel);
        userInfoFragment = (UserInfoFragment) getChildFragmentManager().findFragmentById(R.id.profile_user_details_fragment);


        User currentUser = getCurrentUser();
        UIUtils.setViewsVisible(currentUser.isAdmin(), switchAdminMode);
        if(currentUser.isAdmin()) {
            switchAdminMode.setFocusable(false);
            switchAdminMode.setOnCheckedChangeListener((v, isChecked) -> {
                currentUser.setAdminModeActive(isChecked);
                getParentActivity().setAdminModeTabs(isChecked);
            });

            switchAdminMode.setChecked(currentUser.isAdminModeActive());
        }

        btnLogout.setOnClickListener(v -> {
            getParentActivity().logoutUser();
            switchAdminMode.setChecked(false);
        });

        btnEdit.setOnClickListener(v -> {
            userInfoFragment.setEditable(true);
            userInfoFragment.setShowPasswords(true);
            UIUtils.setViewsVisible(false, btnEdit);
            UIUtils.setViewsVisible(true, btnSave, btnCancel);
        });

        btnCancel.setOnClickListener(v -> {
            userInfoFragment.set(getCurrentUser());
            userInfoFragment.setEditable(false);
            userInfoFragment.setShowPasswords(false);
            UIUtils.setViewsVisible(true, btnEdit);
            UIUtils.setViewsVisible(false, btnSave, btnCancel);
        });

        btnSave.setOnClickListener(v -> {
            if(!userInfoFragment.validate(true)){
                return;
            }
            RemoteServiceManager.getInstance().getUsersService().updateInfo(
                currentUser.getId(),
                userInfoFragment.getEmail(),
                userInfoFragment.getPassword(),
                userInfoFragment.getFirstName(),
                userInfoFragment.getLastName(),
                userInfoFragment.getPhone(),
                userInfoFragment.getAddress(),
                (call, response, result) -> {
                    boolean adminMode = currentUser.isAdminModeActive();
                    currentUser.replace(result);
                    currentUser.setAdminModeActive(currentUser.isAdmin() && adminMode);
                    SimpleMessagePopup.createGenericTimed(getParentActivity(), getString(R.string.profile_update_complete)).show();
                    btnCancel.callOnClick();
                },
                new StandardResponseErrorCallback<User>(getParentActivity()) {
                    @Override
                    public void onUnhandledResponseError(@NonNull Call<ServerResponse<User>> call, ServerResponse.ServerResponseError responseError) {
                        String errorMessage;
                        switch (responseError.getCode()){
                            case ErrorCodes.USERS_NO_SUCH_USER:
                                errorMessage = getString(R.string.error_user_doesnt_exist);
                                break;
                            case ErrorCodes.USERS_ALREADY_EXISTS:
                                errorMessage = getString(R.string.error_user_already_exists);
                                break;
                            default:
                                super.onUnhandledResponseError(call, responseError);
                                return;
                        }
                        ErrorPopup.createGenericOneOff(getParentActivity(), errorMessage).show();
                    }
                },
                    getParentActivity().getLoader());
        });

        userInfoFragment.set(getCurrentUser());
    }

    private User getCurrentUser(){
        return DataSets.getInstance().getCurrentUser();
    }
}
