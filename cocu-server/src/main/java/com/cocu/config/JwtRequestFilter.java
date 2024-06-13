package com.cocu.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtRequestFilter extends OncePerRequestFilter {

    private final String secretKey = "COCU_RYAN"; // Use the same key as in the login system

    // List of endpoints to exclude from JWT token validation
    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/register",
            "/login",
            "/logout",
            "/imageCode",
            "/sendTemplateMail",
            "/checkEmailCode",
            "/forgetPwd",
            "/upload",
            "/anotherEndpointToExclude"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Check if the request URI is in the exclude list
        if (EXCLUDE_URLS.stream().anyMatch(requestURI::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token)
                        .getBody();

                // You can add claims to the request attributes if needed
                request.setAttribute("claims", claims);
            } catch (SignatureException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or invalid");
            return;
        }

        chain.doFilter(request, response);
    }
}
