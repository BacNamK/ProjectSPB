package com.example.projectTLearn.service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.projectTLearn.Type.PageResponse;
import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public PageResponse<StudentModel> getAllStudents(Integer page, Integer size) {
        int pageNumber = page != null ? page : 1;
        int pageSize = size != null ? size : 10;

        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<StudentModel> studentPage = studentRepository.findAll(pageRequest);

        List<StudentModel> students = studentPage.getContent();

        PageResponse<StudentModel> response = new PageResponse<>(
                students,
                studentPage.getNumber() + 1,
                studentPage.getSize(),
                studentPage.getTotalPages(),
                studentPage.getTotalElements());

        return response;
    }

    public List<StudentModel> searchByField(String url) {

        String[] parts = url.split("=", 2);
        String field = parts[0];
        String value = URLDecoder.decode(parts[1], StandardCharsets.UTF_8).trim();

        if ("name".equalsIgnoreCase(field) || "full_name".equalsIgnoreCase(field)) {
            return studentRepository.searchByNameLike(value);
        }

        return studentRepository.searchByField(field, value);
    }

    public String deleteStudentByStudentCode(String code) {
        StudentModel student = studentRepository.findStudentByStudentCode(code);

        if (student != null) {
            studentRepository.deleteById(student.getId());
            return "Student with code " + code + " has been deleted.";
        } else {
            return "Student with code " + code + " not found.";
        }
    }
}
