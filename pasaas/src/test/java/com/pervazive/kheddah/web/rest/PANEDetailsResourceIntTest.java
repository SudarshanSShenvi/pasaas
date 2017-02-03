package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PANEDetails;
import com.pervazive.kheddah.repository.PANEDetailsRepository;
import com.pervazive.kheddah.service.PANEDetailsService;

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
 * Test class for the PANEDetailsResource REST controller.
 *
 * @see PANEDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PANEDetailsResourceIntTest {

    private static final String DEFAULT_DISTINGUISHEDNAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTINGUISHEDNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SITEID = "AAAAAAAAAA";
    private static final String UPDATED_SITEID = "BBBBBBBBBB";

    private static final String DEFAULT_SITENAME = "AAAAAAAAAA";
    private static final String UPDATED_SITENAME = "BBBBBBBBBB";

    private static final String DEFAULT_SITELOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SITELOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_SITEZONE = "AAAAAAAAAA";
    private static final String UPDATED_SITEZONE = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PANEDetailsRepository pANEDetailsRepository;

    @Inject
    private PANEDetailsService pANEDetailsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPANEDetailsMockMvc;

    private PANEDetails pANEDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PANEDetailsResource pANEDetailsResource = new PANEDetailsResource();
        ReflectionTestUtils.setField(pANEDetailsResource, "pANEDetailsService", pANEDetailsService);
        this.restPANEDetailsMockMvc = MockMvcBuilders.standaloneSetup(pANEDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PANEDetails createEntity(EntityManager em) {
        PANEDetails pANEDetails = new PANEDetails()
                .distinguishedname(DEFAULT_DISTINGUISHEDNAME)
                .siteid(DEFAULT_SITEID)
                .sitename(DEFAULT_SITENAME)
                .sitelocation(DEFAULT_SITELOCATION)
                .sitezone(DEFAULT_SITEZONE)
                .pastatus(DEFAULT_PASTATUS);
        return pANEDetails;
    }

    @Before
    public void initTest() {
        pANEDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPANEDetails() throws Exception {
        int databaseSizeBeforeCreate = pANEDetailsRepository.findAll().size();

        // Create the PANEDetails

        restPANEDetailsMockMvc.perform(post("/api/p-ane-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pANEDetails)))
            .andExpect(status().isCreated());

        // Validate the PANEDetails in the database
        List<PANEDetails> pANEDetailsList = pANEDetailsRepository.findAll();
        assertThat(pANEDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PANEDetails testPANEDetails = pANEDetailsList.get(pANEDetailsList.size() - 1);
        assertThat(testPANEDetails.getDistinguishedname()).isEqualTo(DEFAULT_DISTINGUISHEDNAME);
        assertThat(testPANEDetails.getSiteid()).isEqualTo(DEFAULT_SITEID);
        assertThat(testPANEDetails.getSitename()).isEqualTo(DEFAULT_SITENAME);
        assertThat(testPANEDetails.getSitelocation()).isEqualTo(DEFAULT_SITELOCATION);
        assertThat(testPANEDetails.getSitezone()).isEqualTo(DEFAULT_SITEZONE);
        assertThat(testPANEDetails.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPANEDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pANEDetailsRepository.findAll().size();

        // Create the PANEDetails with an existing ID
        PANEDetails existingPANEDetails = new PANEDetails();
        existingPANEDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPANEDetailsMockMvc.perform(post("/api/p-ane-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPANEDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PANEDetails> pANEDetailsList = pANEDetailsRepository.findAll();
        assertThat(pANEDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPANEDetails() throws Exception {
        // Initialize the database
        pANEDetailsRepository.saveAndFlush(pANEDetails);

        // Get all the pANEDetailsList
        restPANEDetailsMockMvc.perform(get("/api/p-ane-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pANEDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].distinguishedname").value(hasItem(DEFAULT_DISTINGUISHEDNAME.toString())))
            .andExpect(jsonPath("$.[*].siteid").value(hasItem(DEFAULT_SITEID.toString())))
            .andExpect(jsonPath("$.[*].sitename").value(hasItem(DEFAULT_SITENAME.toString())))
            .andExpect(jsonPath("$.[*].sitelocation").value(hasItem(DEFAULT_SITELOCATION.toString())))
            .andExpect(jsonPath("$.[*].sitezone").value(hasItem(DEFAULT_SITEZONE.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPANEDetails() throws Exception {
        // Initialize the database
        pANEDetailsRepository.saveAndFlush(pANEDetails);

        // Get the pANEDetails
        restPANEDetailsMockMvc.perform(get("/api/p-ane-details/{id}", pANEDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pANEDetails.getId().intValue()))
            .andExpect(jsonPath("$.distinguishedname").value(DEFAULT_DISTINGUISHEDNAME.toString()))
            .andExpect(jsonPath("$.siteid").value(DEFAULT_SITEID.toString()))
            .andExpect(jsonPath("$.sitename").value(DEFAULT_SITENAME.toString()))
            .andExpect(jsonPath("$.sitelocation").value(DEFAULT_SITELOCATION.toString()))
            .andExpect(jsonPath("$.sitezone").value(DEFAULT_SITEZONE.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPANEDetails() throws Exception {
        // Get the pANEDetails
        restPANEDetailsMockMvc.perform(get("/api/p-ane-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePANEDetails() throws Exception {
        // Initialize the database
        pANEDetailsService.save(pANEDetails);

        int databaseSizeBeforeUpdate = pANEDetailsRepository.findAll().size();

        // Update the pANEDetails
        PANEDetails updatedPANEDetails = pANEDetailsRepository.findOne(pANEDetails.getId());
        updatedPANEDetails
                .distinguishedname(UPDATED_DISTINGUISHEDNAME)
                .siteid(UPDATED_SITEID)
                .sitename(UPDATED_SITENAME)
                .sitelocation(UPDATED_SITELOCATION)
                .sitezone(UPDATED_SITEZONE)
                .pastatus(UPDATED_PASTATUS);

        restPANEDetailsMockMvc.perform(put("/api/p-ane-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPANEDetails)))
            .andExpect(status().isOk());

        // Validate the PANEDetails in the database
        List<PANEDetails> pANEDetailsList = pANEDetailsRepository.findAll();
        assertThat(pANEDetailsList).hasSize(databaseSizeBeforeUpdate);
        PANEDetails testPANEDetails = pANEDetailsList.get(pANEDetailsList.size() - 1);
        assertThat(testPANEDetails.getDistinguishedname()).isEqualTo(UPDATED_DISTINGUISHEDNAME);
        assertThat(testPANEDetails.getSiteid()).isEqualTo(UPDATED_SITEID);
        assertThat(testPANEDetails.getSitename()).isEqualTo(UPDATED_SITENAME);
        assertThat(testPANEDetails.getSitelocation()).isEqualTo(UPDATED_SITELOCATION);
        assertThat(testPANEDetails.getSitezone()).isEqualTo(UPDATED_SITEZONE);
        assertThat(testPANEDetails.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPANEDetails() throws Exception {
        int databaseSizeBeforeUpdate = pANEDetailsRepository.findAll().size();

        // Create the PANEDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPANEDetailsMockMvc.perform(put("/api/p-ane-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pANEDetails)))
            .andExpect(status().isCreated());

        // Validate the PANEDetails in the database
        List<PANEDetails> pANEDetailsList = pANEDetailsRepository.findAll();
        assertThat(pANEDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePANEDetails() throws Exception {
        // Initialize the database
        pANEDetailsService.save(pANEDetails);

        int databaseSizeBeforeDelete = pANEDetailsRepository.findAll().size();

        // Get the pANEDetails
        restPANEDetailsMockMvc.perform(delete("/api/p-ane-details/{id}", pANEDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PANEDetails> pANEDetailsList = pANEDetailsRepository.findAll();
        assertThat(pANEDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
