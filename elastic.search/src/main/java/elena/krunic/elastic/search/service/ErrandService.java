package elena.krunic.elastic.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.krunic.elastic.search.dto.ErrandDTO;
import elena.krunic.elastic.search.dto.ProductDTO;
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

	public String leaveComment(ProductDTO productDTO, Long id) throws Exception {
		Optional<Errand> errandOptional = errandRepository.findById(id); 
		
		if(!errandOptional.isPresent()) {
			throw new Exception("Errand does not exist!"); 
		}
		
		Errand errand = errandOptional.get();
		errand.setComment(productDTO.getComment());
		errand.setGrade(productDTO.getGrade());
		errand.setAnonymousComment(productDTO.isAnonymousComment());
		errand.setArchivedComment(productDTO.isArchivedComment());
		
		errand = errandRepository.save(errand);
		return "You leaved comment successfully";
	}

	public List<ErrandDTO> getByBuyer(Long id) {
		
		if(errandRepository.getById(id) == null)
			throw new EntityNotFoundException(); 
		
		List<Errand> errands = errandRepository.findAllByBuyerId(id); 
		List<ErrandDTO> dtos = new ArrayList<>();
		
		for(Errand errand : errands) {
			dtos.add(new ErrandDTO(errand));
		}
		
		return dtos; 
		
	}

}
