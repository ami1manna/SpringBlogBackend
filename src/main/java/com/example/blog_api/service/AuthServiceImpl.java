package com.example.blog_api.service;

import com.example.blog_api.dto.auth.AuthResponse;
import com.example.blog_api.dto.auth.LoginRequest;
import com.example.blog_api.dto.auth.RefreshTokenRequest;
import com.example.blog_api.dto.user.UserDTO;
import com.example.blog_api.entity.User;
import com.example.blog_api.exception.ApiException;
import com.example.blog_api.mapper.UserMapper;
import com.example.blog_api.respository.UserRepository;
import com.example.blog_api.security.JwtUtil;
import com.example.blog_api.service.impl.AuthService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager , JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }



    @Override
    public UserDTO registerViewer(String username, String password) {
        if(userRepository.existsByName(username)){
            throw new ApiException("User name already exists");
        }

    User user = new User(username, passwordEncoder.encode(password),"ROLE_VIEWER");
        System.out.println(user);
        userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getName(),
                        loginRequest.getPassword()
                )
        );

        String accessToken = jwtUtil.generateToken(loginRequest.getName());
        String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getName());

        return new AuthResponse(accessToken , refreshToken);

    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest req) {

        if(!jwtUtil.validateToken(req.getRefreshToken())){
            throw new ApiException("Invalid refresh token");
        }

        // fetch username from valid token
        String username = jwtUtil.extractUsername(req.getRefreshToken());
        // create new token from current time to expiration time
        String newAccessToken = jwtUtil.generateToken(req.getRefreshToken());

        return new AuthResponse(newAccessToken, req.getRefreshToken());
    }
}
