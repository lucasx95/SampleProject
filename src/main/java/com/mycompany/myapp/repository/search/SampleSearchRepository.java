package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Sample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sample entity.
 */
public interface SampleSearchRepository extends ElasticsearchRepository<Sample, Long> {

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"sampleName\" : \"?0\"}}}}")
    Page<Sample> findBySampleName(String sampleName,Pageable pageable);

}
