package com.aieta.springboot_crud.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@SuppressWarnings("null")
public class SecurityHeadersInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'; object-src 'none';");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
    }
}
