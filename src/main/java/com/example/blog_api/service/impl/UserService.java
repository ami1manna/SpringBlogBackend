package com.example.blog_api.service.impl;

import com.example.blog_api.dto.user.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO registerUser(String name , String rawPassword , String role );

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();
}
