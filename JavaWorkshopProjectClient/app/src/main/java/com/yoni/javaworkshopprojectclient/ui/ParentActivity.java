package com.yoni.javaworkshopprojectclient.ui;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.ui.fragments.BaseFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.SplashFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ParentActivity extends AppCompatActivity {

    public static final int TAB_PRODUCTS = 0;
    public static final int TAB_ORDERS = 1;
    public static final int TAB_CART = 2;
    public static final int TAB_PROFILE = 3;
    public static final int TAB_ADMIN = 4;

    @IntDef({TAB_PRODUCTS, TAB_ORDERS, TAB_CART, TAB_PROFILE, TAB_ADMIN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TabIndex {}



    private FragmentManager fragmentManager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        initializeLocalStores();
        initializeFragments();
        initializeTabLayout();
    }

    private void initializeLocalStores(){
        TokenStore.getInstance().initialize(this);
        CartStore.getInstance().initialize(this);
    }

    private void initializeFragments(){
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.main_frag_holder, new SplashFragment())
                .commit();
    }

    private void initializeTabLayout(){
        tabLayout = findViewById(R.id.main_tab_layout);

        ViewGroup tabs = ((ViewGroup) tabLayout.getChildAt(0));
        for (int i = 0; i < tabs.getChildCount(); i++){
            tabLayout.getTabAt(i).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        }
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, android.R.color.transparent));
        tabLayout.setTabRippleColorResource(android.R.color.darker_gray);

        setAdminTabVisible(false);
    }

    public void makeFragmentTransition(BaseFragment frag){
        makeFragmentTransition(frag, true);
    }
    public void makeFragmentTransition(BaseFragment frag, boolean addToBackStack){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frag_holder, frag);
        if(addToBackStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void setAdminTabVisible(boolean isVisible){
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(TAB_ADMIN).setVisibility(visibility);
    }

    public void setSelectedTab(@TabIndex int tabIndex){
        tabLayout.selectTab(tabLayout.getTabAt(tabIndex));
    }
}