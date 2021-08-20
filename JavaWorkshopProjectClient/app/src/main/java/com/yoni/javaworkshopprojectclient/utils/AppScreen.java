package com.yoni.javaworkshopprojectclient.utils;

import com.yoni.javaworkshopprojectclient.ui.fragments.AdminFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.BaseFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.CartFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.OrdersFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.ProductsFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.ProfileFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.RegisterFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.SplashFragment;

public enum AppScreen {
    SPLASH(new SplashFragment()),
    REGISTER(new RegisterFragment()),
    PRODUCTS(new ProductsFragment()),
    CART(new CartFragment()),
    ORDERS(new OrdersFragment()),
    PROFILE(new ProfileFragment()),
    ADMIN(new AdminFragment());


    private final BaseFragment fragment;
    AppScreen(BaseFragment fragment){
        this.fragment = fragment;
    }

    public BaseFragment getFragment(){
        return this.fragment;
    }
}
