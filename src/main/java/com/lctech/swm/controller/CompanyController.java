package com.lctech.swm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lctech.swm.config.Constants;
import com.lctech.swm.domain.Company;
import com.lctech.swm.domain.MessageToast;
import com.lctech.swm.service.AppContext;
import com.lctech.swm.service.CompanyService;
import com.lctech.swm.service.DistrictService;
import com.lctech.swm.service.ProvinceService;
import com.lctech.swm.service.RegionService;

@Controller
@RequestMapping("/admin")
public class CompanyController {

	final Logger log = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	CompanyService companyService;

	@Autowired
	ProvinceService provinceService;

	@Autowired
	DistrictService districtService;

	@Autowired
	RegionService regionService;
	@Autowired
	AppContext appContext;
	@RequestMapping(value = {"/company"}, method = RequestMethod.GET)
	public String load(Model model, @ModelAttribute("msg") MessageToast msg) {
		log.info("company load acc:"+appContext.getPrincipal().toString());
		model.addAttribute("companies", companyService.findByAccount(appContext.getPrincipal()));
		model.addAttribute("msg", msg);
		return "admin/company/list";
	}

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

	/**
	 * save add or edit
	 * 
	 * @param model
	 * @param company
	 * @return
	 */
	@RequestMapping(value = {"/company"}, method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("company") Company company,
			RedirectAttributes redirectAttributes) {
		log.info("save company" + company.toString());

		MessageToast msg = new MessageToast();
		msg.setMsgType("success");
		msg.setMsgTitle("Quản lý công ty nước");
		if (company.getId() == 0) {
			msg.setMsg("Thêm công ty nước thành công");
		} else {
			msg.setMsg("Cập nhập công ty nước thành công");
		}

		// save
		try {
			companyService.save(company);
		} catch (Exception ex) {
			msg.setMsg("Hệ thống bận. Xin vui lòng thử lại sau!");
			msg.setMsgType("error");
			log.error("Error:", ex);
		}

		redirectAttributes.addFlashAttribute("msg", msg);
		return "redirect:/admin/company";
	}

	/**
	 * delete company by id
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = {"/company/delete"}, method = RequestMethod.GET)
	public String delete(Model model,
			@RequestParam(value = "id", required = true) Long id,
			RedirectAttributes redirectAttributes) {
		log.info("delete company id=" + id);

		MessageToast msg = new MessageToast();
		msg.setMsgType("success");
		msg.setMsgTitle("Quản lý công ty nước");
		msg.setMsg("Xóa công ty nước thành công");

		// delete
		try {
			companyService.delete(id);
		} catch (Exception ex) {
			msg.setMsg("Hệ thống bận. Xin vui lòng thử lại sau!");
			msg.setMsgType("error");
			log.error("Error:", ex);
		}
		redirectAttributes.addFlashAttribute("msg", msg);
		return "redirect:/admin/company";
	}
}
