package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SampleProjectApp;

import com.mycompany.myapp.domain.SampleMany;
import com.mycompany.myapp.repository.SampleManyRepository;
import com.mycompany.myapp.service.SampleManyService;
import com.mycompany.myapp.repository.search.SampleManySearchRepository;
import com.mycompany.myapp.service.dto.SampleManyDTO;
import com.mycompany.myapp.service.mapper.SampleManyMapper;
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
 * Test class for the SampleManyResource REST controller.
 *
 * @see SampleManyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleProjectApp.class)
public class SampleManyResourceIntTest {

    private static final String DEFAULT_SAMPLE_MANY = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_MANY = "BBBBBBBBBB";

    @Autowired
    private SampleManyRepository sampleManyRepository;

    @Autowired
    private SampleManyMapper sampleManyMapper;

    @Autowired
    private SampleManyService sampleManyService;

    @Autowired
    private SampleManySearchRepository sampleManySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSampleManyMockMvc;

    private SampleMany sampleMany;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SampleManyResource sampleManyResource = new SampleManyResource(sampleManyService);
        this.restSampleManyMockMvc = MockMvcBuilders.standaloneSetup(sampleManyResource)
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
    public static SampleMany createEntity(EntityManager em) {
        SampleMany sampleMany = new SampleMany()
            .sampleMany(DEFAULT_SAMPLE_MANY);
        return sampleMany;
    }

    @Before
    public void initTest() {
        sampleManySearchRepository.deleteAll();
        sampleMany = createEntity(em);
    }

