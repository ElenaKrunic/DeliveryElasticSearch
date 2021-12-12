package elena.krunic.elastic.search.lucene.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductLucene {
	
	private String id;
	private String description; 
	private double price; 

}
