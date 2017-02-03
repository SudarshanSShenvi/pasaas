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
                .dr_script(DEFAULT_DR_SCRIPT)
                .dr_inseriesformat(DEFAULT_DR_INSERIESFORMAT)
                .dr_outseriesformat(DEFAULT_DR_OUTSERIESFORMAT)
                .dr_inputfile(DEFAULT_DR_INPUTFILE)
                .dr_expressionfile(DEFAULT_DR_EXPRESSIONFILE)
                .dr_outputfile(DEFAULT_DR_OUTPUTFILE)
                .dr_seriesgcolindex(DEFAULT_DR_SERIESGCOLINDEX)
                .dr_seriesstart(DEFAULT_DR_SERIESSTART)
                .dr_seriesend(DEFAULT_DR_SERIESEND)
                .dr_seriesnxt(DEFAULT_DR_SERIESNXT)
                .df_inputfile(DEFAULT_DF_INPUTFILE)
                .df_outputfile(DEFAULT_DF_OUTPUTFILE)
                .df_entityfld(DEFAULT_DF_ENTITYFLD)
                .df_seriesfld(DEFAULT_DF_SERIESFLD)
                .df_inseriesformat(DEFAULT_DF_INSERIESFORMAT)
                .df_outseriesformat(DEFAULT_DF_OUTSERIESFORMAT)
                .df_isheader(DEFAULT_DF_ISHEADER)
                .df_script(DEFAULT_DF_SCRIPT)
                .df_skipfldindexes(DEFAULT_DF_SKIPFLDINDEXES)
                .ss_inputfile(DEFAULT_SS_INPUTFILE)
                .ss_outputfile(DEFAULT_SS_OUTPUTFILE)
                .ss_saxcodefldindex(DEFAULT_SS_SAXCODEFLDINDEX)
                .ss_subseqinterval(DEFAULT_SS_SUBSEQINTERVAL)
                .ss_subseqintervalthreshhold(DEFAULT_SS_SUBSEQINTERVALTHRESHHOLD)
                .ss_script(DEFAULT_SS_SCRIPT)
                .ss_tempopfile(DEFAULT_SS_TEMPOPFILE)
                .ss_inputdirname(DEFAULT_SS_INPUTDIRNAME)
                .sq_ipaddr(DEFAULT_SQ_IPADDR)
                .sq_mysqlpwd(DEFAULT_SQ_MYSQLPWD)
                .sq_mysqldb(DEFAULT_SQ_MYSQLDB)
                .sq_loadlocalinfile(DEFAULT_SQ_LOADLOCALINFILE)
                .sq_daydumppath(DEFAULT_SQ_DAYDUMPPATH)
                .sq_updquery(DEFAULT_SQ_UPDQUERY)
                .sq_insertquery(DEFAULT_SQ_INSERTQUERY)
                .sq_script(DEFAULT_SQ_SCRIPT)
                .sq_command(DEFAULT_SQ_COMMAND)
                .sq_localinfiile(DEFAULT_SQ_LOCALINFIILE)
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
        assertThat(testPAGeneralConfig.getDr_script()).isEqualTo(DEFAULT_DR_SCRIPT);
        assertThat(testPAGeneralConfig.getDr_inseriesformat()).isEqualTo(DEFAULT_DR_INSERIESFORMAT);
        assertThat(testPAGeneralConfig.getDr_outseriesformat()).isEqualTo(DEFAULT_DR_OUTSERIESFORMAT);
        assertThat(testPAGeneralConfig.getDr_inputfile()).isEqualTo(DEFAULT_DR_INPUTFILE);
        assertThat(testPAGeneralConfig.getDr_expressionfile()).isEqualTo(DEFAULT_DR_EXPRESSIONFILE);
        assertThat(testPAGeneralConfig.getDr_outputfile()).isEqualTo(DEFAULT_DR_OUTPUTFILE);
        assertThat(testPAGeneralConfig.getDr_seriesgcolindex()).isEqualTo(DEFAULT_DR_SERIESGCOLINDEX);
        assertThat(testPAGeneralConfig.getDr_seriesstart()).isEqualTo(DEFAULT_DR_SERIESSTART);
        assertThat(testPAGeneralConfig.getDr_seriesend()).isEqualTo(DEFAULT_DR_SERIESEND);
        assertThat(testPAGeneralConfig.getDr_seriesnxt()).isEqualTo(DEFAULT_DR_SERIESNXT);
        assertThat(testPAGeneralConfig.getDf_inputfile()).isEqualTo(DEFAULT_DF_INPUTFILE);
        assertThat(testPAGeneralConfig.getDf_outputfile()).isEqualTo(DEFAULT_DF_OUTPUTFILE);
        assertThat(testPAGeneralConfig.getDf_entityfld()).isEqualTo(DEFAULT_DF_ENTITYFLD);
        assertThat(testPAGeneralConfig.getDf_seriesfld()).isEqualTo(DEFAULT_DF_SERIESFLD);
        assertThat(testPAGeneralConfig.getDf_inseriesformat()).isEqualTo(DEFAULT_DF_INSERIESFORMAT);
        assertThat(testPAGeneralConfig.getDf_outseriesformat()).isEqualTo(DEFAULT_DF_OUTSERIESFORMAT);
        assertThat(testPAGeneralConfig.getDf_isheader()).isEqualTo(DEFAULT_DF_ISHEADER);
        assertThat(testPAGeneralConfig.getDf_script()).isEqualTo(DEFAULT_DF_SCRIPT);
        assertThat(testPAGeneralConfig.getDf_skipfldindexes()).isEqualTo(DEFAULT_DF_SKIPFLDINDEXES);
        assertThat(testPAGeneralConfig.getSs_inputfile()).isEqualTo(DEFAULT_SS_INPUTFILE);
        assertThat(testPAGeneralConfig.getSs_outputfile()).isEqualTo(DEFAULT_SS_OUTPUTFILE);
        assertThat(testPAGeneralConfig.getSs_saxcodefldindex()).isEqualTo(DEFAULT_SS_SAXCODEFLDINDEX);
        assertThat(testPAGeneralConfig.getSs_subseqinterval()).isEqualTo(DEFAULT_SS_SUBSEQINTERVAL);
        assertThat(testPAGeneralConfig.getSs_subseqintervalthreshhold()).isEqualTo(DEFAULT_SS_SUBSEQINTERVALTHRESHHOLD);
        assertThat(testPAGeneralConfig.getSs_script()).isEqualTo(DEFAULT_SS_SCRIPT);
        assertThat(testPAGeneralConfig.getSs_tempopfile()).isEqualTo(DEFAULT_SS_TEMPOPFILE);
        assertThat(testPAGeneralConfig.getSs_inputdirname()).isEqualTo(DEFAULT_SS_INPUTDIRNAME);
        assertThat(testPAGeneralConfig.getSq_ipaddr()).isEqualTo(DEFAULT_SQ_IPADDR);
        assertThat(testPAGeneralConfig.getSq_mysqlpwd()).isEqualTo(DEFAULT_SQ_MYSQLPWD);
        assertThat(testPAGeneralConfig.getSq_mysqldb()).isEqualTo(DEFAULT_SQ_MYSQLDB);
        assertThat(testPAGeneralConfig.getSq_loadlocalinfile()).isEqualTo(DEFAULT_SQ_LOADLOCALINFILE);
        assertThat(testPAGeneralConfig.getSq_daydumppath()).isEqualTo(DEFAULT_SQ_DAYDUMPPATH);
        assertThat(testPAGeneralConfig.getSq_updquery()).isEqualTo(DEFAULT_SQ_UPDQUERY);
        assertThat(testPAGeneralConfig.getSq_insertquery()).isEqualTo(DEFAULT_SQ_INSERTQUERY);
        assertThat(testPAGeneralConfig.getSq_script()).isEqualTo(DEFAULT_SQ_SCRIPT);
        assertThat(testPAGeneralConfig.getSq_command()).isEqualTo(DEFAULT_SQ_COMMAND);
        assertThat(testPAGeneralConfig.getSq_localinfiile()).isEqualTo(DEFAULT_SQ_LOCALINFIILE);
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
                .dr_script(UPDATED_DR_SCRIPT)
                .dr_inseriesformat(UPDATED_DR_INSERIESFORMAT)
                .dr_outseriesformat(UPDATED_DR_OUTSERIESFORMAT)
                .dr_inputfile(UPDATED_DR_INPUTFILE)
                .dr_expressionfile(UPDATED_DR_EXPRESSIONFILE)
                .dr_outputfile(UPDATED_DR_OUTPUTFILE)
                .dr_seriesgcolindex(UPDATED_DR_SERIESGCOLINDEX)
                .dr_seriesstart(UPDATED_DR_SERIESSTART)
                .dr_seriesend(UPDATED_DR_SERIESEND)
                .dr_seriesnxt(UPDATED_DR_SERIESNXT)
                .df_inputfile(UPDATED_DF_INPUTFILE)
                .df_outputfile(UPDATED_DF_OUTPUTFILE)
                .df_entityfld(UPDATED_DF_ENTITYFLD)
                .df_seriesfld(UPDATED_DF_SERIESFLD)
                .df_inseriesformat(UPDATED_DF_INSERIESFORMAT)
                .df_outseriesformat(UPDATED_DF_OUTSERIESFORMAT)
                .df_isheader(UPDATED_DF_ISHEADER)
                .df_script(UPDATED_DF_SCRIPT)
                .df_skipfldindexes(UPDATED_DF_SKIPFLDINDEXES)
                .ss_inputfile(UPDATED_SS_INPUTFILE)
                .ss_outputfile(UPDATED_SS_OUTPUTFILE)
                .ss_saxcodefldindex(UPDATED_SS_SAXCODEFLDINDEX)
                .ss_subseqinterval(UPDATED_SS_SUBSEQINTERVAL)
                .ss_subseqintervalthreshhold(UPDATED_SS_SUBSEQINTERVALTHRESHHOLD)
                .ss_script(UPDATED_SS_SCRIPT)
                .ss_tempopfile(UPDATED_SS_TEMPOPFILE)
                .ss_inputdirname(UPDATED_SS_INPUTDIRNAME)
                .sq_ipaddr(UPDATED_SQ_IPADDR)
                .sq_mysqlpwd(UPDATED_SQ_MYSQLPWD)
                .sq_mysqldb(UPDATED_SQ_MYSQLDB)
                .sq_loadlocalinfile(UPDATED_SQ_LOADLOCALINFILE)
                .sq_daydumppath(UPDATED_SQ_DAYDUMPPATH)
                .sq_updquery(UPDATED_SQ_UPDQUERY)
                .sq_insertquery(UPDATED_SQ_INSERTQUERY)
                .sq_script(UPDATED_SQ_SCRIPT)
                .sq_command(UPDATED_SQ_COMMAND)
                .sq_localinfiile(UPDATED_SQ_LOCALINFIILE)
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
        assertThat(testPAGeneralConfig.getDr_script()).isEqualTo(UPDATED_DR_SCRIPT);
        assertThat(testPAGeneralConfig.getDr_inseriesformat()).isEqualTo(UPDATED_DR_INSERIESFORMAT);
        assertThat(testPAGeneralConfig.getDr_outseriesformat()).isEqualTo(UPDATED_DR_OUTSERIESFORMAT);
        assertThat(testPAGeneralConfig.getDr_inputfile()).isEqualTo(UPDATED_DR_INPUTFILE);
        assertThat(testPAGeneralConfig.getDr_expressionfile()).isEqualTo(UPDATED_DR_EXPRESSIONFILE);
        assertThat(testPAGeneralConfig.getDr_outputfile()).isEqualTo(UPDATED_DR_OUTPUTFILE);
        assertThat(testPAGeneralConfig.getDr_seriesgcolindex()).isEqualTo(UPDATED_DR_SERIESGCOLINDEX);
        assertThat(testPAGeneralConfig.getDr_seriesstart()).isEqualTo(UPDATED_DR_SERIESSTART);
        assertThat(testPAGeneralConfig.getDr_seriesend()).isEqualTo(UPDATED_DR_SERIESEND);
        assertThat(testPAGeneralConfig.getDr_seriesnxt()).isEqualTo(UPDATED_DR_SERIESNXT);
        assertThat(testPAGeneralConfig.getDf_inputfile()).isEqualTo(UPDATED_DF_INPUTFILE);
        assertThat(testPAGeneralConfig.getDf_outputfile()).isEqualTo(UPDATED_DF_OUTPUTFILE);
        assertThat(testPAGeneralConfig.getDf_entityfld()).isEqualTo(UPDATED_DF_ENTITYFLD);
        assertThat(testPAGeneralConfig.getDf_seriesfld()).isEqualTo(UPDATED_DF_SERIESFLD);
        assertThat(testPAGeneralConfig.getDf_inseriesformat()).isEqualTo(UPDATED_DF_INSERIESFORMAT);
        assertThat(testPAGeneralConfig.getDf_outseriesformat()).isEqualTo(UPDATED_DF_OUTSERIESFORMAT);
        assertThat(testPAGeneralConfig.getDf_isheader()).isEqualTo(UPDATED_DF_ISHEADER);
        assertThat(testPAGeneralConfig.getDf_script()).isEqualTo(UPDATED_DF_SCRIPT);
        assertThat(testPAGeneralConfig.getDf_skipfldindexes()).isEqualTo(UPDATED_DF_SKIPFLDINDEXES);
        assertThat(testPAGeneralConfig.getSs_inputfile()).isEqualTo(UPDATED_SS_INPUTFILE);
        assertThat(testPAGeneralConfig.getSs_outputfile()).isEqualTo(UPDATED_SS_OUTPUTFILE);
        assertThat(testPAGeneralConfig.getSs_saxcodefldindex()).isEqualTo(UPDATED_SS_SAXCODEFLDINDEX);
        assertThat(testPAGeneralConfig.getSs_subseqinterval()).isEqualTo(UPDATED_SS_SUBSEQINTERVAL);
        assertThat(testPAGeneralConfig.getSs_subseqintervalthreshhold()).isEqualTo(UPDATED_SS_SUBSEQINTERVALTHRESHHOLD);
        assertThat(testPAGeneralConfig.getSs_script()).isEqualTo(UPDATED_SS_SCRIPT);
        assertThat(testPAGeneralConfig.getSs_tempopfile()).isEqualTo(UPDATED_SS_TEMPOPFILE);
        assertThat(testPAGeneralConfig.getSs_inputdirname()).isEqualTo(UPDATED_SS_INPUTDIRNAME);
        assertThat(testPAGeneralConfig.getSq_ipaddr()).isEqualTo(UPDATED_SQ_IPADDR);
        assertThat(testPAGeneralConfig.getSq_mysqlpwd()).isEqualTo(UPDATED_SQ_MYSQLPWD);
        assertThat(testPAGeneralConfig.getSq_mysqldb()).isEqualTo(UPDATED_SQ_MYSQLDB);
        assertThat(testPAGeneralConfig.getSq_loadlocalinfile()).isEqualTo(UPDATED_SQ_LOADLOCALINFILE);
        assertThat(testPAGeneralConfig.getSq_daydumppath()).isEqualTo(UPDATED_SQ_DAYDUMPPATH);
        assertThat(testPAGeneralConfig.getSq_updquery()).isEqualTo(UPDATED_SQ_UPDQUERY);
        assertThat(testPAGeneralConfig.getSq_insertquery()).isEqualTo(UPDATED_SQ_INSERTQUERY);
        assertThat(testPAGeneralConfig.getSq_script()).isEqualTo(UPDATED_SQ_SCRIPT);
        assertThat(testPAGeneralConfig.getSq_command()).isEqualTo(UPDATED_SQ_COMMAND);
        assertThat(testPAGeneralConfig.getSq_localinfiile()).isEqualTo(UPDATED_SQ_LOCALINFIILE);
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
