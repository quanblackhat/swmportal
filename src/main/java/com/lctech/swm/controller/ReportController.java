package com.lctech.swm.controller;

import com.lctech.swm.config.Constants;
import com.lctech.swm.config.SWMConfig;
import com.lctech.swm.domain.*;
import com.lctech.swm.service.*;
import com.lctech.swm.web.util.SWMUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/report")
public class ReportController {

    final private ReportService reportService;
    final private CommonService commonService;
    final private ContractService contractService;
    final private UsersService usersService;
    final private CompanyService companyService;
	final private ProvinceService provinceService;
	final private DistrictService districtService;
	final private RegionService regionService;
	final private ContractDeviceMappingService contractDeviceMappingService;
    final private SWMConfig swmConfig;
    final private DeviceService deviceService;

    final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public ReportController(ContractService contractService, ReportService reportService, CommonService commonService,
                            UsersService usersService, CompanyService companyService, ProvinceService provinceService,
                            DistrictService districtService, RegionService regionService,
                            ContractDeviceMappingService contractDeviceMappingService,
                            SWMConfig swmConfig, DeviceService deviceService) {
        this.contractService = contractService;
        this.reportService = reportService;
        this.commonService = commonService;
        this.usersService = usersService;
        this.companyService = companyService;
        this.provinceService = provinceService;
        this.districtService = districtService;
        this.regionService = regionService;
        this.contractDeviceMappingService = contractDeviceMappingService;
        this.swmConfig = swmConfig;
        this.deviceService = deviceService;
    }

    /**
     * Get list tracking measurement report.
     */
    @RequestMapping(value = {"/trackingWater"}, method = RequestMethod.GET)
    public String getTrackingWater(Model model,
										 @RequestParam(value = "companyId", required = false) Long companyId,
										 @RequestParam(value = "userName", required = false) String userName,
										 @RequestParam(value = "provinceId", required = false) Long provinceId,
										 @RequestParam(value = "districtId", required = false) Long districtId,
								 		 @RequestParam(value = "regionId", required = false) Long regionId,
										 @RequestParam(value = "startDateString", required = false) String startDateString,
										 @RequestParam(value = "endDateString", required = false) String endDateString,
                                         @RequestParam(value = "page", required = false) Integer page) {

        String redirectPage = "admin/report/trackingWater";
        if (page != null) {
            redirectPage = "admin/report/trackingWaterPager";
        }
        page = (page != null) ? page : 1;

        /*
         * Init and processing input params.
         */
		userName = (userName != null ? userName : StringUtils.EMPTY);
		companyId = (companyId != null ? companyId : -1);
		provinceId = (provinceId != null) ? provinceId : -1;
		districtId = (districtId != null) ? districtId : -1;
		regionId = (regionId != null) ? regionId : -1;
		Long userId = -1L;
        String role = StringUtils.EMPTY;
		Date startDate = null, endDate = null;
		try {
			if (startDateString != null && !startDateString.trim().equals(StringUtils.EMPTY)) {
				startDate = SWMUtil.formatDate(startDateString);
			}
			if (endDateString != null && !endDateString.trim().equals(StringUtils.EMPTY)) {
				endDate = SWMUtil.formatDate(endDateString);
			}
		} catch (ParseException parseException) {
			logger.error("Error: ", parseException);
		}

		//Get role to show right data
        Account currentAccount = commonService.findCurrentAccount();
        List<Company> companies = new ArrayList<>();
        if(currentAccount != null){
            if(currentAccount.getLevel() == Constants.LEVEL_TYPE_COMPANY){
                companies.addAll(companyService.findByAccount(currentAccount));
                if(!companies.isEmpty()){
                    companyId = companies.get(0).getId();
                    role = "COMPANY";
                }
            } else if (currentAccount.getLevel() != Constants.LEVEL_TYPE_ADMIN){
                userId = currentAccount.getUser().getId();
                role = "CUSTOMER";
            } else {
                companies.addAll(companyService.findAll());
                role = "ADMIN";
            }
        }

        /*
         * Add attribute to Model
         */
        model.addAttribute("role", role);
        model.addAttribute("userName", userName);
        model.addAttribute("provinceId", provinceId);
        model.addAttribute("districtId", districtId);
        model.addAttribute("regionId", regionId);
        model.addAttribute("startDate", startDateString);
        model.addAttribute("endDate", endDateString);
        model.addAttribute("companyId", companyId);
        model.addAttribute("companies", companies);
        model.addAttribute("provinces", provinceService.findAll());
        model.addAttribute("districts", districtService.findByProvince(provinceId));
        model.addAttribute("regions", regionService.findByRegion(districtId));

        //On the first request, don't query database for measure transaction
        if(startDate == null || endDate == null){
            return redirectPage;
        }

        //Get data
        Pageable pageable = new PageRequest(page - 1, swmConfig.getPageSize());
        Page<Device> devicesPage = contractDeviceMappingService.findByDate(userName, userId, provinceId,
                districtId, regionId, companyId, startDate, endDate, pageable);
        List<TrackingMeasure> measures = new ArrayList<>();
        List<Device> devices = new ArrayList<>();
        devices.addAll(devicesPage.getContent());
        measures.addAll(reportService.getTrackingMeasure(devices, startDate, endDate));
        model.addAttribute("page", page);
        model.addAttribute("measures", measures);
        model.addAttribute("totalPages", devicesPage.getTotalPages());
        return redirectPage;
    }

