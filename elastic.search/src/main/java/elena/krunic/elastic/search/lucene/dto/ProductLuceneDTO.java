package elena.krunic.elastic.search.lucene.dto;

import elena.krunic.elastic.search.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductLuceneDTO {

	private Long id;
	private String name; 
	private String description;
	private int price;
	
	public ProductLuceneDTO(Product product) {
		this.id = product.getId(); 
		this.name = product.getName(); 
		this.description = product.getDescription(); 
		this.price = product.getPrice();
	}
}
