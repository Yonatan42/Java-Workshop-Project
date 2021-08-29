package com.yoni.javaworkshopprojectclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

public class ProfileFragment extends BaseFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SwitchCompat switchAdminMode = view.findViewById(R.id.profile_switch_admin_mode);

        User currentUser = DataSets.getInstance().getCurrentUser();
        UIUtils.setViewsVisible(currentUser.isAdmin(), switchAdminMode);
        switchAdminMode.setChecked(currentUser.isAdminModeActive());

        switchAdminMode.setOnCheckedChangeListener((v, isChecked) -> {
            currentUser.setAdminModeActive(isChecked);
            getParentActivity().setAdminTabVisible(isChecked);
        });

        switchAdminMode.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                switchAdminMode.setChecked(!switchAdminMode.isChecked());
            }
        });
    }
}
