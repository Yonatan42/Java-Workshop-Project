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
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

public class ProfileFragment extends BaseFragment {

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
            getParentActivity().finish();
        });
    }
}
