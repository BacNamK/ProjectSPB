package com.example.projectTLearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projectTLearn.model.SessionModel;

public interface SessionRepository extends JpaRepository<SessionModel, Long> {
}
