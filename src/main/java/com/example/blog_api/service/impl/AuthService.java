package com.example.blog_api.service.impl;

import com.example.blog_api.dto.auth.AuthResponse;
import com.example.blog_api.dto.auth.LoginRequest;
import com.example.blog_api.dto.auth.RefreshTokenRequest;
import com.example.blog_api.dto.user.UserDTO;

public interface AuthService {

    UserDTO registerViewer(String username, String password);

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refresh(RefreshTokenRequest req);

}
