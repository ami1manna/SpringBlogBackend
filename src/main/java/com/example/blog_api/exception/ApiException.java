package com.example.blog_api.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
