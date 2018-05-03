package com.lctech.swm.domain;

import java.util.Date;
import java.util.List;


import javax.persistence.*;

@Entity
@Table(name = "group_role")
public class GroupRole {

	@Id
	@Column(name="id")
	@SequenceGenerator(name = "seq_grouprole", sequenceName = "seq_group_role")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_grouprole")
	private Long id;

	@Column(name= "name")
	private String name;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "create_by")
	private Long createBy;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "update_by")
	private Long updateBy;

	@OneToMany(mappedBy = "groupRole")
	private List<Account> accounts;

	@Override
	public String toString() {
		return "GroupRole [id=" + id + ", name=" + name + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", createBy=" + createBy + ", updateBy=" + updateBy + "]";
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
