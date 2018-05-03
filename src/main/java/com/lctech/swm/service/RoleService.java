package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Region;
import com.lctech.swm.domain.Role;
import com.lctech.swm.domain.RoleMapping;
import com.lctech.swm.repository.RegionRepository;
import com.lctech.swm.repository.RoleMappingRepository;
import com.lctech.swm.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	
	public List<Role> findAll(){
		return (List<Role>) roleRepository.findAll();
	}
	
}
