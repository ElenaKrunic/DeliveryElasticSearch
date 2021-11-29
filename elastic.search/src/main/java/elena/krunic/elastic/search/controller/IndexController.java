package elena.krunic.elastic.search.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.lucene.indexing.Indexer;
import elena.krunic.elastic.search.service.ErrandService;
import elena.krunic.elastic.search.service.ProductService;

@RestController
@RequestMapping("/api")
public class IndexController {
	
	@Autowired
	private Indexer indexer; 
	
	@Autowired
	private ProductService productService; 
	
	@Autowired 
	private ErrandService errandService; 
	
	@GetMapping("/reindex-products/seller/{id}")
	public ResponseEntity<String> indexProducts(@PathVariable("id") Long id) throws Exception {
		long start = new Date().getTime(); 
		long end = new Date().getTime(); 
		int numIndexed = indexer.getInstance().indexProducts(productService.getBySeller(id));
		
		String text = "Indeksiranje " + numIndexed + " artikala je trajalo" + (end - start) + " milisekundi";
		return new ResponseEntity<String>(text, HttpStatus.OK);
	}
	
	@GetMapping("/reindex-errands/buyer/{id}")
	public ResponseEntity<String> indexErrands(@PathVariable("id") Long id) throws Exception {
		long start = new Date().getTime(); 
		long end = new Date().getTime(); 
		int numIndexed = indexer.getInstance().indexErrands(errandService.getByBuyer(id)); 
		
		String text = "Indeksiranje " + numIndexed + " porudzbina je trajalo" + (end - start) + " milisekundi";
		return new ResponseEntity<String>(text, HttpStatus.OK);
	}
	

}
