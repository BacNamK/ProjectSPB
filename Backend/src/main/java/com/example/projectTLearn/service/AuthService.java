package com.example.projectTLearn.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public String addTenDemoUsers() {
        String[] names = {
                "Nguyen Van An",
                "Tran Thi Bich",
                "Le Van Cuong",
                "Pham Thi Duyen",
                "Hoang Minh Em",
                "Vo Thanh F",
                "Doan Thi Giao",
                "Bui Van Huy",
                "Nguyen Thi Lan",
                "Truong Minh Nam"
        };

        List<StudentModel> students = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String code = "SV" + String.format("%03d", i + 1);
            if (authRepository.findByStudentCode(code) != null) {
                continue;
            }

            StudentModel student = new StudentModel();
            student.setStudentCode(code);
            student.setName(names[i]);
            student.setFull_name(names[i]);
            student.setPasswordHash(passwordEncoder.encode("123456"));
            student.setPhone("0900000" + (i + 100));
            student.setRole(UserModel.Role.STUDENT);
            student.setStautus(UserModel.Stautus.ACTIVE);
            student.setGender(i % 2 == 0 ? UserModel.Gender.MALE : UserModel.Gender.FEMALE);
            student.setClassId(1);
            student.setEnrollmentYear(LocalDate.now().getYear());
            student.setGpa(BigDecimal.valueOf(3.00 + (i * 0.05)));
            students.add(student);
        }

        if (students.isEmpty()) {
            return "10 users already exist in database.";
        }

        authRepository.saveAll(students);
        return "Inserted " + students.size() + " users into database.";
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
