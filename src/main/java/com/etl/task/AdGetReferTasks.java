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
import com.etl.service.impl.AdGetReferServiceImpl;
import com.etl.utils.DateUtils;

@Component("adGetReferTasks")
@Aspect
public class AdGetReferTasks {

	private static Logger log = LoggerFactory.getLogger(BaseESOption.class);

	@Resource
	private AdGetReferServiceImpl adGetReferStatsService;

	@Scheduled(cron = "0 0 1 * * ?")
	public void adWwwGetReferStats() {

			try {
				Date today = DateUtils.getDateBegin(DateUtils.getCurrent());
				Integer dateInterval = -1;

				String queryIndex = "www-*";

				Long startDateTime = DateUtils.addDate(today, Calendar.DATE, dateInterval).getTime();

				Long endDateTime = DateUtils.getDateBegin(today).getTime();

				adGetReferStatsService.adGetReferStats(queryIndex, startDateTime, endDateTime);

			} catch (Exception e) {
				log.error("Task  adWwwGetReferTasks failed!", e);
				return;
			}

			log.info("Task adWwwGetReferTasks Complete!");

		
	}

	@Scheduled(cron = "0 0 1 * * ?")
	public void adNewsGetReferStats() {

			try {
				Date today = DateUtils.getDateBegin(DateUtils.getCurrent());
				Integer dateInterval = -1;

				String queryIndex = "news-*";

				Long startDateTime = DateUtils.addDate(today, Calendar.DATE, dateInterval).getTime();

				Long endDateTime = DateUtils.getDateBegin(today).getTime();

				adGetReferStatsService.adGetReferStats(queryIndex, startDateTime, endDateTime);

			} catch (Exception e) {
				log.error("Task  adNewsGetReferTasks failed!", e);
				return;
			}

			log.info("Task adNewsGetReferTasks Complete!");

		
	}
}
