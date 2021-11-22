package elena.krunic.elastic.search.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import elena.krunic.elastic.search.model.Item;
import elena.krunic.elastic.search.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
	
	private Long id;
	private String name; 
	private String description;
	private double price;
	private String path; 
	private Long sellerID; 
	private Long buyerID;
	private int quantity; 
	private Date orderedAtDate; 
	private boolean isDelivered; 	
	private int grade; 	
	private String comment; 
	private boolean anonymousComment;
	private boolean archivedComment; 
	
	 public ProductDTO(Product product) {
			this.id = product.getId(); 
			this.name = product.getName(); 
			this.description = product.getDescription(); 
			this.price = product.getPrice(); 
			this.path = product.getPath();
		}

}
