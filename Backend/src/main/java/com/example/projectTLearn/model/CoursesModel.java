package com.example.projectTLearn.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class CoursesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Long credis;

    public CoursesModel() {
    }

    public CoursesModel(long id, String code, String name, Long credis) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credis = credis;
    }

    public Long getCredis() {
        return credis;
    }

    public void setCredis(Long credis) {
        this.credis = credis;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentsModel department; // Đặt tên thực thể tương ứng với bảng departments

}
