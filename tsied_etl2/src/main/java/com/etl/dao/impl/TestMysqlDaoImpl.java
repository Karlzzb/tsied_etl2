package com.etl.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.etl.dao.ITestMysqlDao;
import com.etl.dao.entity.TestMysql;

@Repository
public class TestMysqlDaoImpl extends SqlSessionDaoSupport implements ITestMysqlDao{

	@Override
	public List<TestMysql> findTestByTest(TestMysql test) {
		return this.getSqlSession().selectList("selectTest",test);
	}

	@Override
	public void saveTest(TestMysql test) {
		this.getSqlSession().insert("saveTest",test);
	}
	
}
