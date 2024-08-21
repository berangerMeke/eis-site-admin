package com.eis.site.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eis.site.IntegrationTest;
import com.eis.site.domain.PageService;
import com.eis.site.repository.PageServiceRepository;
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
 * Integration tests for the {@link PageServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageServiceResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SOUSTITRE = "AAAAAAAAAA";
    private static final String UPDATED_SOUSTITRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/page-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageServiceRepository pageServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageServiceMockMvc;

    private PageService pageService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageService createEntity(EntityManager em) {
        PageService pageService = new PageService().titre(DEFAULT_TITRE).soustitre(DEFAULT_SOUSTITRE);
        return pageService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageService createUpdatedEntity(EntityManager em) {
        PageService pageService = new PageService().titre(UPDATED_TITRE).soustitre(UPDATED_SOUSTITRE);
        return pageService;
    }

    @BeforeEach
    public void initTest() {
        pageService = createEntity(em);
    }

    @Test
    @Transactional
    void createPageService() throws Exception {
        int databaseSizeBeforeCreate = pageServiceRepository.findAll().size();
        // Create the PageService
        restPageServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageService)))
            .andExpect(status().isCreated());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeCreate + 1);
        PageService testPageService = pageServiceList.get(pageServiceList.size() - 1);
        assertThat(testPageService.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testPageService.getSoustitre()).isEqualTo(DEFAULT_SOUSTITRE);
    }

    @Test
    @Transactional
    void createPageServiceWithExistingId() throws Exception {
        // Create the PageService with an existing ID
        pageService.setId(1L);

        int databaseSizeBeforeCreate = pageServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageService)))
            .andExpect(status().isBadRequest());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageServices() throws Exception {
        // Initialize the database
        pageServiceRepository.saveAndFlush(pageService);

        // Get all the pageServiceList
        restPageServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageService.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].soustitre").value(hasItem(DEFAULT_SOUSTITRE)));
    }

    @Test
    @Transactional
    void getPageService() throws Exception {
        // Initialize the database
        pageServiceRepository.saveAndFlush(pageService);

        // Get the pageService
        restPageServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, pageService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageService.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.soustitre").value(DEFAULT_SOUSTITRE));
    }

    @Test
    @Transactional
    void getNonExistingPageService() throws Exception {
        // Get the pageService
        restPageServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPageService() throws Exception {
        // Initialize the database
        pageServiceRepository.saveAndFlush(pageService);

        int databaseSizeBeforeUpdate = pageServiceRepository.findAll().size();

        // Update the pageService
        PageService updatedPageService = pageServiceRepository.findById(pageService.getId()).get();
        // Disconnect from session so that the updates on updatedPageService are not directly saved in db
        em.detach(updatedPageService);
        updatedPageService.titre(UPDATED_TITRE).soustitre(UPDATED_SOUSTITRE);

        restPageServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPageService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPageService))
            )
            .andExpect(status().isOk());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeUpdate);
        PageService testPageService = pageServiceList.get(pageServiceList.size() - 1);
        assertThat(testPageService.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testPageService.getSoustitre()).isEqualTo(UPDATED_SOUSTITRE);
    }

    @Test
    @Transactional
    void putNonExistingPageService() throws Exception {
        int databaseSizeBeforeUpdate = pageServiceRepository.findAll().size();
        pageService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageService() throws Exception {
        int databaseSizeBeforeUpdate = pageServiceRepository.findAll().size();
        pageService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageService() throws Exception {
        int databaseSizeBeforeUpdate = pageServiceRepository.findAll().size();
        pageService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageService)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageServiceWithPatch() throws Exception {
        // Initialize the database
        pageServiceRepository.saveAndFlush(pageService);

        int databaseSizeBeforeUpdate = pageServiceRepository.findAll().size();

        // Update the pageService using partial update
        PageService partialUpdatedPageService = new PageService();
        partialUpdatedPageService.setId(pageService.getId());

        restPageServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageService))
            )
            .andExpect(status().isOk());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeUpdate);
        PageService testPageService = pageServiceList.get(pageServiceList.size() - 1);
        assertThat(testPageService.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testPageService.getSoustitre()).isEqualTo(DEFAULT_SOUSTITRE);
    }

    @Test
    @Transactional
    void fullUpdatePageServiceWithPatch() throws Exception {
        // Initialize the database
        pageServiceRepository.saveAndFlush(pageService);

        int databaseSizeBeforeUpdate = pageServiceRepository.findAll().size();

        // Update the pageService using partial update
        PageService partialUpdatedPageService = new PageService();
        partialUpdatedPageService.setId(pageService.getId());

        partialUpdatedPageService.titre(UPDATED_TITRE).soustitre(UPDATED_SOUSTITRE);

        restPageServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageService))
            )
            .andExpect(status().isOk());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeUpdate);
        PageService testPageService = pageServiceList.get(pageServiceList.size() - 1);
        assertThat(testPageService.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testPageService.getSoustitre()).isEqualTo(UPDATED_SOUSTITRE);
    }

    @Test
    @Transactional
    void patchNonExistingPageService() throws Exception {
        int databaseSizeBeforeUpdate = pageServiceRepository.findAll().size();
        pageService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageService() throws Exception {
        int databaseSizeBeforeUpdate = pageServiceRepository.findAll().size();
        pageService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageService() throws Exception {
        int databaseSizeBeforeUpdate = pageServiceRepository.findAll().size();
        pageService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageServiceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageService in the database
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageService() throws Exception {
        // Initialize the database
        pageServiceRepository.saveAndFlush(pageService);

        int databaseSizeBeforeDelete = pageServiceRepository.findAll().size();

        // Delete the pageService
        restPageServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageService> pageServiceList = pageServiceRepository.findAll();
        assertThat(pageServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
