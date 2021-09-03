package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.ui.areafragments.UserInfoFragment;

public class AdminFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCreate = view.findViewById(R.id.admin_create_user_create);
        Button btnClear = view.findViewById(R.id.admin_create_user_clear);
        UserInfoFragment userInfoFragment = (UserInfoFragment) getChildFragmentManager().findFragmentById(R.id.admin_create_user_details_fragment);
        CheckBox cbIsAdmin = view.findViewById(R.id.admin_create_user_cb_is_admin);
        Button btnInvalidate = view.findViewById(R.id.admin_invalidate_token_txt_userid);
        Button txtUserId = view.findViewById(R.id.admin_invalidate_token_btn_invalidate);

    }
}
