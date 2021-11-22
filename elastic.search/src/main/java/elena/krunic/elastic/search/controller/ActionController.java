package elena.krunic.elastic.search.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.dto.ActionDTO;
import elena.krunic.elastic.search.model.Action;
import elena.krunic.elastic.search.model.Seller;
import elena.krunic.elastic.search.repository.ActionRepository;
import elena.krunic.elastic.search.repository.UserRepository;
import elena.krunic.elastic.search.service.ActionService;

@RestController
@RequestMapping(value="/api/actions")
public class ActionController {
	
	@Autowired
	private ActionRepository actionRepository; 
	
	@Autowired
	private ActionService actionService; 
	
	@Autowired 
	private UserRepository userRepository; 
	
	@GetMapping
	public ResponseEntity<List<ActionDTO>> getActions() {
		List<Action> actions = actionRepository.findAll(); 
		List<ActionDTO> actionsDTO = new ArrayList<ActionDTO>(); 
		
		for(Action action: actions) {
			actionsDTO.add(new ActionDTO(action));
		}
		
		return new ResponseEntity<List<ActionDTO>>(actionsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ActionDTO> getAction(@PathVariable("id") Long id) {
		Action action = actionRepository.getById(id); 
		
		if(action == null) {
			return new ResponseEntity<ActionDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ActionDTO>(new ActionDTO(action), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json", value="/saveAction")
	public ResponseEntity<ActionDTO> saveAction(@RequestBody ActionDTO actionDTO) {
		Action action = new Action(); 
		Seller user = (Seller) userRepository.getById(action.getSeller().getId());

		action.setFromDate(actionDTO.getFromDate());
		action.setPercentage(actionDTO.getPercentage());
		action.setText(actionDTO.getText());
		action.setToDate(actionDTO.getToDate());
		action.setSeller(user);
		
		action = actionRepository.save(action);
		return new ResponseEntity<>(new ActionDTO(action), HttpStatus.CREATED);
	}

	@PutMapping(consumes="application/json", value="/{id}")
	public ResponseEntity<ActionDTO> updateAction(@RequestBody ActionDTO actionDTO, @PathVariable("id") Long id) {
		Action action = actionRepository.getById(id); 
		
		if(action == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		action.setFromDate(actionDTO.getFromDate());
		action.setPercentage(actionDTO.getPercentage());
		action.setText(actionDTO.getText());
		action.setToDate(actionDTO.getToDate());
		
		action = actionRepository.save(action);
		return new ResponseEntity<>(new ActionDTO(action), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteAction(@PathVariable("id") Long id) {
		Action action = actionRepository.getById(id); 
		
		if(action != null) {
			actionRepository.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
}
