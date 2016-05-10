package com.etl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.dao.entity.AdPayStats;
import com.etl.es.BaseESOption;
import com.etl.mutilDataSource.DataSourceSwitch;
import com.etl.mutilDataSource.MutilDataSource;
import com.etl.service.IPayService;

/**
 * 
 * @author Abbott
 *
 */
@Service("adgetreferService")
public class AdGetReferServiceImpl {
	private static Logger log = LoggerFactory.getLogger(AdGetReferServiceImpl.class);

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
	private static String adGetReferTemplate = Thread.currentThread().getContextClassLoader()
			.getResource("template/es/").getPath()
			+ "ad_get_refer_template.json";

	/**
	 * 
	 * @param index
	 * @param startDate
	 * @param endDate
	 * @param indextypes
	 */

	public void adGetReferStats(String queryIndex, long startDate, long endDate) {

		DataSourceSwitch.setDataSourceType(MutilDataSource.U_LEAJOY);

		// adPay.setRegTimeStart("2016-03-30 00:00:00");
		// adPay.setRegTimeEnd("2016-03-30 23:59:59");

		try {
			SearchResponse response = apESOption.execQuerySimple(adGetReferTemplate, queryIndex, startDate, endDate);

			log.debug(response.toString());

			Terms domainSgg = response.getAggregations().get("ad_domain");

			List<AdPayStats> saveStats = new ArrayList<AdPayStats>();
			for (Terms.Bucket domainEntry : domainSgg.getBuckets()) {

				AdPayStats adPay = new AdPayStats();
				String domain = (String) domainEntry.getKey();
				log.debug(domain);
				adPay.setSource_url(domain);
				saveStats.add(adPay);
			}

			// System.out.println(DateUtils.formatDateTime(DateUtils.getDateBegin(statsDate.toDate())));
			// System.out.println(DateUtils.formatDateTime(DateUtils.getDateEnd(statsDate.toDate())));
			ipayService.saverefer(saveStats);

		} catch (Exception e) {
			log.error("adGetReferStats Insert or Update failed!", e);
		}
		// baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType,
		// saveStats.toArray());
		log.info("adGetReferStats Complete!");
	}

}
