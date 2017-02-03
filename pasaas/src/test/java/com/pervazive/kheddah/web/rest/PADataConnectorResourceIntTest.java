package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PADataConnector;
import com.pervazive.kheddah.repository.PADataConnectorRepository;
import com.pervazive.kheddah.service.PADataConnectorService;

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

import com.pervazive.kheddah.domain.enumeration.DCType;
import com.pervazive.kheddah.domain.enumeration.PAStatus;
/**
 * Test class for the PADataConnectorResource REST controller.
 *
 * @see PADataConnectorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PADataConnectorResourceIntTest {

    private static final String DEFAULT_DCNAME = "AAAAAAAAAA";
    private static final String UPDATED_DCNAME = "BBBBBBBBBB";

    private static final DCType DEFAULT_DCTYPE = DCType.JSR;
    private static final DCType UPDATED_DCTYPE = DCType.RDBMS;

    private static final String DEFAULT_URLLINK = "AAAAAAAAAA";
    private static final String UPDATED_URLLINK = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTEUNAME = "AAAAAAAAAA";
    private static final String UPDATED_REMOTEUNAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTEPWD = "AAAAAAAAAA";
    private static final String UPDATED_REMOTEPWD = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTEIP = "AAAAAAAAAA";
    private static final String UPDATED_REMOTEIP = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final String DEFAULT_LOCALPATH = "AAAAAAAAAA";
    private static final String UPDATED_LOCALPATH = "BBBBBBBBBB";

    private static final String DEFAULT_JSRPWD = "AAAAAAAAAA";
    private static final String UPDATED_JSRPWD = "BBBBBBBBBB";

    private static final String DEFAULT_JSRUSER = "AAAAAAAAAA";
    private static final String UPDATED_JSRUSER = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATIONPATH = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATIONPATH = "BBBBBBBBBB";

    private static final Integer DEFAULT_RETRIEVE = 1;
    private static final Integer UPDATED_RETRIEVE = 2;

    private static final Integer DEFAULT_RETRIEVEDAYS = 1;
    private static final Integer UPDATED_RETRIEVEDAYS = 2;

    private static final String DEFAULT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_MODE = "BBBBBBBBBB";

    private static final String DEFAULT_DATAMODE = "AAAAAAAAAA";
    private static final String UPDATED_DATAMODE = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PADataConnectorRepository pADataConnectorRepository;

    @Inject
    private PADataConnectorService pADataConnectorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPADataConnectorMockMvc;

    private PADataConnector pADataConnector;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PADataConnectorResource pADataConnectorResource = new PADataConnectorResource();
        ReflectionTestUtils.setField(pADataConnectorResource, "pADataConnectorService", pADataConnectorService);
        this.restPADataConnectorMockMvc = MockMvcBuilders.standaloneSetup(pADataConnectorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PADataConnector createEntity(EntityManager em) {
        PADataConnector pADataConnector = new PADataConnector()
                .dcname(DEFAULT_DCNAME)
                .dctype(DEFAULT_DCTYPE)
                .urllink(DEFAULT_URLLINK)
                .remoteuname(DEFAULT_REMOTEUNAME)
                .remotepwd(DEFAULT_REMOTEPWD)
                .remoteip(DEFAULT_REMOTEIP)
                .port(DEFAULT_PORT)
                .localpath(DEFAULT_LOCALPATH)
                .jsrpwd(DEFAULT_JSRPWD)
                .jsruser(DEFAULT_JSRUSER)
                .destinationpath(DEFAULT_DESTINATIONPATH)
                .retrieve(DEFAULT_RETRIEVE)
                .retrievedays(DEFAULT_RETRIEVEDAYS)
                .mode(DEFAULT_MODE)
                .datamode(DEFAULT_DATAMODE)
                .pastatus(DEFAULT_PASTATUS);
        return pADataConnector;
    }

    @Before
    public void initTest() {
        pADataConnector = createEntity(em);
    }

    @Test
    @Transactional
    public void createPADataConnector() throws Exception {
        int databaseSizeBeforeCreate = pADataConnectorRepository.findAll().size();

        // Create the PADataConnector

        restPADataConnectorMockMvc.perform(post("/api/p-a-data-connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pADataConnector)))
            .andExpect(status().isCreated());

        // Validate the PADataConnector in the database
        List<PADataConnector> pADataConnectorList = pADataConnectorRepository.findAll();
        assertThat(pADataConnectorList).hasSize(databaseSizeBeforeCreate + 1);
        PADataConnector testPADataConnector = pADataConnectorList.get(pADataConnectorList.size() - 1);
        assertThat(testPADataConnector.getDcname()).isEqualTo(DEFAULT_DCNAME);
        assertThat(testPADataConnector.getDctype()).isEqualTo(DEFAULT_DCTYPE);
        assertThat(testPADataConnector.getUrllink()).isEqualTo(DEFAULT_URLLINK);
        assertThat(testPADataConnector.getRemoteuname()).isEqualTo(DEFAULT_REMOTEUNAME);
        assertThat(testPADataConnector.getRemotepwd()).isEqualTo(DEFAULT_REMOTEPWD);
        assertThat(testPADataConnector.getRemoteip()).isEqualTo(DEFAULT_REMOTEIP);
        assertThat(testPADataConnector.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testPADataConnector.getLocalpath()).isEqualTo(DEFAULT_LOCALPATH);
        assertThat(testPADataConnector.getJsrpwd()).isEqualTo(DEFAULT_JSRPWD);
        assertThat(testPADataConnector.getJsruser()).isEqualTo(DEFAULT_JSRUSER);
        assertThat(testPADataConnector.getDestinationpath()).isEqualTo(DEFAULT_DESTINATIONPATH);
        assertThat(testPADataConnector.getRetrieve()).isEqualTo(DEFAULT_RETRIEVE);
        assertThat(testPADataConnector.getRetrievedays()).isEqualTo(DEFAULT_RETRIEVEDAYS);
        assertThat(testPADataConnector.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testPADataConnector.getDatamode()).isEqualTo(DEFAULT_DATAMODE);
        assertThat(testPADataConnector.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPADataConnectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pADataConnectorRepository.findAll().size();

        // Create the PADataConnector with an existing ID
        PADataConnector existingPADataConnector = new PADataConnector();
        existingPADataConnector.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPADataConnectorMockMvc.perform(post("/api/p-a-data-connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPADataConnector)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PADataConnector> pADataConnectorList = pADataConnectorRepository.findAll();
        assertThat(pADataConnectorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPADataConnectors() throws Exception {
        // Initialize the database
        pADataConnectorRepository.saveAndFlush(pADataConnector);

        // Get all the pADataConnectorList
        restPADataConnectorMockMvc.perform(get("/api/p-a-data-connectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pADataConnector.getId().intValue())))
            .andExpect(jsonPath("$.[*].dcname").value(hasItem(DEFAULT_DCNAME.toString())))
            .andExpect(jsonPath("$.[*].dctype").value(hasItem(DEFAULT_DCTYPE.toString())))
            .andExpect(jsonPath("$.[*].urllink").value(hasItem(DEFAULT_URLLINK.toString())))
            .andExpect(jsonPath("$.[*].remoteuname").value(hasItem(DEFAULT_REMOTEUNAME.toString())))
            .andExpect(jsonPath("$.[*].remotepwd").value(hasItem(DEFAULT_REMOTEPWD.toString())))
            .andExpect(jsonPath("$.[*].remoteip").value(hasItem(DEFAULT_REMOTEIP.toString())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].localpath").value(hasItem(DEFAULT_LOCALPATH.toString())))
            .andExpect(jsonPath("$.[*].jsrpwd").value(hasItem(DEFAULT_JSRPWD.toString())))
            .andExpect(jsonPath("$.[*].jsruser").value(hasItem(DEFAULT_JSRUSER.toString())))
            .andExpect(jsonPath("$.[*].destinationpath").value(hasItem(DEFAULT_DESTINATIONPATH.toString())))
            .andExpect(jsonPath("$.[*].retrieve").value(hasItem(DEFAULT_RETRIEVE)))
            .andExpect(jsonPath("$.[*].retrievedays").value(hasItem(DEFAULT_RETRIEVEDAYS)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())))
            .andExpect(jsonPath("$.[*].datamode").value(hasItem(DEFAULT_DATAMODE.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPADataConnector() throws Exception {
        // Initialize the database
        pADataConnectorRepository.saveAndFlush(pADataConnector);

        // Get the pADataConnector
        restPADataConnectorMockMvc.perform(get("/api/p-a-data-connectors/{id}", pADataConnector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pADataConnector.getId().intValue()))
            .andExpect(jsonPath("$.dcname").value(DEFAULT_DCNAME.toString()))
            .andExpect(jsonPath("$.dctype").value(DEFAULT_DCTYPE.toString()))
            .andExpect(jsonPath("$.urllink").value(DEFAULT_URLLINK.toString()))
            .andExpect(jsonPath("$.remoteuname").value(DEFAULT_REMOTEUNAME.toString()))
            .andExpect(jsonPath("$.remotepwd").value(DEFAULT_REMOTEPWD.toString()))
            .andExpect(jsonPath("$.remoteip").value(DEFAULT_REMOTEIP.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.localpath").value(DEFAULT_LOCALPATH.toString()))
            .andExpect(jsonPath("$.jsrpwd").value(DEFAULT_JSRPWD.toString()))
            .andExpect(jsonPath("$.jsruser").value(DEFAULT_JSRUSER.toString()))
            .andExpect(jsonPath("$.destinationpath").value(DEFAULT_DESTINATIONPATH.toString()))
            .andExpect(jsonPath("$.retrieve").value(DEFAULT_RETRIEVE))
            .andExpect(jsonPath("$.retrievedays").value(DEFAULT_RETRIEVEDAYS))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()))
            .andExpect(jsonPath("$.datamode").value(DEFAULT_DATAMODE.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPADataConnector() throws Exception {
        // Get the pADataConnector
        restPADataConnectorMockMvc.perform(get("/api/p-a-data-connectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePADataConnector() throws Exception {
        // Initialize the database
        pADataConnectorService.save(pADataConnector);

        int databaseSizeBeforeUpdate = pADataConnectorRepository.findAll().size();

        // Update the pADataConnector
        PADataConnector updatedPADataConnector = pADataConnectorRepository.findOne(pADataConnector.getId());
        updatedPADataConnector
                .dcname(UPDATED_DCNAME)
                .dctype(UPDATED_DCTYPE)
                .urllink(UPDATED_URLLINK)
                .remoteuname(UPDATED_REMOTEUNAME)
                .remotepwd(UPDATED_REMOTEPWD)
                .remoteip(UPDATED_REMOTEIP)
                .port(UPDATED_PORT)
                .localpath(UPDATED_LOCALPATH)
                .jsrpwd(UPDATED_JSRPWD)
                .jsruser(UPDATED_JSRUSER)
                .destinationpath(UPDATED_DESTINATIONPATH)
                .retrieve(UPDATED_RETRIEVE)
                .retrievedays(UPDATED_RETRIEVEDAYS)
                .mode(UPDATED_MODE)
                .datamode(UPDATED_DATAMODE)
                .pastatus(UPDATED_PASTATUS);

        restPADataConnectorMockMvc.perform(put("/api/p-a-data-connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPADataConnector)))
            .andExpect(status().isOk());

        // Validate the PADataConnector in the database
        List<PADataConnector> pADataConnectorList = pADataConnectorRepository.findAll();
        assertThat(pADataConnectorList).hasSize(databaseSizeBeforeUpdate);
        PADataConnector testPADataConnector = pADataConnectorList.get(pADataConnectorList.size() - 1);
        assertThat(testPADataConnector.getDcname()).isEqualTo(UPDATED_DCNAME);
        assertThat(testPADataConnector.getDctype()).isEqualTo(UPDATED_DCTYPE);
        assertThat(testPADataConnector.getUrllink()).isEqualTo(UPDATED_URLLINK);
        assertThat(testPADataConnector.getRemoteuname()).isEqualTo(UPDATED_REMOTEUNAME);
        assertThat(testPADataConnector.getRemotepwd()).isEqualTo(UPDATED_REMOTEPWD);
        assertThat(testPADataConnector.getRemoteip()).isEqualTo(UPDATED_REMOTEIP);
        assertThat(testPADataConnector.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testPADataConnector.getLocalpath()).isEqualTo(UPDATED_LOCALPATH);
        assertThat(testPADataConnector.getJsrpwd()).isEqualTo(UPDATED_JSRPWD);
        assertThat(testPADataConnector.getJsruser()).isEqualTo(UPDATED_JSRUSER);
        assertThat(testPADataConnector.getDestinationpath()).isEqualTo(UPDATED_DESTINATIONPATH);
        assertThat(testPADataConnector.getRetrieve()).isEqualTo(UPDATED_RETRIEVE);
        assertThat(testPADataConnector.getRetrievedays()).isEqualTo(UPDATED_RETRIEVEDAYS);
        assertThat(testPADataConnector.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testPADataConnector.getDatamode()).isEqualTo(UPDATED_DATAMODE);
        assertThat(testPADataConnector.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPADataConnector() throws Exception {
        int databaseSizeBeforeUpdate = pADataConnectorRepository.findAll().size();

        // Create the PADataConnector

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPADataConnectorMockMvc.perform(put("/api/p-a-data-connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pADataConnector)))
            .andExpect(status().isCreated());

        // Validate the PADataConnector in the database
        List<PADataConnector> pADataConnectorList = pADataConnectorRepository.findAll();
        assertThat(pADataConnectorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePADataConnector() throws Exception {
        // Initialize the database
        pADataConnectorService.save(pADataConnector);

        int databaseSizeBeforeDelete = pADataConnectorRepository.findAll().size();

        // Get the pADataConnector
        restPADataConnectorMockMvc.perform(delete("/api/p-a-data-connectors/{id}", pADataConnector.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PADataConnector> pADataConnectorList = pADataConnectorRepository.findAll();
        assertThat(pADataConnectorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
