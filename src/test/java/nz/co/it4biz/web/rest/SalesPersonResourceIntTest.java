package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.SalesPerson;
import nz.co.it4biz.repository.SalesPersonRepository;
import nz.co.it4biz.service.SalesPersonService;
import nz.co.it4biz.service.dto.SalesPersonDTO;
import nz.co.it4biz.service.mapper.SalesPersonMapper;
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
 * Test class for the SalesPersonResource REST controller.
 *
 * @see SalesPersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class SalesPersonResourceIntTest {

    private static final Integer DEFAULT_SALES_PERSON_ID = 1;
    private static final Integer UPDATED_SALES_PERSON_ID = 2;

    private static final String DEFAULT_SALES_PERSON_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SALES_PERSON_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SALES_PERSON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_PERSON_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_SALES_PERSON_MAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SALES_PERSON_INACTIVE = false;
    private static final Boolean UPDATED_SALES_PERSON_INACTIVE = true;

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private SalesPersonMapper salesPersonMapper;

    @Autowired
    private SalesPersonService salesPersonService;

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

    private MockMvc restSalesPersonMockMvc;

    private SalesPerson salesPerson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalesPersonResource salesPersonResource = new SalesPersonResource(salesPersonService);
        this.restSalesPersonMockMvc = MockMvcBuilders.standaloneSetup(salesPersonResource)
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
    public static SalesPerson createEntity(EntityManager em) {
        SalesPerson salesPerson = new SalesPerson()
            .salesPersonId(DEFAULT_SALES_PERSON_ID)
            .salesPersonCode(DEFAULT_SALES_PERSON_CODE)
            .salesPersonName(DEFAULT_SALES_PERSON_NAME)
            .salesPersonMail(DEFAULT_SALES_PERSON_MAIL)
            .salesPersonInactive(DEFAULT_SALES_PERSON_INACTIVE);
        return salesPerson;
    }

    @Before
    public void initTest() {
        salesPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesPerson() throws Exception {
        int databaseSizeBeforeCreate = salesPersonRepository.findAll().size();

        // Create the SalesPerson
        SalesPersonDTO salesPersonDTO = salesPersonMapper.toDto(salesPerson);
        restSalesPersonMockMvc.perform(post("/api/sales-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeCreate + 1);
        SalesPerson testSalesPerson = salesPersonList.get(salesPersonList.size() - 1);
        assertThat(testSalesPerson.getSalesPersonId()).isEqualTo(DEFAULT_SALES_PERSON_ID);
        assertThat(testSalesPerson.getSalesPersonCode()).isEqualTo(DEFAULT_SALES_PERSON_CODE);
        assertThat(testSalesPerson.getSalesPersonName()).isEqualTo(DEFAULT_SALES_PERSON_NAME);
        assertThat(testSalesPerson.getSalesPersonMail()).isEqualTo(DEFAULT_SALES_PERSON_MAIL);
        assertThat(testSalesPerson.isSalesPersonInactive()).isEqualTo(DEFAULT_SALES_PERSON_INACTIVE);
    }

    @Test
    @Transactional
    public void createSalesPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesPersonRepository.findAll().size();

        // Create the SalesPerson with an existing ID
        salesPerson.setId(1L);
        SalesPersonDTO salesPersonDTO = salesPersonMapper.toDto(salesPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesPersonMockMvc.perform(post("/api/sales-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSalesPersonIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesPersonRepository.findAll().size();
        // set the field null
        salesPerson.setSalesPersonId(null);

        // Create the SalesPerson, which fails.
        SalesPersonDTO salesPersonDTO = salesPersonMapper.toDto(salesPerson);

        restSalesPersonMockMvc.perform(post("/api/sales-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesPersonDTO)))
            .andExpect(status().isBadRequest());

        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalesPersonCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesPersonRepository.findAll().size();
        // set the field null
        salesPerson.setSalesPersonCode(null);

        // Create the SalesPerson, which fails.
        SalesPersonDTO salesPersonDTO = salesPersonMapper.toDto(salesPerson);

        restSalesPersonMockMvc.perform(post("/api/sales-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesPersonDTO)))
            .andExpect(status().isBadRequest());

        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalesPersonNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesPersonRepository.findAll().size();
        // set the field null
        salesPerson.setSalesPersonName(null);

        // Create the SalesPerson, which fails.
        SalesPersonDTO salesPersonDTO = salesPersonMapper.toDto(salesPerson);

        restSalesPersonMockMvc.perform(post("/api/sales-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesPersonDTO)))
            .andExpect(status().isBadRequest());

        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalesPersonMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesPersonRepository.findAll().size();
        // set the field null
        salesPerson.setSalesPersonMail(null);

        // Create the SalesPerson, which fails.
        SalesPersonDTO salesPersonDTO = salesPersonMapper.toDto(salesPerson);

        restSalesPersonMockMvc.perform(post("/api/sales-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesPersonDTO)))
            .andExpect(status().isBadRequest());

        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalesPersonInactiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesPersonRepository.findAll().size();
        // set the field null
        salesPerson.setSalesPersonInactive(null);

        // Create the SalesPerson, which fails.
        SalesPersonDTO salesPersonDTO = salesPersonMapper.toDto(salesPerson);

        restSalesPersonMockMvc.perform(post("/api/sales-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesPersonDTO)))
            .andExpect(status().isBadRequest());

        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalesPeople() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        // Get all the salesPersonList
        restSalesPersonMockMvc.perform(get("/api/sales-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesPersonId").value(hasItem(DEFAULT_SALES_PERSON_ID)))
            .andExpect(jsonPath("$.[*].salesPersonCode").value(hasItem(DEFAULT_SALES_PERSON_CODE.toString())))
            .andExpect(jsonPath("$.[*].salesPersonName").value(hasItem(DEFAULT_SALES_PERSON_NAME.toString())))
            .andExpect(jsonPath("$.[*].salesPersonMail").value(hasItem(DEFAULT_SALES_PERSON_MAIL.toString())))
            .andExpect(jsonPath("$.[*].salesPersonInactive").value(hasItem(DEFAULT_SALES_PERSON_INACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSalesPerson() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        // Get the salesPerson
        restSalesPersonMockMvc.perform(get("/api/sales-people/{id}", salesPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesPerson.getId().intValue()))
            .andExpect(jsonPath("$.salesPersonId").value(DEFAULT_SALES_PERSON_ID))
            .andExpect(jsonPath("$.salesPersonCode").value(DEFAULT_SALES_PERSON_CODE.toString()))
            .andExpect(jsonPath("$.salesPersonName").value(DEFAULT_SALES_PERSON_NAME.toString()))
            .andExpect(jsonPath("$.salesPersonMail").value(DEFAULT_SALES_PERSON_MAIL.toString()))
            .andExpect(jsonPath("$.salesPersonInactive").value(DEFAULT_SALES_PERSON_INACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalesPerson() throws Exception {
        // Get the salesPerson
        restSalesPersonMockMvc.perform(get("/api/sales-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesPerson() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();

        // Update the salesPerson
        SalesPerson updatedSalesPerson = salesPersonRepository.findById(salesPerson.getId()).get();
        // Disconnect from session so that the updates on updatedSalesPerson are not directly saved in db
        em.detach(updatedSalesPerson);
        updatedSalesPerson
            .salesPersonId(UPDATED_SALES_PERSON_ID)
            .salesPersonCode(UPDATED_SALES_PERSON_CODE)
            .salesPersonName(UPDATED_SALES_PERSON_NAME)
            .salesPersonMail(UPDATED_SALES_PERSON_MAIL)
            .salesPersonInactive(UPDATED_SALES_PERSON_INACTIVE);
        SalesPersonDTO salesPersonDTO = salesPersonMapper.toDto(updatedSalesPerson);

        restSalesPersonMockMvc.perform(put("/api/sales-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesPersonDTO)))
            .andExpect(status().isOk());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
        SalesPerson testSalesPerson = salesPersonList.get(salesPersonList.size() - 1);
        assertThat(testSalesPerson.getSalesPersonId()).isEqualTo(UPDATED_SALES_PERSON_ID);
        assertThat(testSalesPerson.getSalesPersonCode()).isEqualTo(UPDATED_SALES_PERSON_CODE);
        assertThat(testSalesPerson.getSalesPersonName()).isEqualTo(UPDATED_SALES_PERSON_NAME);
        assertThat(testSalesPerson.getSalesPersonMail()).isEqualTo(UPDATED_SALES_PERSON_MAIL);
        assertThat(testSalesPerson.isSalesPersonInactive()).isEqualTo(UPDATED_SALES_PERSON_INACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesPerson() throws Exception {
        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();

        // Create the SalesPerson
        SalesPersonDTO salesPersonDTO = salesPersonMapper.toDto(salesPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesPersonMockMvc.perform(put("/api/sales-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalesPerson() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        int databaseSizeBeforeDelete = salesPersonRepository.findAll().size();

        // Delete the salesPerson
        restSalesPersonMockMvc.perform(delete("/api/sales-people/{id}", salesPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesPerson.class);
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setId(1L);
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setId(salesPerson1.getId());
        assertThat(salesPerson1).isEqualTo(salesPerson2);
        salesPerson2.setId(2L);
        assertThat(salesPerson1).isNotEqualTo(salesPerson2);
        salesPerson1.setId(null);
        assertThat(salesPerson1).isNotEqualTo(salesPerson2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesPersonDTO.class);
        SalesPersonDTO salesPersonDTO1 = new SalesPersonDTO();
        salesPersonDTO1.setId(1L);
        SalesPersonDTO salesPersonDTO2 = new SalesPersonDTO();
        assertThat(salesPersonDTO1).isNotEqualTo(salesPersonDTO2);
        salesPersonDTO2.setId(salesPersonDTO1.getId());
        assertThat(salesPersonDTO1).isEqualTo(salesPersonDTO2);
        salesPersonDTO2.setId(2L);
        assertThat(salesPersonDTO1).isNotEqualTo(salesPersonDTO2);
        salesPersonDTO1.setId(null);
        assertThat(salesPersonDTO1).isNotEqualTo(salesPersonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(salesPersonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(salesPersonMapper.fromId(null)).isNull();
    }
}
