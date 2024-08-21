package com.eis.site.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eis.site.IntegrationTest;
import com.eis.site.domain.LienMembreEquipe;
import com.eis.site.repository.LienMembreEquipeRepository;
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
 * Integration tests for the {@link LienMembreEquipeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LienMembreEquipeResourceIT {

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_WHATSAPP = "AAAAAAAAAA";
    private static final String UPDATED_WHATSAPP = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lien-membre-equipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LienMembreEquipeRepository lienMembreEquipeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLienMembreEquipeMockMvc;

    private LienMembreEquipe lienMembreEquipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LienMembreEquipe createEntity(EntityManager em) {
        LienMembreEquipe lienMembreEquipe = new LienMembreEquipe()
            .facebook(DEFAULT_FACEBOOK)
            .whatsapp(DEFAULT_WHATSAPP)
            .linkedin(DEFAULT_LINKEDIN)
            .twitter(DEFAULT_TWITTER);
        return lienMembreEquipe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LienMembreEquipe createUpdatedEntity(EntityManager em) {
        LienMembreEquipe lienMembreEquipe = new LienMembreEquipe()
            .facebook(UPDATED_FACEBOOK)
            .whatsapp(UPDATED_WHATSAPP)
            .linkedin(UPDATED_LINKEDIN)
            .twitter(UPDATED_TWITTER);
        return lienMembreEquipe;
    }

    @BeforeEach
    public void initTest() {
        lienMembreEquipe = createEntity(em);
    }

    @Test
    @Transactional
    void createLienMembreEquipe() throws Exception {
        int databaseSizeBeforeCreate = lienMembreEquipeRepository.findAll().size();
        // Create the LienMembreEquipe
        restLienMembreEquipeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lienMembreEquipe))
            )
            .andExpect(status().isCreated());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeCreate + 1);
        LienMembreEquipe testLienMembreEquipe = lienMembreEquipeList.get(lienMembreEquipeList.size() - 1);
        assertThat(testLienMembreEquipe.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testLienMembreEquipe.getWhatsapp()).isEqualTo(DEFAULT_WHATSAPP);
        assertThat(testLienMembreEquipe.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testLienMembreEquipe.getTwitter()).isEqualTo(DEFAULT_TWITTER);
    }

    @Test
    @Transactional
    void createLienMembreEquipeWithExistingId() throws Exception {
        // Create the LienMembreEquipe with an existing ID
        lienMembreEquipe.setId(1L);

        int databaseSizeBeforeCreate = lienMembreEquipeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLienMembreEquipeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lienMembreEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLienMembreEquipes() throws Exception {
        // Initialize the database
        lienMembreEquipeRepository.saveAndFlush(lienMembreEquipe);

        // Get all the lienMembreEquipeList
        restLienMembreEquipeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lienMembreEquipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].whatsapp").value(hasItem(DEFAULT_WHATSAPP)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)));
    }

    @Test
    @Transactional
    void getLienMembreEquipe() throws Exception {
        // Initialize the database
        lienMembreEquipeRepository.saveAndFlush(lienMembreEquipe);

        // Get the lienMembreEquipe
        restLienMembreEquipeMockMvc
            .perform(get(ENTITY_API_URL_ID, lienMembreEquipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lienMembreEquipe.getId().intValue()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.whatsapp").value(DEFAULT_WHATSAPP))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER));
    }

    @Test
    @Transactional
    void getNonExistingLienMembreEquipe() throws Exception {
        // Get the lienMembreEquipe
        restLienMembreEquipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLienMembreEquipe() throws Exception {
        // Initialize the database
        lienMembreEquipeRepository.saveAndFlush(lienMembreEquipe);

        int databaseSizeBeforeUpdate = lienMembreEquipeRepository.findAll().size();

        // Update the lienMembreEquipe
        LienMembreEquipe updatedLienMembreEquipe = lienMembreEquipeRepository.findById(lienMembreEquipe.getId()).get();
        // Disconnect from session so that the updates on updatedLienMembreEquipe are not directly saved in db
        em.detach(updatedLienMembreEquipe);
        updatedLienMembreEquipe.facebook(UPDATED_FACEBOOK).whatsapp(UPDATED_WHATSAPP).linkedin(UPDATED_LINKEDIN).twitter(UPDATED_TWITTER);

        restLienMembreEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLienMembreEquipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLienMembreEquipe))
            )
            .andExpect(status().isOk());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeUpdate);
        LienMembreEquipe testLienMembreEquipe = lienMembreEquipeList.get(lienMembreEquipeList.size() - 1);
        assertThat(testLienMembreEquipe.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testLienMembreEquipe.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testLienMembreEquipe.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testLienMembreEquipe.getTwitter()).isEqualTo(UPDATED_TWITTER);
    }

    @Test
    @Transactional
    void putNonExistingLienMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = lienMembreEquipeRepository.findAll().size();
        lienMembreEquipe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLienMembreEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lienMembreEquipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lienMembreEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLienMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = lienMembreEquipeRepository.findAll().size();
        lienMembreEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLienMembreEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lienMembreEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLienMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = lienMembreEquipeRepository.findAll().size();
        lienMembreEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLienMembreEquipeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lienMembreEquipe))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLienMembreEquipeWithPatch() throws Exception {
        // Initialize the database
        lienMembreEquipeRepository.saveAndFlush(lienMembreEquipe);

        int databaseSizeBeforeUpdate = lienMembreEquipeRepository.findAll().size();

        // Update the lienMembreEquipe using partial update
        LienMembreEquipe partialUpdatedLienMembreEquipe = new LienMembreEquipe();
        partialUpdatedLienMembreEquipe.setId(lienMembreEquipe.getId());

        partialUpdatedLienMembreEquipe.linkedin(UPDATED_LINKEDIN);

        restLienMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLienMembreEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLienMembreEquipe))
            )
            .andExpect(status().isOk());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeUpdate);
        LienMembreEquipe testLienMembreEquipe = lienMembreEquipeList.get(lienMembreEquipeList.size() - 1);
        assertThat(testLienMembreEquipe.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testLienMembreEquipe.getWhatsapp()).isEqualTo(DEFAULT_WHATSAPP);
        assertThat(testLienMembreEquipe.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testLienMembreEquipe.getTwitter()).isEqualTo(DEFAULT_TWITTER);
    }

    @Test
    @Transactional
    void fullUpdateLienMembreEquipeWithPatch() throws Exception {
        // Initialize the database
        lienMembreEquipeRepository.saveAndFlush(lienMembreEquipe);

        int databaseSizeBeforeUpdate = lienMembreEquipeRepository.findAll().size();

        // Update the lienMembreEquipe using partial update
        LienMembreEquipe partialUpdatedLienMembreEquipe = new LienMembreEquipe();
        partialUpdatedLienMembreEquipe.setId(lienMembreEquipe.getId());

        partialUpdatedLienMembreEquipe
            .facebook(UPDATED_FACEBOOK)
            .whatsapp(UPDATED_WHATSAPP)
            .linkedin(UPDATED_LINKEDIN)
            .twitter(UPDATED_TWITTER);

        restLienMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLienMembreEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLienMembreEquipe))
            )
            .andExpect(status().isOk());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeUpdate);
        LienMembreEquipe testLienMembreEquipe = lienMembreEquipeList.get(lienMembreEquipeList.size() - 1);
        assertThat(testLienMembreEquipe.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testLienMembreEquipe.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testLienMembreEquipe.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testLienMembreEquipe.getTwitter()).isEqualTo(UPDATED_TWITTER);
    }

    @Test
    @Transactional
    void patchNonExistingLienMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = lienMembreEquipeRepository.findAll().size();
        lienMembreEquipe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLienMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lienMembreEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lienMembreEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLienMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = lienMembreEquipeRepository.findAll().size();
        lienMembreEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLienMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lienMembreEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLienMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = lienMembreEquipeRepository.findAll().size();
        lienMembreEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLienMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lienMembreEquipe))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LienMembreEquipe in the database
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLienMembreEquipe() throws Exception {
        // Initialize the database
        lienMembreEquipeRepository.saveAndFlush(lienMembreEquipe);

        int databaseSizeBeforeDelete = lienMembreEquipeRepository.findAll().size();

        // Delete the lienMembreEquipe
        restLienMembreEquipeMockMvc
            .perform(delete(ENTITY_API_URL_ID, lienMembreEquipe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LienMembreEquipe> lienMembreEquipeList = lienMembreEquipeRepository.findAll();
        assertThat(lienMembreEquipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
