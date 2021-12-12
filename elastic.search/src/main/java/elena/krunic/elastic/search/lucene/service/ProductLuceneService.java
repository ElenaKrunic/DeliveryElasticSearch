package elena.krunic.elastic.search.lucene.service;

import java.util.List;

import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.lucene.dto.ProductLuceneDTO;
import elena.krunic.elastic.search.lucene.helper.Indices;
import elena.krunic.elastic.search.lucene.model.ProductLucene;
import elena.krunic.elastic.search.model.Product;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

@Service
public class ProductLuceneService {
	
	private final static ObjectMapper MAPPER = new ObjectMapper();
	private RestHighLevelClient client;
	
	@Autowired
	public ProductLuceneService(RestHighLevelClient client) {
		this.client = client; 
	}
	
	public ProductLuceneDTO getById(final Long id) {
		try {
			final GetResponse documentFields = client.get(new GetRequest(Indices.PRODUCT_INDEX, id.toString()), RequestOptions.DEFAULT);
			
			System.out.println("Document fields su " + documentFields);
			
			if(documentFields == null) {
				return null;
			}
			
			return MAPPER.readValue(documentFields.getSourceAsString(), ProductLuceneDTO.class);
		} catch(Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	public Boolean index(ProductLuceneDTO dto) {
		try {
			final String productAsString = MAPPER.writeValueAsString(dto);
			final IndexRequest request = new IndexRequest(Indices.PRODUCT_INDEX);
			
			request.id(dto.getId().toString());
			request.source(productAsString, XContentType.JSON);
			
			final IndexResponse response = client.index(request, RequestOptions.DEFAULT);
			
			return response != null && response.status().equals(RestStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

}
