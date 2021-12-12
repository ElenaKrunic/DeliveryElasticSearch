package elena.krunic.elastic.search.lucene.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

@Configuration
@EnableElasticsearchRepositories(basePackages = {"elena.krunic.elastic.search.lucene.repository"})
public class Config extends AbstractElasticsearchConfiguration {

	@Value("${elasticsearch.url}")
	public String elasticsearchUrl;
	
	private JestClient jestClient; 
	
	@Bean
	@Override
	public RestHighLevelClient elasticsearchClient() {
			final ClientConfiguration config = ClientConfiguration.builder()
					.connectedTo(elasticsearchUrl)
					.build();
		
		return RestClients.create(config).rest();
	}
}
