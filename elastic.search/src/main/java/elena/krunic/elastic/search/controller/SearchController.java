package elena.krunic.elastic.search.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.krunic.elastic.search.dto.ErrandDTO;
import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.lucene.model.AdvancedQuery;
import elena.krunic.elastic.search.lucene.model.RequiredHighlight;
import elena.krunic.elastic.search.lucene.model.SearchType;
import elena.krunic.elastic.search.lucene.model.SimpleQuery;
import elena.krunic.elastic.search.lucene.search.ResultRetriever;

@RestController
@RequestMapping("api/search")
public class SearchController {
 
	@PostMapping(value = "/term/products", consumes = "application/json")
	public ResponseEntity<List<ProductDTO>> searchProductTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ProductDTO> result = ResultRetriever.getProductResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@PostMapping(value = "/fuzzy/products", consumes = "application/json")
	public ResponseEntity<List<ProductDTO>> searchProductFuzzyQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.FUZZY, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ProductDTO> result = ResultRetriever.getProductResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
 	
	@PostMapping(value = "/prefix/products", consumes = "application/json")
	public ResponseEntity<List<ProductDTO>> searchProductPrefixQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.PREFIX, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ProductDTO> result = ResultRetriever.getProductResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@PostMapping(value = "/range/products", consumes = "application/json")
	public ResponseEntity<List<ProductDTO>> searchProductRangeQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.RANGE, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ProductDTO> result = ResultRetriever.getProductResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	

	@PostMapping(value = "/phrase/products", consumes = "application/json")
	public ResponseEntity<List<ProductDTO>> searchProductPhraseQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.PHRASE, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ProductDTO> result = ResultRetriever.getProductResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@PostMapping(value="/boolean/products", consumes="application/json")
    public ResponseEntity<List<ProductDTO>> searchProductBooleanQuery(@RequestBody AdvancedQuery advancedQuery) throws Exception{
	QueryBuilder query1 = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, advancedQuery.getField1(), advancedQuery.getValue1());
    QueryBuilder query2 = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, advancedQuery.getField2(), advancedQuery.getValue2());

    BoolQueryBuilder builder = QueryBuilders.boolQuery();
    if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
        builder.must(query1);
        builder.must(query2);
    }else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
        builder.should(query1);
        builder.should(query2);
    }else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
        builder.must(query1);
        builder.mustNot(query2);
    }
    List<RequiredHighlight> rh = new ArrayList<>();
    rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
    rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
    List<ProductDTO> result = ResultRetriever.getProductResults(builder, rh);
    return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/term/errands", consumes = "application/json")
	public ResponseEntity<List<ErrandDTO>> searchErrandTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ErrandDTO> result = ResultRetriever.getErrandResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@PostMapping(value = "/fuzzy/errands", consumes = "application/json")
	public ResponseEntity<List<ErrandDTO>> searchErrandFuzzyQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.FUZZY, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ErrandDTO> result = ResultRetriever.getErrandResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@PostMapping(value = "/prefix/errands", consumes = "application/json")
	public ResponseEntity<List<ErrandDTO>> searchErrandPrefixQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.PREFIX, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ErrandDTO> result = ResultRetriever.getErrandResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@PostMapping(value = "/range/errands", consumes = "application/json")
	public ResponseEntity<List<ErrandDTO>> searchErrandRangeQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.RANGE, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ErrandDTO> result = ResultRetriever.getErrandResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@PostMapping(value = "/phrase/errands", consumes = "application/json")
	public ResponseEntity<List<ErrandDTO>> searchErrandPhraseQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.PHRASE, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ErrandDTO> result = ResultRetriever.getErrandResults(queryBuilder, rh);
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@PostMapping(value="/boolean/errands", consumes="application/json")
    public ResponseEntity<List<ErrandDTO>> searchErrandBooleanQuery(@RequestBody AdvancedQuery advancedQuery) throws Exception{
	QueryBuilder query1 = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, advancedQuery.getField1(), advancedQuery.getValue1());
    QueryBuilder query2 = elena.krunic.elastic.search.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, advancedQuery.getField2(), advancedQuery.getValue2());

    BoolQueryBuilder builder = QueryBuilders.boolQuery();
    if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
        builder.must(query1);
        builder.must(query2);
    }else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
        builder.should(query1);
        builder.should(query2);
    }else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
        builder.must(query1);
        builder.mustNot(query2);
    }
    List<RequiredHighlight> rh = new ArrayList<>();
    rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
    rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
    List<ErrandDTO> result = ResultRetriever.getErrandResults(builder, rh);
    return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
}
