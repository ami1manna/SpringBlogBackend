package com.example.blog_api.controller;

import com.example.blog_api.dto.api.ApiResponse;
import com.example.blog_api.dto.auth.AuthResponse;
import com.example.blog_api.dto.auth.LoginRequest;
import com.example.blog_api.dto.auth.RefreshTokenRequest;
import com.example.blog_api.dto.user.UserDTO;
import com.example.blog_api.service.AuthServiceImpl;
import com.example.blog_api.service.impl.AuthService;
import com.example.blog_api.utils.ResponseFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "User registration, login & token refresh")

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private  AuthService authService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody LoginRequest req) {


        UserDTO user = authService.registerViewer(req.getName(), req.getPassword());

        return ResponseFactory.created(user , "User Created");


    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest req) {

        AuthResponse tokens = authService.login(req);

        return ResponseFactory.ok(tokens , "Login successful");

    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestBody RefreshTokenRequest req) {

        AuthResponse tokens = authService.refresh(req);

        return ResponseFactory.ok(tokens , "Token refreshed");
    }



}
