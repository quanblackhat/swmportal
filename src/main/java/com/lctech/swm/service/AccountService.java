package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Account;
import com.lctech.swm.domain.Device;
import com.lctech.swm.domain.Province;
import com.lctech.swm.repository.AccountRepository;
import com.lctech.swm.repository.ProvinceRepository;

@Service
public class AccountService {
	@Autowired
	AccountRepository accountRepository;

	public Account checkUsernamePass(String username,String password){
		
		Account ac = accountRepository.findByUsernameAndPassword(username, password);
		
		return ac;
		
	}

	public Account findByUsername(String username){
		
		Account ac = accountRepository.findByUsername(username);
		return ac;
	}

	public void save(Account acc) {
	accountRepository.save(acc);
}

	public Page<Account> findAll(Pageable pageable) {
	return accountRepository.findAll(pageable);
}
}
