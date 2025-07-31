package com.project.util;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
public class auth {
	@Value("${jwt.secret}")
	private String jwt_key;
	private long expiration_time=24*7*1000 * 60 * 60;
	
	private final Key key=Keys.hmacShaKeyFor(jwt_key.getBytes());
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
	
	
}
