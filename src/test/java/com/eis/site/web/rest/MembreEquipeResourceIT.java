package com.eis.site.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eis.site.IntegrationTest;
import com.eis.site.domain.MembreEquipe;
import com.eis.site.repository.MembreEquipeRepository;
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
 * Integration tests for the {@link MembreEquipeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MembreEquipeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOME = "BBBBBBBBBB";

    private static final String DEFAULT_NIVEAU_ETUDE = "AAAAAAAAAA";
    private static final String UPDATED_NIVEAU_ETUDE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/membre-equipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MembreEquipeRepository membreEquipeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembreEquipeMockMvc;

    private MembreEquipe membreEquipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembreEquipe createEntity(EntityManager em) {
        MembreEquipe membreEquipe = new MembreEquipe()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .adresseMail(DEFAULT_ADRESSE_MAIL)
            .certification(DEFAULT_CERTIFICATION)
            .diplome(DEFAULT_DIPLOME)
            .niveauEtude(DEFAULT_NIVEAU_ETUDE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .message(DEFAULT_MESSAGE)
            .fonction(DEFAULT_FONCTION);
        return membreEquipe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembreEquipe createUpdatedEntity(EntityManager em) {
        MembreEquipe membreEquipe = new MembreEquipe()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .adresseMail(UPDATED_ADRESSE_MAIL)
            .certification(UPDATED_CERTIFICATION)
            .diplome(UPDATED_DIPLOME)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .message(UPDATED_MESSAGE)
            .fonction(UPDATED_FONCTION);
        return membreEquipe;
    }

    @BeforeEach
    public void initTest() {
        membreEquipe = createEntity(em);
    }

    @Test
    @Transactional
    void createMembreEquipe() throws Exception {
        int databaseSizeBeforeCreate = membreEquipeRepository.findAll().size();
        // Create the MembreEquipe
        restMembreEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membreEquipe)))
            .andExpect(status().isCreated());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeCreate + 1);
        MembreEquipe testMembreEquipe = membreEquipeList.get(membreEquipeList.size() - 1);
        assertThat(testMembreEquipe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMembreEquipe.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMembreEquipe.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testMembreEquipe.getAdresseMail()).isEqualTo(DEFAULT_ADRESSE_MAIL);
        assertThat(testMembreEquipe.getCertification()).isEqualTo(DEFAULT_CERTIFICATION);
        assertThat(testMembreEquipe.getDiplome()).isEqualTo(DEFAULT_DIPLOME);
        assertThat(testMembreEquipe.getNiveauEtude()).isEqualTo(DEFAULT_NIVEAU_ETUDE);
        assertThat(testMembreEquipe.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMembreEquipe.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testMembreEquipe.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMembreEquipe.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testMembreEquipe.getFonction()).isEqualTo(DEFAULT_FONCTION);
    }

    @Test
    @Transactional
    void createMembreEquipeWithExistingId() throws Exception {
        // Create the MembreEquipe with an existing ID
        membreEquipe.setId(1L);

        int databaseSizeBeforeCreate = membreEquipeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membreEquipe)))
            .andExpect(status().isBadRequest());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMembreEquipes() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);

        // Get all the membreEquipeList
        restMembreEquipeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreEquipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].adresseMail").value(hasItem(DEFAULT_ADRESSE_MAIL)))
            .andExpect(jsonPath("$.[*].certification").value(hasItem(DEFAULT_CERTIFICATION)))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(DEFAULT_DIPLOME)))
            .andExpect(jsonPath("$.[*].niveauEtude").value(hasItem(DEFAULT_NIVEAU_ETUDE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].fonction").value(hasItem(DEFAULT_FONCTION)));
    }

    @Test
    @Transactional
    void getMembreEquipe() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);

        // Get the membreEquipe
        restMembreEquipeMockMvc
            .perform(get(ENTITY_API_URL_ID, membreEquipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membreEquipe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.adresseMail").value(DEFAULT_ADRESSE_MAIL))
            .andExpect(jsonPath("$.certification").value(DEFAULT_CERTIFICATION))
            .andExpect(jsonPath("$.diplome").value(DEFAULT_DIPLOME))
            .andExpect(jsonPath("$.niveauEtude").value(DEFAULT_NIVEAU_ETUDE))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.fonction").value(DEFAULT_FONCTION));
    }

    @Test
    @Transactional
    void getNonExistingMembreEquipe() throws Exception {
        // Get the membreEquipe
        restMembreEquipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMembreEquipe() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);

        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();

        // Update the membreEquipe
        MembreEquipe updatedMembreEquipe = membreEquipeRepository.findById(membreEquipe.getId()).get();
        // Disconnect from session so that the updates on updatedMembreEquipe are not directly saved in db
        em.detach(updatedMembreEquipe);
        updatedMembreEquipe
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .adresseMail(UPDATED_ADRESSE_MAIL)
            .certification(UPDATED_CERTIFICATION)
            .diplome(UPDATED_DIPLOME)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .message(UPDATED_MESSAGE)
            .fonction(UPDATED_FONCTION);

        restMembreEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMembreEquipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMembreEquipe))
            )
            .andExpect(status().isOk());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
        MembreEquipe testMembreEquipe = membreEquipeList.get(membreEquipeList.size() - 1);
        assertThat(testMembreEquipe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembreEquipe.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMembreEquipe.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testMembreEquipe.getAdresseMail()).isEqualTo(UPDATED_ADRESSE_MAIL);
        assertThat(testMembreEquipe.getCertification()).isEqualTo(UPDATED_CERTIFICATION);
        assertThat(testMembreEquipe.getDiplome()).isEqualTo(UPDATED_DIPLOME);
        assertThat(testMembreEquipe.getNiveauEtude()).isEqualTo(UPDATED_NIVEAU_ETUDE);
        assertThat(testMembreEquipe.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMembreEquipe.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testMembreEquipe.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMembreEquipe.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testMembreEquipe.getFonction()).isEqualTo(UPDATED_FONCTION);
    }

    @Test
    @Transactional
    void putNonExistingMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();
        membreEquipe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, membreEquipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membreEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();
        membreEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembreEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membreEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();
        membreEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembreEquipeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membreEquipe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMembreEquipeWithPatch() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);

        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();

        // Update the membreEquipe using partial update
        MembreEquipe partialUpdatedMembreEquipe = new MembreEquipe();
        partialUpdatedMembreEquipe.setId(membreEquipe.getId());

        partialUpdatedMembreEquipe
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .description(UPDATED_DESCRIPTION)
            .message(UPDATED_MESSAGE)
            .fonction(UPDATED_FONCTION);

        restMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembreEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembreEquipe))
            )
            .andExpect(status().isOk());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
        MembreEquipe testMembreEquipe = membreEquipeList.get(membreEquipeList.size() - 1);
        assertThat(testMembreEquipe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMembreEquipe.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMembreEquipe.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testMembreEquipe.getAdresseMail()).isEqualTo(DEFAULT_ADRESSE_MAIL);
        assertThat(testMembreEquipe.getCertification()).isEqualTo(DEFAULT_CERTIFICATION);
        assertThat(testMembreEquipe.getDiplome()).isEqualTo(DEFAULT_DIPLOME);
        assertThat(testMembreEquipe.getNiveauEtude()).isEqualTo(UPDATED_NIVEAU_ETUDE);
        assertThat(testMembreEquipe.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMembreEquipe.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testMembreEquipe.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMembreEquipe.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testMembreEquipe.getFonction()).isEqualTo(UPDATED_FONCTION);
    }

    @Test
    @Transactional
    void fullUpdateMembreEquipeWithPatch() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);

        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();

        // Update the membreEquipe using partial update
        MembreEquipe partialUpdatedMembreEquipe = new MembreEquipe();
        partialUpdatedMembreEquipe.setId(membreEquipe.getId());

        partialUpdatedMembreEquipe
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .adresseMail(UPDATED_ADRESSE_MAIL)
            .certification(UPDATED_CERTIFICATION)
            .diplome(UPDATED_DIPLOME)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .message(UPDATED_MESSAGE)
            .fonction(UPDATED_FONCTION);

        restMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembreEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembreEquipe))
            )
            .andExpect(status().isOk());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
        MembreEquipe testMembreEquipe = membreEquipeList.get(membreEquipeList.size() - 1);
        assertThat(testMembreEquipe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembreEquipe.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMembreEquipe.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testMembreEquipe.getAdresseMail()).isEqualTo(UPDATED_ADRESSE_MAIL);
        assertThat(testMembreEquipe.getCertification()).isEqualTo(UPDATED_CERTIFICATION);
        assertThat(testMembreEquipe.getDiplome()).isEqualTo(UPDATED_DIPLOME);
        assertThat(testMembreEquipe.getNiveauEtude()).isEqualTo(UPDATED_NIVEAU_ETUDE);
        assertThat(testMembreEquipe.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMembreEquipe.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testMembreEquipe.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMembreEquipe.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testMembreEquipe.getFonction()).isEqualTo(UPDATED_FONCTION);
    }

    @Test
    @Transactional
    void patchNonExistingMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();
        membreEquipe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, membreEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membreEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();
        membreEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membreEquipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();
        membreEquipe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembreEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(membreEquipe))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMembreEquipe() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);

        int databaseSizeBeforeDelete = membreEquipeRepository.findAll().size();

        // Delete the membreEquipe
        restMembreEquipeMockMvc
            .perform(delete(ENTITY_API_URL_ID, membreEquipe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
