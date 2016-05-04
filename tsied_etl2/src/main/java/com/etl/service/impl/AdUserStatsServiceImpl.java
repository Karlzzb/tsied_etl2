package com.etl.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.dao.entity.AdPayStats;
import com.etl.es.BaseESOption;
import com.etl.mutilDataSource.DataSourceSwitch;
import com.etl.mutilDataSource.MutilDataSource;
import com.etl.service.IPayService;
import com.etl.utils.DateUtils;

/**
 * 
 * @author Abbott
 *
 */
@Service("aduserStatsService")
public class AdUserStatsServiceImpl {
	private static Logger log = LoggerFactory.getLogger(AdUserStatsServiceImpl.class);

	@Autowired
	private IPayService ipayService;

	public IPayService getAdPayStatsService() {
		return ipayService;
	}

	public void setAdPayStatsService(IPayService ipayService) {
		this.ipayService = ipayService;
	}

	@Autowired
	private BaseESOption apESOption;

	@Autowired
	private BaseESOption baseESOption;

	/**
	 * The Default web stats template
	 */
	private static String adUserStatsTemplate = Thread.currentThread().getContextClassLoader()
			.getResource("template/es/").getPath()
			+ "user_stats_template.json";

	private static String adUserSpeacilStatsTemplate = Thread.currentThread().getContextClassLoader()
			.getResource("template/es/").getPath()
			+ "user_speacil_stats_template.json";

	/**
	 * 
	 * @param index
	 * @param startDate
	 * @param endDate
	 * @param indextypes
	 */
	public void aduserStatsAllDaily(String queryIndex, Integer project, long startDate, long endDate) {

		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		// adPay.setRegTimeStart("2016-03-30 00:00:00");
		// adPay.setRegTimeEnd("2016-03-30 23:59:59");

		try {
			SearchResponse response = apESOption.execQuery(adUserStatsTemplate, queryIndex, startDate, endDate);

			log.debug(response.toString());

			Histogram agg = response.getAggregations().get("date_aggs");

			for (Histogram.Bucket dateEntry : agg.getBuckets()) {
				DateTime statsDate = (DateTime) dateEntry.getKey();
				System.out.println(statsDate.toDate());
				System.out.println(TimeZone.getDefault().getID());
				log.info("date [{}]", statsDate);
				Terms domainSgg = dateEntry.getAggregations().get("ad_domain");

				List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
				// For each entry
				for (Terms.Bucket domainEntry : domainSgg.getBuckets()) {
					String domain = (String) domainEntry.getKey();
					// System.out.println(domain);

					Terms sessionSgg = domainEntry.getAggregations().get("session_stats");

					for (Terms.Bucket sessionEntry : sessionSgg.getBuckets()) {
						// System.out.println(session);
						Terms userSgg = sessionEntry.getAggregations().get("user_stats");
						//
						for (Terms.Bucket userEntry : userSgg.getBuckets()) {
							AdPayStats adPay = new AdPayStats();
							String user = (String) userEntry.getKey();
							// System.out.println(user);
							// 如果是真实数据需要判断user等不等于-1，等于-1就是游客，不等于的存入数据库
							if (user.compareTo("-1") != 0) {
								System.out.println(user);
								System.out.println(statsDate.toDate());
								adPay.setUid(Integer.parseInt(user));
								adPay.setSource_url(domain);
								adPay.setInittime(statsDate.toDate());
								adPay.setProject(project);
								saveStats.add(adPay);
								// } else {
								// adPay.setUid(Integer.parseInt("108902"));
								// adPay.setUid(Integer.parseInt(user));
							}

							// adPay.setSource_url(domain);
							// adPay.setInittime(statsDate.toDate());
							// adPay.setProject(project);
							// saveStats.add(adPay);

						}
					}
				}
				// System.out.println(DateUtils.formatDateTime(DateUtils.getDateBegin(statsDate.toDate())));
				// System.out.println(DateUtils.formatDateTime(DateUtils.getDateEnd(statsDate.toDate())));
				ipayService.saveuser(saveStats);
				AdPayStats updateAdPay = new AdPayStats();
				updateAdPay.setTimeStart(DateUtils.formatDateTime(DateUtils.getDateBegin(statsDate.toDate())));
				updateAdPay.setTimeEnd(DateUtils.formatDateTime(DateUtils.getDateEnd(statsDate.toDate())));
				updateAdPay.setProject(project);
				ipayService.updateLoginUser(updateAdPay);
			}
		} catch (Exception e) {
			log.error("AduserStatsAllDaily Insert or Update failed!", e);
		}
		// baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType,
		// saveStats.toArray());
	}

