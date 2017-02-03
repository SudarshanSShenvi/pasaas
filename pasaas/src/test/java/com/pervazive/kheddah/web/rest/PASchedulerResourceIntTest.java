package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAScheduler;
import com.pervazive.kheddah.repository.PASchedulerRepository;
import com.pervazive.kheddah.service.PASchedulerService;

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
 * Test class for the PASchedulerResource REST controller.
 *
 * @see PASchedulerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PASchedulerResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_INTERVALTIME = 1;
    private static final Integer UPDATED_INTERVALTIME = 2;

    private static final Integer DEFAULT_HOURVAL = 1;
    private static final Integer UPDATED_HOURVAL = 2;

    private static final Integer DEFAULT_MINUTESVAL = 1;
    private static final Integer UPDATED_MINUTESVAL = 2;

    private static final Boolean DEFAULT_RUNSUNDAY = false;
    private static final Boolean UPDATED_RUNSUNDAY = true;

    private static final Boolean DEFAULT_RUNMONDAY = false;
    private static final Boolean UPDATED_RUNMONDAY = true;

    private static final Boolean DEFAULT_RUNTUESDAY = false;
    private static final Boolean UPDATED_RUNTUESDAY = true;

    private static final Boolean DEFAULT_RUNWEDNESDAY = false;
    private static final Boolean UPDATED_RUNWEDNESDAY = true;

    private static final Boolean DEFAULT_RUNTHURSDAY = false;
    private static final Boolean UPDATED_RUNTHURSDAY = true;

    private static final Boolean DEFAULT_RUNFRIDAY = false;
    private static final Boolean UPDATED_RUNFRIDAY = true;

    private static final String DEFAULT_DCNAME = "AAAAAAAAAA";
    private static final String UPDATED_DCNAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RUNSATURDAY = false;
    private static final Boolean UPDATED_RUNSATURDAY = true;

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PASchedulerRepository pASchedulerRepository;

    @Inject
    private PASchedulerService pASchedulerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPASchedulerMockMvc;

    private PAScheduler pAScheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PASchedulerResource pASchedulerResource = new PASchedulerResource();
        ReflectionTestUtils.setField(pASchedulerResource, "pASchedulerService", pASchedulerService);
        this.restPASchedulerMockMvc = MockMvcBuilders.standaloneSetup(pASchedulerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAScheduler createEntity(EntityManager em) {
        PAScheduler pAScheduler = new PAScheduler()
                .type(DEFAULT_TYPE)
                .intervaltime(DEFAULT_INTERVALTIME)
                .hourval(DEFAULT_HOURVAL)
                .minutesval(DEFAULT_MINUTESVAL)
                .runsunday(DEFAULT_RUNSUNDAY)
                .runmonday(DEFAULT_RUNMONDAY)
                .runtuesday(DEFAULT_RUNTUESDAY)
                .runwednesday(DEFAULT_RUNWEDNESDAY)
                .runthursday(DEFAULT_RUNTHURSDAY)
                .runfriday(DEFAULT_RUNFRIDAY)
                .dcname(DEFAULT_DCNAME)
                .runsaturday(DEFAULT_RUNSATURDAY)
                .pastatus(DEFAULT_PASTATUS);
        return pAScheduler;
    }

    @Before
    public void initTest() {
        pAScheduler = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAScheduler() throws Exception {
        int databaseSizeBeforeCreate = pASchedulerRepository.findAll().size();

        // Create the PAScheduler

        restPASchedulerMockMvc.perform(post("/api/p-a-schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAScheduler)))
            .andExpect(status().isCreated());

        // Validate the PAScheduler in the database
        List<PAScheduler> pASchedulerList = pASchedulerRepository.findAll();
        assertThat(pASchedulerList).hasSize(databaseSizeBeforeCreate + 1);
        PAScheduler testPAScheduler = pASchedulerList.get(pASchedulerList.size() - 1);
        assertThat(testPAScheduler.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPAScheduler.getIntervaltime()).isEqualTo(DEFAULT_INTERVALTIME);
        assertThat(testPAScheduler.getHourval()).isEqualTo(DEFAULT_HOURVAL);
        assertThat(testPAScheduler.getMinutesval()).isEqualTo(DEFAULT_MINUTESVAL);
        assertThat(testPAScheduler.isRunsunday()).isEqualTo(DEFAULT_RUNSUNDAY);
        assertThat(testPAScheduler.isRunmonday()).isEqualTo(DEFAULT_RUNMONDAY);
        assertThat(testPAScheduler.isRuntuesday()).isEqualTo(DEFAULT_RUNTUESDAY);
        assertThat(testPAScheduler.isRunwednesday()).isEqualTo(DEFAULT_RUNWEDNESDAY);
        assertThat(testPAScheduler.isRunthursday()).isEqualTo(DEFAULT_RUNTHURSDAY);
        assertThat(testPAScheduler.isRunfriday()).isEqualTo(DEFAULT_RUNFRIDAY);
        assertThat(testPAScheduler.getDcname()).isEqualTo(DEFAULT_DCNAME);
        assertThat(testPAScheduler.isRunsaturday()).isEqualTo(DEFAULT_RUNSATURDAY);
        assertThat(testPAScheduler.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPASchedulerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pASchedulerRepository.findAll().size();

        // Create the PAScheduler with an existing ID
        PAScheduler existingPAScheduler = new PAScheduler();
        existingPAScheduler.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPASchedulerMockMvc.perform(post("/api/p-a-schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAScheduler)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAScheduler> pASchedulerList = pASchedulerRepository.findAll();
        assertThat(pASchedulerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPASchedulers() throws Exception {
        // Initialize the database
        pASchedulerRepository.saveAndFlush(pAScheduler);

        // Get all the pASchedulerList
        restPASchedulerMockMvc.perform(get("/api/p-a-schedulers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAScheduler.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].intervaltime").value(hasItem(DEFAULT_INTERVALTIME)))
            .andExpect(jsonPath("$.[*].hourval").value(hasItem(DEFAULT_HOURVAL)))
            .andExpect(jsonPath("$.[*].minutesval").value(hasItem(DEFAULT_MINUTESVAL)))
            .andExpect(jsonPath("$.[*].runsunday").value(hasItem(DEFAULT_RUNSUNDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].runmonday").value(hasItem(DEFAULT_RUNMONDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].runtuesday").value(hasItem(DEFAULT_RUNTUESDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].runwednesday").value(hasItem(DEFAULT_RUNWEDNESDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].runthursday").value(hasItem(DEFAULT_RUNTHURSDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].runfriday").value(hasItem(DEFAULT_RUNFRIDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].dcname").value(hasItem(DEFAULT_DCNAME.toString())))
            .andExpect(jsonPath("$.[*].runsaturday").value(hasItem(DEFAULT_RUNSATURDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAScheduler() throws Exception {
        // Initialize the database
        pASchedulerRepository.saveAndFlush(pAScheduler);

        // Get the pAScheduler
        restPASchedulerMockMvc.perform(get("/api/p-a-schedulers/{id}", pAScheduler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAScheduler.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.intervaltime").value(DEFAULT_INTERVALTIME))
            .andExpect(jsonPath("$.hourval").value(DEFAULT_HOURVAL))
            .andExpect(jsonPath("$.minutesval").value(DEFAULT_MINUTESVAL))
            .andExpect(jsonPath("$.runsunday").value(DEFAULT_RUNSUNDAY.booleanValue()))
            .andExpect(jsonPath("$.runmonday").value(DEFAULT_RUNMONDAY.booleanValue()))
            .andExpect(jsonPath("$.runtuesday").value(DEFAULT_RUNTUESDAY.booleanValue()))
            .andExpect(jsonPath("$.runwednesday").value(DEFAULT_RUNWEDNESDAY.booleanValue()))
            .andExpect(jsonPath("$.runthursday").value(DEFAULT_RUNTHURSDAY.booleanValue()))
            .andExpect(jsonPath("$.runfriday").value(DEFAULT_RUNFRIDAY.booleanValue()))
            .andExpect(jsonPath("$.dcname").value(DEFAULT_DCNAME.toString()))
            .andExpect(jsonPath("$.runsaturday").value(DEFAULT_RUNSATURDAY.booleanValue()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAScheduler() throws Exception {
        // Get the pAScheduler
        restPASchedulerMockMvc.perform(get("/api/p-a-schedulers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAScheduler() throws Exception {
        // Initialize the database
        pASchedulerService.save(pAScheduler);

        int databaseSizeBeforeUpdate = pASchedulerRepository.findAll().size();

        // Update the pAScheduler
        PAScheduler updatedPAScheduler = pASchedulerRepository.findOne(pAScheduler.getId());
        updatedPAScheduler
                .type(UPDATED_TYPE)
                .intervaltime(UPDATED_INTERVALTIME)
                .hourval(UPDATED_HOURVAL)
                .minutesval(UPDATED_MINUTESVAL)
                .runsunday(UPDATED_RUNSUNDAY)
                .runmonday(UPDATED_RUNMONDAY)
                .runtuesday(UPDATED_RUNTUESDAY)
                .runwednesday(UPDATED_RUNWEDNESDAY)
                .runthursday(UPDATED_RUNTHURSDAY)
                .runfriday(UPDATED_RUNFRIDAY)
                .dcname(UPDATED_DCNAME)
                .runsaturday(UPDATED_RUNSATURDAY)
                .pastatus(UPDATED_PASTATUS);

        restPASchedulerMockMvc.perform(put("/api/p-a-schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAScheduler)))
            .andExpect(status().isOk());

        // Validate the PAScheduler in the database
        List<PAScheduler> pASchedulerList = pASchedulerRepository.findAll();
        assertThat(pASchedulerList).hasSize(databaseSizeBeforeUpdate);
        PAScheduler testPAScheduler = pASchedulerList.get(pASchedulerList.size() - 1);
        assertThat(testPAScheduler.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPAScheduler.getIntervaltime()).isEqualTo(UPDATED_INTERVALTIME);
        assertThat(testPAScheduler.getHourval()).isEqualTo(UPDATED_HOURVAL);
        assertThat(testPAScheduler.getMinutesval()).isEqualTo(UPDATED_MINUTESVAL);
        assertThat(testPAScheduler.isRunsunday()).isEqualTo(UPDATED_RUNSUNDAY);
        assertThat(testPAScheduler.isRunmonday()).isEqualTo(UPDATED_RUNMONDAY);
        assertThat(testPAScheduler.isRuntuesday()).isEqualTo(UPDATED_RUNTUESDAY);
        assertThat(testPAScheduler.isRunwednesday()).isEqualTo(UPDATED_RUNWEDNESDAY);
        assertThat(testPAScheduler.isRunthursday()).isEqualTo(UPDATED_RUNTHURSDAY);
        assertThat(testPAScheduler.isRunfriday()).isEqualTo(UPDATED_RUNFRIDAY);
        assertThat(testPAScheduler.getDcname()).isEqualTo(UPDATED_DCNAME);
        assertThat(testPAScheduler.isRunsaturday()).isEqualTo(UPDATED_RUNSATURDAY);
        assertThat(testPAScheduler.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAScheduler() throws Exception {
        int databaseSizeBeforeUpdate = pASchedulerRepository.findAll().size();

        // Create the PAScheduler

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPASchedulerMockMvc.perform(put("/api/p-a-schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAScheduler)))
            .andExpect(status().isCreated());

        // Validate the PAScheduler in the database
        List<PAScheduler> pASchedulerList = pASchedulerRepository.findAll();
        assertThat(pASchedulerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAScheduler() throws Exception {
        // Initialize the database
        pASchedulerService.save(pAScheduler);

        int databaseSizeBeforeDelete = pASchedulerRepository.findAll().size();

        // Get the pAScheduler
        restPASchedulerMockMvc.perform(delete("/api/p-a-schedulers/{id}", pAScheduler.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAScheduler> pASchedulerList = pASchedulerRepository.findAll();
        assertThat(pASchedulerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
