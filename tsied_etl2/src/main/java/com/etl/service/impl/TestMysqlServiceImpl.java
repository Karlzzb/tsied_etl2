package com.etl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.dao.ITestMysqlDao;
import com.etl.dao.entity.TestMysql;
import com.etl.service.ITestMysqlService;

@Service("testMysqlService")
public class TestMysqlServiceImpl implements ITestMysqlService{

	@Autowired
	private ITestMysqlDao testMysqlDao;
	
	@Override
	public List<TestMysql> findTestByTest(TestMysql test) {
		return testMysqlDao.findTestByTest(test);
	}

	@Override
	public void saveTest(TestMysql test) {
		testMysqlDao.saveTest(test);
	}
	
}
