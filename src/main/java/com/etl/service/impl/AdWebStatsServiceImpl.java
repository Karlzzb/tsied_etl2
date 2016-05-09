package com.etl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.es.BaseESOption;
import com.etl.es.entity.AdWebStats;
import com.etl.utils.DateUtils;

/**
 * 
 * @author kerl
 *
 */
@Service("adwebStatsService")
public class AdWebStatsServiceImpl {
	private static Logger log = LoggerFactory.getLogger(AdWebStatsServiceImpl.class);

	@Autowired
	private BaseESOption apESOption;

	@Autowired
	private BaseESOption baseESOption;

	/**
	 * The Default web stats template
	 */
	private static String adwebStatsTemplate = Thread.currentThread().getContextClassLoader()
			.getResource("template/es/").getPath()
			+ "ad_stats_template.json";

	/**
	 * 
	 * @param index
	 * @param startDate
	 * @param endDate
	 * @param indextypes
	 */
	public void adwebStatsAllDaily(String queryIndex, long startDate, long endDate, String queryIndextype,
			String saveIndex, String saveIndexType) {

		List<AdWebStats> saveStats = new ArrayList<AdWebStats>();

		try {
			SearchResponse response = apESOption.execQuery(adwebStatsTemplate, queryIndex, startDate, endDate);

			Histogram agg = response.getAggregations().get("date_aggs");

			for (Histogram.Bucket dateEntry : agg.getBuckets()) {
				DateTime statsDate = (DateTime) dateEntry.getKey();
				log.info("date [{}]", statsDate);
				Terms sessionSgg = dateEntry.getAggregations().get("ad_domain");

				// For each entry
				for (Terms.Bucket entry : sessionSgg.getBuckets()) {
					String key = (String) entry.getKey(); // bucket key
					long docCount = entry.getDocCount(); // Doc count
					Cardinality uv_stats = entry.getAggregations().get("uv_stats");
					uv_stats.getValue();
					// Long pv_stat = entry.getDocCount();
					Cardinality ip_stats = entry.getAggregations().get("ip_stats");
					ip_stats.getValue();
					Cardinality session_stats = entry.getAggregations().get("session_stats");
					session_stats.getValue();
					System.out.println(key);
					saveStats.add(new AdWebStats(key + '-' + DateUtils.formatDate(statsDate.toDate()), key, docCount,
							uv_stats.getValue(), session_stats.getValue(), ip_stats.getValue(), statsDate.toDate()));
				}
			}
		} catch (Exception e) {
			log.error("{index[" + queryIndex + "], indexType[" + queryIndextype + "]} AdwebStatsAllDaily failed!", e);
		}

		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());

	}
}
