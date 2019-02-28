package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.CallLogLine;
import nz.co.it4biz.repository.CallLogLineRepository;
import nz.co.it4biz.service.CallLogLineService;
import nz.co.it4biz.service.dto.CallLogLineDTO;
import nz.co.it4biz.service.mapper.CallLogLineMapper;
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
 * Test class for the CallLogLineResource REST controller.
 *
 * @see CallLogLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class CallLogLineResourceIntTest {

    private static final Integer DEFAULT_CALL_LOG_LINE_ID = 1;
    private static final Integer UPDATED_CALL_LOG_LINE_ID = 2;

    private static final String DEFAULT_CALL_LOG_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_CALL_LOG_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CALL_LOG_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CALL_LOG_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CallLogLineRepository callLogLineRepository;

    @Autowired
    private CallLogLineMapper callLogLineMapper;

    @Autowired
    private CallLogLineService callLogLineService;

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

    private MockMvc restCallLogLineMockMvc;

    private CallLogLine callLogLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CallLogLineResource callLogLineResource = new CallLogLineResource(callLogLineService);
        this.restCallLogLineMockMvc = MockMvcBuilders.standaloneSetup(callLogLineResource)
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
    public static CallLogLine createEntity(EntityManager em) {
        CallLogLine callLogLine = new CallLogLine()
            .callLogLineId(DEFAULT_CALL_LOG_LINE_ID)
            .callLogComment(DEFAULT_CALL_LOG_COMMENT)
            .callLogDueDate(DEFAULT_CALL_LOG_DUE_DATE);
        return callLogLine;
    }

    @Before
    public void initTest() {
        callLogLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCallLogLine() throws Exception {
        int databaseSizeBeforeCreate = callLogLineRepository.findAll().size();

        // Create the CallLogLine
        CallLogLineDTO callLogLineDTO = callLogLineMapper.toDto(callLogLine);
        restCallLogLineMockMvc.perform(post("/api/call-log-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CallLogLine in the database
        List<CallLogLine> callLogLineList = callLogLineRepository.findAll();
        assertThat(callLogLineList).hasSize(databaseSizeBeforeCreate + 1);
        CallLogLine testCallLogLine = callLogLineList.get(callLogLineList.size() - 1);
        assertThat(testCallLogLine.getCallLogLineId()).isEqualTo(DEFAULT_CALL_LOG_LINE_ID);
        assertThat(testCallLogLine.getCallLogComment()).isEqualTo(DEFAULT_CALL_LOG_COMMENT);
        assertThat(testCallLogLine.getCallLogDueDate()).isEqualTo(DEFAULT_CALL_LOG_DUE_DATE);
    }

    @Test
    @Transactional
    public void createCallLogLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = callLogLineRepository.findAll().size();

        // Create the CallLogLine with an existing ID
        callLogLine.setId(1L);
        CallLogLineDTO callLogLineDTO = callLogLineMapper.toDto(callLogLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCallLogLineMockMvc.perform(post("/api/call-log-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CallLogLine in the database
        List<CallLogLine> callLogLineList = callLogLineRepository.findAll();
        assertThat(callLogLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCallLogLineIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = callLogLineRepository.findAll().size();
        // set the field null
        callLogLine.setCallLogLineId(null);

        // Create the CallLogLine, which fails.
        CallLogLineDTO callLogLineDTO = callLogLineMapper.toDto(callLogLine);

        restCallLogLineMockMvc.perform(post("/api/call-log-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogLineDTO)))
            .andExpect(status().isBadRequest());

        List<CallLogLine> callLogLineList = callLogLineRepository.findAll();
        assertThat(callLogLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCallLogDueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = callLogLineRepository.findAll().size();
        // set the field null
        callLogLine.setCallLogDueDate(null);

        // Create the CallLogLine, which fails.
        CallLogLineDTO callLogLineDTO = callLogLineMapper.toDto(callLogLine);

        restCallLogLineMockMvc.perform(post("/api/call-log-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogLineDTO)))
            .andExpect(status().isBadRequest());

        List<CallLogLine> callLogLineList = callLogLineRepository.findAll();
        assertThat(callLogLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCallLogLines() throws Exception {
        // Initialize the database
        callLogLineRepository.saveAndFlush(callLogLine);

        // Get all the callLogLineList
        restCallLogLineMockMvc.perform(get("/api/call-log-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(callLogLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].callLogLineId").value(hasItem(DEFAULT_CALL_LOG_LINE_ID)))
            .andExpect(jsonPath("$.[*].callLogComment").value(hasItem(DEFAULT_CALL_LOG_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].callLogDueDate").value(hasItem(DEFAULT_CALL_LOG_DUE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCallLogLine() throws Exception {
        // Initialize the database
        callLogLineRepository.saveAndFlush(callLogLine);

        // Get the callLogLine
        restCallLogLineMockMvc.perform(get("/api/call-log-lines/{id}", callLogLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(callLogLine.getId().intValue()))
            .andExpect(jsonPath("$.callLogLineId").value(DEFAULT_CALL_LOG_LINE_ID))
            .andExpect(jsonPath("$.callLogComment").value(DEFAULT_CALL_LOG_COMMENT.toString()))
            .andExpect(jsonPath("$.callLogDueDate").value(DEFAULT_CALL_LOG_DUE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCallLogLine() throws Exception {
        // Get the callLogLine
        restCallLogLineMockMvc.perform(get("/api/call-log-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCallLogLine() throws Exception {
        // Initialize the database
        callLogLineRepository.saveAndFlush(callLogLine);

        int databaseSizeBeforeUpdate = callLogLineRepository.findAll().size();

        // Update the callLogLine
        CallLogLine updatedCallLogLine = callLogLineRepository.findById(callLogLine.getId()).get();
        // Disconnect from session so that the updates on updatedCallLogLine are not directly saved in db
        em.detach(updatedCallLogLine);
        updatedCallLogLine
            .callLogLineId(UPDATED_CALL_LOG_LINE_ID)
            .callLogComment(UPDATED_CALL_LOG_COMMENT)
            .callLogDueDate(UPDATED_CALL_LOG_DUE_DATE);
        CallLogLineDTO callLogLineDTO = callLogLineMapper.toDto(updatedCallLogLine);

        restCallLogLineMockMvc.perform(put("/api/call-log-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogLineDTO)))
            .andExpect(status().isOk());

        // Validate the CallLogLine in the database
        List<CallLogLine> callLogLineList = callLogLineRepository.findAll();
        assertThat(callLogLineList).hasSize(databaseSizeBeforeUpdate);
        CallLogLine testCallLogLine = callLogLineList.get(callLogLineList.size() - 1);
        assertThat(testCallLogLine.getCallLogLineId()).isEqualTo(UPDATED_CALL_LOG_LINE_ID);
        assertThat(testCallLogLine.getCallLogComment()).isEqualTo(UPDATED_CALL_LOG_COMMENT);
        assertThat(testCallLogLine.getCallLogDueDate()).isEqualTo(UPDATED_CALL_LOG_DUE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCallLogLine() throws Exception {
        int databaseSizeBeforeUpdate = callLogLineRepository.findAll().size();

        // Create the CallLogLine
        CallLogLineDTO callLogLineDTO = callLogLineMapper.toDto(callLogLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCallLogLineMockMvc.perform(put("/api/call-log-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CallLogLine in the database
        List<CallLogLine> callLogLineList = callLogLineRepository.findAll();
        assertThat(callLogLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCallLogLine() throws Exception {
        // Initialize the database
        callLogLineRepository.saveAndFlush(callLogLine);

        int databaseSizeBeforeDelete = callLogLineRepository.findAll().size();

        // Delete the callLogLine
        restCallLogLineMockMvc.perform(delete("/api/call-log-lines/{id}", callLogLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CallLogLine> callLogLineList = callLogLineRepository.findAll();
        assertThat(callLogLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CallLogLine.class);
        CallLogLine callLogLine1 = new CallLogLine();
        callLogLine1.setId(1L);
        CallLogLine callLogLine2 = new CallLogLine();
        callLogLine2.setId(callLogLine1.getId());
        assertThat(callLogLine1).isEqualTo(callLogLine2);
        callLogLine2.setId(2L);
        assertThat(callLogLine1).isNotEqualTo(callLogLine2);
        callLogLine1.setId(null);
        assertThat(callLogLine1).isNotEqualTo(callLogLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CallLogLineDTO.class);
        CallLogLineDTO callLogLineDTO1 = new CallLogLineDTO();
        callLogLineDTO1.setId(1L);
        CallLogLineDTO callLogLineDTO2 = new CallLogLineDTO();
        assertThat(callLogLineDTO1).isNotEqualTo(callLogLineDTO2);
        callLogLineDTO2.setId(callLogLineDTO1.getId());
        assertThat(callLogLineDTO1).isEqualTo(callLogLineDTO2);
        callLogLineDTO2.setId(2L);
        assertThat(callLogLineDTO1).isNotEqualTo(callLogLineDTO2);
        callLogLineDTO1.setId(null);
        assertThat(callLogLineDTO1).isNotEqualTo(callLogLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(callLogLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(callLogLineMapper.fromId(null)).isNull();
    }
}
