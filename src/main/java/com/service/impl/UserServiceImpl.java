package com.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.UserEntity;
import com.repository.UserRepository;
import com.service.IService;

import net.minidev.json.JSONObject;

@Service
public class UserServiceImpl implements IService<UserEntity> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Collection<UserEntity> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public Optional<UserEntity> findById(Long id) {
		return this.userRepository.findById(id);
	}

	@Override
	public UserEntity saveOrUpdate(UserEntity t) {
		return this.userRepository.saveAndFlush(t);
	}

	@Override
	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		this.userRepository.deleteById(id);
		jsonObject.put("message", "User deleted successfully");
		return jsonObject.toString();
	}

}
