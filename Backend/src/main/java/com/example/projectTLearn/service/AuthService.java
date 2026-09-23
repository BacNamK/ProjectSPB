package com.example.projectTLearn.service;

import java.sql.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectTLearn.exception.InvalidCredentialsException;
import com.example.projectTLearn.exception.JwtException;
import com.example.projectTLearn.exception.UserNotFoundException;
import com.example.projectTLearn.model.SessionModel;
import com.example.projectTLearn.model.UserModel;
import com.example.projectTLearn.repository.AuthRepository;
import com.example.projectTLearn.repository.SessionRepository;
import com.example.projectTLearn.types.TokenResponse;
import com.example.projectTLearn.util.JwtTokenProvider;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwt;

    public AuthService(AuthRepository authRepository, SessionRepository sessionRepository,
            PasswordEncoder passwordEncoder, JwtTokenProvider jwt) {
        this.authRepository = authRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    public UserModel verifyUser(String code, String password) {

        if (code == null || code.trim().isEmpty()) {
            throw new InvalidCredentialsException("USER_CODE_NOT_EMPTY!");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new InvalidCredentialsException("PASSWORD_NOT_EMPTY");
        }

        UserModel user;

        if (code.startsWith("AD")) {
            user = authRepository.findByCode(code);
        } else if (code.startsWith("SV")) {
            user = authRepository.findByStudentCode(code);
        } else if (code.startsWith("MD")) {
            user = authRepository.findByModeratorCode(code);
        } else {
            throw new InvalidCredentialsException("USER_CODE_INVALID");
        }

        if (user == null) {
            throw new UserNotFoundException("USER_NOT_FOUND");
        }

        boolean checkPassword = passwordEncoder.matches(password, user.getPasswordHash());

        if (!checkPassword) {
            throw new InvalidCredentialsException("PASSWORD_FAIL");
        }

        return user;
    }

    public TokenResponse endCodeJwtAndCreateSession(Long userId) {

        long SESSION_EXPIRED = 60L * 24 * 60 * 60 * 1000;

        UserModel user_id = authRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));

        String accessToken = jwt.generateAccessToken(String.valueOf(userId));

        String refreshToken = jwt.generateRefreshToken(String.valueOf(userId));

        SessionModel session = new SessionModel(user_id, refreshToken,
                new Date(System.currentTimeMillis() + SESSION_EXPIRED));
        sessionRepository.save(session);

        return new TokenResponse(accessToken, refreshToken);
    }

    public String refreshToken(String refreshToken) {

        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new JwtException("TOKEN_EMPTY!");
        }

        String token = refreshToken.trim();

        if (jwt.validateRefreshToken(token) == false) {
            throw new JwtException("TOKEN_INVALID!");
        }

        String userIdFromJwt = jwt.getUserFromJWT(token);

        if (userIdFromJwt == null || userIdFromJwt.isBlank()) {
            throw new JwtException("TOKEN_INVALID-3!");
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdFromJwt);
        } catch (NumberFormatException e) {
            throw new JwtException("TOKEN_INVALID-4!");
        }

        authRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));

        return jwt.generateAccessToken(String.valueOf(userId));
    }
}
