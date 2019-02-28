package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.CallLogAction;
import nz.co.it4biz.repository.CallLogActionRepository;
import nz.co.it4biz.service.CallLogActionService;
import nz.co.it4biz.service.dto.CallLogActionDTO;
import nz.co.it4biz.service.mapper.CallLogActionMapper;
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
 * Test class for the CallLogActionResource REST controller.
 *
 * @see CallLogActionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class CallLogActionResourceIntTest {

    private static final Integer DEFAULT_CALL_LOG_ACTION_ID = 1;
    private static final Integer UPDATED_CALL_LOG_ACTION_ID = 2;

    private static final String DEFAULT_CALL_LOG_ACTION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CALL_LOG_ACTION_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CallLogActionRepository callLogActionRepository;

    @Autowired
    private CallLogActionMapper callLogActionMapper;

    @Autowired
    private CallLogActionService callLogActionService;

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

    private MockMvc restCallLogActionMockMvc;

    private CallLogAction callLogAction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CallLogActionResource callLogActionResource = new CallLogActionResource(callLogActionService);
        this.restCallLogActionMockMvc = MockMvcBuilders.standaloneSetup(callLogActionResource)
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
    public static CallLogAction createEntity(EntityManager em) {
        CallLogAction callLogAction = new CallLogAction()
            .callLogActionId(DEFAULT_CALL_LOG_ACTION_ID)
            .callLogActionDescription(DEFAULT_CALL_LOG_ACTION_DESCRIPTION);
        return callLogAction;
    }

    @Before
    public void initTest() {
        callLogAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createCallLogAction() throws Exception {
        int databaseSizeBeforeCreate = callLogActionRepository.findAll().size();

        // Create the CallLogAction
        CallLogActionDTO callLogActionDTO = callLogActionMapper.toDto(callLogAction);
        restCallLogActionMockMvc.perform(post("/api/call-log-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogActionDTO)))
            .andExpect(status().isCreated());

        // Validate the CallLogAction in the database
        List<CallLogAction> callLogActionList = callLogActionRepository.findAll();
        assertThat(callLogActionList).hasSize(databaseSizeBeforeCreate + 1);
        CallLogAction testCallLogAction = callLogActionList.get(callLogActionList.size() - 1);
        assertThat(testCallLogAction.getCallLogActionId()).isEqualTo(DEFAULT_CALL_LOG_ACTION_ID);
        assertThat(testCallLogAction.getCallLogActionDescription()).isEqualTo(DEFAULT_CALL_LOG_ACTION_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCallLogActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = callLogActionRepository.findAll().size();

        // Create the CallLogAction with an existing ID
        callLogAction.setId(1L);
        CallLogActionDTO callLogActionDTO = callLogActionMapper.toDto(callLogAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCallLogActionMockMvc.perform(post("/api/call-log-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CallLogAction in the database
        List<CallLogAction> callLogActionList = callLogActionRepository.findAll();
        assertThat(callLogActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCallLogActionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = callLogActionRepository.findAll().size();
        // set the field null
        callLogAction.setCallLogActionId(null);

        // Create the CallLogAction, which fails.
        CallLogActionDTO callLogActionDTO = callLogActionMapper.toDto(callLogAction);

        restCallLogActionMockMvc.perform(post("/api/call-log-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogActionDTO)))
            .andExpect(status().isBadRequest());

        List<CallLogAction> callLogActionList = callLogActionRepository.findAll();
        assertThat(callLogActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCallLogActionDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = callLogActionRepository.findAll().size();
        // set the field null
        callLogAction.setCallLogActionDescription(null);

        // Create the CallLogAction, which fails.
        CallLogActionDTO callLogActionDTO = callLogActionMapper.toDto(callLogAction);

        restCallLogActionMockMvc.perform(post("/api/call-log-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogActionDTO)))
            .andExpect(status().isBadRequest());

        List<CallLogAction> callLogActionList = callLogActionRepository.findAll();
        assertThat(callLogActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCallLogActions() throws Exception {
        // Initialize the database
        callLogActionRepository.saveAndFlush(callLogAction);

        // Get all the callLogActionList
        restCallLogActionMockMvc.perform(get("/api/call-log-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(callLogAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].callLogActionId").value(hasItem(DEFAULT_CALL_LOG_ACTION_ID)))
            .andExpect(jsonPath("$.[*].callLogActionDescription").value(hasItem(DEFAULT_CALL_LOG_ACTION_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCallLogAction() throws Exception {
        // Initialize the database
        callLogActionRepository.saveAndFlush(callLogAction);

        // Get the callLogAction
        restCallLogActionMockMvc.perform(get("/api/call-log-actions/{id}", callLogAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(callLogAction.getId().intValue()))
            .andExpect(jsonPath("$.callLogActionId").value(DEFAULT_CALL_LOG_ACTION_ID))
            .andExpect(jsonPath("$.callLogActionDescription").value(DEFAULT_CALL_LOG_ACTION_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCallLogAction() throws Exception {
        // Get the callLogAction
        restCallLogActionMockMvc.perform(get("/api/call-log-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCallLogAction() throws Exception {
        // Initialize the database
        callLogActionRepository.saveAndFlush(callLogAction);

        int databaseSizeBeforeUpdate = callLogActionRepository.findAll().size();

        // Update the callLogAction
        CallLogAction updatedCallLogAction = callLogActionRepository.findById(callLogAction.getId()).get();
        // Disconnect from session so that the updates on updatedCallLogAction are not directly saved in db
        em.detach(updatedCallLogAction);
        updatedCallLogAction
            .callLogActionId(UPDATED_CALL_LOG_ACTION_ID)
            .callLogActionDescription(UPDATED_CALL_LOG_ACTION_DESCRIPTION);
        CallLogActionDTO callLogActionDTO = callLogActionMapper.toDto(updatedCallLogAction);

        restCallLogActionMockMvc.perform(put("/api/call-log-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogActionDTO)))
            .andExpect(status().isOk());

        // Validate the CallLogAction in the database
        List<CallLogAction> callLogActionList = callLogActionRepository.findAll();
        assertThat(callLogActionList).hasSize(databaseSizeBeforeUpdate);
        CallLogAction testCallLogAction = callLogActionList.get(callLogActionList.size() - 1);
        assertThat(testCallLogAction.getCallLogActionId()).isEqualTo(UPDATED_CALL_LOG_ACTION_ID);
        assertThat(testCallLogAction.getCallLogActionDescription()).isEqualTo(UPDATED_CALL_LOG_ACTION_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCallLogAction() throws Exception {
        int databaseSizeBeforeUpdate = callLogActionRepository.findAll().size();

        // Create the CallLogAction
        CallLogActionDTO callLogActionDTO = callLogActionMapper.toDto(callLogAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCallLogActionMockMvc.perform(put("/api/call-log-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLogActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CallLogAction in the database
        List<CallLogAction> callLogActionList = callLogActionRepository.findAll();
        assertThat(callLogActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCallLogAction() throws Exception {
        // Initialize the database
        callLogActionRepository.saveAndFlush(callLogAction);

        int databaseSizeBeforeDelete = callLogActionRepository.findAll().size();

        // Delete the callLogAction
        restCallLogActionMockMvc.perform(delete("/api/call-log-actions/{id}", callLogAction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CallLogAction> callLogActionList = callLogActionRepository.findAll();
        assertThat(callLogActionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CallLogAction.class);
        CallLogAction callLogAction1 = new CallLogAction();
        callLogAction1.setId(1L);
        CallLogAction callLogAction2 = new CallLogAction();
        callLogAction2.setId(callLogAction1.getId());
        assertThat(callLogAction1).isEqualTo(callLogAction2);
        callLogAction2.setId(2L);
        assertThat(callLogAction1).isNotEqualTo(callLogAction2);
        callLogAction1.setId(null);
        assertThat(callLogAction1).isNotEqualTo(callLogAction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CallLogActionDTO.class);
        CallLogActionDTO callLogActionDTO1 = new CallLogActionDTO();
        callLogActionDTO1.setId(1L);
        CallLogActionDTO callLogActionDTO2 = new CallLogActionDTO();
        assertThat(callLogActionDTO1).isNotEqualTo(callLogActionDTO2);
        callLogActionDTO2.setId(callLogActionDTO1.getId());
        assertThat(callLogActionDTO1).isEqualTo(callLogActionDTO2);
        callLogActionDTO2.setId(2L);
        assertThat(callLogActionDTO1).isNotEqualTo(callLogActionDTO2);
        callLogActionDTO1.setId(null);
        assertThat(callLogActionDTO1).isNotEqualTo(callLogActionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(callLogActionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(callLogActionMapper.fromId(null)).isNull();
    }
}
