package elena.krunic.elastic.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
}
