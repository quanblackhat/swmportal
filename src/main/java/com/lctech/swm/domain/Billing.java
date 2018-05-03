package com.lctech.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the billing database table.
 * 
 */
@Entity
@NamedQuery(name="Billing.findAll", query="SELECT b FROM Billing b")
public class Billing implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BILLING_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BILLING_ID_GENERATOR")
	private long id;

	private BigDecimal amount;

	@Column(name="contract_id")
	private BigDecimal contractId;

	@Column(name="created_by")
	private BigDecimal createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date fromDate;

	@Column(name="price_id")
	private BigDecimal priceId;

	private BigDecimal status;

	@Temporal(TemporalType.DATE)
	@Column(name="to_date")
	private Date toDate;

	@Column(name="total_price")
	private BigDecimal totalPrice;

	@Column(name="updated_by")
	private BigDecimal updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_date")
	private Date updatedDate;

	public Billing() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getContractId() {
		return this.contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public BigDecimal getCreatedBy() {
		return this.createdBy;
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

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public BigDecimal getPriceId() {
		return this.priceId;
	}

	public void setPriceId(BigDecimal priceId) {
		this.priceId = priceId;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
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