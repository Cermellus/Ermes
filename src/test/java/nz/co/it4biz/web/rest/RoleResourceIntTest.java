package nz.co.it4biz.web.rest;

import nz.co.it4biz.ErmesApp;

import nz.co.it4biz.domain.Role;
import nz.co.it4biz.repository.RoleRepository;
import nz.co.it4biz.service.RoleService;
import nz.co.it4biz.service.dto.RoleDTO;
import nz.co.it4biz.service.mapper.RoleMapper;
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
 * Test class for the RoleResource REST controller.
 *
 * @see RoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErmesApp.class)
public class RoleResourceIntTest {

    private static final Integer DEFAULT_ROLE_ID = 1;
    private static final Integer UPDATED_ROLE_ID = 2;

    private static final String DEFAULT_ROLE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ROLE_APPROVE = false;
    private static final Boolean UPDATED_ROLE_APPROVE = true;

    private static final Boolean DEFAULT_ROLE_INSERT = false;
    private static final Boolean UPDATED_ROLE_INSERT = true;

    private static final Boolean DEFAULT_ROLE_EDIT = false;
    private static final Boolean UPDATED_ROLE_EDIT = true;

    private static final Boolean DEFAULT_ROLE_RETURN = false;
    private static final Boolean UPDATED_ROLE_RETURN = true;

    private static final Boolean DEFAULT_ROLE_PROCESS = false;
    private static final Boolean UPDATED_ROLE_PROCESS = true;

    private static final Boolean DEFAULT_ROLE_SEE_ALL_RQUESTS = false;
    private static final Boolean UPDATED_ROLE_SEE_ALL_RQUESTS = true;

    private static final Boolean DEFAULT_ROLE_COURIER_CLAIM = false;
    private static final Boolean UPDATED_ROLE_COURIER_CLAIM = true;

    private static final String DEFAULT_ROLE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_COMMENT = "BBBBBBBBBB";

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

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

    private MockMvc restRoleMockMvc;

    private Role role;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleResource roleResource = new RoleResource(roleService);
        this.restRoleMockMvc = MockMvcBuilders.standaloneSetup(roleResource)
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
    public static Role createEntity(EntityManager em) {
        Role role = new Role()
            .roleId(DEFAULT_ROLE_ID)
            .roleDescription(DEFAULT_ROLE_DESCRIPTION)
            .roleApprove(DEFAULT_ROLE_APPROVE)
            .roleInsert(DEFAULT_ROLE_INSERT)
            .roleEdit(DEFAULT_ROLE_EDIT)
            .roleReturn(DEFAULT_ROLE_RETURN)
            .roleProcess(DEFAULT_ROLE_PROCESS)
            .roleSeeAllRquests(DEFAULT_ROLE_SEE_ALL_RQUESTS)
            .roleCourierClaim(DEFAULT_ROLE_COURIER_CLAIM)
            .roleComment(DEFAULT_ROLE_COMMENT);
        return role;
    }

    @Before
    public void initTest() {
        role = createEntity(em);
    }

