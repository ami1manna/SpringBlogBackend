package com.example.blog_api.controller;

import com.example.blog_api.dto.api.ApiResponse;
import com.example.blog_api.dto.user.RegisterRequest;
import com.example.blog_api.dto.user.UserDTO;
import com.example.blog_api.service.impl.UserService;
import com.example.blog_api.utils.ResponseFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// for swagger
@Tag(name = "Users", description = "User management operations (Admin only)")
@SecurityRequirement(name = "bearerAuth")

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // register user
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody RegisterRequest registerRequest) {

        UserDTO userDTO = userService.registerUser(registerRequest.getName() , registerRequest.getPassword() , "ROLE_VIEWER");

        return ResponseFactory.created(userDTO , "User register successfully");
    }

    // fetch user data

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);

        return ResponseFactory.ok(userDTO , "User fetched successfully");

    }

    // get all users in database
    // admin
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {

        List<UserDTO> users = userService.getAllUsers();
        return ResponseFactory.ok(users , "User fetched successfully");

    }


}
