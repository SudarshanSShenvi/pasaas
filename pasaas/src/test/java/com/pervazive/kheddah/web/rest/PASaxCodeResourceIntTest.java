package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PASaxCode;
import com.pervazive.kheddah.repository.PASaxCodeRepository;
import com.pervazive.kheddah.service.PASaxCodeService;

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
 * Test class for the PASaxCodeResource REST controller.
 *
 * @see PASaxCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PASaxCodeResourceIntTest {

    private static final String DEFAULT_DISTALARM = "AAAAAAAAAA";
    private static final String UPDATED_DISTALARM = "BBBBBBBBBB";

    private static final String DEFAULT_SAXCODE = "AAAAAAAAAA";
    private static final String UPDATED_SAXCODE = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL = "BBBBBBBBBB";

    private static final String DEFAULT_PAINTERVAL = "AAAAAAAAAA";
    private static final String UPDATED_PAINTERVAL = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PASaxCodeRepository pASaxCodeRepository;

    @Inject
    private PASaxCodeService pASaxCodeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPASaxCodeMockMvc;

    private PASaxCode pASaxCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PASaxCodeResource pASaxCodeResource = new PASaxCodeResource();
        ReflectionTestUtils.setField(pASaxCodeResource, "pASaxCodeService", pASaxCodeService);
        this.restPASaxCodeMockMvc = MockMvcBuilders.standaloneSetup(pASaxCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PASaxCode createEntity(EntityManager em) {
        PASaxCode pASaxCode = new PASaxCode()
                .distalarm(DEFAULT_DISTALARM)
                .saxcode(DEFAULT_SAXCODE)
                .total(DEFAULT_TOTAL)
                .painterval(DEFAULT_PAINTERVAL)
                .pastatus(DEFAULT_PASTATUS);
        return pASaxCode;
    }

    @Before
    public void initTest() {
        pASaxCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createPASaxCode() throws Exception {
        int databaseSizeBeforeCreate = pASaxCodeRepository.findAll().size();

        // Create the PASaxCode

        restPASaxCodeMockMvc.perform(post("/api/p-a-sax-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pASaxCode)))
            .andExpect(status().isCreated());

        // Validate the PASaxCode in the database
        List<PASaxCode> pASaxCodeList = pASaxCodeRepository.findAll();
        assertThat(pASaxCodeList).hasSize(databaseSizeBeforeCreate + 1);
        PASaxCode testPASaxCode = pASaxCodeList.get(pASaxCodeList.size() - 1);
        assertThat(testPASaxCode.getDistalarm()).isEqualTo(DEFAULT_DISTALARM);
        assertThat(testPASaxCode.getSaxcode()).isEqualTo(DEFAULT_SAXCODE);
        assertThat(testPASaxCode.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testPASaxCode.getPainterval()).isEqualTo(DEFAULT_PAINTERVAL);
        assertThat(testPASaxCode.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPASaxCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pASaxCodeRepository.findAll().size();

        // Create the PASaxCode with an existing ID
        PASaxCode existingPASaxCode = new PASaxCode();
        existingPASaxCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPASaxCodeMockMvc.perform(post("/api/p-a-sax-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPASaxCode)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PASaxCode> pASaxCodeList = pASaxCodeRepository.findAll();
        assertThat(pASaxCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPASaxCodes() throws Exception {
        // Initialize the database
        pASaxCodeRepository.saveAndFlush(pASaxCode);

        // Get all the pASaxCodeList
        restPASaxCodeMockMvc.perform(get("/api/p-a-sax-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pASaxCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].distalarm").value(hasItem(DEFAULT_DISTALARM.toString())))
            .andExpect(jsonPath("$.[*].saxcode").value(hasItem(DEFAULT_SAXCODE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.toString())))
            .andExpect(jsonPath("$.[*].painterval").value(hasItem(DEFAULT_PAINTERVAL.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPASaxCode() throws Exception {
        // Initialize the database
        pASaxCodeRepository.saveAndFlush(pASaxCode);

        // Get the pASaxCode
        restPASaxCodeMockMvc.perform(get("/api/p-a-sax-codes/{id}", pASaxCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pASaxCode.getId().intValue()))
            .andExpect(jsonPath("$.distalarm").value(DEFAULT_DISTALARM.toString()))
            .andExpect(jsonPath("$.saxcode").value(DEFAULT_SAXCODE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.toString()))
            .andExpect(jsonPath("$.painterval").value(DEFAULT_PAINTERVAL.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPASaxCode() throws Exception {
        // Get the pASaxCode
        restPASaxCodeMockMvc.perform(get("/api/p-a-sax-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePASaxCode() throws Exception {
        // Initialize the database
        pASaxCodeService.save(pASaxCode);

        int databaseSizeBeforeUpdate = pASaxCodeRepository.findAll().size();

        // Update the pASaxCode
        PASaxCode updatedPASaxCode = pASaxCodeRepository.findOne(pASaxCode.getId());
        updatedPASaxCode
                .distalarm(UPDATED_DISTALARM)
                .saxcode(UPDATED_SAXCODE)
                .total(UPDATED_TOTAL)
                .painterval(UPDATED_PAINTERVAL)
                .pastatus(UPDATED_PASTATUS);

        restPASaxCodeMockMvc.perform(put("/api/p-a-sax-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPASaxCode)))
            .andExpect(status().isOk());

        // Validate the PASaxCode in the database
        List<PASaxCode> pASaxCodeList = pASaxCodeRepository.findAll();
        assertThat(pASaxCodeList).hasSize(databaseSizeBeforeUpdate);
        PASaxCode testPASaxCode = pASaxCodeList.get(pASaxCodeList.size() - 1);
        assertThat(testPASaxCode.getDistalarm()).isEqualTo(UPDATED_DISTALARM);
        assertThat(testPASaxCode.getSaxcode()).isEqualTo(UPDATED_SAXCODE);
        assertThat(testPASaxCode.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testPASaxCode.getPainterval()).isEqualTo(UPDATED_PAINTERVAL);
        assertThat(testPASaxCode.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPASaxCode() throws Exception {
        int databaseSizeBeforeUpdate = pASaxCodeRepository.findAll().size();

        // Create the PASaxCode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPASaxCodeMockMvc.perform(put("/api/p-a-sax-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pASaxCode)))
            .andExpect(status().isCreated());

        // Validate the PASaxCode in the database
        List<PASaxCode> pASaxCodeList = pASaxCodeRepository.findAll();
        assertThat(pASaxCodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePASaxCode() throws Exception {
        // Initialize the database
        pASaxCodeService.save(pASaxCode);

        int databaseSizeBeforeDelete = pASaxCodeRepository.findAll().size();

        // Get the pASaxCode
        restPASaxCodeMockMvc.perform(delete("/api/p-a-sax-codes/{id}", pASaxCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PASaxCode> pASaxCodeList = pASaxCodeRepository.findAll();
        assertThat(pASaxCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
