package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAErrorMessage;
import com.pervazive.kheddah.repository.PAErrorMessageRepository;
import com.pervazive.kheddah.service.PAErrorMessageService;

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

/**
 * Test class for the PAErrorMessageResource REST controller.
 *
 * @see PAErrorMessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAErrorMessageResourceIntTest {

    private static final String DEFAULT_ERRORCODE = "AAAAAAAAAA";
    private static final String UPDATED_ERRORCODE = "BBBBBBBBBB";

    private static final String DEFAULT_ERRORMSG = "AAAAAAAAAA";
    private static final String UPDATED_ERRORMSG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ERRORTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ERRORTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ERRORUSER = "AAAAAAAAAA";
    private static final String UPDATED_ERRORUSER = "BBBBBBBBBB";

    @Inject
    private PAErrorMessageRepository pAErrorMessageRepository;

    @Inject
    private PAErrorMessageService pAErrorMessageService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAErrorMessageMockMvc;

    private PAErrorMessage pAErrorMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAErrorMessageResource pAErrorMessageResource = new PAErrorMessageResource();
        ReflectionTestUtils.setField(pAErrorMessageResource, "pAErrorMessageService", pAErrorMessageService);
        this.restPAErrorMessageMockMvc = MockMvcBuilders.standaloneSetup(pAErrorMessageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAErrorMessage createEntity(EntityManager em) {
        PAErrorMessage pAErrorMessage = new PAErrorMessage()
                .errorcode(DEFAULT_ERRORCODE)
                .errormsg(DEFAULT_ERRORMSG)
                .errortime(DEFAULT_ERRORTIME)
                .erroruser(DEFAULT_ERRORUSER);
        return pAErrorMessage;
    }

    @Before
    public void initTest() {
        pAErrorMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAErrorMessage() throws Exception {
        int databaseSizeBeforeCreate = pAErrorMessageRepository.findAll().size();

        // Create the PAErrorMessage

        restPAErrorMessageMockMvc.perform(post("/api/p-a-error-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAErrorMessage)))
            .andExpect(status().isCreated());

        // Validate the PAErrorMessage in the database
        List<PAErrorMessage> pAErrorMessageList = pAErrorMessageRepository.findAll();
        assertThat(pAErrorMessageList).hasSize(databaseSizeBeforeCreate + 1);
        PAErrorMessage testPAErrorMessage = pAErrorMessageList.get(pAErrorMessageList.size() - 1);
        assertThat(testPAErrorMessage.getErrorcode()).isEqualTo(DEFAULT_ERRORCODE);
        assertThat(testPAErrorMessage.getErrormsg()).isEqualTo(DEFAULT_ERRORMSG);
        assertThat(testPAErrorMessage.getErrortime()).isEqualTo(DEFAULT_ERRORTIME);
        assertThat(testPAErrorMessage.getErroruser()).isEqualTo(DEFAULT_ERRORUSER);
    }

    @Test
    @Transactional
    public void createPAErrorMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAErrorMessageRepository.findAll().size();

        // Create the PAErrorMessage with an existing ID
        PAErrorMessage existingPAErrorMessage = new PAErrorMessage();
        existingPAErrorMessage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAErrorMessageMockMvc.perform(post("/api/p-a-error-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAErrorMessage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAErrorMessage> pAErrorMessageList = pAErrorMessageRepository.findAll();
        assertThat(pAErrorMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAErrorMessages() throws Exception {
        // Initialize the database
        pAErrorMessageRepository.saveAndFlush(pAErrorMessage);

        // Get all the pAErrorMessageList
        restPAErrorMessageMockMvc.perform(get("/api/p-a-error-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAErrorMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorcode").value(hasItem(DEFAULT_ERRORCODE.toString())))
            .andExpect(jsonPath("$.[*].errormsg").value(hasItem(DEFAULT_ERRORMSG.toString())))
            .andExpect(jsonPath("$.[*].errortime").value(hasItem(sameInstant(DEFAULT_ERRORTIME))))
            .andExpect(jsonPath("$.[*].erroruser").value(hasItem(DEFAULT_ERRORUSER.toString())));
    }

    @Test
    @Transactional
    public void getPAErrorMessage() throws Exception {
        // Initialize the database
        pAErrorMessageRepository.saveAndFlush(pAErrorMessage);

        // Get the pAErrorMessage
        restPAErrorMessageMockMvc.perform(get("/api/p-a-error-messages/{id}", pAErrorMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAErrorMessage.getId().intValue()))
            .andExpect(jsonPath("$.errorcode").value(DEFAULT_ERRORCODE.toString()))
            .andExpect(jsonPath("$.errormsg").value(DEFAULT_ERRORMSG.toString()))
            .andExpect(jsonPath("$.errortime").value(sameInstant(DEFAULT_ERRORTIME)))
            .andExpect(jsonPath("$.erroruser").value(DEFAULT_ERRORUSER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAErrorMessage() throws Exception {
        // Get the pAErrorMessage
        restPAErrorMessageMockMvc.perform(get("/api/p-a-error-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAErrorMessage() throws Exception {
        // Initialize the database
        pAErrorMessageService.save(pAErrorMessage);

        int databaseSizeBeforeUpdate = pAErrorMessageRepository.findAll().size();

        // Update the pAErrorMessage
        PAErrorMessage updatedPAErrorMessage = pAErrorMessageRepository.findOne(pAErrorMessage.getId());
        updatedPAErrorMessage
                .errorcode(UPDATED_ERRORCODE)
                .errormsg(UPDATED_ERRORMSG)
                .errortime(UPDATED_ERRORTIME)
                .erroruser(UPDATED_ERRORUSER);

        restPAErrorMessageMockMvc.perform(put("/api/p-a-error-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAErrorMessage)))
            .andExpect(status().isOk());

        // Validate the PAErrorMessage in the database
        List<PAErrorMessage> pAErrorMessageList = pAErrorMessageRepository.findAll();
        assertThat(pAErrorMessageList).hasSize(databaseSizeBeforeUpdate);
        PAErrorMessage testPAErrorMessage = pAErrorMessageList.get(pAErrorMessageList.size() - 1);
        assertThat(testPAErrorMessage.getErrorcode()).isEqualTo(UPDATED_ERRORCODE);
        assertThat(testPAErrorMessage.getErrormsg()).isEqualTo(UPDATED_ERRORMSG);
        assertThat(testPAErrorMessage.getErrortime()).isEqualTo(UPDATED_ERRORTIME);
        assertThat(testPAErrorMessage.getErroruser()).isEqualTo(UPDATED_ERRORUSER);
    }

    @Test
    @Transactional
    public void updateNonExistingPAErrorMessage() throws Exception {
        int databaseSizeBeforeUpdate = pAErrorMessageRepository.findAll().size();

        // Create the PAErrorMessage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAErrorMessageMockMvc.perform(put("/api/p-a-error-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAErrorMessage)))
            .andExpect(status().isCreated());

        // Validate the PAErrorMessage in the database
        List<PAErrorMessage> pAErrorMessageList = pAErrorMessageRepository.findAll();
        assertThat(pAErrorMessageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAErrorMessage() throws Exception {
        // Initialize the database
        pAErrorMessageService.save(pAErrorMessage);

        int databaseSizeBeforeDelete = pAErrorMessageRepository.findAll().size();

        // Get the pAErrorMessage
        restPAErrorMessageMockMvc.perform(delete("/api/p-a-error-messages/{id}", pAErrorMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAErrorMessage> pAErrorMessageList = pAErrorMessageRepository.findAll();
        assertThat(pAErrorMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
