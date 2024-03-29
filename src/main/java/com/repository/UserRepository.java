package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query("FROM UserEntity WHERE email = :email")
	public UserEntity findByEmail(@Param("email") String email);

}
