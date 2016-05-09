package com.etl.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.es.BaseESOption;
import com.etl.es.entity.TempWebRateStats;
import com.etl.es.entity.WebSourceStats;
import com.etl.es.entity.WebStats;
import com.etl.utils.DateUtils;

/**
 * 
 * @author kerl
 *
 */
@Service("webStatsService")
public class WebStatsServiceImpl {
	private static Logger log = LoggerFactory.getLogger(WebStatsServiceImpl.class);

	@Autowired
	private BaseESOption apESOption;

	@Autowired
	private BaseESOption baseESOption;

	/**
	 * The Default web stats template
	 */
	private static String webBasicStatsTemplate = Thread.currentThread().getContextClassLoader()
			.getResource("template/es/").getPath()
			+ "web_basic_stats_template.json";

	/**
	 * The Default web stats template
	 */
	private static String webRateStatsTemplate = Thread.currentThread().getContextClassLoader()
			.getResource("template/es/").getPath()
			+ "web_rate_stats_template.json";

	/**
	 * The Default web source stats template
	 */
	private static String webSourceStatsTemplate = Thread.currentThread().getContextClassLoader()
			.getResource("template/es/").getPath()
			+ "web_source_stats_template.json";

	/**
	 * 根据refere&request分组的基本访问统计
	 * 
	 * @param queryIndex
	 * @param startDate
	 * @param endDate
	 * @param queryIndextype
	 * @param saveIndex
	 * @param saveIndexType
	 */
	public void webBasicStatsDaily(String queryIndex, long startDate, long endDate, String queryIndextype,
			String saveIndex, String saveIndexType) {

		List<WebStats> saveStats = new ArrayList<WebStats>();

		try {
			SearchResponse response = apESOption.execQuerySimple(webBasicStatsTemplate, queryIndex, startDate, endDate,
					queryIndextype);
			log.debug(response.toString());

			Histogram agg = response.getAggregations().get("date_aggs");

			for (Histogram.Bucket dateEntry : agg.getBuckets()) {
				DateTime statsDate = (DateTime) dateEntry.getKey();

				Terms sourceTerm = dateEntry.getAggregations().get("refer_aggs");

				for (Terms.Bucket sourceEntry : sourceTerm.getBuckets()) {
					String sourceUrl = (String) sourceEntry.getKey();
					Terms requestTerm = sourceEntry.getAggregations().get("request_aggs");

					for (Terms.Bucket requestEntry : requestTerm.getBuckets()) {
						String requestUrl = (String) requestEntry.getKey();
						saveStats.add(new WebStats(sourceUrl, requestUrl, requestEntry.getDocCount(),
								((Cardinality) requestEntry.getAggregations().get("uv_stats")).getValue(),
								((Cardinality) requestEntry.getAggregations().get("session_stats")).getValue(),
								((Cardinality) requestEntry.getAggregations().get("ip_stats")).getValue(), statsDate
										.toDate()));
					}
				}

			}
		} catch (Exception e) {
			log.error("{index[" + queryIndex + "], indexType[" + queryIndextype + "],[" + startDate + "],[" + endDate
					+ "]} webBasicStatsDaily failed!", e);
		}

		baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());
	}

	/**
	 * 跳出率和退出率的计算
	 * 
	 * @param queryIndex
	 * @param startDate
	 * @param endDate
	 * @param queryIndextype
	 * @param saveIndex
	 * @param saveIndexType
	 */
	public void webRateStatsDaily(String queryIndex, long startDate, long endDate, String queryIndextype,
			String saveIndex, String saveIndexType) {

		try {
			SearchResponse response = apESOption.execQuerySimple(webRateStatsTemplate, queryIndex, startDate, endDate,
					queryIndextype);
			log.debug(response.toString());

			Histogram agg = response.getAggregations().get("date_aggs");

			List<TempWebRateStats> myTempRateList = new ArrayList<TempWebRateStats>();

			for (Histogram.Bucket dateEntry : agg.getBuckets()) {

				DateTime statsDate = (DateTime) dateEntry.getKey();

				Terms sessionSgg = dateEntry.getAggregations().get("session_id_groups");
				for (Terms.Bucket entry : sessionSgg.getBuckets()) {
					String sessionId = (String) entry.getKey();
					long reqCount = entry.getDocCount();

					TopHits entry_access = entry.getAggregations().get("first_request");
					TopHits exit_url = entry.getAggregations().get("last_request");
					String entryURL = (String) entry_access.getHits().getAt(0).getSource().get("request");

					String sourceURL = (String) entry_access.getHits().getAt(0).getSource().get("refer");
					DateTime firstTime = DateTime.parse((String) entry_access.getHits().getAt(0).getSource()
							.get("@timestamp"));

					String exitURL = (String) exit_url.getHits().getAt(0).getSource().get("request");

					DateTime endTime = DateTime.parse((String) exit_url.getHits().getAt(0).getSource()
							.get("@timestamp"));

					Long sessionPeriod = endTime.getMillis() - firstTime.getMillis();

					myTempRateList.add(new TempWebRateStats(sessionId, reqCount, entryURL, sourceURL, exitURL,
							((Cardinality) entry.getAggregations().get("unique_req_count")).getValue(), statsDate
									.toDate(), sessionPeriod));
				}
			}

			String tempIndex = "temp_data" + queryIndextype;
			String tempIndexType = "temp";

			baseESOption.saveOrUpdateMultiObjests(tempIndex, tempIndexType, myTempRateList.toArray());

			// Second stats
			SearchRequestBuilder request2 = baseESOption
					.getClient()
					.prepareSearch(tempIndex)
					.setQuery(
							rangeQuery("statsDate").from(DateUtils.formatEsTime(startDate)).to(
									DateUtils.formatEsTime(endDate))).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

			// Date Aggs
			String dateAggsName = "date_aggs";
			String dateField = "statsDate";
			DateHistogramBuilder dateTermsBuilder = AggregationBuilders.dateHistogram(dateAggsName)
					.timeZone("Asia/Hong_Kong").interval(DateHistogramInterval.DAY);
			dateTermsBuilder.field(dateField);
			dateTermsBuilder.minDocCount(1);
			dateTermsBuilder.extendedBounds(startDate, endDate);
			request2.addAggregation(dateTermsBuilder);

			// SouceTerm Aggs
			String souceTermAggsName = "source_exit_eeq_aggs";
			String souceTermtermField = "sourceURL.raw";
			TermsBuilder souceTermsBuilder = AggregationBuilders.terms(souceTermAggsName).field(souceTermtermField)
					.size(0);
			dateTermsBuilder.subAggregation(souceTermsBuilder);

			// Term Aggs
			String termAggsName = "exit_req_aggs";
			String termField = "exitURL.raw";
			TermsBuilder termsBuilder = AggregationBuilders.terms(termAggsName).field(termField).size(0);
			souceTermsBuilder.subAggregation(termsBuilder);

			// avg session Time
			String sessionTimeAggs = "session_time_avg";
			termsBuilder.subAggregation(AggregationBuilders.avg(sessionTimeAggs).field("sessionPeriod"));

			// avg session Time
			String reqPageAggs = "req_page_avg";
			termsBuilder.subAggregation(AggregationBuilders.avg(reqPageAggs).field("reqCount"));

			// bounce session
			String bounceSessionTerm = "bounce_session";
			termsBuilder.subAggregation(AggregationBuilders.terms(bounceSessionTerm).field("uniqueReqCount").size(0));

			log.debug(request2.toString());

			SearchResponse response2 = request2.execute().actionGet();

			log.debug(response2.toString());

			// Save the final stats
			Histogram agg2 = response2.getAggregations().get("date_aggs");

			List<WebStats> saveStats = new ArrayList<WebStats>();

			for (Histogram.Bucket dateEntry : agg2.getBuckets()) {

				DateTime statsDate = (DateTime) dateEntry.getKey();

				Terms sessionSgg = dateEntry.getAggregations().get(souceTermAggsName);
				for (Terms.Bucket souceEntry : sessionSgg.getBuckets()) {
					String source_url = (String) souceEntry.getKey();

					Terms exitReqTerms = souceEntry.getAggregations().get(termAggsName);

					for (Terms.Bucket exitReqEntry : exitReqTerms.getBuckets()) {
						String request_url = (String) exitReqEntry.getKey();
						long exitSessionCount = exitReqEntry.getDocCount();

						Terms bounceSessionTerms = exitReqEntry.getAggregations().get(bounceSessionTerm);

						Long bounceSessionCount = Long.parseLong("0");

						bounceSessionCount = bounceSessionTerms.getBucketByKey("1") != null ? bounceSessionTerms
								.getBucketByKey("1").getDocCount() : bounceSessionCount;

						Avg sessionTimeAvg = exitReqEntry.getAggregations().get(sessionTimeAggs);

						Avg reqPageAvg = exitReqEntry.getAggregations().get(reqPageAggs);

						saveStats.add(new WebStats(source_url, request_url, statsDate.toDate(), exitSessionCount,
								bounceSessionCount, sessionTimeAvg.getValue(), reqPageAvg.getValue()));

					}

				}
			}

			baseESOption.saveOrUpdateMultiObjests(saveIndex, saveIndexType, saveStats.toArray());

		} catch (Exception e) {
			log.error("{index[" + queryIndex + "], indexType[" + queryIndextype + "]} WebRateStatsDaily failed!", e);
		}

	}

	/**
	 * 根据refere分组的基本访问统计
	 * 
	 * @param queryIndex
	 * @param startDate
	 * @param endDate
	 * @param queryIndextype
	 * @param saveResourceIndex
	 * @param saveResourceIndexType
	 */
	public void webBasicSourceStatsDaily(String queryIndex, Long startDate, Long endDate, String queryIndextype,
			String saveResourceIndex, String saveResourceIndexType) {
		List<WebSourceStats> saveStats = new ArrayList<WebSourceStats>();

		try {
			SearchResponse response = apESOption.execQuerySimple(webSourceStatsTemplate, queryIndex, startDate,
					endDate, queryIndextype);
			log.debug(response.toString());

			Histogram agg = response.getAggregations().get("date_aggs");

			for (Histogram.Bucket dateEntry : agg.getBuckets()) {
				DateTime statsDate = (DateTime) dateEntry.getKey();

				Terms sourceTerm = dateEntry.getAggregations().get("refer_aggs");

				for (Terms.Bucket sourceEntry : sourceTerm.getBuckets()) {
					String sourceUrl = (String) sourceEntry.getKey();

					saveStats.add(new WebSourceStats(sourceUrl, sourceEntry.getDocCount(), ((Cardinality) sourceEntry
							.getAggregations().get("uv_stats")).getValue(), ((Cardinality) sourceEntry
							.getAggregations().get("session_stats")).getValue(), ((Cardinality) sourceEntry
							.getAggregations().get("ip_stats")).getValue(), statsDate.toDate()));
				}

			}
		} catch (Exception e) {
			log.error("{index[" + queryIndex + "], indexType[" + queryIndextype + "],[" + startDate + "],[" + endDate
					+ "]} webBasicSourceStatsDaily failed!", e);
		}

		baseESOption.saveOrUpdateMultiObjests(saveResourceIndex, saveResourceIndexType, saveStats.toArray());
	}

	/**
	 * 根据refer&request分组的统计结果计算按refer分组的跳出率和退出率
	 * 
	 * @param requestSaveIndex
	 * @param startDateTime
	 * @param endDateTime
	 * @param requestSaveIndexType
	 * @param saveResourceIndex
	 * @param saveResourceIndexType
	 */
	public void webSourceRateStatsDaily(String queryIndex, Long startDate, Long endDate, String queryIndextype,
			String saveResourceIndex, String saveResourceIndexType) {
		// Second stats
		SearchRequestBuilder request = baseESOption
				.getClient()
				.prepareSearch(queryIndex)
				.setQuery(
						rangeQuery("statsDate").from(DateUtils.formatEsTime(startDate)).to(
								DateUtils.formatEsTime(endDate))).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

		// Date Aggs
		String dateAggsName = "date_aggs";
		String dateField = "statsDate";
		DateHistogramBuilder dateTermsBuilder = AggregationBuilders.dateHistogram(dateAggsName)
				.timeZone("Asia/Hong_Kong").interval(DateHistogramInterval.DAY);
		dateTermsBuilder.field(dateField);
		dateTermsBuilder.minDocCount(1);
		dateTermsBuilder.extendedBounds(startDate, endDate);
		request.addAggregation(dateTermsBuilder);

		// SouceTerm Aggs
		String souceTermAggsName = "source_exit_eeq_aggs";
		String souceTermtermField = "source_url.raw";
		TermsBuilder souceTermsBuilder = AggregationBuilders.terms(souceTermAggsName).field(souceTermtermField).size(0);
		dateTermsBuilder.subAggregation(souceTermsBuilder);

		// avg session Time
		String sessionTimeAggs = "session_time_avg";
		souceTermsBuilder.subAggregation(AggregationBuilders.avg(sessionTimeAggs).field("sessionTime"));

		// avg req Pages
		String reqPagesAggs = "req_pages_avg";
		souceTermsBuilder.subAggregation(AggregationBuilders.avg(reqPagesAggs).field("reqPages"));

		// sum exit session Count
		String exitSessionSum = "exit_session_count_sum";
		souceTermsBuilder.subAggregation(AggregationBuilders.sum(exitSessionSum).field("exitSessionCount"));

		// sum bounce session Count
		String bounceSessionSum = "bounce_session_count_sum";
		souceTermsBuilder.subAggregation(AggregationBuilders.sum(bounceSessionSum).field("bounceSessionCount"));

		log.debug(request.toString());

		SearchResponse response = request.execute().actionGet();

		log.debug(response.toString());

		// Save the final stats
		Histogram agg2 = response.getAggregations().get("date_aggs");

		List<WebSourceStats> saveStats = new ArrayList<WebSourceStats>();

		for (Histogram.Bucket dateEntry : agg2.getBuckets()) {

			DateTime statsDate = (DateTime) dateEntry.getKey();

			Terms sourceTerms = dateEntry.getAggregations().get(souceTermAggsName);
			for (Terms.Bucket souceEntry : sourceTerms.getBuckets()) {
				String source_url = (String) souceEntry.getKey();

				Avg sessionTimeAvg = souceEntry.getAggregations().get(sessionTimeAggs);

				Avg reqPageAvg = souceEntry.getAggregations().get(reqPagesAggs);

				Sum exitSessionSumAggs = souceEntry.getAggregations().get(exitSessionSum);

				Sum bounceSessionSumAggs = souceEntry.getAggregations().get(bounceSessionSum);

				saveStats.add(new WebSourceStats(source_url, statsDate.toDate(), (Double.valueOf(exitSessionSumAggs
						.getValue())).longValue(), (Double.valueOf(bounceSessionSumAggs.getValue())).longValue(),
						Double.valueOf(sessionTimeAvg.getValue()), Double.valueOf(reqPageAvg.getValue())));

			}
		}

		baseESOption.saveOrUpdateMultiObjests(saveResourceIndex, saveResourceIndexType, saveStats.toArray());

	}
}
