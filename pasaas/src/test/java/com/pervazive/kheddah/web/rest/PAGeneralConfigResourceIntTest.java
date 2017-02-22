package com.pervazive.kheddah.web.rest;

import com.pervazive.kheddah.PasaasApp;

import com.pervazive.kheddah.domain.PAGeneralConfig;
import com.pervazive.kheddah.repository.PAGeneralConfigRepository;
import com.pervazive.kheddah.service.PAGeneralConfigService;

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
 * Test class for the PAGeneralConfigResource REST controller.
 *
 * @see PAGeneralConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasaasApp.class)
public class PAGeneralConfigResourceIntTest {

    private static final String DEFAULT_HDFSURL = "AAAAAAAAAA";
    private static final String UPDATED_HDFSURL = "BBBBBBBBBB";

    private static final String DEFAULT_SPARKURL = "AAAAAAAAAA";
    private static final String UPDATED_SPARKURL = "BBBBBBBBBB";

    private static final String DEFAULT_DR_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_DR_SCRIPT = "BBBBBBBBBB";

    private static final String DEFAULT_DR_INSERIESFORMAT = "AAAAAAAAAA";
    private static final String UPDATED_DR_INSERIESFORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_DR_OUTSERIESFORMAT = "AAAAAAAAAA";
    private static final String UPDATED_DR_OUTSERIESFORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_DR_INPUTFILE = "AAAAAAAAAA";
    private static final String UPDATED_DR_INPUTFILE = "BBBBBBBBBB";

    private static final String DEFAULT_DR_EXPRESSIONFILE = "AAAAAAAAAA";
    private static final String UPDATED_DR_EXPRESSIONFILE = "BBBBBBBBBB";

    private static final String DEFAULT_DR_OUTPUTFILE = "AAAAAAAAAA";
    private static final String UPDATED_DR_OUTPUTFILE = "BBBBBBBBBB";

    private static final String DEFAULT_DR_SERIESGCOLINDEX = "AAAAAAAAAA";
    private static final String UPDATED_DR_SERIESGCOLINDEX = "BBBBBBBBBB";

    private static final Integer DEFAULT_DR_SERIESSTART = 1;
    private static final Integer UPDATED_DR_SERIESSTART = 2;

    private static final Integer DEFAULT_DR_SERIESEND = 1;
    private static final Integer UPDATED_DR_SERIESEND = 2;

    private static final Integer DEFAULT_DR_SERIESNXT = 1;
    private static final Integer UPDATED_DR_SERIESNXT = 2;

    private static final String DEFAULT_DF_INPUTFILE = "AAAAAAAAAA";
    private static final String UPDATED_DF_INPUTFILE = "BBBBBBBBBB";

    private static final String DEFAULT_DF_OUTPUTFILE = "AAAAAAAAAA";
    private static final String UPDATED_DF_OUTPUTFILE = "BBBBBBBBBB";

    private static final String DEFAULT_DF_ENTITYFLD = "AAAAAAAAAA";
    private static final String UPDATED_DF_ENTITYFLD = "BBBBBBBBBB";

    private static final String DEFAULT_DF_SERIESFLD = "AAAAAAAAAA";
    private static final String UPDATED_DF_SERIESFLD = "BBBBBBBBBB";

    private static final String DEFAULT_DF_INSERIESFORMAT = "AAAAAAAAAA";
    private static final String UPDATED_DF_INSERIESFORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_DF_OUTSERIESFORMAT = "AAAAAAAAAA";
    private static final String UPDATED_DF_OUTSERIESFORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_DF_ISHEADER = "AAAAAAAAAA";
    private static final String UPDATED_DF_ISHEADER = "BBBBBBBBBB";

    private static final String DEFAULT_DF_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_DF_SCRIPT = "BBBBBBBBBB";

    private static final String DEFAULT_DF_SKIPFLDINDEXES = "AAAAAAAAAA";
    private static final String UPDATED_DF_SKIPFLDINDEXES = "BBBBBBBBBB";

    private static final String DEFAULT_SS_INPUTFILE = "AAAAAAAAAA";
    private static final String UPDATED_SS_INPUTFILE = "BBBBBBBBBB";

    private static final String DEFAULT_SS_OUTPUTFILE = "AAAAAAAAAA";
    private static final String UPDATED_SS_OUTPUTFILE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SS_SAXCODEFLDINDEX = 1;
    private static final Integer UPDATED_SS_SAXCODEFLDINDEX = 2;

    private static final Integer DEFAULT_SS_SUBSEQINTERVAL = 1;
    private static final Integer UPDATED_SS_SUBSEQINTERVAL = 2;

