package com.eis.site.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eis.site.IntegrationTest;
import com.eis.site.domain.NavBar;
import com.eis.site.repository.NavBarRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link NavBarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NavBarResourceIT {

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MENU_1 = "AAAAAAAAAA";
    private static final String UPDATED_MENU_1 = "BBBBBBBBBB";

    private static final String DEFAULT_MENU_2 = "AAAAAAAAAA";
    private static final String UPDATED_MENU_2 = "BBBBBBBBBB";

    private static final String DEFAULT_MENU_3 = "AAAAAAAAAA";
    private static final String UPDATED_MENU_3 = "BBBBBBBBBB";

    private static final String DEFAULT_MENU_4 = "AAAAAAAAAA";
    private static final String UPDATED_MENU_4 = "BBBBBBBBBB";

    private static final String DEFAULT_MENU_5 = "AAAAAAAAAA";
    private static final String UPDATED_MENU_5 = "BBBBBBBBBB";

    private static final String DEFAULT_MENU_6 = "AAAAAAAAAA";
    private static final String UPDATED_MENU_6 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nav-bars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NavBarRepository navBarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNavBarMockMvc;

    private NavBar navBar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NavBar createEntity(EntityManager em) {
        NavBar navBar = new NavBar()
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .menu1(DEFAULT_MENU_1)
            .menu2(DEFAULT_MENU_2)
            .menu3(DEFAULT_MENU_3)
            .menu4(DEFAULT_MENU_4)
            .menu5(DEFAULT_MENU_5)
            .menu6(DEFAULT_MENU_6);
        return navBar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NavBar createUpdatedEntity(EntityManager em) {
        NavBar navBar = new NavBar()
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .menu1(UPDATED_MENU_1)
            .menu2(UPDATED_MENU_2)
            .menu3(UPDATED_MENU_3)
            .menu4(UPDATED_MENU_4)
            .menu5(UPDATED_MENU_5)
            .menu6(UPDATED_MENU_6);
        return navBar;
    }

    @BeforeEach
    public void initTest() {
        navBar = createEntity(em);
    }

    @Test
    @Transactional
    void createNavBar() throws Exception {
        int databaseSizeBeforeCreate = navBarRepository.findAll().size();
        // Create the NavBar
        restNavBarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(navBar)))
            .andExpect(status().isCreated());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeCreate + 1);
        NavBar testNavBar = navBarList.get(navBarList.size() - 1);
        assertThat(testNavBar.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testNavBar.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testNavBar.getMenu1()).isEqualTo(DEFAULT_MENU_1);
        assertThat(testNavBar.getMenu2()).isEqualTo(DEFAULT_MENU_2);
        assertThat(testNavBar.getMenu3()).isEqualTo(DEFAULT_MENU_3);
        assertThat(testNavBar.getMenu4()).isEqualTo(DEFAULT_MENU_4);
        assertThat(testNavBar.getMenu5()).isEqualTo(DEFAULT_MENU_5);
        assertThat(testNavBar.getMenu6()).isEqualTo(DEFAULT_MENU_6);
    }

    @Test
    @Transactional
    void createNavBarWithExistingId() throws Exception {
        // Create the NavBar with an existing ID
        navBar.setId(1L);

        int databaseSizeBeforeCreate = navBarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNavBarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(navBar)))
            .andExpect(status().isBadRequest());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNavBars() throws Exception {
        // Initialize the database
        navBarRepository.saveAndFlush(navBar);

        // Get all the navBarList
        restNavBarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(navBar.getId().intValue())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].menu1").value(hasItem(DEFAULT_MENU_1)))
            .andExpect(jsonPath("$.[*].menu2").value(hasItem(DEFAULT_MENU_2)))
            .andExpect(jsonPath("$.[*].menu3").value(hasItem(DEFAULT_MENU_3)))
            .andExpect(jsonPath("$.[*].menu4").value(hasItem(DEFAULT_MENU_4)))
            .andExpect(jsonPath("$.[*].menu5").value(hasItem(DEFAULT_MENU_5)))
            .andExpect(jsonPath("$.[*].menu6").value(hasItem(DEFAULT_MENU_6)));
    }

    @Test
    @Transactional
    void getNavBar() throws Exception {
        // Initialize the database
        navBarRepository.saveAndFlush(navBar);

        // Get the navBar
        restNavBarMockMvc
            .perform(get(ENTITY_API_URL_ID, navBar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(navBar.getId().intValue()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.menu1").value(DEFAULT_MENU_1))
            .andExpect(jsonPath("$.menu2").value(DEFAULT_MENU_2))
            .andExpect(jsonPath("$.menu3").value(DEFAULT_MENU_3))
            .andExpect(jsonPath("$.menu4").value(DEFAULT_MENU_4))
            .andExpect(jsonPath("$.menu5").value(DEFAULT_MENU_5))
            .andExpect(jsonPath("$.menu6").value(DEFAULT_MENU_6));
    }

    @Test
    @Transactional
    void getNonExistingNavBar() throws Exception {
        // Get the navBar
        restNavBarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNavBar() throws Exception {
        // Initialize the database
        navBarRepository.saveAndFlush(navBar);

        int databaseSizeBeforeUpdate = navBarRepository.findAll().size();

        // Update the navBar
        NavBar updatedNavBar = navBarRepository.findById(navBar.getId()).get();
        // Disconnect from session so that the updates on updatedNavBar are not directly saved in db
        em.detach(updatedNavBar);
        updatedNavBar
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .menu1(UPDATED_MENU_1)
            .menu2(UPDATED_MENU_2)
            .menu3(UPDATED_MENU_3)
            .menu4(UPDATED_MENU_4)
            .menu5(UPDATED_MENU_5)
            .menu6(UPDATED_MENU_6);

        restNavBarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNavBar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNavBar))
            )
            .andExpect(status().isOk());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeUpdate);
        NavBar testNavBar = navBarList.get(navBarList.size() - 1);
        assertThat(testNavBar.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testNavBar.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testNavBar.getMenu1()).isEqualTo(UPDATED_MENU_1);
        assertThat(testNavBar.getMenu2()).isEqualTo(UPDATED_MENU_2);
        assertThat(testNavBar.getMenu3()).isEqualTo(UPDATED_MENU_3);
        assertThat(testNavBar.getMenu4()).isEqualTo(UPDATED_MENU_4);
        assertThat(testNavBar.getMenu5()).isEqualTo(UPDATED_MENU_5);
        assertThat(testNavBar.getMenu6()).isEqualTo(UPDATED_MENU_6);
    }

    @Test
    @Transactional
    void putNonExistingNavBar() throws Exception {
        int databaseSizeBeforeUpdate = navBarRepository.findAll().size();
        navBar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNavBarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, navBar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(navBar))
            )
            .andExpect(status().isBadRequest());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNavBar() throws Exception {
        int databaseSizeBeforeUpdate = navBarRepository.findAll().size();
        navBar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNavBarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(navBar))
            )
            .andExpect(status().isBadRequest());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNavBar() throws Exception {
        int databaseSizeBeforeUpdate = navBarRepository.findAll().size();
        navBar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNavBarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(navBar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNavBarWithPatch() throws Exception {
        // Initialize the database
        navBarRepository.saveAndFlush(navBar);

        int databaseSizeBeforeUpdate = navBarRepository.findAll().size();

        // Update the navBar using partial update
        NavBar partialUpdatedNavBar = new NavBar();
        partialUpdatedNavBar.setId(navBar.getId());

        partialUpdatedNavBar.logo(UPDATED_LOGO).logoContentType(UPDATED_LOGO_CONTENT_TYPE).menu1(UPDATED_MENU_1).menu6(UPDATED_MENU_6);

        restNavBarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNavBar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNavBar))
            )
            .andExpect(status().isOk());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeUpdate);
        NavBar testNavBar = navBarList.get(navBarList.size() - 1);
        assertThat(testNavBar.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testNavBar.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testNavBar.getMenu1()).isEqualTo(UPDATED_MENU_1);
        assertThat(testNavBar.getMenu2()).isEqualTo(DEFAULT_MENU_2);
        assertThat(testNavBar.getMenu3()).isEqualTo(DEFAULT_MENU_3);
        assertThat(testNavBar.getMenu4()).isEqualTo(DEFAULT_MENU_4);
        assertThat(testNavBar.getMenu5()).isEqualTo(DEFAULT_MENU_5);
        assertThat(testNavBar.getMenu6()).isEqualTo(UPDATED_MENU_6);
    }

    @Test
    @Transactional
    void fullUpdateNavBarWithPatch() throws Exception {
        // Initialize the database
        navBarRepository.saveAndFlush(navBar);

        int databaseSizeBeforeUpdate = navBarRepository.findAll().size();

        // Update the navBar using partial update
        NavBar partialUpdatedNavBar = new NavBar();
        partialUpdatedNavBar.setId(navBar.getId());

        partialUpdatedNavBar
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .menu1(UPDATED_MENU_1)
            .menu2(UPDATED_MENU_2)
            .menu3(UPDATED_MENU_3)
            .menu4(UPDATED_MENU_4)
            .menu5(UPDATED_MENU_5)
            .menu6(UPDATED_MENU_6);

        restNavBarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNavBar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNavBar))
            )
            .andExpect(status().isOk());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeUpdate);
        NavBar testNavBar = navBarList.get(navBarList.size() - 1);
        assertThat(testNavBar.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testNavBar.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testNavBar.getMenu1()).isEqualTo(UPDATED_MENU_1);
        assertThat(testNavBar.getMenu2()).isEqualTo(UPDATED_MENU_2);
        assertThat(testNavBar.getMenu3()).isEqualTo(UPDATED_MENU_3);
        assertThat(testNavBar.getMenu4()).isEqualTo(UPDATED_MENU_4);
        assertThat(testNavBar.getMenu5()).isEqualTo(UPDATED_MENU_5);
        assertThat(testNavBar.getMenu6()).isEqualTo(UPDATED_MENU_6);
    }

    @Test
    @Transactional
    void patchNonExistingNavBar() throws Exception {
        int databaseSizeBeforeUpdate = navBarRepository.findAll().size();
        navBar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNavBarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, navBar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(navBar))
            )
            .andExpect(status().isBadRequest());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNavBar() throws Exception {
        int databaseSizeBeforeUpdate = navBarRepository.findAll().size();
        navBar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNavBarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(navBar))
            )
            .andExpect(status().isBadRequest());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNavBar() throws Exception {
        int databaseSizeBeforeUpdate = navBarRepository.findAll().size();
        navBar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNavBarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(navBar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NavBar in the database
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNavBar() throws Exception {
        // Initialize the database
        navBarRepository.saveAndFlush(navBar);

        int databaseSizeBeforeDelete = navBarRepository.findAll().size();

        // Delete the navBar
        restNavBarMockMvc
            .perform(delete(ENTITY_API_URL_ID, navBar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NavBar> navBarList = navBarRepository.findAll();
        assertThat(navBarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
