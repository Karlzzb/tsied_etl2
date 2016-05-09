package com.etl.es;

public class Script {

	public Script(String indexName, long startTime, long endTime, String dateRange) {
		super();
		this.indexName = indexName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.dateRange = dateRange;
	}

	public Script(String indexName, long startTime, long endTime, String dateRange, String advertAddr) {
		super();
		this.indexName = indexName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.advertAddr = advertAddr;
		this.dateRange = dateRange;
	}

	private String indexName;// ElasticSearch indexName

	private String indexType;// ElasticSearch indexType

	private long startTime;// 开始时间

	private long endTime;// 结束时间

	private String dateRange;// 时间名称

	private String advertAddr;// 广告地址

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getAdvertAddr() {
		return advertAddr;
	}

	public void setAdvertAddr(String advertAddr) {
		this.advertAddr = advertAddr;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

}
