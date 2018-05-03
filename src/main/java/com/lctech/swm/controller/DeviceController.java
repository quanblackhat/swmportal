package com.lctech.swm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lctech.swm.config.Constants;
import com.lctech.swm.config.SWMConfig;
import com.lctech.swm.domain.Device;
import com.lctech.swm.service.DeviceService;
import com.lctech.swm.service.DistrictService;
import com.lctech.swm.service.ProvinceService;
import com.lctech.swm.service.RegionService;
import com.lctech.swm.web.util.SWMUtil;

@Controller
@RequestMapping("/admin")
public class DeviceController {

	final Logger log = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	ProvinceService provinceService;

	@Autowired
	DistrictService districtService;

	@Autowired
	RegionService regionService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	SWMConfig swmConfig;

	/**
	 * load list devices
	 */
	@RequestMapping(value = {"/device"}, method = RequestMethod.GET)
	public String load(Model model,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "regionId", required = false) Long regionId,
			@RequestParam(value = "districtId",
					required = false) Long districtId,
			@RequestParam(value = "provinceId",
					required = false) Long provinceId,
			@RequestParam(value = "setupDate",
					required = false) String setupDateText,
			@RequestParam(value = "page", required = false) Integer page) {

		// find device
		String redirectPage = "admin/device/list";
		if (page != null) {
			redirectPage = "admin/device/pager";
		}
		page = (page != null) ? page : 1;
		code = (code != null) ? code : "";
		regionId = (regionId != null) ? regionId : -1;
		provinceId = (provinceId != null) ? provinceId : -1;
		districtId = (districtId != null) ? districtId : -1;
		Date setupDate = null;
		try {
			setupDate = (setupDateText != null
					&& !setupDateText.trim().equals(""))
							? SWMUtil.formatDate(setupDateText)
							: null;
		} catch (ParseException ex) {
			log.error("Error:", ex);
		}

		// load data
		model.addAttribute("provinceId", provinceId);
		model.addAttribute("districtId", districtId);
		model.addAttribute("regionId", regionId);
		model.addAttribute("provinces", provinceService.findAll());
		model.addAttribute("districts",
				districtService.findByProvince(provinceId));
		model.addAttribute("regions", regionService.findByRegion(districtId));

		Pageable pageable = new PageRequest(page - 1, swmConfig.getPageSize());
		Page<Device> pageDevice = deviceService.load(code, regionId, districtId,
				provinceId, setupDate, pageable);

		model.addAttribute("code", code);
		model.addAttribute("setupDate", setupDate);
		model.addAttribute("page", page);
		model.addAttribute("devices", pageDevice.getContent());
		model.addAttribute("totalPages", pageDevice.getTotalPages());
		return redirectPage;
	}

	@RequestMapping(value = {"/device/redirect"}, method = RequestMethod.GET)
	public String redirect(Model model,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "id", required = false) Long id) {

		model.addAttribute("provinces", provinceService.findAll());

		if (Constants.ACTION_VIEW_EDIT.equals(action)) {
			Device device = deviceService.findById(id);
			model.addAttribute("districts", districtService.findByProvince(
					device.getRegion().getDistrict().getProvince().getId()));
			model.addAttribute("regions", regionService
					.findByRegion(device.getRegion().getDistrict().getId()));
			model.addAttribute("device", device);
			return "admin/device/edit";
		} else if (Constants.ACTION_VIEW.equals(action)) {
			Device device = deviceService.findById(id);
			model.addAttribute("districts", districtService.findByProvince(
					device.getRegion().getDistrict().getProvince().getId()));
			model.addAttribute("regions", regionService
					.findByRegion(device.getRegion().getDistrict().getId()));
			model.addAttribute("device", device);
			return "admin/device/view";
		}
		return "admin/device/add";
	}

	@RequestMapping(value = {"/device/loadDistrict"},
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
		return "admin/device/district";
	}

	@RequestMapping(value = {"/device/loadRegion"}, method = RequestMethod.GET)
	public String loadRegion(Model model,
			@RequestParam(value = "districtId",
					required = true) Long districtId,
			@RequestParam(value = "action", required = false) String action) {
		model.addAttribute("regions", regionService.findByRegion(districtId));
		if (Constants.ACTION_SEARCH.equals(action)) {
			model.addAttribute("action", Constants.ACTION_SEARCH);
		}
		return "admin/device/region";
	}

	/**
	 * save add or edit
	 * 
	 * @param model
	 * @param device
	 * @return
	 */
	@RequestMapping(value = {"/device"}, method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("device") Device device,
			@RequestParam(value = "setupDateText",
					required = false) String setupDateText) {
		try {
			if (setupDateText != null && !setupDateText.trim().equals("")) {
				device.setSetupDate(SWMUtil.formatDate(setupDateText));
			}
		} catch (ParseException ex) {
			log.error("Error:", ex);
		}
		log.info("save device" + device.toString());
		deviceService.save(device);
		return "redirect:/admin/device";
	}

	/**
	 * delete device by id
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = {"/device/delete"}, method = RequestMethod.POST)
	public String delete(Model model, @RequestParam(value = "chooses",
			required = true) String chooseDevices) {
		log.info("delete device ids=" + chooseDevices);
		String[] idLst = chooseDevices.split(",");
		List<Long> ids = new ArrayList<Long>();
		for (String id : idLst) {
			ids.add(Long.valueOf(id));
		}
		deviceService.delete(ids);
		return "redirect:/admin/device";
	}

	@RequestMapping(value = {"/403"}, method = RequestMethod.GET)
	public String error() {
		return "/admin/404";
	}
}
