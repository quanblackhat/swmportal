
package com.lctech.swm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lctech.swm.repository.UsersRepository;

@Controller	
public class AdminController {
	final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping(value = {"/admin"})
	public String admin() {
		
		return "admin/index";
	}
}



