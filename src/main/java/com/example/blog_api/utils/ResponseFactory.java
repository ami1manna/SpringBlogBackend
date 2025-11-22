package com.example.blog_api.utils;

import com.example.blog_api.dto.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    // ok
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        return ResponseEntity.ok(new ApiResponse<>(200, message, data));
    }

    // created
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return new ResponseEntity<>(new ApiResponse<>(201, message, data), HttpStatus.CREATED);
    }

    // notFound
    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return new ResponseEntity<>(new ApiResponse<>(404, message, null), HttpStatus.NOT_FOUND);
    }
}