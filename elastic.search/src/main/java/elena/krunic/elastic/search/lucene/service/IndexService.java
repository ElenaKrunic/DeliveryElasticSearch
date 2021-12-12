package elena.krunic.elastic.search.lucene.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.krunic.elastic.search.lucene.helper.Indices;
import elena.krunic.elastic.search.lucene.helper.Util;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;


@Service
public class IndexService {
	
	private final List<String> INDICES = List.of(Indices.PRODUCT_INDEX);
	private final RestHighLevelClient client; 
	
	@Autowired 
	public IndexService(RestHighLevelClient client) {
		this.client = client; 
	}
	
	@PostConstruct
	public void tryToCreateIndices() {
		recreateIndices(false);
	}

	public void recreateIndices(final Boolean deleteExisting) {
		final String settings = Util.loadAsString("static/es-settings.json");
		
		if(settings == null) {
			System.out.println("Settings je null");
			return; 
		}
		
		for (final String indexName : INDICES) {
			try {
				final boolean indexExists = client
						.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
				if(indexExists) {
					if(!deleteExisting) {
						continue;
					}
					
					client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
				}
				
				final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
				createIndexRequest.settings(settings, XContentType.JSON);
				
				final String mappings = loadMappings(indexName);
				if(mappings != null) {
					createIndexRequest.mapping(mappings, XContentType.JSON);
				}
				
				client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	private String loadMappings(String indexName) {
		final String mappings = Util.loadAsString("static/mappings/" + indexName + ".json");
		
		if(mappings == null) {
			return null; 
		}
		
		return mappings; 
	}

}
