package elena.krunic.elastic.search.lucene.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.lucene.dto.ProductLuceneDTO;
import elena.krunic.elastic.search.lucene.dto.SearchRequestDTO;
import elena.krunic.elastic.search.lucene.helper.Indices;
import elena.krunic.elastic.search.lucene.model.ProductLucene;
import elena.krunic.elastic.search.lucene.repository.ProductLuceneRepository;
import elena.krunic.elastic.search.lucene.utils.SearchUtil;
import elena.krunic.elastic.search.model.Product;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;


@Service
public class ProductLuceneService {
	
	private final static ObjectMapper MAPPER = new ObjectMapper();
	private RestHighLevelClient client;
	
	public static final String PRODUCT_INDEX = "products";

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	public ProductLuceneService(RestHighLevelClient client) {
		this.client = client; 
	}
	
	public List<IndexedObjectInformation> createProductIndexBulk(final List<ProductLuceneDTO> products) {

		List<IndexQuery> queries = products.stream()
				.map(product -> new IndexQueryBuilder().withId(product.getId().toString()).withObject(product).build())
				.collect(Collectors.toList());
		;

		return elasticsearchOperations.bulkIndex(queries, IndexCoordinates.of(PRODUCT_INDEX));

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
	
	public List<ProductLuceneDTO> searchMatchQuery(final SearchRequestDTO dto) {
		
		final SearchRequest request = SearchUtil.buildSearchRequest(Indices.PRODUCT_INDEX, dto); 
		
		if(request == null) {
			System.out.println("Request je null");
			return null; 
		}
		
		return searchInternal(request);
	}
	
	private List<ProductLuceneDTO> searchInternal (final SearchRequest searchRequest) {
		
		if(searchRequest == null) {
			return Collections.emptyList(); 
		}
		
		try {
			final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT); 
			final SearchHit[] searchHits = searchResponse.getHits().getHits();			
			final List<ProductLuceneDTO> luceneDtos = new ArrayList<ProductLuceneDTO>(searchHits.length);
	
			for(SearchHit hit : searchHits) {
				luceneDtos.add(MAPPER.readValue(hit.getSourceAsString(), ProductLuceneDTO.class));
				System.out.println(">>> Search hit je: >>> " + hit.getSourceAsString());
			}
						
			return luceneDtos; 
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

		
	
}
