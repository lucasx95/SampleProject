package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SampleDTO;
import java.util.List;

/**
 * Service Interface for managing Sample.
 */
public interface SampleService {

    /**
     * Save a sample.
     *
     * @param sampleDTO the entity to save
     * @return the persisted entity
     */
    SampleDTO save(SampleDTO sampleDTO);

    /**
     * Get all the samples.
     *
     * @return the list of entities
     */
    List<SampleDTO> findAll();

    /**
     * Get the "id" sample.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SampleDTO findOne(Long id);

    /**
     * Delete the "id" sample.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sample corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<SampleDTO> search(String query);
}
