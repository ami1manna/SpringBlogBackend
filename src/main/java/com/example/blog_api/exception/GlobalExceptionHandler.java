package com.example.blog_api.exception;

import com.example.blog_api.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Resource not found - 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException e) {

        ApiResponse<?> apiResponse = ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    // Handle Bad Request - 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException e) {

        ApiResponse<?> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
}
    // Generic Api Exception - 400
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException  e) {
        ApiResponse<?> apiResponse = ApiResponse.error( HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    // TODO: JWT
    // TODO: Validation Errors (@Valid)

    // GLobal Base Error - 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobal(Exception ex) {


        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                ex.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
