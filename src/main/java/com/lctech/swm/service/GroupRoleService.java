package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.GroupRole;
import com.lctech.swm.repository.GroupRoleRepository;

@Service
public class GroupRoleService {

	@Autowired
	GroupRoleRepository grouproleRepository;
	public List<GroupRole> findAll(){
		return (List<GroupRole>) grouproleRepository.findAll();
	}
	public void save(GroupRole gr){
		grouproleRepository.save(gr) ;
	}
	public GroupRole findByName(String name){
		return grouproleRepository.findByName(name);
	}
	public GroupRole findById(Long id){
		return grouproleRepository.findById(id);
	}
}
