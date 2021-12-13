package elena.krunic.elastic.search.lucene.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import elena.krunic.elastic.search.lucene.dto.ErrandLuceneDTO;
import elena.krunic.elastic.search.lucene.dto.ProductLuceneDTO;
import elena.krunic.elastic.search.lucene.dto.SearchRequestDTO;
import elena.krunic.elastic.search.lucene.helper.Indices;
import elena.krunic.elastic.search.lucene.utils.SearchUtil;

@Service
public class ErrandLuceneService {
	
	private final static ObjectMapper MAPPER = new ObjectMapper();
	private RestHighLevelClient client;
	public static final String ERRAND_INDEX = "errands";
	
	@Autowired
	private ElasticsearchOperations elasticsearchOperations;
	
	@Autowired 
	public ErrandLuceneService(RestHighLevelClient client) {
		this.client = client; 
	}
	
	

	public List<IndexedObjectInformation> createErrandIndexBulk(List<ErrandLuceneDTO> errands) {
		List<IndexQuery> queries = errands.stream()
				.map(errand -> new IndexQueryBuilder().withId(errand.getId().toString()).withObject(errand).build())
				.collect(Collectors.toList());
		;
		
		return elasticsearchOperations.bulkIndex(queries, IndexCoordinates.of(ERRAND_INDEX));
	}



	public List<ErrandLuceneDTO> searchErrandMatchQuery(SearchRequestDTO dto) {
		
		final SearchRequest request = SearchUtil.buildSearchRequest(Indices.ERRAND_INDEX, dto);
		
		if(request == null) {
			System.out.println("Request je null");
			return null; 
		}
		
		return searchInternal(request);
		
	}

	private List<ErrandLuceneDTO> searchInternal(SearchRequest searchRequest) {
		if(searchRequest == null) {
			return Collections.emptyList(); 
		}
		
		try {
			final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT); 
			final SearchHit[] searchHits = searchResponse.getHits().getHits();			
			final List<ErrandLuceneDTO> luceneDtos = new ArrayList<ErrandLuceneDTO>(searchHits.length);
	
			for(SearchHit hit : searchHits) {
				luceneDtos.add(MAPPER.readValue(hit.getSourceAsString(), ErrandLuceneDTO.class));
			}
						
			return luceneDtos; 
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
