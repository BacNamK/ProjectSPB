package com.example.projectTLearn.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrollments")
public class EnrollmentModel {

    @EmbeddedId
    private EnrollmentIdModel id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private StudentModel student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("classId")
    @JoinColumn(name = "class_id")
    private CourseClassModel courseClass;

    @Column(name = "midterm_score")
    private Double midtermScore;

    @Column(name = "final_score")
    private Double finalScore;

    @Column(name = "total_score", insertable = false, updatable = false)
    private Double totalScore;

    public EnrollmentModel() {
    }

    public EnrollmentModel(EnrollmentIdModel id, StudentModel student, CourseClassModel courseClass,
            Double midtermScore, Double finalScore, Double totalScore) {
        this.id = id;
        this.student = student;
        this.courseClass = courseClass;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
        this.totalScore = totalScore;
    }

}