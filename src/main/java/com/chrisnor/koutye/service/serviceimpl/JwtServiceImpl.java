package com.chrisnor.koutye.service.serviceimpl;

import java.util.Base64;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

import com.chrisnor.koutye.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JwtServiceImpl implements JwtService{
	
    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public String extractClaim(String token, Function<Jwt, String> claimsResolver) {
        Jwt decodedJwt = jwtDecoder.decode(token);
        return claimsResolver.apply(decodedJwt);
    }	
}
