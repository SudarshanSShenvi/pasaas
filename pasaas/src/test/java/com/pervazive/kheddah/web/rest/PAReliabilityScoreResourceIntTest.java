package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAReliabilityScore;
import com.pervazive.kheddah.repository.PAReliabilityScoreRepository;
import com.pervazive.kheddah.service.PAReliabilityScoreService;

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
 * Test class for the PAReliabilityScoreResource REST controller.
 *
 * @see PAReliabilityScoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAReliabilityScoreResourceIntTest {

    private static final Integer DEFAULT_PREDICTEDALARMS = 1;
    private static final Integer UPDATED_PREDICTEDALARMS = 2;

    private static final Integer DEFAULT_OCCUREDALARMS = 1;
    private static final Integer UPDATED_OCCUREDALARMS = 2;

    private static final Integer DEFAULT_MATCHEDALARMS = 1;
    private static final Integer UPDATED_MATCHEDALARMS = 2;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_PREDICTEDPERCENTAGE = 1F;
    private static final Float UPDATED_PREDICTEDPERCENTAGE = 2F;

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAReliabilityScoreRepository pAReliabilityScoreRepository;

    @Inject
    private PAReliabilityScoreService pAReliabilityScoreService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAReliabilityScoreMockMvc;

    private PAReliabilityScore pAReliabilityScore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAReliabilityScoreResource pAReliabilityScoreResource = new PAReliabilityScoreResource();
        ReflectionTestUtils.setField(pAReliabilityScoreResource, "pAReliabilityScoreService", pAReliabilityScoreService);
        this.restPAReliabilityScoreMockMvc = MockMvcBuilders.standaloneSetup(pAReliabilityScoreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAReliabilityScore createEntity(EntityManager em) {
        PAReliabilityScore pAReliabilityScore = new PAReliabilityScore()
                .predictedalarms(DEFAULT_PREDICTEDALARMS)
                .occuredalarms(DEFAULT_OCCUREDALARMS)
                .matchedalarms(DEFAULT_MATCHEDALARMS)
                .created(DEFAULT_CREATED)
                .predictedpercentage(DEFAULT_PREDICTEDPERCENTAGE)
                .pastatus(DEFAULT_PASTATUS);
        return pAReliabilityScore;
    }

    @Before
    public void initTest() {
        pAReliabilityScore = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAReliabilityScore() throws Exception {
        int databaseSizeBeforeCreate = pAReliabilityScoreRepository.findAll().size();

        // Create the PAReliabilityScore

        restPAReliabilityScoreMockMvc.perform(post("/api/p-a-reliability-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAReliabilityScore)))
            .andExpect(status().isCreated());

        // Validate the PAReliabilityScore in the database
        List<PAReliabilityScore> pAReliabilityScoreList = pAReliabilityScoreRepository.findAll();
        assertThat(pAReliabilityScoreList).hasSize(databaseSizeBeforeCreate + 1);
        PAReliabilityScore testPAReliabilityScore = pAReliabilityScoreList.get(pAReliabilityScoreList.size() - 1);
        assertThat(testPAReliabilityScore.getPredictedalarms()).isEqualTo(DEFAULT_PREDICTEDALARMS);
        assertThat(testPAReliabilityScore.getOccuredalarms()).isEqualTo(DEFAULT_OCCUREDALARMS);
        assertThat(testPAReliabilityScore.getMatchedalarms()).isEqualTo(DEFAULT_MATCHEDALARMS);
        assertThat(testPAReliabilityScore.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPAReliabilityScore.getPredictedpercentage()).isEqualTo(DEFAULT_PREDICTEDPERCENTAGE);
        assertThat(testPAReliabilityScore.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAReliabilityScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAReliabilityScoreRepository.findAll().size();

        // Create the PAReliabilityScore with an existing ID
        PAReliabilityScore existingPAReliabilityScore = new PAReliabilityScore();
        existingPAReliabilityScore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAReliabilityScoreMockMvc.perform(post("/api/p-a-reliability-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAReliabilityScore)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAReliabilityScore> pAReliabilityScoreList = pAReliabilityScoreRepository.findAll();
        assertThat(pAReliabilityScoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAReliabilityScores() throws Exception {
        // Initialize the database
        pAReliabilityScoreRepository.saveAndFlush(pAReliabilityScore);

        // Get all the pAReliabilityScoreList
        restPAReliabilityScoreMockMvc.perform(get("/api/p-a-reliability-scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAReliabilityScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].predictedalarms").value(hasItem(DEFAULT_PREDICTEDALARMS)))
            .andExpect(jsonPath("$.[*].occuredalarms").value(hasItem(DEFAULT_OCCUREDALARMS)))
            .andExpect(jsonPath("$.[*].matchedalarms").value(hasItem(DEFAULT_MATCHEDALARMS)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].predictedpercentage").value(hasItem(DEFAULT_PREDICTEDPERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAReliabilityScore() throws Exception {
        // Initialize the database
        pAReliabilityScoreRepository.saveAndFlush(pAReliabilityScore);

        // Get the pAReliabilityScore
        restPAReliabilityScoreMockMvc.perform(get("/api/p-a-reliability-scores/{id}", pAReliabilityScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAReliabilityScore.getId().intValue()))
            .andExpect(jsonPath("$.predictedalarms").value(DEFAULT_PREDICTEDALARMS))
            .andExpect(jsonPath("$.occuredalarms").value(DEFAULT_OCCUREDALARMS))
            .andExpect(jsonPath("$.matchedalarms").value(DEFAULT_MATCHEDALARMS))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.predictedpercentage").value(DEFAULT_PREDICTEDPERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAReliabilityScore() throws Exception {
        // Get the pAReliabilityScore
        restPAReliabilityScoreMockMvc.perform(get("/api/p-a-reliability-scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAReliabilityScore() throws Exception {
        // Initialize the database
        pAReliabilityScoreService.save(pAReliabilityScore);

        int databaseSizeBeforeUpdate = pAReliabilityScoreRepository.findAll().size();

        // Update the pAReliabilityScore
        PAReliabilityScore updatedPAReliabilityScore = pAReliabilityScoreRepository.findOne(pAReliabilityScore.getId());
        updatedPAReliabilityScore
                .predictedalarms(UPDATED_PREDICTEDALARMS)
                .occuredalarms(UPDATED_OCCUREDALARMS)
                .matchedalarms(UPDATED_MATCHEDALARMS)
                .created(UPDATED_CREATED)
                .predictedpercentage(UPDATED_PREDICTEDPERCENTAGE)
                .pastatus(UPDATED_PASTATUS);

        restPAReliabilityScoreMockMvc.perform(put("/api/p-a-reliability-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAReliabilityScore)))
            .andExpect(status().isOk());

        // Validate the PAReliabilityScore in the database
        List<PAReliabilityScore> pAReliabilityScoreList = pAReliabilityScoreRepository.findAll();
        assertThat(pAReliabilityScoreList).hasSize(databaseSizeBeforeUpdate);
        PAReliabilityScore testPAReliabilityScore = pAReliabilityScoreList.get(pAReliabilityScoreList.size() - 1);
        assertThat(testPAReliabilityScore.getPredictedalarms()).isEqualTo(UPDATED_PREDICTEDALARMS);
        assertThat(testPAReliabilityScore.getOccuredalarms()).isEqualTo(UPDATED_OCCUREDALARMS);
        assertThat(testPAReliabilityScore.getMatchedalarms()).isEqualTo(UPDATED_MATCHEDALARMS);
        assertThat(testPAReliabilityScore.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPAReliabilityScore.getPredictedpercentage()).isEqualTo(UPDATED_PREDICTEDPERCENTAGE);
        assertThat(testPAReliabilityScore.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAReliabilityScore() throws Exception {
        int databaseSizeBeforeUpdate = pAReliabilityScoreRepository.findAll().size();

        // Create the PAReliabilityScore

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAReliabilityScoreMockMvc.perform(put("/api/p-a-reliability-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAReliabilityScore)))
            .andExpect(status().isCreated());

        // Validate the PAReliabilityScore in the database
        List<PAReliabilityScore> pAReliabilityScoreList = pAReliabilityScoreRepository.findAll();
        assertThat(pAReliabilityScoreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAReliabilityScore() throws Exception {
        // Initialize the database
        pAReliabilityScoreService.save(pAReliabilityScore);

        int databaseSizeBeforeDelete = pAReliabilityScoreRepository.findAll().size();

        // Get the pAReliabilityScore
        restPAReliabilityScoreMockMvc.perform(delete("/api/p-a-reliability-scores/{id}", pAReliabilityScore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAReliabilityScore> pAReliabilityScoreList = pAReliabilityScoreRepository.findAll();
        assertThat(pAReliabilityScoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
