package com.example.blog_api.security;

import com.example.blog_api.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

// For Generating and validating tokens
@Component
public class JwtUtil {

    @Value("${security.jwt.secret-key}")
    private  String JWT_SECRET;
    @Value("${security.jwt.expiration-time}")
    private final long ACCESS_EXPIRATION = 1000 * 60 * 15; // 1 hour
    private final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    // Generate Access Token
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(getSigningKey() , SignatureAlgorithm.HS256)
                .compact();
    }

    // Generate Refresh Token
    public String generateRefreshToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(getSigningKey() , SignatureAlgorithm.HS256)
                .compact();


    }

    // Extract username
    public String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    // Validate Token - Just check whether token is expired or not
    public boolean validateToken(String token){

        try{
            extractUsername(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


}
