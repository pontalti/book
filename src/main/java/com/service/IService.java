package com.service;

import java.util.Collection;
import java.util.Optional;

public interface IService<T> {
	public Collection<T> findAll();
	
	public Optional<T> findById(Long id);
	
	public T saveOrUpdate(T t);
	
	public String deleteById(Long id);
}
