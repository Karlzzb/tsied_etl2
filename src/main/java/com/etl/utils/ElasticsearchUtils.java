package com.etl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService.ScriptType;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Elasticsearch tools
 * 
 * @author kerl
 *
 */
public class ElasticsearchUtils {

	private static Logger log = LoggerFactory.getLogger(ElasticsearchUtils.class);

	private static Gson gson = new GsonBuilder().setDateFormat(DateUtils.ES_DATE_TIME_FORMAT_ZONE)
			.serializeSpecialFloatingPointValues().create();

	/**
	 * Construcate AggregationBuilder
	 * 
	 * @param parentKey
	 * @param jsonNode
	 * @param endTime
	 * @param startTime
	 * @return
	 */
	public static AbstractAggregationBuilder parseJson(String parentKey, JsonNode jsonNode, long startTime, long endTime) {
		AbstractAggregationBuilder aggsBuilder = null;

		try {

			Map<String, Object> node = JSON.parseObject(jsonNode.toString(), new TypeReference<Map<String, Object>>() {
			});
			for (String str : node.keySet()) {

				switch (str) {
				case "aggs":
					break;
				case "terms":
					TermsBuilder termsBuilder = AggregationBuilders.terms(parentKey);
					if (jsonNode.get("terms").get("field") != null) {
						termsBuilder.field(jsonNode.get("terms").get("field").textValue());
					}
					if (jsonNode.get("terms").get("size") != null) {
						termsBuilder.size(Integer.parseInt(jsonNode.get("terms").get("size").toString()));
					}
					if (jsonNode.get("terms").get("min_doc_count") != null) {
						termsBuilder
								.minDocCount(Long.parseLong(jsonNode.get("terms").get("min_doc_count").textValue()));
					}
					aggsBuilder = termsBuilder;
					if (jsonNode.get("aggs") != null) {
						for (String str2 : JSON.parseObject(jsonNode.get("aggs").toString(),
								new TypeReference<Map<String, Object>>() {
								}).keySet()) {
							termsBuilder.subAggregation(parseJson(str2, jsonNode.get("aggs").get(str2), startTime,
									endTime));
						}

					}
					break;
				case "date_histogram":
					DateHistogramBuilder dateTermsBuilder = null;
					String interval = jsonNode.get("date_histogram").get("interval").textValue();
					if (interval.equals("1d")) {
						dateTermsBuilder = AggregationBuilders.dateHistogram(parentKey).interval(
								DateHistogramInterval.DAY);
					} else if (interval.equals("1M")) {
						dateTermsBuilder = AggregationBuilders.dateHistogram(parentKey).interval(
								DateHistogramInterval.MONTH);
					}

					if (jsonNode.get("date_histogram").get("field") != null) {
						dateTermsBuilder.field(jsonNode.get("date_histogram").get("field").textValue());
					}
					if (jsonNode.get("date_histogram").get("min_doc_count") != null) {
						dateTermsBuilder.minDocCount(Integer.parseInt(jsonNode.get("date_histogram")
								.get("min_doc_count").toString()));
					}
					dateTermsBuilder.extendedBounds(startTime, endTime);

					if (jsonNode.get("date_histogram").get("time_zone") != null) {
						dateTermsBuilder.timeZone(jsonNode.get("date_histogram").get("time_zone").textValue());
					}
					aggsBuilder = dateTermsBuilder;
					if (jsonNode.get("aggs") != null) {
						for (String str2 : JSON.parseObject(jsonNode.get("aggs").toString(),
								new TypeReference<Map<String, Object>>() {
								}).keySet()) {
							dateTermsBuilder.subAggregation(parseJson(str2, jsonNode.get("aggs").get(str2), startTime,
									endTime));
						}
					}

					break;
				case "sum":
					SumBuilder sumBuilder = AggregationBuilders.sum(parentKey);
					if (jsonNode.get("sum").get("script") != null) {
						String scripts = jsonNode.get("sum").get("script").textValue();

						sumBuilder.script(new Script(scripts, ScriptType.INLINE, "expression", null));
					}
					if (jsonNode.get("sum").get("field") != null) {
						sumBuilder.field(jsonNode.get("sum").get("field").textValue());
					}
					aggsBuilder = sumBuilder;
					break;
				case "min":
					aggsBuilder = AggregationBuilders.min(parentKey)
							.field(jsonNode.get("min").get("field").textValue());
					break;
				case "max":
					aggsBuilder = AggregationBuilders.max(parentKey)
							.field(jsonNode.get("max").get("field").textValue());
					break;
				case "avg":
					aggsBuilder = AggregationBuilders.avg(parentKey)
							.field(jsonNode.get("avg").get("field").textValue());
					break;
				case "cardinality":
					aggsBuilder = AggregationBuilders.cardinality(parentKey).field(
							jsonNode.get("cardinality").get("field").textValue());
					break;
				case "percentiles":
					String[] percentilesStr = jsonNode.get("percentiles").get("percents").textValue().split(",");
					double[] percentiles = new double[] {};
					for (int i = 0; i < percentilesStr.length; i++) {
						percentiles[i] = Double.parseDouble(percentilesStr[i]);
					}
					aggsBuilder = AggregationBuilders.percentiles(parentKey)
							.field(jsonNode.get("percentiles").get("field").textValue()).percentiles(percentiles);
					break;
				case "top_hits":
					TopHitsBuilder topHitsBuilder = AggregationBuilders.topHits(parentKey);

					topHitsBuilder.setSize(jsonNode.get("top_hits").get("size") != null ? jsonNode.get("top_hits")
							.get("size").asInt() : 1);
					if (jsonNode.get("top_hits").get("_source") != null) {
						List<String> includes = new ArrayList<String>();
						List<String> excludes = new ArrayList<String>();

						if (jsonNode.get("top_hits").get("_source").get("includes").isArray()) {
							for (Iterator<JsonNode> iterator = jsonNode.get("top_hits").get("_source").get("includes")
									.iterator(); iterator.hasNext();) {
								JsonNode textNode = (JsonNode) iterator.next();
								includes.add(textNode.asText());
							}
						}

						if (jsonNode.get("top_hits").get("_source").get("excludes").isArray()) {
							for (Iterator<JsonNode> iterator = jsonNode.get("top_hits").get("_source").get("excludes")
									.iterator(); iterator.hasNext();) {
								JsonNode textNode = (JsonNode) iterator.next();
								excludes.add(textNode.asText());
							}
						}

						topHitsBuilder.setFetchSource(includes.size() > 0 ? includes.toArray(new String[] {}) : null,
								excludes.size() > 0 ? excludes.toArray(new String[] {}) : null);
					}
					if (jsonNode.get("top_hits").get("sort") != null) {
						for (String mykey : JSON.parseObject(jsonNode.get("top_hits").get("sort").get(0).toString(),
								new TypeReference<Map<String, Object>>() {
								}).keySet()) {

							topHitsBuilder.addSort(mykey,
									jsonNode.get("top_hits").get("sort").get(0).get(mykey).get("order").textValue()
											.equalsIgnoreCase("desc") ? SortOrder.DESC : SortOrder.ASC);
						}
					}

					aggsBuilder = topHitsBuilder;
					break;
				default:
					aggsBuilder = parseJson(str, jsonNode.get(str), startTime, endTime);
					break;
				}
			}
		} catch (Exception e) {
			log.error("Construcate aggregation failed", e);

		}

		return aggsBuilder;
	}

