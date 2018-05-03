package com.lctech.swm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the measure_transactions database table.
 * 
 */
@Entity
@Table(name = "measure_transactions")
public class MeasureTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_measure_transactions", sequenceName = "seq_measure_transactions")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_measure_transactions")
	Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "device_id")
	private Device device;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_date")
	private Date transactionDate;

	@Column(name = "amount")
	private Double amount;
	
	@JoinColumn(name = "ph")
	private Float ph;
	
	@JoinColumn(name = "ec")
	private Float ec;
	
	@JoinColumn(name = "turbidity")
	private Float turbidity;
	
	@JoinColumn(name = "temperature")
	private Float temperature;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date")
	private Date createdDate;

	public MeasureTransaction() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Float getEc() {
		return this.ec;
	}

	public void setEc(Float ec) {
		this.ec = ec;
	}

	public Float getPh() {
		return this.ph;
	}

	public void setPh(Float ph) {
		this.ph = ph;
	}

	public Float getTemperature() {
		return this.temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Float getTurbidity() {
		return this.turbidity;
	}

	public void setTurbidity(Float turbidity) {
		this.turbidity = turbidity;
	}

	@Override
	public String toString() {
		return "MeasureTransaction{" +
				"id=" + id +
				", device=" + device +
				", transactionDate=" + transactionDate +
				", amount=" + amount +
				", ph=" + ph +
				", ec=" + ec +
				", turbidity=" + turbidity +
				", temperature=" + temperature +
				", createdDate=" + createdDate +
				'}';
	}
}