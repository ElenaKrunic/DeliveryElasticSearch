package elena.krunic.elastic.search.lucene.dto;

import elena.krunic.elastic.search.model.Errand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrandLuceneDTO {
	
	private String _class; 
	private Long id; 
	private String comment;
	private int price;
	
	public ErrandLuceneDTO(Errand errand) {
		this.id = errand.getId();
		this.comment = errand.getComment();
		this.price = errand.getGrade();
	}
}
