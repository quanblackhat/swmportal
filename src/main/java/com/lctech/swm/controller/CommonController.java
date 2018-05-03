package com.lctech.swm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lctech.swm.config.Constants;
import com.lctech.swm.config.SWMConfig;
import com.lctech.swm.domain.Account;
import com.lctech.swm.domain.Agent;
import com.lctech.swm.domain.Company;
import com.lctech.swm.domain.Users;
import com.lctech.swm.service.AccountService;
import com.lctech.swm.service.AgentService;
import com.lctech.swm.service.CompanyService;
import com.lctech.swm.service.DistrictService;
import com.lctech.swm.service.RegionService;
import com.lctech.swm.service.UsersService;
import com.lctech.swm.service.UsesService;

@Controller
@RequestMapping("/admin")
public class CommonController {

	final Logger log = LoggerFactory.getLogger(CommonController.class);
	@Autowired
	DistrictService districtService;
	@Autowired
	RegionService regionService;
	@Autowired
	SWMConfig swmConfig;
	@Autowired
	AccountService accountService;

	@Autowired
	AgentService agentService;
	@Autowired
	CompanyService companyService;
	@Autowired
	UsersService usersService;
	@RequestMapping(value = {"/common/loadDistrict"},
			method = RequestMethod.GET)
	public String loadDistrict(Model model,
			@RequestParam(value = "provinceId",
					required = true) Long provinceId,
			@RequestParam(value = "action", required = false) String action) {
		model.addAttribute("districts",
				districtService.findByProvince(provinceId));
		if (Constants.ACTION_SEARCH.equals(action)) {
			model.addAttribute("action", Constants.ACTION_SEARCH);
		}
		return "admin/common/district";
	}
	@RequestMapping(value = {"/common/loadLevel"},
			method = RequestMethod.GET)
	public String loadObjectbyLevel(Model model,
			@RequestParam(value = "level",
					required = true) Long level,
			@RequestParam(value = "action", required = false) String action) {
		
		HashMap<Long,String> hm=new HashMap<Long,String>();

		log.info("level:"+ level);
		Account acc = getPrincipal();
		if(level==Constants.LEVEL_TYPE_AGENT) {
			List<Agent> lsa = agentService.findAll();
			for(Agent a : lsa) {
				hm.put(a.getId(), a.getAgentName());
			}
		}else if(level == Constants.LEVEL_TYPE_COMPANY){
			List<Company> lsp = new ArrayList<Company>();
			lsp =companyService.findByAccount(acc);
			for(Company a : lsp) {
				hm.put(a.getId(), a.getName());
			}
		}else if(level == Constants.LEVEL_TYPE_USER){
			List<Users> lsu = new ArrayList<Users>();
			lsu = usersService.findByAccount(acc);
			for(Users a : lsu) {
				hm.put(a.getId(), a.getName());
			}
		} 
		
		model.addAttribute("hm", hm);

		return "admin/common/loadLevel";
	}
	@RequestMapping(value = {"/common/loadRegion"}, method = RequestMethod.GET)
	public String loadRegion(Model model,
			@RequestParam(value = "districtId",
					required = true) Long districtId,
			@RequestParam(value = "action", required = false) String action) {
		model.addAttribute("regions", regionService.findByRegion(districtId));
		if (Constants.ACTION_SEARCH.equals(action)) {
			model.addAttribute("action", Constants.ACTION_SEARCH);
		}
		return "admin/common/region";
	}
	private Account getPrincipal(){
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
