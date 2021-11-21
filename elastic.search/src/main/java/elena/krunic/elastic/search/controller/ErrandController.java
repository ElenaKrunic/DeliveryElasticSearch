package elena.krunic.elastic.search.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
	
}
