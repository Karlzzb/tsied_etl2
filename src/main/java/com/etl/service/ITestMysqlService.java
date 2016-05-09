package com.etl.service;

import java.util.List;

import com.etl.dao.entity.TestMysql;

public interface ITestMysqlService {
	List<TestMysql> findTestByTest(TestMysql test);
	void saveTest(TestMysql test);
}
