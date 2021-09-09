package com.yoni.javaworkshopprojectserver.service;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Yoni
 *
 *
 * This "service" is a fake service that takes the place
 * of verifying credit card information as this project
 * will not handle that.
 */
@Singleton
@LocalBean
public class TransactionService {

    public String verifyCrediCardInfo(String creditCardNum, Date cardExpiration, String cvv){
        // this method supposedly verifies the credit card information
        // (generally via a third party) and if successful returns a
        // token to be for the actual transaction
        return UUID.randomUUID().toString();
    }

    public void makeTransaction(String verificationToken, float amount){
        // this method supposedly charges the credit whose
        // info was stored when generating the token
    }

}
