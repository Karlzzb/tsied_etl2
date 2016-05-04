package com.etl.es.entity;

import java.util.Date;

/**
 * web stats propeties
 * 
 * @author kerl
 *
 */
public class AdWebStats extends BaseEsEntity {

	public AdWebStats(String id, String domain, Long pv, Long uv, Long sessionStat, Long ipStats, Date statsDate) {
		super();
		this.id = id;
		this.domain = domain;
		this.pv = pv;
		this.uv = uv;
		this.sessionStat = sessionStat;
		this.ipStats = ipStats;
		this.statsDate = statsDate;
	}

	public AdWebStats() {
	}

	private String id;
	private String domain;
	private Long pv;
	private Long uv;
	private Long sessionStat;
	private Long ipStats;
	private Date statsDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public Date getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(Date statsDate) {
		this.statsDate = statsDate;
	}

}
