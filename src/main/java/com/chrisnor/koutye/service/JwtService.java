package com.chrisnor.koutye.service;

import java.util.function.Function;

import org.springframework.security.oauth2.jwt.Jwt;


public interface JwtService {
	//public String extractClaim(String token, Function<Claims, String> claimsResolver);

	String extractClaim(String token, Function<Jwt, String> claimsResolver);
}
