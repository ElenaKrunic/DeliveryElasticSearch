package elena.krunic.elastic.search.lucene.service;

import java.util.List;

import org.springframework.stereotype.Service;

import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.lucene.dto.ProductLuceneDTO;
import elena.krunic.elastic.search.model.Product;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

@Service
public class JestIndexer {
	
	private JestClient jestClient; 
	
	private static JestIndexer indexer = new JestIndexer(); 
	
	public JestIndexer getInstance() { return indexer; }
	
	private JestIndexer() {
		JestClientFactory factory = new JestClientFactory(); 
		factory.setHttpClientConfig(
				new HttpClientConfig.Builder("http://localhost:9200")
				.multiThreaded(true)
				.defaultMaxTotalConnectionPerRoute(2)
				.maxTotalConnection(10)
				.build());
		jestClient = factory.getObject();
	}
	
	public int indexProductsFromDatabase(List<ProductLuceneDTO> products) {
		try {
			JestResult result = null; 
			
			for(ProductLuceneDTO product : products) {
				Index index = new Index.Builder(product).index("products").type("product").id(product.getId().toString()).build();
				result = jestClient.execute(index);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public boolean existProduct(Long id) throws Exception {
		DocumentResult result = jestClient.execute(new Get.Builder("products", id + "").build());
		System.out.println("result " + result.getJsonString());
		return result.isSucceeded();	
	}
}
