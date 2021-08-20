package com.yoni.javaworkshopprojectclient.localdatastores;

import android.content.Context;

public abstract class InitializedStore {

    protected boolean isInitialized;


    public void initialize(Context context){
        if(isInitialized){
            return;
        }
        privateInitialize(context);
        isInitialized = true;
    }

    public boolean isInitialized(){
        return isInitialized;
    }


    protected abstract void privateInitialize(Context context);

    protected final void throwIfUninitialized(){
        if(!isInitialized){
            throw new IllegalStateException("initialize() must be called before using this method");
        }
    }
}
