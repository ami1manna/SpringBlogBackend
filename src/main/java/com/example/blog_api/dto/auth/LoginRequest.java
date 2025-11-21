package com.example.blog_api.dto.auth;

public class LoginRequest {
    // need name and pass
    private String name;
    private String password;

    public LoginRequest() {}
    public LoginRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
