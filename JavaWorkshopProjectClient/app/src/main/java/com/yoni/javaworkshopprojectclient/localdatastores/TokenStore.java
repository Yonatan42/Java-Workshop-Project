package com.yoni.javaworkshopprojectclient.localdatastores;

import android.content.Context;
import android.content.SharedPreferences;


public class TokenStore {

    private static TokenStore instance;

    public static TokenStore getInstance(){
        if(instance == null){
            synchronized (TokenStore.class){
                if(instance == null){
                    instance = new TokenStore();
                }
            }
        }
        return instance;
    }

    private final static String TOKEN_KEY = "token";

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private boolean isInitialized;
    private String token;

    private TokenStore(){}

    public void initialize(Context context){
        sharedPrefs = context.getSharedPreferences("TokenStore", Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
        isInitialized = true;
    }

    public boolean isInitialized(){
        return isInitialized;
    }

    public void storeToken (String token){
        throwIfUninitialized();
        if(token.equals(this.token)){
            return;
        }
        if(sharedPrefsEditor.putString(TOKEN_KEY, token).commit()){
            this.token = token;
        }
    }

    public String getToken (){
        throwIfUninitialized();
        if(token == null){
            token = sharedPrefs.getString(TOKEN_KEY, null);
        }
        return token;
    }

    public boolean hasToken(){
        return getToken() != null;
    }

    public void clearToken(){
        if(sharedPrefsEditor.clear().commit()){
            token = null;
        }
    }

    private void throwIfUninitialized(){
        if(!isInitialized){
            throw new IllegalStateException("initialize() must be called before using this method");
        }
    }
}
