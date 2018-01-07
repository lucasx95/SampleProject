package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SampleOneToOneService;
import com.mycompany.myapp.domain.SampleOneToOne;
import com.mycompany.myapp.repository.SampleOneToOneRepository;
import com.mycompany.myapp.repository.search.SampleOneToOneSearchRepository;
import com.mycompany.myapp.service.dto.SampleOneToOneDTO;
import com.mycompany.myapp.service.mapper.SampleOneToOneMapper;
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
 * Service Implementation for managing SampleOneToOne.
 */
@Service
@Transactional
public class SampleOneToOneServiceImpl implements SampleOneToOneService {

    private final Logger log = LoggerFactory.getLogger(SampleOneToOneServiceImpl.class);

    private final SampleOneToOneRepository sampleOneToOneRepository;

    private final SampleOneToOneMapper sampleOneToOneMapper;

    private final SampleOneToOneSearchRepository sampleOneToOneSearchRepository;

    public SampleOneToOneServiceImpl(SampleOneToOneRepository sampleOneToOneRepository, SampleOneToOneMapper sampleOneToOneMapper, SampleOneToOneSearchRepository sampleOneToOneSearchRepository) {
        this.sampleOneToOneRepository = sampleOneToOneRepository;
        this.sampleOneToOneMapper = sampleOneToOneMapper;
        this.sampleOneToOneSearchRepository = sampleOneToOneSearchRepository;
    }

    /**
     * Save a sampleOneToOne.
     *
     * @param sampleOneToOneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SampleOneToOneDTO save(SampleOneToOneDTO sampleOneToOneDTO) {
        log.debug("Request to save SampleOneToOne : {}", sampleOneToOneDTO);
        SampleOneToOne sampleOneToOne = sampleOneToOneMapper.toEntity(sampleOneToOneDTO);
        sampleOneToOne = sampleOneToOneRepository.save(sampleOneToOne);
        SampleOneToOneDTO result = sampleOneToOneMapper.toDto(sampleOneToOne);
        sampleOneToOneSearchRepository.save(sampleOneToOne);
        return result;
    }

    /**
     * Get all the sampleOneToOnes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SampleOneToOneDTO> findAll() {
        log.debug("Request to get all SampleOneToOnes");
        return sampleOneToOneRepository.findAll().stream()
            .map(sampleOneToOneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sampleOneToOne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SampleOneToOneDTO findOne(Long id) {
        log.debug("Request to get SampleOneToOne : {}", id);
        SampleOneToOne sampleOneToOne = sampleOneToOneRepository.findOne(id);
        return sampleOneToOneMapper.toDto(sampleOneToOne);
    }

    /**
     * Delete the sampleOneToOne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SampleOneToOne : {}", id);
        sampleOneToOneRepository.delete(id);
        sampleOneToOneSearchRepository.delete(id);
    }

    /**
     * Search for the sampleOneToOne corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SampleOneToOneDTO> search(String query) {
        log.debug("Request to search SampleOneToOnes for query {}", query);
        return StreamSupport
            .stream(sampleOneToOneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(sampleOneToOneMapper::toDto)
            .collect(Collectors.toList());
    }
}
