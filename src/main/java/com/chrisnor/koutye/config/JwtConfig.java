package com.chrisnor.koutye.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.chrisnor.koutye.service.JwtService;
import com.chrisnor.koutye.service.serviceimpl.JwtServiceImpl;

import org.springframework.context.annotation.Bean;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


@Configuration
public class JwtConfig {
	@Value("${jwt.secret}")
    private String secret;
	
	@Autowired
    private JwtServiceImpl jwtService;
	/*
    @Bean
    public SecretKey jwtSecretKey() {
    	return new SecretKeySpec(jwtService.generateKey().getBytes(), "HmacSHA512");
    }
    */
}
