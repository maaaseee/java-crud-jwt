package com.aieta.springboot_crud.security.filter;

import static com.aieta.springboot_crud.security.TokenJwtConfig.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.aieta.springboot_crud.security.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        String token = validateHeader(request, response, chain);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = Jwts.parser()
                                .verifyWith(getSecretKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();
            
            String username = claims.getSubject();
            //String secondUsername = (String) claims.get("username");
            Object authoritiesClaims = claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                    new ObjectMapper().addMixIn(
                        SimpleGrantedAuthority.class, 
                        SimpleGrantedAuthorityJsonCreator.class
                    ).readValue(
                        authoritiesClaims.toString().getBytes(),
                        SimpleGrantedAuthority[].class
                    )
                );

            UsernamePasswordAuthenticationToken jwt = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(jwt);
            chain.doFilter(request, response);
        } 
        catch (ExpiredJwtException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", ex.getMessage());
            body.put("message", "El token JWT ha caducado");

            setResponseForException(response, body);
        } 
        catch (MalformedJwtException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", ex.getMessage());
            body.put("message", "El formato del token JWT no es válido.");

            setResponseForException(response, body);
        }
        catch (JwtException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", ex.getMessage());
            body.put("message", "El token JWT no es válido");

            setResponseForException(response, body);
        }
    }

    private String validateHeader(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            return null;
        }
        
        return header.replace(PREFIX_TOKEN, "");
    }

    private void setResponseForException(HttpServletResponse response, Map<String, String> body) 
            throws JsonProcessingException, IOException {
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(CONTENT_TYPE);
    }
}
