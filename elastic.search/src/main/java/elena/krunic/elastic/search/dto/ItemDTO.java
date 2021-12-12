package elena.krunic.elastic.search.dto;

import java.util.Date;

import javax.persistence.Column;

import elena.krunic.elastic.search.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {

	private Long id;
	private int quantity; 
	
	public ItemDTO(Item item) {
		this.id = item.getId();
		this.quantity = item.getQuantity();
	}
	     
}
