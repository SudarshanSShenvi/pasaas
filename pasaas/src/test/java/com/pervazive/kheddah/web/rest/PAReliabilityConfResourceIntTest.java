package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAReliabilityConf;
import com.pervazive.kheddah.repository.PAReliabilityConfRepository;
import com.pervazive.kheddah.service.PAReliabilityConfService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pervazive.kheddah.domain.enumeration.PAStatus;
/**
 * Test class for the PAReliabilityConfResource REST controller.
 *
 * @see PAReliabilityConfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAReliabilityConfResourceIntTest {

    private static final Float DEFAULT_BSCORE = 1F;
    private static final Float UPDATED_BSCORE = 2F;

    private static final Float DEFAULT_CSCORE = 1F;
    private static final Float UPDATED_CSCORE = 2F;

    private static final String DEFAULT_DATASET = "AAAAAAAAAA";
    private static final String UPDATED_DATASET = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAReliabilityConfRepository pAReliabilityConfRepository;

    @Inject
    private PAReliabilityConfService pAReliabilityConfService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAReliabilityConfMockMvc;

    private PAReliabilityConf pAReliabilityConf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAReliabilityConfResource pAReliabilityConfResource = new PAReliabilityConfResource();
        ReflectionTestUtils.setField(pAReliabilityConfResource, "pAReliabilityConfService", pAReliabilityConfService);
        this.restPAReliabilityConfMockMvc = MockMvcBuilders.standaloneSetup(pAReliabilityConfResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAReliabilityConf createEntity(EntityManager em) {
        PAReliabilityConf pAReliabilityConf = new PAReliabilityConf()
                .bscore(DEFAULT_BSCORE)
                .cscore(DEFAULT_CSCORE)
                .dataset(DEFAULT_DATASET)
                .category(DEFAULT_CATEGORY)
                .pastatus(DEFAULT_PASTATUS);
        return pAReliabilityConf;
    }

    @Before
    public void initTest() {
        pAReliabilityConf = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAReliabilityConf() throws Exception {
        int databaseSizeBeforeCreate = pAReliabilityConfRepository.findAll().size();

        // Create the PAReliabilityConf

        restPAReliabilityConfMockMvc.perform(post("/api/p-a-reliability-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAReliabilityConf)))
            .andExpect(status().isCreated());

        // Validate the PAReliabilityConf in the database
        List<PAReliabilityConf> pAReliabilityConfList = pAReliabilityConfRepository.findAll();
        assertThat(pAReliabilityConfList).hasSize(databaseSizeBeforeCreate + 1);
        PAReliabilityConf testPAReliabilityConf = pAReliabilityConfList.get(pAReliabilityConfList.size() - 1);
        assertThat(testPAReliabilityConf.getBscore()).isEqualTo(DEFAULT_BSCORE);
        assertThat(testPAReliabilityConf.getCscore()).isEqualTo(DEFAULT_CSCORE);
        assertThat(testPAReliabilityConf.getDataset()).isEqualTo(DEFAULT_DATASET);
        assertThat(testPAReliabilityConf.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testPAReliabilityConf.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAReliabilityConfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAReliabilityConfRepository.findAll().size();

        // Create the PAReliabilityConf with an existing ID
        PAReliabilityConf existingPAReliabilityConf = new PAReliabilityConf();
        existingPAReliabilityConf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAReliabilityConfMockMvc.perform(post("/api/p-a-reliability-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAReliabilityConf)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAReliabilityConf> pAReliabilityConfList = pAReliabilityConfRepository.findAll();
        assertThat(pAReliabilityConfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAReliabilityConfs() throws Exception {
        // Initialize the database
        pAReliabilityConfRepository.saveAndFlush(pAReliabilityConf);

        // Get all the pAReliabilityConfList
        restPAReliabilityConfMockMvc.perform(get("/api/p-a-reliability-confs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAReliabilityConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].bscore").value(hasItem(DEFAULT_BSCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].cscore").value(hasItem(DEFAULT_CSCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].dataset").value(hasItem(DEFAULT_DATASET.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAReliabilityConf() throws Exception {
        // Initialize the database
        pAReliabilityConfRepository.saveAndFlush(pAReliabilityConf);

        // Get the pAReliabilityConf
        restPAReliabilityConfMockMvc.perform(get("/api/p-a-reliability-confs/{id}", pAReliabilityConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAReliabilityConf.getId().intValue()))
            .andExpect(jsonPath("$.bscore").value(DEFAULT_BSCORE.doubleValue()))
            .andExpect(jsonPath("$.cscore").value(DEFAULT_CSCORE.doubleValue()))
            .andExpect(jsonPath("$.dataset").value(DEFAULT_DATASET.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAReliabilityConf() throws Exception {
        // Get the pAReliabilityConf
        restPAReliabilityConfMockMvc.perform(get("/api/p-a-reliability-confs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAReliabilityConf() throws Exception {
        // Initialize the database
        pAReliabilityConfService.save(pAReliabilityConf);

        int databaseSizeBeforeUpdate = pAReliabilityConfRepository.findAll().size();

        // Update the pAReliabilityConf
        PAReliabilityConf updatedPAReliabilityConf = pAReliabilityConfRepository.findOne(pAReliabilityConf.getId());
        updatedPAReliabilityConf
                .bscore(UPDATED_BSCORE)
                .cscore(UPDATED_CSCORE)
                .dataset(UPDATED_DATASET)
                .category(UPDATED_CATEGORY)
                .pastatus(UPDATED_PASTATUS);

        restPAReliabilityConfMockMvc.perform(put("/api/p-a-reliability-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAReliabilityConf)))
            .andExpect(status().isOk());

        // Validate the PAReliabilityConf in the database
        List<PAReliabilityConf> pAReliabilityConfList = pAReliabilityConfRepository.findAll();
        assertThat(pAReliabilityConfList).hasSize(databaseSizeBeforeUpdate);
        PAReliabilityConf testPAReliabilityConf = pAReliabilityConfList.get(pAReliabilityConfList.size() - 1);
        assertThat(testPAReliabilityConf.getBscore()).isEqualTo(UPDATED_BSCORE);
        assertThat(testPAReliabilityConf.getCscore()).isEqualTo(UPDATED_CSCORE);
        assertThat(testPAReliabilityConf.getDataset()).isEqualTo(UPDATED_DATASET);
        assertThat(testPAReliabilityConf.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPAReliabilityConf.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAReliabilityConf() throws Exception {
        int databaseSizeBeforeUpdate = pAReliabilityConfRepository.findAll().size();

        // Create the PAReliabilityConf

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAReliabilityConfMockMvc.perform(put("/api/p-a-reliability-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAReliabilityConf)))
            .andExpect(status().isCreated());

        // Validate the PAReliabilityConf in the database
        List<PAReliabilityConf> pAReliabilityConfList = pAReliabilityConfRepository.findAll();
        assertThat(pAReliabilityConfList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAReliabilityConf() throws Exception {
        // Initialize the database
        pAReliabilityConfService.save(pAReliabilityConf);

        int databaseSizeBeforeDelete = pAReliabilityConfRepository.findAll().size();

        // Get the pAReliabilityConf
        restPAReliabilityConfMockMvc.perform(delete("/api/p-a-reliability-confs/{id}", pAReliabilityConf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAReliabilityConf> pAReliabilityConfList = pAReliabilityConfRepository.findAll();
        assertThat(pAReliabilityConfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
