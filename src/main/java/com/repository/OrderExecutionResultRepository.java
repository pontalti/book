package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.OrderExecutionResultEntity;

public interface OrderExecutionResultRepository extends JpaRepository<OrderExecutionResultEntity, Long> {
	
	
}