package com.yoni.javaworkshopprojectclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.ui.fragments.BaseFragment;
import com.yoni.javaworkshopprojectclient.ui.fragments.SplashFragment;

public class ParentActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        initializeLocalStores();
        initializeFragments();
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
}