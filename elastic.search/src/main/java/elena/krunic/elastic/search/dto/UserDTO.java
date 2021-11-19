package elena.krunic.elastic.search.dto;

import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	private Long id; 
	private String firstname; 
	private String lastname; 
	private String username;
	private String password; 
	private boolean blocked; 
}
