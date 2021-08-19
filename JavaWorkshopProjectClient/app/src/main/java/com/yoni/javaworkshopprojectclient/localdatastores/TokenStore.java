package com.yoni.javaworkshopprojectclient.localdatastores;

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

    private TokenStore(){}

    // todo - implement shared preferences

    private String token;

    public void storeToken (String token){
        // todo - fill this in once we have shared preferences
        // will check if the token is equal to the field, if not, save to shared preferences and update field
    }

    public String getToken (){
        if(token == null){
            // todo - fill this in once we have shared preferences
            // get token from share preferences and set it to the field
            token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJteWVtYWlsN0BteS5lbWFpbGQ0OTBjODdhMjYyZDVkODhlMzRmNTY5ZWM2NGMxZjMxYjg0ZDg4ODIyM2UxZjQwYzg1NWU0MGU3YmEyYjE3N2MiLCJpYXQiOjE2MjkzODQ2OTIsImV4cCI6MTYyOTM4NDk0Mn0.6G2peVjWbA6XQalp6jQgg1yA6tPIBqv7F6FkEanThFY";
        }
        return token;
    }

    public boolean hasToken(){
        return getToken() != null;
    }
}
