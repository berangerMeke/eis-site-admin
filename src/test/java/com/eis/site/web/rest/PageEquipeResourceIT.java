package com.eis.site.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eis.site.IntegrationTest;
import com.eis.site.domain.PageEquipe;
import com.eis.site.repository.PageEquipeRepository;
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

/**
 * Integration tests for the {@link PageEquipeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageEquipeResourceIT {

    private static final String DEFAULT_SEC_1_IMG = "AAAAAAAAAA";
    private static final String UPDATED_SEC_1_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_1_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_1_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_1_DESC = "AAAAAAAAAA";
    private static final String UPDATED_SEC_1_DESC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/page-equipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageEquipeRepository pageEquipeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageEquipeMockMvc;

    private PageEquipe pageEquipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageEquipe createEntity(EntityManager em) {
        PageEquipe pageEquipe = new PageEquipe().sec1Img(DEFAULT_SEC_1_IMG).sec1Titre(DEFAULT_SEC_1_TITRE).sec1Desc(DEFAULT_SEC_1_DESC);
        return pageEquipe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageEquipe createUpdatedEntity(EntityManager em) {
        PageEquipe pageEquipe = new PageEquipe().sec1Img(UPDATED_SEC_1_IMG).sec1Titre(UPDATED_SEC_1_TITRE).sec1Desc(UPDATED_SEC_1_DESC);
        return pageEquipe;
    }

    @BeforeEach
    public void initTest() {
        pageEquipe = createEntity(em);
    }

    @Test
    @Transactional
    void createPageEquipe() throws Exception {
        int databaseSizeBeforeCreate = pageEquipeRepository.findAll().size();
        // Create the PageEquipe
        restPageEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageEquipe)))
            .andExpect(status().isCreated());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeCreate + 1);
        PageEquipe testPageEquipe = pageEquipeList.get(pageEquipeList.size() - 1);
        assertThat(testPageEquipe.getSec1Img()).isEqualTo(DEFAULT_SEC_1_IMG);
        assertThat(testPageEquipe.getSec1Titre()).isEqualTo(DEFAULT_SEC_1_TITRE);
        assertThat(testPageEquipe.getSec1Desc()).isEqualTo(DEFAULT_SEC_1_DESC);
    }

    @Test
    @Transactional
    void createPageEquipeWithExistingId() throws Exception {
        // Create the PageEquipe with an existing ID
        pageEquipe.setId(1L);

        int databaseSizeBeforeCreate = pageEquipeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageEquipe)))
            .andExpect(status().isBadRequest());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageEquipes() throws Exception {
        // Initialize the database
        pageEquipeRepository.saveAndFlush(pageEquipe);

        // Get all the pageEquipeList
        restPageEquipeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageEquipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].sec1Img").value(hasItem(DEFAULT_SEC_1_IMG)))
            .andExpect(jsonPath("$.[*].sec1Titre").value(hasItem(DEFAULT_SEC_1_TITRE)))
            .andExpect(jsonPath("$.[*].sec1Desc").value(hasItem(DEFAULT_SEC_1_DESC)));
    }

    @Test
    @Transactional
    void getPageEquipe() throws Exception {
        // Initialize the database
        pageEquipeRepository.saveAndFlush(pageEquipe);

        // Get the pageEquipe
        restPageEquipeMockMvc
            .perform(get(ENTITY_API_URL_ID, pageEquipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageEquipe.getId().intValue()))
            .andExpect(jsonPath("$.sec1Img").value(DEFAULT_SEC_1_IMG))
            .andExpect(jsonPath("$.sec1Titre").value(DEFAULT_SEC_1_TITRE))
            .andExpect(jsonPath("$.sec1Desc").value(DEFAULT_SEC_1_DESC));
    }

    @Test
    @Transactional
    void getNonExistingPageEquipe() throws Exception {
        // Get the pageEquipe
        restPageEquipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPageEquipe() throws Exception {
        // Initialize the database
        pageEquipeRepository.saveAndFlush(pageEquipe);

        int databaseSizeBeforeUpdate = pageEquipeRepository.findAll().size();

        // Update the pageEquipe
        PageEquipe updatedPageEquipe = pageEquipeRepository.findById(pageEquipe.getId()).get();
        // Disconnect from session so that the updates on updatedPageEquipe are not directly saved in db
        em.detach(updatedPageEquipe);
        updatedPageEquipe.sec1Img(UPDATED_SEC_1_IMG).sec1Titre(UPDATED_SEC_1_TITRE).sec1Desc(UPDATED_SEC_1_DESC);

        restPageEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPageEquipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPageEquipe))
            )
            .andExpect(status().isOk());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeUpdate);
        PageEquipe testPageEquipe = pageEquipeList.get(pageEquipeList.size() - 1);
        assertThat(testPageEquipe.getSec1Img()).isEqualTo(UPDATED_SEC_1_IMG);
        assertThat(testPageEquipe.getSec1Titre()).isEqualTo(UPDATED_SEC_1_TITRE);
        assertThat(testPageEquipe.getSec1Desc()).isEqualTo(UPDATED_SEC_1_DESC);
    }

    @Test
    @Transactional
    void putNonExistingPageEquipe() throws Exception {
        int databaseSizeBeforeUpdate = pageEquipeRepository.findAll().size();
        pageEquipe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageEquipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageEquipe() throws Exception {
        int databaseSizeBeforeUpdate = pageEquipeRepository.findAll().size();
        pageEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageEquipe() throws Exception {
        int databaseSizeBeforeUpdate = pageEquipeRepository.findAll().size();
        pageEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageEquipeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageEquipe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageEquipeWithPatch() throws Exception {
        // Initialize the database
        pageEquipeRepository.saveAndFlush(pageEquipe);

        int databaseSizeBeforeUpdate = pageEquipeRepository.findAll().size();

        // Update the pageEquipe using partial update
        PageEquipe partialUpdatedPageEquipe = new PageEquipe();
        partialUpdatedPageEquipe.setId(pageEquipe.getId());

        partialUpdatedPageEquipe.sec1Titre(UPDATED_SEC_1_TITRE).sec1Desc(UPDATED_SEC_1_DESC);

        restPageEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageEquipe))
            )
            .andExpect(status().isOk());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeUpdate);
        PageEquipe testPageEquipe = pageEquipeList.get(pageEquipeList.size() - 1);
        assertThat(testPageEquipe.getSec1Img()).isEqualTo(DEFAULT_SEC_1_IMG);
        assertThat(testPageEquipe.getSec1Titre()).isEqualTo(UPDATED_SEC_1_TITRE);
        assertThat(testPageEquipe.getSec1Desc()).isEqualTo(UPDATED_SEC_1_DESC);
    }

    @Test
    @Transactional
    void fullUpdatePageEquipeWithPatch() throws Exception {
        // Initialize the database
        pageEquipeRepository.saveAndFlush(pageEquipe);

        int databaseSizeBeforeUpdate = pageEquipeRepository.findAll().size();

        // Update the pageEquipe using partial update
        PageEquipe partialUpdatedPageEquipe = new PageEquipe();
        partialUpdatedPageEquipe.setId(pageEquipe.getId());

        partialUpdatedPageEquipe.sec1Img(UPDATED_SEC_1_IMG).sec1Titre(UPDATED_SEC_1_TITRE).sec1Desc(UPDATED_SEC_1_DESC);

        restPageEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageEquipe))
            )
            .andExpect(status().isOk());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeUpdate);
        PageEquipe testPageEquipe = pageEquipeList.get(pageEquipeList.size() - 1);
        assertThat(testPageEquipe.getSec1Img()).isEqualTo(UPDATED_SEC_1_IMG);
        assertThat(testPageEquipe.getSec1Titre()).isEqualTo(UPDATED_SEC_1_TITRE);
        assertThat(testPageEquipe.getSec1Desc()).isEqualTo(UPDATED_SEC_1_DESC);
    }

    @Test
    @Transactional
    void patchNonExistingPageEquipe() throws Exception {
        int databaseSizeBeforeUpdate = pageEquipeRepository.findAll().size();
        pageEquipe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageEquipe() throws Exception {
        int databaseSizeBeforeUpdate = pageEquipeRepository.findAll().size();
        pageEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageEquipe() throws Exception {
        int databaseSizeBeforeUpdate = pageEquipeRepository.findAll().size();
        pageEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageEquipe))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageEquipe in the database
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageEquipe() throws Exception {
        // Initialize the database
        pageEquipeRepository.saveAndFlush(pageEquipe);

        int databaseSizeBeforeDelete = pageEquipeRepository.findAll().size();

        // Delete the pageEquipe
        restPageEquipeMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageEquipe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageEquipe> pageEquipeList = pageEquipeRepository.findAll();
        assertThat(pageEquipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
