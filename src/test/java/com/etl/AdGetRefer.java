package com.etl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.etl.es.BaseESOption;
import com.etl.service.impl.AdGetReferServiceImpl;

/**
 * @author Rainisic
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../applicationContext.xml")
public class AdGetRefer extends AbstractJUnit4SpringContextTests {

	@Resource
	private AdGetReferServiceImpl adGetReferStatsService;

	@Autowired
	private BaseESOption apESOption;

	@Autowired
	private BaseESOption baseESOption;

	// 2015-04-25 1461513600000 1461599999999
	// 2015-04-26 1461600000000 1461686399999

	@Test
	public void getReferQueryTest() {

		adGetReferStatsService.adGetReferStats("www-*", Long.parseLong("1462636800000"),
				Long.parseLong("1462723199999"));
	}

}