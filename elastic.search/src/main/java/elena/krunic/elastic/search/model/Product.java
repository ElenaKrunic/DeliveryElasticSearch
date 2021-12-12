package elena.krunic.elastic.search.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="product")
@AllArgsConstructor
@Getter
@Setter
public class Product {
	
	public Product() {
		
	}

	//zbog testa nullable == true
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = true)
	private String name; 
    
    @Column(name = "description", nullable = true)
   	private String description;
    
    @Column(name = "price", nullable = true)
   	private int price;
    
    @Column(name = "path", nullable = true)
   	private String path; 
    
    //obrisati svaku vezu koju proizvod ima
    @OneToMany(mappedBy="product", cascade = CascadeType.REMOVE)
    private List<Item> items;

    @ManyToOne
    private Seller seller;
}
