package elena.krunic.elastic.search.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.krunic.elastic.search.dto.BuyerDTO;
import elena.krunic.elastic.search.dto.SellerDTO;
import elena.krunic.elastic.search.dto.UserDTO;
import elena.krunic.elastic.search.model.Buyer;
import elena.krunic.elastic.search.model.Seller;
import elena.krunic.elastic.search.model.User;
import elena.krunic.elastic.search.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository; 

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

}
