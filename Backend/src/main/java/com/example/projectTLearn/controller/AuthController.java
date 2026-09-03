package com.example.projectTLearn.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

        UserModel user = authService.verifyUser(request.getCode(), request.getPassWord());

        TokenResponse tokenResponse = authService.endCodeJwtAndCreateSession(user.getId());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/api/auth")
                .maxAge(Duration.ofDays(60))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of(
                        "message", "Login Success",
                        "accessToken", tokenResponse.getAccessToken(),
                        "user", Map.of("name", user.getName(), "Code", request.getCode(),
                                "role", user.getRole())));

    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(Map.of("accessToken", authService.refreshToken(refreshToken)));
    }
}
