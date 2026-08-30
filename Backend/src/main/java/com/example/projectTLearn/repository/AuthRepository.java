package com.example.projectTLearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.model.UserModel;

@Repository
public interface AuthRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT s FROM StudentModel s WHERE s.studentCode = :studentCode")
    StudentModel findByStudentCode(@Param("studentCode") String studentCode);

}
