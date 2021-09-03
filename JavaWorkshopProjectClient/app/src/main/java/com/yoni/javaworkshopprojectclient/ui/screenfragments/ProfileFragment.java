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
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.ui.areafragments.UserInfoFragment;
import com.yoni.javaworkshopprojectclient.utils.AppScreen;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

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


        User currentUser = DataSets.getInstance().getCurrentUser();
        UIUtils.setViewsVisible(currentUser.isAdmin(), switchAdminMode);
        switchAdminMode.setChecked(currentUser.isAdminModeActive());

        switchAdminMode.setOnCheckedChangeListener((v, isChecked) -> {
            currentUser.setAdminModeActive(isChecked);
            getParentActivity().setAdminModeTabs(isChecked);
        });

        switchAdminMode.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                switchAdminMode.setChecked(!switchAdminMode.isChecked());
            }
        });

        btnLogout.setOnClickListener(v -> {
            TokenStore.getInstance().clearToken();
            CartStore.getInstance().clear();
            getParentActivity().makeFragmentTransition(AppScreen.SPLASH.getFragment());
        });

        btnEdit.setOnClickListener(v -> {
            userInfoFragment.setIsEditable(true);
            userInfoFragment.setShowPasswords(true);
            UIUtils.setViewsVisible(false, btnEdit);
            UIUtils.setViewsVisible(true, btnSave, btnCancel);
        });

        btnCancel.setOnClickListener(v -> {
            setUserInfoDataToUser();
            userInfoFragment.setIsEditable(false);
            userInfoFragment.setShowPasswords(false);
            UIUtils.setViewsVisible(true, btnEdit);
            UIUtils.setViewsVisible(false, btnSave, btnCancel);
        });

        btnSave.setOnClickListener(v -> {
            // todo - server call, on success, set to new user and close the form (make not editable)
        });

        setUserInfoDataToUser();
    }


    private void setUserInfoDataToUser(){
        User currentUser = DataSets.getInstance().getCurrentUser();
        userInfoFragment.setFirstName(currentUser.getFirstName());
        userInfoFragment.setLastName(currentUser.getLastName());
        userInfoFragment.setEmail(currentUser.getEmail());
        userInfoFragment.setPhone(currentUser.getPhone());
        userInfoFragment.setAddress(currentUser.getAddress());
        userInfoFragment.setPassword("");
        userInfoFragment.setPassword2("");
    }
}
