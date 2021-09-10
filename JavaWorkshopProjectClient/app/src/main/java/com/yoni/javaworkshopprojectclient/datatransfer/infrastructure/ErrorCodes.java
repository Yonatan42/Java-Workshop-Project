package com.yoni.javaworkshopprojectclient.datatransfer.infrastructure;

public class ErrorCodes {
    public static final int UNKNOWN_ERROR = 0;

    public static final int SERVER_UNKNOWN_ERROR = 100;

    public static final int USERS_PASSWORD_MISMATCH = 401;
    public static final int USERS_NO_SUCH_USER = 402;
    public static final int USERS_ALREADY_EXISTS = 403;
    public static final int USERS_UNAUTHORIZED = 404;

    public static final int TOKEN_INVALID = 502;

    public static final int RESOURCES_NOT_FOUND = 601;
    public static final int RESOURCES_UNAVAILABLE = 602;

    public static final int ORDERS_EMPTY = 701;
    public static final int ORDERS_FAILED_CREDIT_VERIFICATION = 702;

    public static final int PRODUCTS_NO_CATEGORIES = 801;

}
