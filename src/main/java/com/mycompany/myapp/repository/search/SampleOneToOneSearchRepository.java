package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.SampleOneToOne;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SampleOneToOne entity.
 */
public interface SampleOneToOneSearchRepository extends ElasticsearchRepository<SampleOneToOne, Long> {
}
