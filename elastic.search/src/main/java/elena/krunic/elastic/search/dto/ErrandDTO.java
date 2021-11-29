package elena.krunic.elastic.search.dto;

import java.util.Date;

import elena.krunic.elastic.search.model.Errand;
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
	 
	 public ErrandDTO(Errand errand) {
		 this.id = errand.getId(); 
		 this.orderedAtDate = errand.getOrderedAtDate(); 
		 this.isDelivered = errand.isDelivered(); 
		 this.grade = errand.getGrade(); 
		 this.comment = errand.getComment(); 
		 this.anonymousComment = errand.isAnonymousComment(); 
		 this.archivedComment = errand.isArchivedComment();
	 }

}
