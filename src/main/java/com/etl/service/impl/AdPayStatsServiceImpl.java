package com.etl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.dao.entity.AdPayStats;
import com.etl.es.BaseESOption;
import com.etl.mutilDataSource.DataSourceSwitch;
import com.etl.mutilDataSource.MutilDataSource;
import com.etl.service.IPayService;
import com.etl.utils.DateUtils;

@Service("adPayStatsServiceImpl")
public class AdPayStatsServiceImpl {

	@Autowired
	private IPayService ipayService;

	@Autowired
	private BaseESOption baseESOption;

	private void setPayValue(List<AdPayStats> saveStats, AdPayStats adPayStats, String startPayDateTime) {
		adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
		adPayStats.setStatsDate(DateUtils.parseDateTime(startPayDateTime));
		adPayStats.setProject(adPayStats.getProject());
		saveStats.add(adPayStats);
	}

//	public void regUserPayCnt(String id, Integer project, String StartTime, String EndTime, String saveIndex,
//			String saveIndexType) {
//		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
//		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);
//
//		AdPayStats adPay = new AdPayStats();
//		adPay.setId(id);
//		adPay.setRegTimeStart(StartTime);
//		adPay.setRegTimeEnd(EndTime);
//		adPay.setProject(project);
//		List<AdPayStats> payList = ipayService.findPayByPay(adPay);
//		for (AdPayStats adPayStats : payList) {
//			setPayValue(saveStats, adPayStats);
//		}
//
//		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
//	}
//
//	public void loginUserPayCnt(String id, Integer project, String StartTime, String EndTime, String saveIndex,
//			String saveIndexType) {
//		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
//		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);
//
//		AdPayStats adPay = new AdPayStats();
//		adPay.setId(id);
//		adPay.setLoginTimeStart(StartTime);
//		adPay.setLoginTimeEnd(EndTime);
//		adPay.setProject(project);
//		List<AdPayStats> payList = ipayService.findLoginUserByLoginUser(adPay);
//		for (AdPayStats adPayStats : payList) {
//			setPayValue(saveStats, adPayStats);
//		}
//
//		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
//	}

//	public void payUserCnt(String id, Integer project, String StartTime, String EndTime, String saveIndex,
//			String saveIndexType) {
//		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
//		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);
//
//		AdPayStats adPay = new AdPayStats();
//		adPay.setId(id);
//		adPay.setTimeStart(StartTime);
//		adPay.setTimeEnd(EndTime);
//		adPay.setProject(project);
//		List<AdPayStats> payList = ipayService.findUserCntByLoginUserCnt(adPay);
//		for (AdPayStats adPayStats : payList) {
//			setPayValue(saveStats, adPayStats);
//		}
//
//		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
//	}
//
//	public void payCnt(String id, Integer project, String StartTime, String EndTime, String saveIndex,
//			String saveIndexType) {
//		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
//		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);
//
//		AdPayStats adPay = new AdPayStats();
//		adPay.setId(id);
//		adPay.setTimeStart(StartTime);
//		adPay.setTimeEnd(EndTime);
//		adPay.setProject(project);
//		List<AdPayStats> payList = ipayService.findPayCntByPayCnt(adPay);
//		for (AdPayStats adPayStats : payList) {
//			setPayValue(saveStats, adPayStats);
//		}
//
//		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
//	}

	public void regUserCnt(String id, String startPayDateTime, Integer project, String StartTime, String EndTime, String saveIndex,
			String saveIndexType) {
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		adPay.setId(id);
		adPay.setRegTimeStart(StartTime);
		adPay.setRegTimeEnd(EndTime);
		adPay.setProject(project);
		List<AdPayStats> payList = ipayService.findRegUserCntByRegUserCnt(adPay);
		for (AdPayStats adPayStats : payList) {

			setPayValue(saveStats, adPayStats, startPayDateTime);
		}

		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
	}

	public void loginUserCnt(String id, String startPayDateTime, Integer project, String StartTime, String EndTime, String saveIndex,
			String saveIndexType) {
		// AdPayStats pay = new AdPayStats();
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		adPay.setId(id);
		adPay.setLoginTimeStart(StartTime);
		adPay.setLoginTimeEnd(EndTime);
		adPay.setProject(project);
		List<AdPayStats> payList = ipayService.findLoginUserCntByLoginUserCnt(adPay);
		for (AdPayStats adPayStats : payList) {
			setPayValue(saveStats, adPayStats, startPayDateTime);
		}

		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
	}

