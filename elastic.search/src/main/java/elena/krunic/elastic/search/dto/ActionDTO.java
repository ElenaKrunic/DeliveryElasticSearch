package elena.krunic.elastic.search.dto;

import java.util.Date;

import elena.krunic.elastic.search.model.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActionDTO {

	
	private Long id;
	private int percentage; 
	private Date fromDate; 
	private Date toDate; 
	private String text;
	  
	 public ActionDTO(Action action) {
		this.id = action.getId(); 
		this.percentage = action.getPercentage(); 
		this.fromDate = action.getFromDate(); 
		this.toDate = action.getToDate();
		this.text = action.getText();
	}
	  
}
