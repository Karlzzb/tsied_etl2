package com.etl.dao.entity;

import java.util.Date;

import com.etl.es.entity.BaseEsEntity;

public class AdPayStats extends BaseEsEntity {

	public AdPayStats() {

	}

	public AdPayStats(String id, String domain, Float rate, Long regusercnt, Long loginusercnt, Long payusercnt,
			Long paycnt, Integer project, String projectname, Long reguserpaycnt, Long reguserpayargvcnt,
			Long loginuserpaycnt, Long loginuserpayargvcnt, Long userpaycnt, Long userpayargvcnt, Long usepayrarpu,
			Long userarpu, Float ydrate, Float tdrate, Float sdrate, String regTimeStart, String regTimeEnd,
			String loginTimeStart, String loginTimeEnd, String timeStart, String timeEnd, Long day, Integer uid,
			String source_url, Date inittime, Long sumregusercnt, Long sumaddregusercnt, Long sumloginusercnt,
			Date statsDate) {
		super();
		this.id = id;
		this.domain = domain;
		this.rate = rate;
		this.regusercnt = regusercnt;
		this.loginusercnt = loginusercnt;
		this.payusercnt = payusercnt;
		this.paycnt = paycnt;
		this.project = project;
		this.projectname = projectname;
		this.reguserpaycnt = reguserpaycnt;
		this.reguserpayargvcnt = reguserpayargvcnt;
		this.loginuserpaycnt = loginuserpaycnt;
		this.loginuserpayargvcnt = loginuserpayargvcnt;
		this.userpaycnt = userpaycnt;
		this.userpayargvcnt = userpayargvcnt;
		this.usepayrarpu = usepayrarpu;
		this.userarpu = userarpu;
		this.ydrate = ydrate;
		this.tdrate = tdrate;
		this.sdrate = sdrate;
		this.regTimeStart = regTimeStart;
		this.regTimeEnd = regTimeEnd;
		this.loginTimeStart = loginTimeStart;
		this.loginTimeEnd = loginTimeEnd;
		TimeStart = timeStart;
		TimeEnd = timeEnd;
		this.day = day;
		this.uid = uid;
		this.source_url = source_url;
		this.inittime = inittime;
		this.sumregusercnt = sumregusercnt;
		this.sumaddregusercnt = sumaddregusercnt;
		this.sumloginusercnt = sumloginusercnt;
		this.statsDate = statsDate;
	}

	private String id;
	private String domain;
	private Float rate;// 留存率
	private Long regusercnt; // 注册用户数
	private Long loginusercnt; // 登录用户数
	private Long payusercnt; // 付费用户数
	private Long paycnt;// 消费次数
	private Integer project;// 项目ID
	private String projectname;// 项目名称
	private Long reguserpaycnt; // 注册用户消费金额
	private Long reguserpayargvcnt; // 注册用户日均消费金额
	private Long loginuserpaycnt; // 登录用户消费金额
	private Long loginuserpayargvcnt; // 登录用户日均消费金额
	private Long userpaycnt; // 付费用户消费合计
	private Long userpayargvcnt; // 付费用户消费日均消费
	private Long usepayrarpu; // 付费用户平均付费金额
	private Long userarpu; // 用户平均付费金

	private Float ydrate;// 昨日留存率
	private Float tdrate;// 3天留存率
	private Float sdrate;// 7留存率

	private String regTimeStart; // 注册开始时间
	private String regTimeEnd; // 注册结束时间

	private String loginTimeStart; // 登录开始时间
	private String loginTimeEnd; // 登录结束时间

	private String TimeStart;// 开始时间
	private String TimeEnd;// 结束时间

	private Long day;// 间隔时间

	private Integer uid;// 用户uid
	private String source_url;// 广告url
	private Date inittime;// 数据时间

	private Long sumregusercnt;// 各个项目总注册用户数

	private Long sumaddregusercnt;// 各个项目新增注册用户数
	private Long sumloginusercnt;// 各个项目登录用户数

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

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Long getRegusercnt() {
		return regusercnt;
	}

	public void setRegusercnt(Long regusercnt) {
		this.regusercnt = regusercnt;
	}

	public Long getLoginusercnt() {
		return loginusercnt;
	}

	public void setLoginusercnt(Long loginusercnt) {
		this.loginusercnt = loginusercnt;
	}

