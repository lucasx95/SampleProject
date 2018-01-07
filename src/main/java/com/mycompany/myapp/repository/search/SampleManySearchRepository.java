package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.SampleMany;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SampleMany entity.
 */
public interface SampleManySearchRepository extends ElasticsearchRepository<SampleMany, Long> {
}
