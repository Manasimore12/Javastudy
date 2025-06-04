package com.code.SellCar_Spring.utils;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	private Key getSigningKey() {
		String SECRET = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
		
	}
	
	private <T> T extractClaims(String token, Function<Claims, T> claimResolvers) {
		final Claims claims =extractAllClaims(token);
		return claimResolvers.apply(claims);
		
	}
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
		
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
	    final String username = extractUsername(token);
	    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();		
	}
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
}
