package elena.krunic.elastic.search.lucene.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.lucene.dto.ErrandLuceneDTO;
import elena.krunic.elastic.search.lucene.dto.SearchRequestDTO;
import elena.krunic.elastic.search.lucene.service.ErrandLuceneService;
import elena.krunic.elastic.search.service.BuyerService;

@RestController
@RequestMapping(value="api/errand")
public class ErrandLuceneController {
	
	@Autowired 
	private ErrandLuceneService errandLuceneService; 
	
	@Autowired
	private BuyerService buyerService;

	public ErrandLuceneController(ErrandLuceneService errandLuceneService) {
		this.errandLuceneService = errandLuceneService; 
	}
	
	@PostMapping("/indexErrands/{id}")
	public void indexErrands(@PathVariable("id") Long id) {
		List<ErrandLuceneDTO> errandsLucene = buyerService.findErrandsLuceneForBuyer(id);
		errandLuceneService.createErrandIndexBulk(errandsLucene);
	}
	
	@PostMapping("/searchErrandMatchQuery")
	public List<ErrandLuceneDTO> searchErrandMatchQuery(@RequestBody final SearchRequestDTO dto) {
		return errandLuceneService.searchErrandMatchQuery(dto);
	}
}
