package com.etl.es.entity;

import java.util.Date;

import com.etl.utils.DateUtils;

/**
 * 计算跳出率的临时数据
 * 
 * @author kerl
 *
 */
public class TempWebRateStats extends BaseEsEntity {

	public TempWebRateStats(String sessionId, Long reqCount, String entryURL, String sourceURL, String exitURL,
			Long uniqueReqCount, Date statsDate, Long sessionPeriod) {
		super();
		this.sessionId = sessionId;
		this.reqCount = reqCount;
		this.entryURL = entryURL;
		this.sourceURL = sourceURL;
		this.exitURL = exitURL;
		this.uniqueReqCount = uniqueReqCount;
		this.statsDate = statsDate;
		this.sessionPeriod = sessionPeriod;
	}

	private String sessionId;
	private Long reqCount;
	private String entryURL;
	private String sourceURL;
	private String exitURL;
	private Long uniqueReqCount;
	private Date statsDate;
	private Long sessionPeriod;

	public String getId() {
		return sessionId + "|" + DateUtils.formatDate(statsDate);

	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getReqCount() {
		return reqCount;
	}

	public void setReqCount(Long reqCount) {
		this.reqCount = reqCount;
	}

	public String getEntryURL() {
		return entryURL;
	}

	public void setEntryURL(String entryURL) {
		this.entryURL = entryURL;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public String getExitURL() {
		return exitURL;
	}

	public void setExitURL(String exitURL) {
		this.exitURL = exitURL;
	}

	public Long getUniqueReqCount() {
		return uniqueReqCount;
	}

	public void setUniqueReqCount(Long uniqueReqCount) {
		this.uniqueReqCount = uniqueReqCount;
	}

	public Date getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(Date statsDate) {
		this.statsDate = statsDate;
	}

	public Long getSessionPeriod() {
		return sessionPeriod;
	}

	public void setSessionPeriod(Long sessionPeriod) {
		this.sessionPeriod = sessionPeriod;
	}

}
