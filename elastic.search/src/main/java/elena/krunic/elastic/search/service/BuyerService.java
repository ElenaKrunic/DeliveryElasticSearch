package elena.krunic.elastic.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.krunic.elastic.search.lucene.dto.ErrandLuceneDTO;
import elena.krunic.elastic.search.model.Errand;
import elena.krunic.elastic.search.repository.BuyerRepository;
import elena.krunic.elastic.search.repository.ErrandRepository;

@Service
public class BuyerService {

	@Autowired 
	private ErrandRepository errandRepository;
	
	@Autowired
	private BuyerRepository buyerRepository;
	
	public List<ErrandLuceneDTO> findErrandsLuceneForBuyer(Long id) {
		List<Errand> errands = errandRepository.findErrandsByBuyerId(id);
		List<ErrandLuceneDTO> luceneDTOs = new ArrayList<>();
		
		for(Errand errand : errands) {
			luceneDTOs.add(new ErrandLuceneDTO(errand));
		}
		
		return luceneDTOs;
	}
}
