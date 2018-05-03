package com.lctech.swm.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the uses database table.
 * 
 */
@Entity
public class Uses implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_uses", sequenceName = "seq_uses")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_uses")
	private long id;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date")
	private Date createdDate;

    @Column(name = "name")
	private String name;

	@Column(name = "uses_code")
	private String usesCode;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_date")
	private Date updatedDate;

	@OneToMany(mappedBy = "uses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Price> prices;

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    @OneToMany(mappedBy = "uses", cascade = CascadeType.MERGE)
	private List<Contract> contracts = new ArrayList<>();

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Uses() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getUsesCode() {
		return usesCode;
	}

	public void setUsesCode(String usesCode) {
		this.usesCode = usesCode;
	}

}