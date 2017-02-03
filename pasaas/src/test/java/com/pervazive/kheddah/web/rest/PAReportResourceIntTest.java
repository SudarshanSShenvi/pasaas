package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAReport;
import com.pervazive.kheddah.repository.PAReportRepository;
import com.pervazive.kheddah.service.PAReportService;

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
 * Test class for the PAReportResource REST controller.
 *
 * @see PAReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAReportResourceIntTest {

    private static final String DEFAULT_REPORTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_REPORTTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REPORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_REPORTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_REPORTPARMS = "AAAAAAAAAA";
    private static final String UPDATED_REPORTPARMS = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAReportRepository pAReportRepository;

    @Inject
    private PAReportService pAReportService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAReportMockMvc;

    private PAReport pAReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAReportResource pAReportResource = new PAReportResource();
        ReflectionTestUtils.setField(pAReportResource, "pAReportService", pAReportService);
        this.restPAReportMockMvc = MockMvcBuilders.standaloneSetup(pAReportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAReport createEntity(EntityManager em) {
        PAReport pAReport = new PAReport()
                .reporttype(DEFAULT_REPORTTYPE)
                .reportname(DEFAULT_REPORTNAME)
                .reportparms(DEFAULT_REPORTPARMS)
                .pastatus(DEFAULT_PASTATUS);
        return pAReport;
    }

    @Before
    public void initTest() {
        pAReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAReport() throws Exception {
        int databaseSizeBeforeCreate = pAReportRepository.findAll().size();

        // Create the PAReport

        restPAReportMockMvc.perform(post("/api/p-a-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAReport)))
            .andExpect(status().isCreated());

        // Validate the PAReport in the database
        List<PAReport> pAReportList = pAReportRepository.findAll();
        assertThat(pAReportList).hasSize(databaseSizeBeforeCreate + 1);
        PAReport testPAReport = pAReportList.get(pAReportList.size() - 1);
        assertThat(testPAReport.getReporttype()).isEqualTo(DEFAULT_REPORTTYPE);
        assertThat(testPAReport.getReportname()).isEqualTo(DEFAULT_REPORTNAME);
        assertThat(testPAReport.getReportparms()).isEqualTo(DEFAULT_REPORTPARMS);
        assertThat(testPAReport.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAReportRepository.findAll().size();

        // Create the PAReport with an existing ID
        PAReport existingPAReport = new PAReport();
        existingPAReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAReportMockMvc.perform(post("/api/p-a-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAReport)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAReport> pAReportList = pAReportRepository.findAll();
        assertThat(pAReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAReports() throws Exception {
        // Initialize the database
        pAReportRepository.saveAndFlush(pAReport);

        // Get all the pAReportList
        restPAReportMockMvc.perform(get("/api/p-a-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].reporttype").value(hasItem(DEFAULT_REPORTTYPE.toString())))
            .andExpect(jsonPath("$.[*].reportname").value(hasItem(DEFAULT_REPORTNAME.toString())))
            .andExpect(jsonPath("$.[*].reportparms").value(hasItem(DEFAULT_REPORTPARMS.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAReport() throws Exception {
        // Initialize the database
        pAReportRepository.saveAndFlush(pAReport);

        // Get the pAReport
        restPAReportMockMvc.perform(get("/api/p-a-reports/{id}", pAReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAReport.getId().intValue()))
            .andExpect(jsonPath("$.reporttype").value(DEFAULT_REPORTTYPE.toString()))
            .andExpect(jsonPath("$.reportname").value(DEFAULT_REPORTNAME.toString()))
            .andExpect(jsonPath("$.reportparms").value(DEFAULT_REPORTPARMS.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAReport() throws Exception {
        // Get the pAReport
        restPAReportMockMvc.perform(get("/api/p-a-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAReport() throws Exception {
        // Initialize the database
        pAReportService.save(pAReport);

        int databaseSizeBeforeUpdate = pAReportRepository.findAll().size();

        // Update the pAReport
        PAReport updatedPAReport = pAReportRepository.findOne(pAReport.getId());
        updatedPAReport
                .reporttype(UPDATED_REPORTTYPE)
                .reportname(UPDATED_REPORTNAME)
                .reportparms(UPDATED_REPORTPARMS)
                .pastatus(UPDATED_PASTATUS);

        restPAReportMockMvc.perform(put("/api/p-a-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAReport)))
            .andExpect(status().isOk());

        // Validate the PAReport in the database
        List<PAReport> pAReportList = pAReportRepository.findAll();
        assertThat(pAReportList).hasSize(databaseSizeBeforeUpdate);
        PAReport testPAReport = pAReportList.get(pAReportList.size() - 1);
        assertThat(testPAReport.getReporttype()).isEqualTo(UPDATED_REPORTTYPE);
        assertThat(testPAReport.getReportname()).isEqualTo(UPDATED_REPORTNAME);
        assertThat(testPAReport.getReportparms()).isEqualTo(UPDATED_REPORTPARMS);
        assertThat(testPAReport.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAReport() throws Exception {
        int databaseSizeBeforeUpdate = pAReportRepository.findAll().size();

        // Create the PAReport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAReportMockMvc.perform(put("/api/p-a-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAReport)))
            .andExpect(status().isCreated());

        // Validate the PAReport in the database
        List<PAReport> pAReportList = pAReportRepository.findAll();
        assertThat(pAReportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAReport() throws Exception {
        // Initialize the database
        pAReportService.save(pAReport);

        int databaseSizeBeforeDelete = pAReportRepository.findAll().size();

        // Get the pAReport
        restPAReportMockMvc.perform(delete("/api/p-a-reports/{id}", pAReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAReport> pAReportList = pAReportRepository.findAll();
        assertThat(pAReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
