package com.lctech.swm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Application constants.
 */

@Configuration
@PropertySource(value = {"classpath:/config/config.properties"})
public class SWMConfig {

	@Value("${pageSize}")
    private Integer pageSize;

	@Value("${dataExcel}")
	private String dataExcel;

    @Value("${vatTax}")
    private Double vatTax;

    @Value("${environmentalProtectionFee}")
    private Double environmentalProtectionFee;

    @Value("${adminEmail}")
    private String adminEmail;

    public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getDataExcel() {
		return dataExcel;
	}
	public void setDataExcel(String dataExcel) {
		this.dataExcel = dataExcel;
	}
    public Double getVatTax() {
        return vatTax;
    }
    public void setVatTax(Double vatTax) {
        this.vatTax = vatTax;
    }
    public Double getEnvironmentalProtectionFee() {
        return environmentalProtectionFee;
    }
    public void setEnvironmentalProtectionFee(Double environmentalProtectionFee) {
        this.environmentalProtectionFee = environmentalProtectionFee;
    }
    public String getAdminEmail() {
        return adminEmail;
    }
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }


}
