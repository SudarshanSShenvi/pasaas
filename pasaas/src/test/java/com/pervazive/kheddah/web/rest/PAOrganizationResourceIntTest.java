package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.service.PAOrganizationService;

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
 * Test class for the PAOrganizationResource REST controller.
 *
 * @see PAOrganizationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAOrganizationResourceIntTest {

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_VALIDFROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VALIDFROM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_VALIDTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VALIDTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAOrganizationRepository pAOrganizationRepository;

    @Inject
    private PAOrganizationService pAOrganizationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAOrganizationMockMvc;

    private PAOrganization pAOrganization;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAOrganizationResource pAOrganizationResource = new PAOrganizationResource();
        ReflectionTestUtils.setField(pAOrganizationResource, "pAOrganizationService", pAOrganizationService);
        this.restPAOrganizationMockMvc = MockMvcBuilders.standaloneSetup(pAOrganizationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAOrganization createEntity(EntityManager em) {
        PAOrganization pAOrganization = new PAOrganization()
                .organization(DEFAULT_ORGANIZATION)
                .validfrom(DEFAULT_VALIDFROM)
                .validto(DEFAULT_VALIDTO)
                .pastatus(DEFAULT_PASTATUS);
        return pAOrganization;
    }

    @Before
    public void initTest() {
        pAOrganization = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAOrganization() throws Exception {
        int databaseSizeBeforeCreate = pAOrganizationRepository.findAll().size();

        // Create the PAOrganization

        restPAOrganizationMockMvc.perform(post("/api/p-a-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAOrganization)))
            .andExpect(status().isCreated());

        // Validate the PAOrganization in the database
        List<PAOrganization> pAOrganizationList = pAOrganizationRepository.findAll();
        assertThat(pAOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        PAOrganization testPAOrganization = pAOrganizationList.get(pAOrganizationList.size() - 1);
        assertThat(testPAOrganization.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testPAOrganization.getValidfrom()).isEqualTo(DEFAULT_VALIDFROM);
        assertThat(testPAOrganization.getValidto()).isEqualTo(DEFAULT_VALIDTO);
        assertThat(testPAOrganization.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAOrganizationRepository.findAll().size();

        // Create the PAOrganization with an existing ID
        PAOrganization existingPAOrganization = new PAOrganization();
        existingPAOrganization.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAOrganizationMockMvc.perform(post("/api/p-a-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAOrganization)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAOrganization> pAOrganizationList = pAOrganizationRepository.findAll();
        assertThat(pAOrganizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAOrganizations() throws Exception {
        // Initialize the database
        pAOrganizationRepository.saveAndFlush(pAOrganization);

        // Get all the pAOrganizationList
        restPAOrganizationMockMvc.perform(get("/api/p-a-organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
            .andExpect(jsonPath("$.[*].validfrom").value(hasItem(sameInstant(DEFAULT_VALIDFROM))))
            .andExpect(jsonPath("$.[*].validto").value(hasItem(sameInstant(DEFAULT_VALIDTO))))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAOrganization() throws Exception {
        // Initialize the database
        pAOrganizationRepository.saveAndFlush(pAOrganization);

        // Get the pAOrganization
        restPAOrganizationMockMvc.perform(get("/api/p-a-organizations/{id}", pAOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAOrganization.getId().intValue()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.validfrom").value(sameInstant(DEFAULT_VALIDFROM)))
            .andExpect(jsonPath("$.validto").value(sameInstant(DEFAULT_VALIDTO)))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAOrganization() throws Exception {
        // Get the pAOrganization
        restPAOrganizationMockMvc.perform(get("/api/p-a-organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAOrganization() throws Exception {
        // Initialize the database
        pAOrganizationService.save(pAOrganization);

        int databaseSizeBeforeUpdate = pAOrganizationRepository.findAll().size();

        // Update the pAOrganization
        PAOrganization updatedPAOrganization = pAOrganizationRepository.findOne(pAOrganization.getId());
        updatedPAOrganization
                .organization(UPDATED_ORGANIZATION)
                .validfrom(UPDATED_VALIDFROM)
                .validto(UPDATED_VALIDTO)
                .pastatus(UPDATED_PASTATUS);

        restPAOrganizationMockMvc.perform(put("/api/p-a-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAOrganization)))
            .andExpect(status().isOk());

        // Validate the PAOrganization in the database
        List<PAOrganization> pAOrganizationList = pAOrganizationRepository.findAll();
        assertThat(pAOrganizationList).hasSize(databaseSizeBeforeUpdate);
        PAOrganization testPAOrganization = pAOrganizationList.get(pAOrganizationList.size() - 1);
        assertThat(testPAOrganization.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testPAOrganization.getValidfrom()).isEqualTo(UPDATED_VALIDFROM);
        assertThat(testPAOrganization.getValidto()).isEqualTo(UPDATED_VALIDTO);
        assertThat(testPAOrganization.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAOrganization() throws Exception {
        int databaseSizeBeforeUpdate = pAOrganizationRepository.findAll().size();

        // Create the PAOrganization

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAOrganizationMockMvc.perform(put("/api/p-a-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAOrganization)))
            .andExpect(status().isCreated());

        // Validate the PAOrganization in the database
        List<PAOrganization> pAOrganizationList = pAOrganizationRepository.findAll();
        assertThat(pAOrganizationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAOrganization() throws Exception {
        // Initialize the database
        pAOrganizationService.save(pAOrganization);

        int databaseSizeBeforeDelete = pAOrganizationRepository.findAll().size();

        // Get the pAOrganization
        restPAOrganizationMockMvc.perform(delete("/api/p-a-organizations/{id}", pAOrganization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAOrganization> pAOrganizationList = pAOrganizationRepository.findAll();
        assertThat(pAOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
