package com.yoni.javaworkshopprojectclient.utils;

import com.yoni.javaworkshopprojectclient.ui.fragments.BaseFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.ProductsFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.RegisterFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.SplashFragment;

public enum AppScreen {
    SPLASH(new SplashFragment()),
    REGISTER(new RegisterFragment()),
    PRODUCTS(new ProductsFragment());


    private BaseFragment fragment;
    AppScreen(BaseFragment fragment){
        this.fragment = fragment;
    }

    public BaseFragment getFragment(){
        return this.fragment;
    }
}
