package com.etl.task;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.etl.es.BaseESOption;
import com.etl.service.impl.AdUserStatsServiceImpl;
import com.etl.utils.DateUtils;

@Component("adUserStatsTasks")
@Aspect
public class AdUserStatsTasks {

	private static Logger log = LoggerFactory.getLogger(BaseESOption.class);

	@Resource
	private AdUserStatsServiceImpl aduserStatsService;

	@Scheduled(cron = "0 10 1 * * ?")
	public void adWWWUserStatsAllDaily() {

			try {
				Date today = DateUtils.getDateBegin(DateUtils.getCurrent());
				Integer dateInterval = -1;

				String queryIndex = "www-*";

				Integer project = 2;

				Long startDateTime = DateUtils.addDate(today, Calendar.DATE,
						dateInterval).getTime();

				Long endDateTime = DateUtils.getDateBegin(today).getTime();

				aduserStatsService.aduserStatsAllDaily(queryIndex, project,
						startDateTime, endDateTime);
				
				aduserStatsService.aduserSpeacilStats(queryIndex, project,
						startDateTime, endDateTime);

			} catch (Exception e) {
				log.error(
						"Task {index[www-*]}, adWWWUserStatsAllDailyTasks failed!",
						e);
				return;
			}

			log.info("Task {index[www-*]}, adWWWUserStatsAllDailyTasks Complete!");

		
	}
	@Scheduled(cron = "0 10 1 * * ?")
	public void adNewsUserStatsAllDaily() {

			try {
				Date today = DateUtils.getDateBegin(DateUtils.getCurrent());
				Integer dateInterval = -1;

				String queryIndex = "news-*";

				Integer project = 3;

				Long startDateTime = DateUtils.addDate(today, Calendar.DATE,
						dateInterval).getTime();

				Long endDateTime = DateUtils.getDateBegin(today).getTime();

				aduserStatsService.aduserStatsAllDaily(queryIndex, project,
						startDateTime, endDateTime);
				
				aduserStatsService.aduserSpeacilStats(queryIndex, project,
						startDateTime, endDateTime);

			} catch (Exception e) {
				log.error(
						"Task {index[www-*]}, adNewsUserStatsAllDailyTasks failed!",
						e);
				return;
			}

			log.info("Task {index[www-*]}, adNewsUserStatsAllDailyTasks Complete!");

		
	}
}