    @Test
    @Transactional
    public void createRole() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);
        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isCreated());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate + 1);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testRole.getRoleDescription()).isEqualTo(DEFAULT_ROLE_DESCRIPTION);
        assertThat(testRole.isRoleApprove()).isEqualTo(DEFAULT_ROLE_APPROVE);
        assertThat(testRole.isRoleInsert()).isEqualTo(DEFAULT_ROLE_INSERT);
        assertThat(testRole.isRoleEdit()).isEqualTo(DEFAULT_ROLE_EDIT);
        assertThat(testRole.isRoleReturn()).isEqualTo(DEFAULT_ROLE_RETURN);
        assertThat(testRole.isRoleProcess()).isEqualTo(DEFAULT_ROLE_PROCESS);
        assertThat(testRole.isRoleSeeAllRquests()).isEqualTo(DEFAULT_ROLE_SEE_ALL_RQUESTS);
        assertThat(testRole.isRoleCourierClaim()).isEqualTo(DEFAULT_ROLE_COURIER_CLAIM);
        assertThat(testRole.getRoleComment()).isEqualTo(DEFAULT_ROLE_COMMENT);
    }

    @Test
    @Transactional
    public void createRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();

        // Create the Role with an existing ID
        role.setId(1L);
        RoleDTO roleDTO = roleMapper.toDto(role);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRoleIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRoleId(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRoleDescription(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleApproveIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRoleApprove(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleInsertIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRoleInsert(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleEditIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRoleEdit(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleReturnIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRoleReturn(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleProcessIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRoleProcess(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleSeeAllRquestsIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRoleSeeAllRquests(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleCourierClaimIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRoleCourierClaim(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoles() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID)))
            .andExpect(jsonPath("$.[*].roleDescription").value(hasItem(DEFAULT_ROLE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].roleApprove").value(hasItem(DEFAULT_ROLE_APPROVE.booleanValue())))
            .andExpect(jsonPath("$.[*].roleInsert").value(hasItem(DEFAULT_ROLE_INSERT.booleanValue())))
            .andExpect(jsonPath("$.[*].roleEdit").value(hasItem(DEFAULT_ROLE_EDIT.booleanValue())))
            .andExpect(jsonPath("$.[*].roleReturn").value(hasItem(DEFAULT_ROLE_RETURN.booleanValue())))
            .andExpect(jsonPath("$.[*].roleProcess").value(hasItem(DEFAULT_ROLE_PROCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].roleSeeAllRquests").value(hasItem(DEFAULT_ROLE_SEE_ALL_RQUESTS.booleanValue())))
            .andExpect(jsonPath("$.[*].roleCourierClaim").value(hasItem(DEFAULT_ROLE_COURIER_CLAIM.booleanValue())))
            .andExpect(jsonPath("$.[*].roleComment").value(hasItem(DEFAULT_ROLE_COMMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", role.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(role.getId().intValue()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID))
            .andExpect(jsonPath("$.roleDescription").value(DEFAULT_ROLE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.roleApprove").value(DEFAULT_ROLE_APPROVE.booleanValue()))
            .andExpect(jsonPath("$.roleInsert").value(DEFAULT_ROLE_INSERT.booleanValue()))
            .andExpect(jsonPath("$.roleEdit").value(DEFAULT_ROLE_EDIT.booleanValue()))
            .andExpect(jsonPath("$.roleReturn").value(DEFAULT_ROLE_RETURN.booleanValue()))
            .andExpect(jsonPath("$.roleProcess").value(DEFAULT_ROLE_PROCESS.booleanValue()))
            .andExpect(jsonPath("$.roleSeeAllRquests").value(DEFAULT_ROLE_SEE_ALL_RQUESTS.booleanValue()))
            .andExpect(jsonPath("$.roleCourierClaim").value(DEFAULT_ROLE_COURIER_CLAIM.booleanValue()))
            .andExpect(jsonPath("$.roleComment").value(DEFAULT_ROLE_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRole() throws Exception {
        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Update the role
        Role updatedRole = roleRepository.findById(role.getId()).get();
        // Disconnect from session so that the updates on updatedRole are not directly saved in db
        em.detach(updatedRole);
        updatedRole
            .roleId(UPDATED_ROLE_ID)
            .roleDescription(UPDATED_ROLE_DESCRIPTION)
            .roleApprove(UPDATED_ROLE_APPROVE)
            .roleInsert(UPDATED_ROLE_INSERT)
            .roleEdit(UPDATED_ROLE_EDIT)
            .roleReturn(UPDATED_ROLE_RETURN)
            .roleProcess(UPDATED_ROLE_PROCESS)
            .roleSeeAllRquests(UPDATED_ROLE_SEE_ALL_RQUESTS)
            .roleCourierClaim(UPDATED_ROLE_COURIER_CLAIM)
            .roleComment(UPDATED_ROLE_COMMENT);
        RoleDTO roleDTO = roleMapper.toDto(updatedRole);

        restRoleMockMvc.perform(put("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isOk());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testRole.getRoleDescription()).isEqualTo(UPDATED_ROLE_DESCRIPTION);
        assertThat(testRole.isRoleApprove()).isEqualTo(UPDATED_ROLE_APPROVE);
        assertThat(testRole.isRoleInsert()).isEqualTo(UPDATED_ROLE_INSERT);
        assertThat(testRole.isRoleEdit()).isEqualTo(UPDATED_ROLE_EDIT);
        assertThat(testRole.isRoleReturn()).isEqualTo(UPDATED_ROLE_RETURN);
        assertThat(testRole.isRoleProcess()).isEqualTo(UPDATED_ROLE_PROCESS);
        assertThat(testRole.isRoleSeeAllRquests()).isEqualTo(UPDATED_ROLE_SEE_ALL_RQUESTS);
        assertThat(testRole.isRoleCourierClaim()).isEqualTo(UPDATED_ROLE_COURIER_CLAIM);
        assertThat(testRole.getRoleComment()).isEqualTo(UPDATED_ROLE_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleMockMvc.perform(put("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeDelete = roleRepository.findAll().size();

        // Delete the role
        restRoleMockMvc.perform(delete("/api/roles/{id}", role.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Role.class);
        Role role1 = new Role();
        role1.setId(1L);
        Role role2 = new Role();
        role2.setId(role1.getId());
        assertThat(role1).isEqualTo(role2);
        role2.setId(2L);
        assertThat(role1).isNotEqualTo(role2);
        role1.setId(null);
        assertThat(role1).isNotEqualTo(role2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleDTO.class);
        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setId(1L);
        RoleDTO roleDTO2 = new RoleDTO();
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
        roleDTO2.setId(roleDTO1.getId());
        assertThat(roleDTO1).isEqualTo(roleDTO2);
        roleDTO2.setId(2L);
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
        roleDTO1.setId(null);
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roleMapper.fromId(null)).isNull();
    }
}