	/**
	 * Get list tracking money report.
	 */
	@RequestMapping(value = {"/trackingMoney"}, method = RequestMethod.GET)
	public String getTrackingMoney(Model model,
									 @RequestParam(value = "companyId", required = false) Long companyId,
									 @RequestParam(value = "userName", required = false) String userName,
									 @RequestParam(value = "provinceId", required = false) Long provinceId,
									 @RequestParam(value = "districtId", required = false) Long districtId,
									 @RequestParam(value = "regionId", required = false) Long regionId,
									 @RequestParam(value = "month", required = false) Integer month,
									 @RequestParam(value = "year", required = false) Integer year,
									 @RequestParam(value = "page", required = false) Integer page) {

		String redirectPage = "admin/report/trackingMoney";
		if (page != null) {
			redirectPage = "admin/report/trackingMoneyPager";
		}
		page = (page != null) ? page : 1;

		//Initial list of months and years.
		List<Integer> months = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		List<Integer> years = new ArrayList<>();
		int currentYear = Year.now().getValue();
		for(int i = 2018; i <= currentYear; i++){
			years.add(i);
		}

		//Init and processing input params.
		userName = (userName != null ? userName : StringUtils.EMPTY);
		companyId = (companyId != null ? companyId : -1);
		provinceId = (provinceId != null) ? provinceId : -1;
		districtId = (districtId != null) ? districtId : -1;
		regionId = (regionId != null) ? regionId : -1;
		Long userId = -1L;
		String role = StringUtils.EMPTY;

		//Get role to show right data
		Account currentAccount = commonService.findCurrentAccount();
		List<Company> companies = new ArrayList<>();
		if(currentAccount != null){
			if(currentAccount.getLevel() == Constants.LEVEL_TYPE_COMPANY){
				companies.addAll(companyService.findByAccount(currentAccount));
				if(!companies.isEmpty()){
					companyId = companies.get(0).getId();
					role = "COMPANY";
				}
			} else if (currentAccount.getLevel() != Constants.LEVEL_TYPE_ADMIN){
				userId = currentAccount.getUser().getId();
				role = "CUSTOMER";
			} else {
				companies.addAll(companyService.findAll());
				role = "ADMIN";
			}
		}

		/*
		 * Add attribute to Model
		 */
		model.addAttribute("role", role);
		model.addAttribute("userName", userName);
		model.addAttribute("provinceId", provinceId);
		model.addAttribute("districtId", districtId);
		model.addAttribute("regionId", regionId);
		model.addAttribute("months", months);
		model.addAttribute("years", years);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("companyId", companyId);
		model.addAttribute("companies", companies);
		model.addAttribute("provinces", provinceService.findAll());
		model.addAttribute("districts", districtService.findByProvince(provinceId));
		model.addAttribute("regions", regionService.findByRegion(districtId));

		//On the first request, don't query database for measure transaction
		if(month == null || month == -1){
			return redirectPage;
		}
		//Get data
		Pageable pageable = new PageRequest(page - 1, swmConfig.getPageSize());
		Page<Device> devicesPage = contractDeviceMappingService.findByMonthAndYear(userName, userId, provinceId,
				districtId, regionId, companyId, month, year, pageable);

		List<Device> devices = new ArrayList<>();
		devices.addAll(devicesPage.getContent());

        List<String> deviceCodes = new ArrayList<>();
        for (Device device: devices){
            deviceCodes.add(device.getCode());
        }
        List<Invoice> invoices = new ArrayList<>();
        invoices.addAll(reportService.getInvoices(deviceCodes, month, year));
		model.addAttribute("page", page);
		model.addAttribute("measures", invoices);
		model.addAttribute("totalPages", devicesPage.getTotalPages());
		return redirectPage;
	}

    @RequestMapping(value = {"/exportBill"}, method = RequestMethod.GET)
    public String exportBill(Model model,
                                   @RequestParam(value = "deviceCode", required = false) String deviceCode,
                                   @RequestParam(value = "month") Integer month,
                                   @RequestParam(value = "year") Integer year){

        List<String> deviceCodes = Arrays.asList(deviceCode);
	    //Common
        List<Invoice> invoices = new ArrayList<>();
        invoices.addAll(reportService.getInvoices(deviceCodes, month, year));
        model.addAttribute("timeUsed", SWMUtil.getDisplayPeriodDate(month, year));
        model.addAttribute("invoices", invoices);

        return "admin/report/invoice";
    }

    /**
     *  Export multiple bills
     */
	@RequestMapping(value = {"/exportBills"}, method = RequestMethod.GET)
	public String exportBills(Model model,
                              @RequestParam(value = "deviceCodes") String deviceCodesInput,
                              @RequestParam(value = "month") Integer month,
                              @RequestParam(value = "year") Integer year) {

        String[] idLst = deviceCodesInput.split(",");
        List<String> deviceCodes = new ArrayList<>();
        deviceCodes.addAll(Arrays.asList(idLst));

        List<Invoice> invoices = new ArrayList<>();
        invoices.addAll(reportService.getInvoices(deviceCodes, month, year));

        model.addAttribute("timeUsed", SWMUtil.getDisplayPeriodDate(month, year));
        model.addAttribute("invoices", invoices);

        return "admin/report/invoice";

	}

}
