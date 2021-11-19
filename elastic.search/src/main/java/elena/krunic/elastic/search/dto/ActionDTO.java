package elena.krunic.elastic.search.dto;

import java.util.Date;

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
	  
}