	public void aduserSpeacilStats(String queryIndex, Integer project, long startDate, long endDate) {

		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		// adPay.setRegTimeStart("2016-03-30 00:00:00");
		// adPay.setRegTimeEnd("2016-03-30 23:59:59");

		try {
			SearchResponse response = apESOption.execQuerySimple(adUserSpeacilStatsTemplate, queryIndex, startDate,
					endDate);

			log.debug(response.toString());

			Histogram agg = response.getAggregations().get("date_aggs");

			for (Histogram.Bucket dateEntry : agg.getBuckets()) {
				DateTime statsDate = (DateTime) dateEntry.getKey();
				System.out.println(statsDate.toDate());
				System.out.println(TimeZone.getDefault().getID());
				log.info("date [{}]", statsDate);
				Terms domainSgg = dateEntry.getAggregations().get("ad_domain");

				List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
				// For each entry
				for (Terms.Bucket domainEntry : domainSgg.getBuckets()) {
					String domain = (String) domainEntry.getKey();
					System.out.println(domain);

					Terms pathSgg = domainEntry.getAggregations().get("ad_path");

					for (Terms.Bucket pathEntry : pathSgg.getBuckets()) {
						String path = (String) pathEntry.getKey();
						System.out.println(path);
						Terms userSgg = pathEntry.getAggregations().get("user_stats");
						//
						for (Terms.Bucket userEntry : userSgg.getBuckets()) {
							AdPayStats adPay = new AdPayStats();
							String user = (String) userEntry.getKey();
							// System.out.println(user);
							// 如果是真实数据需要判断user等不等于-1，等于-1就是游客，不等于的存入数据库
							if (user.compareTo("-1") != 0) {
								System.out.println(user);
								System.out.println(statsDate.toDate());
								adPay.setUid(Integer.parseInt(user));
								adPay.setSource_url(domain + path);
								adPay.setInittime(statsDate.toDate());
								adPay.setProject(project);
								saveStats.add(adPay);
								// } else {
								// adPay.setUid(Integer.parseInt("108902"));
								// adPay.setUid(Integer.parseInt(user));
							}

							// adPay.setSource_url(domain);
							// adPay.setInittime(statsDate.toDate());
							// adPay.setProject(project);
							// saveStats.add(adPay);

						}
					}
				}
				// System.out.println(DateUtils.formatDateTime(DateUtils.getDateBegin(statsDate.toDate())));
				// System.out.println(DateUtils.formatDateTime(DateUtils.getDateEnd(statsDate.toDate())));
				ipayService.saveuser(saveStats);
				AdPayStats updateAdPay = new AdPayStats();
				updateAdPay.setTimeStart(DateUtils.formatDateTime(DateUtils.getDateBegin(statsDate.toDate())));
				updateAdPay.setTimeEnd(DateUtils.formatDateTime(DateUtils.getDateEnd(statsDate.toDate())));
				updateAdPay.setProject(project);
				ipayService.updateLoginUser(updateAdPay);
			}
		} catch (Exception e) {
			log.error("AduserStatsAllDaily Insert or Update failed!", e);
		}
		// baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType,
		// saveStats.toArray());
	}

}
