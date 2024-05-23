package com.example.practiceexamfinal.exception;

import com.example.practiceexamfinal.exception.errorResponse.ValidError;
import com.example.practiceexamfinal.exception.errorResponse.ValidResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return buildValidResponse(ex, httpStatus, request);
    }

    private ResponseEntity<ValidResponse> buildValidResponse(MethodArgumentNotValidException ex, HttpStatus httpStatus, WebRequest request) {
        String instance = request.getDescription(false);
        String reason = "Validation error. Check 'errors' field for details.";
        List<ValidError> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(new ValidError(error.getField(), error.getDefaultMessage()));
        });
        return new ResponseEntity<>(new ValidResponse(httpStatus.value(), reason, System.currentTimeMillis(), instance, errors), httpStatus);
    }
}