    @Test
    @Transactional
    public void createSampleMany() throws Exception {
        int databaseSizeBeforeCreate = sampleManyRepository.findAll().size();

        // Create the SampleMany
        SampleManyDTO sampleManyDTO = sampleManyMapper.toDto(sampleMany);
        restSampleManyMockMvc.perform(post("/api/sample-manies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleManyDTO)))
            .andExpect(status().isCreated());

        // Validate the SampleMany in the database
        List<SampleMany> sampleManyList = sampleManyRepository.findAll();
        assertThat(sampleManyList).hasSize(databaseSizeBeforeCreate + 1);
        SampleMany testSampleMany = sampleManyList.get(sampleManyList.size() - 1);
        assertThat(testSampleMany.getSampleMany()).isEqualTo(DEFAULT_SAMPLE_MANY);

        // Validate the SampleMany in Elasticsearch
        SampleMany sampleManyEs = sampleManySearchRepository.findOne(testSampleMany.getId());
        assertThat(sampleManyEs).isEqualToIgnoringGivenFields(testSampleMany);
    }

    @Test
    @Transactional
    public void createSampleManyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sampleManyRepository.findAll().size();

        // Create the SampleMany with an existing ID
        sampleMany.setId(1L);
        SampleManyDTO sampleManyDTO = sampleManyMapper.toDto(sampleMany);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleManyMockMvc.perform(post("/api/sample-manies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleManyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SampleMany in the database
        List<SampleMany> sampleManyList = sampleManyRepository.findAll();
        assertThat(sampleManyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSampleManies() throws Exception {
        // Initialize the database
        sampleManyRepository.saveAndFlush(sampleMany);

        // Get all the sampleManyList
        restSampleManyMockMvc.perform(get("/api/sample-manies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sampleMany.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleMany").value(hasItem(DEFAULT_SAMPLE_MANY.toString())));
    }

    @Test
    @Transactional
    public void getSampleMany() throws Exception {
        // Initialize the database
        sampleManyRepository.saveAndFlush(sampleMany);

        // Get the sampleMany
        restSampleManyMockMvc.perform(get("/api/sample-manies/{id}", sampleMany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sampleMany.getId().intValue()))
            .andExpect(jsonPath("$.sampleMany").value(DEFAULT_SAMPLE_MANY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSampleMany() throws Exception {
        // Get the sampleMany
        restSampleManyMockMvc.perform(get("/api/sample-manies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSampleMany() throws Exception {
        // Initialize the database
        sampleManyRepository.saveAndFlush(sampleMany);
        sampleManySearchRepository.save(sampleMany);
        int databaseSizeBeforeUpdate = sampleManyRepository.findAll().size();

        // Update the sampleMany
        SampleMany updatedSampleMany = sampleManyRepository.findOne(sampleMany.getId());
        // Disconnect from session so that the updates on updatedSampleMany are not directly saved in db
        em.detach(updatedSampleMany);
        updatedSampleMany
            .sampleMany(UPDATED_SAMPLE_MANY);
        SampleManyDTO sampleManyDTO = sampleManyMapper.toDto(updatedSampleMany);

        restSampleManyMockMvc.perform(put("/api/sample-manies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleManyDTO)))
            .andExpect(status().isOk());

        // Validate the SampleMany in the database
        List<SampleMany> sampleManyList = sampleManyRepository.findAll();
        assertThat(sampleManyList).hasSize(databaseSizeBeforeUpdate);
        SampleMany testSampleMany = sampleManyList.get(sampleManyList.size() - 1);
        assertThat(testSampleMany.getSampleMany()).isEqualTo(UPDATED_SAMPLE_MANY);

        // Validate the SampleMany in Elasticsearch
        SampleMany sampleManyEs = sampleManySearchRepository.findOne(testSampleMany.getId());
        assertThat(sampleManyEs).isEqualToIgnoringGivenFields(testSampleMany);
    }

    @Test
    @Transactional
    public void updateNonExistingSampleMany() throws Exception {
        int databaseSizeBeforeUpdate = sampleManyRepository.findAll().size();

        // Create the SampleMany
        SampleManyDTO sampleManyDTO = sampleManyMapper.toDto(sampleMany);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSampleManyMockMvc.perform(put("/api/sample-manies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleManyDTO)))
            .andExpect(status().isCreated());

        // Validate the SampleMany in the database
        List<SampleMany> sampleManyList = sampleManyRepository.findAll();
        assertThat(sampleManyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSampleMany() throws Exception {
        // Initialize the database
        sampleManyRepository.saveAndFlush(sampleMany);
        sampleManySearchRepository.save(sampleMany);
        int databaseSizeBeforeDelete = sampleManyRepository.findAll().size();

        // Get the sampleMany
        restSampleManyMockMvc.perform(delete("/api/sample-manies/{id}", sampleMany.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sampleManyExistsInEs = sampleManySearchRepository.exists(sampleMany.getId());
        assertThat(sampleManyExistsInEs).isFalse();

        // Validate the database is empty
        List<SampleMany> sampleManyList = sampleManyRepository.findAll();
        assertThat(sampleManyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSampleMany() throws Exception {
        // Initialize the database
        sampleManyRepository.saveAndFlush(sampleMany);
        sampleManySearchRepository.save(sampleMany);

        // Search the sampleMany
        restSampleManyMockMvc.perform(get("/api/_search/sample-manies?query=id:" + sampleMany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sampleMany.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleMany").value(hasItem(DEFAULT_SAMPLE_MANY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleMany.class);
        SampleMany sampleMany1 = new SampleMany();
        sampleMany1.setId(1L);
        SampleMany sampleMany2 = new SampleMany();
        sampleMany2.setId(sampleMany1.getId());
        assertThat(sampleMany1).isEqualTo(sampleMany2);
        sampleMany2.setId(2L);
        assertThat(sampleMany1).isNotEqualTo(sampleMany2);
        sampleMany1.setId(null);
        assertThat(sampleMany1).isNotEqualTo(sampleMany2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleManyDTO.class);
        SampleManyDTO sampleManyDTO1 = new SampleManyDTO();
        sampleManyDTO1.setId(1L);
        SampleManyDTO sampleManyDTO2 = new SampleManyDTO();
        assertThat(sampleManyDTO1).isNotEqualTo(sampleManyDTO2);
        sampleManyDTO2.setId(sampleManyDTO1.getId());
        assertThat(sampleManyDTO1).isEqualTo(sampleManyDTO2);
        sampleManyDTO2.setId(2L);
        assertThat(sampleManyDTO1).isNotEqualTo(sampleManyDTO2);
        sampleManyDTO1.setId(null);
        assertThat(sampleManyDTO1).isNotEqualTo(sampleManyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sampleManyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sampleManyMapper.fromId(null)).isNull();
    }
}
