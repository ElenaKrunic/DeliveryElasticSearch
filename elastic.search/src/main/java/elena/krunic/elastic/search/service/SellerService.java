package elena.krunic.elastic.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.model.Product;
import elena.krunic.elastic.search.repository.ProductRepository;
import elena.krunic.elastic.search.repository.SellerRepository;

@Service
public class SellerService {

	@Autowired
	private SellerRepository sellerRepository; 
	
	@Autowired 
	private ProductRepository productRepository; 
	
	public List<ProductDTO> findProductsForSeller(Long id) {
		List<Product> products = productRepository.findProductsBySellerId(id); 
		List<ProductDTO> productsDTO = new ArrayList<>();
		
		for(Product product: products) {
			productsDTO.add(new ProductDTO(product));
		}
		return productsDTO;
	}

}
