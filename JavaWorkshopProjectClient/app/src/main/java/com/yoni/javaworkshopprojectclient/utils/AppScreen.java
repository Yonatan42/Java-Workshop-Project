package com.yoni.javaworkshopprojectclient.utils;

import com.yoni.javaworkshopprojectclient.ui.screenfragments.AdminFragment;
import com.yoni.javaworkshopprojectclient.ui.screenfragments.BaseFragment;
import com.yoni.javaworkshopprojectclient.ui.screenfragments.CartFragment;
import com.yoni.javaworkshopprojectclient.ui.screenfragments.OrdersFragment;
import com.yoni.javaworkshopprojectclient.ui.screenfragments.ProductsFragment;
import com.yoni.javaworkshopprojectclient.ui.screenfragments.ProfileFragment;
import com.yoni.javaworkshopprojectclient.ui.screenfragments.RegisterFragment;
import com.yoni.javaworkshopprojectclient.ui.screenfragments.SplashFragment;

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