	public Long getPayusercnt() {
		return payusercnt;
	}

	public void setPayusercnt(Long payusercnt) {
		this.payusercnt = payusercnt;
	}

	public Long getPaycnt() {
		return paycnt;
	}

	public void setPaycnt(Long paycnt) {
		this.paycnt = paycnt;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public Long getReguserpaycnt() {
		return reguserpaycnt;
	}

	public void setReguserpaycnt(Long reguserpaycnt) {
		this.reguserpaycnt = reguserpaycnt;
	}

	public Long getReguserpayargvcnt() {
		return reguserpayargvcnt;
	}

	public void setReguserpayargvcnt(Long reguserpayargvcnt) {
		this.reguserpayargvcnt = reguserpayargvcnt;
	}

	public Long getLoginuserpaycnt() {
		return loginuserpaycnt;
	}

	public void setLoginuserpaycnt(Long loginuserpaycnt) {
		this.loginuserpaycnt = loginuserpaycnt;
	}

	public Long getLoginuserpayargvcnt() {
		return loginuserpayargvcnt;
	}

	public void setLoginuserpayargvcnt(Long loginuserpayargvcnt) {
		this.loginuserpayargvcnt = loginuserpayargvcnt;
	}

	public Long getUserpaycnt() {
		return userpaycnt;
	}

	public void setUserpaycnt(Long userpaycnt) {
		this.userpaycnt = userpaycnt;
	}

	public Long getUserpayargvcnt() {
		return userpayargvcnt;
	}

	public void setUserpayargvcnt(Long userpayargvcnt) {
		this.userpayargvcnt = userpayargvcnt;
	}

	public Long getUsepayrarpu() {
		return usepayrarpu;
	}

	public void setUsepayrarpu(Long usepayrarpu) {
		this.usepayrarpu = usepayrarpu;
	}

	public Long getUserarpu() {
		return userarpu;
	}

	public void setUserarpu(Long userarpu) {
		this.userarpu = userarpu;
	}

	public Float getYdrate() {
		return ydrate;
	}

	public void setYdrate(Float ydrate) {
		this.ydrate = ydrate;
	}

	public Float getTdrate() {
		return tdrate;
	}

	public void setTdrate(Float tdrate) {
		this.tdrate = tdrate;
	}

	public Float getSdrate() {
		return sdrate;
	}

	public void setSdrate(Float sdrate) {
		this.sdrate = sdrate;
	}

	public String getRegTimeStart() {
		return regTimeStart;
	}

	public void setRegTimeStart(String regTimeStart) {
		this.regTimeStart = regTimeStart;
	}

	public String getRegTimeEnd() {
		return regTimeEnd;
	}

	public void setRegTimeEnd(String regTimeEnd) {
		this.regTimeEnd = regTimeEnd;
	}

	public String getLoginTimeStart() {
		return loginTimeStart;
	}

	public void setLoginTimeStart(String loginTimeStart) {
		this.loginTimeStart = loginTimeStart;
	}

	public String getLoginTimeEnd() {
		return loginTimeEnd;
	}

	public void setLoginTimeEnd(String loginTimeEnd) {
		this.loginTimeEnd = loginTimeEnd;
	}

	public String getTimeStart() {
		return TimeStart;
	}

	public void setTimeStart(String timeStart) {
		TimeStart = timeStart;
	}

	public String getTimeEnd() {
		return TimeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		TimeEnd = timeEnd;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long day) {
		this.day = day;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public Date getInittime() {
		return inittime;
	}

	public void setInittime(Date inittime) {
		this.inittime = inittime;
	}

	public Long getSumregusercnt() {
		return sumregusercnt;
	}

	public void setSumregusercnt(Long sumregusercnt) {
		this.sumregusercnt = sumregusercnt;
	}

	public Long getSumaddregusercnt() {
		return sumaddregusercnt;
	}

	public void setSumaddregusercnt(Long sumaddregusercnt) {
		this.sumaddregusercnt = sumaddregusercnt;
	}

	public Long getSumloginusercnt() {
		return sumloginusercnt;
	}

	public void setSumloginusercnt(Long sumloginusercnt) {
		this.sumloginusercnt = sumloginusercnt;
	}

	public Date getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(Date statsDate) {
		this.statsDate = statsDate;
	}

}