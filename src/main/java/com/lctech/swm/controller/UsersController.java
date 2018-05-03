package com.lctech.swm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lctech.swm.service.*;
import org.apache.commons.lang.StringUtils;
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
import com.lctech.swm.domain.Address;
import com.lctech.swm.domain.Device;
import com.lctech.swm.domain.Users;
import com.lctech.swm.web.util.SWMUtil;

@Controller
@RequestMapping("/admin")
public class UsersController {

	final Logger log = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	ProvinceService provinceService;

	@Autowired
	DistrictService districtService;

	@Autowired
	RegionService regionService;

	@Autowired
	UsersService usersService;

	@Autowired
	ContractService contractService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	UsesService usesService;

	@Autowired
	SWMConfig swmConfig;

	@Autowired
    EmailService emailService;

	/**
	 * load list devices
	 */
	@RequestMapping(value = {"/users"}, method = RequestMethod.GET)
	public String load(Model model,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "regionId", required = false) Long regionId,
			@RequestParam(value = "districtId",
					required = false) Long districtId,
			@RequestParam(value = "provinceId",
					required = false) Long provinceId,
			@RequestParam(value = "page", required = false) Integer page) {

		// find device
		String redirectPage = "admin/users/list";
		if (page != null) {
			redirectPage = "admin/users/pager";
		}
		page = (page != null) ? page : 1;
		name = (name != null) ? name : "";
		regionId = (regionId != null) ? regionId : -1;
		provinceId = (provinceId != null) ? provinceId : -1;
		districtId = (districtId != null) ? districtId : -1;

		// load data
		model.addAttribute("provinceId", provinceId);
		model.addAttribute("districtId", districtId);
		model.addAttribute("regionId", regionId);
		model.addAttribute("provinces", provinceService.findAll());
		model.addAttribute("districts",
				districtService.findByProvince(provinceId));
		model.addAttribute("regions", regionService.findByRegion(districtId));

		Pageable pageable = new PageRequest(page - 1, swmConfig.getPageSize());
		Page<Users> pageUsers = usersService.load(name, regionId, districtId,
				provinceId, pageable);

		model.addAttribute("name", name);
		model.addAttribute("page", page);
		model.addAttribute("users", pageUsers.getContent());
		model.addAttribute("totalPages", pageUsers.getTotalPages());
		return redirectPage;
	}

	@RequestMapping(value = {"/users/redirect"}, method = RequestMethod.GET)
	public String redirect(Model model,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "id", required = false) Long id) {

		model.addAttribute("provinces", provinceService.findAll());

		if (Constants.ACTION_VIEW_EDIT.equals(action)) {

			Users user = usersService.findById(id);
			model.addAttribute("districts", districtService.findByProvince(
					user.getRegion().getDistrict().getProvince().getId()));
			model.addAttribute("regions", regionService
                    .findByRegion(user.getRegion().getDistrict().getId()));

			model.addAttribute("user", user);
			return "admin/users/edit";

		} else if (Constants.ACTION_VIEW.equals(action)) {

			Users user = usersService.findById(id);
			model.addAttribute("districts", districtService.findByProvince(
					user.getRegion().getDistrict().getProvince().getId()));
			model.addAttribute("regions", regionService
					.findByRegion(user.getRegion().getDistrict().getId()));

            model.addAttribute("address", SWMUtil.getDisplayAddress(user));
			model.addAttribute("user", user);
			return "admin/users/view";
		} else if (Constants.ACTION_VIEW_CREATE_FORM.equals(action)) {
			Users user = usersService.findById(id);
			model.addAttribute("uses", usesService.findAll());
			model.addAttribute("user", user);
			return "admin/users/createForm";
		}
		return "admin/users/add";
	}

	/**
	 * load assign
	 */
	@RequestMapping(value = {"/users/loadAssign"}, method = RequestMethod.GET)
	public String loadAssign(Model model,
			@RequestParam(value = "id", required = false) Long id,
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
		String redirectPage = "admin/users/assignDevice";
		if (page != null) {
			redirectPage = "admin/users/loadPagerDevice";
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
		model.addAttribute("user", usersService.findById(id));
		return redirectPage;
	}

//	/**Quang comment for delete domain DeviceMapping
//	 * delete users by id
//	 *
//	 * @param model
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value = {"/users/assign"}, method = RequestMethod.POST)
//	public String assign(Model model, @ModelAttribute("user") Users user,
//			@RequestParam(value = "chooses",
//					required = true) String chooseDevices) {
//		log.info("assign device ids=" + chooseDevices);
//		String[] idLst = chooseDevices.split(",");
//		for (String id : idLst) {
//			try {
//				DeviceMapping deviceMapping = new DeviceMapping();
//				deviceMapping.setUser(user);
//				Device device = new Device();
//				device.setId(Long.valueOf(id));
//				deviceMapping.setStatus((byte) 0);
//				deviceMapping.setDevice(device);
//				deviceMappingService.save(deviceMapping);
//			} catch (Exception ex) {
//				log.error("Error:", ex);
//			}
//		}
//
//		return "redirect:/admin/deviceMapping";
//	}

	/**
	 * save add or edit
	 */
	@RequestMapping(value = {"/users"}, method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("user") Users user) {

		log.info("Save user" + user.toString());
        Boolean isNewUser = (user.getId() == 0);
		usersService.save(user);
		if(isNewUser){
		    List<String> receives = new ArrayList<>();
		    receives.add(swmConfig.getAdminEmail());
		    emailService.sendEmailAfterCreatedUser(receives, user.getName());
        }
		return "redirect:/admin/users";
	}

	/**
	 * delete users by id
	 */
	@RequestMapping(value = {"/users/delete"}, method = RequestMethod.POST)
	public String delete(Model model, @RequestParam(value = "chooses",
			required = true) String chooseUsers) {
		log.info("delete users ids=" + chooseUsers);
		String[] idLst = chooseUsers.split(",");
		List<Long> ids = new ArrayList<Long>();
		for (String id : idLst) {
			ids.add(Long.valueOf(id));
		}
		usersService.delete(ids);
		return "redirect:/admin/users";
	}
}
