package com.marktech.task_management_system.config.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    Logger log = LoggerFactory.getLogger(JwtService.class);

    private String Secret = "11416371afya6381391931563171839131084tquetuq";

    /**
     * This method will generate JWT Token with 10 minute
     * Expiration time
    */
    public String generateToken(String user) {
        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user)
                .setIssuedAt(new Date())
                .setExpiration(new Date( System.currentTimeMillis() + 1000*60*10) )
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    /**
     * This method is used to extract claims
     */
    public Claims extractAllClaims(String token){
        Claims claims = null;
       try {
           claims = Jwts.parser()
                   .setSigningKey(getKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
       }catch (ExpiredJwtException exception){
           log.info(exception.getMessage());
       }
       return  claims;
    }

    public String extractUserName(String token) {
        Claims claims = extractAllClaims(token);
        if(claims != null){
             return claims.getSubject();
        }
        return  null;
    }
    public boolean validateToken(String token, UserDetails details) {
        String user = extractUserName(token);
        Boolean isExpire = extractAllClaims(token).getExpiration().before(new Date());
        return ( user.equals(details.getUsername()) && !isExpire );
    }


    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(Secret));
    }


}
