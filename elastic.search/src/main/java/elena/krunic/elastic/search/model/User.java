package elena.krunic.elastic.search.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable=false, unique=true)
	private Long id; 
	
	@Column(name="firstname", nullable = false)
	private String firstname; 
	
	@Column(name="lastname", nullable = false)
	private String lastname; 
	
	@Column(name="username", nullable = false)
	private String username; 
	
	@Column(name="password")
	private String password; 
	
	@Column(name="blocked", nullable = false)
	private boolean blocked; 
	
	public User(String username, String password) {
		this.username = username; 
		this.password = password; 
	}
	
	
}
