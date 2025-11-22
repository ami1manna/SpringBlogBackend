package com.example.blog_api.utils;

import com.example.blog_api.dto.api.ApiResponse;
import com.example.blog_api.dto.api.PaginatedResponse;
import org.springframework.data.domain.Page;
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

    public static <T> ResponseEntity<ApiResponse<T>> error(int status, String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(status, message, data);
        return new ResponseEntity<>(body, HttpStatus.valueOf(status));
    }

    // For Paginated Response
    public static <T> ResponseEntity<ApiResponse<PaginatedResponse<T>>> paginated(Page<T> page, String message) {
        //user define class
        PaginatedResponse<T> payload = new PaginatedResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );

        ApiResponse<PaginatedResponse<T>> body = new ApiResponse<>(200, message, payload);
        return ResponseEntity.ok(body);
    }

}