	/**
	 * format ES result
	 * 
	 * @param pdata
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static String formatEsResult(Map<String, String> paramMap) {
		ObjectMapper mapper = new ObjectMapper();
		Configuration cfg = new Configuration();// init freemarker template
		String result = null;
		try {
			cfg.setDirectoryForTemplateLoading(new File(Thread.currentThread().getContextClassLoader()
					.getResource("resource/template/freemarker/").getPath()));
			Template temp = cfg.getTemplate(paramMap.get("templateName"));
			Map<String, Object> map = new HashMap<String, Object>();
			JsonNode goodsArray = mapper.readTree(paramMap.get("data"));
			StringWriter sw = new StringWriter();
			if (goodsArray.isArray()) {
				List<?> data = mapper.readValue(paramMap.get("data"), List.class);
				map.put("data", data);
				temp.process(map, sw);
			} else {
				Map<String, Object> data = mapper.readValue(paramMap.get("data"), Map.class);
				map.put("data", data);
				temp.process(data, sw);
			}
			result = sw.toString();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}

	/**
	 * Read Elasticsearch template
	 * 
	 * @param templatePath
	 * @param searchParam
	 */
	public static String readSourceTemplate(String templateName) {
		String sourcePath = Thread.currentThread().getContextClassLoader().getResource("resource/template/es/")
				.getPath();

		String templatePath = sourcePath + templateName;

		StringBuilder strBuffer = new StringBuilder();
		try {
			/**
			 * read template content
			 */
			try (BufferedReader bodyReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(templatePath), "utf8"))) {
				String line = null;
				while ((line = bodyReader.readLine()) != null) {
					strBuffer.append(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strBuffer.toString();
	}

	/**
	 * search execQuery
	 * 
	 * @param source
	 * @param indexName
	 * @return
	 */
	public String execQuery(String source, Script script) {
		StringBuffer queryBody = new StringBuffer();
		try {

			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(source);

			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("templateName", "script-body.ftl");
			paramMap.put("data", mapper.writeValueAsString(script));
			String scriptBody = formatEsResult(paramMap);
			queryBody.append(scriptBody);

			if (!StringUtil.isBlank(jsonNode.get("size").toString())) {
				queryBody.append(",\"size\":").append(jsonNode.get("size").toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return queryBody.toString();
	}

	public static String objectToJson(Object obj) {

		return obj == null ? null : gson.toJson(obj);
	}
}
