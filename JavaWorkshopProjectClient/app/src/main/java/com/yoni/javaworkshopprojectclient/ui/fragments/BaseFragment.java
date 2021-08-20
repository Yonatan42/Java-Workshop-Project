package com.yoni.javaworkshopprojectclient.ui.fragments;

import androidx.fragment.app.Fragment;

import com.yoni.javaworkshopprojectclient.ui.ParentActivity;

public abstract class BaseFragment extends Fragment {

    public ParentActivity getParentActivity(){
        return (ParentActivity)getActivity();
    }

    public String getIdentifier(){
        return this.getClass().getName();
    }
}
