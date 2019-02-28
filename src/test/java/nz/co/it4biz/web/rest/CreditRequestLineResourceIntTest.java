package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.CreditRequestLine;
import nz.co.it4biz.repository.CreditRequestLineRepository;
import nz.co.it4biz.service.CreditRequestLineService;
import nz.co.it4biz.service.dto.CreditRequestLineDTO;
import nz.co.it4biz.service.mapper.CreditRequestLineMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;


import static nz.co.it4biz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CreditRequestLineResource REST controller.
 *
 * @see CreditRequestLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class CreditRequestLineResourceIntTest {

    private static final Integer DEFAULT_CREDIT_REQUEST_LINE_ID = 1;
    private static final Integer UPDATED_CREDIT_REQUEST_LINE_ID = 2;

    private static final BigDecimal DEFAULT_CREDIT_REQUEST_LINE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_REQUEST_LINE_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CREDIT_REQUEST_LINE_QTY_CREDITED = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_REQUEST_LINE_QTY_CREDITED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CREDIT_REQUEST_LINE_QTY_RETURN = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_REQUEST_LINE_QTY_RETURN = new BigDecimal(2);

    private static final String DEFAULT_CREDIT_REQUEST_LINE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_REQUEST_LINE_COMMENT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CREDIT_REQUEST_LINE_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CREDIT_REQUEST_LINE_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CREDIT_REQUEST_LINE_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CREDIT_REQUEST_LINE_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private CreditRequestLineRepository creditRequestLineRepository;

    @Autowired
    private CreditRequestLineMapper creditRequestLineMapper;

    @Autowired
    private CreditRequestLineService creditRequestLineService;

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

    private MockMvc restCreditRequestLineMockMvc;

    private CreditRequestLine creditRequestLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditRequestLineResource creditRequestLineResource = new CreditRequestLineResource(creditRequestLineService);
        this.restCreditRequestLineMockMvc = MockMvcBuilders.standaloneSetup(creditRequestLineResource)
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
    public static CreditRequestLine createEntity(EntityManager em) {
        CreditRequestLine creditRequestLine = new CreditRequestLine()
            .creditRequestLineId(DEFAULT_CREDIT_REQUEST_LINE_ID)
            .creditRequestLineAmount(DEFAULT_CREDIT_REQUEST_LINE_AMOUNT)
            .creditRequestLineQtyCredited(DEFAULT_CREDIT_REQUEST_LINE_QTY_CREDITED)
            .creditRequestLineQtyReturn(DEFAULT_CREDIT_REQUEST_LINE_QTY_RETURN)
            .creditRequestLineComment(DEFAULT_CREDIT_REQUEST_LINE_COMMENT)
            .creditRequestLineImage(DEFAULT_CREDIT_REQUEST_LINE_IMAGE)
            .creditRequestLineImageContentType(DEFAULT_CREDIT_REQUEST_LINE_IMAGE_CONTENT_TYPE);
        return creditRequestLine;
    }

    @Before
    public void initTest() {
        creditRequestLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditRequestLine() throws Exception {
        int databaseSizeBeforeCreate = creditRequestLineRepository.findAll().size();

        // Create the CreditRequestLine
        CreditRequestLineDTO creditRequestLineDTO = creditRequestLineMapper.toDto(creditRequestLine);
        restCreditRequestLineMockMvc.perform(post("/api/credit-request-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditRequestLine in the database
        List<CreditRequestLine> creditRequestLineList = creditRequestLineRepository.findAll();
        assertThat(creditRequestLineList).hasSize(databaseSizeBeforeCreate + 1);
        CreditRequestLine testCreditRequestLine = creditRequestLineList.get(creditRequestLineList.size() - 1);
        assertThat(testCreditRequestLine.getCreditRequestLineId()).isEqualTo(DEFAULT_CREDIT_REQUEST_LINE_ID);
        assertThat(testCreditRequestLine.getCreditRequestLineAmount()).isEqualTo(DEFAULT_CREDIT_REQUEST_LINE_AMOUNT);
        assertThat(testCreditRequestLine.getCreditRequestLineQtyCredited()).isEqualTo(DEFAULT_CREDIT_REQUEST_LINE_QTY_CREDITED);
        assertThat(testCreditRequestLine.getCreditRequestLineQtyReturn()).isEqualTo(DEFAULT_CREDIT_REQUEST_LINE_QTY_RETURN);
        assertThat(testCreditRequestLine.getCreditRequestLineComment()).isEqualTo(DEFAULT_CREDIT_REQUEST_LINE_COMMENT);
        assertThat(testCreditRequestLine.getCreditRequestLineImage()).isEqualTo(DEFAULT_CREDIT_REQUEST_LINE_IMAGE);
        assertThat(testCreditRequestLine.getCreditRequestLineImageContentType()).isEqualTo(DEFAULT_CREDIT_REQUEST_LINE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCreditRequestLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditRequestLineRepository.findAll().size();

        // Create the CreditRequestLine with an existing ID
        creditRequestLine.setId(1L);
        CreditRequestLineDTO creditRequestLineDTO = creditRequestLineMapper.toDto(creditRequestLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditRequestLineMockMvc.perform(post("/api/credit-request-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditRequestLine in the database
        List<CreditRequestLine> creditRequestLineList = creditRequestLineRepository.findAll();
        assertThat(creditRequestLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreditRequestLineIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestLineRepository.findAll().size();
        // set the field null
        creditRequestLine.setCreditRequestLineId(null);

        // Create the CreditRequestLine, which fails.
        CreditRequestLineDTO creditRequestLineDTO = creditRequestLineMapper.toDto(creditRequestLine);

        restCreditRequestLineMockMvc.perform(post("/api/credit-request-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestLineDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequestLine> creditRequestLineList = creditRequestLineRepository.findAll();
        assertThat(creditRequestLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditRequestLineAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestLineRepository.findAll().size();
        // set the field null
        creditRequestLine.setCreditRequestLineAmount(null);

        // Create the CreditRequestLine, which fails.
        CreditRequestLineDTO creditRequestLineDTO = creditRequestLineMapper.toDto(creditRequestLine);

        restCreditRequestLineMockMvc.perform(post("/api/credit-request-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestLineDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequestLine> creditRequestLineList = creditRequestLineRepository.findAll();
        assertThat(creditRequestLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditRequestLineQtyCreditedIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestLineRepository.findAll().size();
        // set the field null
        creditRequestLine.setCreditRequestLineQtyCredited(null);

        // Create the CreditRequestLine, which fails.
        CreditRequestLineDTO creditRequestLineDTO = creditRequestLineMapper.toDto(creditRequestLine);

        restCreditRequestLineMockMvc.perform(post("/api/credit-request-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestLineDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequestLine> creditRequestLineList = creditRequestLineRepository.findAll();
        assertThat(creditRequestLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditRequestLineQtyReturnIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRequestLineRepository.findAll().size();
        // set the field null
        creditRequestLine.setCreditRequestLineQtyReturn(null);

        // Create the CreditRequestLine, which fails.
        CreditRequestLineDTO creditRequestLineDTO = creditRequestLineMapper.toDto(creditRequestLine);

        restCreditRequestLineMockMvc.perform(post("/api/credit-request-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestLineDTO)))
            .andExpect(status().isBadRequest());

        List<CreditRequestLine> creditRequestLineList = creditRequestLineRepository.findAll();
        assertThat(creditRequestLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCreditRequestLines() throws Exception {
        // Initialize the database
        creditRequestLineRepository.saveAndFlush(creditRequestLine);

        // Get all the creditRequestLineList
        restCreditRequestLineMockMvc.perform(get("/api/credit-request-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditRequestLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditRequestLineId").value(hasItem(DEFAULT_CREDIT_REQUEST_LINE_ID)))
            .andExpect(jsonPath("$.[*].creditRequestLineAmount").value(hasItem(DEFAULT_CREDIT_REQUEST_LINE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].creditRequestLineQtyCredited").value(hasItem(DEFAULT_CREDIT_REQUEST_LINE_QTY_CREDITED.intValue())))
            .andExpect(jsonPath("$.[*].creditRequestLineQtyReturn").value(hasItem(DEFAULT_CREDIT_REQUEST_LINE_QTY_RETURN.intValue())))
            .andExpect(jsonPath("$.[*].creditRequestLineComment").value(hasItem(DEFAULT_CREDIT_REQUEST_LINE_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].creditRequestLineImageContentType").value(hasItem(DEFAULT_CREDIT_REQUEST_LINE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].creditRequestLineImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_CREDIT_REQUEST_LINE_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getCreditRequestLine() throws Exception {
        // Initialize the database
        creditRequestLineRepository.saveAndFlush(creditRequestLine);

        // Get the creditRequestLine
        restCreditRequestLineMockMvc.perform(get("/api/credit-request-lines/{id}", creditRequestLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creditRequestLine.getId().intValue()))
            .andExpect(jsonPath("$.creditRequestLineId").value(DEFAULT_CREDIT_REQUEST_LINE_ID))
            .andExpect(jsonPath("$.creditRequestLineAmount").value(DEFAULT_CREDIT_REQUEST_LINE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.creditRequestLineQtyCredited").value(DEFAULT_CREDIT_REQUEST_LINE_QTY_CREDITED.intValue()))
            .andExpect(jsonPath("$.creditRequestLineQtyReturn").value(DEFAULT_CREDIT_REQUEST_LINE_QTY_RETURN.intValue()))
            .andExpect(jsonPath("$.creditRequestLineComment").value(DEFAULT_CREDIT_REQUEST_LINE_COMMENT.toString()))
            .andExpect(jsonPath("$.creditRequestLineImageContentType").value(DEFAULT_CREDIT_REQUEST_LINE_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.creditRequestLineImage").value(Base64Utils.encodeToString(DEFAULT_CREDIT_REQUEST_LINE_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingCreditRequestLine() throws Exception {
        // Get the creditRequestLine
        restCreditRequestLineMockMvc.perform(get("/api/credit-request-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditRequestLine() throws Exception {
        // Initialize the database
        creditRequestLineRepository.saveAndFlush(creditRequestLine);

        int databaseSizeBeforeUpdate = creditRequestLineRepository.findAll().size();

        // Update the creditRequestLine
        CreditRequestLine updatedCreditRequestLine = creditRequestLineRepository.findById(creditRequestLine.getId()).get();
        // Disconnect from session so that the updates on updatedCreditRequestLine are not directly saved in db
        em.detach(updatedCreditRequestLine);
        updatedCreditRequestLine
            .creditRequestLineId(UPDATED_CREDIT_REQUEST_LINE_ID)
            .creditRequestLineAmount(UPDATED_CREDIT_REQUEST_LINE_AMOUNT)
            .creditRequestLineQtyCredited(UPDATED_CREDIT_REQUEST_LINE_QTY_CREDITED)
            .creditRequestLineQtyReturn(UPDATED_CREDIT_REQUEST_LINE_QTY_RETURN)
            .creditRequestLineComment(UPDATED_CREDIT_REQUEST_LINE_COMMENT)
            .creditRequestLineImage(UPDATED_CREDIT_REQUEST_LINE_IMAGE)
            .creditRequestLineImageContentType(UPDATED_CREDIT_REQUEST_LINE_IMAGE_CONTENT_TYPE);
        CreditRequestLineDTO creditRequestLineDTO = creditRequestLineMapper.toDto(updatedCreditRequestLine);

        restCreditRequestLineMockMvc.perform(put("/api/credit-request-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestLineDTO)))
            .andExpect(status().isOk());

        // Validate the CreditRequestLine in the database
        List<CreditRequestLine> creditRequestLineList = creditRequestLineRepository.findAll();
        assertThat(creditRequestLineList).hasSize(databaseSizeBeforeUpdate);
        CreditRequestLine testCreditRequestLine = creditRequestLineList.get(creditRequestLineList.size() - 1);
        assertThat(testCreditRequestLine.getCreditRequestLineId()).isEqualTo(UPDATED_CREDIT_REQUEST_LINE_ID);
        assertThat(testCreditRequestLine.getCreditRequestLineAmount()).isEqualTo(UPDATED_CREDIT_REQUEST_LINE_AMOUNT);
        assertThat(testCreditRequestLine.getCreditRequestLineQtyCredited()).isEqualTo(UPDATED_CREDIT_REQUEST_LINE_QTY_CREDITED);
        assertThat(testCreditRequestLine.getCreditRequestLineQtyReturn()).isEqualTo(UPDATED_CREDIT_REQUEST_LINE_QTY_RETURN);
        assertThat(testCreditRequestLine.getCreditRequestLineComment()).isEqualTo(UPDATED_CREDIT_REQUEST_LINE_COMMENT);
        assertThat(testCreditRequestLine.getCreditRequestLineImage()).isEqualTo(UPDATED_CREDIT_REQUEST_LINE_IMAGE);
        assertThat(testCreditRequestLine.getCreditRequestLineImageContentType()).isEqualTo(UPDATED_CREDIT_REQUEST_LINE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditRequestLine() throws Exception {
        int databaseSizeBeforeUpdate = creditRequestLineRepository.findAll().size();

        // Create the CreditRequestLine
        CreditRequestLineDTO creditRequestLineDTO = creditRequestLineMapper.toDto(creditRequestLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditRequestLineMockMvc.perform(put("/api/credit-request-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditRequestLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditRequestLine in the database
        List<CreditRequestLine> creditRequestLineList = creditRequestLineRepository.findAll();
        assertThat(creditRequestLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCreditRequestLine() throws Exception {
        // Initialize the database
        creditRequestLineRepository.saveAndFlush(creditRequestLine);

        int databaseSizeBeforeDelete = creditRequestLineRepository.findAll().size();

        // Delete the creditRequestLine
        restCreditRequestLineMockMvc.perform(delete("/api/credit-request-lines/{id}", creditRequestLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CreditRequestLine> creditRequestLineList = creditRequestLineRepository.findAll();
        assertThat(creditRequestLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditRequestLine.class);
        CreditRequestLine creditRequestLine1 = new CreditRequestLine();
        creditRequestLine1.setId(1L);
        CreditRequestLine creditRequestLine2 = new CreditRequestLine();
        creditRequestLine2.setId(creditRequestLine1.getId());
        assertThat(creditRequestLine1).isEqualTo(creditRequestLine2);
        creditRequestLine2.setId(2L);
        assertThat(creditRequestLine1).isNotEqualTo(creditRequestLine2);
        creditRequestLine1.setId(null);
        assertThat(creditRequestLine1).isNotEqualTo(creditRequestLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditRequestLineDTO.class);
        CreditRequestLineDTO creditRequestLineDTO1 = new CreditRequestLineDTO();
        creditRequestLineDTO1.setId(1L);
        CreditRequestLineDTO creditRequestLineDTO2 = new CreditRequestLineDTO();
        assertThat(creditRequestLineDTO1).isNotEqualTo(creditRequestLineDTO2);
        creditRequestLineDTO2.setId(creditRequestLineDTO1.getId());
        assertThat(creditRequestLineDTO1).isEqualTo(creditRequestLineDTO2);
        creditRequestLineDTO2.setId(2L);
        assertThat(creditRequestLineDTO1).isNotEqualTo(creditRequestLineDTO2);
        creditRequestLineDTO1.setId(null);
        assertThat(creditRequestLineDTO1).isNotEqualTo(creditRequestLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditRequestLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditRequestLineMapper.fromId(null)).isNull();
    }
}