	public void ydRetRate(String id, String startPayDateTime, Integer project, String StartTime, String EndTime, String saveIndex,
			String saveIndexType) {
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		adPay.setId(id);
		adPay.setDay((long) 1);
		adPay.setTimeStart(StartTime);
		adPay.setTimeEnd(EndTime);
		adPay.setProject(project);
		List<AdPayStats> payList = ipayService.findRetRateByRetRate(adPay);
		for (AdPayStats adPayStats : payList) {
			adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
			adPayStats.setYdrate(adPayStats.getRate());
			adPayStats.setSource_url(adPayStats.getSource_url());
			adPayStats.setProject(adPayStats.getProject());
			adPayStats.setStatsDate(DateUtils.parseDateTime(startPayDateTime));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
	}

	public void tdRetRate(String id, String startPayDateTime, Integer project, String StartTime, String EndTime, String saveIndex,
			String saveIndexType) {
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		adPay.setId(id);
		adPay.setDay((long) 3);
		adPay.setTimeStart(StartTime);
		adPay.setTimeEnd(EndTime);
		adPay.setProject(project);
		List<AdPayStats> payList = ipayService.findRetRateByRetRate(adPay);
		for (AdPayStats adPayStats : payList) {
			adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
			adPayStats.setTdrate(adPayStats.getRate());
			adPayStats.setSource_url(adPayStats.getSource_url());
			adPayStats.setProject(adPayStats.getProject());
			adPayStats.setStatsDate(DateUtils.parseDateTime(startPayDateTime));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
	}

	public void sdRetRate(String id, String startPayDateTime, Integer project, String StartTime, String EndTime, String saveIndex,
			String saveIndexType) {
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		adPay.setId(id);
		adPay.setDay((long) 7);
		adPay.setTimeStart(StartTime);
		adPay.setTimeEnd(EndTime);
		adPay.setProject(project);
		List<AdPayStats> payList = ipayService.findRetRateByRetRate(adPay);
		for (AdPayStats adPayStats : payList) {
			adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
			adPayStats.setSdrate(adPayStats.getRate());
			adPayStats.setSource_url(adPayStats.getSource_url());
			adPayStats.setProject(adPayStats.getProject());
			adPayStats.setStatsDate(DateUtils.parseDateTime(startPayDateTime));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
	}

	public void sumRegUserCnt(String id, Integer project, String projectName, String StartTime, String EndTime,
			String saveIndex, String saveIndexType) {
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		adPay.setId(id);
		// adPay.setLoginTimeStart(StartTime);
		adPay.setRegTimeEnd(EndTime);
		adPay.setProjectname(projectName);
		List<AdPayStats> payList = ipayService.findSumRegUserCntBySumRegUserCnt(adPay);
		for (AdPayStats adPayStats : payList) {
			adPayStats.setId(adPayStats.getId() + "-local-" + project);
			adPayStats.setProject(project);
//			adPayStats.setSource_url("local");
			System.out.println(adPayStats.getSumregusercnt());
			adPayStats.setSumregusercnt(adPayStats.getSumregusercnt());
			adPayStats.setStatsDate(DateUtils.parseDateTime(StartTime));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
	}

	public void SumLoginUser(String id, Integer project, String StartTime, String EndTime, String saveIndex,
			String saveIndexType) {
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		adPay.setId(id);
		adPay.setLoginTimeStart(StartTime);
		adPay.setLoginTimeEnd(EndTime);
		adPay.setProject(project);
		List<AdPayStats> payList = ipayService.findSumLoginUserCntBySumLoginUserCnt(adPay);
		for (AdPayStats adPayStats : payList) {
			adPayStats.setId(adPayStats.getId() + "-local-" + project);
			adPayStats.setProject(project);
//			adPayStats.setSource_url("local");
			adPayStats.setSumloginusercnt(adPayStats.getSumloginusercnt());
			adPayStats.setStatsDate(DateUtils.parseDateTime(StartTime));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
	}
}
