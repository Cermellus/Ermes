package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.CreditReturnType;
import nz.co.it4biz.repository.CreditReturnTypeRepository;
import nz.co.it4biz.service.CreditReturnTypeService;
import nz.co.it4biz.service.dto.CreditReturnTypeDTO;
import nz.co.it4biz.service.mapper.CreditReturnTypeMapper;
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
 * Test class for the CreditReturnTypeResource REST controller.
 *
 * @see CreditReturnTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class CreditReturnTypeResourceIntTest {

    private static final Integer DEFAULT_CREDIT_RETURN_TYPE_ID = 1;
    private static final Integer UPDATED_CREDIT_RETURN_TYPE_ID = 2;

    private static final String DEFAULT_CREDIT_RETURN_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_RETURN_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CREDIT_RETURN_TYPE_ARRANGE_RETURN = false;
    private static final Boolean UPDATED_CREDIT_RETURN_TYPE_ARRANGE_RETURN = true;

    @Autowired
    private CreditReturnTypeRepository creditReturnTypeRepository;

    @Autowired
    private CreditReturnTypeMapper creditReturnTypeMapper;

    @Autowired
    private CreditReturnTypeService creditReturnTypeService;

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

    private MockMvc restCreditReturnTypeMockMvc;

    private CreditReturnType creditReturnType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditReturnTypeResource creditReturnTypeResource = new CreditReturnTypeResource(creditReturnTypeService);
        this.restCreditReturnTypeMockMvc = MockMvcBuilders.standaloneSetup(creditReturnTypeResource)
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
    public static CreditReturnType createEntity(EntityManager em) {
        CreditReturnType creditReturnType = new CreditReturnType()
            .creditReturnTypeId(DEFAULT_CREDIT_RETURN_TYPE_ID)
            .creditReturnTypeDescription(DEFAULT_CREDIT_RETURN_TYPE_DESCRIPTION)
            .creditReturnTypeArrangeReturn(DEFAULT_CREDIT_RETURN_TYPE_ARRANGE_RETURN);
        return creditReturnType;
    }

    @Before
    public void initTest() {
        creditReturnType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditReturnType() throws Exception {
        int databaseSizeBeforeCreate = creditReturnTypeRepository.findAll().size();

        // Create the CreditReturnType
        CreditReturnTypeDTO creditReturnTypeDTO = creditReturnTypeMapper.toDto(creditReturnType);
        restCreditReturnTypeMockMvc.perform(post("/api/credit-return-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReturnTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditReturnType in the database
        List<CreditReturnType> creditReturnTypeList = creditReturnTypeRepository.findAll();
        assertThat(creditReturnTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CreditReturnType testCreditReturnType = creditReturnTypeList.get(creditReturnTypeList.size() - 1);
        assertThat(testCreditReturnType.getCreditReturnTypeId()).isEqualTo(DEFAULT_CREDIT_RETURN_TYPE_ID);
        assertThat(testCreditReturnType.getCreditReturnTypeDescription()).isEqualTo(DEFAULT_CREDIT_RETURN_TYPE_DESCRIPTION);
        assertThat(testCreditReturnType.isCreditReturnTypeArrangeReturn()).isEqualTo(DEFAULT_CREDIT_RETURN_TYPE_ARRANGE_RETURN);
    }

    @Test
    @Transactional
    public void createCreditReturnTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditReturnTypeRepository.findAll().size();

        // Create the CreditReturnType with an existing ID
        creditReturnType.setId(1L);
        CreditReturnTypeDTO creditReturnTypeDTO = creditReturnTypeMapper.toDto(creditReturnType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditReturnTypeMockMvc.perform(post("/api/credit-return-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReturnTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditReturnType in the database
        List<CreditReturnType> creditReturnTypeList = creditReturnTypeRepository.findAll();
        assertThat(creditReturnTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreditReturnTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReturnTypeRepository.findAll().size();
        // set the field null
        creditReturnType.setCreditReturnTypeId(null);

        // Create the CreditReturnType, which fails.
        CreditReturnTypeDTO creditReturnTypeDTO = creditReturnTypeMapper.toDto(creditReturnType);

        restCreditReturnTypeMockMvc.perform(post("/api/credit-return-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReturnTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReturnType> creditReturnTypeList = creditReturnTypeRepository.findAll();
        assertThat(creditReturnTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditReturnTypeDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReturnTypeRepository.findAll().size();
        // set the field null
        creditReturnType.setCreditReturnTypeDescription(null);

        // Create the CreditReturnType, which fails.
        CreditReturnTypeDTO creditReturnTypeDTO = creditReturnTypeMapper.toDto(creditReturnType);

        restCreditReturnTypeMockMvc.perform(post("/api/credit-return-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReturnTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReturnType> creditReturnTypeList = creditReturnTypeRepository.findAll();
        assertThat(creditReturnTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditReturnTypeArrangeReturnIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditReturnTypeRepository.findAll().size();
        // set the field null
        creditReturnType.setCreditReturnTypeArrangeReturn(null);

        // Create the CreditReturnType, which fails.
        CreditReturnTypeDTO creditReturnTypeDTO = creditReturnTypeMapper.toDto(creditReturnType);

        restCreditReturnTypeMockMvc.perform(post("/api/credit-return-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReturnTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CreditReturnType> creditReturnTypeList = creditReturnTypeRepository.findAll();
        assertThat(creditReturnTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCreditReturnTypes() throws Exception {
        // Initialize the database
        creditReturnTypeRepository.saveAndFlush(creditReturnType);

        // Get all the creditReturnTypeList
        restCreditReturnTypeMockMvc.perform(get("/api/credit-return-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditReturnType.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditReturnTypeId").value(hasItem(DEFAULT_CREDIT_RETURN_TYPE_ID)))
            .andExpect(jsonPath("$.[*].creditReturnTypeDescription").value(hasItem(DEFAULT_CREDIT_RETURN_TYPE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].creditReturnTypeArrangeReturn").value(hasItem(DEFAULT_CREDIT_RETURN_TYPE_ARRANGE_RETURN.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCreditReturnType() throws Exception {
        // Initialize the database
        creditReturnTypeRepository.saveAndFlush(creditReturnType);

        // Get the creditReturnType
        restCreditReturnTypeMockMvc.perform(get("/api/credit-return-types/{id}", creditReturnType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creditReturnType.getId().intValue()))
            .andExpect(jsonPath("$.creditReturnTypeId").value(DEFAULT_CREDIT_RETURN_TYPE_ID))
            .andExpect(jsonPath("$.creditReturnTypeDescription").value(DEFAULT_CREDIT_RETURN_TYPE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.creditReturnTypeArrangeReturn").value(DEFAULT_CREDIT_RETURN_TYPE_ARRANGE_RETURN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCreditReturnType() throws Exception {
        // Get the creditReturnType
        restCreditReturnTypeMockMvc.perform(get("/api/credit-return-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditReturnType() throws Exception {
        // Initialize the database
        creditReturnTypeRepository.saveAndFlush(creditReturnType);

        int databaseSizeBeforeUpdate = creditReturnTypeRepository.findAll().size();

        // Update the creditReturnType
        CreditReturnType updatedCreditReturnType = creditReturnTypeRepository.findById(creditReturnType.getId()).get();
        // Disconnect from session so that the updates on updatedCreditReturnType are not directly saved in db
        em.detach(updatedCreditReturnType);
        updatedCreditReturnType
            .creditReturnTypeId(UPDATED_CREDIT_RETURN_TYPE_ID)
            .creditReturnTypeDescription(UPDATED_CREDIT_RETURN_TYPE_DESCRIPTION)
            .creditReturnTypeArrangeReturn(UPDATED_CREDIT_RETURN_TYPE_ARRANGE_RETURN);
        CreditReturnTypeDTO creditReturnTypeDTO = creditReturnTypeMapper.toDto(updatedCreditReturnType);

        restCreditReturnTypeMockMvc.perform(put("/api/credit-return-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReturnTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CreditReturnType in the database
        List<CreditReturnType> creditReturnTypeList = creditReturnTypeRepository.findAll();
        assertThat(creditReturnTypeList).hasSize(databaseSizeBeforeUpdate);
        CreditReturnType testCreditReturnType = creditReturnTypeList.get(creditReturnTypeList.size() - 1);
        assertThat(testCreditReturnType.getCreditReturnTypeId()).isEqualTo(UPDATED_CREDIT_RETURN_TYPE_ID);
        assertThat(testCreditReturnType.getCreditReturnTypeDescription()).isEqualTo(UPDATED_CREDIT_RETURN_TYPE_DESCRIPTION);
        assertThat(testCreditReturnType.isCreditReturnTypeArrangeReturn()).isEqualTo(UPDATED_CREDIT_RETURN_TYPE_ARRANGE_RETURN);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditReturnType() throws Exception {
        int databaseSizeBeforeUpdate = creditReturnTypeRepository.findAll().size();

        // Create the CreditReturnType
        CreditReturnTypeDTO creditReturnTypeDTO = creditReturnTypeMapper.toDto(creditReturnType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditReturnTypeMockMvc.perform(put("/api/credit-return-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditReturnTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditReturnType in the database
        List<CreditReturnType> creditReturnTypeList = creditReturnTypeRepository.findAll();
        assertThat(creditReturnTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCreditReturnType() throws Exception {
        // Initialize the database
        creditReturnTypeRepository.saveAndFlush(creditReturnType);

        int databaseSizeBeforeDelete = creditReturnTypeRepository.findAll().size();

        // Delete the creditReturnType
        restCreditReturnTypeMockMvc.perform(delete("/api/credit-return-types/{id}", creditReturnType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CreditReturnType> creditReturnTypeList = creditReturnTypeRepository.findAll();
        assertThat(creditReturnTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditReturnType.class);
        CreditReturnType creditReturnType1 = new CreditReturnType();
        creditReturnType1.setId(1L);
        CreditReturnType creditReturnType2 = new CreditReturnType();
        creditReturnType2.setId(creditReturnType1.getId());
        assertThat(creditReturnType1).isEqualTo(creditReturnType2);
        creditReturnType2.setId(2L);
        assertThat(creditReturnType1).isNotEqualTo(creditReturnType2);
        creditReturnType1.setId(null);
        assertThat(creditReturnType1).isNotEqualTo(creditReturnType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditReturnTypeDTO.class);
        CreditReturnTypeDTO creditReturnTypeDTO1 = new CreditReturnTypeDTO();
        creditReturnTypeDTO1.setId(1L);
        CreditReturnTypeDTO creditReturnTypeDTO2 = new CreditReturnTypeDTO();
        assertThat(creditReturnTypeDTO1).isNotEqualTo(creditReturnTypeDTO2);
        creditReturnTypeDTO2.setId(creditReturnTypeDTO1.getId());
        assertThat(creditReturnTypeDTO1).isEqualTo(creditReturnTypeDTO2);
        creditReturnTypeDTO2.setId(2L);
        assertThat(creditReturnTypeDTO1).isNotEqualTo(creditReturnTypeDTO2);
        creditReturnTypeDTO1.setId(null);
        assertThat(creditReturnTypeDTO1).isNotEqualTo(creditReturnTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditReturnTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditReturnTypeMapper.fromId(null)).isNull();
    }
}
