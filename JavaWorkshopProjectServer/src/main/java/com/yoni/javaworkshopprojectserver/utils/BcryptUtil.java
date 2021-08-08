/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Yoni
 */
public class BcryptUtil {
    
    public static String encrypt(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public static boolean checkEq(String password, String hashed){
        return BCrypt.checkpw(password, hashed);
    }
}
