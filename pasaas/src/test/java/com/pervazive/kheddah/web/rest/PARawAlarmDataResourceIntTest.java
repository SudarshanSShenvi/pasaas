package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PARawAlarmData;
import com.pervazive.kheddah.repository.PARawAlarmDataRepository;
import com.pervazive.kheddah.service.PARawAlarmDataService;

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

/**
 * Test class for the PARawAlarmDataResource REST controller.
 *
 * @see PARawAlarmDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PARawAlarmDataResourceIntTest {

    private static final Integer DEFAULT_ALARMNO = 1;
    private static final Integer UPDATED_ALARMNO = 2;

    private static final String DEFAULT_DISTNAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTNAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_STARTEDDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STARTEDDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private PARawAlarmDataRepository pARawAlarmDataRepository;

    @Inject
    private PARawAlarmDataService pARawAlarmDataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPARawAlarmDataMockMvc;

    private PARawAlarmData pARawAlarmData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PARawAlarmDataResource pARawAlarmDataResource = new PARawAlarmDataResource();
        ReflectionTestUtils.setField(pARawAlarmDataResource, "pARawAlarmDataService", pARawAlarmDataService);
        this.restPARawAlarmDataMockMvc = MockMvcBuilders.standaloneSetup(pARawAlarmDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PARawAlarmData createEntity(EntityManager em) {
        PARawAlarmData pARawAlarmData = new PARawAlarmData()
                .alarmno(DEFAULT_ALARMNO)
                .distname(DEFAULT_DISTNAME)
                .starteddate(DEFAULT_STARTEDDATE);
        return pARawAlarmData;
    }

    @Before
    public void initTest() {
        pARawAlarmData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPARawAlarmData() throws Exception {
        int databaseSizeBeforeCreate = pARawAlarmDataRepository.findAll().size();

        // Create the PARawAlarmData

        restPARawAlarmDataMockMvc.perform(post("/api/p-a-raw-alarm-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pARawAlarmData)))
            .andExpect(status().isCreated());

        // Validate the PARawAlarmData in the database
        List<PARawAlarmData> pARawAlarmDataList = pARawAlarmDataRepository.findAll();
        assertThat(pARawAlarmDataList).hasSize(databaseSizeBeforeCreate + 1);
        PARawAlarmData testPARawAlarmData = pARawAlarmDataList.get(pARawAlarmDataList.size() - 1);
        assertThat(testPARawAlarmData.getAlarmno()).isEqualTo(DEFAULT_ALARMNO);
        assertThat(testPARawAlarmData.getDistname()).isEqualTo(DEFAULT_DISTNAME);
        assertThat(testPARawAlarmData.getStarteddate()).isEqualTo(DEFAULT_STARTEDDATE);
    }

    @Test
    @Transactional
    public void createPARawAlarmDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pARawAlarmDataRepository.findAll().size();

        // Create the PARawAlarmData with an existing ID
        PARawAlarmData existingPARawAlarmData = new PARawAlarmData();
        existingPARawAlarmData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPARawAlarmDataMockMvc.perform(post("/api/p-a-raw-alarm-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPARawAlarmData)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PARawAlarmData> pARawAlarmDataList = pARawAlarmDataRepository.findAll();
        assertThat(pARawAlarmDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPARawAlarmData() throws Exception {
        // Initialize the database
        pARawAlarmDataRepository.saveAndFlush(pARawAlarmData);

        // Get all the pARawAlarmDataList
        restPARawAlarmDataMockMvc.perform(get("/api/p-a-raw-alarm-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pARawAlarmData.getId().intValue())))
            .andExpect(jsonPath("$.[*].alarmno").value(hasItem(DEFAULT_ALARMNO)))
            .andExpect(jsonPath("$.[*].distname").value(hasItem(DEFAULT_DISTNAME.toString())))
            .andExpect(jsonPath("$.[*].starteddate").value(hasItem(sameInstant(DEFAULT_STARTEDDATE))));
    }

    @Test
    @Transactional
    public void getPARawAlarmData() throws Exception {
        // Initialize the database
        pARawAlarmDataRepository.saveAndFlush(pARawAlarmData);

        // Get the pARawAlarmData
        restPARawAlarmDataMockMvc.perform(get("/api/p-a-raw-alarm-data/{id}", pARawAlarmData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pARawAlarmData.getId().intValue()))
            .andExpect(jsonPath("$.alarmno").value(DEFAULT_ALARMNO))
            .andExpect(jsonPath("$.distname").value(DEFAULT_DISTNAME.toString()))
            .andExpect(jsonPath("$.starteddate").value(sameInstant(DEFAULT_STARTEDDATE)));
    }

    @Test
    @Transactional
    public void getNonExistingPARawAlarmData() throws Exception {
        // Get the pARawAlarmData
        restPARawAlarmDataMockMvc.perform(get("/api/p-a-raw-alarm-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePARawAlarmData() throws Exception {
        // Initialize the database
        pARawAlarmDataService.save(pARawAlarmData);

        int databaseSizeBeforeUpdate = pARawAlarmDataRepository.findAll().size();

        // Update the pARawAlarmData
        PARawAlarmData updatedPARawAlarmData = pARawAlarmDataRepository.findOne(pARawAlarmData.getId());
        updatedPARawAlarmData
                .alarmno(UPDATED_ALARMNO)
                .distname(UPDATED_DISTNAME)
                .starteddate(UPDATED_STARTEDDATE);

        restPARawAlarmDataMockMvc.perform(put("/api/p-a-raw-alarm-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPARawAlarmData)))
            .andExpect(status().isOk());

        // Validate the PARawAlarmData in the database
        List<PARawAlarmData> pARawAlarmDataList = pARawAlarmDataRepository.findAll();
        assertThat(pARawAlarmDataList).hasSize(databaseSizeBeforeUpdate);
        PARawAlarmData testPARawAlarmData = pARawAlarmDataList.get(pARawAlarmDataList.size() - 1);
        assertThat(testPARawAlarmData.getAlarmno()).isEqualTo(UPDATED_ALARMNO);
        assertThat(testPARawAlarmData.getDistname()).isEqualTo(UPDATED_DISTNAME);
        assertThat(testPARawAlarmData.getStarteddate()).isEqualTo(UPDATED_STARTEDDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPARawAlarmData() throws Exception {
        int databaseSizeBeforeUpdate = pARawAlarmDataRepository.findAll().size();

        // Create the PARawAlarmData

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPARawAlarmDataMockMvc.perform(put("/api/p-a-raw-alarm-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pARawAlarmData)))
            .andExpect(status().isCreated());

        // Validate the PARawAlarmData in the database
        List<PARawAlarmData> pARawAlarmDataList = pARawAlarmDataRepository.findAll();
        assertThat(pARawAlarmDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePARawAlarmData() throws Exception {
        // Initialize the database
        pARawAlarmDataService.save(pARawAlarmData);

        int databaseSizeBeforeDelete = pARawAlarmDataRepository.findAll().size();

        // Get the pARawAlarmData
        restPARawAlarmDataMockMvc.perform(delete("/api/p-a-raw-alarm-data/{id}", pARawAlarmData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PARawAlarmData> pARawAlarmDataList = pARawAlarmDataRepository.findAll();
        assertThat(pARawAlarmDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
