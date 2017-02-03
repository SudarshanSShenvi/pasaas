package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PASchedulerInterval;
import com.pervazive.kheddah.repository.PASchedulerIntervalRepository;
import com.pervazive.kheddah.service.PASchedulerIntervalService;

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
 * Test class for the PASchedulerIntervalResource REST controller.
 *
 * @see PASchedulerIntervalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PASchedulerIntervalResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_HOURVAL = 1;
    private static final Integer UPDATED_HOURVAL = 2;

    private static final Integer DEFAULT_MINUTESVAL = 1;
    private static final Integer UPDATED_MINUTESVAL = 2;

    private static final String DEFAULT_SCHNAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DCNAME = "AAAAAAAAAA";
    private static final String UPDATED_DCNAME = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PASchedulerIntervalRepository pASchedulerIntervalRepository;

    @Inject
    private PASchedulerIntervalService pASchedulerIntervalService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPASchedulerIntervalMockMvc;

    private PASchedulerInterval pASchedulerInterval;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PASchedulerIntervalResource pASchedulerIntervalResource = new PASchedulerIntervalResource();
        ReflectionTestUtils.setField(pASchedulerIntervalResource, "pASchedulerIntervalService", pASchedulerIntervalService);
        this.restPASchedulerIntervalMockMvc = MockMvcBuilders.standaloneSetup(pASchedulerIntervalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PASchedulerInterval createEntity(EntityManager em) {
        PASchedulerInterval pASchedulerInterval = new PASchedulerInterval()
                .type(DEFAULT_TYPE)
                .hourval(DEFAULT_HOURVAL)
                .minutesval(DEFAULT_MINUTESVAL)
                .schname(DEFAULT_SCHNAME)
                .dcname(DEFAULT_DCNAME)
                .pastatus(DEFAULT_PASTATUS);
        return pASchedulerInterval;
    }

    @Before
    public void initTest() {
        pASchedulerInterval = createEntity(em);
    }

    @Test
    @Transactional
    public void createPASchedulerInterval() throws Exception {
        int databaseSizeBeforeCreate = pASchedulerIntervalRepository.findAll().size();

        // Create the PASchedulerInterval

        restPASchedulerIntervalMockMvc.perform(post("/api/p-a-scheduler-intervals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pASchedulerInterval)))
            .andExpect(status().isCreated());

        // Validate the PASchedulerInterval in the database
        List<PASchedulerInterval> pASchedulerIntervalList = pASchedulerIntervalRepository.findAll();
        assertThat(pASchedulerIntervalList).hasSize(databaseSizeBeforeCreate + 1);
        PASchedulerInterval testPASchedulerInterval = pASchedulerIntervalList.get(pASchedulerIntervalList.size() - 1);
        assertThat(testPASchedulerInterval.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPASchedulerInterval.getHourval()).isEqualTo(DEFAULT_HOURVAL);
        assertThat(testPASchedulerInterval.getMinutesval()).isEqualTo(DEFAULT_MINUTESVAL);
        assertThat(testPASchedulerInterval.getSchname()).isEqualTo(DEFAULT_SCHNAME);
        assertThat(testPASchedulerInterval.getDcname()).isEqualTo(DEFAULT_DCNAME);
        assertThat(testPASchedulerInterval.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPASchedulerIntervalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pASchedulerIntervalRepository.findAll().size();

        // Create the PASchedulerInterval with an existing ID
        PASchedulerInterval existingPASchedulerInterval = new PASchedulerInterval();
        existingPASchedulerInterval.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPASchedulerIntervalMockMvc.perform(post("/api/p-a-scheduler-intervals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPASchedulerInterval)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PASchedulerInterval> pASchedulerIntervalList = pASchedulerIntervalRepository.findAll();
        assertThat(pASchedulerIntervalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPASchedulerIntervals() throws Exception {
        // Initialize the database
        pASchedulerIntervalRepository.saveAndFlush(pASchedulerInterval);

        // Get all the pASchedulerIntervalList
        restPASchedulerIntervalMockMvc.perform(get("/api/p-a-scheduler-intervals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pASchedulerInterval.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].hourval").value(hasItem(DEFAULT_HOURVAL)))
            .andExpect(jsonPath("$.[*].minutesval").value(hasItem(DEFAULT_MINUTESVAL)))
            .andExpect(jsonPath("$.[*].schname").value(hasItem(DEFAULT_SCHNAME.toString())))
            .andExpect(jsonPath("$.[*].dcname").value(hasItem(DEFAULT_DCNAME.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPASchedulerInterval() throws Exception {
        // Initialize the database
        pASchedulerIntervalRepository.saveAndFlush(pASchedulerInterval);

        // Get the pASchedulerInterval
        restPASchedulerIntervalMockMvc.perform(get("/api/p-a-scheduler-intervals/{id}", pASchedulerInterval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pASchedulerInterval.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.hourval").value(DEFAULT_HOURVAL))
            .andExpect(jsonPath("$.minutesval").value(DEFAULT_MINUTESVAL))
            .andExpect(jsonPath("$.schname").value(DEFAULT_SCHNAME.toString()))
            .andExpect(jsonPath("$.dcname").value(DEFAULT_DCNAME.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPASchedulerInterval() throws Exception {
        // Get the pASchedulerInterval
        restPASchedulerIntervalMockMvc.perform(get("/api/p-a-scheduler-intervals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePASchedulerInterval() throws Exception {
        // Initialize the database
        pASchedulerIntervalService.save(pASchedulerInterval);

        int databaseSizeBeforeUpdate = pASchedulerIntervalRepository.findAll().size();

        // Update the pASchedulerInterval
        PASchedulerInterval updatedPASchedulerInterval = pASchedulerIntervalRepository.findOne(pASchedulerInterval.getId());
        updatedPASchedulerInterval
                .type(UPDATED_TYPE)
                .hourval(UPDATED_HOURVAL)
                .minutesval(UPDATED_MINUTESVAL)
                .schname(UPDATED_SCHNAME)
                .dcname(UPDATED_DCNAME)
                .pastatus(UPDATED_PASTATUS);

        restPASchedulerIntervalMockMvc.perform(put("/api/p-a-scheduler-intervals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPASchedulerInterval)))
            .andExpect(status().isOk());

        // Validate the PASchedulerInterval in the database
        List<PASchedulerInterval> pASchedulerIntervalList = pASchedulerIntervalRepository.findAll();
        assertThat(pASchedulerIntervalList).hasSize(databaseSizeBeforeUpdate);
        PASchedulerInterval testPASchedulerInterval = pASchedulerIntervalList.get(pASchedulerIntervalList.size() - 1);
        assertThat(testPASchedulerInterval.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPASchedulerInterval.getHourval()).isEqualTo(UPDATED_HOURVAL);
        assertThat(testPASchedulerInterval.getMinutesval()).isEqualTo(UPDATED_MINUTESVAL);
        assertThat(testPASchedulerInterval.getSchname()).isEqualTo(UPDATED_SCHNAME);
        assertThat(testPASchedulerInterval.getDcname()).isEqualTo(UPDATED_DCNAME);
        assertThat(testPASchedulerInterval.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPASchedulerInterval() throws Exception {
        int databaseSizeBeforeUpdate = pASchedulerIntervalRepository.findAll().size();

        // Create the PASchedulerInterval

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPASchedulerIntervalMockMvc.perform(put("/api/p-a-scheduler-intervals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pASchedulerInterval)))
            .andExpect(status().isCreated());

        // Validate the PASchedulerInterval in the database
        List<PASchedulerInterval> pASchedulerIntervalList = pASchedulerIntervalRepository.findAll();
        assertThat(pASchedulerIntervalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePASchedulerInterval() throws Exception {
        // Initialize the database
        pASchedulerIntervalService.save(pASchedulerInterval);

        int databaseSizeBeforeDelete = pASchedulerIntervalRepository.findAll().size();

        // Get the pASchedulerInterval
        restPASchedulerIntervalMockMvc.perform(delete("/api/p-a-scheduler-intervals/{id}", pASchedulerInterval.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PASchedulerInterval> pASchedulerIntervalList = pASchedulerIntervalRepository.findAll();
        assertThat(pASchedulerIntervalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
