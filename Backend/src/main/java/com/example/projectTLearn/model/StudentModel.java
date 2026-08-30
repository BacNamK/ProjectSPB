package com.example.projectTLearn.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "user_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class StudentModel extends UserModel {

    @Column(name = "student_code", length = 20, unique = true, nullable = false)
    private String studentCode;

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    @Column(name = "class_id")
    private Integer classId;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    // Tham chiếu đến bảng departments (department_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentsModel department; // Đặt tên thực thể tương ứng với bảng departments

    public DepartmentsModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentsModel department) {
        this.department = department;
    }

    @Column(name = "enrollment_year", nullable = false)
    private Integer enrollmentYear;

    public Integer getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(Integer enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    @Column(name = "gpa", precision = 3, scale = 2)
    private BigDecimal gpa; // Kiểu NUMERIC trong SQL thường map với BigDecimal trong Java

    public BigDecimal getGpa() {
        return gpa;
    }

    public void setGpa(BigDecimal gpa) {
        this.gpa = gpa;
    }

}
