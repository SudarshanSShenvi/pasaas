package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAPredictionScore;
import com.pervazive.kheddah.repository.PAPredictionScoreRepository;
import com.pervazive.kheddah.service.PAPredictionScoreService;

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
import java.time.LocalDate;
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
 * Test class for the PAPredictionScoreResource REST controller.
 *
 * @see PAPredictionScoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAPredictionScoreResourceIntTest {

    private static final String DEFAULT_DIST = "AAAAAAAAAA";
    private static final String UPDATED_DIST = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALARMNO = 1;
    private static final Integer UPDATED_ALARMNO = 2;

    private static final Integer DEFAULT_BCOUNT = 1;
    private static final Integer UPDATED_BCOUNT = 2;

    private static final Integer DEFAULT_CCOUNT = 1;
    private static final Integer UPDATED_CCOUNT = 2;

    private static final Float DEFAULT_BSCORE = 1F;
    private static final Float UPDATED_BSCORE = 2F;

    private static final Float DEFAULT_CSCORE = 1F;
    private static final Float UPDATED_CSCORE = 2F;

    private static final ZonedDateTime DEFAULT_CREATEDON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_ZAXIS = 1;
    private static final Integer UPDATED_ZAXIS = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEVERITY = "AAAAAAAAAA";
    private static final String UPDATED_SEVERITY = "BBBBBBBBBB";

    private static final String DEFAULT_PAINTERVAL = "AAAAAAAAAA";
    private static final String UPDATED_PAINTERVAL = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAPredictionScoreRepository pAPredictionScoreRepository;

    @Inject
    private PAPredictionScoreService pAPredictionScoreService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAPredictionScoreMockMvc;

    private PAPredictionScore pAPredictionScore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAPredictionScoreResource pAPredictionScoreResource = new PAPredictionScoreResource();
        ReflectionTestUtils.setField(pAPredictionScoreResource, "pAPredictionScoreService", pAPredictionScoreService);
        this.restPAPredictionScoreMockMvc = MockMvcBuilders.standaloneSetup(pAPredictionScoreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAPredictionScore createEntity(EntityManager em) {
        PAPredictionScore pAPredictionScore = new PAPredictionScore()
                .dist(DEFAULT_DIST)
                .alarmno(DEFAULT_ALARMNO)
                .bcount(DEFAULT_BCOUNT)
                .ccount(DEFAULT_CCOUNT)
                .bscore(DEFAULT_BSCORE)
                .cscore(DEFAULT_CSCORE)
                .createdon(DEFAULT_CREATEDON)
                .zaxis(DEFAULT_ZAXIS)
                .date(DEFAULT_DATE)
                .severity(DEFAULT_SEVERITY)
                .painterval(DEFAULT_PAINTERVAL)
                .pastatus(DEFAULT_PASTATUS);
        return pAPredictionScore;
    }

    @Before
    public void initTest() {
        pAPredictionScore = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAPredictionScore() throws Exception {
        int databaseSizeBeforeCreate = pAPredictionScoreRepository.findAll().size();

        // Create the PAPredictionScore

        restPAPredictionScoreMockMvc.perform(post("/api/p-a-prediction-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAPredictionScore)))
            .andExpect(status().isCreated());

        // Validate the PAPredictionScore in the database
        List<PAPredictionScore> pAPredictionScoreList = pAPredictionScoreRepository.findAll();
        assertThat(pAPredictionScoreList).hasSize(databaseSizeBeforeCreate + 1);
        PAPredictionScore testPAPredictionScore = pAPredictionScoreList.get(pAPredictionScoreList.size() - 1);
        assertThat(testPAPredictionScore.getDist()).isEqualTo(DEFAULT_DIST);
        assertThat(testPAPredictionScore.getAlarmno()).isEqualTo(DEFAULT_ALARMNO);
        assertThat(testPAPredictionScore.getBcount()).isEqualTo(DEFAULT_BCOUNT);
        assertThat(testPAPredictionScore.getCcount()).isEqualTo(DEFAULT_CCOUNT);
        assertThat(testPAPredictionScore.getBscore()).isEqualTo(DEFAULT_BSCORE);
        assertThat(testPAPredictionScore.getCscore()).isEqualTo(DEFAULT_CSCORE);
        assertThat(testPAPredictionScore.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testPAPredictionScore.getZaxis()).isEqualTo(DEFAULT_ZAXIS);
        assertThat(testPAPredictionScore.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPAPredictionScore.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testPAPredictionScore.getPainterval()).isEqualTo(DEFAULT_PAINTERVAL);
        assertThat(testPAPredictionScore.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAPredictionScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAPredictionScoreRepository.findAll().size();

        // Create the PAPredictionScore with an existing ID
        PAPredictionScore existingPAPredictionScore = new PAPredictionScore();
        existingPAPredictionScore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAPredictionScoreMockMvc.perform(post("/api/p-a-prediction-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAPredictionScore)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAPredictionScore> pAPredictionScoreList = pAPredictionScoreRepository.findAll();
        assertThat(pAPredictionScoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAPredictionScores() throws Exception {
        // Initialize the database
        pAPredictionScoreRepository.saveAndFlush(pAPredictionScore);

        // Get all the pAPredictionScoreList
        restPAPredictionScoreMockMvc.perform(get("/api/p-a-prediction-scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAPredictionScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].dist").value(hasItem(DEFAULT_DIST.toString())))
            .andExpect(jsonPath("$.[*].alarmno").value(hasItem(DEFAULT_ALARMNO)))
            .andExpect(jsonPath("$.[*].bcount").value(hasItem(DEFAULT_BCOUNT)))
            .andExpect(jsonPath("$.[*].ccount").value(hasItem(DEFAULT_CCOUNT)))
            .andExpect(jsonPath("$.[*].bscore").value(hasItem(DEFAULT_BSCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].cscore").value(hasItem(DEFAULT_CSCORE)))
            .andExpect(jsonPath("$.[*].createdon").value(hasItem(sameInstant(DEFAULT_CREATEDON))))
            .andExpect(jsonPath("$.[*].zaxis").value(hasItem(DEFAULT_ZAXIS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].painterval").value(hasItem(DEFAULT_PAINTERVAL.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAPredictionScore() throws Exception {
        // Initialize the database
        pAPredictionScoreRepository.saveAndFlush(pAPredictionScore);

        // Get the pAPredictionScore
        restPAPredictionScoreMockMvc.perform(get("/api/p-a-prediction-scores/{id}", pAPredictionScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAPredictionScore.getId().intValue()))
            .andExpect(jsonPath("$.dist").value(DEFAULT_DIST.toString()))
            .andExpect(jsonPath("$.alarmno").value(DEFAULT_ALARMNO))
            .andExpect(jsonPath("$.bcount").value(DEFAULT_BCOUNT))
            .andExpect(jsonPath("$.ccount").value(DEFAULT_CCOUNT))
            .andExpect(jsonPath("$.bscore").value(DEFAULT_BSCORE.doubleValue()))
            .andExpect(jsonPath("$.cscore").value(DEFAULT_CSCORE))
            .andExpect(jsonPath("$.createdon").value(sameInstant(DEFAULT_CREATEDON)))
            .andExpect(jsonPath("$.zaxis").value(DEFAULT_ZAXIS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.painterval").value(DEFAULT_PAINTERVAL.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAPredictionScore() throws Exception {
        // Get the pAPredictionScore
        restPAPredictionScoreMockMvc.perform(get("/api/p-a-prediction-scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAPredictionScore() throws Exception {
        // Initialize the database
        pAPredictionScoreService.save(pAPredictionScore);

        int databaseSizeBeforeUpdate = pAPredictionScoreRepository.findAll().size();

        // Update the pAPredictionScore
        PAPredictionScore updatedPAPredictionScore = pAPredictionScoreRepository.findOne(pAPredictionScore.getId());
        updatedPAPredictionScore
                .dist(UPDATED_DIST)
                .alarmno(UPDATED_ALARMNO)
                .bcount(UPDATED_BCOUNT)
                .ccount(UPDATED_CCOUNT)
                .bscore(UPDATED_BSCORE)
                .cscore(UPDATED_CSCORE)
                .createdon(UPDATED_CREATEDON)
                .zaxis(UPDATED_ZAXIS)
                .date(UPDATED_DATE)
                .severity(UPDATED_SEVERITY)
                .painterval(UPDATED_PAINTERVAL)
                .pastatus(UPDATED_PASTATUS);

        restPAPredictionScoreMockMvc.perform(put("/api/p-a-prediction-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAPredictionScore)))
            .andExpect(status().isOk());

        // Validate the PAPredictionScore in the database
        List<PAPredictionScore> pAPredictionScoreList = pAPredictionScoreRepository.findAll();
        assertThat(pAPredictionScoreList).hasSize(databaseSizeBeforeUpdate);
        PAPredictionScore testPAPredictionScore = pAPredictionScoreList.get(pAPredictionScoreList.size() - 1);
        assertThat(testPAPredictionScore.getDist()).isEqualTo(UPDATED_DIST);
        assertThat(testPAPredictionScore.getAlarmno()).isEqualTo(UPDATED_ALARMNO);
        assertThat(testPAPredictionScore.getBcount()).isEqualTo(UPDATED_BCOUNT);
        assertThat(testPAPredictionScore.getCcount()).isEqualTo(UPDATED_CCOUNT);
        assertThat(testPAPredictionScore.getBscore()).isEqualTo(UPDATED_BSCORE);
        assertThat(testPAPredictionScore.getCscore()).isEqualTo(UPDATED_CSCORE);
        assertThat(testPAPredictionScore.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testPAPredictionScore.getZaxis()).isEqualTo(UPDATED_ZAXIS);
        assertThat(testPAPredictionScore.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPAPredictionScore.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testPAPredictionScore.getPainterval()).isEqualTo(UPDATED_PAINTERVAL);
        assertThat(testPAPredictionScore.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAPredictionScore() throws Exception {
        int databaseSizeBeforeUpdate = pAPredictionScoreRepository.findAll().size();

        // Create the PAPredictionScore

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAPredictionScoreMockMvc.perform(put("/api/p-a-prediction-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAPredictionScore)))
            .andExpect(status().isCreated());

        // Validate the PAPredictionScore in the database
        List<PAPredictionScore> pAPredictionScoreList = pAPredictionScoreRepository.findAll();
        assertThat(pAPredictionScoreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAPredictionScore() throws Exception {
        // Initialize the database
        pAPredictionScoreService.save(pAPredictionScore);

        int databaseSizeBeforeDelete = pAPredictionScoreRepository.findAll().size();

        // Get the pAPredictionScore
        restPAPredictionScoreMockMvc.perform(delete("/api/p-a-prediction-scores/{id}", pAPredictionScore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAPredictionScore> pAPredictionScoreList = pAPredictionScoreRepository.findAll();
        assertThat(pAPredictionScoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
