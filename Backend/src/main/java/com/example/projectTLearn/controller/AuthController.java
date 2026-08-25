package com.example.projectTLearn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.service.AuthService;
import com.example.projectTLearn.typeRequest.loginRequest;

@CrossOrigin
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")

    public <T> ResponseEntity<T> register(@RequestBody StudentModel student) {

       StudentModel createdStudent = authService.createStudent(student.getName(), student.getAge(), student.getEmail(), student.isSex(),student.getPassword(), null);

       if (createdStudent == null){
        return null;
       }

        return (ResponseEntity<T>) ResponseEntity.status(200).body(createdStudent);
    }

    @PostMapping("/login")
    public <T> ResponseEntity login(@RequestBody loginRequest student) {

        String verify = authService.verifyStudent(student.getName(),student.getPassword());

        return ResponseEntity.status(200).body(verify);

    }
    
}
