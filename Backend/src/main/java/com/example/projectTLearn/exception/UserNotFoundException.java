package com.example.projectTLearn.exception;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
