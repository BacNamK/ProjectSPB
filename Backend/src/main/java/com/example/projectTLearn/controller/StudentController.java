package com.example.projectTLearn.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.model.UserModel;
import com.example.projectTLearn.service.StudentService;
import com.example.projectTLearn.types.ApiResponse;
import com.example.projectTLearn.types.PageResponse;
import com.example.projectTLearn.types.RegisterRequest;

@RestController
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public ApiResponse<PageResponse<StudentModel>> getAllStudents(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return new ApiResponse<>(true, "Lấy danh sách sinh viên thành công",
                studentService.getAllStudents(page, size));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest request) {
        UserModel user = studentService.registerUser(request);

        return ResponseEntity.ok(Map.of(
                "message", "Add Success",
                "user", user.getName(),
                "studentCode", ((com.example.projectTLearn.model.StudentModel) user).getStudentCode()));
    }

    @GetMapping("/search")
    public ApiResponse<List<StudentModel>> searchStudents(@RequestParam Map<String, String> params) {
        if (params.size() != 1) {
            throw new IllegalArgumentException("Search phải có đúng một param");
        }

        Map.Entry<String, String> searchParam = params.entrySet().iterator().next();
        return new ApiResponse<>(true, "Tìm kiếm sinh viên thành công",
                studentService.searchByField(searchParam.getKey(), searchParam.getValue()));
    }

    @PatchMapping("/{studentCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> updateStudent(@PathVariable String studentCode,
            @RequestBody Map<String, Object> fields) {
        return new ApiResponse<>(true, "Cập nhật sinh viên thành công",
                studentService.updateStudentByStudentCode(studentCode, fields));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{studentCode}")
    public ApiResponse<String> deleteStudent(@PathVariable String studentCode) {
        return new ApiResponse<>(true, "Xóa sinh viên thành công",
                studentService.deleteStudentByStudentCode(studentCode));
    }
}
