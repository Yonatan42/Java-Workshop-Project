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
public class Result<TV, TE>{
   
    public static <TV, TE> Result<TV, TE> MakeValue(TV value){
        return new Result(value, null);
    }
    
    public static <TV, TE> Result<TV, TE> MakeError(TE error){
        return new Result(null, error);
    }
    
    
    private final TV value;
    private final TE error;
    
    public Result(TV value, TE error){
        this.value = value;
        this.error = error;
    }
    
    public boolean isValid(){
        return value != null && error == null;
    }

    public TV getValue() {
        return value;
    }

    public TE getError() {
        return error;
    }
    
    
}