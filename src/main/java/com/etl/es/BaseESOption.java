package com.etl.es;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryAction;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etl.utils.ElasticsearchUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Elasticsearch options
 * 
 * @author Kerl
 *
 */
public class BaseESOption {

	private TransportClient client;

	private static Logger log = LoggerFactory.getLogger(BaseESOption.class);

	private static String mappingSource = Thread.currentThread().getContextClassLoader().getResource("template/es/")
			.getPath()
			+ "tsied_default_mapping.json";

	private BufferedReader bodyReader;

	public BaseESOption(String esip, Integer esport, String clusterName) {
		try {
			Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).build();
			client = TransportClient.builder().settings(settings).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esip), esport));
		} catch (Exception e) {
			log.error("Elasticsearch connection failed", e);
			closed();
		}
	}

	/**
	 * close client
	 */
	public void closed() {
		if (null != client) {
			client.close();
			client = null;
		}
	}

	/**
	 * search execQuery
	 * 
	 * @param searchTemplate
	 * @param index
	 * @return
	 */
	public SearchResponse execQuerySimple(String searchTemplate, String index, long startTime, long endTime,
			String... indexTypes) {
		SearchResponse sResponse = null;
		StringBuilder mappingBuilder = new StringBuilder();
		try {
			bodyReader = new BufferedReader(new InputStreamReader(new FileInputStream(searchTemplate), "utf8"));
			String line = null;
			while ((line = bodyReader.readLine()) != null) {
				mappingBuilder.append(line);
			}

			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(mappingBuilder.toString());

			JSONObject jsonObject = JSONObject.parseObject(jsonNode.get("query").toString());

			String queryBody = jsonObject.toJSONString();
			queryBody = queryBody.replace("1144", String.valueOf(startTime));
			queryBody = queryBody.replace("9944", String.valueOf(endTime));

			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(indexTypes).setQuery(queryBody)
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

			if (jsonNode.get("aggs") != null) {
				AbstractAggregationBuilder myAggs = ElasticsearchUtils.parseJson("aggs", jsonNode.get("aggs"),
						startTime, endTime);
				if (myAggs != null) {
					srb.addAggregation(myAggs);
				}
			}
			log.debug(srb.toString());
			sResponse = srb.execute().actionGet();

		} catch (Exception e) {
			log.error("excute template failed", e);
		} finally {
			mappingBuilder = null;
		}

		return sResponse;
	}

	/**
	 * search execQuery
	 * 
	 * @param searchTemplate
	 * @param index
	 * @return
	 */
	public SearchResponse execQuery(String searchTemplate, String index, long startTime, long endTime,
			String... indexTypes) {
		SearchResponse sResponse = null;
		StringBuilder mappingBuilder = new StringBuilder();
		try {
			bodyReader = new BufferedReader(new InputStreamReader(new FileInputStream(searchTemplate), "utf8"));
			String line = null;
			while ((line = bodyReader.readLine()) != null) {
				mappingBuilder.append(line);
			}

			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(mappingBuilder.toString());

			JSONObject jsonObject = JSONObject.parseObject(jsonNode.get("query").toString());

			JSONArray mustNodeArray = jsonObject.getJSONObject("filtered").getJSONObject("filter")
					.getJSONObject("bool").getJSONArray("must");

			String dateRange = "";

			for (Iterator<Object> iterator = mustNodeArray.iterator(); iterator.hasNext();) {
				JSONObject jSONObject = (JSONObject) iterator.next();

				if (jSONObject.getJSONObject("range") != null) {
					@SuppressWarnings("unchecked")
					Map<String, String> timestampMap = mapper.readValue(jSONObject.getJSONObject("range")
							.toJSONString(), Map.class);
					for (String object : timestampMap.keySet()) {
						dateRange = object;
						break;
					}
				}

			}

			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("templateName", "script-body.ftl");
			paramMap.put("data", mapper.writeValueAsString(new Script(index, startTime, endTime, dateRange)));
			String queryBody = ElasticsearchUtils.formatEsResult(paramMap);

			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(indexTypes).setQuery(queryBody)
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

			if (jsonNode.get("aggs") != null) {
				AbstractAggregationBuilder myAggs = ElasticsearchUtils.parseJson("aggs", jsonNode.get("aggs"),
						startTime, endTime);
				if (myAggs != null) {
					srb.addAggregation(myAggs);
				}
			}
			log.debug(srb.toString());
			sResponse = srb.execute().actionGet();

		} catch (Exception e) {
			log.error("excute template failed", e);
		} finally {
			mappingBuilder = null;
		}

		return sResponse;
	}

	public void searchData(String index, String... indexType) {
		QueryBuilder qb = termQuery("multi", "test");

		SearchResponse scrollResp = client.prepareSearch(index).setIndices(index)
				.setSearchType(SearchType.DFS_QUERY_AND_FETCH).setScroll(new TimeValue(60000)).setQuery(qb)
				.setSize(100).execute().actionGet();
		// Scroll until no hits are returned
		while (true) {

			for (SearchHit hit : scrollResp.getHits().getHits()) {
				// Handle the hit...
				System.out.println(hit.toString());
			}
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute()
					.actionGet();
			// Break condition: No hits are returned
			if (scrollResp.getHits().getHits().length == 0) {
				break;
			}
		}

	}

	/**
	 * Insert multiple Objects to Eslasticsearch
	 * 
	 * @param index
	 * @param indexType
	 * @param objs
	 */
	public void addMultiObjests(String index, String indexType, Object... objs) {
		BulkRequestBuilder bulkRequest = client.prepareBulk();

		createIndex(index, indexType);

		String mySource = null;

		for (int i = 0; i < objs.length; i++) {
			Object object = (Object) objs[i];
			try {
				mySource = ElasticsearchUtils.objectToJson(object);
				log.info(mySource);

				bulkRequest.add(client.prepareIndex(index, indexType,
						object.getClass().getMethod("getId").invoke(object).toString()).setSource(mySource));
			} catch (Exception e) {
				log.error("Insert Obj do not have getId Methon", e);
			}
		}

		BulkResponse bulkResponse = bulkRequest.setRefresh(true).execute().actionGet();
		if (bulkResponse.hasFailures()) {
			log.error(bulkResponse.buildFailureMessage());
		}
	}

	/**
	 * Insert multiple Objects to Eslasticsearch
	 * 
	 * @param index
	 * @param indexType
	 * @param objs
	 */
	public void saveOrUpdateMultiObjests(String index, String indexType, Object... objs) {
		BulkRequestBuilder bulkRequest = client.prepareBulk();

		createIndex(index, indexType);

		String mySource = null;

		for (int i = 0; i < objs.length; i++) {
			Object object = (Object) objs[i];
			mySource = ElasticsearchUtils.objectToJson(object);
			log.debug(mySource);
			try {
				bulkRequest
						.add(client
								.prepareUpdate(index, indexType,
										object.getClass().getMethod("getId").invoke(object).toString())
								.setDoc(mySource).setUpsert(mySource));
			} catch (Exception e) {
				log.error("Insert Obj do not have getId Methon", e);
			}
		}

		BulkResponse bulkResponse = bulkRequest.setRefresh(true).execute().actionGet();
		if (bulkResponse.hasFailures()) {
			log.error(bulkResponse.buildFailureMessage());
		}
	}

	/**
	 * Create Index with Mapping
	 * 
	 * @param index
	 * @param indexType
	 */
	public void createIndex(String index, String indexType) {
		if (exists(index)) {
			return;
		}

		StringBuilder mappingBuilder = new StringBuilder();

		try {
			bodyReader = new BufferedReader(new InputStreamReader(new FileInputStream(mappingSource), "utf8"));
			String line = null;
			while ((line = bodyReader.readLine()) != null) {
				mappingBuilder.append(line);
			}

			CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(index)
					.setSource(mappingBuilder.toString()).setUpdateAllTypes(true);
			createIndexRequestBuilder.execute().actionGet();

		} catch (Exception e) {
			log.error("create index failed!", e);
		} finally {
			mappingBuilder = null;
		}

	}

	/**
	 * Delete The Whole IndexType
	 * 
	 * @param index
	 * @param indexType
	 */
	public void deleteByIndexType(String index, String indexType) {
		DeleteByQueryRequestBuilder requestBuilder = new DeleteByQueryRequestBuilder(client,
				DeleteByQueryAction.INSTANCE);
		QueryBuilder qb = matchAllQuery();
		requestBuilder.setIndices(index).setTypes(indexType).setQuery(qb);
		requestBuilder.execute().actionGet();
	}

	/**
	 * 
	 * @param index
	 */
	public void deleteByIndex(String... indices) {

		DeleteIndexResponse response = client.admin().indices().delete(new DeleteIndexRequest(indices)).actionGet();

		log.info("Deleted indices:[" + Arrays.toString(indices) + "] response:" + response.getContext().toString());

	}

	/**
	 * if a index already existed
	 * 
	 * @param indices
	 * @return
	 */
	public boolean exists(String index) {

		boolean result = false;

		try {
			SearchResponse response = client.prepareSearch(index).setSize(0).execute().actionGet();
			result = response.isContextEmpty();
		} catch (Exception e) {
			log.info(index + " already existed");
		}
		return result;
	}

	/**
	 * Delete data by Ids[]
	 * 
	 * @param index
	 * @param indexType
	 * @param ids
	 */
	public void deleteByIndexIds(String index, String indexType, String... ids) {
		if (ids != null) {
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			for (int i = 0; i < ids.length; i++) {
				bulkRequest.add(client.prepareDelete(index, indexType, ids[i]));
			}
			BulkResponse bulkResponse = bulkRequest.setRefresh(true).execute().actionGet();
			if (bulkResponse.hasFailures()) {
				log.error("addMultiObjests failure");
			}
		}
	}

	public TransportClient getClient() {
		return client;
	}
}
