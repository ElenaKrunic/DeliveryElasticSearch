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
public class ErrandDTO {
	
	 private Long id;
	 private Date orderedAtDate; 
	 private boolean isDelivered; 	
	 private int grade; 	
	 private String comment; 
	 private boolean anonymousComment;
	 private boolean archivedComment; 

}
