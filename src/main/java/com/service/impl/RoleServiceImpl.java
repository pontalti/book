package com.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.RoleEntity;
import com.repository.RoleRepository;
import com.service.IService;

import net.minidev.json.JSONObject;

@Service
public class RoleServiceImpl implements IService<RoleEntity> {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Collection<RoleEntity> findAll() {
		return this.roleRepository.findAll();
	}

	@Override
	public Optional<RoleEntity> findById(Long id) {
		return this.roleRepository.findById(id);
	}

	@Override
	public RoleEntity saveOrUpdate(RoleEntity role) {
		return this.roleRepository.saveAndFlush(role);
	}

	@Override
	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		this.roleRepository.deleteById(id);
		jsonObject.put("message", "Role deleted successfully");
		return jsonObject.toString();
	}

}
