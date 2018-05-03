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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lctech.swm.config.Constants;
import com.lctech.swm.config.SWMConfig;
import com.lctech.swm.domain.Agent;
import com.lctech.swm.domain.Device;
import com.lctech.swm.domain.MessageToast;
import com.lctech.swm.domain.Users;
import com.lctech.swm.service.AgentService;
import com.lctech.swm.service.ContractService;
import com.lctech.swm.service.DeviceService;
import com.lctech.swm.service.DistrictService;
import com.lctech.swm.service.ProvinceService;
import com.lctech.swm.service.RegionService;
import com.lctech.swm.service.UsersService;
import com.lctech.swm.service.UsesService;
import com.lctech.swm.web.util.SWMUtil;

@Controller
@RequestMapping("/admin")
public class AgentController {

	final Logger log = LoggerFactory.getLogger(AgentController.class);

	@Autowired
	ProvinceService provinceService;

	@Autowired
	DistrictService districtService;

	@Autowired
	RegionService regionService;

	@Autowired
	UsersService usersService;

	@Autowired
	AgentService agentService;

	@Autowired
	ContractService contractService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	UsesService usesService;

	@Autowired
	SWMConfig swmConfig;

	/**
	 * load list devices
	 */
	@RequestMapping(value = {"/agent"}, method = RequestMethod.GET)
	public String load(Model model,
			@RequestParam(value = "agentName", required = false) String agentName,
			@RequestParam(value = "regionId", required = false) Long regionId,
			@RequestParam(value = "districtId",
					required = false) Long districtId,
			@RequestParam(value = "provinceId",
					required = false) Long provinceId,
			@RequestParam(value = "page", required = false) Integer page,
			@ModelAttribute("msg") MessageToast msg) {

		// find device
		String redirectPage = "admin/agent/list";
		if (page != null) {
			redirectPage = "admin/agent/pager";
		}
		page = (page != null) ? page : 1;
		agentName = (agentName != null) ? agentName : "";
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
		Page<Agent> pageUsers = agentService.load(agentName, regionId, districtId,
				provinceId, pageable);

		model.addAttribute("agentName", agentName);
		model.addAttribute("page", page);
		model.addAttribute("agents", pageUsers.getContent());
		model.addAttribute("totalPages", pageUsers.getTotalPages());
		model.addAttribute("msg", msg);
		return redirectPage;
	}

	@RequestMapping(value = {"/agent/redirect"}, method = RequestMethod.GET)
	public String redirect(Model model,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "id", required = false) Long id) {

		model.addAttribute("provinces", provinceService.findAll());

		if (Constants.ACTION_VIEW_EDIT.equals(action)) {
			Agent agent = agentService.findById(id);
			model.addAttribute("districts", districtService.findByProvince(
					agent.getRegion().getDistrict().getProvince().getId()));
			model.addAttribute("regions", regionService
					.findByRegion(agent.getRegion().getDistrict().getId()));
			model.addAttribute("agent", agent);
			return "admin/agent/edit";
		} else if (Constants.ACTION_VIEW.equals(action)) {
			Agent agent = agentService.findById(id);
			model.addAttribute("districts", districtService.findByProvince(
					agent.getRegion().getDistrict().getProvince().getId()));
			model.addAttribute("regions", regionService
					.findByRegion(agent.getRegion().getDistrict().getId()));
			model.addAttribute("agent", agent);
			return "admin/agent/view";
		} else if (Constants.ACTION_VIEW_CREATE_FORM.equals(action)) {
			Agent agent = agentService.findById(id);
			model.addAttribute("uses", usesService.findAll());
			model.addAttribute("agent", agent);
			return "admin/agent/createForm";
		}
		return "admin/agent/add";
	}

	/**
	 * load assign
	 */
	@RequestMapping(value = {"/agent/loadAssign"}, method = RequestMethod.GET)
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
		String redirectPage = "admin/agent/assignDevice";
		if (page != null) {
			redirectPage = "admin/agent/loadPagerDevice";
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

//	/**Quang Comment for delete domain DeviceMapping
//	 * delete users by id
//	 *
//	 * @param model
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value = {"/agent/assign"}, method = RequestMethod.POST)
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
	 *
	 * @param model
	 * @param agent
	 * @return
	 */
	@RequestMapping(value = {"/agent"}, method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("agent") Agent agent,
			RedirectAttributes redirectAttributes) {
		log.info("save agent" + agent.toString());

		MessageToast msg = new MessageToast();
		msg.setMsgType("success");
		msg.setMsgTitle("Quản lý khách hàng doanh nghiệp");
		if (agent.getId() == 0) {
			msg.setMsg("Thêm khách hàng doanh nghiệp thành công");
		} else {
			msg.setMsg("Cập nhập khách hàng doanh nghiệp thành công");
		}

		try {
			agentService.save(agent);
		} catch (Exception ex) {
			msg.setMsg("Hệ thống bận. Xin vui lòng thử lại sau!");
			msg.setMsgType("error");
			log.error("Error:", ex);
		}
		redirectAttributes.addFlashAttribute("msg", msg);
		return "redirect:/admin/agent";
	}

	/**
	 * delete agents by id
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = {"/agent/delete"}, method = RequestMethod.POST)
	public String delete(Model model,
			@RequestParam(value = "chooses",
					required = true) String chooseAgents,
			RedirectAttributes redirectAttributes) {
		log.info("delete agents ids=" + chooseAgents);

		MessageToast msg = new MessageToast();
		msg.setMsgType("success");
		msg.setMsgTitle("Quản lý khách hàng doanh nghiệp");
		msg.setMsg("Xóa khách hàng thành công");

		// delete
		try {
			String[] idLst = chooseAgents.split(",");
			List<Long> ids = new ArrayList<Long>();
			for (String id : idLst) {
				ids.add(Long.valueOf(id));
			}
			agentService.delete(ids);
		} catch (Exception ex) {
			msg.setMsg("Hệ thống bận. Xin vui lòng thử lại sau!");
			msg.setMsgType("error");
			log.error("Error:", ex);
		}
		redirectAttributes.addFlashAttribute("msg", msg);
		return "redirect:/admin/agent";
	}
}
