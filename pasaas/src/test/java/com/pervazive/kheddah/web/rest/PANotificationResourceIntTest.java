package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PANotification;
import com.pervazive.kheddah.repository.PANotificationRepository;
import com.pervazive.kheddah.service.PANotificationService;

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
import org.springframework.util.Base64Utils;

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

import com.pervazive.kheddah.domain.enumeration.PANotifType;
/**
 * Test class for the PANotificationResource REST controller.
 *
 * @see PANotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PANotificationResourceIntTest {

    private static final PANotifType DEFAULT_NOTIFTYPE = PANotifType.Email;
    private static final PANotifType UPDATED_NOTIFTYPE = PANotifType.Mobile;

    private static final String DEFAULT_MSGTO = "AAAAAAAAAA";
    private static final String UPDATED_MSGTO = "BBBBBBBBBB";

    private static final String DEFAULT_MSGCC = "AAAAAAAAAA";
    private static final String UPDATED_MSGCC = "BBBBBBBBBB";

    private static final String DEFAULT_MSGSUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_MSGSUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_MSGBODY = "AAAAAAAAAA";
    private static final String UPDATED_MSGBODY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MSGTOUCHTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MSGTOUCHTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_MSGATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MSGATTACHMENTS = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_MSGATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MSGATTACHMENTS_CONTENT_TYPE = "image/png";

    @Inject
    private PANotificationRepository pANotificationRepository;

    @Inject
    private PANotificationService pANotificationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPANotificationMockMvc;

    private PANotification pANotification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PANotificationResource pANotificationResource = new PANotificationResource();
        ReflectionTestUtils.setField(pANotificationResource, "pANotificationService", pANotificationService);
        this.restPANotificationMockMvc = MockMvcBuilders.standaloneSetup(pANotificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PANotification createEntity(EntityManager em) {
        PANotification pANotification = new PANotification()
                .notiftype(DEFAULT_NOTIFTYPE)
                .msgto(DEFAULT_MSGTO)
                .msgcc(DEFAULT_MSGCC)
                .msgsubject(DEFAULT_MSGSUBJECT)
                .msgbody(DEFAULT_MSGBODY)
                .msgtouchtime(DEFAULT_MSGTOUCHTIME)
                .msgattachments(DEFAULT_MSGATTACHMENTS)
                .msgattachmentsContentType(DEFAULT_MSGATTACHMENTS_CONTENT_TYPE);
        return pANotification;
    }

    @Before
    public void initTest() {
        pANotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createPANotification() throws Exception {
        int databaseSizeBeforeCreate = pANotificationRepository.findAll().size();

        // Create the PANotification

        restPANotificationMockMvc.perform(post("/api/p-a-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pANotification)))
            .andExpect(status().isCreated());

        // Validate the PANotification in the database
        List<PANotification> pANotificationList = pANotificationRepository.findAll();
        assertThat(pANotificationList).hasSize(databaseSizeBeforeCreate + 1);
        PANotification testPANotification = pANotificationList.get(pANotificationList.size() - 1);
        assertThat(testPANotification.getNotiftype()).isEqualTo(DEFAULT_NOTIFTYPE);
        assertThat(testPANotification.getMsgto()).isEqualTo(DEFAULT_MSGTO);
        assertThat(testPANotification.getMsgcc()).isEqualTo(DEFAULT_MSGCC);
        assertThat(testPANotification.getMsgsubject()).isEqualTo(DEFAULT_MSGSUBJECT);
        assertThat(testPANotification.getMsgbody()).isEqualTo(DEFAULT_MSGBODY);
        assertThat(testPANotification.getMsgtouchtime()).isEqualTo(DEFAULT_MSGTOUCHTIME);
        assertThat(testPANotification.getMsgattachments()).isEqualTo(DEFAULT_MSGATTACHMENTS);
        assertThat(testPANotification.getMsgattachmentsContentType()).isEqualTo(DEFAULT_MSGATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPANotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pANotificationRepository.findAll().size();

        // Create the PANotification with an existing ID
        PANotification existingPANotification = new PANotification();
        existingPANotification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPANotificationMockMvc.perform(post("/api/p-a-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPANotification)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PANotification> pANotificationList = pANotificationRepository.findAll();
        assertThat(pANotificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPANotifications() throws Exception {
        // Initialize the database
        pANotificationRepository.saveAndFlush(pANotification);

        // Get all the pANotificationList
        restPANotificationMockMvc.perform(get("/api/p-a-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pANotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].notiftype").value(hasItem(DEFAULT_NOTIFTYPE.toString())))
            .andExpect(jsonPath("$.[*].msgto").value(hasItem(DEFAULT_MSGTO.toString())))
            .andExpect(jsonPath("$.[*].msgcc").value(hasItem(DEFAULT_MSGCC.toString())))
            .andExpect(jsonPath("$.[*].msgsubject").value(hasItem(DEFAULT_MSGSUBJECT.toString())))
            .andExpect(jsonPath("$.[*].msgbody").value(hasItem(DEFAULT_MSGBODY.toString())))
            .andExpect(jsonPath("$.[*].msgtouchtime").value(hasItem(sameInstant(DEFAULT_MSGTOUCHTIME))))
            .andExpect(jsonPath("$.[*].msgattachmentsContentType").value(hasItem(DEFAULT_MSGATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].msgattachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_MSGATTACHMENTS))));
    }

    @Test
    @Transactional
    public void getPANotification() throws Exception {
        // Initialize the database
        pANotificationRepository.saveAndFlush(pANotification);

        // Get the pANotification
        restPANotificationMockMvc.perform(get("/api/p-a-notifications/{id}", pANotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pANotification.getId().intValue()))
            .andExpect(jsonPath("$.notiftype").value(DEFAULT_NOTIFTYPE.toString()))
            .andExpect(jsonPath("$.msgto").value(DEFAULT_MSGTO.toString()))
            .andExpect(jsonPath("$.msgcc").value(DEFAULT_MSGCC.toString()))
            .andExpect(jsonPath("$.msgsubject").value(DEFAULT_MSGSUBJECT.toString()))
            .andExpect(jsonPath("$.msgbody").value(DEFAULT_MSGBODY.toString()))
            .andExpect(jsonPath("$.msgtouchtime").value(sameInstant(DEFAULT_MSGTOUCHTIME)))
            .andExpect(jsonPath("$.msgattachmentsContentType").value(DEFAULT_MSGATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.msgattachments").value(Base64Utils.encodeToString(DEFAULT_MSGATTACHMENTS)));
    }

    @Test
    @Transactional
    public void getNonExistingPANotification() throws Exception {
        // Get the pANotification
        restPANotificationMockMvc.perform(get("/api/p-a-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePANotification() throws Exception {
        // Initialize the database
        pANotificationService.save(pANotification);

        int databaseSizeBeforeUpdate = pANotificationRepository.findAll().size();

        // Update the pANotification
        PANotification updatedPANotification = pANotificationRepository.findOne(pANotification.getId());
        updatedPANotification
                .notiftype(UPDATED_NOTIFTYPE)
                .msgto(UPDATED_MSGTO)
                .msgcc(UPDATED_MSGCC)
                .msgsubject(UPDATED_MSGSUBJECT)
                .msgbody(UPDATED_MSGBODY)
                .msgtouchtime(UPDATED_MSGTOUCHTIME)
                .msgattachments(UPDATED_MSGATTACHMENTS)
                .msgattachmentsContentType(UPDATED_MSGATTACHMENTS_CONTENT_TYPE);

        restPANotificationMockMvc.perform(put("/api/p-a-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPANotification)))
            .andExpect(status().isOk());

        // Validate the PANotification in the database
        List<PANotification> pANotificationList = pANotificationRepository.findAll();
        assertThat(pANotificationList).hasSize(databaseSizeBeforeUpdate);
        PANotification testPANotification = pANotificationList.get(pANotificationList.size() - 1);
        assertThat(testPANotification.getNotiftype()).isEqualTo(UPDATED_NOTIFTYPE);
        assertThat(testPANotification.getMsgto()).isEqualTo(UPDATED_MSGTO);
        assertThat(testPANotification.getMsgcc()).isEqualTo(UPDATED_MSGCC);
        assertThat(testPANotification.getMsgsubject()).isEqualTo(UPDATED_MSGSUBJECT);
        assertThat(testPANotification.getMsgbody()).isEqualTo(UPDATED_MSGBODY);
        assertThat(testPANotification.getMsgtouchtime()).isEqualTo(UPDATED_MSGTOUCHTIME);
        assertThat(testPANotification.getMsgattachments()).isEqualTo(UPDATED_MSGATTACHMENTS);
        assertThat(testPANotification.getMsgattachmentsContentType()).isEqualTo(UPDATED_MSGATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPANotification() throws Exception {
        int databaseSizeBeforeUpdate = pANotificationRepository.findAll().size();

        // Create the PANotification

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPANotificationMockMvc.perform(put("/api/p-a-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pANotification)))
            .andExpect(status().isCreated());

        // Validate the PANotification in the database
        List<PANotification> pANotificationList = pANotificationRepository.findAll();
        assertThat(pANotificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePANotification() throws Exception {
        // Initialize the database
        pANotificationService.save(pANotification);

        int databaseSizeBeforeDelete = pANotificationRepository.findAll().size();

        // Get the pANotification
        restPANotificationMockMvc.perform(delete("/api/p-a-notifications/{id}", pANotification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PANotification> pANotificationList = pANotificationRepository.findAll();
        assertThat(pANotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
