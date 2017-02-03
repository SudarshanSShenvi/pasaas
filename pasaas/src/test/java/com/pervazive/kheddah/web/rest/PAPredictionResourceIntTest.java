package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAPrediction;
import com.pervazive.kheddah.repository.PAPredictionRepository;
import com.pervazive.kheddah.service.PAPredictionService;

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
 * Test class for the PAPredictionResource REST controller.
 *
 * @see PAPredictionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAPredictionResourceIntTest {

    private static final ZonedDateTime DEFAULT_PREDICTIONDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PREDICTIONDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NENAME = "AAAAAAAAAA";
    private static final String UPDATED_NENAME = "BBBBBBBBBB";

    private static final String DEFAULT_FAULTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_FAULTTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SEVERITY = "AAAAAAAAAA";
    private static final String UPDATED_SEVERITY = "BBBBBBBBBB";

    private static final String DEFAULT_SITEID = "AAAAAAAAAA";
    private static final String UPDATED_SITEID = "BBBBBBBBBB";

    private static final String DEFAULT_SITEPRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_SITEPRIORITY = "BBBBBBBBBB";

    private static final String DEFAULT_TOTALPREDICTION = "AAAAAAAAAA";
    private static final String UPDATED_TOTALPREDICTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_FAILCOUNT = 1;
    private static final Integer UPDATED_FAILCOUNT = 2;

    private static final Integer DEFAULT_NOFAILCOUNT = 1;
    private static final Integer UPDATED_NOFAILCOUNT = 2;

    private static final Float DEFAULT_FAILPROB = 1F;
    private static final Float UPDATED_FAILPROB = 2F;

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAPredictionRepository pAPredictionRepository;

    @Inject
    private PAPredictionService pAPredictionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAPredictionMockMvc;

    private PAPrediction pAPrediction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAPredictionResource pAPredictionResource = new PAPredictionResource();
        ReflectionTestUtils.setField(pAPredictionResource, "pAPredictionService", pAPredictionService);
        this.restPAPredictionMockMvc = MockMvcBuilders.standaloneSetup(pAPredictionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAPrediction createEntity(EntityManager em) {
        PAPrediction pAPrediction = new PAPrediction()
                .predictiondate(DEFAULT_PREDICTIONDATE)
                .nename(DEFAULT_NENAME)
                .faulttype(DEFAULT_FAULTTYPE)
                .severity(DEFAULT_SEVERITY)
                .siteid(DEFAULT_SITEID)
                .sitepriority(DEFAULT_SITEPRIORITY)
                .totalprediction(DEFAULT_TOTALPREDICTION)
                .failcount(DEFAULT_FAILCOUNT)
                .nofailcount(DEFAULT_NOFAILCOUNT)
                .failprob(DEFAULT_FAILPROB)
                .pastatus(DEFAULT_PASTATUS);
        return pAPrediction;
    }

    @Before
    public void initTest() {
        pAPrediction = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAPrediction() throws Exception {
        int databaseSizeBeforeCreate = pAPredictionRepository.findAll().size();

        // Create the PAPrediction

        restPAPredictionMockMvc.perform(post("/api/p-a-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAPrediction)))
            .andExpect(status().isCreated());

        // Validate the PAPrediction in the database
        List<PAPrediction> pAPredictionList = pAPredictionRepository.findAll();
        assertThat(pAPredictionList).hasSize(databaseSizeBeforeCreate + 1);
        PAPrediction testPAPrediction = pAPredictionList.get(pAPredictionList.size() - 1);
        assertThat(testPAPrediction.getPredictiondate()).isEqualTo(DEFAULT_PREDICTIONDATE);
        assertThat(testPAPrediction.getNename()).isEqualTo(DEFAULT_NENAME);
        assertThat(testPAPrediction.getFaulttype()).isEqualTo(DEFAULT_FAULTTYPE);
        assertThat(testPAPrediction.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testPAPrediction.getSiteid()).isEqualTo(DEFAULT_SITEID);
        assertThat(testPAPrediction.getSitepriority()).isEqualTo(DEFAULT_SITEPRIORITY);
        assertThat(testPAPrediction.getTotalprediction()).isEqualTo(DEFAULT_TOTALPREDICTION);
        assertThat(testPAPrediction.getFailcount()).isEqualTo(DEFAULT_FAILCOUNT);
        assertThat(testPAPrediction.getNofailcount()).isEqualTo(DEFAULT_NOFAILCOUNT);
        assertThat(testPAPrediction.getFailprob()).isEqualTo(DEFAULT_FAILPROB);
        assertThat(testPAPrediction.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAPredictionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAPredictionRepository.findAll().size();

        // Create the PAPrediction with an existing ID
        PAPrediction existingPAPrediction = new PAPrediction();
        existingPAPrediction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAPredictionMockMvc.perform(post("/api/p-a-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAPrediction)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAPrediction> pAPredictionList = pAPredictionRepository.findAll();
        assertThat(pAPredictionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAPredictions() throws Exception {
        // Initialize the database
        pAPredictionRepository.saveAndFlush(pAPrediction);

        // Get all the pAPredictionList
        restPAPredictionMockMvc.perform(get("/api/p-a-predictions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAPrediction.getId().intValue())))
            .andExpect(jsonPath("$.[*].predictiondate").value(hasItem(sameInstant(DEFAULT_PREDICTIONDATE))))
            .andExpect(jsonPath("$.[*].nename").value(hasItem(DEFAULT_NENAME.toString())))
            .andExpect(jsonPath("$.[*].faulttype").value(hasItem(DEFAULT_FAULTTYPE.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].siteid").value(hasItem(DEFAULT_SITEID.toString())))
            .andExpect(jsonPath("$.[*].sitepriority").value(hasItem(DEFAULT_SITEPRIORITY.toString())))
            .andExpect(jsonPath("$.[*].totalprediction").value(hasItem(DEFAULT_TOTALPREDICTION.toString())))
            .andExpect(jsonPath("$.[*].failcount").value(hasItem(DEFAULT_FAILCOUNT)))
            .andExpect(jsonPath("$.[*].nofailcount").value(hasItem(DEFAULT_NOFAILCOUNT)))
            .andExpect(jsonPath("$.[*].failprob").value(hasItem(DEFAULT_FAILPROB.doubleValue())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAPrediction() throws Exception {
        // Initialize the database
        pAPredictionRepository.saveAndFlush(pAPrediction);

        // Get the pAPrediction
        restPAPredictionMockMvc.perform(get("/api/p-a-predictions/{id}", pAPrediction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAPrediction.getId().intValue()))
            .andExpect(jsonPath("$.predictiondate").value(sameInstant(DEFAULT_PREDICTIONDATE)))
            .andExpect(jsonPath("$.nename").value(DEFAULT_NENAME.toString()))
            .andExpect(jsonPath("$.faulttype").value(DEFAULT_FAULTTYPE.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.siteid").value(DEFAULT_SITEID.toString()))
            .andExpect(jsonPath("$.sitepriority").value(DEFAULT_SITEPRIORITY.toString()))
            .andExpect(jsonPath("$.totalprediction").value(DEFAULT_TOTALPREDICTION.toString()))
            .andExpect(jsonPath("$.failcount").value(DEFAULT_FAILCOUNT))
            .andExpect(jsonPath("$.nofailcount").value(DEFAULT_NOFAILCOUNT))
            .andExpect(jsonPath("$.failprob").value(DEFAULT_FAILPROB.doubleValue()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAPrediction() throws Exception {
        // Get the pAPrediction
        restPAPredictionMockMvc.perform(get("/api/p-a-predictions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAPrediction() throws Exception {
        // Initialize the database
        pAPredictionService.save(pAPrediction);

        int databaseSizeBeforeUpdate = pAPredictionRepository.findAll().size();

        // Update the pAPrediction
        PAPrediction updatedPAPrediction = pAPredictionRepository.findOne(pAPrediction.getId());
        updatedPAPrediction
                .predictiondate(UPDATED_PREDICTIONDATE)
                .nename(UPDATED_NENAME)
                .faulttype(UPDATED_FAULTTYPE)
                .severity(UPDATED_SEVERITY)
                .siteid(UPDATED_SITEID)
                .sitepriority(UPDATED_SITEPRIORITY)
                .totalprediction(UPDATED_TOTALPREDICTION)
                .failcount(UPDATED_FAILCOUNT)
                .nofailcount(UPDATED_NOFAILCOUNT)
                .failprob(UPDATED_FAILPROB)
                .pastatus(UPDATED_PASTATUS);

        restPAPredictionMockMvc.perform(put("/api/p-a-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAPrediction)))
            .andExpect(status().isOk());

        // Validate the PAPrediction in the database
        List<PAPrediction> pAPredictionList = pAPredictionRepository.findAll();
        assertThat(pAPredictionList).hasSize(databaseSizeBeforeUpdate);
        PAPrediction testPAPrediction = pAPredictionList.get(pAPredictionList.size() - 1);
        assertThat(testPAPrediction.getPredictiondate()).isEqualTo(UPDATED_PREDICTIONDATE);
        assertThat(testPAPrediction.getNename()).isEqualTo(UPDATED_NENAME);
        assertThat(testPAPrediction.getFaulttype()).isEqualTo(UPDATED_FAULTTYPE);
        assertThat(testPAPrediction.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testPAPrediction.getSiteid()).isEqualTo(UPDATED_SITEID);
        assertThat(testPAPrediction.getSitepriority()).isEqualTo(UPDATED_SITEPRIORITY);
        assertThat(testPAPrediction.getTotalprediction()).isEqualTo(UPDATED_TOTALPREDICTION);
        assertThat(testPAPrediction.getFailcount()).isEqualTo(UPDATED_FAILCOUNT);
        assertThat(testPAPrediction.getNofailcount()).isEqualTo(UPDATED_NOFAILCOUNT);
        assertThat(testPAPrediction.getFailprob()).isEqualTo(UPDATED_FAILPROB);
        assertThat(testPAPrediction.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAPrediction() throws Exception {
        int databaseSizeBeforeUpdate = pAPredictionRepository.findAll().size();

        // Create the PAPrediction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAPredictionMockMvc.perform(put("/api/p-a-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAPrediction)))
            .andExpect(status().isCreated());

        // Validate the PAPrediction in the database
        List<PAPrediction> pAPredictionList = pAPredictionRepository.findAll();
        assertThat(pAPredictionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAPrediction() throws Exception {
        // Initialize the database
        pAPredictionService.save(pAPrediction);

        int databaseSizeBeforeDelete = pAPredictionRepository.findAll().size();

        // Get the pAPrediction
        restPAPredictionMockMvc.perform(delete("/api/p-a-predictions/{id}", pAPrediction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAPrediction> pAPredictionList = pAPredictionRepository.findAll();
        assertThat(pAPredictionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
