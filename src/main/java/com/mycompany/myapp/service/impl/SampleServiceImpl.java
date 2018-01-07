package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SampleService;
import com.mycompany.myapp.domain.Sample;
import com.mycompany.myapp.repository.SampleRepository;
import com.mycompany.myapp.repository.search.SampleSearchRepository;
import com.mycompany.myapp.service.dto.SampleDTO;
import com.mycompany.myapp.service.mapper.SampleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Sample.
 */
@Service
@Transactional
public class SampleServiceImpl implements SampleService {

    private final Logger log = LoggerFactory.getLogger(SampleServiceImpl.class);

    private final SampleRepository sampleRepository;

    private final SampleMapper sampleMapper;

    private final SampleSearchRepository sampleSearchRepository;

    public SampleServiceImpl(SampleRepository sampleRepository, SampleMapper sampleMapper, SampleSearchRepository sampleSearchRepository) {
        this.sampleRepository = sampleRepository;
        this.sampleMapper = sampleMapper;
        this.sampleSearchRepository = sampleSearchRepository;
    }

    /**
     * Save a sample.
     *
     * @param sampleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SampleDTO save(SampleDTO sampleDTO) {
        log.debug("Request to save Sample : {}", sampleDTO);
        Sample sample = sampleMapper.toEntity(sampleDTO);
        sample = sampleRepository.save(sample);
        SampleDTO result = sampleMapper.toDto(sample);
        sampleSearchRepository.save(sample);
        return result;
    }

    /**
     * Get all the samples.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SampleDTO> findAll() {
        log.debug("Request to get all Samples");
        return sampleRepository.findAll().stream()
            .map(sampleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sample by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SampleDTO findOne(Long id) {
        log.debug("Request to get Sample : {}", id);
        Sample sample = sampleRepository.findOne(id);
        return sampleMapper.toDto(sample);
    }

    /**
     * Delete the sample by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sample : {}", id);
        sampleRepository.delete(id);
        sampleSearchRepository.delete(id);
    }

    /**
     * Search for the sample corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SampleDTO> search(String query) {
        log.debug("Request to search Samples for query {}", query);
        return StreamSupport
            .stream(sampleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(sampleMapper::toDto)
            .collect(Collectors.toList());
    }
}
