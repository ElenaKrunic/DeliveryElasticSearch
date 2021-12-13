package elena.krunic.elastic.search.lucene.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import elena.krunic.elastic.search.lucene.dto.ProductLuceneDTO;
import elena.krunic.elastic.search.lucene.model.ProductLucene;

@Repository
public interface ProductLuceneRepository extends ElasticsearchRepository<ProductLuceneDTO, Long> {
	
}
