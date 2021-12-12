package elena.krunic.elastic.search.lucene.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import elena.krunic.elastic.search.lucene.model.ProductLucene;

public interface ProductLuceneRepository extends ElasticsearchRepository<ProductLucene, String> {

}
