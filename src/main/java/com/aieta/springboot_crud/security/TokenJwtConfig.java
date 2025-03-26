package com.aieta.springboot_crud.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class TokenJwtConfig {
    //public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    private static String secretKey;

    @Value("${jwt.expirationtime}")
    public static Long expirationTime;

    @Value("${jwt.secret}")
    public void secretKey(String secretKey) {
        TokenJwtConfig.secretKey = secretKey;
    }

    public static SecretKey getSecretKey() {
        //byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        //return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
