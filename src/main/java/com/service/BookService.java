package com.service;

public interface BookService{
	public <T, S> T create(S obj, Class<T> c);
	public <T> T findById(Long id, Class<T> c);
}
