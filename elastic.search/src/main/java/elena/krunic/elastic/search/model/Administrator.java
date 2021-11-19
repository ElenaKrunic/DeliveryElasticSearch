package elena.krunic.elastic.search.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="administrator")
@Getter
@Setter
public class Administrator extends User {
	 

}
