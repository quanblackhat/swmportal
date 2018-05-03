package com.lctech.swm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Account;
import com.lctech.swm.service.AccountService;

@Service
public class AppContext {

@Autowired
AccountService accountService;

	public Account getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return accountService.findByUsername(userName);
	}

}
