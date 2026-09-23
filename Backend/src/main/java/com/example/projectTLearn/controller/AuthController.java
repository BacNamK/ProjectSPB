package com.example.projectTLearn.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectTLearn.model.UserModel;
import com.example.projectTLearn.service.AuthService;
import com.example.projectTLearn.types.LoginRequest;
import com.example.projectTLearn.types.TokenResponse;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        UserModel user = authService.verifyUser(request.getCode(), request.getPassWord());

        TokenResponse tokenResponse = authService.endCodeJwtAndCreateSession(user.getId());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenResponse.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("lax")
                .path("/")
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
    public ResponseEntity<?> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        return ResponseEntity.ok(Map.of("accessToken", authService.refreshToken(refreshToken)));
    }
}
