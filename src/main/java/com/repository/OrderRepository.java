package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	
}