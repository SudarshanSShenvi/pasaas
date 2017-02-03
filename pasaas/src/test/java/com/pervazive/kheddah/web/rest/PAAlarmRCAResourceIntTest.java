package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAAlarmRCA;
import com.pervazive.kheddah.repository.PAAlarmRCARepository;
import com.pervazive.kheddah.service.PAAlarmRCAService;

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
 * Test class for the PAAlarmRCAResource REST controller.
 *
 * @see PAAlarmRCAResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAAlarmRCAResourceIntTest {

    private static final String DEFAULT_ALARMNO = "AAAAAAAAAA";
    private static final String UPDATED_ALARMNO = "BBBBBBBBBB";

    private static final String DEFAULT_ALARMTEXT = "AAAAAAAAAA";
    private static final String UPDATED_ALARMTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_ALARMTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ALARMTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RCDETAILS = "AAAAAAAAAA";
    private static final String UPDATED_RCDETAILS = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAAlarmRCARepository pAAlarmRCARepository;

    @Inject
    private PAAlarmRCAService pAAlarmRCAService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAAlarmRCAMockMvc;

    private PAAlarmRCA pAAlarmRCA;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAAlarmRCAResource pAAlarmRCAResource = new PAAlarmRCAResource();
        ReflectionTestUtils.setField(pAAlarmRCAResource, "pAAlarmRCAService", pAAlarmRCAService);
        this.restPAAlarmRCAMockMvc = MockMvcBuilders.standaloneSetup(pAAlarmRCAResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAAlarmRCA createEntity(EntityManager em) {
        PAAlarmRCA pAAlarmRCA = new PAAlarmRCA()
                .alarmno(DEFAULT_ALARMNO)
                .alarmtext(DEFAULT_ALARMTEXT)
                .alarmtype(DEFAULT_ALARMTYPE)
                .rcdetails(DEFAULT_RCDETAILS)
                .pastatus(DEFAULT_PASTATUS);
        return pAAlarmRCA;
    }

    @Before
    public void initTest() {
        pAAlarmRCA = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAAlarmRCA() throws Exception {
        int databaseSizeBeforeCreate = pAAlarmRCARepository.findAll().size();

        // Create the PAAlarmRCA

        restPAAlarmRCAMockMvc.perform(post("/api/p-a-alarm-rcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAAlarmRCA)))
            .andExpect(status().isCreated());

        // Validate the PAAlarmRCA in the database
        List<PAAlarmRCA> pAAlarmRCAList = pAAlarmRCARepository.findAll();
        assertThat(pAAlarmRCAList).hasSize(databaseSizeBeforeCreate + 1);
        PAAlarmRCA testPAAlarmRCA = pAAlarmRCAList.get(pAAlarmRCAList.size() - 1);
        assertThat(testPAAlarmRCA.getAlarmno()).isEqualTo(DEFAULT_ALARMNO);
        assertThat(testPAAlarmRCA.getAlarmtext()).isEqualTo(DEFAULT_ALARMTEXT);
        assertThat(testPAAlarmRCA.getAlarmtype()).isEqualTo(DEFAULT_ALARMTYPE);
        assertThat(testPAAlarmRCA.getRcdetails()).isEqualTo(DEFAULT_RCDETAILS);
        assertThat(testPAAlarmRCA.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAAlarmRCAWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAAlarmRCARepository.findAll().size();

        // Create the PAAlarmRCA with an existing ID
        PAAlarmRCA existingPAAlarmRCA = new PAAlarmRCA();
        existingPAAlarmRCA.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAAlarmRCAMockMvc.perform(post("/api/p-a-alarm-rcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAAlarmRCA)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAAlarmRCA> pAAlarmRCAList = pAAlarmRCARepository.findAll();
        assertThat(pAAlarmRCAList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAAlarmRCAS() throws Exception {
        // Initialize the database
        pAAlarmRCARepository.saveAndFlush(pAAlarmRCA);

        // Get all the pAAlarmRCAList
        restPAAlarmRCAMockMvc.perform(get("/api/p-a-alarm-rcas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAAlarmRCA.getId().intValue())))
            .andExpect(jsonPath("$.[*].alarmno").value(hasItem(DEFAULT_ALARMNO.toString())))
            .andExpect(jsonPath("$.[*].alarmtext").value(hasItem(DEFAULT_ALARMTEXT.toString())))
            .andExpect(jsonPath("$.[*].alarmtype").value(hasItem(DEFAULT_ALARMTYPE.toString())))
            .andExpect(jsonPath("$.[*].rcdetails").value(hasItem(DEFAULT_RCDETAILS.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAAlarmRCA() throws Exception {
        // Initialize the database
        pAAlarmRCARepository.saveAndFlush(pAAlarmRCA);

        // Get the pAAlarmRCA
        restPAAlarmRCAMockMvc.perform(get("/api/p-a-alarm-rcas/{id}", pAAlarmRCA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAAlarmRCA.getId().intValue()))
            .andExpect(jsonPath("$.alarmno").value(DEFAULT_ALARMNO.toString()))
            .andExpect(jsonPath("$.alarmtext").value(DEFAULT_ALARMTEXT.toString()))
            .andExpect(jsonPath("$.alarmtype").value(DEFAULT_ALARMTYPE.toString()))
            .andExpect(jsonPath("$.rcdetails").value(DEFAULT_RCDETAILS.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAAlarmRCA() throws Exception {
        // Get the pAAlarmRCA
        restPAAlarmRCAMockMvc.perform(get("/api/p-a-alarm-rcas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAAlarmRCA() throws Exception {
        // Initialize the database
        pAAlarmRCAService.save(pAAlarmRCA);

        int databaseSizeBeforeUpdate = pAAlarmRCARepository.findAll().size();

        // Update the pAAlarmRCA
        PAAlarmRCA updatedPAAlarmRCA = pAAlarmRCARepository.findOne(pAAlarmRCA.getId());
        updatedPAAlarmRCA
                .alarmno(UPDATED_ALARMNO)
                .alarmtext(UPDATED_ALARMTEXT)
                .alarmtype(UPDATED_ALARMTYPE)
                .rcdetails(UPDATED_RCDETAILS)
                .pastatus(UPDATED_PASTATUS);

        restPAAlarmRCAMockMvc.perform(put("/api/p-a-alarm-rcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAAlarmRCA)))
            .andExpect(status().isOk());

        // Validate the PAAlarmRCA in the database
        List<PAAlarmRCA> pAAlarmRCAList = pAAlarmRCARepository.findAll();
        assertThat(pAAlarmRCAList).hasSize(databaseSizeBeforeUpdate);
        PAAlarmRCA testPAAlarmRCA = pAAlarmRCAList.get(pAAlarmRCAList.size() - 1);
        assertThat(testPAAlarmRCA.getAlarmno()).isEqualTo(UPDATED_ALARMNO);
        assertThat(testPAAlarmRCA.getAlarmtext()).isEqualTo(UPDATED_ALARMTEXT);
        assertThat(testPAAlarmRCA.getAlarmtype()).isEqualTo(UPDATED_ALARMTYPE);
        assertThat(testPAAlarmRCA.getRcdetails()).isEqualTo(UPDATED_RCDETAILS);
        assertThat(testPAAlarmRCA.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAAlarmRCA() throws Exception {
        int databaseSizeBeforeUpdate = pAAlarmRCARepository.findAll().size();

        // Create the PAAlarmRCA

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAAlarmRCAMockMvc.perform(put("/api/p-a-alarm-rcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAAlarmRCA)))
            .andExpect(status().isCreated());

        // Validate the PAAlarmRCA in the database
        List<PAAlarmRCA> pAAlarmRCAList = pAAlarmRCARepository.findAll();
        assertThat(pAAlarmRCAList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAAlarmRCA() throws Exception {
        // Initialize the database
        pAAlarmRCAService.save(pAAlarmRCA);

        int databaseSizeBeforeDelete = pAAlarmRCARepository.findAll().size();

        // Get the pAAlarmRCA
        restPAAlarmRCAMockMvc.perform(delete("/api/p-a-alarm-rcas/{id}", pAAlarmRCA.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAAlarmRCA> pAAlarmRCAList = pAAlarmRCARepository.findAll();
        assertThat(pAAlarmRCAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
