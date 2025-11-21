package com.example.blog_api.security;

import com.example.blog_api.entity.User;
import com.example.blog_api.exception.ResourceNotFoundException;
import com.example.blog_api.respository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user = userRepository.findByName(username);
     if(user == null) {
         new ResourceNotFoundException("User not found with name: " + username);
     }

     return org.springframework.security.core.userdetails.User.builder()
             .username(user.getName())
             .password(user.getPassword())
             .roles(user.getRole().replace("ROLE_",""))
             .build();

    }
}
