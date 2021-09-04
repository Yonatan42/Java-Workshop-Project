package com.yoni.javaworkshopprojectclient.ui;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.ViewGroup;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.events.Event;
import com.yoni.javaworkshopprojectclient.events.OnActivityResultListener;
import com.yoni.javaworkshopprojectclient.events.OnRequestPermissionResultListener;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.ui.screenfragments.BaseFragment;
import com.yoni.javaworkshopprojectclient.utils.AppScreen;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


public class ParentActivity extends AppCompatActivity {


    public static final int TAB_PROFILE = 0;
    public static final int TAB_PRODUCTS = 1;
    public static final int TAB_ORDERS = 2;
    public static final int TAB_CART = 3;
    public static final int TAB_ADMIN = 4;

    @IntDef({TAB_PRODUCTS, TAB_ORDERS, TAB_CART, TAB_PROFILE, TAB_ADMIN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TabIndex {}

    @TabIndex public static final int INITIAL_TAB = TAB_PRODUCTS;

    private FragmentManager fragmentManager;
    private TabLayout tabLayout;

    private final Event<OnActivityResultListener> onActivityResultEvent = new Event<>();
    private final Event<OnRequestPermissionResultListener> onPermissionResultEvent = new Event<>();

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
                .add(R.id.main_frag_holder, AppScreen.SPLASH.getFragment())
                .commit();
    }


    private void initializeTabLayout(){
        tabLayout = findViewById(R.id.main_tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPos = tab.getPosition();
                switch (tabPos){
                    case TAB_PRODUCTS:
                        makeFragmentTransition(AppScreen.PRODUCTS.getFragment());
                        break;
                    case TAB_ORDERS:
                        makeFragmentTransition(AppScreen.ORDERS.getFragment());
                        break;
                    case TAB_CART:
                        makeFragmentTransition(AppScreen.CART.getFragment());
                        break;
                    case TAB_PROFILE:
                        makeFragmentTransition(AppScreen.PROFILE.getFragment());
                        break;
                    case TAB_ADMIN:
                        makeFragmentTransition(AppScreen.ADMIN.getFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        ViewGroup tabs = ((ViewGroup) tabLayout.getChildAt(0));
        for (int i = 0; i < tabs.getChildCount(); i++){
            tabLayout.getTabAt(i).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        }
        tabLayout.setSelectedTabIndicatorColor(((MaterialShapeDrawable)tabLayout.getBackground()).getFillColor().getDefaultColor());
        tabLayout.setTabRippleColorResource(android.R.color.darker_gray);

        setAdminModeTabs(DataSets.getInstance().getCurrentUser().isAdminModeActive());
    }

    public void makeFragmentTransition(BaseFragment frag){
        makeFragmentTransition(frag, false);
    }

    public void makeFragmentTransition(BaseFragment frag, boolean addToBackStack){
        if(fragmentManager.getFragments().lastIndexOf(frag) == fragmentManager.getFragments().size()-1){
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frag_holder, frag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if(addToBackStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void addOnPermissionsResultListener(OnRequestPermissionResultListener listener){
        onPermissionResultEvent.addListener(listener);
    }

    public void removeOnPermissionsResultListener(OnRequestPermissionResultListener listener){
        onPermissionResultEvent.removeListener(listener);
    }

    public boolean requestPermissions(int requestCode, String... permissions){
        List<String> permissionToRequest = null;
        for(String permission: permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                if(permissionToRequest == null){
                    permissionToRequest = new ArrayList<>();
                }
                permissionToRequest.add(permission);
            }
        }

        if(permissionToRequest != null){
            ActivityCompat.requestPermissions(this, permissionToRequest.toArray(new String[0]), requestCode);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onPermissionResultEvent.fire(requestCode, permissions, grantResults);
    }

    public void addOnActivityResultListener(OnActivityResultListener listener){
        onActivityResultEvent.addListener(listener);
    }

    public void removeOnActivityResultListener(OnActivityResultListener listener){
        onActivityResultEvent.removeListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityResultEvent.fire(requestCode, resultCode, data);
    }

    public void setAdminModeTabs(boolean isAdminMode){
        ViewGroup tabs =  ((ViewGroup) tabLayout.getChildAt(0));
        UIUtils.setViewsVisible(isAdminMode, tabs.getChildAt(TAB_ADMIN));
        UIUtils.setViewsVisible(!isAdminMode, tabs.getChildAt(TAB_CART));
    }

    public void setTabBarVisibility(boolean isVisible){
        UIUtils.setViewsVisible(isVisible, tabLayout);
    }

    public void setSelectedTab(@TabIndex int tabIndex){
        tabLayout.selectTab(tabLayout.getTabAt(tabIndex));
    }

    public void logoutUser(){
        TokenStore.getInstance().clearToken();
        CartStore.getInstance().clear();
        makeFragmentTransition(AppScreen.SPLASH.getFragment());
    }

    public void loginUser(LoginResponse loginData){
        DataSets.getInstance().setCurrentUser(loginData.getUser());
        DataSets.getInstance().setCategories(loginData.getCategories());
        setSelectedTab(ParentActivity.INITIAL_TAB);
    }
}