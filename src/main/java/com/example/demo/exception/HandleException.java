package com.example.demo.exception;

import com.example.demo.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleException {
    @ExceptionHandler(AppException.class)
    public ResponseEntity handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<String> response = new ApiResponse<>(errorCode.getCode(),errorCode.getMessage(), null);
        return ResponseEntity.status(errorCode.getCode()).body(response);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(CustomException e) {
        ApiResponse<String> response = new ApiResponse<>(e.getCode(),e.getMessage(),null);
        return ResponseEntity.status(e.getCode()).body(response);
    }
}
