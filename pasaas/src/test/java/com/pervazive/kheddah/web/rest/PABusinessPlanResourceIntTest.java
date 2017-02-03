package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PABusinessPlan;
import com.pervazive.kheddah.repository.PABusinessPlanRepository;
import com.pervazive.kheddah.service.PABusinessPlanService;

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

import com.pervazive.kheddah.domain.enumeration.PlanType;
import com.pervazive.kheddah.domain.enumeration.PAStatus;
/**
 * Test class for the PABusinessPlanResource REST controller.
 *
 * @see PABusinessPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PABusinessPlanResourceIntTest {

    private static final PlanType DEFAULT_BUSINESSPLAN = PlanType.Trial;
    private static final PlanType UPDATED_BUSINESSPLAN = PlanType.Silver;

    private static final Integer DEFAULT_USERS = 1;
    private static final Integer UPDATED_USERS = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    private static final Integer DEFAULT_PROJECTS = 1;
    private static final Integer UPDATED_PROJECTS = 2;

    @Inject
    private PABusinessPlanRepository pABusinessPlanRepository;

    @Inject
    private PABusinessPlanService pABusinessPlanService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPABusinessPlanMockMvc;

    private PABusinessPlan pABusinessPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PABusinessPlanResource pABusinessPlanResource = new PABusinessPlanResource();
        ReflectionTestUtils.setField(pABusinessPlanResource, "pABusinessPlanService", pABusinessPlanService);
        this.restPABusinessPlanMockMvc = MockMvcBuilders.standaloneSetup(pABusinessPlanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PABusinessPlan createEntity(EntityManager em) {
        PABusinessPlan pABusinessPlan = new PABusinessPlan()
                .businessplan(DEFAULT_BUSINESSPLAN)
                .users(DEFAULT_USERS)
                .description(DEFAULT_DESCRIPTION)
                .pastatus(DEFAULT_PASTATUS)
                .projects(DEFAULT_PROJECTS);
        return pABusinessPlan;
    }

    @Before
    public void initTest() {
        pABusinessPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createPABusinessPlan() throws Exception {
        int databaseSizeBeforeCreate = pABusinessPlanRepository.findAll().size();

        // Create the PABusinessPlan

        restPABusinessPlanMockMvc.perform(post("/api/p-a-business-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pABusinessPlan)))
            .andExpect(status().isCreated());

        // Validate the PABusinessPlan in the database
        List<PABusinessPlan> pABusinessPlanList = pABusinessPlanRepository.findAll();
        assertThat(pABusinessPlanList).hasSize(databaseSizeBeforeCreate + 1);
        PABusinessPlan testPABusinessPlan = pABusinessPlanList.get(pABusinessPlanList.size() - 1);
        assertThat(testPABusinessPlan.getBusinessplan()).isEqualTo(DEFAULT_BUSINESSPLAN);
        assertThat(testPABusinessPlan.getUsers()).isEqualTo(DEFAULT_USERS);
        assertThat(testPABusinessPlan.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPABusinessPlan.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
        assertThat(testPABusinessPlan.getProjects()).isEqualTo(DEFAULT_PROJECTS);
    }

    @Test
    @Transactional
    public void createPABusinessPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pABusinessPlanRepository.findAll().size();

        // Create the PABusinessPlan with an existing ID
        PABusinessPlan existingPABusinessPlan = new PABusinessPlan();
        existingPABusinessPlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPABusinessPlanMockMvc.perform(post("/api/p-a-business-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPABusinessPlan)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PABusinessPlan> pABusinessPlanList = pABusinessPlanRepository.findAll();
        assertThat(pABusinessPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPABusinessPlans() throws Exception {
        // Initialize the database
        pABusinessPlanRepository.saveAndFlush(pABusinessPlan);

        // Get all the pABusinessPlanList
        restPABusinessPlanMockMvc.perform(get("/api/p-a-business-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pABusinessPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].businessplan").value(hasItem(DEFAULT_BUSINESSPLAN.toString())))
            .andExpect(jsonPath("$.[*].users").value(hasItem(DEFAULT_USERS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())))
            .andExpect(jsonPath("$.[*].projects").value(hasItem(DEFAULT_PROJECTS)));
    }

    @Test
    @Transactional
    public void getPABusinessPlan() throws Exception {
        // Initialize the database
        pABusinessPlanRepository.saveAndFlush(pABusinessPlan);

        // Get the pABusinessPlan
        restPABusinessPlanMockMvc.perform(get("/api/p-a-business-plans/{id}", pABusinessPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pABusinessPlan.getId().intValue()))
            .andExpect(jsonPath("$.businessplan").value(DEFAULT_BUSINESSPLAN.toString()))
            .andExpect(jsonPath("$.users").value(DEFAULT_USERS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()))
            .andExpect(jsonPath("$.projects").value(DEFAULT_PROJECTS));
    }

    @Test
    @Transactional
    public void getNonExistingPABusinessPlan() throws Exception {
        // Get the pABusinessPlan
        restPABusinessPlanMockMvc.perform(get("/api/p-a-business-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePABusinessPlan() throws Exception {
        // Initialize the database
        pABusinessPlanService.save(pABusinessPlan);

        int databaseSizeBeforeUpdate = pABusinessPlanRepository.findAll().size();

        // Update the pABusinessPlan
        PABusinessPlan updatedPABusinessPlan = pABusinessPlanRepository.findOne(pABusinessPlan.getId());
        updatedPABusinessPlan
                .businessplan(UPDATED_BUSINESSPLAN)
                .users(UPDATED_USERS)
                .description(UPDATED_DESCRIPTION)
                .pastatus(UPDATED_PASTATUS)
                .projects(UPDATED_PROJECTS);

        restPABusinessPlanMockMvc.perform(put("/api/p-a-business-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPABusinessPlan)))
            .andExpect(status().isOk());

        // Validate the PABusinessPlan in the database
        List<PABusinessPlan> pABusinessPlanList = pABusinessPlanRepository.findAll();
        assertThat(pABusinessPlanList).hasSize(databaseSizeBeforeUpdate);
        PABusinessPlan testPABusinessPlan = pABusinessPlanList.get(pABusinessPlanList.size() - 1);
        assertThat(testPABusinessPlan.getBusinessplan()).isEqualTo(UPDATED_BUSINESSPLAN);
        assertThat(testPABusinessPlan.getUsers()).isEqualTo(UPDATED_USERS);
        assertThat(testPABusinessPlan.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPABusinessPlan.getPastatus()).isEqualTo(UPDATED_PASTATUS);
        assertThat(testPABusinessPlan.getProjects()).isEqualTo(UPDATED_PROJECTS);
    }

    @Test
    @Transactional
    public void updateNonExistingPABusinessPlan() throws Exception {
        int databaseSizeBeforeUpdate = pABusinessPlanRepository.findAll().size();

        // Create the PABusinessPlan

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPABusinessPlanMockMvc.perform(put("/api/p-a-business-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pABusinessPlan)))
            .andExpect(status().isCreated());

        // Validate the PABusinessPlan in the database
        List<PABusinessPlan> pABusinessPlanList = pABusinessPlanRepository.findAll();
        assertThat(pABusinessPlanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePABusinessPlan() throws Exception {
        // Initialize the database
        pABusinessPlanService.save(pABusinessPlan);

        int databaseSizeBeforeDelete = pABusinessPlanRepository.findAll().size();

        // Get the pABusinessPlan
        restPABusinessPlanMockMvc.perform(delete("/api/p-a-business-plans/{id}", pABusinessPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PABusinessPlan> pABusinessPlanList = pABusinessPlanRepository.findAll();
        assertThat(pABusinessPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
