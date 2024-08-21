package com.eis.site.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eis.site.IntegrationTest;
import com.eis.site.domain.PageFormation;
import com.eis.site.repository.PageFormationRepository;
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
 * Integration tests for the {@link PageFormationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageFormationResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SOUS_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SOUS_TITRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/page-formations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageFormationRepository pageFormationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageFormationMockMvc;

    private PageFormation pageFormation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageFormation createEntity(EntityManager em) {
        PageFormation pageFormation = new PageFormation().titre(DEFAULT_TITRE).sousTitre(DEFAULT_SOUS_TITRE);
        return pageFormation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageFormation createUpdatedEntity(EntityManager em) {
        PageFormation pageFormation = new PageFormation().titre(UPDATED_TITRE).sousTitre(UPDATED_SOUS_TITRE);
        return pageFormation;
    }

    @BeforeEach
    public void initTest() {
        pageFormation = createEntity(em);
    }

    @Test
    @Transactional
    void createPageFormation() throws Exception {
        int databaseSizeBeforeCreate = pageFormationRepository.findAll().size();
        // Create the PageFormation
        restPageFormationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageFormation)))
            .andExpect(status().isCreated());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeCreate + 1);
        PageFormation testPageFormation = pageFormationList.get(pageFormationList.size() - 1);
        assertThat(testPageFormation.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testPageFormation.getSousTitre()).isEqualTo(DEFAULT_SOUS_TITRE);
    }

    @Test
    @Transactional
    void createPageFormationWithExistingId() throws Exception {
        // Create the PageFormation with an existing ID
        pageFormation.setId(1L);

        int databaseSizeBeforeCreate = pageFormationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageFormationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageFormation)))
            .andExpect(status().isBadRequest());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageFormations() throws Exception {
        // Initialize the database
        pageFormationRepository.saveAndFlush(pageFormation);

        // Get all the pageFormationList
        restPageFormationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageFormation.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].sousTitre").value(hasItem(DEFAULT_SOUS_TITRE)));
    }

    @Test
    @Transactional
    void getPageFormation() throws Exception {
        // Initialize the database
        pageFormationRepository.saveAndFlush(pageFormation);

        // Get the pageFormation
        restPageFormationMockMvc
            .perform(get(ENTITY_API_URL_ID, pageFormation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageFormation.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.sousTitre").value(DEFAULT_SOUS_TITRE));
    }

    @Test
    @Transactional
    void getNonExistingPageFormation() throws Exception {
        // Get the pageFormation
        restPageFormationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPageFormation() throws Exception {
        // Initialize the database
        pageFormationRepository.saveAndFlush(pageFormation);

        int databaseSizeBeforeUpdate = pageFormationRepository.findAll().size();

        // Update the pageFormation
        PageFormation updatedPageFormation = pageFormationRepository.findById(pageFormation.getId()).get();
        // Disconnect from session so that the updates on updatedPageFormation are not directly saved in db
        em.detach(updatedPageFormation);
        updatedPageFormation.titre(UPDATED_TITRE).sousTitre(UPDATED_SOUS_TITRE);

        restPageFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPageFormation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPageFormation))
            )
            .andExpect(status().isOk());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeUpdate);
        PageFormation testPageFormation = pageFormationList.get(pageFormationList.size() - 1);
        assertThat(testPageFormation.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testPageFormation.getSousTitre()).isEqualTo(UPDATED_SOUS_TITRE);
    }

    @Test
    @Transactional
    void putNonExistingPageFormation() throws Exception {
        int databaseSizeBeforeUpdate = pageFormationRepository.findAll().size();
        pageFormation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageFormation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageFormation))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageFormation() throws Exception {
        int databaseSizeBeforeUpdate = pageFormationRepository.findAll().size();
        pageFormation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageFormation))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageFormation() throws Exception {
        int databaseSizeBeforeUpdate = pageFormationRepository.findAll().size();
        pageFormation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageFormationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageFormation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageFormationWithPatch() throws Exception {
        // Initialize the database
        pageFormationRepository.saveAndFlush(pageFormation);

        int databaseSizeBeforeUpdate = pageFormationRepository.findAll().size();

        // Update the pageFormation using partial update
        PageFormation partialUpdatedPageFormation = new PageFormation();
        partialUpdatedPageFormation.setId(pageFormation.getId());

        partialUpdatedPageFormation.sousTitre(UPDATED_SOUS_TITRE);

        restPageFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageFormation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageFormation))
            )
            .andExpect(status().isOk());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeUpdate);
        PageFormation testPageFormation = pageFormationList.get(pageFormationList.size() - 1);
        assertThat(testPageFormation.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testPageFormation.getSousTitre()).isEqualTo(UPDATED_SOUS_TITRE);
    }

    @Test
    @Transactional
    void fullUpdatePageFormationWithPatch() throws Exception {
        // Initialize the database
        pageFormationRepository.saveAndFlush(pageFormation);

        int databaseSizeBeforeUpdate = pageFormationRepository.findAll().size();

        // Update the pageFormation using partial update
        PageFormation partialUpdatedPageFormation = new PageFormation();
        partialUpdatedPageFormation.setId(pageFormation.getId());

        partialUpdatedPageFormation.titre(UPDATED_TITRE).sousTitre(UPDATED_SOUS_TITRE);

        restPageFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageFormation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageFormation))
            )
            .andExpect(status().isOk());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeUpdate);
        PageFormation testPageFormation = pageFormationList.get(pageFormationList.size() - 1);
        assertThat(testPageFormation.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testPageFormation.getSousTitre()).isEqualTo(UPDATED_SOUS_TITRE);
    }

    @Test
    @Transactional
    void patchNonExistingPageFormation() throws Exception {
        int databaseSizeBeforeUpdate = pageFormationRepository.findAll().size();
        pageFormation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageFormation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageFormation))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageFormation() throws Exception {
        int databaseSizeBeforeUpdate = pageFormationRepository.findAll().size();
        pageFormation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageFormation))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageFormation() throws Exception {
        int databaseSizeBeforeUpdate = pageFormationRepository.findAll().size();
        pageFormation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageFormationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageFormation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageFormation in the database
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageFormation() throws Exception {
        // Initialize the database
        pageFormationRepository.saveAndFlush(pageFormation);

        int databaseSizeBeforeDelete = pageFormationRepository.findAll().size();

        // Delete the pageFormation
        restPageFormationMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageFormation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageFormation> pageFormationList = pageFormationRepository.findAll();
        assertThat(pageFormationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
