package elena.krunic.elastic.search.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import elena.krunic.elastic.search.dto.BuyerDTO;
import elena.krunic.elastic.search.dto.SellerDTO;
import elena.krunic.elastic.search.dto.UserDTO;
import elena.krunic.elastic.search.model.Buyer;
import elena.krunic.elastic.search.model.Seller;
import elena.krunic.elastic.search.model.User;
import elena.krunic.elastic.search.repository.BuyerRepository;
import elena.krunic.elastic.search.repository.SellerRepository;
import elena.krunic.elastic.search.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository; 
	
	@Autowired 
	private SellerRepository sellerRepository;
	
	@Autowired
	private BuyerRepository buyerRepository; 

	public String registerSeller(SellerDTO sellerDTO) throws Exception {
		User user = userRepository.findByUsername(sellerDTO.getUsername()); 
		
		if(user != null) {
			throw new Exception("User already exists! "); 
		}
		
		Seller seller = new Seller(); 
		seller.setAddress(sellerDTO.getAddress());
		seller.setBlocked(false);
		seller.setEmail(sellerDTO.getEmail());
		seller.setFirstname(sellerDTO.getFirstname());
		seller.setLastname(sellerDTO.getLastname());
		Date date = new Date(); 
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		date = calendar.getTime(); 
		seller.setOperatesSince(date);
		
		//hesuj !!! 
		seller.setPassword(sellerDTO.getPassword());
		
		seller.setStoreName(sellerDTO.getStoreName());
		seller.setUsername(sellerDTO.getUsername());
		
		seller = userRepository.save(seller);
		
		
		return "Seller successfully registered!"; 
	}

	public String registerBuyer(BuyerDTO buyerDTO) throws Exception {
		User user = userRepository.findByUsername(buyerDTO.getUsername()); 
		
		if(user != null) {
			throw new Exception("User already exists!"); 
		}
		
		Buyer buyer = new Buyer(); 
		buyer.setAddress(buyerDTO.getAddress());
		buyer.setBlocked(false);
		buyer.setFirstname(buyerDTO.getFirstname());
		buyer.setLastname(buyerDTO.getLastname());
		
		//hesuj lozinku! 
		
		buyer.setPassword(buyerDTO.getPassword());
		buyer.setUsername(buyerDTO.getUsername());
		
		buyer = userRepository.save(buyer);  
		
		return "Buyer successfully registered!";
	}

	public String editSeller(SellerDTO sellerDTO, String name) throws Exception {
		
		Seller seller = sellerRepository.findByUsername(name); 
		
		if(seller == null ) {
			throw new Exception("User does not exist!");
		}
		
		seller.setAddress(sellerDTO.getAddress());
		seller.setBlocked(sellerDTO.isBlocked());
		seller.setEmail(sellerDTO.getEmail());
		seller.setFirstname(sellerDTO.getFirstname());
		seller.setLastname(sellerDTO.getLastname());
		seller.setOperatesSince(sellerDTO.getOperatesSince());
		
		//hesuj lozinku! 

		seller.setPassword(sellerDTO.getPassword());
		seller.setStoreName(sellerDTO.getStoreName());
		seller.setUsername(sellerDTO.getUsername());
		
		seller = sellerRepository.save(seller); 
		
		return "You successfully edited seller!";
	}

	public String editBuyer(BuyerDTO buyerDTO, String name) throws Exception {
		
		Buyer buyer = buyerRepository.findByUsername(name);
		
		if(buyer == null) {
			throw new Exception("Buyer does not exist!"); 
		}
		
		buyer.setAddress(buyerDTO.getAddress());
		buyer.setBlocked(buyerDTO.isBlocked());
		buyer.setFirstname(buyerDTO.getFirstname());
		buyer.setLastname(buyerDTO.getLastname());
		
		// hesuj lozinku! 
		
		buyer.setPassword(buyerDTO.getPassword());
		buyer.setUsername(buyerDTO.getUsername());
		
		buyer = buyerRepository.save(buyer); 
		
		return "Buyer successfully edited!";
	}

	public String blockUser(Long id) throws Exception {
		Optional<User> userOptional = userRepository.findById(id); 
		
		if(!userOptional.isPresent()) {
			throw new Exception("No user with id provided!"); 
		}
		
		User user = userOptional.get(); 
		user.setBlocked(true);
		user = userRepository.save(user);
		return "User is banned from system!";
	}

	@DeleteMapping(value="/deleteUser/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
		User user = userRepository.getById(id); 
		
		if(user != null) {
			userRepository.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK); 
		}
		
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username); 
		System.out.println("User"  + user);
		if(user == null) {
			throw new UsernameNotFoundException(String.format("User not found with username '%s'.", username));
		}
		
			return new User(user.getUsername(), user.getPassword());
		}
}
