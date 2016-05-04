package com.etl.dao.entity;

public class PayMysql {
	private Integer uid;
	private String reg_time_start;
	private String reg_time_end;
	private Integer paymoney;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getReg_time_start() {
		return reg_time_start;
	}
	public void setReg_time_start(String reg_time_start) {
		this.reg_time_start = reg_time_start;
	}
	public String getReg_time_end() {
		return reg_time_end;
	}
	public void setReg_time_end(String reg_time_end) {
		this.reg_time_end = reg_time_end;
	}
	public Integer getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(Integer paymoney) {
		this.paymoney = paymoney;
	}
	
	
}
