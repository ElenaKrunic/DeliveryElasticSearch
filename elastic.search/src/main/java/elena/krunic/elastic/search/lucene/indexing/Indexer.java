package elena.krunic.elastic.search.lucene.indexing;

import org.springframework.stereotype.Service;

import elena.krunic.elastic.search.dto.ErrandDTO;
import elena.krunic.elastic.search.dto.ProductDTO;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class Indexer {

	private JestClient jestClient; 
	
	private static final Logger log = LoggerFactory.getLogger(Indexer.class);
	
	private static Indexer indexer = new Indexer(); 
	
	public Indexer getInstance() { return indexer; }
	
	private Indexer() {
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(
				new HttpClientConfig.Builder("http://localhost:9200")
					.multiThreaded(true)
					.defaultMaxTotalConnectionPerRoute(2)
					.maxTotalConnection(10)
					.build());
		jestClient = factory.getObject();
	}
	
	public int indexProducts(List<ProductDTO> products) throws Exception {
		JestResult result = null; 
		int retVal = 0; 
		
		for(ProductDTO productDTO : products) {
			Index index = new Index.Builder(productDTO).index("products").type("product").id(productDTO.getId().toString()).build();
			result = jestClient.execute(index);
		}
		
		if(result.isSucceeded()) {
			log.warn("Zavrseno indeksiranje artikala"); 
			return retVal += products.size();
		} else {
			log.warn("Neuspjesno indeksiranje artikala");
			return -1; 
		}
	}
	
	public boolean addProduct(ProductDTO productDTO) {
		Index index = new Index.Builder(productDTO).index("products").type("product").id(productDTO.getId().toString()).build();
		System.out.println("Index je " + index);
		JestResult result;
		
		try {
			result = jestClient.execute(index);
			System.out.println("Dodajem indeks " + result);
			return result.isSucceeded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false; 
		
	}
	
	public boolean existProduct(int id) throws Exception {
		DocumentResult result = jestClient.execute(new Get.Builder("products", id + "").build());
		log.warn("Proizvod postoji/ne postoji" + result);
		return result.isSucceeded();	
	}
	
	public boolean deleteProduct(int id) {
		JestResult result; 
		try {
			result = jestClient.execute(new Delete.Builder(id + "")
					.index("products")
					.type("product")
					.build());
			log.warn("Brisem artikl sa id-jem" + id);
			if(result.isSucceeded()) 
				return true; 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false; 
	}
	
	public int indexErrands(List<ErrandDTO> errands) throws Exception {
		JestResult result = null; 
		int retVal = 0; 
		
		for(ErrandDTO errandDTO : errands) {
			Index index = new Index.Builder(errandDTO).index("errands").type("errand").id(errandDTO.getId().toString()).build();
			result = jestClient.execute(index);
		}
		
		if(result.isSucceeded()) {
			log.warn("Zavrseno indeksiranje porudzbina"); 
			return retVal += errands.size();
		} else {
			log.warn("Neuspjesno indeksiranje porudzbina");
			return -1; 
		}
	}
	
	public boolean addErrand(ErrandDTO errandDTO) {
		Index index = new Index.Builder(errandDTO).index("errands").type("errand").id(errandDTO.getId().toString()).build();
		System.out.println("Index je " + index);
		JestResult result;
		
		try {
			result = jestClient.execute(index);
			System.out.println("Dodajem indeks " + result);
			return result.isSucceeded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false; 
		
	}
	
	public boolean existErrand(int id) throws Exception {
		DocumentResult result = jestClient.execute(new Get.Builder("errands", id + "").build());
		log.warn("Poruzbina postoji/ne postoji" + result);
		return result.isSucceeded();	
	}
	
	public boolean deleteErrand(int id) {
		JestResult result; 
		try {
			result = jestClient.execute(new Delete.Builder(id + "")
					.index("errands")
					.type("errand")
					.build());
			log.warn("Brisem porudzbinu sa id-jem" + id);
			if(result.isSucceeded()) 
				return true; 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false; 
	}
	
	
	
}
