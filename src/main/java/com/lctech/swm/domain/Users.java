package com.lctech.swm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "USERS_ID_GENERATOR", sequenceName = "seq_users")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "USERS_ID_GENERATOR")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "id_card")
	private String idCard;

	@Column(name = "phone_number")
	private String phoneNumber;

    @Column(name = "address_no")
    private String addressNo;

    @Column(name = "address_lane")
    private String addressLand;

    @Column(name = "address_alley")
    private String addressAlley;

    @Column(name = "address_alley2")
    private String addressAlley2;

    @Column(name = "address_street")
    private String addressStreet;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "region_id")
	private Region region;

	@Column(name = "user_code")
	private String userCode;

	@Column(name = "bank_account")
	private String bankAccount;

	@Column(name = "bank")
	private String bank;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_date")
	private Date updatedDate;

	@OneToMany(mappedBy = "user")
	private List<Account> account;

    @Column(name = "tax_code")
    private String taxCode;

    @OneToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public List<Account> getAccount() {
		return account;
	}

	public void setAccount(List<Account> accounts) {
		this.account = accounts;
	}

	public Users() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public String getAddressLand() {
        return addressLand;
    }

    public void setAddressLand(String addressLand) {
        this.addressLand = addressLand;
    }

    public String getAddressAlley() {
        return addressAlley;
    }

    public void setAddressAlley(String addressAlley) {
        this.addressAlley = addressAlley;
    }

    public String getAddressAlley2() {
        return addressAlley2;
    }

    public void setAddressAlley2(String addressAlley2) {
        this.addressAlley2 = addressAlley2;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
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

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
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

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
	public String toString() {
		return "Users [id=" + id +  ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", idCard=" + idCard + ", name=" + name + ", phoneNumber=" + phoneNumber + ", region=" + region
				+ ", company="  + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", userCode="
				+ userCode + "]";
	}

	



}