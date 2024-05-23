package com.example.practiceexamfinal.exception.errorResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ValidError {
    private final String field;
    private final String message;
}
