package com.example.projectTLearn.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectTLearn.model.StudentModel;

public interface StudentRepository extends JpaRepository<StudentModel,Long> {
    StudentModel findById(int id);

    StudentModel findByName(String name);

    List<StudentModel> findAll();
} 
    
