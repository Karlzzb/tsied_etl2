package com.etl;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.etl.es.BaseESOption;
import com.etl.utils.DateUtils;

public class TempTest {

	private static final String ID_NOT_FOUND = "<ID NOT FOUND>";

	private static Client getClient() {
		BaseESOption myes = new BaseESOption("59.188.30.132", 9300, "elasticsearch");

		return myes.getClient();
	}

	public static void main(final String[] args) throws IOException, InterruptedException {
		final String mydate = Long.toString(DateUtils.getTime(DateUtils.getCurrent()));

		final Client client = getClient();
		// Create Index and set settings and mappings
		final String indexName = "test";
		final String documentType = "tweet";
		final String documentId = "3";
		final String fieldName = "foo";
		final String value = "bar";

		final IndicesExistsResponse res = client.admin().indices().prepareExists(indexName).execute().actionGet();
		if (res.isExists()) {
			final DeleteIndexRequestBuilder delIdx = client.admin().indices().prepareDelete(indexName);
			delIdx.execute().actionGet();
		}

		final CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);

		// MAPPING GOES HERE

		final XContentBuilder mappingBuilder = jsonBuilder().startObject().startObject(documentType)
				.startObject("_timestamp").field("enabled", "true").field("default", mydate).endObject().endObject()
				.endObject();
		System.out.println(mappingBuilder.string());
		createIndexRequestBuilder.addMapping(documentType, mappingBuilder);

		// MAPPING DONE
		createIndexRequestBuilder.execute().actionGet();

		// Add documents
		final IndexRequestBuilder indexRequestBuilder = client.prepareIndex(indexName, documentType, documentId)
				.setTimestamp("1358793638086");
		// build json object
		final XContentBuilder contentBuilder = jsonBuilder().startObject().prettyPrint();
		contentBuilder.field(fieldName, value);

		indexRequestBuilder.setSource(contentBuilder);
		indexRequestBuilder.execute().actionGet();

	}

	protected static String getValue(final Client client, final String indexName, final String documentType,
			final String documentId, final String fieldName) {
		final GetRequestBuilder getRequestBuilder = client.prepareGet(indexName, documentType, documentId);
		getRequestBuilder.setFields(new String[] { fieldName });
		final GetResponse response2 = getRequestBuilder.execute().actionGet();
		if (response2.isExists()) {
			final String name = response2.getField(fieldName).getValue().toString();
			return name;
		} else {
			return ID_NOT_FOUND;
		}

	}

}