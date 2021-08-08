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
public class ResponseErrorCodes {
  public static final int UNKNOWN = 100;
  public static final int PAGE_NOT_FOUND = 200;

  public static final int PERSISTENCE_GENERAL = 300;

  public static final int LOGIN_GENERAL = 400;
  public static final int LOGIN_PASSWORD_MISSMATCH = 401;
  public static final int LOGIN_NO_SUCH_EMAIL = 402;

}