package com.example.projectTLearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectTLearn.model.UserModel;

@Repository
public interface AuthRepository extends JpaRepository<UserModel, Long> {

    UserModel findByName(String name);

    UserModel save(UserModel newStudent);

}
