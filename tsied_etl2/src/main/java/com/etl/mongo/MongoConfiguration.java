package com.etl.mongo;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * Created by vivex on 29/10/15.
 */

@Configuration
@EnableMongoRepositories(basePackages = { "repositories" })
public class MongoConfiguration extends AbstractMongoConfiguration {
	@Override
	protected String getDatabaseName() {
		return "sample";
	}

	public @Bean Mongo mongo() throws UnknownHostException {
		ServerAddress serverAddress = new ServerAddress("localhost", 27017);
		MongoCredential credential = MongoCredential.createMongoCRCredential("vivek", "sample", "vivek".toCharArray());
		MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(4).socketKeepAlive(true).build();
		Mongo mongo = new MongoClient(serverAddress, Arrays.asList(credential), options);
		// mongo.setWriteConcern(WriteConcern.SAFE);
		return mongo;
	}

	@Bean(name = "MongoTemplate")
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), "sample");
	}

}