package com.etl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.etl.service.impl.WebStatsServiceImpl;
import com.etl.utils.DateUtils;

/**
 * @author Rainisic
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../applicationContext.xml")
public class WebStatsTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private WebStatsServiceImpl webStatsService;

	@Test
	public void webBasicStatsDailyTest() {

		webStatsService.webBasicStatsDaily("logstash-*", Long.parseLong("1456761600000"),
				Long.parseLong("1459439999999"), "", "all_stats", "ap_main_site");
	}

	@Test
	public void webUserStatsAllDaily() {

		try {
			webStats("www-*", "nginx-user-access", "all_request_stats", "user_center", "all_stats", "user_center", -1);
		} catch (Exception e) {
			return;
		}

	}

	@Test
	public void webMainStatsAllDaily() {

		try {

			webStats("www-*", "nginx-www-access", "all_request_stats", "ap_main_site", "all_stats", "ap_main_site", -1);

		} catch (Exception e) {
			return;
		}

	}

	@Test
	public void webNewsStatsAllDaily() {

		try {

			webStats("news-*", "nginx-news-access", "all_request_news_stats", "ap_news_stats", "all_news_stats",
					"ap_news_site", -1);

		} catch (Exception e) {
			return;
		}

	}

	@Test
	public void webGameStatsAllDaily() {

		try {

			webStats("game-*", "nginx-game-access", "all_request_game_stats", "game", "all_game_stats", "game", -1);

		} catch (Exception e) {
			return;
		}
	}

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

	@Test
	public void forTempUser() {
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				continue;
			}

		}

	}
}