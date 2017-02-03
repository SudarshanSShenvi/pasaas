package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.repository.PAProjectRepository;
import com.pervazive.kheddah.service.PAProjectService;

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

/**
 * Test class for the PAProjectResource REST controller.
 *
 * @see PAProjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAProjectResourceIntTest {

    private static final String DEFAULT_PROJECTNAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private PAProjectRepository pAProjectRepository;

    @Inject
    private PAProjectService pAProjectService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAProjectMockMvc;

    private PAProject pAProject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAProjectResource pAProjectResource = new PAProjectResource();
        ReflectionTestUtils.setField(pAProjectResource, "pAProjectService", pAProjectService);
        this.restPAProjectMockMvc = MockMvcBuilders.standaloneSetup(pAProjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAProject createEntity(EntityManager em) {
        PAProject pAProject = new PAProject()
                .projectname(DEFAULT_PROJECTNAME)
                .description(DEFAULT_DESCRIPTION);
        return pAProject;
    }

    @Before
    public void initTest() {
        pAProject = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAProject() throws Exception {
        int databaseSizeBeforeCreate = pAProjectRepository.findAll().size();

        // Create the PAProject

        restPAProjectMockMvc.perform(post("/api/p-a-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAProject)))
            .andExpect(status().isCreated());

        // Validate the PAProject in the database
        List<PAProject> pAProjectList = pAProjectRepository.findAll();
        assertThat(pAProjectList).hasSize(databaseSizeBeforeCreate + 1);
        PAProject testPAProject = pAProjectList.get(pAProjectList.size() - 1);
        assertThat(testPAProject.getProjectname()).isEqualTo(DEFAULT_PROJECTNAME);
        assertThat(testPAProject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPAProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAProjectRepository.findAll().size();

        // Create the PAProject with an existing ID
        PAProject existingPAProject = new PAProject();
        existingPAProject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAProjectMockMvc.perform(post("/api/p-a-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAProject)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAProject> pAProjectList = pAProjectRepository.findAll();
        assertThat(pAProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAProjects() throws Exception {
        // Initialize the database
        pAProjectRepository.saveAndFlush(pAProject);

        // Get all the pAProjectList
        restPAProjectMockMvc.perform(get("/api/p-a-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectname").value(hasItem(DEFAULT_PROJECTNAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPAProject() throws Exception {
        // Initialize the database
        pAProjectRepository.saveAndFlush(pAProject);

        // Get the pAProject
        restPAProjectMockMvc.perform(get("/api/p-a-projects/{id}", pAProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAProject.getId().intValue()))
            .andExpect(jsonPath("$.projectname").value(DEFAULT_PROJECTNAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAProject() throws Exception {
        // Get the pAProject
        restPAProjectMockMvc.perform(get("/api/p-a-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAProject() throws Exception {
        // Initialize the database
        pAProjectService.save(pAProject);

        int databaseSizeBeforeUpdate = pAProjectRepository.findAll().size();

        // Update the pAProject
        PAProject updatedPAProject = pAProjectRepository.findOne(pAProject.getId());
        updatedPAProject
                .projectname(UPDATED_PROJECTNAME)
                .description(UPDATED_DESCRIPTION);

        restPAProjectMockMvc.perform(put("/api/p-a-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAProject)))
            .andExpect(status().isOk());

        // Validate the PAProject in the database
        List<PAProject> pAProjectList = pAProjectRepository.findAll();
        assertThat(pAProjectList).hasSize(databaseSizeBeforeUpdate);
        PAProject testPAProject = pAProjectList.get(pAProjectList.size() - 1);
        assertThat(testPAProject.getProjectname()).isEqualTo(UPDATED_PROJECTNAME);
        assertThat(testPAProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPAProject() throws Exception {
        int databaseSizeBeforeUpdate = pAProjectRepository.findAll().size();

        // Create the PAProject

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAProjectMockMvc.perform(put("/api/p-a-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAProject)))
            .andExpect(status().isCreated());

        // Validate the PAProject in the database
        List<PAProject> pAProjectList = pAProjectRepository.findAll();
        assertThat(pAProjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAProject() throws Exception {
        // Initialize the database
        pAProjectService.save(pAProject);

        int databaseSizeBeforeDelete = pAProjectRepository.findAll().size();

        // Get the pAProject
        restPAProjectMockMvc.perform(delete("/api/p-a-projects/{id}", pAProject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAProject> pAProjectList = pAProjectRepository.findAll();
        assertThat(pAProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
