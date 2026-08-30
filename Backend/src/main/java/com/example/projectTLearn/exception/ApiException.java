package com.example.projectTLearn.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
