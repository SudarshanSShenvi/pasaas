package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PASaxCodeTmp;
import com.pervazive.kheddah.repository.PASaxCodeTmpRepository;
import com.pervazive.kheddah.service.PASaxCodeTmpService;

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
 * Test class for the PASaxCodeTmpResource REST controller.
 *
 * @see PASaxCodeTmpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PASaxCodeTmpResourceIntTest {

    private static final String DEFAULT_DISTALARM = "AAAAAAAAAA";
    private static final String UPDATED_DISTALARM = "BBBBBBBBBB";

    private static final String DEFAULT_SAXCODE = "AAAAAAAAAA";
    private static final String UPDATED_SAXCODE = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL = "BBBBBBBBBB";

    private static final String DEFAULT_PAINTERVAL = "AAAAAAAAAA";
    private static final String UPDATED_PAINTERVAL = "BBBBBBBBBB";

    private static final String DEFAULT_SEVERITY = "AAAAAAAAAA";
    private static final String UPDATED_SEVERITY = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PASaxCodeTmpRepository pASaxCodeTmpRepository;

    @Inject
    private PASaxCodeTmpService pASaxCodeTmpService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPASaxCodeTmpMockMvc;

    private PASaxCodeTmp pASaxCodeTmp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PASaxCodeTmpResource pASaxCodeTmpResource = new PASaxCodeTmpResource();
        ReflectionTestUtils.setField(pASaxCodeTmpResource, "pASaxCodeTmpService", pASaxCodeTmpService);
        this.restPASaxCodeTmpMockMvc = MockMvcBuilders.standaloneSetup(pASaxCodeTmpResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PASaxCodeTmp createEntity(EntityManager em) {
        PASaxCodeTmp pASaxCodeTmp = new PASaxCodeTmp()
                .distalarm(DEFAULT_DISTALARM)
                .saxcode(DEFAULT_SAXCODE)
                .total(DEFAULT_TOTAL)
                .painterval(DEFAULT_PAINTERVAL)
                .severity(DEFAULT_SEVERITY)
                .pastatus(DEFAULT_PASTATUS);
        return pASaxCodeTmp;
    }

    @Before
    public void initTest() {
        pASaxCodeTmp = createEntity(em);
    }

    @Test
    @Transactional
    public void createPASaxCodeTmp() throws Exception {
        int databaseSizeBeforeCreate = pASaxCodeTmpRepository.findAll().size();

        // Create the PASaxCodeTmp

        restPASaxCodeTmpMockMvc.perform(post("/api/p-a-sax-code-tmps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pASaxCodeTmp)))
            .andExpect(status().isCreated());

        // Validate the PASaxCodeTmp in the database
        List<PASaxCodeTmp> pASaxCodeTmpList = pASaxCodeTmpRepository.findAll();
        assertThat(pASaxCodeTmpList).hasSize(databaseSizeBeforeCreate + 1);
        PASaxCodeTmp testPASaxCodeTmp = pASaxCodeTmpList.get(pASaxCodeTmpList.size() - 1);
        assertThat(testPASaxCodeTmp.getDistalarm()).isEqualTo(DEFAULT_DISTALARM);
        assertThat(testPASaxCodeTmp.getSaxcode()).isEqualTo(DEFAULT_SAXCODE);
        assertThat(testPASaxCodeTmp.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testPASaxCodeTmp.getPainterval()).isEqualTo(DEFAULT_PAINTERVAL);
        assertThat(testPASaxCodeTmp.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testPASaxCodeTmp.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPASaxCodeTmpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pASaxCodeTmpRepository.findAll().size();

        // Create the PASaxCodeTmp with an existing ID
        PASaxCodeTmp existingPASaxCodeTmp = new PASaxCodeTmp();
        existingPASaxCodeTmp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPASaxCodeTmpMockMvc.perform(post("/api/p-a-sax-code-tmps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPASaxCodeTmp)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PASaxCodeTmp> pASaxCodeTmpList = pASaxCodeTmpRepository.findAll();
        assertThat(pASaxCodeTmpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPASaxCodeTmps() throws Exception {
        // Initialize the database
        pASaxCodeTmpRepository.saveAndFlush(pASaxCodeTmp);

        // Get all the pASaxCodeTmpList
        restPASaxCodeTmpMockMvc.perform(get("/api/p-a-sax-code-tmps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pASaxCodeTmp.getId().intValue())))
            .andExpect(jsonPath("$.[*].distalarm").value(hasItem(DEFAULT_DISTALARM.toString())))
            .andExpect(jsonPath("$.[*].saxcode").value(hasItem(DEFAULT_SAXCODE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.toString())))
            .andExpect(jsonPath("$.[*].painterval").value(hasItem(DEFAULT_PAINTERVAL.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPASaxCodeTmp() throws Exception {
        // Initialize the database
        pASaxCodeTmpRepository.saveAndFlush(pASaxCodeTmp);

        // Get the pASaxCodeTmp
        restPASaxCodeTmpMockMvc.perform(get("/api/p-a-sax-code-tmps/{id}", pASaxCodeTmp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pASaxCodeTmp.getId().intValue()))
            .andExpect(jsonPath("$.distalarm").value(DEFAULT_DISTALARM.toString()))
            .andExpect(jsonPath("$.saxcode").value(DEFAULT_SAXCODE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.toString()))
            .andExpect(jsonPath("$.painterval").value(DEFAULT_PAINTERVAL.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPASaxCodeTmp() throws Exception {
        // Get the pASaxCodeTmp
        restPASaxCodeTmpMockMvc.perform(get("/api/p-a-sax-code-tmps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePASaxCodeTmp() throws Exception {
        // Initialize the database
        pASaxCodeTmpService.save(pASaxCodeTmp);

        int databaseSizeBeforeUpdate = pASaxCodeTmpRepository.findAll().size();

        // Update the pASaxCodeTmp
        PASaxCodeTmp updatedPASaxCodeTmp = pASaxCodeTmpRepository.findOne(pASaxCodeTmp.getId());
        updatedPASaxCodeTmp
                .distalarm(UPDATED_DISTALARM)
                .saxcode(UPDATED_SAXCODE)
                .total(UPDATED_TOTAL)
                .painterval(UPDATED_PAINTERVAL)
                .severity(UPDATED_SEVERITY)
                .pastatus(UPDATED_PASTATUS);

        restPASaxCodeTmpMockMvc.perform(put("/api/p-a-sax-code-tmps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPASaxCodeTmp)))
            .andExpect(status().isOk());

        // Validate the PASaxCodeTmp in the database
        List<PASaxCodeTmp> pASaxCodeTmpList = pASaxCodeTmpRepository.findAll();
        assertThat(pASaxCodeTmpList).hasSize(databaseSizeBeforeUpdate);
        PASaxCodeTmp testPASaxCodeTmp = pASaxCodeTmpList.get(pASaxCodeTmpList.size() - 1);
        assertThat(testPASaxCodeTmp.getDistalarm()).isEqualTo(UPDATED_DISTALARM);
        assertThat(testPASaxCodeTmp.getSaxcode()).isEqualTo(UPDATED_SAXCODE);
        assertThat(testPASaxCodeTmp.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testPASaxCodeTmp.getPainterval()).isEqualTo(UPDATED_PAINTERVAL);
        assertThat(testPASaxCodeTmp.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testPASaxCodeTmp.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPASaxCodeTmp() throws Exception {
        int databaseSizeBeforeUpdate = pASaxCodeTmpRepository.findAll().size();

        // Create the PASaxCodeTmp

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPASaxCodeTmpMockMvc.perform(put("/api/p-a-sax-code-tmps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pASaxCodeTmp)))
            .andExpect(status().isCreated());

        // Validate the PASaxCodeTmp in the database
        List<PASaxCodeTmp> pASaxCodeTmpList = pASaxCodeTmpRepository.findAll();
        assertThat(pASaxCodeTmpList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePASaxCodeTmp() throws Exception {
        // Initialize the database
        pASaxCodeTmpService.save(pASaxCodeTmp);

        int databaseSizeBeforeDelete = pASaxCodeTmpRepository.findAll().size();

        // Get the pASaxCodeTmp
        restPASaxCodeTmpMockMvc.perform(delete("/api/p-a-sax-code-tmps/{id}", pASaxCodeTmp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PASaxCodeTmp> pASaxCodeTmpList = pASaxCodeTmpRepository.findAll();
        assertThat(pASaxCodeTmpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
