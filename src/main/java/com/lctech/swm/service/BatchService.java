package com.lctech.swm.service;

import com.lctech.swm.config.Constants;
import com.lctech.swm.config.SWMConfig;
import com.lctech.swm.domain.*;
import com.lctech.swm.repository.DeviceRepository;
import com.lctech.swm.repository.InvoiceRepository;
import com.lctech.swm.repository.MeasureTransRepository;
import com.lctech.swm.web.util.SWMUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

/**
 * The service using for batch job system.
 */
@Service
public class BatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchService.class);

    final
    InvoiceRepository invoiceRepository;

    final
    DeviceRepository deviceRepository;

    final
    MeasureTransRepository measureTransRepository;

    final
    SWMConfig swmConfig;

    final
    ReportService reportService;

    @Autowired
    public BatchService(InvoiceRepository invoiceRepository, DeviceRepository deviceRepository, MeasureTransRepository measureTransRepository, SWMConfig swmConfig, ReportService reportService) {
        this.invoiceRepository = invoiceRepository;
        this.deviceRepository = deviceRepository;
        this.measureTransRepository = measureTransRepository;
        this.swmConfig = swmConfig;
        this.reportService = reportService;
    }

    @Scheduled(cron = "0 31 13 22 * *")
    public void createInvoice(){
        LocalDate currentDate = LocalDate.now();
        Integer month = currentDate.getMonthValue();
        Integer year = currentDate.getYear();
        List<Device> devices = deviceRepository.findAll();
        for (Device device: devices) {
            List<MeasureTransaction> measureTransactions
                    = measureTransRepository.getByMonthAndYear(device.getCode(), month, year);
            if (measureTransactions != null && !measureTransactions.isEmpty()) {
                Invoice invoice = new Invoice();
                Contract contract = device.getContractDeviceMapping().getContract();
                Users users = contract.getUser();
                Company company = contract.getCompany();
                Uses uses = contract.getUses();

                invoice.setCompanyName(company.getName());
                invoice.setCompanyAddress(company.getAddress());
                invoice.setCompanyTaxCode(company.getTaxCode());
                invoice.setCustomerName(users.getName());
                invoice.setCustomerAddress(SWMUtil.getDisplayAddress(users));
                invoice.setCustomerTaxCode(users.getTaxCode());
                invoice.setContractNumber(contract.getNumberContract());
                invoice.setUsesCode(uses.getUsesCode());
                invoice.setUsesName(uses.getName());
                invoice.setMonth(month);
                invoice.setYear(year);

                Double firstMetric = measureTransactions.get(0).getAmount();
                Double lastMetric = measureTransactions.get(measureTransactions.size() - 1).getAmount();
                invoice.setOldMetric(firstMetric);
                invoice.setNewMetric(lastMetric);
                invoice.setAmount(lastMetric - firstMetric);
                invoice.setEnvironmentTax(swmConfig.getEnvironmentalProtectionFee());
                invoice.setVatTax(swmConfig.getVatTax());

                if (Constants.USES_CODE_SH.equals(invoice.getUsesCode())) {
                    Double money = 0D;
                    List<PriceDetail> priceDetails = reportService.getPriceDetail(invoice.getAmount());
                    for (PriceDetail priceDetail : priceDetails) {
                        money += priceDetail.getTotal();
                    }
                    invoice.setWaterMoney(money);
                } else {
                    invoice.setPrice(uses.getPrices().get(0).getPrice());
                    invoice.setWaterMoney(invoice.getAmount() * invoice.getPrice());
                }

                invoice.setEnvMoney(invoice.getWaterMoney() * invoice.getEnvironmentTax() / 100);
                invoice.setVatMoney(invoice.getWaterMoney() * invoice.getVatTax() / 100);
                invoice.setTotalMoney(invoice.getWaterMoney() + invoice.getEnvMoney() + invoice.getVatMoney());
                invoice.setDevice(device);
                invoice.setStatus((byte) 0);
                invoice.setCreatedDate(java.sql.Date.valueOf(currentDate));
                invoiceRepository.save(invoice);
                LOGGER.info("Created invoice for Device " + invoice.getDevice().getCode()
                        + " On " + invoice.getMonth() + "/" + invoice.getYear());
            }
        }
    }

}
