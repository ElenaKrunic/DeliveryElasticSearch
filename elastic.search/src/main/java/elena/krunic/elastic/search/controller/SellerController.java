package elena.krunic.elastic.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.model.Product;
import elena.krunic.elastic.search.service.SellerService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


@RestController
@RequestMapping(value="/api/sellers")
@CrossOrigin(value="*")
public class SellerController {
	
	@Autowired
	private SellerService sellerService; 
	
	@GetMapping(value="/products/{id}")
	private List<ProductDTO> getProductsForSeller(@PathVariable("id") Long id) {
		return sellerService.findProductsForSeller(id); 
	}
}
