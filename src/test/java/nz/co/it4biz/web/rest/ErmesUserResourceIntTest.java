package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.ErmesUser;
import nz.co.it4biz.repository.ErmesUserRepository;
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
 * Test class for the ErmesUserResource REST controller.
 *
 * @see ErmesUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class ErmesUserResourceIntTest {

    private static final Integer DEFAULT_ERMES_USER_ID = 1;
    private static final Integer UPDATED_ERMES_USER_ID = 2;

    private static final String DEFAULT_ERMES_USER_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_ERMES_USER_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ERMES_USER_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_ERMES_USER_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ERMES_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ERMES_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ERMES_USER_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_ERMES_USER_MAIL = "BBBBBBBBBB";

    @Autowired
    private ErmesUserRepository ermesUserRepository;

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

    private MockMvc restErmesUserMockMvc;

    private ErmesUser ermesUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ErmesUserResource ermesUserResource = new ErmesUserResource(ermesUserRepository);
        this.restErmesUserMockMvc = MockMvcBuilders.standaloneSetup(ermesUserResource)
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
    public static ErmesUser createEntity(EntityManager em) {
        ErmesUser ermesUser = new ErmesUser()
            .ermesUserId(DEFAULT_ERMES_USER_ID)
            .ermesUserUsername(DEFAULT_ERMES_USER_USERNAME)
            .ermesUserPassword(DEFAULT_ERMES_USER_PASSWORD)
            .ermesUserName(DEFAULT_ERMES_USER_NAME)
            .ermesUserMail(DEFAULT_ERMES_USER_MAIL);
        return ermesUser;
    }

    @Before
    public void initTest() {
        ermesUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createErmesUser() throws Exception {
        int databaseSizeBeforeCreate = ermesUserRepository.findAll().size();

        // Create the ErmesUser
        restErmesUserMockMvc.perform(post("/api/ermes-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ermesUser)))
            .andExpect(status().isCreated());

        // Validate the ErmesUser in the database
        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeCreate + 1);
        ErmesUser testErmesUser = ermesUserList.get(ermesUserList.size() - 1);
        assertThat(testErmesUser.getErmesUserId()).isEqualTo(DEFAULT_ERMES_USER_ID);
        assertThat(testErmesUser.getErmesUserUsername()).isEqualTo(DEFAULT_ERMES_USER_USERNAME);
        assertThat(testErmesUser.getErmesUserPassword()).isEqualTo(DEFAULT_ERMES_USER_PASSWORD);
        assertThat(testErmesUser.getErmesUserName()).isEqualTo(DEFAULT_ERMES_USER_NAME);
        assertThat(testErmesUser.getErmesUserMail()).isEqualTo(DEFAULT_ERMES_USER_MAIL);
    }

    @Test
    @Transactional
    public void createErmesUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ermesUserRepository.findAll().size();

        // Create the ErmesUser with an existing ID
        ermesUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restErmesUserMockMvc.perform(post("/api/ermes-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ermesUser)))
            .andExpect(status().isBadRequest());

        // Validate the ErmesUser in the database
        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkErmesUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ermesUserRepository.findAll().size();
        // set the field null
        ermesUser.setErmesUserId(null);

        // Create the ErmesUser, which fails.

        restErmesUserMockMvc.perform(post("/api/ermes-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ermesUser)))
            .andExpect(status().isBadRequest());

        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkErmesUserUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ermesUserRepository.findAll().size();
        // set the field null
        ermesUser.setErmesUserUsername(null);

        // Create the ErmesUser, which fails.

        restErmesUserMockMvc.perform(post("/api/ermes-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ermesUser)))
            .andExpect(status().isBadRequest());

        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkErmesUserPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = ermesUserRepository.findAll().size();
        // set the field null
        ermesUser.setErmesUserPassword(null);

        // Create the ErmesUser, which fails.

        restErmesUserMockMvc.perform(post("/api/ermes-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ermesUser)))
            .andExpect(status().isBadRequest());

        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkErmesUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ermesUserRepository.findAll().size();
        // set the field null
        ermesUser.setErmesUserName(null);

        // Create the ErmesUser, which fails.

        restErmesUserMockMvc.perform(post("/api/ermes-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ermesUser)))
            .andExpect(status().isBadRequest());

        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkErmesUserMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = ermesUserRepository.findAll().size();
        // set the field null
        ermesUser.setErmesUserMail(null);

        // Create the ErmesUser, which fails.

        restErmesUserMockMvc.perform(post("/api/ermes-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ermesUser)))
            .andExpect(status().isBadRequest());

        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllErmesUsers() throws Exception {
        // Initialize the database
        ermesUserRepository.saveAndFlush(ermesUser);

        // Get all the ermesUserList
        restErmesUserMockMvc.perform(get("/api/ermes-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ermesUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].ermesUserId").value(hasItem(DEFAULT_ERMES_USER_ID)))
            .andExpect(jsonPath("$.[*].ermesUserUsername").value(hasItem(DEFAULT_ERMES_USER_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].ermesUserPassword").value(hasItem(DEFAULT_ERMES_USER_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].ermesUserName").value(hasItem(DEFAULT_ERMES_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].ermesUserMail").value(hasItem(DEFAULT_ERMES_USER_MAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getErmesUser() throws Exception {
        // Initialize the database
        ermesUserRepository.saveAndFlush(ermesUser);

        // Get the ermesUser
        restErmesUserMockMvc.perform(get("/api/ermes-users/{id}", ermesUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ermesUser.getId().intValue()))
            .andExpect(jsonPath("$.ermesUserId").value(DEFAULT_ERMES_USER_ID))
            .andExpect(jsonPath("$.ermesUserUsername").value(DEFAULT_ERMES_USER_USERNAME.toString()))
            .andExpect(jsonPath("$.ermesUserPassword").value(DEFAULT_ERMES_USER_PASSWORD.toString()))
            .andExpect(jsonPath("$.ermesUserName").value(DEFAULT_ERMES_USER_NAME.toString()))
            .andExpect(jsonPath("$.ermesUserMail").value(DEFAULT_ERMES_USER_MAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingErmesUser() throws Exception {
        // Get the ermesUser
        restErmesUserMockMvc.perform(get("/api/ermes-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateErmesUser() throws Exception {
        // Initialize the database
        ermesUserRepository.saveAndFlush(ermesUser);

        int databaseSizeBeforeUpdate = ermesUserRepository.findAll().size();

        // Update the ermesUser
        ErmesUser updatedErmesUser = ermesUserRepository.findById(ermesUser.getId()).get();
        // Disconnect from session so that the updates on updatedErmesUser are not directly saved in db
        em.detach(updatedErmesUser);
        updatedErmesUser
            .ermesUserId(UPDATED_ERMES_USER_ID)
            .ermesUserUsername(UPDATED_ERMES_USER_USERNAME)
            .ermesUserPassword(UPDATED_ERMES_USER_PASSWORD)
            .ermesUserName(UPDATED_ERMES_USER_NAME)
            .ermesUserMail(UPDATED_ERMES_USER_MAIL);

        restErmesUserMockMvc.perform(put("/api/ermes-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedErmesUser)))
            .andExpect(status().isOk());

        // Validate the ErmesUser in the database
        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeUpdate);
        ErmesUser testErmesUser = ermesUserList.get(ermesUserList.size() - 1);
        assertThat(testErmesUser.getErmesUserId()).isEqualTo(UPDATED_ERMES_USER_ID);
        assertThat(testErmesUser.getErmesUserUsername()).isEqualTo(UPDATED_ERMES_USER_USERNAME);
        assertThat(testErmesUser.getErmesUserPassword()).isEqualTo(UPDATED_ERMES_USER_PASSWORD);
        assertThat(testErmesUser.getErmesUserName()).isEqualTo(UPDATED_ERMES_USER_NAME);
        assertThat(testErmesUser.getErmesUserMail()).isEqualTo(UPDATED_ERMES_USER_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingErmesUser() throws Exception {
        int databaseSizeBeforeUpdate = ermesUserRepository.findAll().size();

        // Create the ErmesUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErmesUserMockMvc.perform(put("/api/ermes-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ermesUser)))
            .andExpect(status().isBadRequest());

        // Validate the ErmesUser in the database
        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteErmesUser() throws Exception {
        // Initialize the database
        ermesUserRepository.saveAndFlush(ermesUser);

        int databaseSizeBeforeDelete = ermesUserRepository.findAll().size();

        // Delete the ermesUser
        restErmesUserMockMvc.perform(delete("/api/ermes-users/{id}", ermesUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ErmesUser> ermesUserList = ermesUserRepository.findAll();
        assertThat(ermesUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ErmesUser.class);
        ErmesUser ermesUser1 = new ErmesUser();
        ermesUser1.setId(1L);
        ErmesUser ermesUser2 = new ErmesUser();
        ermesUser2.setId(ermesUser1.getId());
        assertThat(ermesUser1).isEqualTo(ermesUser2);
        ermesUser2.setId(2L);
        assertThat(ermesUser1).isNotEqualTo(ermesUser2);
        ermesUser1.setId(null);
        assertThat(ermesUser1).isNotEqualTo(ermesUser2);
    }
}
