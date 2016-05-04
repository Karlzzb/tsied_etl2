package com.etl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.etl.dao.entity.AdPayStats;
import com.etl.es.BaseESOption;
import com.etl.mutilDataSource.DataSourceSwitch;
import com.etl.mutilDataSource.MutilDataSource;
import com.etl.service.IPayService;
import com.etl.utils.DateUtils;

/**
 * @author Rainisic
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../applicationContext.xml")
public class AdPayStatsTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private IPayService ipayService;

	@Autowired
	private BaseESOption baseESOption;

	// @Test
	// // 注册用户消费金额
	// public void findRegUserPayCnt() {
	// // AdPayStats pay = new AdPayStats();
	// List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
	// DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);
	//
	// AdPayStats adPay = new AdPayStats();
	// adPay.setId("2016-03-21");
	// adPay.setRegTimeStart("2016-03-21 00:00:00");
	// adPay.setRegTimeEnd("2016-03-21 23:59:59");
	// List<AdPayStats> payList = ipayService.findPayByPay(adPay);
	// for (AdPayStats adPayStats : payList) {
	// // payList1.add(adPayStats);
	// System.out.println("+++" + adPayStats.getSource_url());
	// System.out.println("+++" + adPayStats.getId());
	// // AdPayStats adpay = new AdPayStats();
	// // adpay.setReguserpaycnt(adPayStats.getReguserpaycnt());
	// adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
	//
	// adPayStats.setStatsDate(DateUtils.getCurrent());
	// saveStats.add(adPayStats);
	// }
	// baseESOption.saveOrUpdateMultiObjests("pay_stats", "pay_test",
	// saveStats.toArray());
	// }
	//
	// @Test
	// public void findLoginUserPayCnt() {
	// // AdPayStats pay = new AdPayStats();
	// List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
	// DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);
	//
	// AdPayStats adPay = new AdPayStats();
	// adPay.setId("2016-03-21");
	// adPay.setLoginTimeStart("2016-03-21 00:00:00");
	// adPay.setLoginTimeEnd("2016-03-21 23:59:59");
	// List<AdPayStats> payList = ipayService.findLoginUserByLoginUser(adPay);
	// for (AdPayStats adPayStats : payList) {
	// // payList1.add(adPayStats);
	// System.out.println("+++" + adPayStats.getLoginuserpaycnt());
	// System.out.println("+++" + adPayStats.getId());
	// // AdPayStats adpay = new AdPayStats();
	// adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
	// // adpay.setLoginuserpaycnt(adPayStats.getLoginuserpaycnt());
	// adPayStats.setStatsDate(DateUtils.getCurrent());
	// saveStats.add(adPayStats);
	// }
	//
	// baseESOption.saveOrUpdateMultiObjests("pay_stats", "pay_test",
	// saveStats.toArray());
	// }
	//
	// @Test
	// public void findPayUserCnt() {
	// // AdPayStats pay = new AdPayStats();
	// List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
	// DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);
	//
	// AdPayStats adPay = new AdPayStats();
	// adPay.setId("2016-03-21");
	// adPay.setTimeStart("2016-03-21 00:00:00");
	// adPay.setTimeEnd("2016-03-21 23:59:59");
	// List<AdPayStats> payList = ipayService.findUserCntByLoginUserCnt(adPay);
	// for (AdPayStats adPayStats : payList) {
	// // payList1.add(adPayStats);
	// System.out.println("+++" + adPayStats.getPayusercnt());
	// System.out.println("+++" + adPayStats.getId());
	// // AdPayStats adpay = new AdPayStats();
	// adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
	// // adpay.setPayusercnt(adPayStats.getPayusercnt());
	// adPayStats.setStatsDate(DateUtils.getCurrent());
	// saveStats.add(adPayStats);
	// }
	//
	// baseESOption.saveOrUpdateMultiObjests("pay_stats", "pay_test",
	// saveStats.toArray());
	// }
	//
	// @Test
	// public void findPayCnt() {
	// // AdPayStats pay = new AdPayStats();
	// List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
	// DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);
	//
	// AdPayStats adPay = new AdPayStats();
	// adPay.setId("2016-03-21");
	// adPay.setTimeStart("2016-03-21 00:00:00");
	// adPay.setTimeEnd("2016-03-21 23:59:59");
	// List<AdPayStats> payList = ipayService.findPayCntByPayCnt(adPay);
	// for (AdPayStats adPayStats : payList) {
	// // payList1.add(adPayStats);
	// System.out.println("+++" + adPayStats.getPaycnt());
	// System.out.println("+++" + adPayStats.getId());
	// // AdPayStats adpay = new AdPayStats();
	// adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
	// // adpay.setPaycnt(adPayStats.getPaycnt());
	// adPayStats.setStatsDate(DateUtils.getCurrent());
	// saveStats.add(adPayStats);
	// }
	//
	// baseESOption.saveOrUpdateMultiObjests("pay_stats", "pay_test",
	// saveStats.toArray());
	// }

	// 具体的指标
	// 门户 index tmp_stats type www_stats project 2
	// 咨询 index news_stats type news_stats project 3

	@Test
	public void findRegUserCnt() {
		// AdPayStats pay = new AdPayStats();
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		adPay.setId("2016-04-27");
		adPay.setRegTimeStart("2016-04-27 00:00:00");
		adPay.setRegTimeEnd("2016-04-27 23:59:59");
		adPay.setProject(3);
		List<AdPayStats> payList = ipayService.findRegUserCntByRegUserCnt(adPay);
		if (payList != null && !payList.isEmpty()) {
			System.out.println(payList);
			for (AdPayStats adPayStats : payList) {

				// payList1.add(adPayStats);
				// AdPayStats adpay = new AdPayStats();
				adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
				// adpay.setRegusercnt(adPayStats.getRegusercnt());
				adPayStats.setStatsDate(DateUtils.parseDateTime("2016-04-27 01:00:00"));
				// System.out.println("+++" + DateUtils.getCurrent());
				saveStats.add(adPayStats);
			}

			baseESOption.saveOrUpdateMultiObjests("news_stats", "news_stats", saveStats.toArray());
		}
	}

	@Test
	public void findLoginUserCnt() {
		// AdPayStats pay = new AdPayStats();
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		adPay.setId("2016-04-27");
		adPay.setLoginTimeStart("2016-04-27 00:00:00");
		adPay.setLoginTimeEnd("2016-04-27 23:59:59");
		adPay.setProject(3);
		List<AdPayStats> payList = ipayService.findLoginUserCntByLoginUserCnt(adPay);
		if (payList != null && !payList.isEmpty()) {
			for (AdPayStats adPayStats : payList) {
				// payList1.add(adPayStats);
				// AdPayStats adpay = new AdPayStats();
				adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
				// adpay.setLoginusercnt(adPayStats.getLoginusercnt());
				adPayStats.setStatsDate(DateUtils.parseDateTime("2016-04-27 00:00:00"));
				saveStats.add(adPayStats);
			}

			baseESOption.saveOrUpdateMultiObjests("news_stats", "news_stats", saveStats.toArray());
		}
	}

	@Test
	public void findYdRetRate() {
		// AdPayStats pay = new AdPayStats();
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		// adPay.setRate("YdRetRate");
		adPay.setId("2016-04-27");
		adPay.setDay((long) 1);
		adPay.setTimeStart("2016-04-27 00:00:00");
		adPay.setTimeEnd("2016-04-27 23:59:59");
		adPay.setProject(3);
		List<AdPayStats> payList = ipayService.findRetRateByRetRate(adPay);
		for (AdPayStats adPayStats : payList) {
			// payList1.add(adPayStats);
			// AdPayStats adpay = new AdPayStats();
			adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
			adPayStats.setYdrate(adPayStats.getRate());
			adPayStats.setStatsDate(DateUtils.parseDateTime("2016-04-27 00:00:00"));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests("news_stats", "news_stats", saveStats.toArray());
	}

	@Test
	public void findTdRetRate() {
		// AdPayStats pay = new AdPayStats();
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		// adPay.setRate("YdRetRate");
		adPay.setId("2016-04-27");
		adPay.setDay((long) 3);
		adPay.setTimeStart("2016-04-27 00:00:00");
		adPay.setTimeEnd("2016-04-27 23:59:59");
		adPay.setProject(3);
		List<AdPayStats> payList = ipayService.findRetRateByRetRate(adPay);
		for (AdPayStats adPayStats : payList) {
			// payList1.add(adPayStats);
			// AdPayStats adpay = new AdPayStats();
			adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
			adPayStats.setTdrate(adPayStats.getRate());
			adPayStats.setStatsDate(DateUtils.parseDateTime("2016-04-27 00:00:00"));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests("news_stats", "news_stats", saveStats.toArray());
	}

	@Test
	public void findSdRetRate() {
		// AdPayStats pay = new AdPayStats();
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		// adPay.setRate("YdRetRate");
		adPay.setId("2016-04-27");
		adPay.setDay((long) 7);

		adPay.setTimeStart("2016-04-27 00:00:00");
		adPay.setTimeEnd("2016-04-27 23:59:59");
		adPay.setProject(3);
		List<AdPayStats> payList = ipayService.findRetRateByRetRate(adPay);
		for (AdPayStats adPayStats : payList) {
			// payList1.add(adPayStats);
			// AdPayStats adpay = new AdPayStats();
			adPayStats.setId(adPayStats.getId() + "-" + adPayStats.getSource_url());
			adPayStats.setSdrate(adPayStats.getRate());
			adPayStats.setStatsDate(DateUtils.parseDateTime("2016-04-27 00:00:00"));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests("news_stats", "news_stats", saveStats.toArray());
	}

	// sum总的
	// 门户 index pay_s type pay_game projce 2
	// 咨询 index pay_news type pay_news projce 3

	@Test
	public void SumLoginUser() {
		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		// adPay.setRate("YdRetRate");

		Integer project = 3;
		adPay.setId("2016-04-27");

		adPay.setLoginTimeStart("2016-04-27 00:00:00");
		adPay.setLoginTimeEnd("2016-04-27 23:59:59");
		adPay.setProject(3);
		List<AdPayStats> payList = ipayService.findSumLoginUserCntBySumLoginUserCnt(adPay);
		for (AdPayStats adPayStats : payList) {
			// payList1.add(adPayStats);
			// System.out.println("+++" + adPayStats.getRate());
			// System.out.println("+++" + adPayStats.getId());
			// AdPayStats adpay = new AdPayStats();
			adPayStats.setProject(3);
			adPayStats.setId(adPayStats.getId() + "-local-" + project);
			adPayStats.setSumloginusercnt(adPayStats.getSumloginusercnt());
			adPayStats.setStatsDate(DateUtils.parseDateTime("2016-04-27 00:00:00"));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests("pay_news", "pay_news", saveStats.toArray());
	}

	@Test
	public void SumRegUser() {

		List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		AdPayStats adPay = new AdPayStats();
		// adPay.setRate("YdRetRate");
		Integer project = 3;
		adPay.setId("2016-04-27");

		// adPay.setTimeStart("2016-04-27 00:00:00");
		adPay.setRegTimeEnd("2016-04-27 23:59:59");
		adPay.setProject(3);
		adPay.setProjectname("u");
		List<AdPayStats> payList = ipayService.findSumRegUserCntBySumRegUserCnt(adPay);
		for (AdPayStats adPayStats : payList) {
			// payList1.add(adPayStats);
			// System.out.println("+++" + adPayStats.getRate());
			// System.out.println("+++" + adPayStats.getId());
			// AdPayStats adpay = new AdPayStats();
			adPayStats.setId(adPayStats.getId() + "-local-" + project);
			adPayStats.setSumregusercnt(adPayStats.getSumregusercnt());
			adPayStats.setStatsDate(DateUtils.parseDateTime("2016-04-27 00:00:00"));
			saveStats.add(adPayStats);
		}

		baseESOption.saveOrUpdateMultiObjests("pay_news", "pay_news", saveStats.toArray());

		// List<AdPayStats> saveStats1 = new ArrayList<AdPayStats>();
		// DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);
		//
		// AdPayStats adPay1 = new AdPayStats();
		//
		// Long sum = (long) 100;
		//
		// adPay1.setId("2016-04-04-www.baidu.com");
		// adPay1.setSource_url("www.baidu.com");
		// adPay1.setRegusercnt(sum);
		// System.out.println(DateUtils.getCurrent());
		// adPay1.setStatsDate(new
		// Date(DateUtils.parseDate("2016-4-4").getTime()));
		// saveStats1.add(adPay1);
		//
		// baseESOption.saveOrUpdateMultiObjests("pay_s", "pay_game",
		// saveStats1.toArray());
	}

}
