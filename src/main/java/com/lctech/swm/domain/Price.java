package com.lctech.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the price database table.
 * 
 */
@Entity
@NamedQuery(name="Price.findAll", query="SELECT p FROM Price p")
public class Price implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRICE_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRICE_ID_GENERATOR")
	private long id;

	@Column(name="created_by")
	private BigDecimal createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="district_id")
	private BigDecimal districtId;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;

	@Column(name="price")
	private Double price;

	@Column(name="province_id")
	private BigDecimal provinceId;

	@Column(name="region_id")
	private BigDecimal regionId;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	@Column(name="updated_by")
	private BigDecimal updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_date")
	private Date updatedDate;

	@Column(name="start_m2")
	private Integer startM2;

	@Column(name="end_m2")
	private Integer endM2;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "uses_id")
	private Uses uses;

	public Uses getUses() {
		return uses;
	}

	public void setUses(Uses uses) {
		this.uses = uses;
	}

	public Price() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getCreatedBy() {
		return this.createdBy;
	}

	public Integer getStartM2() {
		return startM2;
	}

	public void setStartM2(Integer startM2) {
		this.startM2 = startM2;
	}

	public Integer getEndM2() {
		return endM2;
	}

	public void setEndM2(Integer endM2) {
		this.endM2 = endM2;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public BigDecimal getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(BigDecimal provinceId) {
		this.provinceId = provinceId;
	}

	public BigDecimal getRegionId() {
		return this.regionId;
	}

	public void setRegionId(BigDecimal regionId) {
		this.regionId = regionId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}