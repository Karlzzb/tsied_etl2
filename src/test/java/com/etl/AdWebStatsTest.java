package com.etl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.etl.es.BaseESOption;
import com.etl.service.impl.AdWebStatsServiceImpl;

/**
 * @author Rainisic
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../applicationContext.xml")
public class AdWebStatsTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private AdWebStatsServiceImpl adwebStatsService;

	@Autowired
	private BaseESOption apESOption;

	@Autowired
	private BaseESOption baseESOption;

	@Test
	public void deleteTest() {
		baseESOption.deleteByIndex(new String[] { "test_stats" });

	}

	@Test
	public void queryTest() {

		adwebStatsService.adwebStatsAllDaily("logstash-*", Long.parseLong("1456761600000"),
				Long.parseLong("1459439999999"), "", "test_stats", "test_site");
	}
}