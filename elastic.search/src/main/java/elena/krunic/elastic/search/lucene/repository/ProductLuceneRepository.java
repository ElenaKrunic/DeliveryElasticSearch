package elena.krunic.elastic.search.lucene.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import elena.krunic.elastic.search.lucene.dto.ProductLuceneDTO;

@Repository
public interface ProductLuceneRepository extends ElasticsearchRepository<ProductLuceneDTO, Long> {
	
}
