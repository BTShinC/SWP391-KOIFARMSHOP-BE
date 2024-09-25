package com.example.SWP391_KOIFARMSHOP_BE.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalExceptionHandler  {





    // Xử lý lỗi xác thực cho các thuộc tính trong thực thể
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Lấy các lỗi từ BindingResult
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        // Trả về ResponseEntity với mã trạng thái BAD_REQUEST
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Nên bắt cả Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        // Log lỗi ra và ẩn đi message thực sự (xem phần 3.2)
        e.printStackTrace();  // Thực tế người ta dùng logger
        return ResponseEntity.status(500).body("Unknown error");
    }
}
