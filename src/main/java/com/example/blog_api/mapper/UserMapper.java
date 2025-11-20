package com.example.blog_api.mapper;

import com.example.blog_api.dto.UserDTO;
import com.example.blog_api.entity.User;

/**
 * Manual mapper for User entity.
 * Converts User -> UserDTO (never exposes password).
 */
public class UserMapper {

    public static UserDTO toDTO(User user){
        if(user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getRole()
        );
    }
    public static User toEntity(UserDTO dto){
        if(dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setRole(dto.getRole());

        return user;
    }

}
