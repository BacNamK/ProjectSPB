package com.example.projectTLearn.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.repository.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public StudentModel createStudent(String name, int age , String email, boolean sex, String password, String role) {
        StudentModel newStudent = new StudentModel( name, age, email, sex, password,role="USER");

        newStudent.setName(name);
        newStudent.setAge(age);
        newStudent.setPassword(passwordEncoder.encode(password));
        newStudent.setRole(role);

        return authRepository.save(newStudent);
    };

    public <T> String verifyStudent(String name , String password ){

        StudentModel studentOp = authRepository.findByName(name); 

        return password;
    };
    
}
