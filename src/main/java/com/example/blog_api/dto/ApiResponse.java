package com.example.blog_api.dto;

/**
 * Generic API response wrapper used across all controllers.
 */
public class ApiResponse<T> {
    private int status;
    private T data;
    private String message;

    public ApiResponse() {}

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data;}

    }
