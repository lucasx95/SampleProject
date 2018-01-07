package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Sample;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sample entity.
 */
public interface SampleSearchRepository extends ElasticsearchRepository<Sample, Long> {
}
