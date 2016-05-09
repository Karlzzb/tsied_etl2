package com.etl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.etl.dao.entity.TestMysql;
import com.etl.mutilDataSource.DataSourceSwitch;
import com.etl.mutilDataSource.MutilDataSource;
import com.etl.service.ITestMysqlService;

/**
 * @author Rainisic
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../applicationContext.xml")
public class MysqlTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ITestMysqlService testMysqlService;

	public ITestMysqlService getTestMysqlService() {
		return testMysqlService;
	}

	public void setTestMysqlService(ITestMysqlService testMysqlService) {
		this.testMysqlService = testMysqlService;
	}

	@Test
	public void saveTest() {
		TestMysql test = new TestMysql();
		DataSourceSwitch.setDataSourceType(MutilDataSource.GB_LEAJOY);
		test.setTestKey("啊伯特");
		test.setTestValue(22);
		testMysqlService.saveTest(test);
	}

	/*
	 * @Test public void findTest() { TestMysql test = new TestMysql();
	 * test.setTestValue(19); List<TestMysql> testList =
	 * testMysqlService.findTestByTest(test); for (TestMysql testMysql :
	 * testList) { System.out.println("============"+testMysql.getTestKey());
	 * System.out.println("============"+testMysql.getTestValue()); } }
	 */
}
