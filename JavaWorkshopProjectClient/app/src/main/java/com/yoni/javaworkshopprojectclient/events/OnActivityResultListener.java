package com.yoni.javaworkshopprojectclient.events;

import android.content.Intent;
import androidx.annotation.Nullable;

public abstract class OnActivityResultListener implements EventListener {

    @Override
    public void fire(Object... params) {
        if(params.length != 3){
            throw new IllegalArgumentException("OnActivityResultListener cannot be fired - wrong number of arguments");
        }
        else if(!(params[0] instanceof Integer)){
            throw new IllegalArgumentException("OnActivityResultListener cannot be fired - first argument must be an Integer");
        }
        else if(!(params[1] instanceof Integer)){
            throw new IllegalArgumentException("OnActivityResultListener cannot be fired - second argument must be an Integer");
        }
        else if(!(params[2] instanceof Intent)){
            throw new IllegalArgumentException("OnActivityResultListener cannot be fired - third argument must be an Intent");
        }
        onActivityResult((Integer) params[0], (Integer) params[1], (Intent) params[2]);
    }

    public abstract void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
