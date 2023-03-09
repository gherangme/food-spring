package com.example.food.security;

import com.example.food.utils.JwtUtilsHelpers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class CustomFilterJwt extends OncePerRequestFilter {

    private Gson gson = new Gson();

    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            boolean isSuccess = jwtUtilsHelpers.verifyToken(jwt);

            if(isSuccess) {
                String data = jwtUtilsHelpers.getDataFromToken(jwt);

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());// new ArrayList<>() sẽ lưu ds role của người dùng
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(token);

                System.out.println("Hello filter: "+data);

            }

        }catch (Exception e) {

        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
