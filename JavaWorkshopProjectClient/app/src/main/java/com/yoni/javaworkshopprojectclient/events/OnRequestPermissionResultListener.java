package com.yoni.javaworkshopprojectclient.events;

import android.content.Intent;

import androidx.annotation.NonNull;

public abstract class OnRequestPermissionResultListener implements EventListener {

    @Override
    public void fire(Object... params) {
        if(params.length != 3){
            throw new IllegalArgumentException("OnActivityResultListener cannot be fired - wrong number of arguments");
        }
        else if(!(params[0] instanceof Integer)){
            throw new IllegalArgumentException("OnActivityResultListener cannot be fired - first argument must be an Integer");
        }
        else if(!(params[1] instanceof String[])){
            throw new IllegalArgumentException("OnActivityResultListener cannot be fired - second argument must be a String Array");
        }
        else if(!(params[2] instanceof int[])){
            throw new IllegalArgumentException("OnActivityResultListener cannot be fired - third argument must be an int Array");
        }
        onRequestPermissionsResult((Integer) params[0], (String[]) params[1], (int[]) params[2]);
    }

    public abstract void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
