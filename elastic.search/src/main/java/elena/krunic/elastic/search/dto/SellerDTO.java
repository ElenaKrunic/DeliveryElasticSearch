package elena.krunic.elastic.search.dto;

import java.sql.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellerDTO {

	private Long id; 
	private String firstname; 
	private String lastname; 
	private String username;
	private String password; 
	private boolean blocked;
	private Date operatesSince; 
	private String email; 
	private String address; 
	private String storeName; 

}
