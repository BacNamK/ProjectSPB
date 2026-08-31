package com.example.projectTLearn.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectTLearn.Type.LoginRequest;
import com.example.projectTLearn.Type.RegisterRequest;
import com.example.projectTLearn.Type.TokenResponse;
import com.example.projectTLearn.model.UserModel;
import com.example.projectTLearn.service.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest request) {
        UserModel user = authService.registerUser(request);

        return ResponseEntity.ok(Map.of(
                "message", "Add Success",
                "user", user.getName(),
                "studentCode", ((com.example.projectTLearn.model.StudentModel) user).getStudentCode()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        UserModel user = authService.verifyUser(request.getStudentCode(), request.getPassWord());

        TokenResponse tokenResponse = authService.endCodeJwtAndCreateSession(user.getId());

        return ResponseEntity.ok(Map.of(
                "message", "Login success",
                "user", user.getName(),
                "accessToken", tokenResponse.getAccessToken(),
                "refreshToken", tokenResponse.getRefreshToken()));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(Map.of("accessToken", authService.refreshToken(refreshToken)));
    }
}