    private static final Integer DEFAULT_SS_SUBSEQINTERVALTHRESHHOLD = 1;
    private static final Integer UPDATED_SS_SUBSEQINTERVALTHRESHHOLD = 2;

    private static final String DEFAULT_SS_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_SS_SCRIPT = "BBBBBBBBBB";

    private static final String DEFAULT_SS_TEMPOPFILE = "AAAAAAAAAA";
    private static final String UPDATED_SS_TEMPOPFILE = "BBBBBBBBBB";

    private static final String DEFAULT_SS_INPUTDIRNAME = "AAAAAAAAAA";
    private static final String UPDATED_SS_INPUTDIRNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_IPADDR = "AAAAAAAAAA";
    private static final String UPDATED_SQ_IPADDR = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_MYSQLPWD = "AAAAAAAAAA";
    private static final String UPDATED_SQ_MYSQLPWD = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_MYSQLDB = "AAAAAAAAAA";
    private static final String UPDATED_SQ_MYSQLDB = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_LOADLOCALINFILE = "AAAAAAAAAA";
    private static final String UPDATED_SQ_LOADLOCALINFILE = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_DAYDUMPPATH = "AAAAAAAAAA";
    private static final String UPDATED_SQ_DAYDUMPPATH = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_UPDQUERY = "AAAAAAAAAA";
    private static final String UPDATED_SQ_UPDQUERY = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_INSERTQUERY = "AAAAAAAAAA";
    private static final String UPDATED_SQ_INSERTQUERY = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_SQ_SCRIPT = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_COMMAND = "AAAAAAAAAA";
    private static final String UPDATED_SQ_COMMAND = "BBBBBBBBBB";

    private static final String DEFAULT_SQ_LOCALINFIILE = "AAAAAAAAAA";
    private static final String UPDATED_SQ_LOCALINFIILE = "BBBBBBBBBB";

    private static final PAStatus DEFAULT_PASTATUS = PAStatus.Active;
    private static final PAStatus UPDATED_PASTATUS = PAStatus.Inactive;

    @Inject
    private PAGeneralConfigRepository pAGeneralConfigRepository;

    @Inject
    private PAGeneralConfigService pAGeneralConfigService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPAGeneralConfigMockMvc;

