/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Date;

/**
 *
 * @author Yoni
 */
public class JwtUtil {
    private static final String SECRET_KEY = "$ydKctHLiYqz{:da8PgppSc)n5=:WGGK+khd-,v5#e,4Q6tKRMT}Vn!vJ;yd";
    private static final long DEFAULT_EXPIRATION_OFFSET = 10 * 60 * 60 * 1000;// 10 minutes 
    
    public static String getEmail(String token){
       return getClaims(token).getSubject();
    }
    
    public static Date getExpiration(String token){
       return getClaims(token).getExpiration();
    }
    
    public static boolean isExpired(String token){
       return getClaims(token).getExpiration().before(new Date());
    }
    
    public static String create(String email){
        return create(email, DEFAULT_EXPIRATION_OFFSET);
    }
    
    public static String create(String email, long expirationOffsetMillis){
        Date now = new Date();
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+expirationOffsetMillis))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    
    public static boolean isValid(String token, String email){
        try{
            return !isExpired(token) && getEmail(token).equals(email);
        }
        catch(SignatureException e){
            e.printStackTrace(System.err);
            return false;
        }
    }
    
    private static Claims getClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    
}
