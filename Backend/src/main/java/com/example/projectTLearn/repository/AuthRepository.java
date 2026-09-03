package com.example.projectTLearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projectTLearn.model.AdminModel;
import com.example.projectTLearn.model.ModeratorModel;
import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.model.UserModel;

@Repository
public interface AuthRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT s FROM StudentModel s WHERE s.studentCode = :studentCode")
    StudentModel findByStudentCode(@Param("studentCode") String studentCode);

    @Query("SELECT a FROM AdminModel a WHERE a.code = :code")
    AdminModel findByCode(@Param("code") String code);

    @Query("SELECT m FROM ModeratorModel m WHERE m.code = :code")
    ModeratorModel findByModeratorCode(@Param("code") String code);

}
