package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SampleManyDTO;
import java.util.List;

/**
 * Service Interface for managing SampleMany.
 */
public interface SampleManyService {

    /**
     * Save a sampleMany.
     *
     * @param sampleManyDTO the entity to save
     * @return the persisted entity
     */
    SampleManyDTO save(SampleManyDTO sampleManyDTO);

    /**
     * Get all the sampleManies.
     *
     * @return the list of entities
     */
    List<SampleManyDTO> findAll();

    /**
     * Get the "id" sampleMany.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SampleManyDTO findOne(Long id);

    /**
     * Delete the "id" sampleMany.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sampleMany corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<SampleManyDTO> search(String query);
}
