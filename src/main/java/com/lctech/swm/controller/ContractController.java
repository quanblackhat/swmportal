package com.lctech.swm.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lctech.swm.config.SWMConfig;
import com.lctech.swm.domain.Address;
import com.lctech.swm.domain.Agent;
import com.lctech.swm.domain.Contract;
import com.lctech.swm.domain.Users;
import com.lctech.swm.service.ContractService;
import com.lctech.swm.service.DistrictService;
import com.lctech.swm.service.ProvinceService;
import com.lctech.swm.service.RegionService;
import com.lctech.swm.service.UsersService;
import com.lctech.swm.web.util.SWMUtil;
import com.lctech.swm.web.util.VnStringUtil;

@Controller
@RequestMapping("/admin")
public class ContractController {

	final Logger log = LoggerFactory.getLogger(ContractController.class);

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
	SWMConfig swmConfig;

	/**
	 * load list devices
	 */
	@RequestMapping(value = {"/contract"}, method = RequestMethod.GET)
	public String load(Model model,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "regionId", required = false) Long regionId,
			@RequestParam(value = "districtId",
					required = false) Long districtId,
			@RequestParam(value = "provinceId",
					required = false) Long provinceId,
			@RequestParam(value = "status", required = false) Byte status,
			@RequestParam(value = "page", required = false) Integer page) {

		// find device
		String redirectPage = "admin/contract/list";
		if (page != null) {
			redirectPage = "admin/contract/pager";
		}
		page = (page != null) ? page : 1;
		name = (name != null) ? name : "";
		regionId = (regionId != null) ? regionId : -1;
		provinceId = (provinceId != null) ? provinceId : -1;
		districtId = (districtId != null) ? districtId : -1;
		status = (status != null) ? status : -1;

		// load data
		model.addAttribute("status", status);
		model.addAttribute("provinceId", provinceId);
		model.addAttribute("districtId", districtId);
		model.addAttribute("regionId", regionId);
		model.addAttribute("provinces", provinceService.findAll());
		model.addAttribute("districts",
				districtService.findByProvince(provinceId));
		model.addAttribute("regions", regionService.findByRegion(districtId));

		Pageable pageable = new PageRequest(page - 1, swmConfig.getPageSize());
		Page<Contract> pageUsers = contractService.load(name, provinceId,
				districtId, regionId, status, pageable);

		model.addAttribute("page", page);
		model.addAttribute("contracts", pageUsers.getContent());
		model.addAttribute("totalPages", pageUsers.getTotalPages());
		return redirectPage;
	}

	/**
	 * delete device by id
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = {"/contract/delete"}, method = RequestMethod.POST)
	public String delete(Model model, @RequestParam(value = "chooses",
			required = true) String chooseContracts) {
		log.info("delete contract ids=" + chooseContracts);
		String[] idLst = chooseContracts.split(",");
		List<Long> ids = new ArrayList<Long>();
		for (String id : idLst) {
			ids.add(Long.valueOf(id));
		}
		contractService.delete(ids);
		return "redirect:/admin/contract";
	}

	/**
	 * active contract
	 *
	 * @param model
	 * @param device
	 * @return
	 */
	@RequestMapping(value = {"/contract/active"}, method = RequestMethod.GET)
	public String active(Model model,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "status", required = false) Byte status) {
		log.info("save device mapping, id=" + id + ";status=" + status);
		Contract contract = contractService.findById(id);
		if (contract != null) {
			contract.setStatus(status);
			contractService.save(contract);
		}
		return "redirect:/admin/contract";
	}

	/**
	 * save add or edit
	 *
	 * @param model
	 * @param device
	 * @return
	 */
	@RequestMapping(value = {"/contract/createForm/user"},
			method = RequestMethod.POST)
	public ResponseEntity<Resource> creatFormUser(Model model,
			@ModelAttribute("contract") Contract contract) {
		Long time = System.currentTimeMillis();
		Users user = contract.getUser();
		log.info("create form" + user.toString());
		File file = null;
		InputStreamResource resource = null;
		ByteArrayOutputStream b = null;
		XWPFDocument docx = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			file = new ClassPathResource(
					"/static/admin/file_template/house_form.docx").getFile();
			docx = new XWPFDocument(new FileInputStream(file));
			List<XWPFParagraph> paragrahps = docx.getParagraphs();
			for (int j = 0; j < paragrahps.size(); j++) {
				XWPFParagraph p = paragrahps.get(j);
				List<XWPFRun> runs = p.getRuns();
				for (int k = 0; k < runs.size(); k++) {
					spillTextUser(runs.get(k), contract, user);
				}
			}

			String fileName = "De_nghi_cap_nuoc_sach_" + VnStringUtil
					.vnConvert(user.getName().replaceAll(" ", "_")) + ".docx";
			b = new ByteArrayOutputStream();
			docx.write(b); // doc should be a XWPFDocument
			resource = new InputStreamResource(
					new ByteArrayInputStream(b.toByteArray()));
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");

			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.set(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=" + fileName);

		} catch (Exception ex) {
			log.error("Error:", ex);
		} finally {
			if (docx != null) {
				try {
					docx.close();
				} catch (IOException ex) {
					log.error("Error:", ex);
				}
			}
		}

		// default status = 0
		contract.setStatus((byte) 0);
		contractService.save(contract);

		ResponseEntity<Resource> res = ResponseEntity.ok().headers(headers)
				.contentLength(b.size())
				.contentType(
						MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
		log.info("time=" + (System.currentTimeMillis() - time));
		return res;
	}

	/**TODO QuangTran comment for remove Agent out of Contract
	 * save add or edit
	 *
	 * @param model
	 * @param device
	 * @return
	 */
	@RequestMapping(value = {"/contract/createForm/agent"},
			method = RequestMethod.POST)
	public ResponseEntity<Resource> creatFormAgent(Model model,
			@ModelAttribute("contract") Contract contract,
			@ModelAttribute("address") Address address) {
		Long time = System.currentTimeMillis();
		Agent agent = contract.getUser().getAgent();
		log.info("create form" + agent.toString());
		File file = null;
		InputStreamResource resource = null;
		ByteArrayOutputStream b = null;
		XWPFDocument docx = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			file = new ClassPathResource(
					"/static/admin/file_template/agent_form.docx").getFile();
			docx = new XWPFDocument(new FileInputStream(file));
			List<XWPFParagraph> paragrahps = docx.getParagraphs();
			for (int j = 0; j < paragrahps.size(); j++) {
				XWPFParagraph p = paragrahps.get(j);
				List<XWPFRun> runs = p.getRuns();
				for (int k = 0; k < runs.size(); k++) {
					spillTextAgent(runs.get(k), contract, agent, address);
				}
			}

			String fileName = "De_nghi_cap_nuoc_sach_"
					+ VnStringUtil.vnConvert(
							agent.getAgentName().replaceAll(" ", "_"))
					+ ".docx";
			b = new ByteArrayOutputStream();
			docx.write(b); // doc should be a XWPFDocument
			resource = new InputStreamResource(
					new ByteArrayInputStream(b.toByteArray()));
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");

			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.set(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=" + fileName);

		} catch (Exception ex) {
			log.error("Error:", ex);
		} finally {
			if (docx != null) {
				try {
					docx.close();
				} catch (IOException ex) {
					log.error("Error:", ex);
				}
			}
		}

		StringBuilder adr = new StringBuilder();
		if (address != null) {
			adr.append(address.getNo().isEmpty() ? "_" : address.getNo());
			adr.append(";");
			adr.append(address.getLane().isEmpty() ? "_" : address.getLane());
			adr.append(";");
			adr.append(address.getAlley().isEmpty() ? "_" : address.getAlley());
			adr.append(";");
			adr.append(
					address.getAlley2().isEmpty() ? "_" : address.getAlley2());
			adr.append(";");
			adr.append(
					address.getStreet().isEmpty() ? "_" : address.getStreet());
			contract.setAddress(adr.toString());
		}
		// default status = 0
		contract.setStatus((byte) 0);
		contractService.save(contract);

		ResponseEntity<Resource> res = ResponseEntity.ok().headers(headers)
				.contentLength(b.size())
				.contentType(
						MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
		log.info("time=" + (System.currentTimeMillis() - time));
		return res;
	}

	/**
	 * @param run
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 */
	private void spillTextUser(XWPFRun run, Contract contract, Users user)
			throws FileNotFoundException, IOException, InvalidFormatException {
		String text = run.text();
//		String address[] = SWMUtil.isEmpty(user.getAddress())
//				? new String[]{"_", "_", "_", "_", "_"}
//				: user.getAddress().split(";");
        String[] address = new String[]{user.getAddressNo(),user.getAddressLand(),
                user.getAddressAlley(), user.getAddressAlley2(), user.getAddressStreet()};
		// replace date
		if (text.contains("[date]")) {
			int date = Calendar.getInstance().get(Calendar.DATE);
			run.setText(String.valueOf(date), 0);
		}
		// replace month
		if (text.contains("[month]")) {
			int month = Calendar.getInstance().get(Calendar.MONTH);
			run.setText(String.valueOf(month + 1), 0);
		}
		// replace year
		if (text.contains("[year]")) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			run.setText(String.valueOf(year), 0);
		}
		// replace name
		if (text.contains("[NAME]")) {
			String name = SWMUtil.isEmpty(user.getName())
					? ".........."
					: user.getName();
			run.setText(name, 0);
		}
		// replace phone number
		if (text.contains("[PHONENUMBER]")) {
			String phoneNumber = SWMUtil.isEmpty(user.getPhoneNumber())
					? ".........."
					: user.getPhoneNumber();
			run.setText(phoneNumber, 0);
		}
		// replace no
		if (text.contains("[NO]")) {
			String no = address[0].equals("_") ? ".........." : address[0];
			run.setText(no, 0);
		}
		// replace lane
		if (text.contains("[LANE]")) {
			String lane = address[1].equals("_") ? ".........." : address[1];
			run.setText(lane, 0);
		}
		// replace alley
		if (text.contains("[ALLEY]")) {
			String alley = address[2].equals("_") ? ".........." : address[2];
			run.setText(alley, 0);
		}
		// replace alley2
		if (text.contains("[ALLEY2]")) {
			String alley2 = address[3].equals("_") ? ".........." : address[3];
			run.setText(alley2, 0);
		}
		// replace street
		if (text.contains("[STREET]")) {
			String street = address[4].equals("_") ? ".........." : address[4];
			run.setText(street, 0);
		}
		// replace region
		if (text.contains("[REGION]")) {
			String region = "..........";
			if (user.getRegion() != null
					&& !SWMUtil.isEmpty(user.getRegion().getName())) {
				region = user.getRegion().getName();
			}
			run.setText(region, 0);
		}
		// replace district
		if (text.contains("[DISTRICT]")) {
			String district = "..........";
			if (user.getRegion() != null
					&& user.getRegion().getDistrict() != null
					&& SWMUtil.isEmpty(
							user.getRegion().getDistrict().getName())) {
				district = user.getRegion().getDistrict().getName();
			}
			run.setText(district, 0);
		}
		// replace amount
		if (text.contains("[AMOUNT]")) {
			String amount = "..........";
			if (contract.getExpectAmount() != null) {
				amount = String.valueOf(contract.getExpectAmount());
			}
			run.setText(amount, 0);
		}
		// replace user uses
//		if (text.contains("[USER_USES]")) {
//			String userUses = "..........";
//			if (!SWMUtil.isEmpty(contract.getUserUses())) {
//				userUses = contract.getUserUses();
//			}
//			run.setText(userUses, 0);
//		}
		// replace REGIS_NEW
		if (text.contains("[REGIS_NEW]") && contract.getRegisterType() != null
				&& contract.getRegisterType() == 1) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "checked.png", Units.toEMU(15),
					Units.toEMU(15));
		} else if (text.contains("[REGIS_NEW]")
				&& (contract.getRegisterType() == null
						|| (contract.getRegisterType() != null
								&& contract.getRegisterType() != 1))) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/un-checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "un-checked.png", Units.toEMU(15),
					Units.toEMU(15));
		}

		// replace REGIS_RENEW
		if (text.contains("[REGIS_RENEW]") && contract.getRegisterType() != null
				&& contract.getRegisterType() == 3) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "checked.png", Units.toEMU(15),
					Units.toEMU(15));
		} else if (text.contains("[REGIS_RENEW]")
				&& (contract.getRegisterType() == null
						|| (contract.getRegisterType() != null
								&& contract.getRegisterType() != 3))) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/un-checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "un-checked.png", Units.toEMU(15),
					Units.toEMU(15));
		}

		// replace REGIS_TH
		if (text.contains("[REGIS_TH]") && contract.getRegisterType() != null
				&& contract.getRegisterType() == 2) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "checked.png", Units.toEMU(15),
					Units.toEMU(15));
		} else if (text.contains("[REGIS_TH]")
				&& (contract.getRegisterType() == null
						|| (contract.getRegisterType() != null
								&& contract.getRegisterType() != 2))) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/un-checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "un-checked.png", Units.toEMU(15),
					Units.toEMU(15));
		}

		// replace USES_SH
		if (text.contains("[USES_SH]") && contract.getUses() != null
				&& contract.getUses().getUsesCode().equals("SH")) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "checked.png", Units.toEMU(15),
					Units.toEMU(15));
		} else if (text.contains("[USES_SH]")
				&& (contract.getUses() == null || (contract.getUses() != null
						&& !contract.getUses().getUsesCode().equals("SH")))) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/un-checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "un-checked.png", Units.toEMU(15),
					Units.toEMU(15));
		}

		// replace USES_SX
		if (text.contains("[USES_SX]") && contract.getUses() != null
				&& contract.getUses().getUsesCode().equals("SX")) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "checked.png", Units.toEMU(15),
					Units.toEMU(15));
		} else if (text.contains("[USES_SX]")
				&& (contract.getUses() == null || (contract.getUses() != null
						&& !contract.getUses().getUsesCode().equals("SX")))) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/un-checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "un-checked.png", Units.toEMU(15),
					Units.toEMU(15));
		}

		// replace USES_KDDV
		if (text.contains("[USES_KDDV]") && contract.getUses() != null
				&& contract.getUses().getUsesCode().equals("KDDV")) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "checked.png", Units.toEMU(15),
					Units.toEMU(15));
		} else if (text.contains("[USES_KDDV]")
				&& (contract.getUses() == null || (contract.getUses() != null
						&& !contract.getUses().getUsesCode().equals("KDDV")))) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/un-checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "un-checked.png", Units.toEMU(15),
					Units.toEMU(15));
		}
	}

	/**
	 * @param run
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 */
	private void spillTextAgent(XWPFRun run, Contract contract, Agent agent,
			Address address)
			throws FileNotFoundException, IOException, InvalidFormatException {
		String text = run.text();
		// replace date
		if (text.contains("[date]")) {
			int date = Calendar.getInstance().get(Calendar.DATE);
			run.setText(String.valueOf(date), 0);
		}
		// replace month
		if (text.contains("[month]")) {
			int month = Calendar.getInstance().get(Calendar.MONTH);
			run.setText(String.valueOf(month + 1), 0);
		}
		// replace year
		if (text.contains("[year]")) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			run.setText(String.valueOf(year), 0);
		}
		// replace name
		if (text.contains("[agent_name]")) {
			String name = SWMUtil.isEmpty(agent.getAgentName())
					? ".........."
					: agent.getAgentName();
			run.setText(name, 0);
		}
		// replace phone number
		if (text.contains("[phonenumber]")) {
			String phoneNumber = SWMUtil.isEmpty(agent.getPhoneNumber())
					? ".........."
					: agent.getPhoneNumber();
			run.setText(phoneNumber, 0);
		}
		// replace representative
		if (text.contains("[representative]")) {
			String representative = SWMUtil.isEmpty(agent.getRepresentative())
					? ".........."
					: agent.getRepresentative();
			run.setText(representative, 0);
		}
		// replace function
		if (text.contains("[function]")) {
			String function = SWMUtil.isEmpty(agent.getFunction())
					? ".........."
					: agent.getFunction();
			run.setText(function, 0);
		}
		// replace agent address
		if (text.contains("[agent_address]")) {
			String agentAddress = SWMUtil.isEmpty(agent.getAgentAddress())
					? ".........."
					: agent.getAgentAddress();
			run.setText(agentAddress, 0);
		}
		// replace bank account
		if (text.contains("[bank_account]")) {
			String bankAccount = SWMUtil.isEmpty(agent.getBankAccount())
					? ".........."
					: agent.getBankAccount();
			run.setText(bankAccount, 0);
		}
		// replace bank
		if (text.contains("[bank]")) {
			String bank = SWMUtil.isEmpty(agent.getBank())
					? ".........."
					: agent.getBank();
			run.setText(bank, 0);
		}
		// replace tax code
		if (text.contains("[tax_code]")) {
			String taxCode = SWMUtil.isEmpty(agent.getTaxCode())
					? ".........."
					: agent.getTaxCode();
			run.setText(taxCode, 0);
		}
		// replace no
		if (text.contains("[no]")) {
			String no = address.getNo().isEmpty()
					? ".........."
					: address.getNo();
			run.setText(no, 0);
		}
		// replace alley
		if (text.contains("[alley]")) {
			String alley = address.getLane().isEmpty()
					? "..."
					: address.getLane();
			alley += address.getAlley().isEmpty()
					? "/..."
					: "/" + address.getAlley();
			alley += address.getAlley2().isEmpty()
					? "/..."
					: "/" + address.getAlley2();
			run.setText(alley, 0);
		}
		// replace street
		if (text.contains("[street]")) {
			String street = address.getStreet().isEmpty()
					? ".........."
					: address.getStreet();
			run.setText(street, 0);
		}
		// replace district
		if (text.contains("[district]")) {
			String district = "..........";
			if (agent.getRegion() != null
					&& agent.getRegion().getDistrict() != null
					&& !SWMUtil.isEmpty(
							agent.getRegion().getDistrict().getName())) {
				district = agent.getRegion().getDistrict().getName();
			}
			run.setText(district, 0);
		}
		// replace province
		if (text.contains("[province]")) {
			String province = "..........";
			if (agent.getRegion() != null
					&& agent.getRegion().getDistrict() != null
					&& agent.getRegion().getDistrict().getProvince() != null
					&& !SWMUtil.isEmpty(agent.getRegion().getDistrict()
							.getProvince().getName())) {
				province = agent.getRegion().getDistrict().getProvince()
						.getName();
			}
			run.setText(province, 0);
		}
		// replace amount
		if (text.contains("[amount]")) {
			String amount = "..........";
			if (contract.getExpectAmount() != null) {
				amount = String.valueOf(contract.getExpectAmount());
			}
			run.setText(amount, 0);
		}
		// replace USES_SH
		if (text.contains("[uses_hcsn]") && contract.getUses() != null
				&& contract.getUses().getUsesCode().equals("HCSN")) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "checked.png", Units.toEMU(15),
					Units.toEMU(15));
		} else if (text.contains("[uses_hcsn]")
				&& (contract.getUses() == null || (contract.getUses() != null
						&& !contract.getUses().getUsesCode().equals("HCSN")))) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/un-checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "un-checked.png", Units.toEMU(15),
					Units.toEMU(15));
		}

		// replace USES_SX
		if (text.contains("[uses_sx]") && contract.getUses() != null
				&& contract.getUses().getUsesCode().equals("SX")) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "checked.png", Units.toEMU(15),
					Units.toEMU(15));
		} else if (text.contains("[uses_sx]")
				&& (contract.getUses() == null || (contract.getUses() != null
						&& !contract.getUses().getUsesCode().equals("SX")))) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/un-checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "un-checked.png", Units.toEMU(15),
					Units.toEMU(15));
		}

		// replace USES_KDDV
		if (text.contains("[uses_kddv]") && contract.getUses() != null
				&& contract.getUses().getUsesCode().equals("KDDV")) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "checked.png", Units.toEMU(15),
					Units.toEMU(15));
		} else if (text.contains("[uses_kddv]")
				&& (contract.getUses() == null || (contract.getUses() != null
						&& !contract.getUses().getUsesCode().equals("KDDV")))) {
			run.setText("", 0);
			FileInputStream f = new FileInputStream(new ClassPathResource(
					"/static/admin/img/checkbox/un-checked.png").getFile());
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			run.addPicture(f, format, "un-checked.png", Units.toEMU(15),
					Units.toEMU(15));
		}
	}

}
