package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAFileUpload;
import com.pervazive.kheddah.repository.PAFileUploadRepository;
import com.pervazive.kheddah.service.PAFileUploadService;

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
 * Test class for the PAFileUploadResource REST controller.
 *
 * @see PAFileUploadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAFileUploadResourceIntTest {

    private static final String DEFAULT_REMOTEPWD = "AAAAAAAAAA";
    private static final String UPDATED_REMOTEPWD = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORTNO = 1;
    private static final Integer UPDATED_PORTNO = 2;

    private static final String DEFAULT_FILEPATH = "AAAAAAAAAA";
    private static final String UPDATED_FILEPATH = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSFERTYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSFERTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTEIPADDR = "AAAAAAAAAA";
    private static final String UPDATED_REMOTEIPADDR = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTEUSER = "AAAAAAAAAA";
    private static final String UPDATED_REMOTEUSER = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCHEDULEDPROCESS = 1;
    private static final Integer UPDATED_SCHEDULEDPROCESS = 2;

    private static final String DEFAULT_MAPREDUCE = "AAAAAAAAAA";
    private static final String UPDATED_MAPREDUCE = "BBBBBBBBBB";

    private static final String DEFAULT_FILETYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILETYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMSEPARATOR = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMSEPARATOR = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAFileUploadRepository pAFileUploadRepository;

    @Inject
    private PAFileUploadService pAFileUploadService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAFileUploadMockMvc;

    private PAFileUpload pAFileUpload;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAFileUploadResource pAFileUploadResource = new PAFileUploadResource();
        ReflectionTestUtils.setField(pAFileUploadResource, "pAFileUploadService", pAFileUploadService);
        this.restPAFileUploadMockMvc = MockMvcBuilders.standaloneSetup(pAFileUploadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAFileUpload createEntity(EntityManager em) {
        PAFileUpload pAFileUpload = new PAFileUpload()
                .remotepwd(DEFAULT_REMOTEPWD)
                .portno(DEFAULT_PORTNO)
                .filepath(DEFAULT_FILEPATH)
                .transfertype(DEFAULT_TRANSFERTYPE)
                .remoteipaddr(DEFAULT_REMOTEIPADDR)
                .remoteuser(DEFAULT_REMOTEUSER)
                .scheduledprocess(DEFAULT_SCHEDULEDPROCESS)
                .mapreduce(DEFAULT_MAPREDUCE)
                .filetype(DEFAULT_FILETYPE)
                .customseparator(DEFAULT_CUSTOMSEPARATOR)
                .pastatus(DEFAULT_PASTATUS);
        return pAFileUpload;
    }

    @Before
    public void initTest() {
        pAFileUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAFileUpload() throws Exception {
        int databaseSizeBeforeCreate = pAFileUploadRepository.findAll().size();

        // Create the PAFileUpload

        restPAFileUploadMockMvc.perform(post("/api/p-a-file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAFileUpload)))
            .andExpect(status().isCreated());

        // Validate the PAFileUpload in the database
        List<PAFileUpload> pAFileUploadList = pAFileUploadRepository.findAll();
        assertThat(pAFileUploadList).hasSize(databaseSizeBeforeCreate + 1);
        PAFileUpload testPAFileUpload = pAFileUploadList.get(pAFileUploadList.size() - 1);
        assertThat(testPAFileUpload.getRemotepwd()).isEqualTo(DEFAULT_REMOTEPWD);
        assertThat(testPAFileUpload.getPortno()).isEqualTo(DEFAULT_PORTNO);
        assertThat(testPAFileUpload.getFilepath()).isEqualTo(DEFAULT_FILEPATH);
        assertThat(testPAFileUpload.getTransfertype()).isEqualTo(DEFAULT_TRANSFERTYPE);
        assertThat(testPAFileUpload.getRemoteipaddr()).isEqualTo(DEFAULT_REMOTEIPADDR);
        assertThat(testPAFileUpload.getRemoteuser()).isEqualTo(DEFAULT_REMOTEUSER);
        assertThat(testPAFileUpload.getScheduledprocess()).isEqualTo(DEFAULT_SCHEDULEDPROCESS);
        assertThat(testPAFileUpload.getMapreduce()).isEqualTo(DEFAULT_MAPREDUCE);
        assertThat(testPAFileUpload.getFiletype()).isEqualTo(DEFAULT_FILETYPE);
        assertThat(testPAFileUpload.getCustomseparator()).isEqualTo(DEFAULT_CUSTOMSEPARATOR);
        assertThat(testPAFileUpload.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAFileUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAFileUploadRepository.findAll().size();

        // Create the PAFileUpload with an existing ID
        PAFileUpload existingPAFileUpload = new PAFileUpload();
        existingPAFileUpload.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAFileUploadMockMvc.perform(post("/api/p-a-file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAFileUpload)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAFileUpload> pAFileUploadList = pAFileUploadRepository.findAll();
        assertThat(pAFileUploadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAFileUploads() throws Exception {
        // Initialize the database
        pAFileUploadRepository.saveAndFlush(pAFileUpload);

        // Get all the pAFileUploadList
        restPAFileUploadMockMvc.perform(get("/api/p-a-file-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAFileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].remotepwd").value(hasItem(DEFAULT_REMOTEPWD.toString())))
            .andExpect(jsonPath("$.[*].portno").value(hasItem(DEFAULT_PORTNO)))
            .andExpect(jsonPath("$.[*].filepath").value(hasItem(DEFAULT_FILEPATH.toString())))
            .andExpect(jsonPath("$.[*].transfertype").value(hasItem(DEFAULT_TRANSFERTYPE.toString())))
            .andExpect(jsonPath("$.[*].remoteipaddr").value(hasItem(DEFAULT_REMOTEIPADDR.toString())))
            .andExpect(jsonPath("$.[*].remoteuser").value(hasItem(DEFAULT_REMOTEUSER.toString())))
            .andExpect(jsonPath("$.[*].scheduledprocess").value(hasItem(DEFAULT_SCHEDULEDPROCESS)))
            .andExpect(jsonPath("$.[*].mapreduce").value(hasItem(DEFAULT_MAPREDUCE.toString())))
            .andExpect(jsonPath("$.[*].filetype").value(hasItem(DEFAULT_FILETYPE.toString())))
            .andExpect(jsonPath("$.[*].customseparator").value(hasItem(DEFAULT_CUSTOMSEPARATOR.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAFileUpload() throws Exception {
        // Initialize the database
        pAFileUploadRepository.saveAndFlush(pAFileUpload);

        // Get the pAFileUpload
        restPAFileUploadMockMvc.perform(get("/api/p-a-file-uploads/{id}", pAFileUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAFileUpload.getId().intValue()))
            .andExpect(jsonPath("$.remotepwd").value(DEFAULT_REMOTEPWD.toString()))
            .andExpect(jsonPath("$.portno").value(DEFAULT_PORTNO))
            .andExpect(jsonPath("$.filepath").value(DEFAULT_FILEPATH.toString()))
            .andExpect(jsonPath("$.transfertype").value(DEFAULT_TRANSFERTYPE.toString()))
            .andExpect(jsonPath("$.remoteipaddr").value(DEFAULT_REMOTEIPADDR.toString()))
            .andExpect(jsonPath("$.remoteuser").value(DEFAULT_REMOTEUSER.toString()))
            .andExpect(jsonPath("$.scheduledprocess").value(DEFAULT_SCHEDULEDPROCESS))
            .andExpect(jsonPath("$.mapreduce").value(DEFAULT_MAPREDUCE.toString()))
            .andExpect(jsonPath("$.filetype").value(DEFAULT_FILETYPE.toString()))
            .andExpect(jsonPath("$.customseparator").value(DEFAULT_CUSTOMSEPARATOR.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAFileUpload() throws Exception {
        // Get the pAFileUpload
        restPAFileUploadMockMvc.perform(get("/api/p-a-file-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAFileUpload() throws Exception {
        // Initialize the database
        pAFileUploadService.save(pAFileUpload);

        int databaseSizeBeforeUpdate = pAFileUploadRepository.findAll().size();

        // Update the pAFileUpload
        PAFileUpload updatedPAFileUpload = pAFileUploadRepository.findOne(pAFileUpload.getId());
        updatedPAFileUpload
                .remotepwd(UPDATED_REMOTEPWD)
                .portno(UPDATED_PORTNO)
                .filepath(UPDATED_FILEPATH)
                .transfertype(UPDATED_TRANSFERTYPE)
                .remoteipaddr(UPDATED_REMOTEIPADDR)
                .remoteuser(UPDATED_REMOTEUSER)
                .scheduledprocess(UPDATED_SCHEDULEDPROCESS)
                .mapreduce(UPDATED_MAPREDUCE)
                .filetype(UPDATED_FILETYPE)
                .customseparator(UPDATED_CUSTOMSEPARATOR)
                .pastatus(UPDATED_PASTATUS);

        restPAFileUploadMockMvc.perform(put("/api/p-a-file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAFileUpload)))
            .andExpect(status().isOk());

        // Validate the PAFileUpload in the database
        List<PAFileUpload> pAFileUploadList = pAFileUploadRepository.findAll();
        assertThat(pAFileUploadList).hasSize(databaseSizeBeforeUpdate);
        PAFileUpload testPAFileUpload = pAFileUploadList.get(pAFileUploadList.size() - 1);
        assertThat(testPAFileUpload.getRemotepwd()).isEqualTo(UPDATED_REMOTEPWD);
        assertThat(testPAFileUpload.getPortno()).isEqualTo(UPDATED_PORTNO);
        assertThat(testPAFileUpload.getFilepath()).isEqualTo(UPDATED_FILEPATH);
        assertThat(testPAFileUpload.getTransfertype()).isEqualTo(UPDATED_TRANSFERTYPE);
        assertThat(testPAFileUpload.getRemoteipaddr()).isEqualTo(UPDATED_REMOTEIPADDR);
        assertThat(testPAFileUpload.getRemoteuser()).isEqualTo(UPDATED_REMOTEUSER);
        assertThat(testPAFileUpload.getScheduledprocess()).isEqualTo(UPDATED_SCHEDULEDPROCESS);
        assertThat(testPAFileUpload.getMapreduce()).isEqualTo(UPDATED_MAPREDUCE);
        assertThat(testPAFileUpload.getFiletype()).isEqualTo(UPDATED_FILETYPE);
        assertThat(testPAFileUpload.getCustomseparator()).isEqualTo(UPDATED_CUSTOMSEPARATOR);
        assertThat(testPAFileUpload.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAFileUpload() throws Exception {
        int databaseSizeBeforeUpdate = pAFileUploadRepository.findAll().size();

        // Create the PAFileUpload

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAFileUploadMockMvc.perform(put("/api/p-a-file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAFileUpload)))
            .andExpect(status().isCreated());

        // Validate the PAFileUpload in the database
        List<PAFileUpload> pAFileUploadList = pAFileUploadRepository.findAll();
        assertThat(pAFileUploadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAFileUpload() throws Exception {
        // Initialize the database
        pAFileUploadService.save(pAFileUpload);

        int databaseSizeBeforeDelete = pAFileUploadRepository.findAll().size();

        // Get the pAFileUpload
        restPAFileUploadMockMvc.perform(delete("/api/p-a-file-uploads/{id}", pAFileUpload.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAFileUpload> pAFileUploadList = pAFileUploadRepository.findAll();
        assertThat(pAFileUploadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
