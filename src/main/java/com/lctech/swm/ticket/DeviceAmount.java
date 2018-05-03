package com.lctech.swm.ticket;

import java.util.Date;

import com.lctech.swm.domain.Device;

public class DeviceAmount {
	private Device de;
	private Date startdate;
	private Float startAmount;
	private Date enddate;
	private Float endAmount;
	public Device getDe() {
		return de;
	}
	public void setDe(Device de) {
		this.de = de;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Float getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(Float startAmount) {
		this.startAmount = startAmount;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Float getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(Float endAmount) {
		this.endAmount = endAmount;
	}
	
	
}
