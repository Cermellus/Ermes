package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.CallLog;
import nz.co.it4biz.repository.CallLogRepository;
import nz.co.it4biz.service.CallLogService;
import nz.co.it4biz.service.dto.CallLogDTO;
import nz.co.it4biz.service.mapper.CallLogMapper;
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
 * Test class for the CallLogResource REST controller.
 *
 * @see CallLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class CallLogResourceIntTest {

    private static final Integer DEFAULT_CALL_LOG_ID = 1;
    private static final Integer UPDATED_CALL_LOG_ID = 2;

    private static final String DEFAULT_CALL_LOG_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_CALL_LOG_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CALL_LOG_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CALL_LOG_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CALL_LOG_CONTACT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_CALL_LOG_CONTACT_MAIL = "BBBBBBBBBB";

    @Autowired
    private CallLogRepository callLogRepository;

    @Autowired
    private CallLogMapper callLogMapper;

    @Autowired
    private CallLogService callLogService;

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

    private MockMvc restCallLogMockMvc;

    private CallLog callLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CallLogResource callLogResource = new CallLogResource(callLogService);
        this.restCallLogMockMvc = MockMvcBuilders.standaloneSetup(callLogResource)
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
    public static CallLog createEntity(EntityManager em) {
        CallLog callLog = new CallLog()
            .callLogId(DEFAULT_CALL_LOG_ID)
            .callLogComment(DEFAULT_CALL_LOG_COMMENT)
            .callLogDate(DEFAULT_CALL_LOG_DATE)
            .callLogContactMail(DEFAULT_CALL_LOG_CONTACT_MAIL);
        return callLog;
    }

    @Before
    public void initTest() {
        callLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createCallLog() throws Exception {
        int databaseSizeBeforeCreate = callLogRepository.findAll().size();

        // Create the CallLog
        CallLogDTO callLogDTO = callLogMapper.toDto(callLog);
        restCallLogMockMvc.perform(post("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogDTO)))
            .andExpect(status().isCreated());

        // Validate the CallLog in the database
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeCreate + 1);
        CallLog testCallLog = callLogList.get(callLogList.size() - 1);
        assertThat(testCallLog.getCallLogId()).isEqualTo(DEFAULT_CALL_LOG_ID);
        assertThat(testCallLog.getCallLogComment()).isEqualTo(DEFAULT_CALL_LOG_COMMENT);
        assertThat(testCallLog.getCallLogDate()).isEqualTo(DEFAULT_CALL_LOG_DATE);
        assertThat(testCallLog.getCallLogContactMail()).isEqualTo(DEFAULT_CALL_LOG_CONTACT_MAIL);
    }

    @Test
    @Transactional
    public void createCallLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = callLogRepository.findAll().size();

        // Create the CallLog with an existing ID
        callLog.setId(1L);
        CallLogDTO callLogDTO = callLogMapper.toDto(callLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCallLogMockMvc.perform(post("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CallLog in the database
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCallLogIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = callLogRepository.findAll().size();
        // set the field null
        callLog.setCallLogId(null);

        // Create the CallLog, which fails.
        CallLogDTO callLogDTO = callLogMapper.toDto(callLog);

        restCallLogMockMvc.perform(post("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogDTO)))
            .andExpect(status().isBadRequest());

        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCallLogCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = callLogRepository.findAll().size();
        // set the field null
        callLog.setCallLogComment(null);

        // Create the CallLog, which fails.
        CallLogDTO callLogDTO = callLogMapper.toDto(callLog);

        restCallLogMockMvc.perform(post("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogDTO)))
            .andExpect(status().isBadRequest());

        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCallLogDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = callLogRepository.findAll().size();
        // set the field null
        callLog.setCallLogDate(null);

        // Create the CallLog, which fails.
        CallLogDTO callLogDTO = callLogMapper.toDto(callLog);

        restCallLogMockMvc.perform(post("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogDTO)))
            .andExpect(status().isBadRequest());

        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCallLogs() throws Exception {
        // Initialize the database
        callLogRepository.saveAndFlush(callLog);

        // Get all the callLogList
        restCallLogMockMvc.perform(get("/api/call-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(callLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].callLogId").value(hasItem(DEFAULT_CALL_LOG_ID)))
            .andExpect(jsonPath("$.[*].callLogComment").value(hasItem(DEFAULT_CALL_LOG_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].callLogDate").value(hasItem(DEFAULT_CALL_LOG_DATE.toString())))
            .andExpect(jsonPath("$.[*].callLogContactMail").value(hasItem(DEFAULT_CALL_LOG_CONTACT_MAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getCallLog() throws Exception {
        // Initialize the database
        callLogRepository.saveAndFlush(callLog);

        // Get the callLog
        restCallLogMockMvc.perform(get("/api/call-logs/{id}", callLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(callLog.getId().intValue()))
            .andExpect(jsonPath("$.callLogId").value(DEFAULT_CALL_LOG_ID))
            .andExpect(jsonPath("$.callLogComment").value(DEFAULT_CALL_LOG_COMMENT.toString()))
            .andExpect(jsonPath("$.callLogDate").value(DEFAULT_CALL_LOG_DATE.toString()))
            .andExpect(jsonPath("$.callLogContactMail").value(DEFAULT_CALL_LOG_CONTACT_MAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCallLog() throws Exception {
        // Get the callLog
        restCallLogMockMvc.perform(get("/api/call-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCallLog() throws Exception {
        // Initialize the database
        callLogRepository.saveAndFlush(callLog);

        int databaseSizeBeforeUpdate = callLogRepository.findAll().size();

        // Update the callLog
        CallLog updatedCallLog = callLogRepository.findById(callLog.getId()).get();
        // Disconnect from session so that the updates on updatedCallLog are not directly saved in db
        em.detach(updatedCallLog);
        updatedCallLog
            .callLogId(UPDATED_CALL_LOG_ID)
            .callLogComment(UPDATED_CALL_LOG_COMMENT)
            .callLogDate(UPDATED_CALL_LOG_DATE)
            .callLogContactMail(UPDATED_CALL_LOG_CONTACT_MAIL);
        CallLogDTO callLogDTO = callLogMapper.toDto(updatedCallLog);

        restCallLogMockMvc.perform(put("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogDTO)))
            .andExpect(status().isOk());

        // Validate the CallLog in the database
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeUpdate);
        CallLog testCallLog = callLogList.get(callLogList.size() - 1);
        assertThat(testCallLog.getCallLogId()).isEqualTo(UPDATED_CALL_LOG_ID);
        assertThat(testCallLog.getCallLogComment()).isEqualTo(UPDATED_CALL_LOG_COMMENT);
        assertThat(testCallLog.getCallLogDate()).isEqualTo(UPDATED_CALL_LOG_DATE);
        assertThat(testCallLog.getCallLogContactMail()).isEqualTo(UPDATED_CALL_LOG_CONTACT_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingCallLog() throws Exception {
        int databaseSizeBeforeUpdate = callLogRepository.findAll().size();

        // Create the CallLog
        CallLogDTO callLogDTO = callLogMapper.toDto(callLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCallLogMockMvc.perform(put("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CallLog in the database
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCallLog() throws Exception {
        // Initialize the database
        callLogRepository.saveAndFlush(callLog);

        int databaseSizeBeforeDelete = callLogRepository.findAll().size();

        // Delete the callLog
        restCallLogMockMvc.perform(delete("/api/call-logs/{id}", callLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CallLog.class);
        CallLog callLog1 = new CallLog();
        callLog1.setId(1L);
        CallLog callLog2 = new CallLog();
        callLog2.setId(callLog1.getId());
        assertThat(callLog1).isEqualTo(callLog2);
        callLog2.setId(2L);
        assertThat(callLog1).isNotEqualTo(callLog2);
        callLog1.setId(null);
        assertThat(callLog1).isNotEqualTo(callLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CallLogDTO.class);
        CallLogDTO callLogDTO1 = new CallLogDTO();
        callLogDTO1.setId(1L);
        CallLogDTO callLogDTO2 = new CallLogDTO();
        assertThat(callLogDTO1).isNotEqualTo(callLogDTO2);
        callLogDTO2.setId(callLogDTO1.getId());
        assertThat(callLogDTO1).isEqualTo(callLogDTO2);
        callLogDTO2.setId(2L);
        assertThat(callLogDTO1).isNotEqualTo(callLogDTO2);
        callLogDTO1.setId(null);
        assertThat(callLogDTO1).isNotEqualTo(callLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(callLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(callLogMapper.fromId(null)).isNull();
    }
}
