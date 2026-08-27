package com.example.projectTLearn.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class EnrollmentIdModel implements Serializable {

    private Long studentId;

    private Integer classId;

    public EnrollmentIdModel() {
    }

    public EnrollmentIdModel(Long studentId, Integer classId) {
        this.studentId = studentId;
        this.classId = classId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

}