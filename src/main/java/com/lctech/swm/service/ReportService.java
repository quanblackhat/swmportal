package com.lctech.swm.service;


import com.lctech.swm.config.Constants;
import com.lctech.swm.config.SWMConfig;
import com.lctech.swm.domain.*;
import com.lctech.swm.repository.*;
import com.lctech.swm.utils.DBMapper;
import com.lctech.swm.web.util.SWMUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ReportService {

    final Logger logger = LoggerFactory.getLogger(ReportService.class);
    @Autowired
    PriceRepository priceRepository;

    @Autowired
    MeasureTransRepository measureRepository;

    @Autowired
    ContractRepository contractRepository;
    
    @Autowired
    ContractDeviceMappingRepository contractDeviceMappingRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    SWMConfig swmConfig;

    @Autowired
    InvoiceRepository invoiceRepository;


    //Get total money by amount and uses
    private double getTotalPrice(double amount, String usesCode){
        Double price = priceRepository.getPriceByUses(usesCode).getPrice();
        return price * amount;
    }

    public List<PriceDetail> getPriceDetail(double amount){
        List<PriceDetail> priceDetails = new ArrayList<>();
        PriceLevel[] priceLevels = DBMapper.getPriceLevelArray(priceRepository.getPriceOfHousehold());
        int j = 0;
        while (amount > 0 && j < priceLevels.length) {
            PriceDetail priceDetail = new PriceDetail();
            priceDetail.setId(j);
            priceDetail.setStart(priceLevels[j].getStart());
            priceDetail.setEnd(priceLevels[j].getEnd());
            priceDetail.setPrice(priceLevels[j].getPrice());

            if(priceLevels[j].getEnd() != null){
                double range = priceLevels[j].getEnd() - priceLevels[j].getStart();
                if(amount >= range){
                    priceDetail.setAmount(range);
                } else {
                    priceDetail.setAmount(amount);
                }
                amount = amount - range;
                priceDetails.add(priceDetail);

            } else {
                //amount: phần còn lại
                priceDetail.setAmount(amount);
                priceDetails.add(priceDetail);
                break;
            }
            j++;
        }
        for (PriceDetail priceDetail: priceDetails){
            priceDetail.setTotal(priceDetail.getAmount() * priceDetail.getPrice());
        }

        return priceDetails;
    }

    /**
     * Find all device which mount of a contract.
     */
    public List<String> findListDeviceCodesByContractNumber(String contractNumber){
    	return contractDeviceMappingRepository.findListDeviceCodesByContractNumber(contractNumber);
    }

    /**
     * Get water metric at a time
     */
    public double getAmountByDeviceCodeAndDate(String deviceCode, Date date){
        double amount = 0L;
        MeasureTransaction measureTransaction = measureRepository.getByDeviceAndTransactionDate(deviceCode, date);
        if(measureTransaction != null && measureTransaction.getAmount() > 0){
            amount = measureTransaction.getAmount();
        }
        return amount;
    }

    /**
     * Gets the total amount of water used per contract in a period
     */
	public List<TrackingMeasure> getTrackingMeasure(List<Device> devices, Date startDate, Date endDate){

        List<TrackingMeasure> trackingMeasures = new ArrayList<>();
        if (devices == null || devices.isEmpty()){
            return trackingMeasures;
        }

        for(Device device: devices){
        	Contract contract = device.getContractDeviceMapping().getContract();
            TrackingMeasure trackingMeasure = new TrackingMeasure();
            List<MeasureTransaction> measureTransactions = new ArrayList<>();
            double oldMetric = 0;
            double newMetric = 0;

            if(startDate != null && endDate != null) {
                measureTransactions = measureRepository.getByDeviceAndTransactionDate(device.getCode(), startDate, endDate);
            }

            if(measureTransactions != null && !measureTransactions.isEmpty()){
                oldMetric = measureTransactions.get(0).getAmount();
                newMetric = measureTransactions.get(measureTransactions.size() - 1).getAmount();

            }
            trackingMeasure.setCompany(contract.getCompany());
            trackingMeasure.setUser(contract.getUser());
            trackingMeasure.setUses(contract.getUses().getName());
            trackingMeasure.setContractNumber(contract.getNumberContract());
            trackingMeasure.setDeviceCode(device.getCode());
            trackingMeasure.setOldMetric(oldMetric);
            trackingMeasure.setNewMetric(newMetric);
            trackingMeasure.setAmount(newMetric - oldMetric);

            trackingMeasures.add(trackingMeasure);
        }
        return trackingMeasures;
    }

    /**
     * Gets invoice
     */
    public List<Invoice> getInvoices(List<String> deviceCodes, Integer month, Integer year){

        List<Invoice> invoices = new ArrayList<>();
        if (deviceCodes == null || deviceCodes.isEmpty()){
            return invoices;
        }

        invoices = invoiceRepository.findByYearAndMonthAndDeviceCodeIn(year, month, deviceCodes);

        for(Invoice invoice: invoices){
            if(Constants.USES_CODE_SH.equals(invoice.getUsesCode())) {
                List<PriceDetail> priceDetails = this.getPriceDetail(invoice.getAmount());
                invoice.setPriceDetails(priceDetails);
            }

        }
        return invoices;
    }
}
