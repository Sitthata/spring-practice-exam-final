package com.example.practiceexamfinal.exception.errorResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ValidResponse {
    private final int status;
    private final String message;
    private final long timestamp;
    private final String instance;
    private final List<ValidError> errors;
}
