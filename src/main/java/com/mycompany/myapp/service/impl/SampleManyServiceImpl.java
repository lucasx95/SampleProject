package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SampleManyService;
import com.mycompany.myapp.domain.SampleMany;
import com.mycompany.myapp.repository.SampleManyRepository;
import com.mycompany.myapp.repository.search.SampleManySearchRepository;
import com.mycompany.myapp.service.dto.SampleManyDTO;
import com.mycompany.myapp.service.mapper.SampleManyMapper;
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
 * Service Implementation for managing SampleMany.
 */
@Service
@Transactional
public class SampleManyServiceImpl implements SampleManyService {

    private final Logger log = LoggerFactory.getLogger(SampleManyServiceImpl.class);

    private final SampleManyRepository sampleManyRepository;

    private final SampleManyMapper sampleManyMapper;

    private final SampleManySearchRepository sampleManySearchRepository;

    public SampleManyServiceImpl(SampleManyRepository sampleManyRepository, SampleManyMapper sampleManyMapper, SampleManySearchRepository sampleManySearchRepository) {
        this.sampleManyRepository = sampleManyRepository;
        this.sampleManyMapper = sampleManyMapper;
        this.sampleManySearchRepository = sampleManySearchRepository;
    }

    /**
     * Save a sampleMany.
     *
     * @param sampleManyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SampleManyDTO save(SampleManyDTO sampleManyDTO) {
        log.debug("Request to save SampleMany : {}", sampleManyDTO);
        SampleMany sampleMany = sampleManyMapper.toEntity(sampleManyDTO);
        sampleMany = sampleManyRepository.save(sampleMany);
        SampleManyDTO result = sampleManyMapper.toDto(sampleMany);
        sampleManySearchRepository.save(sampleMany);
        return result;
    }

    /**
     * Get all the sampleManies.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SampleManyDTO> findAll() {
        log.debug("Request to get all SampleManies");
        return sampleManyRepository.findAll().stream()
            .map(sampleManyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sampleMany by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SampleManyDTO findOne(Long id) {
        log.debug("Request to get SampleMany : {}", id);
        SampleMany sampleMany = sampleManyRepository.findOne(id);
        return sampleManyMapper.toDto(sampleMany);
    }

    /**
     * Delete the sampleMany by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SampleMany : {}", id);
        sampleManyRepository.delete(id);
        sampleManySearchRepository.delete(id);
    }

    /**
     * Search for the sampleMany corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SampleManyDTO> search(String query) {
        log.debug("Request to search SampleManies for query {}", query);
        return StreamSupport
            .stream(sampleManySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(sampleManyMapper::toDto)
            .collect(Collectors.toList());
    }
}
