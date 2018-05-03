package com.lctech.swm.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lctech.swm.domain.GroupRole;
import com.lctech.swm.domain.GroupRoleExtend;
import com.lctech.swm.domain.Role;
import com.lctech.swm.domain.RoleMapping;
import com.lctech.swm.service.GroupRoleService;
import com.lctech.swm.service.RoleMappingService;
import com.lctech.swm.service.RoleService;

@Controller
@RequestMapping("/admin/authorization/")
public class AuthoritiesController {

	final Logger log = LoggerFactory.getLogger(AuthoritiesController.class);

	@Autowired
	RoleService roleService;
	@Autowired
	RoleMappingService roleMappingService;
	@Autowired
	GroupRoleService groupRoleService;

	@RequestMapping(value = { "roles" }, method = RequestMethod.GET)
	public String roles(Model model) {
		model.addAttribute("roles", roleService.findAll());
		return "admin/authorization/rolelist";
	}

	@RequestMapping(value = { "grouproles" }, method = RequestMethod.GET)
	public String grouprole(Model model) {
		model.addAttribute("grouproles", groupRoleService.findAll());

		return "admin/authorization/grouprolelist";
	}

	@RequestMapping(value = { "add" }, method = RequestMethod.GET)
	public String addgrouproleget(Model model, @RequestParam(value = "id", required = false) Long id) {
		GroupRole gr = new GroupRole();
		GroupRoleExtend gre = new GroupRoleExtend();
		List<RoleMapping> lrm = new ArrayList<RoleMapping>();
		if (id != null && id > 0) {
			gr = groupRoleService.findById(id);
			lrm=roleMappingService.findByGroup(gr.getId());
			log.info(lrm.toString());
			gre.setName(gr.getName());
		} else {
			for (Role r : roleService.findAll()) {
				RoleMapping rm = new RoleMapping();
				rm.setRole(r);
				rm.setStatus(false);
				lrm.add(rm);
			}
		}
		
		gre.setRm(lrm);
		//model.addAttribute("grouproles",gr);
		//model.addAttribute("roles", roleService.findAll());
		model.addAttribute("gre", gre);
		
		return "admin/authorization/add";
	}

	@RequestMapping(value = { "add" }, method = RequestMethod.POST)
	public String addgrouprole(Model model, @ModelAttribute("gre") GroupRoleExtend gre) {

		GroupRole gr = new GroupRole();
		log.info(gre.toString());
		gr.setName(gre.getName());
		groupRoleService.save(gr);
		Long idgr = groupRoleService.findByName(gr.getName()).getId();
		for (RoleMapping rm : gre.getRm()) {
			rm.setGroupRole(idgr);
			rm.setId(roleMappingService.nextval());
			roleMappingService.save(rm);
		}
		return "redirect:grouproles";
	}
	/*
	 * @RequestMapping(value = {"/company/redirect"}, method =
	 * RequestMethod.GET) public String redirect(Model model,
	 * 
	 * @RequestParam(value = "action", required = false) String action,
	 * 
	 * @RequestParam(value = "id", required = false) Long id) {
	 * 
	 * model.addAttribute("provinces", provinceService.findAll());
	 * 
	 * if (Constants.ACTION_VIEW_EDIT.equals(action)) { Company company =
	 * companyService.findById(id); model.addAttribute("districts",
	 * districtService.findByProvince(
	 * company.getRegion().getDistrict().getProvince().getId()));
	 * model.addAttribute("regions", regionService
	 * .findByRegion(company.getRegion().getDistrict().getId()));
	 * model.addAttribute("company", company); return "admin/company/edit"; }
	 * else if (Constants.ACTION_VIEW.equals(action)) { Company company =
	 * companyService.findById(id); model.addAttribute("districts",
	 * districtService.findByProvince(
	 * company.getRegion().getDistrict().getProvince().getId()));
	 * model.addAttribute("regions", regionService
	 * .findByRegion(company.getRegion().getDistrict().getId()));
	 * model.addAttribute("company", company); return "admin/company/view"; }
	 * return "admin/company/add"; }
	 * 
	 *//**
		 * save add or edit
		 * 
		 * @param model
		 * @param company
		 * @return
		 */
	/*
	 * @RequestMapping(value = {"/company"}, method = RequestMethod.POST) public
	 * String save(Model model,
	 * 
	 * @ModelAttribute("company") Company company) { log.info("save company" +
	 * company.toString()); companyService.save(company); return
	 * "redirect:/admin/company"; }
	 * 
	 *//**
		 * delete company by id
		 * 
		 * @param model
		 * @param id
		 * @return
		 *//*
		 * @RequestMapping(value = {"/company/delete"}, method =
		 * RequestMethod.GET) public String delete(Model model,
		 * 
		 * @RequestParam(value = "id", required = true) Long id) {
		 * log.info("delete company id=" + id); companyService.delete(id);
		 * return "redirect:/admin/company"; }
		 */
}
