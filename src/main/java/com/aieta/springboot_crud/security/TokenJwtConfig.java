package com.aieta.springboot_crud.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Configuration
public class TokenJwtConfig {
    //public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    private static String secretKey;
    private static Long expirationTime;

    @Value("${jwt.secret}")
    private String secretKeyValue;

    @Value("${jwt.expirationtime}")
    private Long expirationTimeValue;

    @PostConstruct
    public void init() {
        secretKey = secretKeyValue;
        expirationTime = expirationTimeValue;
    }

    public static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public static Long getExpirationTime() {
        return expirationTime;
    }
}
