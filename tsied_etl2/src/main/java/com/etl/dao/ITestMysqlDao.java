package com.etl.dao;

import java.util.List;

import com.etl.dao.entity.TestMysql;

public interface ITestMysqlDao {
	List<TestMysql> findTestByTest(TestMysql test);
	void saveTest(TestMysql test);
}
