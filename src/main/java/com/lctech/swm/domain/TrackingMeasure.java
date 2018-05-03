package com.lctech.swm.domain;

import java.util.List;

/**
 * Temporary class has include information of measure tracking.
 */
public class TrackingMeasure {
    private Company company;
    private Users user;
    private String contractNumber;
    private String deviceCode;
    private double oldMetric;
    private double newMetric;
    private double amount;
    private String uses;
    private double waterMoney;
    private double vatMoney;
    private double environmentMoney;
    private double totalMoney;

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getVatMoney() {
        return vatMoney;
    }

    public void setVatMoney(double vatMoney) {
        this.vatMoney = vatMoney;
    }

    public double getEnvironmentMoney() {
        return environmentMoney;
    }

    public void setEnvironmentMoney(double enviromentMoney) {
        this.environmentMoney = enviromentMoney;
    }

    // Price for household
    private List<PriceDetail> priceDetails;
    //Price for enterprise
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<PriceDetail> getPriceDetails() {
        return priceDetails;
    }

    public void setPriceDetails(List<PriceDetail> priceDetails) {
        this.priceDetails = priceDetails;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public double getOldMetric() {
        return oldMetric;
    }

    public void setOldMetric(double oldMetric) {
        this.oldMetric = oldMetric;
    }

    public double getNewMetric() {
        return newMetric;
    }

    public void setNewMetric(double newMetric) {
        this.newMetric = newMetric;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public double getWaterMoney() {
        return waterMoney;
    }

    public void setWaterMoney(double money) {
        this.waterMoney = money;
    }


}
