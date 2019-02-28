package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.AppUser;
import nz.co.it4biz.repository.AppUserRepository;
import nz.co.it4biz.service.AppUserService;
import nz.co.it4biz.service.dto.AppUserDTO;
import nz.co.it4biz.service.mapper.AppUserMapper;
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
 * Test class for the AppUserResource REST controller.
 *
 * @see AppUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class AppUserResourceIntTest {

    private static final Integer DEFAULT_APP_USER_ID = 1;
    private static final Integer UPDATED_APP_USER_ID = 2;

    private static final String DEFAULT_APP_USER_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_APP_USER_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_APP_USER_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_APP_USER_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_APP_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APP_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_APP_USER_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_APP_USER_MAIL = "BBBBBBBBBB";

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private AppUserService appUserService;

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

    private MockMvc restAppUserMockMvc;

    private AppUser appUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppUserResource appUserResource = new AppUserResource(appUserService);
        this.restAppUserMockMvc = MockMvcBuilders.standaloneSetup(appUserResource)
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
    public static AppUser createEntity(EntityManager em) {
        AppUser appUser = new AppUser()
            .appUserId(DEFAULT_APP_USER_ID)
            .appUserUsername(DEFAULT_APP_USER_USERNAME)
            .appUserPassword(DEFAULT_APP_USER_PASSWORD)
            .appUserName(DEFAULT_APP_USER_NAME)
            .appUserMail(DEFAULT_APP_USER_MAIL);
        return appUser;
    }

    @Before
    public void initTest() {
        appUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppUser() throws Exception {
        int databaseSizeBeforeCreate = appUserRepository.findAll().size();

        // Create the AppUser
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);
        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AppUser in the database
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeCreate + 1);
        AppUser testAppUser = appUserList.get(appUserList.size() - 1);
        assertThat(testAppUser.getAppUserId()).isEqualTo(DEFAULT_APP_USER_ID);
        assertThat(testAppUser.getAppUserUsername()).isEqualTo(DEFAULT_APP_USER_USERNAME);
        assertThat(testAppUser.getAppUserPassword()).isEqualTo(DEFAULT_APP_USER_PASSWORD);
        assertThat(testAppUser.getAppUserName()).isEqualTo(DEFAULT_APP_USER_NAME);
        assertThat(testAppUser.getAppUserMail()).isEqualTo(DEFAULT_APP_USER_MAIL);
    }

    @Test
    @Transactional
    public void createAppUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appUserRepository.findAll().size();

        // Create the AppUser with an existing ID
        appUser.setId(1L);
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppUser in the database
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAppUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appUserRepository.findAll().size();
        // set the field null
        appUser.setAppUserId(null);

        // Create the AppUser, which fails.
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isBadRequest());

        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppUserUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appUserRepository.findAll().size();
        // set the field null
        appUser.setAppUserUsername(null);

        // Create the AppUser, which fails.
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isBadRequest());

        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppUserPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = appUserRepository.findAll().size();
        // set the field null
        appUser.setAppUserPassword(null);

        // Create the AppUser, which fails.
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isBadRequest());

        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appUserRepository.findAll().size();
        // set the field null
        appUser.setAppUserName(null);

        // Create the AppUser, which fails.
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isBadRequest());

        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppUserMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = appUserRepository.findAll().size();
        // set the field null
        appUser.setAppUserMail(null);

        // Create the AppUser, which fails.
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isBadRequest());

        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppUsers() throws Exception {
        // Initialize the database
        appUserRepository.saveAndFlush(appUser);

        // Get all the appUserList
        restAppUserMockMvc.perform(get("/api/app-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].appUserId").value(hasItem(DEFAULT_APP_USER_ID)))
            .andExpect(jsonPath("$.[*].appUserUsername").value(hasItem(DEFAULT_APP_USER_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].appUserPassword").value(hasItem(DEFAULT_APP_USER_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].appUserName").value(hasItem(DEFAULT_APP_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].appUserMail").value(hasItem(DEFAULT_APP_USER_MAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getAppUser() throws Exception {
        // Initialize the database
        appUserRepository.saveAndFlush(appUser);

        // Get the appUser
        restAppUserMockMvc.perform(get("/api/app-users/{id}", appUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appUser.getId().intValue()))
            .andExpect(jsonPath("$.appUserId").value(DEFAULT_APP_USER_ID))
            .andExpect(jsonPath("$.appUserUsername").value(DEFAULT_APP_USER_USERNAME.toString()))
            .andExpect(jsonPath("$.appUserPassword").value(DEFAULT_APP_USER_PASSWORD.toString()))
            .andExpect(jsonPath("$.appUserName").value(DEFAULT_APP_USER_NAME.toString()))
            .andExpect(jsonPath("$.appUserMail").value(DEFAULT_APP_USER_MAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppUser() throws Exception {
        // Get the appUser
        restAppUserMockMvc.perform(get("/api/app-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppUser() throws Exception {
        // Initialize the database
        appUserRepository.saveAndFlush(appUser);

        int databaseSizeBeforeUpdate = appUserRepository.findAll().size();

        // Update the appUser
        AppUser updatedAppUser = appUserRepository.findById(appUser.getId()).get();
        // Disconnect from session so that the updates on updatedAppUser are not directly saved in db
        em.detach(updatedAppUser);
        updatedAppUser
            .appUserId(UPDATED_APP_USER_ID)
            .appUserUsername(UPDATED_APP_USER_USERNAME)
            .appUserPassword(UPDATED_APP_USER_PASSWORD)
            .appUserName(UPDATED_APP_USER_NAME)
            .appUserMail(UPDATED_APP_USER_MAIL);
        AppUserDTO appUserDTO = appUserMapper.toDto(updatedAppUser);

        restAppUserMockMvc.perform(put("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isOk());

        // Validate the AppUser in the database
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeUpdate);
        AppUser testAppUser = appUserList.get(appUserList.size() - 1);
        assertThat(testAppUser.getAppUserId()).isEqualTo(UPDATED_APP_USER_ID);
        assertThat(testAppUser.getAppUserUsername()).isEqualTo(UPDATED_APP_USER_USERNAME);
        assertThat(testAppUser.getAppUserPassword()).isEqualTo(UPDATED_APP_USER_PASSWORD);
        assertThat(testAppUser.getAppUserName()).isEqualTo(UPDATED_APP_USER_NAME);
        assertThat(testAppUser.getAppUserMail()).isEqualTo(UPDATED_APP_USER_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingAppUser() throws Exception {
        int databaseSizeBeforeUpdate = appUserRepository.findAll().size();

        // Create the AppUser
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppUserMockMvc.perform(put("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppUser in the database
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppUser() throws Exception {
        // Initialize the database
        appUserRepository.saveAndFlush(appUser);

        int databaseSizeBeforeDelete = appUserRepository.findAll().size();

        // Delete the appUser
        restAppUserMockMvc.perform(delete("/api/app-users/{id}", appUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = new AppUser();
        appUser1.setId(1L);
        AppUser appUser2 = new AppUser();
        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);
        appUser2.setId(2L);
        assertThat(appUser1).isNotEqualTo(appUser2);
        appUser1.setId(null);
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUserDTO.class);
        AppUserDTO appUserDTO1 = new AppUserDTO();
        appUserDTO1.setId(1L);
        AppUserDTO appUserDTO2 = new AppUserDTO();
        assertThat(appUserDTO1).isNotEqualTo(appUserDTO2);
        appUserDTO2.setId(appUserDTO1.getId());
        assertThat(appUserDTO1).isEqualTo(appUserDTO2);
        appUserDTO2.setId(2L);
        assertThat(appUserDTO1).isNotEqualTo(appUserDTO2);
        appUserDTO1.setId(null);
        assertThat(appUserDTO1).isNotEqualTo(appUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appUserMapper.fromId(null)).isNull();
    }
}
