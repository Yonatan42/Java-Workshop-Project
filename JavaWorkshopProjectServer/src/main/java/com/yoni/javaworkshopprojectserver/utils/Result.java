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
   
    public static <TV, TE> Result<TV, TE> makeValue(TV value){
        Result<TV, TE> result = new Result(value, null);
        result.isValid = true;
        return result;
    }
    
    public static <TV, TE> Result<TV, TE> makeError(TE error){
        Result<TV, TE> result =  new Result(null, error);
        result.isValid = false;
        return result;
    }
    
    
    private final TV value;
    private final TE error;
    private boolean isValid;
    
    public Result(TV value, TE error){
        this.value = value;
        this.error = error;
    }
    
    public boolean isValid(){
        return isValid;
    }

    public TV getValue() {
        return value;
    }

    public TE getError() {
        return error;
    }
    
    
}