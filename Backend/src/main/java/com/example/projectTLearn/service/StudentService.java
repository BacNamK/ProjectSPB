package com.example.projectTLearn.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tools.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectTLearn.Type.PageResponse;
import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.model.UserModel;
import com.example.projectTLearn.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, ObjectMapper objectMapper,
            PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
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

    public List<StudentModel> searchByField(String field, String value) {
        value = value.trim();
        if ("name".equalsIgnoreCase(field) || "full_name".equalsIgnoreCase(field)) {
            return studentRepository.searchByNameLike(value);
        }

        return studentRepository.searchByField(field, value);
    }

    public List<StudentModel> addStudents() {
        return addStudents(100);
    }

    public List<StudentModel> addStudents(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Số lượng sinh viên phải lớn hơn 0");
        }

        List<StudentModel> students = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();

        for (int i = 1; i <= count; i++) {
            StudentModel student = new StudentModel();
            String code = String.format("SV%03d", i);
            student.setStudentCode(code);
            student.setPasswordHash(passwordEncoder.encode("123456"));
            student.setName("Sinh viên " + i);
            student.setEmail(code.toLowerCase() + "@example.com");
            student.setFull_name("Sinh viên " + i);
            student.setPhone("090000" + String.format("%04d", i));
            student.setRole(UserModel.Role.STUDENT);
            student.setStautus(UserModel.Stautus.ACTIVE);
            student.setGender(i % 2 == 0 ? UserModel.Gender.FEMALE : UserModel.Gender.MALE);
            student.setClassId(1);
            student.setEnrollmentYear(currentYear);
            student.setGpa(BigDecimal.valueOf(0.00));
            students.add(student);
        }

        return studentRepository.saveAll(students);
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

    public String updateStudentByStudentCode(String code, Map<String, Object> fields) {
        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("Body phải chứa ít nhất một field");
        }

        StudentModel student = studentRepository.findStudentByStudentCode(code);
        if (student == null) {
            return "Student with code " + code + " not found.";
        }

        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            updateField(student, entry.getKey(), entry.getValue());
        }

        studentRepository.save(student);
        return "Student with code " + code + " has been updated.";
    }

    private void updateField(StudentModel student, String field, Object value) {
        String normalizedField = field == null ? "" : field.trim();

        switch (normalizedField) {
            case "name" -> student.setName(convert(value, String.class));
            case "email" -> student.setEmail(convert(value, String.class));
            case "full_name", "fullName" -> student.setFull_name(convert(value, String.class));
            case "gender" -> student.setGender(convert(value, StudentModel.Gender.class));
            case "phone" -> student.setPhone(convert(value, String.class));
            case "role" -> student.setRole(convert(value, StudentModel.Role.class));
            case "status", "stautus" -> student.setStautus(convert(value, StudentModel.Stautus.class));
            case "classId" -> student.setClassId(convert(value, Integer.class));
            case "enrollmentYear" -> student.setEnrollmentYear(convert(value, Integer.class));
            case "gpa" -> student.setGpa(convert(value, java.math.BigDecimal.class));
            default -> throw new IllegalArgumentException("Field không hợp lệ hoặc không thể cập nhật: " + field);
        }
    }

    private <T> T convert(Object value, Class<T> targetType) {
        return value == null ? null : objectMapper.convertValue(value, targetType);
    }
}
