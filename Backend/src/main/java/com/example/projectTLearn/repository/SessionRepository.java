package com.example.projectTLearn.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.projectTLearn.model.SessionModel;

public interface SessionRepository extends JpaRepository<SessionModel, Long> {

	@Modifying
	@Transactional
	@Query("delete from SessionModel s where s.user_id.id = :userId")
	void deleteByUserId(@Param("userId") Long userId);
}
