package com.etl.es.entity;

import java.util.Date;

import com.etl.utils.DateUtils;

/**
 * Es basic Entity which contains some common proproties
 * 
 * @author kerl
 *
 */
public class BaseEsEntity {

	public BaseEsEntity() {
		dmlTime = DateUtils.getCurrent();
	}

	private Date dmlTime;

	public Date getDmlTime() {
		return dmlTime;
	}

	public void setDmlTime(Date dmlTime) {
		this.dmlTime = dmlTime;
	}

}
