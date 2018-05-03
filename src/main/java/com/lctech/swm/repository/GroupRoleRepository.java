package com.lctech.swm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lctech.swm.domain.GroupRole;

public interface GroupRoleRepository extends CrudRepository<GroupRole, Long> {
	
	GroupRole findById(Long id);
	public List<GroupRole> findAll();
	GroupRole findByName(String name);
}
