package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.SampleOneToOneService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SampleOneToOneDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SampleOneToOne.
 */
@RestController
@RequestMapping("/api")
public class SampleOneToOneResource {

    private final Logger log = LoggerFactory.getLogger(SampleOneToOneResource.class);

    private static final String ENTITY_NAME = "sampleOneToOne";

    private final SampleOneToOneService sampleOneToOneService;

    public SampleOneToOneResource(SampleOneToOneService sampleOneToOneService) {
        this.sampleOneToOneService = sampleOneToOneService;
    }

    /**
     * POST  /sample-one-to-ones : Create a new sampleOneToOne.
     *
     * @param sampleOneToOneDTO the sampleOneToOneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sampleOneToOneDTO, or with status 400 (Bad Request) if the sampleOneToOne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sample-one-to-ones")
    @Timed
    public ResponseEntity<SampleOneToOneDTO> createSampleOneToOne(@RequestBody SampleOneToOneDTO sampleOneToOneDTO) throws URISyntaxException {
        log.debug("REST request to save SampleOneToOne : {}", sampleOneToOneDTO);
        if (sampleOneToOneDTO.getId() != null) {
            throw new BadRequestAlertException("A new sampleOneToOne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleOneToOneDTO result = sampleOneToOneService.save(sampleOneToOneDTO);
        return ResponseEntity.created(new URI("/api/sample-one-to-ones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sample-one-to-ones : Updates an existing sampleOneToOne.
     *
     * @param sampleOneToOneDTO the sampleOneToOneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sampleOneToOneDTO,
     * or with status 400 (Bad Request) if the sampleOneToOneDTO is not valid,
     * or with status 500 (Internal Server Error) if the sampleOneToOneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sample-one-to-ones")
    @Timed
    public ResponseEntity<SampleOneToOneDTO> updateSampleOneToOne(@RequestBody SampleOneToOneDTO sampleOneToOneDTO) throws URISyntaxException {
        log.debug("REST request to update SampleOneToOne : {}", sampleOneToOneDTO);
        if (sampleOneToOneDTO.getId() == null) {
            return createSampleOneToOne(sampleOneToOneDTO);
        }
        SampleOneToOneDTO result = sampleOneToOneService.save(sampleOneToOneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sampleOneToOneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sample-one-to-ones : get all the sampleOneToOnes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sampleOneToOnes in body
     */
    @GetMapping("/sample-one-to-ones")
    @Timed
    public List<SampleOneToOneDTO> getAllSampleOneToOnes() {
        log.debug("REST request to get all SampleOneToOnes");
        return sampleOneToOneService.findAll();
        }

    /**
     * GET  /sample-one-to-ones/:id : get the "id" sampleOneToOne.
     *
     * @param id the id of the sampleOneToOneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sampleOneToOneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sample-one-to-ones/{id}")
    @Timed
    public ResponseEntity<SampleOneToOneDTO> getSampleOneToOne(@PathVariable Long id) {
        log.debug("REST request to get SampleOneToOne : {}", id);
        SampleOneToOneDTO sampleOneToOneDTO = sampleOneToOneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sampleOneToOneDTO));
    }

    /**
     * DELETE  /sample-one-to-ones/:id : delete the "id" sampleOneToOne.
     *
     * @param id the id of the sampleOneToOneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sample-one-to-ones/{id}")
    @Timed
    public ResponseEntity<Void> deleteSampleOneToOne(@PathVariable Long id) {
        log.debug("REST request to delete SampleOneToOne : {}", id);
        sampleOneToOneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sample-one-to-ones?query=:query : search for the sampleOneToOne corresponding
     * to the query.
     *
     * @param query the query of the sampleOneToOne search
     * @return the result of the search
     */
    @GetMapping("/_search/sample-one-to-ones")
    @Timed
    public List<SampleOneToOneDTO> searchSampleOneToOnes(@RequestParam String query) {
        log.debug("REST request to search SampleOneToOnes for query {}", query);
        return sampleOneToOneService.search(query);
    }

}
