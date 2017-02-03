package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAPMTRequest;
import com.pervazive.kheddah.repository.PAPMTRequestRepository;
import com.pervazive.kheddah.service.PAPMTRequestService;

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

import com.pervazive.kheddah.domain.enumeration.PMTCategory;
import com.pervazive.kheddah.domain.enumeration.PMTStatus;
import com.pervazive.kheddah.domain.enumeration.PMTPriority;
/**
 * Test class for the PAPMTRequestResource REST controller.
 *
 * @see PAPMTRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAPMTRequestResourceIntTest {

    private static final PMTCategory DEFAULT_CATEGORY = PMTCategory.Cat1;
    private static final PMTCategory UPDATED_CATEGORY = PMTCategory.Cat2;

    private static final String DEFAULT_PMTDESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PMTDESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PMTREASON = "AAAAAAAAAA";
    private static final String UPDATED_PMTREASON = "BBBBBBBBBB";

    private static final String DEFAULT_PMTTITLE = "AAAAAAAAAA";
    private static final String UPDATED_PMTTITLE = "BBBBBBBBBB";

    private static final String DEFAULT_PMTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_PMTTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SITEID = "AAAAAAAAAA";
    private static final String UPDATED_SITEID = "BBBBBBBBBB";

    private static final String DEFAULT_SITENAME = "AAAAAAAAAA";
    private static final String UPDATED_SITENAME = "BBBBBBBBBB";

    private static final String DEFAULT_SITEPRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_SITEPRIORITY = "BBBBBBBBBB";

    private static final PMTStatus DEFAULT_PMTSTATUS = PMTStatus.Open;
    private static final PMTStatus UPDATED_PMTSTATUS = PMTStatus.Assigned;

    private static final PMTPriority DEFAULT_PMTPRIORITY = PMTPriority.P1;
    private static final PMTPriority UPDATED_PMTPRIORITY = PMTPriority.P2;

    @Inject
    private PAPMTRequestRepository pAPMTRequestRepository;

    @Inject
    private PAPMTRequestService pAPMTRequestService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAPMTRequestMockMvc;

    private PAPMTRequest pAPMTRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAPMTRequestResource pAPMTRequestResource = new PAPMTRequestResource();
        ReflectionTestUtils.setField(pAPMTRequestResource, "pAPMTRequestService", pAPMTRequestService);
        this.restPAPMTRequestMockMvc = MockMvcBuilders.standaloneSetup(pAPMTRequestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAPMTRequest createEntity(EntityManager em) {
        PAPMTRequest pAPMTRequest = new PAPMTRequest()
                .category(DEFAULT_CATEGORY)
                .pmtdescription(DEFAULT_PMTDESCRIPTION)
                .pmtreason(DEFAULT_PMTREASON)
                .pmttitle(DEFAULT_PMTTITLE)
                .pmttype(DEFAULT_PMTTYPE)
                .siteid(DEFAULT_SITEID)
                .sitename(DEFAULT_SITENAME)
                .sitepriority(DEFAULT_SITEPRIORITY)
                .pmtstatus(DEFAULT_PMTSTATUS)
                .pmtpriority(DEFAULT_PMTPRIORITY);
        return pAPMTRequest;
    }

    @Before
    public void initTest() {
        pAPMTRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAPMTRequest() throws Exception {
        int databaseSizeBeforeCreate = pAPMTRequestRepository.findAll().size();

        // Create the PAPMTRequest

        restPAPMTRequestMockMvc.perform(post("/api/p-apmt-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAPMTRequest)))
            .andExpect(status().isCreated());

        // Validate the PAPMTRequest in the database
        List<PAPMTRequest> pAPMTRequestList = pAPMTRequestRepository.findAll();
        assertThat(pAPMTRequestList).hasSize(databaseSizeBeforeCreate + 1);
        PAPMTRequest testPAPMTRequest = pAPMTRequestList.get(pAPMTRequestList.size() - 1);
        assertThat(testPAPMTRequest.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testPAPMTRequest.getPmtdescription()).isEqualTo(DEFAULT_PMTDESCRIPTION);
        assertThat(testPAPMTRequest.getPmtreason()).isEqualTo(DEFAULT_PMTREASON);
        assertThat(testPAPMTRequest.getPmttitle()).isEqualTo(DEFAULT_PMTTITLE);
        assertThat(testPAPMTRequest.getPmttype()).isEqualTo(DEFAULT_PMTTYPE);
        assertThat(testPAPMTRequest.getSiteid()).isEqualTo(DEFAULT_SITEID);
        assertThat(testPAPMTRequest.getSitename()).isEqualTo(DEFAULT_SITENAME);
        assertThat(testPAPMTRequest.getSitepriority()).isEqualTo(DEFAULT_SITEPRIORITY);
        assertThat(testPAPMTRequest.getPmtstatus()).isEqualTo(DEFAULT_PMTSTATUS);
        assertThat(testPAPMTRequest.getPmtpriority()).isEqualTo(DEFAULT_PMTPRIORITY);
    }

    @Test
    @Transactional
    public void createPAPMTRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAPMTRequestRepository.findAll().size();

        // Create the PAPMTRequest with an existing ID
        PAPMTRequest existingPAPMTRequest = new PAPMTRequest();
        existingPAPMTRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAPMTRequestMockMvc.perform(post("/api/p-apmt-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAPMTRequest)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAPMTRequest> pAPMTRequestList = pAPMTRequestRepository.findAll();
        assertThat(pAPMTRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAPMTRequests() throws Exception {
        // Initialize the database
        pAPMTRequestRepository.saveAndFlush(pAPMTRequest);

        // Get all the pAPMTRequestList
        restPAPMTRequestMockMvc.perform(get("/api/p-apmt-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAPMTRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].pmtdescription").value(hasItem(DEFAULT_PMTDESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].pmtreason").value(hasItem(DEFAULT_PMTREASON.toString())))
            .andExpect(jsonPath("$.[*].pmttitle").value(hasItem(DEFAULT_PMTTITLE.toString())))
            .andExpect(jsonPath("$.[*].pmttype").value(hasItem(DEFAULT_PMTTYPE.toString())))
            .andExpect(jsonPath("$.[*].siteid").value(hasItem(DEFAULT_SITEID.toString())))
            .andExpect(jsonPath("$.[*].sitename").value(hasItem(DEFAULT_SITENAME.toString())))
            .andExpect(jsonPath("$.[*].sitepriority").value(hasItem(DEFAULT_SITEPRIORITY.toString())))
            .andExpect(jsonPath("$.[*].pmtstatus").value(hasItem(DEFAULT_PMTSTATUS.toString())))
            .andExpect(jsonPath("$.[*].pmtpriority").value(hasItem(DEFAULT_PMTPRIORITY.toString())));
    }

    @Test
    @Transactional
    public void getPAPMTRequest() throws Exception {
        // Initialize the database
        pAPMTRequestRepository.saveAndFlush(pAPMTRequest);

        // Get the pAPMTRequest
        restPAPMTRequestMockMvc.perform(get("/api/p-apmt-requests/{id}", pAPMTRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAPMTRequest.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.pmtdescription").value(DEFAULT_PMTDESCRIPTION.toString()))
            .andExpect(jsonPath("$.pmtreason").value(DEFAULT_PMTREASON.toString()))
            .andExpect(jsonPath("$.pmttitle").value(DEFAULT_PMTTITLE.toString()))
            .andExpect(jsonPath("$.pmttype").value(DEFAULT_PMTTYPE.toString()))
            .andExpect(jsonPath("$.siteid").value(DEFAULT_SITEID.toString()))
            .andExpect(jsonPath("$.sitename").value(DEFAULT_SITENAME.toString()))
            .andExpect(jsonPath("$.sitepriority").value(DEFAULT_SITEPRIORITY.toString()))
            .andExpect(jsonPath("$.pmtstatus").value(DEFAULT_PMTSTATUS.toString()))
            .andExpect(jsonPath("$.pmtpriority").value(DEFAULT_PMTPRIORITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAPMTRequest() throws Exception {
        // Get the pAPMTRequest
        restPAPMTRequestMockMvc.perform(get("/api/p-apmt-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAPMTRequest() throws Exception {
        // Initialize the database
        pAPMTRequestService.save(pAPMTRequest);

        int databaseSizeBeforeUpdate = pAPMTRequestRepository.findAll().size();

        // Update the pAPMTRequest
        PAPMTRequest updatedPAPMTRequest = pAPMTRequestRepository.findOne(pAPMTRequest.getId());
        updatedPAPMTRequest
                .category(UPDATED_CATEGORY)
                .pmtdescription(UPDATED_PMTDESCRIPTION)
                .pmtreason(UPDATED_PMTREASON)
                .pmttitle(UPDATED_PMTTITLE)
                .pmttype(UPDATED_PMTTYPE)
                .siteid(UPDATED_SITEID)
                .sitename(UPDATED_SITENAME)
                .sitepriority(UPDATED_SITEPRIORITY)
                .pmtstatus(UPDATED_PMTSTATUS)
                .pmtpriority(UPDATED_PMTPRIORITY);

        restPAPMTRequestMockMvc.perform(put("/api/p-apmt-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAPMTRequest)))
            .andExpect(status().isOk());

        // Validate the PAPMTRequest in the database
        List<PAPMTRequest> pAPMTRequestList = pAPMTRequestRepository.findAll();
        assertThat(pAPMTRequestList).hasSize(databaseSizeBeforeUpdate);
        PAPMTRequest testPAPMTRequest = pAPMTRequestList.get(pAPMTRequestList.size() - 1);
        assertThat(testPAPMTRequest.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPAPMTRequest.getPmtdescription()).isEqualTo(UPDATED_PMTDESCRIPTION);
        assertThat(testPAPMTRequest.getPmtreason()).isEqualTo(UPDATED_PMTREASON);
        assertThat(testPAPMTRequest.getPmttitle()).isEqualTo(UPDATED_PMTTITLE);
        assertThat(testPAPMTRequest.getPmttype()).isEqualTo(UPDATED_PMTTYPE);
        assertThat(testPAPMTRequest.getSiteid()).isEqualTo(UPDATED_SITEID);
        assertThat(testPAPMTRequest.getSitename()).isEqualTo(UPDATED_SITENAME);
        assertThat(testPAPMTRequest.getSitepriority()).isEqualTo(UPDATED_SITEPRIORITY);
        assertThat(testPAPMTRequest.getPmtstatus()).isEqualTo(UPDATED_PMTSTATUS);
        assertThat(testPAPMTRequest.getPmtpriority()).isEqualTo(UPDATED_PMTPRIORITY);
    }

    @Test
    @Transactional
    public void updateNonExistingPAPMTRequest() throws Exception {
        int databaseSizeBeforeUpdate = pAPMTRequestRepository.findAll().size();

        // Create the PAPMTRequest

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAPMTRequestMockMvc.perform(put("/api/p-apmt-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAPMTRequest)))
            .andExpect(status().isCreated());

        // Validate the PAPMTRequest in the database
        List<PAPMTRequest> pAPMTRequestList = pAPMTRequestRepository.findAll();
        assertThat(pAPMTRequestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAPMTRequest() throws Exception {
        // Initialize the database
        pAPMTRequestService.save(pAPMTRequest);

        int databaseSizeBeforeDelete = pAPMTRequestRepository.findAll().size();

        // Get the pAPMTRequest
        restPAPMTRequestMockMvc.perform(delete("/api/p-apmt-requests/{id}", pAPMTRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAPMTRequest> pAPMTRequestList = pAPMTRequestRepository.findAll();
        assertThat(pAPMTRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
