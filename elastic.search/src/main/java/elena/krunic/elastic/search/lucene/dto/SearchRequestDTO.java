package elena.krunic.elastic.search.lucene.dto;

import java.util.List;

import org.elasticsearch.search.sort.SortOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchRequestDTO extends PageRequestDTO {
	
	private List<String> fields; 
	private String searchTerm;
	private String sortBy; 
	private SortOrder order; 

}
