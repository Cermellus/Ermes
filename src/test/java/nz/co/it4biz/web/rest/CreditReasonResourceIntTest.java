package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.CreditReason;
import nz.co.it4biz.repository.CreditReasonRepository;
import nz.co.it4biz.service.CreditReasonService;
import nz.co.it4biz.service.dto.CreditReasonDTO;
import nz.co.it4biz.service.mapper.CreditReasonMapper;
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
 * Test class for the CreditReasonResource REST controller.
 *
 * @see CreditReasonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class CreditReasonResourceIntTest {

    private static final Integer DEFAULT_CREDIT_REASON_ID = 1;
    private static final Integer UPDATED_CREDIT_REASON_ID = 2;

    private static final String DEFAULT_CREDIT_REASON_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_REASON_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_REASON_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_REASON_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CREDIT_REASON_PRODUCT_REQUIRED = false;
    private static final Boolean UPDATED_CREDIT_REASON_PRODUCT_REQUIRED = true;

    private static final Boolean DEFAULT_CREDIT_REASON_COURIER_CLAIM = false;
    private static final Boolean UPDATED_CREDIT_REASON_COURIER_CLAIM = true;

    @Autowired
    private CreditReasonRepository creditReasonRepository;

    @Autowired
    private CreditReasonMapper creditReasonMapper;

    @Autowired
    private CreditReasonService creditReasonService;

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

    private MockMvc restCreditReasonMockMvc;

    private CreditReason creditReason;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditReasonResource creditReasonResource = new CreditReasonResource(creditReasonService);
        this.restCreditReasonMockMvc = MockMvcBuilders.standaloneSetup(creditReasonResource)
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
    public static CreditReason createEntity(EntityManager em) {
        CreditReason creditReason = new CreditReason()
            .creditReasonId(DEFAULT_CREDIT_REASON_ID)
            .creditReasonDescription(DEFAULT_CREDIT_REASON_DESCRIPTION)
            .creditReasonCode(DEFAULT_CREDIT_REASON_CODE)
            .creditReasonProductRequired(DEFAULT_CREDIT_REASON_PRODUCT_REQUIRED)
            .creditReasonCourierClaim(DEFAULT_CREDIT_REASON_COURIER_CLAIM);
        return creditReason;
    }

    @Before
    public void initTest() {
        creditReason = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditReason() throws Exception {
        int databaseSizeBeforeCreate = creditReasonRepository.findAll().size();

        // Create the CreditReason
        CreditReasonDTO creditReasonDTO = creditReasonMapper.toDto(creditReason);
        restCreditReasonMockMvc.perform(post("/api/credit-reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReasonDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditReason in the database
        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeCreate + 1);
        CreditReason testCreditReason = creditReasonList.get(creditReasonList.size() - 1);
        assertThat(testCreditReason.getCreditReasonId()).isEqualTo(DEFAULT_CREDIT_REASON_ID);
        assertThat(testCreditReason.getCreditReasonDescription()).isEqualTo(DEFAULT_CREDIT_REASON_DESCRIPTION);
        assertThat(testCreditReason.getCreditReasonCode()).isEqualTo(DEFAULT_CREDIT_REASON_CODE);
        assertThat(testCreditReason.isCreditReasonProductRequired()).isEqualTo(DEFAULT_CREDIT_REASON_PRODUCT_REQUIRED);
        assertThat(testCreditReason.isCreditReasonCourierClaim()).isEqualTo(DEFAULT_CREDIT_REASON_COURIER_CLAIM);
    }

    @Test
    @Transactional
    public void createCreditReasonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditReasonRepository.findAll().size();

        // Create the CreditReason with an existing ID
        creditReason.setId(1L);
        CreditReasonDTO creditReasonDTO = creditReasonMapper.toDto(creditReason);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditReasonMockMvc.perform(post("/api/credit-reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReasonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditReason in the database
        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreditReasonIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReasonRepository.findAll().size();
        // set the field null
        creditReason.setCreditReasonId(null);

        // Create the CreditReason, which fails.
        CreditReasonDTO creditReasonDTO = creditReasonMapper.toDto(creditReason);

        restCreditReasonMockMvc.perform(post("/api/credit-reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReasonDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditReasonDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReasonRepository.findAll().size();
        // set the field null
        creditReason.setCreditReasonDescription(null);

        // Create the CreditReason, which fails.
        CreditReasonDTO creditReasonDTO = creditReasonMapper.toDto(creditReason);

        restCreditReasonMockMvc.perform(post("/api/credit-reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReasonDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditReasonCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReasonRepository.findAll().size();
        // set the field null
        creditReason.setCreditReasonCode(null);

        // Create the CreditReason, which fails.
        CreditReasonDTO creditReasonDTO = creditReasonMapper.toDto(creditReason);

        restCreditReasonMockMvc.perform(post("/api/credit-reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReasonDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditReasonProductRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReasonRepository.findAll().size();
        // set the field null
        creditReason.setCreditReasonProductRequired(null);

        // Create the CreditReason, which fails.
        CreditReasonDTO creditReasonDTO = creditReasonMapper.toDto(creditReason);

        restCreditReasonMockMvc.perform(post("/api/credit-reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReasonDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditReasonCourierClaimIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReasonRepository.findAll().size();
        // set the field null
        creditReason.setCreditReasonCourierClaim(null);

        // Create the CreditReason, which fails.
        CreditReasonDTO creditReasonDTO = creditReasonMapper.toDto(creditReason);

        restCreditReasonMockMvc.perform(post("/api/credit-reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReasonDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCreditReasons() throws Exception {
        // Initialize the database
        creditReasonRepository.saveAndFlush(creditReason);

        // Get all the creditReasonList
        restCreditReasonMockMvc.perform(get("/api/credit-reasons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditReason.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditReasonId").value(hasItem(DEFAULT_CREDIT_REASON_ID)))
            .andExpect(jsonPath("$.[*].creditReasonDescription").value(hasItem(DEFAULT_CREDIT_REASON_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].creditReasonCode").value(hasItem(DEFAULT_CREDIT_REASON_CODE.toString())))
            .andExpect(jsonPath("$.[*].creditReasonProductRequired").value(hasItem(DEFAULT_CREDIT_REASON_PRODUCT_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].creditReasonCourierClaim").value(hasItem(DEFAULT_CREDIT_REASON_COURIER_CLAIM.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCreditReason() throws Exception {
        // Initialize the database
        creditReasonRepository.saveAndFlush(creditReason);

        // Get the creditReason
        restCreditReasonMockMvc.perform(get("/api/credit-reasons/{id}", creditReason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creditReason.getId().intValue()))
            .andExpect(jsonPath("$.creditReasonId").value(DEFAULT_CREDIT_REASON_ID))
            .andExpect(jsonPath("$.creditReasonDescription").value(DEFAULT_CREDIT_REASON_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.creditReasonCode").value(DEFAULT_CREDIT_REASON_CODE.toString()))
            .andExpect(jsonPath("$.creditReasonProductRequired").value(DEFAULT_CREDIT_REASON_PRODUCT_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.creditReasonCourierClaim").value(DEFAULT_CREDIT_REASON_COURIER_CLAIM.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCreditReason() throws Exception {
        // Get the creditReason
        restCreditReasonMockMvc.perform(get("/api/credit-reasons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditReason() throws Exception {
        // Initialize the database
        creditReasonRepository.saveAndFlush(creditReason);

        int databaseSizeBeforeUpdate = creditReasonRepository.findAll().size();

        // Update the creditReason
        CreditReason updatedCreditReason = creditReasonRepository.findById(creditReason.getId()).get();
        // Disconnect from session so that the updates on updatedCreditReason are not directly saved in db
        em.detach(updatedCreditReason);
        updatedCreditReason
            .creditReasonId(UPDATED_CREDIT_REASON_ID)
            .creditReasonDescription(UPDATED_CREDIT_REASON_DESCRIPTION)
            .creditReasonCode(UPDATED_CREDIT_REASON_CODE)
            .creditReasonProductRequired(UPDATED_CREDIT_REASON_PRODUCT_REQUIRED)
            .creditReasonCourierClaim(UPDATED_CREDIT_REASON_COURIER_CLAIM);
        CreditReasonDTO creditReasonDTO = creditReasonMapper.toDto(updatedCreditReason);

        restCreditReasonMockMvc.perform(put("/api/credit-reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReasonDTO)))
            .andExpect(status().isOk());

        // Validate the CreditReason in the database
        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeUpdate);
        CreditReason testCreditReason = creditReasonList.get(creditReasonList.size() - 1);
        assertThat(testCreditReason.getCreditReasonId()).isEqualTo(UPDATED_CREDIT_REASON_ID);
        assertThat(testCreditReason.getCreditReasonDescription()).isEqualTo(UPDATED_CREDIT_REASON_DESCRIPTION);
        assertThat(testCreditReason.getCreditReasonCode()).isEqualTo(UPDATED_CREDIT_REASON_CODE);
        assertThat(testCreditReason.isCreditReasonProductRequired()).isEqualTo(UPDATED_CREDIT_REASON_PRODUCT_REQUIRED);
        assertThat(testCreditReason.isCreditReasonCourierClaim()).isEqualTo(UPDATED_CREDIT_REASON_COURIER_CLAIM);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditReason() throws Exception {
        int databaseSizeBeforeUpdate = creditReasonRepository.findAll().size();

        // Create the CreditReason
        CreditReasonDTO creditReasonDTO = creditReasonMapper.toDto(creditReason);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditReasonMockMvc.perform(put("/api/credit-reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReasonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditReason in the database
        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCreditReason() throws Exception {
        // Initialize the database
        creditReasonRepository.saveAndFlush(creditReason);

        int databaseSizeBeforeDelete = creditReasonRepository.findAll().size();

        // Delete the creditReason
        restCreditReasonMockMvc.perform(delete("/api/credit-reasons/{id}", creditReason.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CreditReason> creditReasonList = creditReasonRepository.findAll();
        assertThat(creditReasonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditReason.class);
        CreditReason creditReason1 = new CreditReason();
        creditReason1.setId(1L);
        CreditReason creditReason2 = new CreditReason();
        creditReason2.setId(creditReason1.getId());
        assertThat(creditReason1).isEqualTo(creditReason2);
        creditReason2.setId(2L);
        assertThat(creditReason1).isNotEqualTo(creditReason2);
        creditReason1.setId(null);
        assertThat(creditReason1).isNotEqualTo(creditReason2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditReasonDTO.class);
        CreditReasonDTO creditReasonDTO1 = new CreditReasonDTO();
        creditReasonDTO1.setId(1L);
        CreditReasonDTO creditReasonDTO2 = new CreditReasonDTO();
        assertThat(creditReasonDTO1).isNotEqualTo(creditReasonDTO2);
        creditReasonDTO2.setId(creditReasonDTO1.getId());
        assertThat(creditReasonDTO1).isEqualTo(creditReasonDTO2);
        creditReasonDTO2.setId(2L);
        assertThat(creditReasonDTO1).isNotEqualTo(creditReasonDTO2);
        creditReasonDTO1.setId(null);
        assertThat(creditReasonDTO1).isNotEqualTo(creditReasonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditReasonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditReasonMapper.fromId(null)).isNull();
    }
}
