package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAAccPrecision;
import com.pervazive.kheddah.repository.PAAccPrecisionRepository;
import com.pervazive.kheddah.service.PAAccPrecisionService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.pervazive.kheddah.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pervazive.kheddah.domain.enumeration.PAStatus;
/**
 * Test class for the PAAccPrecisionResource REST controller.
 *
 * @see PAAccPrecisionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAAccPrecisionResourceIntTest {

    private static final Integer DEFAULT_TOTALPREDICTIONS = 1;
    private static final Integer UPDATED_TOTALPREDICTIONS = 2;

    private static final Integer DEFAULT_FAILPREDICTIONS = 1;
    private static final Integer UPDATED_FAILPREDICTIONS = 2;

    private static final Integer DEFAULT_NOFAILPREDICTIONS = 1;
    private static final Integer UPDATED_NOFAILPREDICTIONS = 2;

    private static final Integer DEFAULT_TOTALEVENTS = 1;
    private static final Integer UPDATED_TOTALEVENTS = 2;

    private static final Integer DEFAULT_PFAILMATCHED = 1;
    private static final Integer UPDATED_PFAILMATCHED = 2;

    private static final Integer DEFAULT_PNOFAILMATCHED = 1;
    private static final Integer UPDATED_PNOFAILMATCHED = 2;

    private static final ZonedDateTime DEFAULT_PREDICTIONDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PREDICTIONDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_ACCURACYVAL = 1;
    private static final Integer UPDATED_ACCURACYVAL = 2;

    private static final Integer DEFAULT_PRCISIONVAL = 1;
    private static final Integer UPDATED_PRCISIONVAL = 2;

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAAccPrecisionRepository pAAccPrecisionRepository;

    @Inject
    private PAAccPrecisionService pAAccPrecisionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAAccPrecisionMockMvc;

    private PAAccPrecision pAAccPrecision;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAAccPrecisionResource pAAccPrecisionResource = new PAAccPrecisionResource();
        ReflectionTestUtils.setField(pAAccPrecisionResource, "pAAccPrecisionService", pAAccPrecisionService);
        this.restPAAccPrecisionMockMvc = MockMvcBuilders.standaloneSetup(pAAccPrecisionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAAccPrecision createEntity(EntityManager em) {
        PAAccPrecision pAAccPrecision = new PAAccPrecision()
                .totalpredictions(DEFAULT_TOTALPREDICTIONS)
                .failpredictions(DEFAULT_FAILPREDICTIONS)
                .nofailpredictions(DEFAULT_NOFAILPREDICTIONS)
                .totalevents(DEFAULT_TOTALEVENTS)
                .pfailmatched(DEFAULT_PFAILMATCHED)
                .pnofailmatched(DEFAULT_PNOFAILMATCHED)
                .predictiondate(DEFAULT_PREDICTIONDATE)
                .accuracyval(DEFAULT_ACCURACYVAL)
                .prcisionval(DEFAULT_PRCISIONVAL)
                .pastatus(DEFAULT_PASTATUS);
        return pAAccPrecision;
    }

    @Before
    public void initTest() {
        pAAccPrecision = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAAccPrecision() throws Exception {
        int databaseSizeBeforeCreate = pAAccPrecisionRepository.findAll().size();

        // Create the PAAccPrecision

        restPAAccPrecisionMockMvc.perform(post("/api/p-a-acc-precisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAAccPrecision)))
            .andExpect(status().isCreated());

        // Validate the PAAccPrecision in the database
        List<PAAccPrecision> pAAccPrecisionList = pAAccPrecisionRepository.findAll();
        assertThat(pAAccPrecisionList).hasSize(databaseSizeBeforeCreate + 1);
        PAAccPrecision testPAAccPrecision = pAAccPrecisionList.get(pAAccPrecisionList.size() - 1);
        assertThat(testPAAccPrecision.getTotalpredictions()).isEqualTo(DEFAULT_TOTALPREDICTIONS);
        assertThat(testPAAccPrecision.getFailpredictions()).isEqualTo(DEFAULT_FAILPREDICTIONS);
        assertThat(testPAAccPrecision.getNofailpredictions()).isEqualTo(DEFAULT_NOFAILPREDICTIONS);
        assertThat(testPAAccPrecision.getTotalevents()).isEqualTo(DEFAULT_TOTALEVENTS);
        assertThat(testPAAccPrecision.getPfailmatched()).isEqualTo(DEFAULT_PFAILMATCHED);
        assertThat(testPAAccPrecision.getPnofailmatched()).isEqualTo(DEFAULT_PNOFAILMATCHED);
        assertThat(testPAAccPrecision.getPredictiondate()).isEqualTo(DEFAULT_PREDICTIONDATE);
        assertThat(testPAAccPrecision.getAccuracyval()).isEqualTo(DEFAULT_ACCURACYVAL);
        assertThat(testPAAccPrecision.getPrcisionval()).isEqualTo(DEFAULT_PRCISIONVAL);
        assertThat(testPAAccPrecision.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAAccPrecisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAAccPrecisionRepository.findAll().size();

        // Create the PAAccPrecision with an existing ID
        PAAccPrecision existingPAAccPrecision = new PAAccPrecision();
        existingPAAccPrecision.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAAccPrecisionMockMvc.perform(post("/api/p-a-acc-precisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAAccPrecision)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAAccPrecision> pAAccPrecisionList = pAAccPrecisionRepository.findAll();
        assertThat(pAAccPrecisionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAAccPrecisions() throws Exception {
        // Initialize the database
        pAAccPrecisionRepository.saveAndFlush(pAAccPrecision);

        // Get all the pAAccPrecisionList
        restPAAccPrecisionMockMvc.perform(get("/api/p-a-acc-precisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAAccPrecision.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalpredictions").value(hasItem(DEFAULT_TOTALPREDICTIONS)))
            .andExpect(jsonPath("$.[*].failpredictions").value(hasItem(DEFAULT_FAILPREDICTIONS)))
            .andExpect(jsonPath("$.[*].nofailpredictions").value(hasItem(DEFAULT_NOFAILPREDICTIONS)))
            .andExpect(jsonPath("$.[*].totalevents").value(hasItem(DEFAULT_TOTALEVENTS)))
            .andExpect(jsonPath("$.[*].pfailmatched").value(hasItem(DEFAULT_PFAILMATCHED)))
            .andExpect(jsonPath("$.[*].pnofailmatched").value(hasItem(DEFAULT_PNOFAILMATCHED)))
            .andExpect(jsonPath("$.[*].predictiondate").value(hasItem(sameInstant(DEFAULT_PREDICTIONDATE))))
            .andExpect(jsonPath("$.[*].accuracyval").value(hasItem(DEFAULT_ACCURACYVAL)))
            .andExpect(jsonPath("$.[*].prcisionval").value(hasItem(DEFAULT_PRCISIONVAL)))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAAccPrecision() throws Exception {
        // Initialize the database
        pAAccPrecisionRepository.saveAndFlush(pAAccPrecision);

        // Get the pAAccPrecision
        restPAAccPrecisionMockMvc.perform(get("/api/p-a-acc-precisions/{id}", pAAccPrecision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAAccPrecision.getId().intValue()))
            .andExpect(jsonPath("$.totalpredictions").value(DEFAULT_TOTALPREDICTIONS))
            .andExpect(jsonPath("$.failpredictions").value(DEFAULT_FAILPREDICTIONS))
            .andExpect(jsonPath("$.nofailpredictions").value(DEFAULT_NOFAILPREDICTIONS))
            .andExpect(jsonPath("$.totalevents").value(DEFAULT_TOTALEVENTS))
            .andExpect(jsonPath("$.pfailmatched").value(DEFAULT_PFAILMATCHED))
            .andExpect(jsonPath("$.pnofailmatched").value(DEFAULT_PNOFAILMATCHED))
            .andExpect(jsonPath("$.predictiondate").value(sameInstant(DEFAULT_PREDICTIONDATE)))
            .andExpect(jsonPath("$.accuracyval").value(DEFAULT_ACCURACYVAL))
            .andExpect(jsonPath("$.prcisionval").value(DEFAULT_PRCISIONVAL))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAAccPrecision() throws Exception {
        // Get the pAAccPrecision
        restPAAccPrecisionMockMvc.perform(get("/api/p-a-acc-precisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAAccPrecision() throws Exception {
        // Initialize the database
        pAAccPrecisionService.save(pAAccPrecision);

        int databaseSizeBeforeUpdate = pAAccPrecisionRepository.findAll().size();

        // Update the pAAccPrecision
        PAAccPrecision updatedPAAccPrecision = pAAccPrecisionRepository.findOne(pAAccPrecision.getId());
        updatedPAAccPrecision
                .totalpredictions(UPDATED_TOTALPREDICTIONS)
                .failpredictions(UPDATED_FAILPREDICTIONS)
                .nofailpredictions(UPDATED_NOFAILPREDICTIONS)
                .totalevents(UPDATED_TOTALEVENTS)
                .pfailmatched(UPDATED_PFAILMATCHED)
                .pnofailmatched(UPDATED_PNOFAILMATCHED)
                .predictiondate(UPDATED_PREDICTIONDATE)
                .accuracyval(UPDATED_ACCURACYVAL)
                .prcisionval(UPDATED_PRCISIONVAL)
                .pastatus(UPDATED_PASTATUS);

        restPAAccPrecisionMockMvc.perform(put("/api/p-a-acc-precisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAAccPrecision)))
            .andExpect(status().isOk());

        // Validate the PAAccPrecision in the database
        List<PAAccPrecision> pAAccPrecisionList = pAAccPrecisionRepository.findAll();
        assertThat(pAAccPrecisionList).hasSize(databaseSizeBeforeUpdate);
        PAAccPrecision testPAAccPrecision = pAAccPrecisionList.get(pAAccPrecisionList.size() - 1);
        assertThat(testPAAccPrecision.getTotalpredictions()).isEqualTo(UPDATED_TOTALPREDICTIONS);
        assertThat(testPAAccPrecision.getFailpredictions()).isEqualTo(UPDATED_FAILPREDICTIONS);
        assertThat(testPAAccPrecision.getNofailpredictions()).isEqualTo(UPDATED_NOFAILPREDICTIONS);
        assertThat(testPAAccPrecision.getTotalevents()).isEqualTo(UPDATED_TOTALEVENTS);
        assertThat(testPAAccPrecision.getPfailmatched()).isEqualTo(UPDATED_PFAILMATCHED);
        assertThat(testPAAccPrecision.getPnofailmatched()).isEqualTo(UPDATED_PNOFAILMATCHED);
        assertThat(testPAAccPrecision.getPredictiondate()).isEqualTo(UPDATED_PREDICTIONDATE);
        assertThat(testPAAccPrecision.getAccuracyval()).isEqualTo(UPDATED_ACCURACYVAL);
        assertThat(testPAAccPrecision.getPrcisionval()).isEqualTo(UPDATED_PRCISIONVAL);
        assertThat(testPAAccPrecision.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAAccPrecision() throws Exception {
        int databaseSizeBeforeUpdate = pAAccPrecisionRepository.findAll().size();

        // Create the PAAccPrecision

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAAccPrecisionMockMvc.perform(put("/api/p-a-acc-precisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAAccPrecision)))
            .andExpect(status().isCreated());

        // Validate the PAAccPrecision in the database
        List<PAAccPrecision> pAAccPrecisionList = pAAccPrecisionRepository.findAll();
        assertThat(pAAccPrecisionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAAccPrecision() throws Exception {
        // Initialize the database
        pAAccPrecisionService.save(pAAccPrecision);

        int databaseSizeBeforeDelete = pAAccPrecisionRepository.findAll().size();

        // Get the pAAccPrecision
        restPAAccPrecisionMockMvc.perform(delete("/api/p-a-acc-precisions/{id}", pAAccPrecision.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAAccPrecision> pAAccPrecisionList = pAAccPrecisionRepository.findAll();
        assertThat(pAAccPrecisionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
