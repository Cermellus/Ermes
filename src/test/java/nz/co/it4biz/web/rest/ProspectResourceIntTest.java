package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.Prospect;
import nz.co.it4biz.repository.ProspectRepository;
import nz.co.it4biz.service.ProspectService;
import nz.co.it4biz.service.dto.ProspectDTO;
import nz.co.it4biz.service.mapper.ProspectMapper;
import nz.co.it4biz.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static nz.co.it4biz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProspectResource REST controller.
 *
 * @see ProspectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class ProspectResourceIntTest {

    private static final Integer DEFAULT_PROSPECT_ID = 1;
    private static final Integer UPDATED_PROSPECT_ID = 2;

    private static final String DEFAULT_PROSPECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROSPECT_NAME = "BBBBBBBBBB";

    @Autowired
    private ProspectRepository prospectRepository;

    @Autowired
    private ProspectMapper prospectMapper;

    @Autowired
    private ProspectService prospectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProspectMockMvc;

    private Prospect prospect;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProspectResource prospectResource = new ProspectResource(prospectService);
        this.restProspectMockMvc = MockMvcBuilders.standaloneSetup(prospectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prospect createEntity(EntityManager em) {
        Prospect prospect = new Prospect()
            .prospectId(DEFAULT_PROSPECT_ID)
            .prospectName(DEFAULT_PROSPECT_NAME);
        return prospect;
    }

    @Before
    public void initTest() {
        prospect = createEntity(em);
    }

    @Test
    @Transactional
    public void createProspect() throws Exception {
        int databaseSizeBeforeCreate = prospectRepository.findAll().size();

        // Create the Prospect
        ProspectDTO prospectDTO = prospectMapper.toDto(prospect);
        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isCreated());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeCreate + 1);
        Prospect testProspect = prospectList.get(prospectList.size() - 1);
        assertThat(testProspect.getProspectId()).isEqualTo(DEFAULT_PROSPECT_ID);
        assertThat(testProspect.getProspectName()).isEqualTo(DEFAULT_PROSPECT_NAME);
    }

    @Test
    @Transactional
    public void createProspectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prospectRepository.findAll().size();

        // Create the Prospect with an existing ID
        prospect.setId(1L);
        ProspectDTO prospectDTO = prospectMapper.toDto(prospect);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProspectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = prospectRepository.findAll().size();
        // set the field null
        prospect.setProspectId(null);

        // Create the Prospect, which fails.
        ProspectDTO prospectDTO = prospectMapper.toDto(prospect);

        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isBadRequest());

        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProspectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = prospectRepository.findAll().size();
        // set the field null
        prospect.setProspectName(null);

        // Create the Prospect, which fails.
        ProspectDTO prospectDTO = prospectMapper.toDto(prospect);

        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isBadRequest());

        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProspects() throws Exception {
        // Initialize the database
        prospectRepository.saveAndFlush(prospect);

        // Get all the prospectList
        restProspectMockMvc.perform(get("/api/prospects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospect.getId().intValue())))
            .andExpect(jsonPath("$.[*].prospectId").value(hasItem(DEFAULT_PROSPECT_ID)))
            .andExpect(jsonPath("$.[*].prospectName").value(hasItem(DEFAULT_PROSPECT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getProspect() throws Exception {
        // Initialize the database
        prospectRepository.saveAndFlush(prospect);

        // Get the prospect
        restProspectMockMvc.perform(get("/api/prospects/{id}", prospect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prospect.getId().intValue()))
            .andExpect(jsonPath("$.prospectId").value(DEFAULT_PROSPECT_ID))
            .andExpect(jsonPath("$.prospectName").value(DEFAULT_PROSPECT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProspect() throws Exception {
        // Get the prospect
        restProspectMockMvc.perform(get("/api/prospects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProspect() throws Exception {
        // Initialize the database
        prospectRepository.saveAndFlush(prospect);

        int databaseSizeBeforeUpdate = prospectRepository.findAll().size();

        // Update the prospect
        Prospect updatedProspect = prospectRepository.findById(prospect.getId()).get();
        // Disconnect from session so that the updates on updatedProspect are not directly saved in db
        em.detach(updatedProspect);
        updatedProspect
            .prospectId(UPDATED_PROSPECT_ID)
            .prospectName(UPDATED_PROSPECT_NAME);
        ProspectDTO prospectDTO = prospectMapper.toDto(updatedProspect);

        restProspectMockMvc.perform(put("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isOk());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeUpdate);
        Prospect testProspect = prospectList.get(prospectList.size() - 1);
        assertThat(testProspect.getProspectId()).isEqualTo(UPDATED_PROSPECT_ID);
        assertThat(testProspect.getProspectName()).isEqualTo(UPDATED_PROSPECT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProspect() throws Exception {
        int databaseSizeBeforeUpdate = prospectRepository.findAll().size();

        // Create the Prospect
        ProspectDTO prospectDTO = prospectMapper.toDto(prospect);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProspectMockMvc.perform(put("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProspect() throws Exception {
        // Initialize the database
        prospectRepository.saveAndFlush(prospect);

        int databaseSizeBeforeDelete = prospectRepository.findAll().size();

        // Delete the prospect
        restProspectMockMvc.perform(delete("/api/prospects/{id}", prospect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prospect.class);
        Prospect prospect1 = new Prospect();
        prospect1.setId(1L);
        Prospect prospect2 = new Prospect();
        prospect2.setId(prospect1.getId());
        assertThat(prospect1).isEqualTo(prospect2);
        prospect2.setId(2L);
        assertThat(prospect1).isNotEqualTo(prospect2);
        prospect1.setId(null);
        assertThat(prospect1).isNotEqualTo(prospect2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProspectDTO.class);
        ProspectDTO prospectDTO1 = new ProspectDTO();
        prospectDTO1.setId(1L);
        ProspectDTO prospectDTO2 = new ProspectDTO();
        assertThat(prospectDTO1).isNotEqualTo(prospectDTO2);
        prospectDTO2.setId(prospectDTO1.getId());
        assertThat(prospectDTO1).isEqualTo(prospectDTO2);
        prospectDTO2.setId(2L);
        assertThat(prospectDTO1).isNotEqualTo(prospectDTO2);
        prospectDTO1.setId(null);
        assertThat(prospectDTO1).isNotEqualTo(prospectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prospectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prospectMapper.fromId(null)).isNull();
    }
}
