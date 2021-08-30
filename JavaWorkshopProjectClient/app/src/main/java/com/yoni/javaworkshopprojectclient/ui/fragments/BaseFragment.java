package com.yoni.javaworkshopprojectclient.ui.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;

public abstract class BaseFragment extends Fragment {

    public ParentActivity getParentActivity(){
        return (ParentActivity)getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getParentActivity().setTabBarVisibility(true);
    }
}
