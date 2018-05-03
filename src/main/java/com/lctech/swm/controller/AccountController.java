package com.lctech.swm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lctech.swm.config.Constants;
import com.lctech.swm.config.SWMConfig;
//import com.lctech.swm.constant.CommonConstant;
import com.lctech.swm.domain.Account;
import com.lctech.swm.domain.Agent;
import com.lctech.swm.domain.Company;
import com.lctech.swm.domain.Users;
import com.lctech.swm.repository.AccountRepository;
import com.lctech.swm.service.AccountService;
import com.lctech.swm.service.AgentService;
import com.lctech.swm.service.AppContext;
import com.lctech.swm.service.CompanyService;
import com.lctech.swm.service.DistrictService;
import com.lctech.swm.service.GroupRoleService;
import com.lctech.swm.service.ProvinceService;
import com.lctech.swm.service.RegionService;
import com.lctech.swm.service.RoleMappingService;
import com.lctech.swm.service.RoleService;
import com.lctech.swm.service.UsersService;

@Controller
@RequestMapping("/admin/account/")
public class AccountController {

	final Logger log = LoggerFactory.getLogger(AccountController.class);

    
	@Autowired
	AppContext appContext;
	@Autowired
	AccountService accountService;
	@Autowired
	CompanyService companyService;
	@Autowired
	AgentService agentService;
	@Autowired
	UsersService userService;
	@Autowired
	SWMConfig swmConfig;
	@RequestMapping(value = {"profile"}, method = RequestMethod.GET)
	public String profile(Model model) {
		//model.addAttribute("profile", accountService.);
		Account acc = appContext.getPrincipal();
		model.addAttribute("acc", acc);

		return "admin/account/profile";
	}
	@RequestMapping(value = {"list"}, method = RequestMethod.GET)
	public String list(Model model,@RequestParam(value = "page", required = false) Integer page) {
		Account acc = appContext.getPrincipal();
		model.addAttribute("acc", acc);
		if(page == null) page =1;
		Pageable pageable = new PageRequest(page - 1, swmConfig.getPageSize());
		Page<Account> pa = accountService.findAll(pageable);
		
		model.addAttribute("pa", pa);

		return "admin/account/list";
	}
	@RequestMapping(value = {"add"}, method = RequestMethod.GET)
	public String add(Model model) {
		//model.addAttribute("profile", accountService.);
		// get level account 
		Account acc = appContext.getPrincipal();
		model.addAttribute("acc", acc);
		return "admin/account/add";
	}
	@RequestMapping(value = {"add"}, method = RequestMethod.POST)
	public String addPost(Model model,@ModelAttribute("account") Account account) {
		//model.addAttribute("profile", accountService.);
		// get level account 
		Account acc = appContext.getPrincipal();
		model.addAttribute("acc", acc);
		//Quang.Tran Remove cause account not have field agent
//        if(account.getLevel()==Constants.LEVEL_TYPE_AGENT) {
//        	Agent a  = agentService.findById(account.getAgent().getId());
//        	account.setAgent(a);
//        }else if(account.getLevel()==Constants.LEVEL_TYPE_COMPANY) {
//        	Company c = companyService.findById(account.getAgent().getId());
//        	account.setCompany(c);
//        	account.setAgent(null);
//        }else if(account.getLevel()==Constants.LEVEL_TYPE_USER) {
//        	Users u = userService.findById(account.getAgent().getId());
//        	account.setUser(u);
//        	account.setAgent(null);
//        }
        
		if(account.getPassword() != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
			account.setPassword(passwordEncoder.encode(account.getPassword()));
		}
        accountService.save(account);
        log.info("save account success");
		
		return "redirect:add";
	}
	/*
	@RequestMapping(value = {"/company/redirect"}, method = RequestMethod.GET)
	public String redirect(Model model,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "id", required = false) Long id) {

		model.addAttribute("provinces", provinceService.findAll());

		if (Constants.ACTION_VIEW_EDIT.equals(action)) {
			Company company = companyService.findById(id);
			model.addAttribute("districts", districtService.findByProvince(
					company.getRegion().getDistrict().getProvince().getId()));
			model.addAttribute("regions", regionService
					.findByRegion(company.getRegion().getDistrict().getId()));
			model.addAttribute("company", company);
			return "admin/company/edit";
		} else if (Constants.ACTION_VIEW.equals(action)) {
			Company company = companyService.findById(id);
			model.addAttribute("districts", districtService.findByProvince(
					company.getRegion().getDistrict().getProvince().getId()));
			model.addAttribute("regions", regionService
					.findByRegion(company.getRegion().getDistrict().getId()));
			model.addAttribute("company", company);
			return "admin/company/view";
		}
		return "admin/company/add";
	}

	*//**
	 * save add or edit
	 * 
	 * @param model
	 * @param company
	 * @return
	 *//*
	@RequestMapping(value = {"/company"}, method = RequestMethod.POST)
	public String save(Model model,
			@ModelAttribute("company") Company company) {
		log.info("save company" + company.toString());
		companyService.save(company);
		return "redirect:/admin/company";
	}

	*//**
	 * delete company by id
	 * 
	 * @param model
	 * @param id
	 * @return
	 *//*
	@RequestMapping(value = {"/company/delete"}, method = RequestMethod.GET)
	public String delete(Model model,
			@RequestParam(value = "id", required = true) Long id) {
		log.info("delete company id=" + id);
		companyService.delete(id);
		return "redirect:/admin/company";
	}*/

}
