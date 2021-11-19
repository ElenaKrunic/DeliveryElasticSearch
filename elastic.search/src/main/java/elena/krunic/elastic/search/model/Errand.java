package elena.krunic.elastic.search.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//porudzbina
@Entity
@Table(name="errand")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Errand {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
	
	@Column(name="orderedAtDate", nullable=false)
	private Date orderedAtDate; 
	
	@Column(name="isDelivered", nullable=false)
	private boolean isDelivered; 
	
	@Column(name="grade", nullable=false)
	private int grade; 
	
	@Column(name="comment", nullable=false)
	private String comment; 
	
	@Column(name="anonymousComment", nullable=false)
	private boolean anonymousComment;
	
	@Column(name="archivedComment", nullable=false)
	private boolean archivedComment; 
	
	@ManyToOne
	private Buyer buyer;
	
	@OneToMany(mappedBy="errand")
	private List<Item> items; 
}
