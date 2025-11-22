package com.example.blog_api.dto.user;

/*
 User DTO
 Returned in response; does NOT expose Password
 */

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing a user (password excluded)")
public class UserDTO {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "Full name of the user", example = "Amit Manna")
    private String name;

    @Schema(description = "User role", example = "ROLE_ADMIN")
    private String role;

    public UserDTO() {}

    public UserDTO(Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