    private PAGeneralConfig pAGeneralConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PAGeneralConfigResource pAGeneralConfigResource = new PAGeneralConfigResource();
        ReflectionTestUtils.setField(pAGeneralConfigResource, "pAGeneralConfigService", pAGeneralConfigService);
        this.restPAGeneralConfigMockMvc = MockMvcBuilders.standaloneSetup(pAGeneralConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PAGeneralConfig createEntity(EntityManager em) {
        PAGeneralConfig pAGeneralConfig = new PAGeneralConfig()
                .hdfsurl(DEFAULT_HDFSURL)
                .sparkurl(DEFAULT_SPARKURL)
                .pastatus(DEFAULT_PASTATUS);
        return pAGeneralConfig;
    }

    @Before
    public void initTest() {
        pAGeneralConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createPAGeneralConfig() throws Exception {
        int databaseSizeBeforeCreate = pAGeneralConfigRepository.findAll().size();

        // Create the PAGeneralConfig

        restPAGeneralConfigMockMvc.perform(post("/api/p-a-general-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAGeneralConfig)))
            .andExpect(status().isCreated());

        // Validate the PAGeneralConfig in the database
        List<PAGeneralConfig> pAGeneralConfigList = pAGeneralConfigRepository.findAll();
        assertThat(pAGeneralConfigList).hasSize(databaseSizeBeforeCreate + 1);
        PAGeneralConfig testPAGeneralConfig = pAGeneralConfigList.get(pAGeneralConfigList.size() - 1);
        assertThat(testPAGeneralConfig.getHdfsurl()).isEqualTo(DEFAULT_HDFSURL);
        assertThat(testPAGeneralConfig.getSparkurl()).isEqualTo(DEFAULT_SPARKURL);
        assertThat(testPAGeneralConfig.getPastatus()).isEqualTo(DEFAULT_PASTATUS);
    }

    @Test
    @Transactional
    public void createPAGeneralConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pAGeneralConfigRepository.findAll().size();

        // Create the PAGeneralConfig with an existing ID
        PAGeneralConfig existingPAGeneralConfig = new PAGeneralConfig();
        existingPAGeneralConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPAGeneralConfigMockMvc.perform(post("/api/p-a-general-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPAGeneralConfig)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PAGeneralConfig> pAGeneralConfigList = pAGeneralConfigRepository.findAll();
        assertThat(pAGeneralConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPAGeneralConfigs() throws Exception {
        // Initialize the database
        pAGeneralConfigRepository.saveAndFlush(pAGeneralConfig);

        // Get all the pAGeneralConfigList
        restPAGeneralConfigMockMvc.perform(get("/api/p-a-general-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pAGeneralConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].hdfsurl").value(hasItem(DEFAULT_HDFSURL.toString())))
            .andExpect(jsonPath("$.[*].sparkurl").value(hasItem(DEFAULT_SPARKURL.toString())))
            .andExpect(jsonPath("$.[*].dr_script").value(hasItem(DEFAULT_DR_SCRIPT.toString())))
            .andExpect(jsonPath("$.[*].dr_inseriesformat").value(hasItem(DEFAULT_DR_INSERIESFORMAT.toString())))
            .andExpect(jsonPath("$.[*].dr_outseriesformat").value(hasItem(DEFAULT_DR_OUTSERIESFORMAT.toString())))
            .andExpect(jsonPath("$.[*].dr_inputfile").value(hasItem(DEFAULT_DR_INPUTFILE.toString())))
            .andExpect(jsonPath("$.[*].dr_expressionfile").value(hasItem(DEFAULT_DR_EXPRESSIONFILE.toString())))
            .andExpect(jsonPath("$.[*].dr_outputfile").value(hasItem(DEFAULT_DR_OUTPUTFILE.toString())))
            .andExpect(jsonPath("$.[*].dr_seriesgcolindex").value(hasItem(DEFAULT_DR_SERIESGCOLINDEX.toString())))
            .andExpect(jsonPath("$.[*].dr_seriesstart").value(hasItem(DEFAULT_DR_SERIESSTART)))
            .andExpect(jsonPath("$.[*].dr_seriesend").value(hasItem(DEFAULT_DR_SERIESEND)))
            .andExpect(jsonPath("$.[*].dr_seriesnxt").value(hasItem(DEFAULT_DR_SERIESNXT)))
            .andExpect(jsonPath("$.[*].df_inputfile").value(hasItem(DEFAULT_DF_INPUTFILE.toString())))
            .andExpect(jsonPath("$.[*].df_outputfile").value(hasItem(DEFAULT_DF_OUTPUTFILE.toString())))
            .andExpect(jsonPath("$.[*].df_entityfld").value(hasItem(DEFAULT_DF_ENTITYFLD.toString())))
            .andExpect(jsonPath("$.[*].df_seriesfld").value(hasItem(DEFAULT_DF_SERIESFLD.toString())))
            .andExpect(jsonPath("$.[*].df_inseriesformat").value(hasItem(DEFAULT_DF_INSERIESFORMAT.toString())))
            .andExpect(jsonPath("$.[*].df_outseriesformat").value(hasItem(DEFAULT_DF_OUTSERIESFORMAT.toString())))
            .andExpect(jsonPath("$.[*].df_isheader").value(hasItem(DEFAULT_DF_ISHEADER.toString())))
            .andExpect(jsonPath("$.[*].df_script").value(hasItem(DEFAULT_DF_SCRIPT.toString())))
            .andExpect(jsonPath("$.[*].df_skipfldindexes").value(hasItem(DEFAULT_DF_SKIPFLDINDEXES.toString())))
            .andExpect(jsonPath("$.[*].ss_inputfile").value(hasItem(DEFAULT_SS_INPUTFILE.toString())))
            .andExpect(jsonPath("$.[*].ss_outputfile").value(hasItem(DEFAULT_SS_OUTPUTFILE.toString())))
            .andExpect(jsonPath("$.[*].ss_saxcodefldindex").value(hasItem(DEFAULT_SS_SAXCODEFLDINDEX)))
            .andExpect(jsonPath("$.[*].ss_subseqinterval").value(hasItem(DEFAULT_SS_SUBSEQINTERVAL)))
            .andExpect(jsonPath("$.[*].ss_subseqintervalthreshhold").value(hasItem(DEFAULT_SS_SUBSEQINTERVALTHRESHHOLD)))
            .andExpect(jsonPath("$.[*].ss_script").value(hasItem(DEFAULT_SS_SCRIPT.toString())))
            .andExpect(jsonPath("$.[*].ss_tempopfile").value(hasItem(DEFAULT_SS_TEMPOPFILE.toString())))
            .andExpect(jsonPath("$.[*].ss_inputdirname").value(hasItem(DEFAULT_SS_INPUTDIRNAME.toString())))
            .andExpect(jsonPath("$.[*].sq_ipaddr").value(hasItem(DEFAULT_SQ_IPADDR.toString())))
            .andExpect(jsonPath("$.[*].sq_mysqlpwd").value(hasItem(DEFAULT_SQ_MYSQLPWD.toString())))
            .andExpect(jsonPath("$.[*].sq_mysqldb").value(hasItem(DEFAULT_SQ_MYSQLDB.toString())))
            .andExpect(jsonPath("$.[*].sq_loadlocalinfile").value(hasItem(DEFAULT_SQ_LOADLOCALINFILE.toString())))
            .andExpect(jsonPath("$.[*].sq_daydumppath").value(hasItem(DEFAULT_SQ_DAYDUMPPATH.toString())))
            .andExpect(jsonPath("$.[*].sq_updquery").value(hasItem(DEFAULT_SQ_UPDQUERY.toString())))
            .andExpect(jsonPath("$.[*].sq_insertquery").value(hasItem(DEFAULT_SQ_INSERTQUERY.toString())))
            .andExpect(jsonPath("$.[*].sq_script").value(hasItem(DEFAULT_SQ_SCRIPT.toString())))
            .andExpect(jsonPath("$.[*].sq_command").value(hasItem(DEFAULT_SQ_COMMAND.toString())))
            .andExpect(jsonPath("$.[*].sq_localinfiile").value(hasItem(DEFAULT_SQ_LOCALINFIILE.toString())))
            .andExpect(jsonPath("$.[*].pastatus").value(hasItem(DEFAULT_PASTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPAGeneralConfig() throws Exception {
        // Initialize the database
        pAGeneralConfigRepository.saveAndFlush(pAGeneralConfig);

        // Get the pAGeneralConfig
        restPAGeneralConfigMockMvc.perform(get("/api/p-a-general-configs/{id}", pAGeneralConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pAGeneralConfig.getId().intValue()))
            .andExpect(jsonPath("$.hdfsurl").value(DEFAULT_HDFSURL.toString()))
            .andExpect(jsonPath("$.sparkurl").value(DEFAULT_SPARKURL.toString()))
            .andExpect(jsonPath("$.dr_script").value(DEFAULT_DR_SCRIPT.toString()))
            .andExpect(jsonPath("$.dr_inseriesformat").value(DEFAULT_DR_INSERIESFORMAT.toString()))
            .andExpect(jsonPath("$.dr_outseriesformat").value(DEFAULT_DR_OUTSERIESFORMAT.toString()))
            .andExpect(jsonPath("$.dr_inputfile").value(DEFAULT_DR_INPUTFILE.toString()))
            .andExpect(jsonPath("$.dr_expressionfile").value(DEFAULT_DR_EXPRESSIONFILE.toString()))
            .andExpect(jsonPath("$.dr_outputfile").value(DEFAULT_DR_OUTPUTFILE.toString()))
            .andExpect(jsonPath("$.dr_seriesgcolindex").value(DEFAULT_DR_SERIESGCOLINDEX.toString()))
            .andExpect(jsonPath("$.dr_seriesstart").value(DEFAULT_DR_SERIESSTART))
            .andExpect(jsonPath("$.dr_seriesend").value(DEFAULT_DR_SERIESEND))
            .andExpect(jsonPath("$.dr_seriesnxt").value(DEFAULT_DR_SERIESNXT))
            .andExpect(jsonPath("$.df_inputfile").value(DEFAULT_DF_INPUTFILE.toString()))
            .andExpect(jsonPath("$.df_outputfile").value(DEFAULT_DF_OUTPUTFILE.toString()))
            .andExpect(jsonPath("$.df_entityfld").value(DEFAULT_DF_ENTITYFLD.toString()))
            .andExpect(jsonPath("$.df_seriesfld").value(DEFAULT_DF_SERIESFLD.toString()))
            .andExpect(jsonPath("$.df_inseriesformat").value(DEFAULT_DF_INSERIESFORMAT.toString()))
            .andExpect(jsonPath("$.df_outseriesformat").value(DEFAULT_DF_OUTSERIESFORMAT.toString()))
            .andExpect(jsonPath("$.df_isheader").value(DEFAULT_DF_ISHEADER.toString()))
            .andExpect(jsonPath("$.df_script").value(DEFAULT_DF_SCRIPT.toString()))
            .andExpect(jsonPath("$.df_skipfldindexes").value(DEFAULT_DF_SKIPFLDINDEXES.toString()))
            .andExpect(jsonPath("$.ss_inputfile").value(DEFAULT_SS_INPUTFILE.toString()))
            .andExpect(jsonPath("$.ss_outputfile").value(DEFAULT_SS_OUTPUTFILE.toString()))
            .andExpect(jsonPath("$.ss_saxcodefldindex").value(DEFAULT_SS_SAXCODEFLDINDEX))
            .andExpect(jsonPath("$.ss_subseqinterval").value(DEFAULT_SS_SUBSEQINTERVAL))
            .andExpect(jsonPath("$.ss_subseqintervalthreshhold").value(DEFAULT_SS_SUBSEQINTERVALTHRESHHOLD))
            .andExpect(jsonPath("$.ss_script").value(DEFAULT_SS_SCRIPT.toString()))
            .andExpect(jsonPath("$.ss_tempopfile").value(DEFAULT_SS_TEMPOPFILE.toString()))
            .andExpect(jsonPath("$.ss_inputdirname").value(DEFAULT_SS_INPUTDIRNAME.toString()))
            .andExpect(jsonPath("$.sq_ipaddr").value(DEFAULT_SQ_IPADDR.toString()))
            .andExpect(jsonPath("$.sq_mysqlpwd").value(DEFAULT_SQ_MYSQLPWD.toString()))
            .andExpect(jsonPath("$.sq_mysqldb").value(DEFAULT_SQ_MYSQLDB.toString()))
            .andExpect(jsonPath("$.sq_loadlocalinfile").value(DEFAULT_SQ_LOADLOCALINFILE.toString()))
            .andExpect(jsonPath("$.sq_daydumppath").value(DEFAULT_SQ_DAYDUMPPATH.toString()))
            .andExpect(jsonPath("$.sq_updquery").value(DEFAULT_SQ_UPDQUERY.toString()))
            .andExpect(jsonPath("$.sq_insertquery").value(DEFAULT_SQ_INSERTQUERY.toString()))
            .andExpect(jsonPath("$.sq_script").value(DEFAULT_SQ_SCRIPT.toString()))
            .andExpect(jsonPath("$.sq_command").value(DEFAULT_SQ_COMMAND.toString()))
            .andExpect(jsonPath("$.sq_localinfiile").value(DEFAULT_SQ_LOCALINFIILE.toString()))
            .andExpect(jsonPath("$.pastatus").value(DEFAULT_PASTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPAGeneralConfig() throws Exception {
        // Get the pAGeneralConfig
        restPAGeneralConfigMockMvc.perform(get("/api/p-a-general-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePAGeneralConfig() throws Exception {
        // Initialize the database
        pAGeneralConfigService.save(pAGeneralConfig);

        int databaseSizeBeforeUpdate = pAGeneralConfigRepository.findAll().size();

        // Update the pAGeneralConfig
        PAGeneralConfig updatedPAGeneralConfig = pAGeneralConfigRepository.findOne(pAGeneralConfig.getId());
        updatedPAGeneralConfig
                .hdfsurl(UPDATED_HDFSURL)
                .sparkurl(UPDATED_SPARKURL)
                .pastatus(UPDATED_PASTATUS);

        restPAGeneralConfigMockMvc.perform(put("/api/p-a-general-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPAGeneralConfig)))
            .andExpect(status().isOk());

        // Validate the PAGeneralConfig in the database
        List<PAGeneralConfig> pAGeneralConfigList = pAGeneralConfigRepository.findAll();
        assertThat(pAGeneralConfigList).hasSize(databaseSizeBeforeUpdate);
        PAGeneralConfig testPAGeneralConfig = pAGeneralConfigList.get(pAGeneralConfigList.size() - 1);
        assertThat(testPAGeneralConfig.getHdfsurl()).isEqualTo(UPDATED_HDFSURL);
        assertThat(testPAGeneralConfig.getSparkurl()).isEqualTo(UPDATED_SPARKURL);
        assertThat(testPAGeneralConfig.getPastatus()).isEqualTo(UPDATED_PASTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPAGeneralConfig() throws Exception {
        int databaseSizeBeforeUpdate = pAGeneralConfigRepository.findAll().size();

        // Create the PAGeneralConfig

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPAGeneralConfigMockMvc.perform(put("/api/p-a-general-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pAGeneralConfig)))
            .andExpect(status().isCreated());

        // Validate the PAGeneralConfig in the database
        List<PAGeneralConfig> pAGeneralConfigList = pAGeneralConfigRepository.findAll();
        assertThat(pAGeneralConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePAGeneralConfig() throws Exception {
        // Initialize the database
        pAGeneralConfigService.save(pAGeneralConfig);

        int databaseSizeBeforeDelete = pAGeneralConfigRepository.findAll().size();

        // Get the pAGeneralConfig
        restPAGeneralConfigMockMvc.perform(delete("/api/p-a-general-configs/{id}", pAGeneralConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PAGeneralConfig> pAGeneralConfigList = pAGeneralConfigRepository.findAll();
        assertThat(pAGeneralConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
