package elena.krunic.elastic.search.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.krunic.elastic.search.model.Errand;
import elena.krunic.elastic.search.model.Product;
import elena.krunic.elastic.search.repository.ErrandRepository;

@Service
public class ErrandService {
	
	@Autowired
	private ErrandRepository errandRepository; 
	
	public String checkDeliveryStatus(Long id) throws Exception {
		Optional<Errand> errandOptional = errandRepository.findById(id); 
		
		if(!errandOptional.isPresent()) {
			throw new Exception("Errand does not exist!"); 
		}
		
		Errand errand = errandOptional.get(); 
		errand.setDelivered(true);
		errand = errandRepository.save(errand); 
		
		return "Delivery status changed!";
	}

}
