package com.example.projectTLearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectTLearn.model.SessionModel;

@Repository
public interface SessionRepository extends JpaRepository<SessionModel, Long> {
}
