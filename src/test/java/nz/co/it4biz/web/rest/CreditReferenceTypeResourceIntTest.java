package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.CreditReferenceType;
import nz.co.it4biz.repository.CreditReferenceTypeRepository;
import nz.co.it4biz.service.CreditReferenceTypeService;
import nz.co.it4biz.service.dto.CreditReferenceTypeDTO;
import nz.co.it4biz.service.mapper.CreditReferenceTypeMapper;
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
 * Test class for the CreditReferenceTypeResource REST controller.
 *
 * @see CreditReferenceTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class CreditReferenceTypeResourceIntTest {

    private static final Integer DEFAULT_CREDIT_REFERENCE_TYPE_ID = 1;
    private static final Integer UPDATED_CREDIT_REFERENCE_TYPE_ID = 2;

    private static final String DEFAULT_CREDIT_REFERENCE_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_REFERENCE_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CreditReferenceTypeRepository creditReferenceTypeRepository;

    @Autowired
    private CreditReferenceTypeMapper creditReferenceTypeMapper;

    @Autowired
    private CreditReferenceTypeService creditReferenceTypeService;

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

    private MockMvc restCreditReferenceTypeMockMvc;

    private CreditReferenceType creditReferenceType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditReferenceTypeResource creditReferenceTypeResource = new CreditReferenceTypeResource(creditReferenceTypeService);
        this.restCreditReferenceTypeMockMvc = MockMvcBuilders.standaloneSetup(creditReferenceTypeResource)
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
    public static CreditReferenceType createEntity(EntityManager em) {
        CreditReferenceType creditReferenceType = new CreditReferenceType()
            .creditReferenceTypeId(DEFAULT_CREDIT_REFERENCE_TYPE_ID)
            .creditReferenceTypeDescription(DEFAULT_CREDIT_REFERENCE_TYPE_DESCRIPTION);
        return creditReferenceType;
    }

    @Before
    public void initTest() {
        creditReferenceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditReferenceType() throws Exception {
        int databaseSizeBeforeCreate = creditReferenceTypeRepository.findAll().size();

        // Create the CreditReferenceType
        CreditReferenceTypeDTO creditReferenceTypeDTO = creditReferenceTypeMapper.toDto(creditReferenceType);
        restCreditReferenceTypeMockMvc.perform(post("/api/credit-reference-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReferenceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditReferenceType in the database
        List<CreditReferenceType> creditReferenceTypeList = creditReferenceTypeRepository.findAll();
        assertThat(creditReferenceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CreditReferenceType testCreditReferenceType = creditReferenceTypeList.get(creditReferenceTypeList.size() - 1);
        assertThat(testCreditReferenceType.getCreditReferenceTypeId()).isEqualTo(DEFAULT_CREDIT_REFERENCE_TYPE_ID);
        assertThat(testCreditReferenceType.getCreditReferenceTypeDescription()).isEqualTo(DEFAULT_CREDIT_REFERENCE_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCreditReferenceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditReferenceTypeRepository.findAll().size();

        // Create the CreditReferenceType with an existing ID
        creditReferenceType.setId(1L);
        CreditReferenceTypeDTO creditReferenceTypeDTO = creditReferenceTypeMapper.toDto(creditReferenceType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditReferenceTypeMockMvc.perform(post("/api/credit-reference-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReferenceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditReferenceType in the database
        List<CreditReferenceType> creditReferenceTypeList = creditReferenceTypeRepository.findAll();
        assertThat(creditReferenceTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreditReferenceTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReferenceTypeRepository.findAll().size();
        // set the field null
        creditReferenceType.setCreditReferenceTypeId(null);

        // Create the CreditReferenceType, which fails.
        CreditReferenceTypeDTO creditReferenceTypeDTO = creditReferenceTypeMapper.toDto(creditReferenceType);

        restCreditReferenceTypeMockMvc.perform(post("/api/credit-reference-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReferenceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReferenceType> creditReferenceTypeList = creditReferenceTypeRepository.findAll();
        assertThat(creditReferenceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditReferenceTypeDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReferenceTypeRepository.findAll().size();
        // set the field null
        creditReferenceType.setCreditReferenceTypeDescription(null);

        // Create the CreditReferenceType, which fails.
        CreditReferenceTypeDTO creditReferenceTypeDTO = creditReferenceTypeMapper.toDto(creditReferenceType);

        restCreditReferenceTypeMockMvc.perform(post("/api/credit-reference-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReferenceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReferenceType> creditReferenceTypeList = creditReferenceTypeRepository.findAll();
        assertThat(creditReferenceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCreditReferenceTypes() throws Exception {
        // Initialize the database
        creditReferenceTypeRepository.saveAndFlush(creditReferenceType);

        // Get all the creditReferenceTypeList
        restCreditReferenceTypeMockMvc.perform(get("/api/credit-reference-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditReferenceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditReferenceTypeId").value(hasItem(DEFAULT_CREDIT_REFERENCE_TYPE_ID)))
            .andExpect(jsonPath("$.[*].creditReferenceTypeDescription").value(hasItem(DEFAULT_CREDIT_REFERENCE_TYPE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCreditReferenceType() throws Exception {
        // Initialize the database
        creditReferenceTypeRepository.saveAndFlush(creditReferenceType);

        // Get the creditReferenceType
        restCreditReferenceTypeMockMvc.perform(get("/api/credit-reference-types/{id}", creditReferenceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creditReferenceType.getId().intValue()))
            .andExpect(jsonPath("$.creditReferenceTypeId").value(DEFAULT_CREDIT_REFERENCE_TYPE_ID))
            .andExpect(jsonPath("$.creditReferenceTypeDescription").value(DEFAULT_CREDIT_REFERENCE_TYPE_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCreditReferenceType() throws Exception {
        // Get the creditReferenceType
        restCreditReferenceTypeMockMvc.perform(get("/api/credit-reference-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditReferenceType() throws Exception {
        // Initialize the database
        creditReferenceTypeRepository.saveAndFlush(creditReferenceType);

        int databaseSizeBeforeUpdate = creditReferenceTypeRepository.findAll().size();

        // Update the creditReferenceType
        CreditReferenceType updatedCreditReferenceType = creditReferenceTypeRepository.findById(creditReferenceType.getId()).get();
        // Disconnect from session so that the updates on updatedCreditReferenceType are not directly saved in db
        em.detach(updatedCreditReferenceType);
        updatedCreditReferenceType
            .creditReferenceTypeId(UPDATED_CREDIT_REFERENCE_TYPE_ID)
            .creditReferenceTypeDescription(UPDATED_CREDIT_REFERENCE_TYPE_DESCRIPTION);
        CreditReferenceTypeDTO creditReferenceTypeDTO = creditReferenceTypeMapper.toDto(updatedCreditReferenceType);

        restCreditReferenceTypeMockMvc.perform(put("/api/credit-reference-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReferenceTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CreditReferenceType in the database
        List<CreditReferenceType> creditReferenceTypeList = creditReferenceTypeRepository.findAll();
        assertThat(creditReferenceTypeList).hasSize(databaseSizeBeforeUpdate);
        CreditReferenceType testCreditReferenceType = creditReferenceTypeList.get(creditReferenceTypeList.size() - 1);
        assertThat(testCreditReferenceType.getCreditReferenceTypeId()).isEqualTo(UPDATED_CREDIT_REFERENCE_TYPE_ID);
        assertThat(testCreditReferenceType.getCreditReferenceTypeDescription()).isEqualTo(UPDATED_CREDIT_REFERENCE_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditReferenceType() throws Exception {
        int databaseSizeBeforeUpdate = creditReferenceTypeRepository.findAll().size();

        // Create the CreditReferenceType
        CreditReferenceTypeDTO creditReferenceTypeDTO = creditReferenceTypeMapper.toDto(creditReferenceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditReferenceTypeMockMvc.perform(put("/api/credit-reference-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReferenceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditReferenceType in the database
        List<CreditReferenceType> creditReferenceTypeList = creditReferenceTypeRepository.findAll();
        assertThat(creditReferenceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCreditReferenceType() throws Exception {
        // Initialize the database
        creditReferenceTypeRepository.saveAndFlush(creditReferenceType);

        int databaseSizeBeforeDelete = creditReferenceTypeRepository.findAll().size();

        // Delete the creditReferenceType
        restCreditReferenceTypeMockMvc.perform(delete("/api/credit-reference-types/{id}", creditReferenceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CreditReferenceType> creditReferenceTypeList = creditReferenceTypeRepository.findAll();
        assertThat(creditReferenceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditReferenceType.class);
        CreditReferenceType creditReferenceType1 = new CreditReferenceType();
        creditReferenceType1.setId(1L);
        CreditReferenceType creditReferenceType2 = new CreditReferenceType();
        creditReferenceType2.setId(creditReferenceType1.getId());
        assertThat(creditReferenceType1).isEqualTo(creditReferenceType2);
        creditReferenceType2.setId(2L);
        assertThat(creditReferenceType1).isNotEqualTo(creditReferenceType2);
        creditReferenceType1.setId(null);
        assertThat(creditReferenceType1).isNotEqualTo(creditReferenceType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditReferenceTypeDTO.class);
        CreditReferenceTypeDTO creditReferenceTypeDTO1 = new CreditReferenceTypeDTO();
        creditReferenceTypeDTO1.setId(1L);
        CreditReferenceTypeDTO creditReferenceTypeDTO2 = new CreditReferenceTypeDTO();
        assertThat(creditReferenceTypeDTO1).isNotEqualTo(creditReferenceTypeDTO2);
        creditReferenceTypeDTO2.setId(creditReferenceTypeDTO1.getId());
        assertThat(creditReferenceTypeDTO1).isEqualTo(creditReferenceTypeDTO2);
        creditReferenceTypeDTO2.setId(2L);
        assertThat(creditReferenceTypeDTO1).isNotEqualTo(creditReferenceTypeDTO2);
        creditReferenceTypeDTO1.setId(null);
        assertThat(creditReferenceTypeDTO1).isNotEqualTo(creditReferenceTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditReferenceTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditReferenceTypeMapper.fromId(null)).isNull();
    }
}
