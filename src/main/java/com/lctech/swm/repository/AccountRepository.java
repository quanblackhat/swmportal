package com.lctech.swm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lctech.swm.domain.Account;
import com.lctech.swm.domain.Device;

public interface AccountRepository extends CrudRepository<Account, Long> {

	Account findByUsername(String username);
	Account findByUsernameAndPassword(String username , String password);
	Page<Account> findAll(Pageable pageable);
}
