package com.example.projectTLearn.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectTLearn.Type.PageResponse;
import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public PageResponse<StudentModel> getAllStudents(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return studentService.getAllStudents(page, size);
    }

    @GetMapping("/search")
    public List<StudentModel> searchStudents(HttpServletRequest request) {

        String fullUrl = request.getRequestURL().toString();
        String query = request.getQueryString();

        return studentService.searchByField(query);
    }

    @DeleteMapping("/delete/{studentCode}")
    public String deleteStudent(@PathVariable String studentCode) {
        return studentService.deleteStudentByStudentCode(studentCode);
    }
}
