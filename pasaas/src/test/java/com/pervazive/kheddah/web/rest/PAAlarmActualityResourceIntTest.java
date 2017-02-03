package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAAlarmActuality;
import com.pervazive.kheddah.repository.PAAlarmActualityRepository;
import com.pervazive.kheddah.service.PAAlarmActualityService;

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
 * Test class for the PAAlarmActualityResource REST controller.
 *
 * @see PAAlarmActualityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAAlarmActualityResourceIntTest {

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

    private static final Integer DEFAULT_PREDICTIONMATCHED = 1;
    private static final Integer UPDATED_PREDICTIONMATCHED = 2;

    private static final ZonedDateTime DEFAULT_OCCURED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OCCURED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_PMTCREATED = 1;
    private static final Integer UPDATED_PMTCREATED = 2;

    private static final Integer DEFAULT_TOTALEVENTS = 1;
    private static final Integer UPDATED_TOTALEVENTS = 2;

    private static final Integer DEFAULT_FAILEDCOUNTMATCH = 1;
    private static final Integer UPDATED_FAILEDCOUNTMATCH = 2;

    private static final Integer DEFAULT_NOFAILCOUNTMATCH = 1;
    private static final Integer UPDATED_NOFAILCOUNTMATCH = 2;

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAAlarmActualityRepository pAAlarmActualityRepository;

    @Inject
    private PAAlarmActualityService pAAlarmActualityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAAlarmActualityMockMvc;

    private PAAlarmActuality pAAlarmActuality;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAAlarmActualityResource pAAlarmActualityResource = new PAAlarmActualityResource();
        ReflectionTestUtils.setField(pAAlarmActualityResource, "pAAlarmActualityService", pAAlarmActualityService);
        this.restPAAlarmActualityMockMvc = MockMvcBuilders.standaloneSetup(pAAlarmActualityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAAlarmActuality createEntity(EntityManager em) {
        PAAlarmActuality pAAlarmActuality = new PAAlarmActuality()
                .nename(DEFAULT_NENAME)
                .faulttype(DEFAULT_FAULTTYPE)
                .severity(DEFAULT_SEVERITY)
                .siteid(DEFAULT_SITEID)
                .sitepriority(DEFAULT_SITEPRIORITY)
                .predictionmatched(DEFAULT_PREDICTIONMATCHED)
                .occured(DEFAULT_OCCURED)
                .pmtcreated(DEFAULT_PMTCREATED)
                .totalevents(DEFAULT_TOTALEVENTS)
                .failedcountmatch(DEFAULT_FAILEDCOUNTMATCH)
                .nofailcountmatch(DEFAULT_NOFAILCOUNTMATCH)
                .pastatus(DEFAULT_PASTATUS);
        return pAAlarmActuality;
    }

    @Before
    public void initTest() {
        pAAlarmActuality = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAAlarmActuality() throws Exception {
        int databaseSizeBeforeCreate = pAAlarmActualityRepository.findAll().size();

        // Create the PAAlarmActuality

        restPAAlarmActualityMockMvc.perform(post("/api/p-a-alarm-actualities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAAlarmActuality)))
            .andExpect(status().isCreated());

        // Validate the PAAlarmActuality in the database
        List<PAAlarmActuality> pAAlarmActualityList = pAAlarmActualityRepository.findAll();
        assertThat(pAAlarmActualityList).hasSize(databaseSizeBeforeCreate + 1);
        PAAlarmActuality testPAAlarmActuality = pAAlarmActualityList.get(pAAlarmActualityList.size() - 1);
        assertThat(testPAAlarmActuality.getNename()).isEqualTo(DEFAULT_NENAME);
        assertThat(testPAAlarmActuality.getFaulttype()).isEqualTo(DEFAULT_FAULTTYPE);
        assertThat(testPAAlarmActuality.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testPAAlarmActuality.getSiteid()).isEqualTo(DEFAULT_SITEID);
        assertThat(testPAAlarmActuality.getSitepriority()).isEqualTo(DEFAULT_SITEPRIORITY);
        assertThat(testPAAlarmActuality.getPredictionmatched()).isEqualTo(DEFAULT_PREDICTIONMATCHED);
        assertThat(testPAAlarmActuality.getOccured()).isEqualTo(DEFAULT_OCCURED);
        assertThat(testPAAlarmActuality.getPmtcreated()).isEqualTo(DEFAULT_PMTCREATED);
        assertThat(testPAAlarmActuality.getTotalevents()).isEqualTo(DEFAULT_TOTALEVENTS);
        assertThat(testPAAlarmActuality.getFailedcountmatch()).isEqualTo(DEFAULT_FAILEDCOUNTMATCH);
        assertThat(testPAAlarmActuality.getNofailcountmatch()).isEqualTo(DEFAULT_NOFAILCOUNTMATCH);
        assertThat(testPAAlarmActuality.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAAlarmActualityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAAlarmActualityRepository.findAll().size();

        // Create the PAAlarmActuality with an existing ID
        PAAlarmActuality existingPAAlarmActuality = new PAAlarmActuality();
        existingPAAlarmActuality.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAAlarmActualityMockMvc.perform(post("/api/p-a-alarm-actualities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAAlarmActuality)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAAlarmActuality> pAAlarmActualityList = pAAlarmActualityRepository.findAll();
        assertThat(pAAlarmActualityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAAlarmActualities() throws Exception {
        // Initialize the database
        pAAlarmActualityRepository.saveAndFlush(pAAlarmActuality);

        // Get all the pAAlarmActualityList
        restPAAlarmActualityMockMvc.perform(get("/api/p-a-alarm-actualities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAAlarmActuality.getId().intValue())))
            .andExpect(jsonPath("$.[*].nename").value(hasItem(DEFAULT_NENAME.toString())))
            .andExpect(jsonPath("$.[*].faulttype").value(hasItem(DEFAULT_FAULTTYPE.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].siteid").value(hasItem(DEFAULT_SITEID.toString())))
            .andExpect(jsonPath("$.[*].sitepriority").value(hasItem(DEFAULT_SITEPRIORITY.toString())))
            .andExpect(jsonPath("$.[*].predictionmatched").value(hasItem(DEFAULT_PREDICTIONMATCHED)))
            .andExpect(jsonPath("$.[*].occured").value(hasItem(sameInstant(DEFAULT_OCCURED))))
            .andExpect(jsonPath("$.[*].pmtcreated").value(hasItem(DEFAULT_PMTCREATED)))
            .andExpect(jsonPath("$.[*].totalevents").value(hasItem(DEFAULT_TOTALEVENTS)))
            .andExpect(jsonPath("$.[*].failedcountmatch").value(hasItem(DEFAULT_FAILEDCOUNTMATCH)))
            .andExpect(jsonPath("$.[*].nofailcountmatch").value(hasItem(DEFAULT_NOFAILCOUNTMATCH)))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAAlarmActuality() throws Exception {
        // Initialize the database
        pAAlarmActualityRepository.saveAndFlush(pAAlarmActuality);

        // Get the pAAlarmActuality
        restPAAlarmActualityMockMvc.perform(get("/api/p-a-alarm-actualities/{id}", pAAlarmActuality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAAlarmActuality.getId().intValue()))
            .andExpect(jsonPath("$.nename").value(DEFAULT_NENAME.toString()))
            .andExpect(jsonPath("$.faulttype").value(DEFAULT_FAULTTYPE.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.siteid").value(DEFAULT_SITEID.toString()))
            .andExpect(jsonPath("$.sitepriority").value(DEFAULT_SITEPRIORITY.toString()))
            .andExpect(jsonPath("$.predictionmatched").value(DEFAULT_PREDICTIONMATCHED))
            .andExpect(jsonPath("$.occured").value(sameInstant(DEFAULT_OCCURED)))
            .andExpect(jsonPath("$.pmtcreated").value(DEFAULT_PMTCREATED))
            .andExpect(jsonPath("$.totalevents").value(DEFAULT_TOTALEVENTS))
            .andExpect(jsonPath("$.failedcountmatch").value(DEFAULT_FAILEDCOUNTMATCH))
            .andExpect(jsonPath("$.nofailcountmatch").value(DEFAULT_NOFAILCOUNTMATCH))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAAlarmActuality() throws Exception {
        // Get the pAAlarmActuality
        restPAAlarmActualityMockMvc.perform(get("/api/p-a-alarm-actualities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAAlarmActuality() throws Exception {
        // Initialize the database
        pAAlarmActualityService.save(pAAlarmActuality);

        int databaseSizeBeforeUpdate = pAAlarmActualityRepository.findAll().size();

        // Update the pAAlarmActuality
        PAAlarmActuality updatedPAAlarmActuality = pAAlarmActualityRepository.findOne(pAAlarmActuality.getId());
        updatedPAAlarmActuality
                .nename(UPDATED_NENAME)
                .faulttype(UPDATED_FAULTTYPE)
                .severity(UPDATED_SEVERITY)
                .siteid(UPDATED_SITEID)
                .sitepriority(UPDATED_SITEPRIORITY)
                .predictionmatched(UPDATED_PREDICTIONMATCHED)
                .occured(UPDATED_OCCURED)
                .pmtcreated(UPDATED_PMTCREATED)
                .totalevents(UPDATED_TOTALEVENTS)
                .failedcountmatch(UPDATED_FAILEDCOUNTMATCH)
                .nofailcountmatch(UPDATED_NOFAILCOUNTMATCH)
                .pastatus(UPDATED_PASTATUS);

        restPAAlarmActualityMockMvc.perform(put("/api/p-a-alarm-actualities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAAlarmActuality)))
            .andExpect(status().isOk());

        // Validate the PAAlarmActuality in the database
        List<PAAlarmActuality> pAAlarmActualityList = pAAlarmActualityRepository.findAll();
        assertThat(pAAlarmActualityList).hasSize(databaseSizeBeforeUpdate);
        PAAlarmActuality testPAAlarmActuality = pAAlarmActualityList.get(pAAlarmActualityList.size() - 1);
        assertThat(testPAAlarmActuality.getNename()).isEqualTo(UPDATED_NENAME);
        assertThat(testPAAlarmActuality.getFaulttype()).isEqualTo(UPDATED_FAULTTYPE);
        assertThat(testPAAlarmActuality.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testPAAlarmActuality.getSiteid()).isEqualTo(UPDATED_SITEID);
        assertThat(testPAAlarmActuality.getSitepriority()).isEqualTo(UPDATED_SITEPRIORITY);
        assertThat(testPAAlarmActuality.getPredictionmatched()).isEqualTo(UPDATED_PREDICTIONMATCHED);
        assertThat(testPAAlarmActuality.getOccured()).isEqualTo(UPDATED_OCCURED);
        assertThat(testPAAlarmActuality.getPmtcreated()).isEqualTo(UPDATED_PMTCREATED);
        assertThat(testPAAlarmActuality.getTotalevents()).isEqualTo(UPDATED_TOTALEVENTS);
        assertThat(testPAAlarmActuality.getFailedcountmatch()).isEqualTo(UPDATED_FAILEDCOUNTMATCH);
        assertThat(testPAAlarmActuality.getNofailcountmatch()).isEqualTo(UPDATED_NOFAILCOUNTMATCH);
        assertThat(testPAAlarmActuality.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAAlarmActuality() throws Exception {
        int databaseSizeBeforeUpdate = pAAlarmActualityRepository.findAll().size();

        // Create the PAAlarmActuality

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAAlarmActualityMockMvc.perform(put("/api/p-a-alarm-actualities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAAlarmActuality)))
            .andExpect(status().isCreated());

        // Validate the PAAlarmActuality in the database
        List<PAAlarmActuality> pAAlarmActualityList = pAAlarmActualityRepository.findAll();
        assertThat(pAAlarmActualityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAAlarmActuality() throws Exception {
        // Initialize the database
        pAAlarmActualityService.save(pAAlarmActuality);

        int databaseSizeBeforeDelete = pAAlarmActualityRepository.findAll().size();

        // Get the pAAlarmActuality
        restPAAlarmActualityMockMvc.perform(delete("/api/p-a-alarm-actualities/{id}", pAAlarmActuality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAAlarmActuality> pAAlarmActualityList = pAAlarmActualityRepository.findAll();
        assertThat(pAAlarmActualityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
