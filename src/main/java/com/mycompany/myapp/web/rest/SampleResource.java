package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.SampleService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SampleDTO;
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
 * REST controller for managing Sample.
 */
@RestController
@RequestMapping("/api")
public class SampleResource {

    private final Logger log = LoggerFactory.getLogger(SampleResource.class);

    private static final String ENTITY_NAME = "sample";

    private final SampleService sampleService;

    public SampleResource(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    /**
     * POST  /samples : Create a new sample.
     *
     * @param sampleDTO the sampleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sampleDTO, or with status 400 (Bad Request) if the sample has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/samples")
    @Timed
    public ResponseEntity<SampleDTO> createSample(@RequestBody SampleDTO sampleDTO) throws URISyntaxException {
        log.debug("REST request to save Sample : {}", sampleDTO);
        if (sampleDTO.getId() != null) {
            throw new BadRequestAlertException("A new sample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleDTO result = sampleService.save(sampleDTO);
        return ResponseEntity.created(new URI("/api/samples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /samples : Updates an existing sample.
     *
     * @param sampleDTO the sampleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sampleDTO,
     * or with status 400 (Bad Request) if the sampleDTO is not valid,
     * or with status 500 (Internal Server Error) if the sampleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/samples")
    @Timed
    public ResponseEntity<SampleDTO> updateSample(@RequestBody SampleDTO sampleDTO) throws URISyntaxException {
        log.debug("REST request to update Sample : {}", sampleDTO);
        if (sampleDTO.getId() == null) {
            return createSample(sampleDTO);
        }
        SampleDTO result = sampleService.save(sampleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sampleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /samples : get all the samples.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of samples in body
     */
    @GetMapping("/samples")
    @Timed
    public List<SampleDTO> getAllSamples() {
        log.debug("REST request to get all Samples");
        return sampleService.findAll();
        }

    /**
     * GET  /samples/:id : get the "id" sample.
     *
     * @param id the id of the sampleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sampleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/samples/{id}")
    @Timed
    public ResponseEntity<SampleDTO> getSample(@PathVariable Long id) {
        log.debug("REST request to get Sample : {}", id);
        SampleDTO sampleDTO = sampleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sampleDTO));
    }

    /**
     * DELETE  /samples/:id : delete the "id" sample.
     *
     * @param id the id of the sampleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/samples/{id}")
    @Timed
    public ResponseEntity<Void> deleteSample(@PathVariable Long id) {
        log.debug("REST request to delete Sample : {}", id);
        sampleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/samples?query=:query : search for the sample corresponding
     * to the query.
     *
     * @param query the query of the sample search
     * @return the result of the search
     */
    @GetMapping("/_search/samples")
    @Timed
    public List<SampleDTO> searchSamples(@RequestParam String query) {
        log.debug("REST request to search Samples for query {}", query);
        return sampleService.search(query);
    }

}
