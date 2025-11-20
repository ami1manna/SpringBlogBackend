package com.example.blog_api.controller;

import com.example.blog_api.dto.ApiResponse;
import com.example.blog_api.dto.UserDTO;
import com.example.blog_api.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // register user
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestParam String name ,
                                                         @RequestParam String password) {

        UserDTO userDTO = userService.registerUser(name , password , "ROLE_VIEWER");

        ApiResponse<UserDTO> res = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "User register succesfully",
                userDTO
        );

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    // fetch user data

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);

        ApiResponse<UserDTO> res = new ApiResponse<>(HttpStatus.OK.value() , "User fetched successfully" ,userDTO );

        return new ResponseEntity<>(res , HttpStatus.OK);
    }

    // get all users in database
    // admin
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {

        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value() , "User fetched successfully" ,users)
        );
    }


}
