package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SampleOneToOneDTO;
import java.util.List;

/**
 * Service Interface for managing SampleOneToOne.
 */
public interface SampleOneToOneService {

    /**
     * Save a sampleOneToOne.
     *
     * @param sampleOneToOneDTO the entity to save
     * @return the persisted entity
     */
    SampleOneToOneDTO save(SampleOneToOneDTO sampleOneToOneDTO);

    /**
     * Get all the sampleOneToOnes.
     *
     * @return the list of entities
     */
    List<SampleOneToOneDTO> findAll();

    /**
     * Get the "id" sampleOneToOne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SampleOneToOneDTO findOne(Long id);

    /**
     * Delete the "id" sampleOneToOne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sampleOneToOne corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<SampleOneToOneDTO> search(String query);
}
