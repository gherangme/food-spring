package com.example.food.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtilsHelpers {

    @Value("${jwt.privateKey}")
    private String privateKey;

    private long expiredTime = 8 * 60 * 60 * 1000;
//    private long expiredTime = 20 * 1000;

    public String generateToken(String data) {
        System.out.println("Kiem tra: "+privateKey);

        SecretKey key = Keys.hmacShaKeyFor(privateKey.getBytes());
        Date date = new Date();
        long currentDateMilis = date.getTime() + expiredTime;
        Date expiredDate = new Date(currentDateMilis);

        String jwt = Jwts.builder()
                .setSubject(data)
                .signWith(key)
                .setExpiration(expiredDate)
                .compact();

        System.out.println("Token: "+jwt);
        return jwt;
    }

    public boolean verifyToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(privateKey.getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public String getDataFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(privateKey.getBytes());
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e) {
            return "";
        }
    }

}
