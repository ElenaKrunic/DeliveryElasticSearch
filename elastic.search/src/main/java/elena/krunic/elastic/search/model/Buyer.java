package elena.krunic.elastic.search.model;

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
@Table(name="buyer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Buyer extends User {

	@Column(name = "address", nullable = false)
	private String address; 
	
	@OneToMany(mappedBy="buyer")
	private List<Errand> errands; 
}
