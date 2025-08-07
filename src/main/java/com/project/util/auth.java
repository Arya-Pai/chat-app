package com.project.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.security.Key;
import java.util.Date;

@Component
public class auth {
	@Value("${jwt.secret}")
    private String jwtKey;

    private Key key;

    // Token valid for 7 days
    private final long expiration_time = 7 * 24 * 60 * 60 * 1000;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtKey.getBytes());
    }
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+expiration_time)).signWith(key,SignatureAlgorithm.HS256).compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
	}
	 public long getExpirationTime() {
	        return System.currentTimeMillis() + expiration_time;
	    }
	
	
}
