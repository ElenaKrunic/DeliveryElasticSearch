package elena.krunic.elastic.search.lucene.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageRequestDTO {

	private static final int DEFAULT_SIZE = 100; 
	
	private int page; 
	private int size;
	
	public int getSize() {
		  return size != 0 ? size : DEFAULT_SIZE;
	}
}
