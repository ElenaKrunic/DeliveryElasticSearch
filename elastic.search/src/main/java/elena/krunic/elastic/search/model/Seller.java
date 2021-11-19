package elena.krunic.elastic.search.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="seller")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends User {

	@Column(name = "operatesSince", nullable = false)
	private Date operatesSince; 
	
	@Column(name = "email", nullable = false)
	private String email; 
	
	@Column(name = "address", nullable = false)
	private String address; 
	
	@Column(name = "name", nullable = false)
	private String name; 
	
	@OneToMany(mappedBy="seller")
	private List<Action> actions;
	
	@OneToMany(mappedBy="seller")
	private List<Product> products; 
}
