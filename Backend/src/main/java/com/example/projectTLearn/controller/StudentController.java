package com.example.projectTLearn.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.service.StudentService;

@CrossOrigin
@Controller
@RequestMapping("/students")

public class StudentController {

    @Autowired
    public StudentService studentService;

    @GetMapping("/{id}")
    public <T> StudentModel getId(@PathVariable int id){

        return studentService.findById(id);
    };

    @RequestMapping("")
    public <T> ResponseEntity<StudentModel> getName(@RequestHeader String authorization, @RequestParam String name){

        StudentModel student = studentService.findByName(name);

        return ResponseEntity.status(200)
            .header("Authorization", authorization)
            .body(student);
    };

    @RequestMapping("/getAll")
    public String getAll(Model model) {
        model.addAttribute("students", studentService.returnAll());
        return "hello";
    }

    
}
