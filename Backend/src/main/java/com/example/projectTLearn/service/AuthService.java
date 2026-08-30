package com.example.projectTLearn.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectTLearn.Type.RegisterRequest;
import com.example.projectTLearn.config.JwtTokenProvider;
import com.example.projectTLearn.exception.InvalidCredentialsException;
import com.example.projectTLearn.exception.JwtException;
import com.example.projectTLearn.exception.UserNotFoundException;
import com.example.projectTLearn.model.SessionModel;
import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.model.UserModel;
import com.example.projectTLearn.repository.AuthRepository;
import com.example.projectTLearn.repository.SessionRepository;

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

    public String endCodeJwtAndCreateSession(Long userId) {

        int SESSION_EXPIRED = 30 * 24 * 60 * 60;

        UserModel user_id = authRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));

        String refreshToken = jwt.generateToken(String.valueOf(userId));

        SessionModel session = new SessionModel(user_id, refreshToken,
                new Date(System.currentTimeMillis() + SESSION_EXPIRED * 1000L));
        sessionRepository.save(session);

        return refreshToken;
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

    public String ComfirmToken(String token) {

        boolean verify = jwt.validateToken(token);

        if (!verify) {
            throw new JwtException("TOKEN_INVALID!");
        }

        String userIdFromJwt = jwt.getUserFromJWT(token);

        if (userIdFromJwt == null || userIdFromJwt.trim().isEmpty()) {
            throw new JwtException("TOKEN_INVALID!");
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdFromJwt);
        } catch (NumberFormatException e) {
            throw new JwtException("TOKEN_INVALID!");
        }

        authRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));

        return jwt.generateToken(String.valueOf(userId));
    }
}
