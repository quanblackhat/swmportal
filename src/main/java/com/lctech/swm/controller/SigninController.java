package com.lctech.swm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SigninController {
	final Logger logger = LoggerFactory.getLogger(SigninController.class);
	
	@GetMapping("/login")
	public String getLogin() {
		logger.info("Login");
		return "admin/login";
	}
	
	// Login form with error
	  @RequestMapping("/login-error")
	  public String loginError(Model model) {
	    model.addAttribute("loginError", true);
	    return "admin/login";
	  }
	
	@GetMapping("/registration")
	public String getRegistration() {
		logger.info("Registration");
		return "registration/registration";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Logout");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		return "frontend/index";
	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "403";
	}
}
