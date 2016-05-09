package com.etl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.etl.mongo.ValueObject;

/**
 * @author Rainisic
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../applicationContext.xml")
public class MongoTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MongoOperations mongoOps;

	@Test
	public void saveTest() {
		String mapFunction = "function () { var cdate = get_date(-2); var pdate = get_date(-1); var flag = \"-1\"; if (this.adate == pdate || this.adate == cdate) {emit({gid: this.gid, adate: this.adate}, {gid: this.gid, cnt: this.login_list.length, login_list: ary_unique(this.login_list), adate: this.adate, flag: flag});}}";

		String reduceFunction = "function (key, values) {var cdate = get_date(-1); var ret = {login_list: [], cnt: 0, today: cdate, flag: \"1\"}; values.forEach(function (v) {ret.login_list.push.apply(ret.login_list, v.login_list); }); ret.login_list = ary_unique(ret.login_list);ret.cnt = ret.login_list.length;ret.flag = v.flag;return ret;}";

		MapReduceResults<ValueObject> results = mongoOps.mapReduce("game_login_log", mapFunction, reduceFunction,
				ValueObject.class);

		for (ValueObject valueObject : results) {
			System.out.println(valueObject);
		}

	}
}