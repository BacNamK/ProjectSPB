package com.example.projectTLearn.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_classes")
public class CourseClassModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khớp với kiểu SERIAL tự tăng
    private Integer id;

    // Tham chiếu đến bảng courses (course_id)
    // ON DELETE CASCADE được xử lý phía DB, nhưng bạn có thể thêm CascadeType nếu
    // muốn Hibernate xử lý
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CoursesModel course;

    // Tham chiếu đến bảng lecturers (lecturer_id)
    // Cột này có thể NULL (do có ON DELETE SET NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id", nullable = true)
    private LecturersModel lecturer;

    @Column(name = "semester", length = 10, nullable = false)
    private String semester;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Column(name = "academic_year", length = 10, nullable = false)
    private String academicYear;

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public CourseClassModel() {
    }

    public CourseClassModel(Integer id, CoursesModel course, LecturersModel lecturer, String semester,
            String academicYear) {
        this.id = id;
        this.course = course;
        this.lecturer = lecturer;
        this.semester = semester;
        this.academicYear = academicYear;
    }

}
