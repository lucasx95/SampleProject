package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SampleProjectApp;

import com.mycompany.myapp.domain.Sample;
import com.mycompany.myapp.repository.SampleRepository;
import com.mycompany.myapp.service.SampleService;
import com.mycompany.myapp.repository.search.SampleSearchRepository;
import com.mycompany.myapp.service.dto.SampleDTO;
import com.mycompany.myapp.service.mapper.SampleMapper;
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
 * Test class for the SampleResource REST controller.
 *
 * @see SampleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleProjectApp.class)
public class SampleResourceIntTest {

    private static final String DEFAULT_SAMPLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SAMPLE_SIZE = 1;
    private static final Integer UPDATED_SAMPLE_SIZE = 2;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private SampleSearchRepository sampleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSampleMockMvc;

    private Sample sample;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SampleResource sampleResource = new SampleResource(sampleService);
        this.restSampleMockMvc = MockMvcBuilders.standaloneSetup(sampleResource)
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
    public static Sample createEntity(EntityManager em) {
        Sample sample = new Sample()
            .sampleName(DEFAULT_SAMPLE_NAME)
            .sampleSize(DEFAULT_SAMPLE_SIZE);
        return sample;
    }

    @Before
    public void initTest() {
        sampleSearchRepository.deleteAll();
        sample = createEntity(em);
    }

    @Test
    @Transactional
    public void createSample() throws Exception {
        int databaseSizeBeforeCreate = sampleRepository.findAll().size();

        // Create the Sample
        SampleDTO sampleDTO = sampleMapper.toDto(sample);
        restSampleMockMvc.perform(post("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleDTO)))
            .andExpect(status().isCreated());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeCreate + 1);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getSampleName()).isEqualTo(DEFAULT_SAMPLE_NAME);
        assertThat(testSample.getSampleSize()).isEqualTo(DEFAULT_SAMPLE_SIZE);

        // Validate the Sample in Elasticsearch
        Sample sampleEs = sampleSearchRepository.findOne(testSample.getId());
        assertThat(sampleEs).isEqualToIgnoringGivenFields(testSample);
    }

    @Test
    @Transactional
    public void createSampleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sampleRepository.findAll().size();

        // Create the Sample with an existing ID
        sample.setId(1L);
        SampleDTO sampleDTO = sampleMapper.toDto(sample);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleMockMvc.perform(post("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSamples() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        // Get all the sampleList
        restSampleMockMvc.perform(get("/api/samples?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sample.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleName").value(hasItem(DEFAULT_SAMPLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sampleSize").value(hasItem(DEFAULT_SAMPLE_SIZE)));
    }

    @Test
    @Transactional
    public void getSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        // Get the sample
        restSampleMockMvc.perform(get("/api/samples/{id}", sample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sample.getId().intValue()))
            .andExpect(jsonPath("$.sampleName").value(DEFAULT_SAMPLE_NAME.toString()))
            .andExpect(jsonPath("$.sampleSize").value(DEFAULT_SAMPLE_SIZE));
    }

    @Test
    @Transactional
    public void getNonExistingSample() throws Exception {
        // Get the sample
        restSampleMockMvc.perform(get("/api/samples/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);
        sampleSearchRepository.save(sample);
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Update the sample
        Sample updatedSample = sampleRepository.findOne(sample.getId());
        // Disconnect from session so that the updates on updatedSample are not directly saved in db
        em.detach(updatedSample);
        updatedSample
            .sampleName(UPDATED_SAMPLE_NAME)
            .sampleSize(UPDATED_SAMPLE_SIZE);
        SampleDTO sampleDTO = sampleMapper.toDto(updatedSample);

        restSampleMockMvc.perform(put("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleDTO)))
            .andExpect(status().isOk());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getSampleName()).isEqualTo(UPDATED_SAMPLE_NAME);
        assertThat(testSample.getSampleSize()).isEqualTo(UPDATED_SAMPLE_SIZE);

        // Validate the Sample in Elasticsearch
        Sample sampleEs = sampleSearchRepository.findOne(testSample.getId());
        assertThat(sampleEs).isEqualToIgnoringGivenFields(testSample);
    }

    @Test
    @Transactional
    public void updateNonExistingSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Create the Sample
        SampleDTO sampleDTO = sampleMapper.toDto(sample);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSampleMockMvc.perform(put("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleDTO)))
            .andExpect(status().isCreated());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);
        sampleSearchRepository.save(sample);
        int databaseSizeBeforeDelete = sampleRepository.findAll().size();

        // Get the sample
        restSampleMockMvc.perform(delete("/api/samples/{id}", sample.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sampleExistsInEs = sampleSearchRepository.exists(sample.getId());
        assertThat(sampleExistsInEs).isFalse();

        // Validate the database is empty
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);
        sampleSearchRepository.save(sample);

        // Search the sample
        restSampleMockMvc.perform(get("/api/_search/samples?query=id:" + sample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sample.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleName").value(hasItem(DEFAULT_SAMPLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sampleSize").value(hasItem(DEFAULT_SAMPLE_SIZE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sample.class);
        Sample sample1 = new Sample();
        sample1.setId(1L);
        Sample sample2 = new Sample();
        sample2.setId(sample1.getId());
        assertThat(sample1).isEqualTo(sample2);
        sample2.setId(2L);
        assertThat(sample1).isNotEqualTo(sample2);
        sample1.setId(null);
        assertThat(sample1).isNotEqualTo(sample2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleDTO.class);
        SampleDTO sampleDTO1 = new SampleDTO();
        sampleDTO1.setId(1L);
        SampleDTO sampleDTO2 = new SampleDTO();
        assertThat(sampleDTO1).isNotEqualTo(sampleDTO2);
        sampleDTO2.setId(sampleDTO1.getId());
        assertThat(sampleDTO1).isEqualTo(sampleDTO2);
        sampleDTO2.setId(2L);
        assertThat(sampleDTO1).isNotEqualTo(sampleDTO2);
        sampleDTO1.setId(null);
        assertThat(sampleDTO1).isNotEqualTo(sampleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sampleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sampleMapper.fromId(null)).isNull();
    }
}
