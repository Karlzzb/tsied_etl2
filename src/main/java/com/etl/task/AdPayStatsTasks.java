package com.etl.task;

import java.util.Calendar;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.etl.service.impl.AdPayStatsServiceImpl;
import com.etl.utils.DateUtils;

//门户 index tmp_stats  type www_stats project 2
//咨询 index news_stats  type news_stats	project 3
@Component("adPayStatsTasks")
@Aspect
public class AdPayStatsTasks {

	private static Logger log = LoggerFactory.getLogger(AdPayStatsTasks.class);
	@Autowired
	private AdPayStatsServiceImpl adPayStatsServiceImpl;

	@Scheduled(cron = "0 20 1 * * ?")
	public void adWwwUserPayStatsAllDaily() {

		try {

			Integer dateInterval = -1;
			String id = DateUtils.formatDate(DateUtils.addDate(
					DateUtils.getDateBegin(DateUtils.getCurrent()),
					Calendar.DATE, dateInterval));

			String startDateTime = DateUtils.formatDateTime(DateUtils.addDate(
					DateUtils.getDateBegin(DateUtils.getCurrent()),
					Calendar.DATE, dateInterval));
			String endDateTime = DateUtils.formatDateTime(DateUtils.addDate(
					DateUtils.getDateEnd(DateUtils.getCurrent()),
					Calendar.DATE, dateInterval));

			Integer project = 2;
			String saveIndex = "tmp_stats";
			String saveIndexType = "www_stats";
			String projectName = "A_PORTAL";

			// adPayStatsServiceImpl.regUserPayCnt(id, project,
			// startDateTime,
			// endDateTime, saveIndex, saveIndexType);
			// adPayStatsServiceImpl.loginUserPayCnt(id, project,
			// startDateTime,
			// endDateTime, saveIndex, saveIndexType);
			// adPayStatsServiceImpl.payUserCnt(id, project, startDateTime,
			// endDateTime, saveIndex, saveIndexType);
			// adPayStatsServiceImpl.payCnt(id, project, startDateTime,
			// endDateTime,
			// saveIndex, saveIndexType);
			adPayStatsServiceImpl.regUserCnt(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.loginUserCnt(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.ydRetRate(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.tdRetRate(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.sdRetRate(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.sumRegUserCnt(id, project, projectName,
					startDateTime, endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.SumLoginUser(id, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
		} catch (Exception e) {
			log.error("Task  adWwwUserPayStatsAllDailyTasks failed!", e);
			return;
		}

		log.info("Task adWwwUserPayStatsAllDailyTasks Complete!");
	}

	@Scheduled(cron = "0 20 1 * * ?")
	public void adNewsUserPayStatsAllDaily() {

		try {

			Integer dateInterval = -1;
			String id = DateUtils.formatDate(DateUtils.addDate(
					DateUtils.getDateBegin(DateUtils.getCurrent()),
					Calendar.DATE, dateInterval));

			String startDateTime = DateUtils.formatDateTime(DateUtils.addDate(
					DateUtils.getDateBegin(DateUtils.getCurrent()),
					Calendar.DATE, dateInterval));
			String endDateTime = DateUtils.formatDateTime(DateUtils.addDate(
					DateUtils.getDateEnd(DateUtils.getCurrent()),
					Calendar.DATE, dateInterval));

			Integer project = 3;
			String saveIndex = "news_stats";
			String saveIndexType = "news_stats";
			String projectName = "A_NEWS";

			// adPayStatsServiceImpl.regUserPayCnt(id, project,
			// startDateTime,
			// endDateTime, saveIndex, saveIndexType);
			// adPayStatsServiceImpl.loginUserPayCnt(id, project,
			// startDateTime,
			// endDateTime, saveIndex, saveIndexType);
			// adPayStatsServiceImpl.payUserCnt(id, project, startDateTime,
			// endDateTime, saveIndex, saveIndexType);
			// adPayStatsServiceImpl.payCnt(id, project, startDateTime,
			// endDateTime,
			// saveIndex, saveIndexType);
			adPayStatsServiceImpl.regUserCnt(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.loginUserCnt(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.ydRetRate(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.tdRetRate(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.sdRetRate(id, startDateTime, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.sumRegUserCnt(id, project, projectName,
					startDateTime, endDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.SumLoginUser(id, project, startDateTime,
					endDateTime, saveIndex, saveIndexType);
		} catch (Exception e) {
			log.error("Task  adNewsUserPayStatsAllDailyTasks failed!", e);
			return;
		}

		log.info("Task adNewsUserPayStatsAllDailyTasks Complete!");
	}

}
