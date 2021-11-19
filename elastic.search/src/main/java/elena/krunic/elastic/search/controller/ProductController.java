package elena.krunic.elastic.search.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.model.Product;
import elena.krunic.elastic.search.repository.ProductRepository;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository; 
	
	@GetMapping
	public ResponseEntity<List<ProductDTO>> getProducts(){
		List<Product> products = productRepository.findAll(); 
		List<ProductDTO> productsDTO = new ArrayList<>();
		
		for(Product product: products) {
			productsDTO.add(new ProductDTO(product));
		}
		
		return new ResponseEntity<List<ProductDTO>>(productsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id) {
		Product product = productRepository.getById(id); 
		
		if(product == null) {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProductDTO>(new ProductDTO(product), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json", value="/saveProduct")
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
		Product product = new Product(); 
		
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPath(productDTO.getPath());
		product.setPrice(productDTO.getPrice());
		
		product = productRepository.save(product); 
		
		return new ResponseEntity<>(new ProductDTO(product), HttpStatus.CREATED); 
	}
	
	@PutMapping(consumes="applicaiton/json", value="/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable("id") Long id) {
		Product product = productRepository.getById(id); 
		
		if(product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPath(productDTO.getPath());
		product.setPrice(productDTO.getPrice());
		
		product = productRepository.save(product); 
		
		return new ResponseEntity<>(new ProductDTO(product), HttpStatus.OK); 
	}
	
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
		Product product = productRepository.getById(id); 
		
		if(product != null) {
			productRepository.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK); 
		}
		
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
}
