package com.lctech.swm.domain;

import java.util.List;

public class GroupRoleExtend{
	private String name;
	private List<RoleMapping> rm;


	@Override
	public String toString() {
		return "GroupRoleExtend [rm=" + rm + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RoleMapping> getRm() {
		return rm;
	}

	public void setRm(List<RoleMapping> rm) {
		this.rm = rm;
	}





	
	
	

}
