/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Date;

/**
 *
 * @author Yoni
 */
public class JwtUtils {
    
    private JwtUtils(){}
    
    
    private static final String SECRET_KEY = "$ydKctHLiYqz{:da8PgppSc)n5=:WGGK+khd-,v5#e,4Q6tKRMT}Vn!vJ;yd";
    private static final int SECURITY_STRING_LENGTH = 64;
    private static final long DEFAULT_EXPIRATION_OFFSET = 5 * 60 * 1000; // 5 minutes
    
    public static String getEmail(String token){
        String subject = getSubject(token);
        return subject.substring(0, subject.length()-SECURITY_STRING_LENGTH);
    }
    
    public static String getSecurityString(String token){
        String subject = getSubject(token);
        return subject.substring(subject.length()-SECURITY_STRING_LENGTH);
    }
    
    private static String getSubject(String token){
        return getClaims(token).getSubject();
    }
    
    public static Date getExpiration(String token){
       return getClaims(token).getExpiration();
    }
    
    public static boolean isExpired(String token){
       return getClaims(token).getExpiration().before(new Date());
    }
    
    public static String create(String email, String securityString){
        return create(email, securityString, DEFAULT_EXPIRATION_OFFSET);
    }
    
    public static String create(String email, String securityString, long expirationOffsetMillis){
        if(securityString.length() != SECURITY_STRING_LENGTH){
            throw new IllegalArgumentException("extra string must be exactly 64 characters");
        }
        Date now = new Date();
        return Jwts
                .builder()
                .setSubject(email+securityString)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+expirationOffsetMillis))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    
    private static Claims getClaims(String token){
        try{
            return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        }
        catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }
    
}
