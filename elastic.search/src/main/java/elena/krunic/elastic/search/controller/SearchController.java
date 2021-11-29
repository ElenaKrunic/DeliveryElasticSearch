package elena.krunic.elastic.search.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.dto.ProductDTO;

@RestController
@RequestMapping("api/search")
public class SearchController {

	//napraviti sve modele pretrage 
	//@PostMapping(value = "/term/products", consumes = "application/json")
	//public ResponseEntity<List<ProductDTO>> searchProductTermQuery(@RequestBody SimpleQuery )
}
