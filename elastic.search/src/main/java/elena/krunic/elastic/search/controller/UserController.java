package elena.krunic.elastic.search.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.dto.BuyerDTO;
import elena.krunic.elastic.search.dto.SellerDTO;
import elena.krunic.elastic.search.dto.StringResponseDTO;
import elena.krunic.elastic.search.dto.UserDTO;
import elena.krunic.elastic.search.service.UserService;

@RestController
@RequestMapping(value="api/users")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService; 
		
	@PostMapping(value="/registerSeller")
	public ResponseEntity<?> registerSeller(@RequestBody SellerDTO sellerDTO) {
		try {
			String register = userService.registerSeller(sellerDTO); 
			return new ResponseEntity<>(new StringResponseDTO(register), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/registerBuyer")
	public ResponseEntity<?> registerBuyer(@RequestBody BuyerDTO buyerDTO) {
		try {
			String register = userService.registerBuyer(buyerDTO); 
			return new ResponseEntity<>(new StringResponseDTO(register), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/editSeller")
	public ResponseEntity<?> editSeller(@RequestBody SellerDTO sellerDTO, Principal principal) {
		try {
			String message = userService.editSeller(sellerDTO, "krunicele@gmail.com");
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/editBuyer")
	public ResponseEntity<?> editBuyer(@RequestBody BuyerDTO buyerDTO, Principal principal) {
		try {
			String message = userService.editBuyer(buyerDTO, "krunicele@gmail.com"); 
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/blockUser/{id}")
	public ResponseEntity<?> blockUser(@PathVariable("id") Long id) {
		try {
			String message = userService.blockUser(id); 
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK); 
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
