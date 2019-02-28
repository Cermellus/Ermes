package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.CreditRequestStatus;
import nz.co.it4biz.repository.CreditRequestStatusRepository;
import nz.co.it4biz.service.CreditRequestStatusService;
import nz.co.it4biz.service.dto.CreditRequestStatusDTO;
import nz.co.it4biz.service.mapper.CreditRequestStatusMapper;
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
 * Test class for the CreditRequestStatusResource REST controller.
 *
 * @see CreditRequestStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class CreditRequestStatusResourceIntTest {

    private static final Integer DEFAULT_CREDIT_REQUEST_STATUS_ID = 1;
    private static final Integer UPDATED_CREDIT_REQUEST_STATUS_ID = 2;

    private static final String DEFAULT_CREDIT_REQUEST_STATUS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_REQUEST_STATUS_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CREDIT_REQUEST_STATUS_EXPORT = false;
    private static final Boolean UPDATED_CREDIT_REQUEST_STATUS_EXPORT = true;

    @Autowired
    private CreditRequestStatusRepository creditRequestStatusRepository;

    @Autowired
    private CreditRequestStatusMapper creditRequestStatusMapper;

    @Autowired
    private CreditRequestStatusService creditRequestStatusService;

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

    private MockMvc restCreditRequestStatusMockMvc;

    private CreditRequestStatus creditRequestStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditRequestStatusResource creditRequestStatusResource = new CreditRequestStatusResource(creditRequestStatusService);
        this.restCreditRequestStatusMockMvc = MockMvcBuilders.standaloneSetup(creditRequestStatusResource)
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
    public static CreditRequestStatus createEntity(EntityManager em) {
        CreditRequestStatus creditRequestStatus = new CreditRequestStatus()
            .creditRequestStatusId(DEFAULT_CREDIT_REQUEST_STATUS_ID)
            .creditRequestStatusDescription(DEFAULT_CREDIT_REQUEST_STATUS_DESCRIPTION)
            .creditRequestStatusExport(DEFAULT_CREDIT_REQUEST_STATUS_EXPORT);
        return creditRequestStatus;
    }

    @Before
    public void initTest() {
        creditRequestStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditRequestStatus() throws Exception {
        int databaseSizeBeforeCreate = creditRequestStatusRepository.findAll().size();

        // Create the CreditRequestStatus
        CreditRequestStatusDTO creditRequestStatusDTO = creditRequestStatusMapper.toDto(creditRequestStatus);
        restCreditRequestStatusMockMvc.perform(post("/api/credit-request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditRequestStatus in the database
        List<CreditRequestStatus> creditRequestStatusList = creditRequestStatusRepository.findAll();
        assertThat(creditRequestStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CreditRequestStatus testCreditRequestStatus = creditRequestStatusList.get(creditRequestStatusList.size() - 1);
        assertThat(testCreditRequestStatus.getCreditRequestStatusId()).isEqualTo(DEFAULT_CREDIT_REQUEST_STATUS_ID);
        assertThat(testCreditRequestStatus.getCreditRequestStatusDescription()).isEqualTo(DEFAULT_CREDIT_REQUEST_STATUS_DESCRIPTION);
        assertThat(testCreditRequestStatus.isCreditRequestStatusExport()).isEqualTo(DEFAULT_CREDIT_REQUEST_STATUS_EXPORT);
    }

    @Test
    @Transactional
    public void createCreditRequestStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditRequestStatusRepository.findAll().size();

        // Create the CreditRequestStatus with an existing ID
        creditRequestStatus.setId(1L);
        CreditRequestStatusDTO creditRequestStatusDTO = creditRequestStatusMapper.toDto(creditRequestStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditRequestStatusMockMvc.perform(post("/api/credit-request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditRequestStatus in the database
        List<CreditRequestStatus> creditRequestStatusList = creditRequestStatusRepository.findAll();
        assertThat(creditRequestStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreditRequestStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestStatusRepository.findAll().size();
        // set the field null
        creditRequestStatus.setCreditRequestStatusId(null);

        // Create the CreditRequestStatus, which fails.
        CreditRequestStatusDTO creditRequestStatusDTO = creditRequestStatusMapper.toDto(creditRequestStatus);

        restCreditRequestStatusMockMvc.perform(post("/api/credit-request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestStatusDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequestStatus> creditRequestStatusList = creditRequestStatusRepository.findAll();
        assertThat(creditRequestStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditRequestStatusDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestStatusRepository.findAll().size();
        // set the field null
        creditRequestStatus.setCreditRequestStatusDescription(null);

        // Create the CreditRequestStatus, which fails.
        CreditRequestStatusDTO creditRequestStatusDTO = creditRequestStatusMapper.toDto(creditRequestStatus);

        restCreditRequestStatusMockMvc.perform(post("/api/credit-request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestStatusDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequestStatus> creditRequestStatusList = creditRequestStatusRepository.findAll();
        assertThat(creditRequestStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditRequestStatusExportIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestStatusRepository.findAll().size();
        // set the field null
        creditRequestStatus.setCreditRequestStatusExport(null);

        // Create the CreditRequestStatus, which fails.
        CreditRequestStatusDTO creditRequestStatusDTO = creditRequestStatusMapper.toDto(creditRequestStatus);

        restCreditRequestStatusMockMvc.perform(post("/api/credit-request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestStatusDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequestStatus> creditRequestStatusList = creditRequestStatusRepository.findAll();
        assertThat(creditRequestStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCreditRequestStatuses() throws Exception {
        // Initialize the database
        creditRequestStatusRepository.saveAndFlush(creditRequestStatus);

        // Get all the creditRequestStatusList
        restCreditRequestStatusMockMvc.perform(get("/api/credit-request-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditRequestStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditRequestStatusId").value(hasItem(DEFAULT_CREDIT_REQUEST_STATUS_ID)))
            .andExpect(jsonPath("$.[*].creditRequestStatusDescription").value(hasItem(DEFAULT_CREDIT_REQUEST_STATUS_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].creditRequestStatusExport").value(hasItem(DEFAULT_CREDIT_REQUEST_STATUS_EXPORT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCreditRequestStatus() throws Exception {
        // Initialize the database
        creditRequestStatusRepository.saveAndFlush(creditRequestStatus);

        // Get the creditRequestStatus
        restCreditRequestStatusMockMvc.perform(get("/api/credit-request-statuses/{id}", creditRequestStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creditRequestStatus.getId().intValue()))
            .andExpect(jsonPath("$.creditRequestStatusId").value(DEFAULT_CREDIT_REQUEST_STATUS_ID))
            .andExpect(jsonPath("$.creditRequestStatusDescription").value(DEFAULT_CREDIT_REQUEST_STATUS_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.creditRequestStatusExport").value(DEFAULT_CREDIT_REQUEST_STATUS_EXPORT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCreditRequestStatus() throws Exception {
        // Get the creditRequestStatus
        restCreditRequestStatusMockMvc.perform(get("/api/credit-request-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditRequestStatus() throws Exception {
        // Initialize the database
        creditRequestStatusRepository.saveAndFlush(creditRequestStatus);

        int databaseSizeBeforeUpdate = creditRequestStatusRepository.findAll().size();

        // Update the creditRequestStatus
        CreditRequestStatus updatedCreditRequestStatus = creditRequestStatusRepository.findById(creditRequestStatus.getId()).get();
        // Disconnect from session so that the updates on updatedCreditRequestStatus are not directly saved in db
        em.detach(updatedCreditRequestStatus);
        updatedCreditRequestStatus
            .creditRequestStatusId(UPDATED_CREDIT_REQUEST_STATUS_ID)
            .creditRequestStatusDescription(UPDATED_CREDIT_REQUEST_STATUS_DESCRIPTION)
            .creditRequestStatusExport(UPDATED_CREDIT_REQUEST_STATUS_EXPORT);
        CreditRequestStatusDTO creditRequestStatusDTO = creditRequestStatusMapper.toDto(updatedCreditRequestStatus);

        restCreditRequestStatusMockMvc.perform(put("/api/credit-request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CreditRequestStatus in the database
        List<CreditRequestStatus> creditRequestStatusList = creditRequestStatusRepository.findAll();
        assertThat(creditRequestStatusList).hasSize(databaseSizeBeforeUpdate);
        CreditRequestStatus testCreditRequestStatus = creditRequestStatusList.get(creditRequestStatusList.size() - 1);
        assertThat(testCreditRequestStatus.getCreditRequestStatusId()).isEqualTo(UPDATED_CREDIT_REQUEST_STATUS_ID);
        assertThat(testCreditRequestStatus.getCreditRequestStatusDescription()).isEqualTo(UPDATED_CREDIT_REQUEST_STATUS_DESCRIPTION);
        assertThat(testCreditRequestStatus.isCreditRequestStatusExport()).isEqualTo(UPDATED_CREDIT_REQUEST_STATUS_EXPORT);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditRequestStatus() throws Exception {
        int databaseSizeBeforeUpdate = creditRequestStatusRepository.findAll().size();

        // Create the CreditRequestStatus
        CreditRequestStatusDTO creditRequestStatusDTO = creditRequestStatusMapper.toDto(creditRequestStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditRequestStatusMockMvc.perform(put("/api/credit-request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditRequestStatus in the database
        List<CreditRequestStatus> creditRequestStatusList = creditRequestStatusRepository.findAll();
        assertThat(creditRequestStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCreditRequestStatus() throws Exception {
        // Initialize the database
        creditRequestStatusRepository.saveAndFlush(creditRequestStatus);

        int databaseSizeBeforeDelete = creditRequestStatusRepository.findAll().size();

        // Delete the creditRequestStatus
        restCreditRequestStatusMockMvc.perform(delete("/api/credit-request-statuses/{id}", creditRequestStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CreditRequestStatus> creditRequestStatusList = creditRequestStatusRepository.findAll();
        assertThat(creditRequestStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditRequestStatus.class);
        CreditRequestStatus creditRequestStatus1 = new CreditRequestStatus();
        creditRequestStatus1.setId(1L);
        CreditRequestStatus creditRequestStatus2 = new CreditRequestStatus();
        creditRequestStatus2.setId(creditRequestStatus1.getId());
        assertThat(creditRequestStatus1).isEqualTo(creditRequestStatus2);
        creditRequestStatus2.setId(2L);
        assertThat(creditRequestStatus1).isNotEqualTo(creditRequestStatus2);
        creditRequestStatus1.setId(null);
        assertThat(creditRequestStatus1).isNotEqualTo(creditRequestStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditRequestStatusDTO.class);
        CreditRequestStatusDTO creditRequestStatusDTO1 = new CreditRequestStatusDTO();
        creditRequestStatusDTO1.setId(1L);
        CreditRequestStatusDTO creditRequestStatusDTO2 = new CreditRequestStatusDTO();
        assertThat(creditRequestStatusDTO1).isNotEqualTo(creditRequestStatusDTO2);
        creditRequestStatusDTO2.setId(creditRequestStatusDTO1.getId());
        assertThat(creditRequestStatusDTO1).isEqualTo(creditRequestStatusDTO2);
        creditRequestStatusDTO2.setId(2L);
        assertThat(creditRequestStatusDTO1).isNotEqualTo(creditRequestStatusDTO2);
        creditRequestStatusDTO1.setId(null);
        assertThat(creditRequestStatusDTO1).isNotEqualTo(creditRequestStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditRequestStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditRequestStatusMapper.fromId(null)).isNull();
    }
}
