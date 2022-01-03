package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
		
}