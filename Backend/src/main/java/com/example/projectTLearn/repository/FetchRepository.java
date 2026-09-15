package com.example.projectTLearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.projectTLearn.model.UserModel;

public interface FetchRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT u FROM UserModel u WHERE u.id = :id")
    UserModel fetchMe(@Param("id") Long id);
}
