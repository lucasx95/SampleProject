package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.SampleManyService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SampleManyDTO;
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
 * REST controller for managing SampleMany.
 */
@RestController
@RequestMapping("/api")
public class SampleManyResource {

    private final Logger log = LoggerFactory.getLogger(SampleManyResource.class);

    private static final String ENTITY_NAME = "sampleMany";

    private final SampleManyService sampleManyService;

    public SampleManyResource(SampleManyService sampleManyService) {
        this.sampleManyService = sampleManyService;
    }

    /**
     * POST  /sample-manies : Create a new sampleMany.
     *
     * @param sampleManyDTO the sampleManyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sampleManyDTO, or with status 400 (Bad Request) if the sampleMany has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sample-manies")
    @Timed
    public ResponseEntity<SampleManyDTO> createSampleMany(@RequestBody SampleManyDTO sampleManyDTO) throws URISyntaxException {
        log.debug("REST request to save SampleMany : {}", sampleManyDTO);
        if (sampleManyDTO.getId() != null) {
            throw new BadRequestAlertException("A new sampleMany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleManyDTO result = sampleManyService.save(sampleManyDTO);
        return ResponseEntity.created(new URI("/api/sample-manies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sample-manies : Updates an existing sampleMany.
     *
     * @param sampleManyDTO the sampleManyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sampleManyDTO,
     * or with status 400 (Bad Request) if the sampleManyDTO is not valid,
     * or with status 500 (Internal Server Error) if the sampleManyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sample-manies")
    @Timed
    public ResponseEntity<SampleManyDTO> updateSampleMany(@RequestBody SampleManyDTO sampleManyDTO) throws URISyntaxException {
        log.debug("REST request to update SampleMany : {}", sampleManyDTO);
        if (sampleManyDTO.getId() == null) {
            return createSampleMany(sampleManyDTO);
        }
        SampleManyDTO result = sampleManyService.save(sampleManyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sampleManyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sample-manies : get all the sampleManies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sampleManies in body
     */
    @GetMapping("/sample-manies")
    @Timed
    public List<SampleManyDTO> getAllSampleManies() {
        log.debug("REST request to get all SampleManies");
        return sampleManyService.findAll();
        }

    /**
     * GET  /sample-manies/:id : get the "id" sampleMany.
     *
     * @param id the id of the sampleManyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sampleManyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sample-manies/{id}")
    @Timed
    public ResponseEntity<SampleManyDTO> getSampleMany(@PathVariable Long id) {
        log.debug("REST request to get SampleMany : {}", id);
        SampleManyDTO sampleManyDTO = sampleManyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sampleManyDTO));
    }

    /**
     * DELETE  /sample-manies/:id : delete the "id" sampleMany.
     *
     * @param id the id of the sampleManyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sample-manies/{id}")
    @Timed
    public ResponseEntity<Void> deleteSampleMany(@PathVariable Long id) {
        log.debug("REST request to delete SampleMany : {}", id);
        sampleManyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sample-manies?query=:query : search for the sampleMany corresponding
     * to the query.
     *
     * @param query the query of the sampleMany search
     * @return the result of the search
     */
    @GetMapping("/_search/sample-manies")
    @Timed
    public List<SampleManyDTO> searchSampleManies(@RequestParam String query) {
        log.debug("REST request to search SampleManies for query {}", query);
        return sampleManyService.search(query);
    }

}
