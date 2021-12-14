package elena.krunic.elastic.search.lucene.utils;

import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
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
			final int page = dto.getPage(); 
			final int size = dto.getSize(); 
			final int from = page <= 0 ? 0 : page * size;
			
			 SearchSourceBuilder builder = new SearchSourceBuilder()
					.from(from)
					.size(size)
					.postFilter(getQueryBuilder(dto));
			
			  if (dto.getSortBy() != null) {
	                builder = builder.sort(
	                        dto.getSortBy(),
	                        dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC
	                );
	            }
						
			if(dto.getSortBy() != null) {
				builder = builder.sort(dto.getSortBy(), dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
			}
			
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

	public static SearchRequest buildRangeGTESearchRequest(String productIndex, String field, double price) {
		
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getGTERangeQueryBuilder(field, price));
			
			final SearchRequest request = new SearchRequest(productIndex);
            request.source(builder);

            return request;
            
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public static SearchRequest buildBooleanSearchRequest(String productIndex, final SearchRequestDTO dto, final double price) {
		try {
			final QueryBuilder searchQuery = getQueryBuilder(dto);
			final QueryBuilder priceQuery = getGTERangeQueryBuilder("price", price);
			
			final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().mustNot(searchQuery).must(priceQuery);
			
			SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(boolQuery);
			
			 if (dto.getSortBy() != null) {
	                builder = builder.sort(
	                        dto.getSortBy(),
	                        dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC
	                );
	            }
			 
			  final SearchRequest request = new SearchRequest(productIndex);
	          request.source(builder);

	            return request;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SearchRequest buildRangeGreaterThenSearchRequest(String productIndex, String field, double price) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getGreaterThenRangeQueryBuilder(field, price));
			final SearchRequest request = new SearchRequest(productIndex); 
			request.source(builder); 
			
			return request; 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null; 
	}
	
	public static SearchRequest buildRangeLTESearchRequest(String productIndex, String field, double price) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getLTERangeQueryBuilder(field, price)); 
			final SearchRequest request = new SearchRequest(productIndex); 
			request.source(builder); 
			
			return request; 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static SearchRequest buildRangeLessThenSearchRequest(String productIndex, String field, double price) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getLessThenRangeQueryBuilder(field, price)); 
			final SearchRequest request = new SearchRequest(productIndex); 
			request.source(builder); 
			
			return request; 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static QueryBuilder getGTERangeQueryBuilder(String field, double price) {
		return QueryBuilders.rangeQuery(field).gte(price);
	}

	private static QueryBuilder getGreaterThenRangeQueryBuilder(String productIndex, double price) {
		return QueryBuilders.rangeQuery(productIndex).gt(price);
	}
	
	private static QueryBuilder getLTERangeQueryBuilder(String productIndex, double price) {
		return QueryBuilders.rangeQuery(productIndex).lte(price); 
	}
	
	private static QueryBuilder getLessThenRangeQueryBuilder(String productIndex, double price) {
		return QueryBuilders.rangeQuery(productIndex).lt(price);
	}

}
	