package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Region;
import com.lctech.swm.domain.RoleMapping;
import com.lctech.swm.repository.RegionRepository;
import com.lctech.swm.repository.RoleMappingRepository;

@Service
public class RoleMappingService {

	@Autowired
	RoleMappingRepository roleMappingRepository;

	public List<RoleMapping> findByGroupRole(Long groupRole) {
		return (List<RoleMapping>) roleMappingRepository.findByGroupRoleAndStatus(groupRole,true);
	}
	public List<RoleMapping> findByGroup(Long groupRole) {
		return (List<RoleMapping>) roleMappingRepository.findByGroupRole(groupRole);
	}
	public void save(RoleMapping rm){
		roleMappingRepository.save(rm);
	}
	public Long nextval(){
		return roleMappingRepository.getnextval();
	}
}
