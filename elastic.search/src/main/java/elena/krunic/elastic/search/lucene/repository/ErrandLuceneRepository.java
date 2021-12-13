package elena.krunic.elastic.search.lucene.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import elena.krunic.elastic.search.lucene.dto.ErrandLuceneDTO;

@Repository
public interface ErrandLuceneRepository extends ElasticsearchRepository<ErrandLuceneDTO, Long> {

}
