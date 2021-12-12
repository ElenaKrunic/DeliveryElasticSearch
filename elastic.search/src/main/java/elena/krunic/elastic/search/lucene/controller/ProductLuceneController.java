package elena.krunic.elastic.search.lucene.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.lucene.dto.ProductLuceneDTO;
import elena.krunic.elastic.search.lucene.service.JestIndexer;
import elena.krunic.elastic.search.lucene.service.ProductLuceneService;

import elena.krunic.elastic.search.service.SellerService;

@RestController
@RequestMapping(value = "api/product")
public class ProductLuceneController {

	@Autowired
	private ProductLuceneService productLuceneService; 	
	
	@Autowired
	private JestIndexer jestIndexer;
	
	@Autowired
	private SellerService sellerService; 
	
	public ProductLuceneController(ProductLuceneService productLuceneService) {
		this.productLuceneService = productLuceneService; 
	}
	
	@GetMapping("/oneIndexed/{id}")
	public Boolean getId(@PathVariable final Long id) throws Exception {
		return jestIndexer.existProduct(id);
	}
	
	@PostMapping("/indexProduct")
	public void indexProduct(@RequestBody final ProductLuceneDTO product) {
		productLuceneService.index(product);
	}
	
	@PostMapping("indexAll/{id}")
	public void indexProductsFromDatabase(@PathVariable("id") Long id) {
		List<ProductLuceneDTO> productsLucene = sellerService.findProductsLuceneForSeller(id);
		jestIndexer.indexProductsFromDatabase(productsLucene);
	}
}
