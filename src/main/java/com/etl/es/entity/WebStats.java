package com.etl.es.entity;

import java.util.Date;

import com.etl.utils.DateUtils;

/**
 * web standard stats Entity
 * 
 * @author kerl
 *
 */
public class WebStats extends BaseEsEntity {

	public WebStats(String sourceUrl, String request_url, Long pv, Long uv, Long sessionStat, Long ipStats,
			Date statsDate) {
		super();
		this.request_url = request_url;
		this.source_url = sourceUrl;
		this.pv = pv;
		this.uv = uv;
		this.sessionStat = sessionStat;
		this.ipStats = ipStats;
		this.statsDate = statsDate;
	}

	public WebStats(String source_url, String request_url, Date statsDate, Long exitSessionCount,
			Long bounceSessionCount, Double sessionTime, Double reqPages) {
		super();
		this.source_url = source_url;
		this.request_url = request_url;
		this.statsDate = statsDate;
		this.exitSessionCount = exitSessionCount;
		this.bounceSessionCount = bounceSessionCount;
		this.sessionTime = sessionTime;
		this.reqPages = reqPages;
	}

	/**
	 * 来源URL
	 */
	private String source_url;

	/**
	 * 用户请求的URL
	 */
	private String request_url;

	private Long pv;
	private Long uv;

	/**
	 * 会话量统计
	 */
	private Long sessionStat;

	/**
	 * 唯一IP统计
	 */
	private Long ipStats;

	/**
	 * 注册用户统计
	 */
	private Long registedUsers;

	/**
	 * 统计日志
	 */
	private Date statsDate;

	/**
	 * 从当前request_url退出的session数量
	 */
	private Long exitSessionCount;

	/**
	 * 从当前request_url跳出的session数量
	 */
	private Long bounceSessionCount;

	/**
	 * 平均会话时长
	 */
	private Double sessionTime;

	/**
	 * 平均访问页面数
	 */
	private Double reqPages;

	public Date getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(Date statsDate) {
		this.statsDate = statsDate;
	}

	public String getId() {
		return "[" + request_url + "]," + source_url + "],[" + DateUtils.formatDate(statsDate) + "]";
	}

	public Long getPv() {
		return pv;
	}

	public void setPv(Long pv) {
		this.pv = pv;
	}

	public Long getUv() {
		return uv;
	}

	public void setUv(Long uv) {
		this.uv = uv;
	}

	public Long getSessionStat() {
		return sessionStat;
	}

	public void setSessionStat(Long sessionStat) {
		this.sessionStat = sessionStat;
	}

	public Long getIpStats() {
		return ipStats;
	}

	public void setIpStats(Long ipStats) {
		this.ipStats = ipStats;
	}

	public Long getRegistedUsers() {
		return registedUsers;
	}

	public void setRegistedUsers(Long registedUsers) {
		this.registedUsers = registedUsers;
	}

	public String getRequest_url() {
		return request_url;
	}

	public void setRequest_url(String request_url) {
		this.request_url = request_url;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public Long getExitSessionCount() {
		return exitSessionCount;
	}

	public void setExitSessionCount(Long exitSessionCount) {
		this.exitSessionCount = exitSessionCount;
	}

	public Long getBounceSessionCount() {
		return bounceSessionCount;
	}

	public void setBounceSessionCount(Long bounceSessionCount) {
		this.bounceSessionCount = bounceSessionCount;
	}

	public Double getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(Double sessionTime) {
		this.sessionTime = sessionTime;
	}

	public Double getReqPages() {
		return reqPages;
	}

	public void setReqPages(Double reqPages) {
		this.reqPages = reqPages;
	}

}
