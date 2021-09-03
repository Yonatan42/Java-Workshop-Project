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
import com.yoni.javaworkshopprojectclient.ui.areafragments.UserInfoFragment;
import com.yoni.javaworkshopprojectclient.ui.screenfragments.BaseFragment;

public class AdminCreateUserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_admin_create_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCreate = view.findViewById(R.id.admin_create_user_create);
        Button btnClear = view.findViewById(R.id.admin_create_user_clear);
        UserInfoFragment userInfoFragment = (UserInfoFragment) getChildFragmentManager().findFragmentById(R.id.admin_create_user_details_fragment);
        CheckBox cbIsAdmin = view.findViewById(R.id.admin_create_user_cb_is_admin);
    }
}
