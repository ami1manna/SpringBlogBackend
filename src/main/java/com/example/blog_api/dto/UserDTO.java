package com.example.blog_api.dto;

/*
 User DTO
 Returned in response; does NOT expose Password
 */

public class UserDTO {

    private Long id;
    private String name;
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
