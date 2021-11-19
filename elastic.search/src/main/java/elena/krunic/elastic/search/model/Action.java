package elena.krunic.elastic.search.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="action")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Action {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "percentage", nullable = false)
	private int percentage; 
    
    @Column(name = "fromDate", nullable = false)
	private Date fromDate; 
    
    @Column(name = "toDate", nullable = false)
	private Date toDate; 
    
    @Column(name = "text", nullable = false)
	private String text;
    
    @ManyToOne
    private Seller seller;
    
}
