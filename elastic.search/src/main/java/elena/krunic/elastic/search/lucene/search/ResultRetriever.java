package elena.krunic.elastic.search.lucene.search;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.lucene.model.RequiredHighlight;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ResultRetriever {

	private static final Logger log = LoggerFactory.getLogger(ResultRetriever.class);
	private static int maxHits = 10; 
	private static JestClient jestClient;
	
	 static {
	        JestClientFactory factory = new JestClientFactory();
	        factory.setHttpClientConfig(new HttpClientConfig
	                .Builder("http://localhost:9200")
	                .multiThreaded(true)
	                .build());
	        ResultRetriever.jestClient = factory.getObject();

	    }

	    public static void setMaxHits(int maxHits) {
	        ResultRetriever.maxHits = maxHits;
	    }

	    public static int getMaxHits() {
	        return ResultRetriever.maxHits;
	    }   
	    
	    public static List<ProductDTO> getProductResults(org.elasticsearch.index.query.QueryBuilder query, List<RequiredHighlight> highlihts) {
	    	
	    	if(query == null) {
	    		throw new IllegalArgumentException(); 
	    	}
	    	
	    	List<ProductDTO> productResult = new ArrayList<>();
	    	
	    	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
	    	searchSourceBuilder.query(query);
	    	searchSourceBuilder.size(maxHits);
	    	
	    	HighlightBuilder highlightBuilder = new HighlightBuilder(); 
	    	highlightBuilder.field("name"); 
	    	highlightBuilder.field("description");
	    	highlightBuilder.field("price");
	    	
	    	highlightBuilder.preTags("<spam style='color:red'>").postTags("</spam>");
	    	highlightBuilder.fragmentSize(200);
	    	searchSourceBuilder.highlighter(highlightBuilder);
	    	
	    	Search search = new Search.Builder(searchSourceBuilder.toString())
	    			.addIndex("products")
	    			.addType("product")
	    			.build();
	    	
	    	SearchResult result; 
	    	try {
	    		result = jestClient.execute(search);
	    		if(result.isSucceeded()) {
	    			log.warn("Uspjesna pretraga");
	    		} else {
	    			log.warn("Neuspjesna pretraga");
	    		}
	    		
	    		List<SearchResult.Hit<ProductDTO, Void>> hits = result.getHits(ProductDTO.class);
	    		ProductDTO resultData = new ProductDTO();
	    		
	    		for(SearchResult.Hit<ProductDTO, Void> sd : hits) {
	    			@SuppressWarnings("unused")
					String highlight = ""; 
	    			
	    			for(String hf : sd.highlight.keySet()) {
	    				for (RequiredHighlight rh : highlihts) {
	    					if (hf.equals(rh.getField())) {
	    						highlight += sd.highlight.get(hf).toString();
	    					}
	    				}
	    			}
	    			
	    			resultData.setName(sd.source.getName());
	    			resultData.setDescription(sd.source.getDescription());
	    			resultData.setPrice(sd.source.getPrice());
	    			
	    			productResult.add(resultData);
	    		}
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	return productResult;  
	    }
}
