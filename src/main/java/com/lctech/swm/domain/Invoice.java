package com.lctech.swm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_invoice", sequenceName = "seq_invoice")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_invoice")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "company_tax_code")
    private String companyTaxCode;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_tax_code")
    private String customerTaxCode;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "uses_code")
    private String usesCode;

    @Column(name = "uses_name")
    private String usesName;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "old_metric")
    private Double oldMetric;

    @Column(name = "new_metric")
    private Double newMetric;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "price")
    private Double price;

    @Column(name = "environment_tax")
    private Double environmentTax;

    @Column(name = "vat_tax")
    private Double vatTax;

    @Column(name = "water_money")
    private Double waterMoney;

    @Column(name = "vat_money")
    private Double vatMoney;

    @Column(name = "env_money")
    private Double envMoney;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name = "status")
    private Byte status;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;

    @Transient
    private List<PriceDetail> priceDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyTaxCode() {
        return companyTaxCode;
    }

    public void setCompanyTaxCode(String companyTaxCode) {
        this.companyTaxCode = companyTaxCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerTaxCode() {
        return customerTaxCode;
    }

    public void setCustomerTaxCode(String customerTaxCode) {
        this.customerTaxCode = customerTaxCode;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }


    public String getUsesCode() {
        return usesCode;
    }

    public void setUsesCode(String usesCode) {
        this.usesCode = usesCode;
    }

    public String getUsesName() {
        return usesName;
    }

    public void setUsesName(String usesName) {
        this.usesName = usesName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getOldMetric() {
        return oldMetric;
    }

    public void setOldMetric(Double oldMetric) {
        this.oldMetric = oldMetric;
    }

    public Double getNewMetric() {
        return newMetric;
    }

    public void setNewMetric(Double newMetric) {
        this.newMetric = newMetric;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getEnvironmentTax() {
        return environmentTax;
    }

    public void setEnvironmentTax(Double environmentTax) {
        this.environmentTax = environmentTax;
    }

    public Double getVatTax() {
        return vatTax;
    }

    public void setVatTax(Double vatTax) {
        this.vatTax = vatTax;
    }

    public Double getWaterMoney() {
        return waterMoney;
    }

    public void setWaterMoney(Double waterMoney) {
        this.waterMoney = waterMoney;
    }

    public Double getVatMoney() {
        return vatMoney;
    }

    public void setVatMoney(Double vatMoney) {
        this.vatMoney = vatMoney;
    }

    public Double getEnvMoney() {
        return envMoney;
    }

    public void setEnvMoney(Double envMoney) {
        this.envMoney = envMoney;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<PriceDetail> getPriceDetails() {
        return priceDetails;
    }

    public void setPriceDetails(List<PriceDetail> priceDetails) {
        this.priceDetails = priceDetails;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "deviceCode='" + device.getCode() + '\'' +
                ", oldMetric=" + oldMetric +
                ", newMetric=" + newMetric +
                ", amount=" + amount +
                ", price=" + price +
                ", waterMoney=" + waterMoney +
                ", vatMoney=" + vatMoney +
                ", envMoney=" + envMoney +
                ", totalMoney=" + totalMoney +
                '}';
    }
}
