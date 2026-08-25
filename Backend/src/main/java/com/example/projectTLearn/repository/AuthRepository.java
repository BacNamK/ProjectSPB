package com.example.projectTLearn.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectTLearn.model.StudentModel;


@Repository
public interface AuthRepository extends JpaRepository<StudentModel, Long> {

    StudentModel findByName(String name);

    StudentModel save(StudentModel newStudent);

}
