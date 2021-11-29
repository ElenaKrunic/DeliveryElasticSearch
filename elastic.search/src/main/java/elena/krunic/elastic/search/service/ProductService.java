package elena.krunic.elastic.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.model.Buyer;
import elena.krunic.elastic.search.model.Errand;
import elena.krunic.elastic.search.model.Item;
import elena.krunic.elastic.search.model.Product;
import elena.krunic.elastic.search.model.Seller;
import elena.krunic.elastic.search.model.User;
import elena.krunic.elastic.search.repository.BuyerRepository;
import elena.krunic.elastic.search.repository.ErrandRepository;
import elena.krunic.elastic.search.repository.ItemRepository;
import elena.krunic.elastic.search.repository.ProductRepository;
import elena.krunic.elastic.search.repository.UserRepository;

@Service
public class ProductService {

	@Autowired 
	private UserRepository userRepository; 
	
	@Autowired 
	private ProductRepository productRepository; 
	
	@Autowired 
	private ErrandRepository errandRepository;
	
	@Autowired 
	private ItemRepository itemRepository; 
	
	@Autowired 
	private BuyerRepository buyerRepository; 
	
	public String orderProduct(ProductDTO productDTO, String address) throws Exception {
		
		//treba da dodam kolicinu i adresu kupca 
		//treba da smanjim njegovu dostupnost kod erranda 
		//treba mi buyer preko adrese 
		
		Buyer buyer = buyerRepository.findByAddress(address);
		
		Seller seller = (Seller) userRepository.findById(productDTO.getSellerID()).orElse(null);
		
		Product product = new Product(); 
		Errand errand = new Errand(); 
		Item item = new Item(); 
		
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPath(productDTO.getPath());
		product.setPrice(productDTO.getPrice());
		product.setSeller(seller);
		
		errand.setDelivered(true);
		errand.setAnonymousComment(productDTO.isAnonymousComment());
		errand.setArchivedComment(productDTO.isArchivedComment());
		errand.setComment(productDTO.getComment());
		errand.setGrade(productDTO.getGrade());
		errand.setOrderedAtDate(productDTO.getOrderedAtDate());
		errand.setBuyer(buyer);
		
		item.setQuantity(productDTO.getQuantity());
		item.setErrand(errand);
		item.setProduct(product);
		
		product = productRepository.save(product); 
		errand = errandRepository.save(errand); 
		item = itemRepository.save(item);
			
		return "Product successfully ordered!";
	}

	public List<ProductDTO> getBySeller(Long id) throws Exception {

		if(productRepository.findById(id) == null)
			throw new EntityNotFoundException();
		
		List<Product> products = productRepository.findAllBySellerId(id);
		List<ProductDTO> dtos = new ArrayList<>();
		
		for(Product product : products) {
			dtos.add(new ProductDTO(product));
		}
		
		return dtos;
	}

	

}
