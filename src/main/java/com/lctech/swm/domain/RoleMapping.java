package com.lctech.swm.domain;

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
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "roles_mapping")
@NamedQuery(name = "RoleMapping.findAll", query = "SELECT d FROM RoleMapping d")
public class RoleMapping {
	@Id
	@SequenceGenerator(name = "seq_roles_mapping",
			sequenceName = "seq_roles_mapping")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "seq_roles_mapping")
	private long id;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;
	@Column(name = "group_role_id")
	private Long groupRole;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "update_date")
	private Date updateDate;
	@Column(name = "update_by")
	private Long updateby;
	@Column(name = "create_by")
	private Long createby;

	@Column(name = "status")
	private Boolean status;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getGroupRole() {
		return groupRole;
	}
	public void setGroupRole(Long groupRole) {
		this.groupRole = groupRole;
	}
	public Long getUpdateby() {
		return updateby;
	}
	public void setUpdateby(Long updateby) {
		this.updateby = updateby;
	}
	public Long getCreateby() {
		return createby;
	}
	public void setCreateby(Long createby) {
		this.createby = createby;

	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RoleMapping [id=" + id + ", role=" + role + ", groupRole="
				+ groupRole + ", createDate=" + createDate + ", updateDate="
				+ updateDate + ", updateby=" + updateby + ", createby="
				+ createby + ", status=" + status + "]";

	}

}
