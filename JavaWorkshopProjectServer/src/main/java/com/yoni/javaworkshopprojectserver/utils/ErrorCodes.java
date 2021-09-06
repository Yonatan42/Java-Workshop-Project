/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.utils;

/**
 *
 * @author Yoni
 */
public class ErrorCodes {

    private ErrorCodes(){}
    
    public static final int UNKNOWN = 100;
    public static final int PAGE_NOT_FOUND = 200;

    public static final int PERSISTENCE_GENERAL = 300;

    public static final int USERS_GENERAL = 400;
    public static final int USERS_PASSWORD_MISSMATCH = 401;
    public static final int USERS_NO_SUCH_USER = 402;
    public static final int USERS_ALREADY_EXISTS = 403;
    public static final int USERS_UNAUTHORIZED = 404;

    public static final int TOKEN_GENERAL = 500;
    public static final int TOKEN_EXPIRED = 501;
    public static final int TOKEN_INVALID = 502;

    public static final int RESOURCES_GENERAL = 500;
    public static final int RESOURCES_NOT_FOUND = 501;

}