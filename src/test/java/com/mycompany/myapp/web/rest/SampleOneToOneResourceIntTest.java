package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SampleProjectApp;

import com.mycompany.myapp.domain.SampleOneToOne;
import com.mycompany.myapp.repository.SampleOneToOneRepository;
import com.mycompany.myapp.service.SampleOneToOneService;
import com.mycompany.myapp.repository.search.SampleOneToOneSearchRepository;
import com.mycompany.myapp.service.dto.SampleOneToOneDTO;
import com.mycompany.myapp.service.mapper.SampleOneToOneMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SampleOneToOneResource REST controller.
 *
 * @see SampleOneToOneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleProjectApp.class)
public class SampleOneToOneResourceIntTest {

    private static final String DEFAULT_SAMPLE_ONE = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_ONE = "BBBBBBBBBB";

    @Autowired
    private SampleOneToOneRepository sampleOneToOneRepository;

    @Autowired
    private SampleOneToOneMapper sampleOneToOneMapper;

    @Autowired
    private SampleOneToOneService sampleOneToOneService;

    @Autowired
    private SampleOneToOneSearchRepository sampleOneToOneSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSampleOneToOneMockMvc;

    private SampleOneToOne sampleOneToOne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SampleOneToOneResource sampleOneToOneResource = new SampleOneToOneResource(sampleOneToOneService);
        this.restSampleOneToOneMockMvc = MockMvcBuilders.standaloneSetup(sampleOneToOneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SampleOneToOne createEntity(EntityManager em) {
        SampleOneToOne sampleOneToOne = new SampleOneToOne()
            .sampleOne(DEFAULT_SAMPLE_ONE);
        return sampleOneToOne;
    }

    @Before
    public void initTest() {
        sampleOneToOneSearchRepository.deleteAll();
        sampleOneToOne = createEntity(em);
    }

    @Test
    @Transactional
    public void createSampleOneToOne() throws Exception {
        int databaseSizeBeforeCreate = sampleOneToOneRepository.findAll().size();

        // Create the SampleOneToOne
        SampleOneToOneDTO sampleOneToOneDTO = sampleOneToOneMapper.toDto(sampleOneToOne);
        restSampleOneToOneMockMvc.perform(post("/api/sample-one-to-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOneToOneDTO)))
            .andExpect(status().isCreated());

        // Validate the SampleOneToOne in the database
        List<SampleOneToOne> sampleOneToOneList = sampleOneToOneRepository.findAll();
        assertThat(sampleOneToOneList).hasSize(databaseSizeBeforeCreate + 1);
        SampleOneToOne testSampleOneToOne = sampleOneToOneList.get(sampleOneToOneList.size() - 1);
        assertThat(testSampleOneToOne.getSampleOne()).isEqualTo(DEFAULT_SAMPLE_ONE);

        // Validate the SampleOneToOne in Elasticsearch
        SampleOneToOne sampleOneToOneEs = sampleOneToOneSearchRepository.findOne(testSampleOneToOne.getId());
        assertThat(sampleOneToOneEs).isEqualToIgnoringGivenFields(testSampleOneToOne);
    }

    @Test
    @Transactional
    public void createSampleOneToOneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sampleOneToOneRepository.findAll().size();

        // Create the SampleOneToOne with an existing ID
        sampleOneToOne.setId(1L);
        SampleOneToOneDTO sampleOneToOneDTO = sampleOneToOneMapper.toDto(sampleOneToOne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleOneToOneMockMvc.perform(post("/api/sample-one-to-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOneToOneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SampleOneToOne in the database
        List<SampleOneToOne> sampleOneToOneList = sampleOneToOneRepository.findAll();
        assertThat(sampleOneToOneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSampleOneToOnes() throws Exception {
        // Initialize the database
        sampleOneToOneRepository.saveAndFlush(sampleOneToOne);

        // Get all the sampleOneToOneList
        restSampleOneToOneMockMvc.perform(get("/api/sample-one-to-ones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sampleOneToOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleOne").value(hasItem(DEFAULT_SAMPLE_ONE.toString())));
    }

    @Test
    @Transactional
    public void getSampleOneToOne() throws Exception {
        // Initialize the database
        sampleOneToOneRepository.saveAndFlush(sampleOneToOne);

        // Get the sampleOneToOne
        restSampleOneToOneMockMvc.perform(get("/api/sample-one-to-ones/{id}", sampleOneToOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sampleOneToOne.getId().intValue()))
            .andExpect(jsonPath("$.sampleOne").value(DEFAULT_SAMPLE_ONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSampleOneToOne() throws Exception {
        // Get the sampleOneToOne
        restSampleOneToOneMockMvc.perform(get("/api/sample-one-to-ones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSampleOneToOne() throws Exception {
        // Initialize the database
        sampleOneToOneRepository.saveAndFlush(sampleOneToOne);
        sampleOneToOneSearchRepository.save(sampleOneToOne);
        int databaseSizeBeforeUpdate = sampleOneToOneRepository.findAll().size();

        // Update the sampleOneToOne
        SampleOneToOne updatedSampleOneToOne = sampleOneToOneRepository.findOne(sampleOneToOne.getId());
        // Disconnect from session so that the updates on updatedSampleOneToOne are not directly saved in db
        em.detach(updatedSampleOneToOne);
        updatedSampleOneToOne
            .sampleOne(UPDATED_SAMPLE_ONE);
        SampleOneToOneDTO sampleOneToOneDTO = sampleOneToOneMapper.toDto(updatedSampleOneToOne);

        restSampleOneToOneMockMvc.perform(put("/api/sample-one-to-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOneToOneDTO)))
            .andExpect(status().isOk());

        // Validate the SampleOneToOne in the database
        List<SampleOneToOne> sampleOneToOneList = sampleOneToOneRepository.findAll();
        assertThat(sampleOneToOneList).hasSize(databaseSizeBeforeUpdate);
        SampleOneToOne testSampleOneToOne = sampleOneToOneList.get(sampleOneToOneList.size() - 1);
        assertThat(testSampleOneToOne.getSampleOne()).isEqualTo(UPDATED_SAMPLE_ONE);

        // Validate the SampleOneToOne in Elasticsearch
        SampleOneToOne sampleOneToOneEs = sampleOneToOneSearchRepository.findOne(testSampleOneToOne.getId());
        assertThat(sampleOneToOneEs).isEqualToIgnoringGivenFields(testSampleOneToOne);
    }

    @Test
    @Transactional
    public void updateNonExistingSampleOneToOne() throws Exception {
        int databaseSizeBeforeUpdate = sampleOneToOneRepository.findAll().size();

        // Create the SampleOneToOne
        SampleOneToOneDTO sampleOneToOneDTO = sampleOneToOneMapper.toDto(sampleOneToOne);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSampleOneToOneMockMvc.perform(put("/api/sample-one-to-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOneToOneDTO)))
            .andExpect(status().isCreated());

        // Validate the SampleOneToOne in the database
        List<SampleOneToOne> sampleOneToOneList = sampleOneToOneRepository.findAll();
        assertThat(sampleOneToOneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSampleOneToOne() throws Exception {
        // Initialize the database
        sampleOneToOneRepository.saveAndFlush(sampleOneToOne);
        sampleOneToOneSearchRepository.save(sampleOneToOne);
        int databaseSizeBeforeDelete = sampleOneToOneRepository.findAll().size();

        // Get the sampleOneToOne
        restSampleOneToOneMockMvc.perform(delete("/api/sample-one-to-ones/{id}", sampleOneToOne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sampleOneToOneExistsInEs = sampleOneToOneSearchRepository.exists(sampleOneToOne.getId());
        assertThat(sampleOneToOneExistsInEs).isFalse();

        // Validate the database is empty
        List<SampleOneToOne> sampleOneToOneList = sampleOneToOneRepository.findAll();
        assertThat(sampleOneToOneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSampleOneToOne() throws Exception {
        // Initialize the database
        sampleOneToOneRepository.saveAndFlush(sampleOneToOne);
        sampleOneToOneSearchRepository.save(sampleOneToOne);

        // Search the sampleOneToOne
        restSampleOneToOneMockMvc.perform(get("/api/_search/sample-one-to-ones?query=id:" + sampleOneToOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sampleOneToOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleOne").value(hasItem(DEFAULT_SAMPLE_ONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleOneToOne.class);
        SampleOneToOne sampleOneToOne1 = new SampleOneToOne();
        sampleOneToOne1.setId(1L);
        SampleOneToOne sampleOneToOne2 = new SampleOneToOne();
        sampleOneToOne2.setId(sampleOneToOne1.getId());
        assertThat(sampleOneToOne1).isEqualTo(sampleOneToOne2);
        sampleOneToOne2.setId(2L);
        assertThat(sampleOneToOne1).isNotEqualTo(sampleOneToOne2);
        sampleOneToOne1.setId(null);
        assertThat(sampleOneToOne1).isNotEqualTo(sampleOneToOne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleOneToOneDTO.class);
        SampleOneToOneDTO sampleOneToOneDTO1 = new SampleOneToOneDTO();
        sampleOneToOneDTO1.setId(1L);
        SampleOneToOneDTO sampleOneToOneDTO2 = new SampleOneToOneDTO();
        assertThat(sampleOneToOneDTO1).isNotEqualTo(sampleOneToOneDTO2);
        sampleOneToOneDTO2.setId(sampleOneToOneDTO1.getId());
        assertThat(sampleOneToOneDTO1).isEqualTo(sampleOneToOneDTO2);
        sampleOneToOneDTO2.setId(2L);
        assertThat(sampleOneToOneDTO1).isNotEqualTo(sampleOneToOneDTO2);
        sampleOneToOneDTO1.setId(null);
        assertThat(sampleOneToOneDTO1).isNotEqualTo(sampleOneToOneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sampleOneToOneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sampleOneToOneMapper.fromId(null)).isNull();
    }
}
