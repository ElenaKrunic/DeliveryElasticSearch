package elena.krunic.elastic.search.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.dto.BuyerDTO;
import elena.krunic.elastic.search.dto.JwtAuthenticationRequestDTO;
import elena.krunic.elastic.search.dto.SellerDTO;
import elena.krunic.elastic.search.dto.StringResponseDTO;
import elena.krunic.elastic.search.dto.UserDTO;
import elena.krunic.elastic.search.dto.UserTokenStateDTO;
import elena.krunic.elastic.search.model.User;
import elena.krunic.elastic.search.repository.UserRepository;
import elena.krunic.elastic.search.security.TokenUtils;
import elena.krunic.elastic.search.service.UserService;

@RestController
@RequestMapping(value="api/users")
@CrossOrigin
public class UserController {
	
	@Autowired 
	private UserDetailsService userDetailsService; 

	@Autowired
	private UserService userService; 
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private TokenUtils tokenUtils; 
	
	@PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> login(@RequestBody JwtAuthenticationRequestDTO req, HttpServletResponse response) {
    	
    	try {		
    		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());

    		SecurityContextHolder.getContext().setAuthentication(authentication);

    		User user = userRepository.findByUsername(req.getUsername());
    		String jwt = tokenUtils.generateToken(user.getUsername());
    		int expiresIn = tokenUtils.getExpiredIn();

    		return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return ResponseEntity.ok(new UserTokenStateDTO());
    }
	
	 @PutMapping("/changePassword")
	    public ResponseEntity<StringResponseDTO> changePassword(@RequestBody UserDTO userDTO, Principal principal) {
	        try {
	            userService.changePassword(userDTO, principal.getName());
	            return new ResponseEntity<>(new StringResponseDTO("Successful!"), HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
	        }
	    }

		
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
	
	@PutMapping(value="/changeProfileData")
	public ResponseEntity<?> changeMyData(@RequestBody UserDTO userDTO, Principal principal) {
		try {
			String message = userService.changeProfileData(userDTO, principal.getName());
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK); 
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
