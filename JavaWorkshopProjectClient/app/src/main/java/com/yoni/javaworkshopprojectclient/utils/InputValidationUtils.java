package com.yoni.javaworkshopprojectclient.utils;

public class InputValidationUtils {

    private InputValidationUtils(){}

    // all regex patterns are courtesy of stackoverflow (with some adjustments)
    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String PHONE_PATTERN = "\\(?\\+?[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?";
    private static final String ADDRESS_PATTERN = "[A-Za-z0-9'\\.\\-\\s\\,]{3,}";
    private static final String NAME_PATTERN = "^[a-zA-Z ,.'-]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"; // at least 8 characters with at least one letter and one number
    private static final String CREDIT_CARD_NUM_PATTERN = "^[0-9]{8,12}$"; // simplified the credit card since we won't be using real ones
    private static final String CREDIT_CARD_CVV_PATTERN = "^[0-9]{3,4}$";
    private static final String TITLE_PATTERN = "[\\w\\[\\]`!@#$%\\^&*()={}:;<>+\\s'-]+";
    private static final String DESCRIPTION_PATTERN = "[\\w\\[\\]`!@#$%\\^&*()={}:;<>+\\s'-]*";

    public static boolean validateEmail(String email){
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean validatePhone(String phone){
        return phone.matches(PHONE_PATTERN);
    }

    public static boolean validateAddress(String address){
        return address.matches(ADDRESS_PATTERN);
    }

    public static boolean validateName(String name){
        return name.matches(NAME_PATTERN);
    }

    public static boolean validatePassword(String password){
        return password.matches(PASSWORD_PATTERN);
    }

    public static boolean validateCreditCardNum(String card){
        return card.matches(CREDIT_CARD_NUM_PATTERN);
    }

    public static boolean validateCreditCardCVV(String cvv){
        return cvv.matches(CREDIT_CARD_CVV_PATTERN);
    }

    public static boolean validateTitle(String title){
        return title.matches(TITLE_PATTERN);
    }
    public static boolean validateDesc(String desc){
        return desc.matches(DESCRIPTION_PATTERN);
    }



}
