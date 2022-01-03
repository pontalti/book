package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.ExecutionOrderEntity;

public interface ExecutionOrderRepository extends JpaRepository<ExecutionOrderEntity, Long> {
		
}