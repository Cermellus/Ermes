package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.CreditRequest;
import nz.co.it4biz.repository.CreditRequestRepository;
import nz.co.it4biz.service.CreditRequestService;
import nz.co.it4biz.service.dto.CreditRequestDTO;
import nz.co.it4biz.service.mapper.CreditRequestMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static nz.co.it4biz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CreditRequestResource REST controller.
 *
 * @see CreditRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class CreditRequestResourceIntTest {

    private static final Integer DEFAULT_CREDIT_REQUEST_ID = 1;
    private static final Integer UPDATED_CREDIT_REQUEST_ID = 2;

    private static final LocalDate DEFAULT_CREDIT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREDIT_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREDIT_REQUEST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_REQUEST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_REQUEST_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_REQUEST_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_REQUEST_CONTACT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_REQUEST_CONTACT_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_REQUEST_REJECT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_REQUEST_REJECT_REASON = "BBBBBBBBBB";

    @Autowired
    private CreditRequestRepository creditRequestRepository;

    @Autowired
    private CreditRequestMapper creditRequestMapper;

    @Autowired
    private CreditRequestService creditRequestService;

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

    private MockMvc restCreditRequestMockMvc;

    private CreditRequest creditRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditRequestResource creditRequestResource = new CreditRequestResource(creditRequestService);
        this.restCreditRequestMockMvc = MockMvcBuilders.standaloneSetup(creditRequestResource)
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
    public static CreditRequest createEntity(EntityManager em) {
        CreditRequest creditRequest = new CreditRequest()
            .creditRequestId(DEFAULT_CREDIT_REQUEST_ID)
            .creditRequestDate(DEFAULT_CREDIT_REQUEST_DATE)
            .creditRequestCode(DEFAULT_CREDIT_REQUEST_CODE)
            .creditRequestReference(DEFAULT_CREDIT_REQUEST_REFERENCE)
            .creditRequestContactMail(DEFAULT_CREDIT_REQUEST_CONTACT_MAIL)
            .creditRequestRejectReason(DEFAULT_CREDIT_REQUEST_REJECT_REASON);
        return creditRequest;
    }

    @Before
    public void initTest() {
        creditRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditRequest() throws Exception {
        int databaseSizeBeforeCreate = creditRequestRepository.findAll().size();

        // Create the CreditRequest
        CreditRequestDTO creditRequestDTO = creditRequestMapper.toDto(creditRequest);
        restCreditRequestMockMvc.perform(post("/api/credit-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditRequest in the database
        List<CreditRequest> creditRequestList = creditRequestRepository.findAll();
        assertThat(creditRequestList).hasSize(databaseSizeBeforeCreate + 1);
        CreditRequest testCreditRequest = creditRequestList.get(creditRequestList.size() - 1);
        assertThat(testCreditRequest.getCreditRequestId()).isEqualTo(DEFAULT_CREDIT_REQUEST_ID);
        assertThat(testCreditRequest.getCreditRequestDate()).isEqualTo(DEFAULT_CREDIT_REQUEST_DATE);
        assertThat(testCreditRequest.getCreditRequestCode()).isEqualTo(DEFAULT_CREDIT_REQUEST_CODE);
        assertThat(testCreditRequest.getCreditRequestReference()).isEqualTo(DEFAULT_CREDIT_REQUEST_REFERENCE);
        assertThat(testCreditRequest.getCreditRequestContactMail()).isEqualTo(DEFAULT_CREDIT_REQUEST_CONTACT_MAIL);
        assertThat(testCreditRequest.getCreditRequestRejectReason()).isEqualTo(DEFAULT_CREDIT_REQUEST_REJECT_REASON);
    }

    @Test
    @Transactional
    public void createCreditRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditRequestRepository.findAll().size();

        // Create the CreditRequest with an existing ID
        creditRequest.setId(1L);
        CreditRequestDTO creditRequestDTO = creditRequestMapper.toDto(creditRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditRequestMockMvc.perform(post("/api/credit-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditRequest in the database
        List<CreditRequest> creditRequestList = creditRequestRepository.findAll();
        assertThat(creditRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreditRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestRepository.findAll().size();
        // set the field null
        creditRequest.setCreditRequestId(null);

        // Create the CreditRequest, which fails.
        CreditRequestDTO creditRequestDTO = creditRequestMapper.toDto(creditRequest);

        restCreditRequestMockMvc.perform(post("/api/credit-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequest> creditRequestList = creditRequestRepository.findAll();
        assertThat(creditRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditRequestDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestRepository.findAll().size();
        // set the field null
        creditRequest.setCreditRequestDate(null);

        // Create the CreditRequest, which fails.
        CreditRequestDTO creditRequestDTO = creditRequestMapper.toDto(creditRequest);

        restCreditRequestMockMvc.perform(post("/api/credit-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequest> creditRequestList = creditRequestRepository.findAll();
        assertThat(creditRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditRequestCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestRepository.findAll().size();
        // set the field null
        creditRequest.setCreditRequestCode(null);

        // Create the CreditRequest, which fails.
        CreditRequestDTO creditRequestDTO = creditRequestMapper.toDto(creditRequest);

        restCreditRequestMockMvc.perform(post("/api/credit-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequest> creditRequestList = creditRequestRepository.findAll();
        assertThat(creditRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditRequestReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestRepository.findAll().size();
        // set the field null
        creditRequest.setCreditRequestReference(null);

        // Create the CreditRequest, which fails.
        CreditRequestDTO creditRequestDTO = creditRequestMapper.toDto(creditRequest);

        restCreditRequestMockMvc.perform(post("/api/credit-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequest> creditRequestList = creditRequestRepository.findAll();
        assertThat(creditRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCreditRequests() throws Exception {
        // Initialize the database
        creditRequestRepository.saveAndFlush(creditRequest);

        // Get all the creditRequestList
        restCreditRequestMockMvc.perform(get("/api/credit-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditRequestId").value(hasItem(DEFAULT_CREDIT_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].creditRequestDate").value(hasItem(DEFAULT_CREDIT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].creditRequestCode").value(hasItem(DEFAULT_CREDIT_REQUEST_CODE.toString())))
            .andExpect(jsonPath("$.[*].creditRequestReference").value(hasItem(DEFAULT_CREDIT_REQUEST_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].creditRequestContactMail").value(hasItem(DEFAULT_CREDIT_REQUEST_CONTACT_MAIL.toString())))
            .andExpect(jsonPath("$.[*].creditRequestRejectReason").value(hasItem(DEFAULT_CREDIT_REQUEST_REJECT_REASON.toString())));
    }
    
    @Test
    @Transactional
    public void getCreditRequest() throws Exception {
        // Initialize the database
        creditRequestRepository.saveAndFlush(creditRequest);

        // Get the creditRequest
        restCreditRequestMockMvc.perform(get("/api/credit-requests/{id}", creditRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creditRequest.getId().intValue()))
            .andExpect(jsonPath("$.creditRequestId").value(DEFAULT_CREDIT_REQUEST_ID))
            .andExpect(jsonPath("$.creditRequestDate").value(DEFAULT_CREDIT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.creditRequestCode").value(DEFAULT_CREDIT_REQUEST_CODE.toString()))
            .andExpect(jsonPath("$.creditRequestReference").value(DEFAULT_CREDIT_REQUEST_REFERENCE.toString()))
            .andExpect(jsonPath("$.creditRequestContactMail").value(DEFAULT_CREDIT_REQUEST_CONTACT_MAIL.toString()))
            .andExpect(jsonPath("$.creditRequestRejectReason").value(DEFAULT_CREDIT_REQUEST_REJECT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCreditRequest() throws Exception {
        // Get the creditRequest
        restCreditRequestMockMvc.perform(get("/api/credit-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditRequest() throws Exception {
        // Initialize the database
        creditRequestRepository.saveAndFlush(creditRequest);

        int databaseSizeBeforeUpdate = creditRequestRepository.findAll().size();

        // Update the creditRequest
        CreditRequest updatedCreditRequest = creditRequestRepository.findById(creditRequest.getId()).get();
        // Disconnect from session so that the updates on updatedCreditRequest are not directly saved in db
        em.detach(updatedCreditRequest);
        updatedCreditRequest
            .creditRequestId(UPDATED_CREDIT_REQUEST_ID)
            .creditRequestDate(UPDATED_CREDIT_REQUEST_DATE)
            .creditRequestCode(UPDATED_CREDIT_REQUEST_CODE)
            .creditRequestReference(UPDATED_CREDIT_REQUEST_REFERENCE)
            .creditRequestContactMail(UPDATED_CREDIT_REQUEST_CONTACT_MAIL)
            .creditRequestRejectReason(UPDATED_CREDIT_REQUEST_REJECT_REASON);
        CreditRequestDTO creditRequestDTO = creditRequestMapper.toDto(updatedCreditRequest);

        restCreditRequestMockMvc.perform(put("/api/credit-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestDTO)))
            .andExpect(status().isOk());

        // Validate the CreditRequest in the database
        List<CreditRequest> creditRequestList = creditRequestRepository.findAll();
        assertThat(creditRequestList).hasSize(databaseSizeBeforeUpdate);
        CreditRequest testCreditRequest = creditRequestList.get(creditRequestList.size() - 1);
        assertThat(testCreditRequest.getCreditRequestId()).isEqualTo(UPDATED_CREDIT_REQUEST_ID);
        assertThat(testCreditRequest.getCreditRequestDate()).isEqualTo(UPDATED_CREDIT_REQUEST_DATE);
        assertThat(testCreditRequest.getCreditRequestCode()).isEqualTo(UPDATED_CREDIT_REQUEST_CODE);
        assertThat(testCreditRequest.getCreditRequestReference()).isEqualTo(UPDATED_CREDIT_REQUEST_REFERENCE);
        assertThat(testCreditRequest.getCreditRequestContactMail()).isEqualTo(UPDATED_CREDIT_REQUEST_CONTACT_MAIL);
        assertThat(testCreditRequest.getCreditRequestRejectReason()).isEqualTo(UPDATED_CREDIT_REQUEST_REJECT_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditRequest() throws Exception {
        int databaseSizeBeforeUpdate = creditRequestRepository.findAll().size();

        // Create the CreditRequest
        CreditRequestDTO creditRequestDTO = creditRequestMapper.toDto(creditRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditRequestMockMvc.perform(put("/api/credit-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditRequest in the database
        List<CreditRequest> creditRequestList = creditRequestRepository.findAll();
        assertThat(creditRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCreditRequest() throws Exception {
        // Initialize the database
        creditRequestRepository.saveAndFlush(creditRequest);

        int databaseSizeBeforeDelete = creditRequestRepository.findAll().size();

        // Delete the creditRequest
        restCreditRequestMockMvc.perform(delete("/api/credit-requests/{id}", creditRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CreditRequest> creditRequestList = creditRequestRepository.findAll();
        assertThat(creditRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditRequest.class);
        CreditRequest creditRequest1 = new CreditRequest();
        creditRequest1.setId(1L);
        CreditRequest creditRequest2 = new CreditRequest();
        creditRequest2.setId(creditRequest1.getId());
        assertThat(creditRequest1).isEqualTo(creditRequest2);
        creditRequest2.setId(2L);
        assertThat(creditRequest1).isNotEqualTo(creditRequest2);
        creditRequest1.setId(null);
        assertThat(creditRequest1).isNotEqualTo(creditRequest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditRequestDTO.class);
        CreditRequestDTO creditRequestDTO1 = new CreditRequestDTO();
        creditRequestDTO1.setId(1L);
        CreditRequestDTO creditRequestDTO2 = new CreditRequestDTO();
        assertThat(creditRequestDTO1).isNotEqualTo(creditRequestDTO2);
        creditRequestDTO2.setId(creditRequestDTO1.getId());
        assertThat(creditRequestDTO1).isEqualTo(creditRequestDTO2);
        creditRequestDTO2.setId(2L);
        assertThat(creditRequestDTO1).isNotEqualTo(creditRequestDTO2);
        creditRequestDTO1.setId(null);
        assertThat(creditRequestDTO1).isNotEqualTo(creditRequestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditRequestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditRequestMapper.fromId(null)).isNull();
    }
}
