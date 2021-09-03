package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.areafragments.UserInfoFragment;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;

public class RegisterFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentActivity().setTabBarVisibility(false);

        Button btnReg = view.findViewById(R.id.register_btn_register);
        Button btnBack = view.findViewById(R.id.register_btn_back);
        UserInfoFragment profileDetailsFrag = (UserInfoFragment) getChildFragmentManager().findFragmentById(R.id.register_profile_details_fragment);

        btnBack.setOnClickListener(v -> getParentActivity().onBackPressed());

        btnReg.setOnClickListener(v -> {
            if(!profileDetailsFrag.validate(true)){
                return;
            }

            attemptRegister(profileDetailsFrag);
        });
    }

    private void attemptRegister(UserInfoFragment profileDetailsFrag) {
        profileDetailsFrag.setEditable(false);
        RemoteServiceManager.getInstance().getUsersService().register(
                profileDetailsFrag.getEmail(),
                profileDetailsFrag.getPassword(),
                profileDetailsFrag.getFirstName(),
                profileDetailsFrag.getLastName(),
                profileDetailsFrag.getPhone(),
                profileDetailsFrag.getAddress(),
                (call, response, result) -> getParentActivity().setSelectedTab(ParentActivity.INITIAL_TAB),
                (call, responseError) -> {
                    if (responseError.getCode() == ServerResponse.ServerResponseError.UNKNOWN_ERROR_CODE) {
                        new ErrorPopup(getContext(), getString(R.string.error_check_internet), () -> attemptRegister(profileDetailsFrag)).show();
                    }
                    ErrorPopup.createGenericOneOff(getParentActivity()).show();
                    profileDetailsFrag.setEditable(true);
                }
        );
    }
}
