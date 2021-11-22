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
import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.dto.StringResponseDTO;
import elena.krunic.elastic.search.service.ErrandService;
import elena.krunic.elastic.search.service.ProductService;

@RestController
@RequestMapping(value="api/errands")
@CrossOrigin
public class ErrandController {

	@Autowired
	private ErrandService errandService; 
	
	@Autowired
	private ProductService productService;
	
	@PutMapping("/checkDeliveryStatus/{id}")
	public ResponseEntity<?> checkNonDeliveredProducts(@PathVariable("id") Long id) {
		try {
			String message = errandService.checkDeliveryStatus(id);
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/leaveComment/{id}")
	public ResponseEntity<?> leaveComment(@RequestBody ProductDTO productDTO, @PathVariable("id") Long id) {
		try {
			String message = errandService.leaveComment(productDTO, id); 
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST); 
		}
	}
}
