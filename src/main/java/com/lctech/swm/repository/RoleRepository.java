package com.lctech.swm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lctech.swm.domain.Account;
import com.lctech.swm.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	public List<Role> findAll();
}
