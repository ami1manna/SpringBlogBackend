package com.example.blog_api.dto;

// RegisterRequest.java
public class RegisterRequest {
    private String name;
    private String password;

    // Getters and Setters needed for Jackson to parse JSON
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


}