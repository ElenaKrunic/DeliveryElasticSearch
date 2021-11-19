package elena.krunic.elastic.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuyerDTO {

	private Long id; 
	private String firstname; 
	private String lastname; 
	private String username;
	private String password; 
	private boolean blocked; 
	private String address; 
}
