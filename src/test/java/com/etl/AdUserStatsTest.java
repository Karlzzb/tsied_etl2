package com.etl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.etl.es.BaseESOption;
import com.etl.service.impl.AdGetReferServiceImpl;
import com.etl.service.impl.AdPayStatsServiceImpl;
import com.etl.service.impl.AdUserStatsServiceImpl;
import com.etl.utils.DateUtils;

/**
 * @author Rainisic
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../applicationContext.xml")
public class AdUserStatsTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private AdUserStatsServiceImpl aduserStatsService;

	@Resource
	private AdGetReferServiceImpl adGetReferStatsService;

	@Resource
	private AdPayStatsServiceImpl adPayStatsServiceImpl;

	@Autowired
	private BaseESOption apESOption;

	@Autowired
	private BaseESOption baseESOption;

	// @Test
	// public void deleteTest() {
	// baseESOption.deleteByIndex(new String[] { "test_stats" });
	//
	// }
	// 2015-04-25 1461513600000 1461599999999
	// 2015-04-26 1461600000000 1461686399999

	@Test
	public void queryWwwTest() {
		for (int i = 10; i > 0; i--) {
			Date today = DateUtils.addDate(DateUtils.getCurrent(), Calendar.DATE,
					-i);
//			Date today = DateUtils.getDateBegin(DateUtils.getCurrent());
			Integer dateInterval = -1;

			Long startDateTime = DateUtils.getDateBegin(DateUtils.addDate(today, Calendar.DATE,
					dateInterval)).getTime();
			System.out.println(startDateTime);
			
			Long endDateTime = DateUtils.getDateBegin(today).getTime();
			
			System.out.println(endDateTime);
			
			String id = DateUtils.formatDate(DateUtils.addDate(
					DateUtils.getDateBegin(today),
					Calendar.DATE, dateInterval));

//			String startPayDateTime = DateUtils.formatDateTime(DateUtils
//					.addDate(DateUtils.getDateBegin(DateUtils.getCurrent()),
//							Calendar.DATE, dateInterval));
//			String endPayDateTime = DateUtils.formatDateTime(DateUtils.addDate(
//					DateUtils.getDateEnd(DateUtils.getCurrent()),
//					Calendar.DATE, dateInterval));
			
			String startPayDateTime = DateUtils.formatDateTime(DateUtils
					.addDate(DateUtils.getDateBegin(today),
							Calendar.DATE, dateInterval));
			System.out.println(startPayDateTime);
			String endPayDateTime = DateUtils.formatDateTime(DateUtils.addDate(
					DateUtils.getDateEnd(today),
					Calendar.DATE, dateInterval));
			System.out.println(endPayDateTime);
			

			String index = "www-*";
			Integer project = 2;
			String saveIndex = "tmp_stats";
			String saveIndexType = "www_stats";
			String projectName = "u";
			String saveSumIndex = "pay_s";
			String saveSumIndexType = "pay_game";

			adGetReferStatsService.adGetReferStats(index, startDateTime,
					endDateTime);

			aduserStatsService.aduserStatsAllDaily(index, project,
					startDateTime, endDateTime);
			// aduserStatsService.aduserStatsAllDaily("news-*", 3,
			// startDateTime,
			// endDateTime);
			aduserStatsService.aduserSpeacilStats(index, project,
					startDateTime, endDateTime);
			// aduserStatsService.aduserSpeacilStats("news-*", 3, startDateTime,
			// endDateTime);

			adPayStatsServiceImpl.regUserCnt(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.loginUserCnt(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.ydRetRate(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.tdRetRate(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.sdRetRate(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
//			adPayStatsServiceImpl.sumRegUserCnt(id, project, projectName,
//					startPayDateTime, endPayDateTime, saveSumIndex,
//					saveSumIndexType);
//			adPayStatsServiceImpl.SumLoginUser(id, project, startPayDateTime,
//					endPayDateTime, saveSumIndex, saveSumIndexType);
		}
	}

	@Test
	public void queryNewsTest() {
		for (int i = 10; i > 0; i--) {
			Date today = DateUtils.addDate(DateUtils.getCurrent(), Calendar.DATE,
					-i);
//			Date today = DateUtils.getDateBegin(DateUtils.getCurrent());
			Integer dateInterval = -1;

//			Long startDateTime = DateUtils.addDate(today, Calendar.DATE,
//					dateInterval).getTime();
			
			Long startDateTime = DateUtils.getDateBegin(DateUtils.addDate(today, Calendar.DATE,
					dateInterval)).getTime();

			Long endDateTime = DateUtils.getDateBegin(today).getTime();

			String id = DateUtils.formatDate(DateUtils.addDate(
					DateUtils.getDateBegin(today),
					Calendar.DATE, dateInterval));
//
//			String startPayDateTime = DateUtils.formatDateTime(DateUtils
//					.addDate(DateUtils.getDateBegin(DateUtils.getCurrent()),
//							Calendar.DATE, dateInterval));
//			String endPayDateTime = DateUtils.formatDateTime(DateUtils.addDate(
//					DateUtils.getDateEnd(DateUtils.getCurrent()),
//					Calendar.DATE, dateInterval));
			
			String startPayDateTime = DateUtils.formatDateTime(DateUtils
					.addDate(DateUtils.getDateBegin(today),
							Calendar.DATE, dateInterval));
			System.out.println(startPayDateTime);
			String endPayDateTime = DateUtils.formatDateTime(DateUtils.addDate(
					DateUtils.getDateEnd(today),
					Calendar.DATE, dateInterval));
			System.out.println(endPayDateTime);

			String index = "news-*";
			Integer project = 3;
			String saveIndex = "news_stats";
			String saveIndexType = "news_stats";
			String projectName = "u";
			String saveSumIndex = "pay_news";
			String saveSumIndexType = "pay_news";

			adGetReferStatsService.adGetReferStats(index, startDateTime,
					endDateTime);

			aduserStatsService.aduserStatsAllDaily(index, project,
					startDateTime, endDateTime);
			// aduserStatsService.aduserStatsAllDaily("news-*", 3,
			// startDateTime,
			// endDateTime);
			aduserStatsService.aduserSpeacilStats(index, project,
					startDateTime, endDateTime);
			// aduserStatsService.aduserSpeacilStats("news-*", 3, startDateTime,
			// endDateTime);

			adPayStatsServiceImpl.regUserCnt(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.loginUserCnt(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.ydRetRate(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.tdRetRate(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
			adPayStatsServiceImpl.sdRetRate(id, project, startPayDateTime,
					endPayDateTime, saveIndex, saveIndexType);
//			adPayStatsServiceImpl.sumRegUserCnt(id, project, projectName,
//					startPayDateTime, endPayDateTime, saveSumIndex,
//					saveSumIndexType);
//			adPayStatsServiceImpl.SumLoginUser(id, project, startPayDateTime,
//					endPayDateTime, saveSumIndex, saveSumIndexType);
		}

	}

}