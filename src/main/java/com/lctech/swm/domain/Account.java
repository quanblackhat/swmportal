package com.lctech.swm.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACCOUNT_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACCOUNT_ID_GENERATOR")
	private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @JoinColumn(name = "status")
    private Long status;

    @Temporal(TemporalType.DATE)
    @Column(name="created_date")
    private Date createdDate;

    @Column(name="created_by")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name="updated_date")
    private Date updatedDate;

    @Column(name="updated_by")
    private Long updatedBy;

    @Fetch(FetchMode.JOIN)
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "grouprole_id")
	private GroupRole groupRole;

    @Column(name="level_type")
    private Integer level;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Account() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
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


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public GroupRole getGroupRole() {
		return groupRole;
	}

	public void setGroupRole(GroupRole groupRole) {
		this.groupRole = groupRole;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", username="
				+ username + ", password=" + password + ", level=" + level + ", group=Role=" + groupRole
				+ ", status=" + status + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", user=" + user +"]";
	}



}