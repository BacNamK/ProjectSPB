package com.example.projectTLearn.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "lecturers")
@PrimaryKeyJoinColumn(name = "user_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class LecturersModel extends UserModel {

    private String lecturer_code;

    public String getLecturer_code() {
        return lecturer_code;
    }

    public void setLecturer_code(String lecturer_code) {
        this.lecturer_code = lecturer_code;
    }

    public LecturersModel() {
    }

    public LecturersModel(Long id, String name, String password_hash, String full_name, Gender gender, String phone,
            Role role, Stautus stautus, LocalDateTime crated_at, LocalDateTime updated_at, String lecturer_code,
            DepartmentsModel department) {
        super(id, name, password_hash, full_name, gender, phone, role, stautus, crated_at, updated_at);
        this.lecturer_code = lecturer_code;
        this.department = department;
    }

    // Tham chiếu đến bảng departments (department_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentsModel department; // Đặt tên thực thể tương ứng với bảng departments

}
