package elena.krunic.elastic.search.lucene.search;

import java.io.IOException;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;

import elena.krunic.elastic.search.lucene.model.SearchType;

public class QueryBuilder {

	private static int maxEdits = 1; 
	
	public static int getMaxEdits() {
		return maxEdits; 
	}
	
	public static void setMaxEdits(int maxEdits) {
		QueryBuilder.maxEdits = maxEdits; 
	}
	
	public static org.elasticsearch.index.query.QueryBuilder buildQuery(SearchType queryType, String field, String value) throws Exception {
		
		if(field == null) {
			throw new Exception("Field je null"); 
		} else if (field.equals("")) {
			throw new Exception("Field je prazan string"); 
		} else if(value == null) {
			throw new Exception("Value je null"); 
		} else if (value.equals("")) {
			throw new Exception("Value je prazan string"); 
		}
		
		org.elasticsearch.index.query.QueryBuilder retVal = null; 
		
		if(queryType.equals(SearchType.REGULAR)) {
			retVal = QueryBuilders.termQuery(field, value);
		} else if(queryType.equals(SearchType.FUZZY)) {
			retVal = QueryBuilders.fuzzyQuery(field, value).fuzziness(Fuzziness.fromEdits(maxEdits));
		} else if(queryType.equals(SearchType.PREFIX)) {
			retVal = QueryBuilders.prefixQuery(field, value);
		} else if(queryType.equals(SearchType.RANGE)) {
			String[] values = value.split(" ");
			retVal = QueryBuilders.rangeQuery(field).from(values[0]).to(values[1]);
		} else {
			retVal = QueryBuilders.matchPhraseQuery(field, value);
		}
		
		return retVal; 
	}
}
