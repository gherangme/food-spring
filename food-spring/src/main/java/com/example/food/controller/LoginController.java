package com.example.food.controller;

import com.example.food.payload.ResponseData;
import com.example.food.utils.JwtUtilsHelpers;
import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;

    //permitAll()
    //authen()
    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @RequestParam String username,
            @RequestParam String password
    ) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        Gson gson = new Gson();
        String data = gson.toJson(authentication);

        System.out.println("Data: "+data);

        ResponseData responseData = new ResponseData();
        responseData.setData(jwtUtilsHelpers.generateToken(data));

        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup() {

        return new ResponseEntity<>("Hello signup", HttpStatus.OK);
    }

}
