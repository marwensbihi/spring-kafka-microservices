package com.angMetal.orders.controller;


import com.angMetal.orders.service.AuthService;
import com.angMetal.orders.payloads.request.LoginRequest;
import com.angMetal.orders.payloads.request.SignUpRequest;
import com.angMetal.orders.payloads.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping(value = "register")
    public ResponseEntity<Response> register(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authService.register(signUpRequest);
    }
}