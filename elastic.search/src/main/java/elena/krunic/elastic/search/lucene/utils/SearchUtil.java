package elena.krunic.elastic.search.lucene.utils;

import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import elena.krunic.elastic.search.lucene.dto.SearchRequestDTO;

public class SearchUtil {

	public static SearchRequest buildSearchRequest(String productIndex, SearchRequestDTO dto) {
		
		try {
			//final int page = dto.getPage(); 
			//final int size = dto.getSize(); 
			//final int from = page <= 0 ? 0 : page * size;
			
			 SearchSourceBuilder builder = new SearchSourceBuilder()
					//.from(from)
					//.size(size)
					.postFilter(getQueryBuilder(dto));
			
			  if (dto.getSortBy() != null) {
	                builder = builder.sort(
	                        dto.getSortBy(),
	                        dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC
	                );
	            }
			
			//System.out.println("Query builder je " + getQueryBuilder(dto).getName());
			
			//if(dto.getSortBy() != null) {
				//builder = builder.sort(dto.getSortBy(), dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
			//}
			
			SearchRequest request = new SearchRequest(productIndex); 
			request.source(builder); 
			
			
			return request; 
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	private static QueryBuilder getQueryBuilder(SearchRequestDTO dto) {

		if(dto == null) {
			return null; 
		}
		
		final List<String> fields = dto.getFields(); 
	
		if(CollectionUtils.isEmpty(fields)) {
			return null; 
		}
		
		if(fields.size() > 1) {
			MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
					.type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
					.operator(Operator.AND); 
			
			fields.forEach(queryBuilder :: field);
			
			return queryBuilder; 
		}
		
		return fields.stream()
				.findFirst()
				.map(field -> QueryBuilders.matchQuery(field, dto.getSearchTerm()).operator(Operator.AND)).orElse(null);
	}

}
	