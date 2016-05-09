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
import com.etl.service.impl.WebStatsServiceImpl;
import com.etl.utils.DateUtils;

@Component("webStatsTasks")
@Aspect
public class WebStatsTasks {

	private static Logger log = LoggerFactory.getLogger(BaseESOption.class);

	@Resource
	private WebStatsServiceImpl webStatsService;

	@Scheduled(cron = "0 25 0 * * ?")
	public void webWWWStatsAllDaily() {
		String saveResourceIndex = "all_stats";
		String saveIndexType = "ap_main_site";
		Integer dateInterval = -1;

		try {
			webStats("www-*", "nginx-www-access", "all_request_stats", saveIndexType, saveResourceIndex, saveIndexType,
					dateInterval);
		} catch (Exception e) {
			log.error("Task {index[" + saveResourceIndex + "], indexType[" + saveIndexType
					+ "]} webWWWStatsAllDaily failed!", e);
			return;
		}

		log.info("Task {index[" + saveResourceIndex + "], indexType[" + saveIndexType
				+ "]} webWWWStatsAllDaily Complete!");
	}

	@Scheduled(cron = "0 35 * * * ?")
	public void webNewsStatsAllDaily() {
		String saveResourceIndex = "all_news_stats";
		String saveIndexType = "ap_news_site";
		Integer dateInterval = -1;

		try {
			webStats("news-*", "nginx-news-access", "all_request_news_stats", saveIndexType, saveResourceIndex,
					saveIndexType, dateInterval);
		} catch (Exception e) {
			log.error("Task {index[" + saveResourceIndex + "], indexType[" + saveIndexType
					+ "]} webNewsStatsAllDaily failed!", e);
			return;
		}

		log.info("Task {index[" + saveResourceIndex + "], indexType[" + saveIndexType
				+ "]} webNewsStatsAllDaily Complete!");
	}

	@Scheduled(cron = "0 45 0 * * ?")
	public void webGameStatsAllDaily() {
		String saveResourceIndex = "all_game_stats";
		String saveIndexType = "game";
		Integer dateInterval = -1;

		try {
			webStats("game-*", "nginx-game-access", "all_request_game_stats", saveIndexType, saveResourceIndex,
					saveIndexType, dateInterval);
		} catch (Exception e) {
			log.error("Task {index[" + saveResourceIndex + "], indexType[" + saveIndexType
					+ "]} webNewsStatsAllDaily failed!", e);
			return;
		}

		log.info("Task {index[" + saveResourceIndex + "], indexType[" + saveIndexType
				+ "]} webNewsStatsAllDaily Complete!");
	}

	/**
	 * Web 全 统计方法
	 * 
	 * @param queryIndex
	 * @param queryIndexType
	 * @param requestSaveIndex
	 * @param requestSaveIndexType
	 * @param saveResourceIndex
	 * @param saveResourceIndexType
	 * @param dateInterval
	 */
	private void webStats(String queryIndex, String queryIndexType, String requestSaveIndex,
			String requestSaveIndexType, String saveResourceIndex, String saveResourceIndexType, Integer dateInterval) {
		Date today = DateUtils.getDateBegin(DateUtils.getCurrent());

		Long startDateTime = DateUtils.addDate(today, Calendar.DATE, dateInterval).getTime();

		Long endDateTime = DateUtils.getDateEnd(today).getTime();

		webStatsService.webBasicStatsDaily(queryIndex, startDateTime, endDateTime, queryIndexType, requestSaveIndex,
				requestSaveIndexType);

		webStatsService.webRateStatsDaily(queryIndex, startDateTime, endDateTime, queryIndexType, requestSaveIndex,
				requestSaveIndexType);

		webStatsService.webBasicSourceStatsDaily(queryIndex, startDateTime, endDateTime, queryIndexType,
				saveResourceIndex, saveResourceIndexType);

		webStatsService.webSourceRateStatsDaily(requestSaveIndex, startDateTime, endDateTime, requestSaveIndexType,
				saveResourceIndex, saveResourceIndexType);
	}

}
