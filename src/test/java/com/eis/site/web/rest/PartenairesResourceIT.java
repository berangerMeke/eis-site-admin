package com.eis.site.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eis.site.IntegrationTest;
import com.eis.site.domain.Partenaires;
import com.eis.site.repository.PartenairesRepository;
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
 * Integration tests for the {@link PartenairesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartenairesResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN = "AAAAAAAAAA";
    private static final String UPDATED_LIEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partenaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartenairesRepository partenairesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartenairesMockMvc;

    private Partenaires partenaires;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partenaires createEntity(EntityManager em) {
        Partenaires partenaires = new Partenaires()
            .nom(DEFAULT_NOM)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .lien(DEFAULT_LIEN);
        return partenaires;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partenaires createUpdatedEntity(EntityManager em) {
        Partenaires partenaires = new Partenaires()
            .nom(UPDATED_NOM)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .lien(UPDATED_LIEN);
        return partenaires;
    }

    @BeforeEach
    public void initTest() {
        partenaires = createEntity(em);
    }

    @Test
    @Transactional
    void createPartenaires() throws Exception {
        int databaseSizeBeforeCreate = partenairesRepository.findAll().size();
        // Create the Partenaires
        restPartenairesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partenaires)))
            .andExpect(status().isCreated());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeCreate + 1);
        Partenaires testPartenaires = partenairesList.get(partenairesList.size() - 1);
        assertThat(testPartenaires.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPartenaires.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPartenaires.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPartenaires.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartenaires.getLien()).isEqualTo(DEFAULT_LIEN);
    }

    @Test
    @Transactional
    void createPartenairesWithExistingId() throws Exception {
        // Create the Partenaires with an existing ID
        partenaires.setId(1L);

        int databaseSizeBeforeCreate = partenairesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartenairesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partenaires)))
            .andExpect(status().isBadRequest());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartenaires() throws Exception {
        // Initialize the database
        partenairesRepository.saveAndFlush(partenaires);

        // Get all the partenairesList
        restPartenairesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partenaires.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN)));
    }

    @Test
    @Transactional
    void getPartenaires() throws Exception {
        // Initialize the database
        partenairesRepository.saveAndFlush(partenaires);

        // Get the partenaires
        restPartenairesMockMvc
            .perform(get(ENTITY_API_URL_ID, partenaires.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partenaires.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lien").value(DEFAULT_LIEN));
    }

    @Test
    @Transactional
    void getNonExistingPartenaires() throws Exception {
        // Get the partenaires
        restPartenairesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartenaires() throws Exception {
        // Initialize the database
        partenairesRepository.saveAndFlush(partenaires);

        int databaseSizeBeforeUpdate = partenairesRepository.findAll().size();

        // Update the partenaires
        Partenaires updatedPartenaires = partenairesRepository.findById(partenaires.getId()).get();
        // Disconnect from session so that the updates on updatedPartenaires are not directly saved in db
        em.detach(updatedPartenaires);
        updatedPartenaires
            .nom(UPDATED_NOM)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .lien(UPDATED_LIEN);

        restPartenairesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartenaires.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartenaires))
            )
            .andExpect(status().isOk());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeUpdate);
        Partenaires testPartenaires = partenairesList.get(partenairesList.size() - 1);
        assertThat(testPartenaires.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPartenaires.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPartenaires.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPartenaires.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartenaires.getLien()).isEqualTo(UPDATED_LIEN);
    }

    @Test
    @Transactional
    void putNonExistingPartenaires() throws Exception {
        int databaseSizeBeforeUpdate = partenairesRepository.findAll().size();
        partenaires.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartenairesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partenaires.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partenaires))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartenaires() throws Exception {
        int databaseSizeBeforeUpdate = partenairesRepository.findAll().size();
        partenaires.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartenairesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partenaires))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartenaires() throws Exception {
        int databaseSizeBeforeUpdate = partenairesRepository.findAll().size();
        partenaires.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartenairesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partenaires)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartenairesWithPatch() throws Exception {
        // Initialize the database
        partenairesRepository.saveAndFlush(partenaires);

        int databaseSizeBeforeUpdate = partenairesRepository.findAll().size();

        // Update the partenaires using partial update
        Partenaires partialUpdatedPartenaires = new Partenaires();
        partialUpdatedPartenaires.setId(partenaires.getId());

        partialUpdatedPartenaires.photo(UPDATED_PHOTO).photoContentType(UPDATED_PHOTO_CONTENT_TYPE).description(UPDATED_DESCRIPTION);

        restPartenairesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartenaires.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartenaires))
            )
            .andExpect(status().isOk());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeUpdate);
        Partenaires testPartenaires = partenairesList.get(partenairesList.size() - 1);
        assertThat(testPartenaires.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPartenaires.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPartenaires.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPartenaires.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartenaires.getLien()).isEqualTo(DEFAULT_LIEN);
    }

    @Test
    @Transactional
    void fullUpdatePartenairesWithPatch() throws Exception {
        // Initialize the database
        partenairesRepository.saveAndFlush(partenaires);

        int databaseSizeBeforeUpdate = partenairesRepository.findAll().size();

        // Update the partenaires using partial update
        Partenaires partialUpdatedPartenaires = new Partenaires();
        partialUpdatedPartenaires.setId(partenaires.getId());

        partialUpdatedPartenaires
            .nom(UPDATED_NOM)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .lien(UPDATED_LIEN);

        restPartenairesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartenaires.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartenaires))
            )
            .andExpect(status().isOk());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeUpdate);
        Partenaires testPartenaires = partenairesList.get(partenairesList.size() - 1);
        assertThat(testPartenaires.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPartenaires.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPartenaires.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPartenaires.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartenaires.getLien()).isEqualTo(UPDATED_LIEN);
    }

    @Test
    @Transactional
    void patchNonExistingPartenaires() throws Exception {
        int databaseSizeBeforeUpdate = partenairesRepository.findAll().size();
        partenaires.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartenairesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partenaires.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partenaires))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartenaires() throws Exception {
        int databaseSizeBeforeUpdate = partenairesRepository.findAll().size();
        partenaires.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartenairesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partenaires))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartenaires() throws Exception {
        int databaseSizeBeforeUpdate = partenairesRepository.findAll().size();
        partenaires.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartenairesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partenaires))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partenaires in the database
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartenaires() throws Exception {
        // Initialize the database
        partenairesRepository.saveAndFlush(partenaires);

        int databaseSizeBeforeDelete = partenairesRepository.findAll().size();

        // Delete the partenaires
        restPartenairesMockMvc
            .perform(delete(ENTITY_API_URL_ID, partenaires.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Partenaires> partenairesList = partenairesRepository.findAll();
        assertThat(partenairesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
