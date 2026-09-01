package com.example.projectTLearn.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectTLearn.Type.RegisterRequest;
import com.example.projectTLearn.Type.TokenResponse;
import com.example.projectTLearn.exception.InvalidCredentialsException;
import com.example.projectTLearn.exception.JwtException;
import com.example.projectTLearn.exception.UserNotFoundException;
import com.example.projectTLearn.model.SessionModel;
import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.model.UserModel;
import com.example.projectTLearn.repository.AuthRepository;
import com.example.projectTLearn.repository.SessionRepository;
import com.example.projectTLearn.security.util.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwt;

    public UserModel verifyUser(String studentCode, String password) {

        if (studentCode == null || studentCode.trim().isEmpty()) {
            throw new InvalidCredentialsException("STUDENT_CODE_NOT_EMPTY!");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new InvalidCredentialsException("PASSWORD_NOT_EMPTY");
        }

        UserModel user = authRepository.findByStudentCode(studentCode);

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

    public UserModel registerUser(RegisterRequest request) {
        if (request == null) {
            throw new InvalidCredentialsException("REQUEST_NOT_EMPTY");
        }

        if (request.getStudentCode() == null || request.getStudentCode().trim().isEmpty()) {
            throw new InvalidCredentialsException("STUDENT_CODE_NOT_EMPTY!");
        }

        if (request.getPassWord() == null || request.getPassWord().trim().isEmpty()) {
            throw new InvalidCredentialsException("PASSWORD_NOT_EMPTY");
        }

        if (authRepository.findByStudentCode(request.getStudentCode()) != null) {
            throw new InvalidCredentialsException("STUDENT_CODE_EXISTS");
        }

        StudentModel student = new StudentModel();
        student.setStudentCode(request.getStudentCode());
        student.setPasswordHash(passwordEncoder.encode(request.getPassWord()));
        student.setName(request.getName());
        student.setFull_name(request.getFullName());
        student.setPhone(request.getPhone());
        student.setRole(UserModel.Role.STUDENT);
        student.setStautus(UserModel.Stautus.ACTIVE);
        student.setGender(UserModel.Gender.valueOf(request.getGender().toUpperCase()));
        student.setClassId(1);
        student.setEnrollmentYear(LocalDate.now().getYear());
        student.setGpa(BigDecimal.valueOf(0.00));

        return authRepository.save(student);
    }

    public String refreshToken(String refreshToken) {

        if (refreshToken.trim().isEmpty()) {
            throw new JwtException("TOKEN_EMPTY!");
        }

        String authorization = refreshToken.trim();
        if (!authorization.regionMatches(true, 0, "Bearer ", 0, 7)) {
            throw new JwtException("TOKEN_INVALID-1!");
        }

        String token = authorization.substring(7).trim();

        if (jwt.validateRefreshToken(token) == false) {
            throw new JwtException("TOKEN_INVALID-2!");
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
