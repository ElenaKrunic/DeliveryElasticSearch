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
import elena.krunic.elastic.search.lucene.dto.SearchRequestDTO;
import elena.krunic.elastic.search.lucene.service.JestIndexer;
import elena.krunic.elastic.search.lucene.service.ProductLuceneService;

import elena.krunic.elastic.search.service.SellerService;

@RestController
@RequestMapping(value = "api/product")
public class ProductLuceneController {

	@Autowired
	private ProductLuceneService productLuceneService; 	
	
	@Autowired
	private SellerService sellerService; 
	
	public ProductLuceneController(ProductLuceneService productLuceneService) {
		this.productLuceneService = productLuceneService; 
	}
	
	@PostMapping("/indexProduct")
	public void indexProduct(@RequestBody final ProductLuceneDTO product) {
		productLuceneService.index(product);
	}

	@PostMapping("/indexProducts/{id}")
	public void indexProducts(@PathVariable("id") Long id) {
		List<ProductLuceneDTO> productsLucene = sellerService.findProductsLuceneForSeller(id);
		productLuceneService.createProductIndexBulk(productsLucene);
	}
	
	
	@GetMapping("/getAllIndexedProducts/{id}")
	public List<ProductLuceneDTO> getIndexedFromDatabase(@PathVariable("id") Long id) {
		List<ProductLuceneDTO> productsLucene = sellerService.findProductsLuceneForSeller(id);
		return productsLucene;
	}
	
	@PostMapping("/searchProductMatchQuery")
	public List<ProductLuceneDTO> searchProducthMatchQuery(@RequestBody final SearchRequestDTO dto) {
		return productLuceneService.searchMatchQuery(dto);
	}
	
	@GetMapping("/searchProductGreatherThenOrEquals/{price}")
	public List<ProductLuceneDTO> getAllProductsWithGreaterThenPrice(@PathVariable("price") double price) {
		return productLuceneService.getAllGTEProducts(price);
	}
	
	@PostMapping("/searchBoolPriceGTE/{price}")
	public List<ProductLuceneDTO> productsPriceBooleanQuery (@RequestBody SearchRequestDTO dto,@PathVariable("price") double price) {
		return  productLuceneService.productsPriceBooleanGTE(dto, price); 
	}
}
