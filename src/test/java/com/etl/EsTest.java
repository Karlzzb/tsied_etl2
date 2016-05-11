package com.etl;

import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.etl.es.BaseESOption;

/**
 * @author Rainisic
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../applicationContext.xml")
public class EsTest extends AbstractJUnit4SpringContextTests {

	// /**
	// * The Default web stats template
	// */
	// private static String webStatsTemplate =
	// Thread.currentThread().getContextClassLoader()
	// .getResource("resource/template/es/").getPath()
	// + "flow-analysis.customcache";

	@Autowired
	private BaseESOption apESOption;

	@Autowired
	private BaseESOption baseESOption;

	@Test
	public void emptyTest() {
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void deleteTest() {
		apESOption.deleteByIndex(new String[] { "temp_datanginx-www-access", "all_stats" });

	}

	@Test
	public void getById() {
		GetResponse getResponse = apESOption.getClient().prepareGet("pay_s", "pay_game", "2016-05-10-local-2").get();

		Map<String, Object> source = getResponse.getSource();
		System.out.println("------------------------------");
		System.out.println("Index: " + getResponse.getIndex());
		System.out.println("Type: " + getResponse.getType());
		System.out.println("Id: " + getResponse.getId());
		System.out.println("Version: " + getResponse.getVersion());
		System.out.println(source);
		System.out.println("------------------------------");

	}

	@Test
	public void searchTest() {
		SearchResponse sResponse = null;
		try {
			SearchRequestBuilder srb = baseESOption
					.getClient()
					.prepareSearch("logstash-*")
					.setPostFilter(
							QueryBuilders.boolQuery().must(
									QueryBuilders.matchPhraseQuery("clientip.raw", "211.103.255.5")))
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

			TermsBuilder termAggs = AggregationBuilders.terms("term_aggs").field("clientip.raw");

			termAggs.subAggregation(AggregationBuilders.topHits("top_req").setSize(1)
					.addSort("@timestamp", SortOrder.DESC).setFetchSource(new String[] { "request", "refer" }, null));

			termAggs.subAggregation(AggregationBuilders.topHits("button_req").setSize(1)
					.addSort("@timestamp", SortOrder.ASC).setFetchSource(new String[] { "request" }, null));

			srb.addAggregation(termAggs);

			System.out.print(srb);

			sResponse = srb.execute().actionGet();

			System.out.print(sResponse);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
		}
	}
}