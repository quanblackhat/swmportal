package com.lctech.swm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the device database table.
 * 
 */
@Entity
@Table(name = "device")
public class Device implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_device", sequenceName = "seq_device")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "seq_device")
	private Long id;

	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date")
	private Date createdDate;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "region_id")
	private Region region;

	@Temporal(TemporalType.DATE)
	@Column(name = "setup_date")
	private Date setupDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_date")
	private Date updatedDate;

	public ContractDeviceMapping getContractDeviceMapping() {
		return contractDeviceMapping;
	}

	public void setContractDeviceMapping(ContractDeviceMapping contractDeviceMapping) {
		this.contractDeviceMapping=contractDeviceMapping;
	}

	@OneToOne(mappedBy = "device")
	private ContractDeviceMapping contractDeviceMapping;

	@OneToMany(mappedBy = "device")
	private List<MeasureTransaction> measureTransaction;

    @OneToMany(mappedBy = "device", cascade = CascadeType.MERGE)
    private List<Invoice> invoices;

	public List<MeasureTransaction> getMeasureTransaction() {
		return measureTransaction;
	}

	public void setMeasureTransaction(List<MeasureTransaction> measureTransaction) {
		this.measureTransaction=measureTransaction;
	}

	public Device() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Date getSetupDate() {
		return this.setupDate;
	}

	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
	public String toString() {
		return "Device [id=" + id + ", code=" + code + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", region="
				+ region + ", setupDate=" + setupDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + "]";
	}

}