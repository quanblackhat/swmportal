package com.lctech.swm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lctech.swm.domain.Users;
import com.lctech.swm.repository.UsersRepository;

@Controller
@RequestMapping(value= {"/","/frontend"})
public class FrontendController {
	final Logger logger = LoggerFactory.getLogger(FrontendController.class);

	/**
	 * Home.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String home() {
		return "frontend/index";
	}

}
