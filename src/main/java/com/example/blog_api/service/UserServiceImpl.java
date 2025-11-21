package com.example.blog_api.service;

import com.example.blog_api.dto.user.UserDTO;
import com.example.blog_api.entity.User;
import com.example.blog_api.exception.ResourceNotFoundException;
import com.example.blog_api.mapper.UserMapper;
import com.example.blog_api.respository.UserRepository;
import com.example.blog_api.service.impl.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    // User Repository for this
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerUser(String name, String rawPassword, String role) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(name , encodedPassword , role);
        user = userRepository.save(user);

        return UserMapper.toDTO(user);
    }


    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return UserMapper.toDTO(user);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
