package com.eis.site.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eis.site.IntegrationTest;
import com.eis.site.domain.PageAPropos;
import com.eis.site.repository.PageAProposRepository;
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
 * Integration tests for the {@link PageAProposResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageAProposResourceIT {

    private static final byte[] DEFAULT_SEC_1_IMG_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_1_IMG_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_1_IMG_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_1_IMG_1_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_1_DESC_1 = "AAAAAAAAAA";
    private static final String UPDATED_SEC_1_DESC_1 = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_1_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_1_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_1_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_1_LOGO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_1_IMG_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_1_IMG_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_1_IMG_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_1_IMG_2_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_1_DESC_2 = "AAAAAAAAAA";
    private static final String UPDATED_SEC_1_DESC_2 = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_1_IMG_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_1_IMG_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_1_IMG_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_1_IMG_3_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_1_DESC_3 = "AAAAAAAAAA";
    private static final String UPDATED_SEC_1_DESC_3 = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_2_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_2_TITRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_2_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_2_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_2_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_2_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_2_SOUS_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_2_SOUS_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_2_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_2_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_3_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_TITRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_3_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_3_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_3_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_3_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_3_SOUS_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_SOUS_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_3_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_TEXT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_4_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_4_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_4_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_4_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_4_DESC_1 = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_DESC_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_4_DESC_2 = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_DESC_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_4_DESC_3 = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_DESC_3 = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_4_DESC_4 = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_DESC_4 = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_5_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_5_TITRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/page-a-propos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageAProposRepository pageAProposRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageAProposMockMvc;

    private PageAPropos pageAPropos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageAPropos createEntity(EntityManager em) {
        PageAPropos pageAPropos = new PageAPropos()
            .sec1Img1(DEFAULT_SEC_1_IMG_1)
            .sec1Img1ContentType(DEFAULT_SEC_1_IMG_1_CONTENT_TYPE)
            .sec1Desc1(DEFAULT_SEC_1_DESC_1)
            .sec1Logo(DEFAULT_SEC_1_LOGO)
            .sec1LogoContentType(DEFAULT_SEC_1_LOGO_CONTENT_TYPE)
            .sec1Img2(DEFAULT_SEC_1_IMG_2)
            .sec1Img2ContentType(DEFAULT_SEC_1_IMG_2_CONTENT_TYPE)
            .sec1Desc2(DEFAULT_SEC_1_DESC_2)
            .sec1Img3(DEFAULT_SEC_1_IMG_3)
            .sec1Img3ContentType(DEFAULT_SEC_1_IMG_3_CONTENT_TYPE)
            .sec1Desc3(DEFAULT_SEC_1_DESC_3)
            .sec2Titre(DEFAULT_SEC_2_TITRE)
            .sec2Img(DEFAULT_SEC_2_IMG)
            .sec2ImgContentType(DEFAULT_SEC_2_IMG_CONTENT_TYPE)
            .sec2SousTitre(DEFAULT_SEC_2_SOUS_TITRE)
            .sec2Text(DEFAULT_SEC_2_TEXT)
            .sec3Titre(DEFAULT_SEC_3_TITRE)
            .sec3Img(DEFAULT_SEC_3_IMG)
            .sec3ImgContentType(DEFAULT_SEC_3_IMG_CONTENT_TYPE)
            .sec3SousTitre(DEFAULT_SEC_3_SOUS_TITRE)
            .sec3Text(DEFAULT_SEC_3_TEXT)
            .sec4Img(DEFAULT_SEC_4_IMG)
            .sec4ImgContentType(DEFAULT_SEC_4_IMG_CONTENT_TYPE)
            .sec4Desc1(DEFAULT_SEC_4_DESC_1)
            .sec4Desc2(DEFAULT_SEC_4_DESC_2)
            .sec4Desc3(DEFAULT_SEC_4_DESC_3)
            .sec4Desc4(DEFAULT_SEC_4_DESC_4)
            .sec5Titre(DEFAULT_SEC_5_TITRE);
        return pageAPropos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageAPropos createUpdatedEntity(EntityManager em) {
        PageAPropos pageAPropos = new PageAPropos()
            .sec1Img1(UPDATED_SEC_1_IMG_1)
            .sec1Img1ContentType(UPDATED_SEC_1_IMG_1_CONTENT_TYPE)
            .sec1Desc1(UPDATED_SEC_1_DESC_1)
            .sec1Logo(UPDATED_SEC_1_LOGO)
            .sec1LogoContentType(UPDATED_SEC_1_LOGO_CONTENT_TYPE)
            .sec1Img2(UPDATED_SEC_1_IMG_2)
            .sec1Img2ContentType(UPDATED_SEC_1_IMG_2_CONTENT_TYPE)
            .sec1Desc2(UPDATED_SEC_1_DESC_2)
            .sec1Img3(UPDATED_SEC_1_IMG_3)
            .sec1Img3ContentType(UPDATED_SEC_1_IMG_3_CONTENT_TYPE)
            .sec1Desc3(UPDATED_SEC_1_DESC_3)
            .sec2Titre(UPDATED_SEC_2_TITRE)
            .sec2Img(UPDATED_SEC_2_IMG)
            .sec2ImgContentType(UPDATED_SEC_2_IMG_CONTENT_TYPE)
            .sec2SousTitre(UPDATED_SEC_2_SOUS_TITRE)
            .sec2Text(UPDATED_SEC_2_TEXT)
            .sec3Titre(UPDATED_SEC_3_TITRE)
            .sec3Img(UPDATED_SEC_3_IMG)
            .sec3ImgContentType(UPDATED_SEC_3_IMG_CONTENT_TYPE)
            .sec3SousTitre(UPDATED_SEC_3_SOUS_TITRE)
            .sec3Text(UPDATED_SEC_3_TEXT)
            .sec4Img(UPDATED_SEC_4_IMG)
            .sec4ImgContentType(UPDATED_SEC_4_IMG_CONTENT_TYPE)
            .sec4Desc1(UPDATED_SEC_4_DESC_1)
            .sec4Desc2(UPDATED_SEC_4_DESC_2)
            .sec4Desc3(UPDATED_SEC_4_DESC_3)
            .sec4Desc4(UPDATED_SEC_4_DESC_4)
            .sec5Titre(UPDATED_SEC_5_TITRE);
        return pageAPropos;
    }

    @BeforeEach
    public void initTest() {
        pageAPropos = createEntity(em);
    }

    @Test
    @Transactional
    void createPageAPropos() throws Exception {
        int databaseSizeBeforeCreate = pageAProposRepository.findAll().size();
        // Create the PageAPropos
        restPageAProposMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageAPropos)))
            .andExpect(status().isCreated());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeCreate + 1);
        PageAPropos testPageAPropos = pageAProposList.get(pageAProposList.size() - 1);
        assertThat(testPageAPropos.getSec1Img1()).isEqualTo(DEFAULT_SEC_1_IMG_1);
        assertThat(testPageAPropos.getSec1Img1ContentType()).isEqualTo(DEFAULT_SEC_1_IMG_1_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc1()).isEqualTo(DEFAULT_SEC_1_DESC_1);
        assertThat(testPageAPropos.getSec1Logo()).isEqualTo(DEFAULT_SEC_1_LOGO);
        assertThat(testPageAPropos.getSec1LogoContentType()).isEqualTo(DEFAULT_SEC_1_LOGO_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Img2()).isEqualTo(DEFAULT_SEC_1_IMG_2);
        assertThat(testPageAPropos.getSec1Img2ContentType()).isEqualTo(DEFAULT_SEC_1_IMG_2_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc2()).isEqualTo(DEFAULT_SEC_1_DESC_2);
        assertThat(testPageAPropos.getSec1Img3()).isEqualTo(DEFAULT_SEC_1_IMG_3);
        assertThat(testPageAPropos.getSec1Img3ContentType()).isEqualTo(DEFAULT_SEC_1_IMG_3_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc3()).isEqualTo(DEFAULT_SEC_1_DESC_3);
        assertThat(testPageAPropos.getSec2Titre()).isEqualTo(DEFAULT_SEC_2_TITRE);
        assertThat(testPageAPropos.getSec2Img()).isEqualTo(DEFAULT_SEC_2_IMG);
        assertThat(testPageAPropos.getSec2ImgContentType()).isEqualTo(DEFAULT_SEC_2_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec2SousTitre()).isEqualTo(DEFAULT_SEC_2_SOUS_TITRE);
        assertThat(testPageAPropos.getSec2Text()).isEqualTo(DEFAULT_SEC_2_TEXT);
        assertThat(testPageAPropos.getSec3Titre()).isEqualTo(DEFAULT_SEC_3_TITRE);
        assertThat(testPageAPropos.getSec3Img()).isEqualTo(DEFAULT_SEC_3_IMG);
        assertThat(testPageAPropos.getSec3ImgContentType()).isEqualTo(DEFAULT_SEC_3_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec3SousTitre()).isEqualTo(DEFAULT_SEC_3_SOUS_TITRE);
        assertThat(testPageAPropos.getSec3Text()).isEqualTo(DEFAULT_SEC_3_TEXT);
        assertThat(testPageAPropos.getSec4Img()).isEqualTo(DEFAULT_SEC_4_IMG);
        assertThat(testPageAPropos.getSec4ImgContentType()).isEqualTo(DEFAULT_SEC_4_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec4Desc1()).isEqualTo(DEFAULT_SEC_4_DESC_1);
        assertThat(testPageAPropos.getSec4Desc2()).isEqualTo(DEFAULT_SEC_4_DESC_2);
        assertThat(testPageAPropos.getSec4Desc3()).isEqualTo(DEFAULT_SEC_4_DESC_3);
        assertThat(testPageAPropos.getSec4Desc4()).isEqualTo(DEFAULT_SEC_4_DESC_4);
        assertThat(testPageAPropos.getSec5Titre()).isEqualTo(DEFAULT_SEC_5_TITRE);
    }

    @Test
    @Transactional
    void createPageAProposWithExistingId() throws Exception {
        // Create the PageAPropos with an existing ID
        pageAPropos.setId(1L);

        int databaseSizeBeforeCreate = pageAProposRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageAProposMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageAPropos)))
            .andExpect(status().isBadRequest());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageAPropos() throws Exception {
        // Initialize the database
        pageAProposRepository.saveAndFlush(pageAPropos);

        // Get all the pageAProposList
        restPageAProposMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageAPropos.getId().intValue())))
            .andExpect(jsonPath("$.[*].sec1Img1ContentType").value(hasItem(DEFAULT_SEC_1_IMG_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec1Img1").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_1_IMG_1))))
            .andExpect(jsonPath("$.[*].sec1Desc1").value(hasItem(DEFAULT_SEC_1_DESC_1)))
            .andExpect(jsonPath("$.[*].sec1LogoContentType").value(hasItem(DEFAULT_SEC_1_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec1Logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_1_LOGO))))
            .andExpect(jsonPath("$.[*].sec1Img2ContentType").value(hasItem(DEFAULT_SEC_1_IMG_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec1Img2").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_1_IMG_2))))
            .andExpect(jsonPath("$.[*].sec1Desc2").value(hasItem(DEFAULT_SEC_1_DESC_2)))
            .andExpect(jsonPath("$.[*].sec1Img3ContentType").value(hasItem(DEFAULT_SEC_1_IMG_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec1Img3").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_1_IMG_3))))
            .andExpect(jsonPath("$.[*].sec1Desc3").value(hasItem(DEFAULT_SEC_1_DESC_3)))
            .andExpect(jsonPath("$.[*].sec2Titre").value(hasItem(DEFAULT_SEC_2_TITRE)))
            .andExpect(jsonPath("$.[*].sec2ImgContentType").value(hasItem(DEFAULT_SEC_2_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec2Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_2_IMG))))
            .andExpect(jsonPath("$.[*].sec2SousTitre").value(hasItem(DEFAULT_SEC_2_SOUS_TITRE)))
            .andExpect(jsonPath("$.[*].sec2Text").value(hasItem(DEFAULT_SEC_2_TEXT)))
            .andExpect(jsonPath("$.[*].sec3Titre").value(hasItem(DEFAULT_SEC_3_TITRE)))
            .andExpect(jsonPath("$.[*].sec3ImgContentType").value(hasItem(DEFAULT_SEC_3_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec3Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_3_IMG))))
            .andExpect(jsonPath("$.[*].sec3SousTitre").value(hasItem(DEFAULT_SEC_3_SOUS_TITRE)))
            .andExpect(jsonPath("$.[*].sec3Text").value(hasItem(DEFAULT_SEC_3_TEXT)))
            .andExpect(jsonPath("$.[*].sec4ImgContentType").value(hasItem(DEFAULT_SEC_4_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec4Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_4_IMG))))
            .andExpect(jsonPath("$.[*].sec4Desc1").value(hasItem(DEFAULT_SEC_4_DESC_1)))
            .andExpect(jsonPath("$.[*].sec4Desc2").value(hasItem(DEFAULT_SEC_4_DESC_2)))
            .andExpect(jsonPath("$.[*].sec4Desc3").value(hasItem(DEFAULT_SEC_4_DESC_3)))
            .andExpect(jsonPath("$.[*].sec4Desc4").value(hasItem(DEFAULT_SEC_4_DESC_4)))
            .andExpect(jsonPath("$.[*].sec5Titre").value(hasItem(DEFAULT_SEC_5_TITRE)));
    }

    @Test
    @Transactional
    void getPageAPropos() throws Exception {
        // Initialize the database
        pageAProposRepository.saveAndFlush(pageAPropos);

        // Get the pageAPropos
        restPageAProposMockMvc
            .perform(get(ENTITY_API_URL_ID, pageAPropos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageAPropos.getId().intValue()))
            .andExpect(jsonPath("$.sec1Img1ContentType").value(DEFAULT_SEC_1_IMG_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec1Img1").value(Base64Utils.encodeToString(DEFAULT_SEC_1_IMG_1)))
            .andExpect(jsonPath("$.sec1Desc1").value(DEFAULT_SEC_1_DESC_1))
            .andExpect(jsonPath("$.sec1LogoContentType").value(DEFAULT_SEC_1_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec1Logo").value(Base64Utils.encodeToString(DEFAULT_SEC_1_LOGO)))
            .andExpect(jsonPath("$.sec1Img2ContentType").value(DEFAULT_SEC_1_IMG_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec1Img2").value(Base64Utils.encodeToString(DEFAULT_SEC_1_IMG_2)))
            .andExpect(jsonPath("$.sec1Desc2").value(DEFAULT_SEC_1_DESC_2))
            .andExpect(jsonPath("$.sec1Img3ContentType").value(DEFAULT_SEC_1_IMG_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec1Img3").value(Base64Utils.encodeToString(DEFAULT_SEC_1_IMG_3)))
            .andExpect(jsonPath("$.sec1Desc3").value(DEFAULT_SEC_1_DESC_3))
            .andExpect(jsonPath("$.sec2Titre").value(DEFAULT_SEC_2_TITRE))
            .andExpect(jsonPath("$.sec2ImgContentType").value(DEFAULT_SEC_2_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec2Img").value(Base64Utils.encodeToString(DEFAULT_SEC_2_IMG)))
            .andExpect(jsonPath("$.sec2SousTitre").value(DEFAULT_SEC_2_SOUS_TITRE))
            .andExpect(jsonPath("$.sec2Text").value(DEFAULT_SEC_2_TEXT))
            .andExpect(jsonPath("$.sec3Titre").value(DEFAULT_SEC_3_TITRE))
            .andExpect(jsonPath("$.sec3ImgContentType").value(DEFAULT_SEC_3_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec3Img").value(Base64Utils.encodeToString(DEFAULT_SEC_3_IMG)))
            .andExpect(jsonPath("$.sec3SousTitre").value(DEFAULT_SEC_3_SOUS_TITRE))
            .andExpect(jsonPath("$.sec3Text").value(DEFAULT_SEC_3_TEXT))
            .andExpect(jsonPath("$.sec4ImgContentType").value(DEFAULT_SEC_4_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec4Img").value(Base64Utils.encodeToString(DEFAULT_SEC_4_IMG)))
            .andExpect(jsonPath("$.sec4Desc1").value(DEFAULT_SEC_4_DESC_1))
            .andExpect(jsonPath("$.sec4Desc2").value(DEFAULT_SEC_4_DESC_2))
            .andExpect(jsonPath("$.sec4Desc3").value(DEFAULT_SEC_4_DESC_3))
            .andExpect(jsonPath("$.sec4Desc4").value(DEFAULT_SEC_4_DESC_4))
            .andExpect(jsonPath("$.sec5Titre").value(DEFAULT_SEC_5_TITRE));
    }

    @Test
    @Transactional
    void getNonExistingPageAPropos() throws Exception {
        // Get the pageAPropos
        restPageAProposMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPageAPropos() throws Exception {
        // Initialize the database
        pageAProposRepository.saveAndFlush(pageAPropos);

        int databaseSizeBeforeUpdate = pageAProposRepository.findAll().size();

        // Update the pageAPropos
        PageAPropos updatedPageAPropos = pageAProposRepository.findById(pageAPropos.getId()).get();
        // Disconnect from session so that the updates on updatedPageAPropos are not directly saved in db
        em.detach(updatedPageAPropos);
        updatedPageAPropos
            .sec1Img1(UPDATED_SEC_1_IMG_1)
            .sec1Img1ContentType(UPDATED_SEC_1_IMG_1_CONTENT_TYPE)
            .sec1Desc1(UPDATED_SEC_1_DESC_1)
            .sec1Logo(UPDATED_SEC_1_LOGO)
            .sec1LogoContentType(UPDATED_SEC_1_LOGO_CONTENT_TYPE)
            .sec1Img2(UPDATED_SEC_1_IMG_2)
            .sec1Img2ContentType(UPDATED_SEC_1_IMG_2_CONTENT_TYPE)
            .sec1Desc2(UPDATED_SEC_1_DESC_2)
            .sec1Img3(UPDATED_SEC_1_IMG_3)
            .sec1Img3ContentType(UPDATED_SEC_1_IMG_3_CONTENT_TYPE)
            .sec1Desc3(UPDATED_SEC_1_DESC_3)
            .sec2Titre(UPDATED_SEC_2_TITRE)
            .sec2Img(UPDATED_SEC_2_IMG)
            .sec2ImgContentType(UPDATED_SEC_2_IMG_CONTENT_TYPE)
            .sec2SousTitre(UPDATED_SEC_2_SOUS_TITRE)
            .sec2Text(UPDATED_SEC_2_TEXT)
            .sec3Titre(UPDATED_SEC_3_TITRE)
            .sec3Img(UPDATED_SEC_3_IMG)
            .sec3ImgContentType(UPDATED_SEC_3_IMG_CONTENT_TYPE)
            .sec3SousTitre(UPDATED_SEC_3_SOUS_TITRE)
            .sec3Text(UPDATED_SEC_3_TEXT)
            .sec4Img(UPDATED_SEC_4_IMG)
            .sec4ImgContentType(UPDATED_SEC_4_IMG_CONTENT_TYPE)
            .sec4Desc1(UPDATED_SEC_4_DESC_1)
            .sec4Desc2(UPDATED_SEC_4_DESC_2)
            .sec4Desc3(UPDATED_SEC_4_DESC_3)
            .sec4Desc4(UPDATED_SEC_4_DESC_4)
            .sec5Titre(UPDATED_SEC_5_TITRE);

        restPageAProposMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPageAPropos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPageAPropos))
            )
            .andExpect(status().isOk());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeUpdate);
        PageAPropos testPageAPropos = pageAProposList.get(pageAProposList.size() - 1);
        assertThat(testPageAPropos.getSec1Img1()).isEqualTo(UPDATED_SEC_1_IMG_1);
        assertThat(testPageAPropos.getSec1Img1ContentType()).isEqualTo(UPDATED_SEC_1_IMG_1_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc1()).isEqualTo(UPDATED_SEC_1_DESC_1);
        assertThat(testPageAPropos.getSec1Logo()).isEqualTo(UPDATED_SEC_1_LOGO);
        assertThat(testPageAPropos.getSec1LogoContentType()).isEqualTo(UPDATED_SEC_1_LOGO_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Img2()).isEqualTo(UPDATED_SEC_1_IMG_2);
        assertThat(testPageAPropos.getSec1Img2ContentType()).isEqualTo(UPDATED_SEC_1_IMG_2_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc2()).isEqualTo(UPDATED_SEC_1_DESC_2);
        assertThat(testPageAPropos.getSec1Img3()).isEqualTo(UPDATED_SEC_1_IMG_3);
        assertThat(testPageAPropos.getSec1Img3ContentType()).isEqualTo(UPDATED_SEC_1_IMG_3_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc3()).isEqualTo(UPDATED_SEC_1_DESC_3);
        assertThat(testPageAPropos.getSec2Titre()).isEqualTo(UPDATED_SEC_2_TITRE);
        assertThat(testPageAPropos.getSec2Img()).isEqualTo(UPDATED_SEC_2_IMG);
        assertThat(testPageAPropos.getSec2ImgContentType()).isEqualTo(UPDATED_SEC_2_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec2SousTitre()).isEqualTo(UPDATED_SEC_2_SOUS_TITRE);
        assertThat(testPageAPropos.getSec2Text()).isEqualTo(UPDATED_SEC_2_TEXT);
        assertThat(testPageAPropos.getSec3Titre()).isEqualTo(UPDATED_SEC_3_TITRE);
        assertThat(testPageAPropos.getSec3Img()).isEqualTo(UPDATED_SEC_3_IMG);
        assertThat(testPageAPropos.getSec3ImgContentType()).isEqualTo(UPDATED_SEC_3_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec3SousTitre()).isEqualTo(UPDATED_SEC_3_SOUS_TITRE);
        assertThat(testPageAPropos.getSec3Text()).isEqualTo(UPDATED_SEC_3_TEXT);
        assertThat(testPageAPropos.getSec4Img()).isEqualTo(UPDATED_SEC_4_IMG);
        assertThat(testPageAPropos.getSec4ImgContentType()).isEqualTo(UPDATED_SEC_4_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec4Desc1()).isEqualTo(UPDATED_SEC_4_DESC_1);
        assertThat(testPageAPropos.getSec4Desc2()).isEqualTo(UPDATED_SEC_4_DESC_2);
        assertThat(testPageAPropos.getSec4Desc3()).isEqualTo(UPDATED_SEC_4_DESC_3);
        assertThat(testPageAPropos.getSec4Desc4()).isEqualTo(UPDATED_SEC_4_DESC_4);
        assertThat(testPageAPropos.getSec5Titre()).isEqualTo(UPDATED_SEC_5_TITRE);
    }

    @Test
    @Transactional
    void putNonExistingPageAPropos() throws Exception {
        int databaseSizeBeforeUpdate = pageAProposRepository.findAll().size();
        pageAPropos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageAProposMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageAPropos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageAPropos))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageAPropos() throws Exception {
        int databaseSizeBeforeUpdate = pageAProposRepository.findAll().size();
        pageAPropos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageAProposMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageAPropos))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageAPropos() throws Exception {
        int databaseSizeBeforeUpdate = pageAProposRepository.findAll().size();
        pageAPropos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageAProposMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageAPropos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageAProposWithPatch() throws Exception {
        // Initialize the database
        pageAProposRepository.saveAndFlush(pageAPropos);

        int databaseSizeBeforeUpdate = pageAProposRepository.findAll().size();

        // Update the pageAPropos using partial update
        PageAPropos partialUpdatedPageAPropos = new PageAPropos();
        partialUpdatedPageAPropos.setId(pageAPropos.getId());

        partialUpdatedPageAPropos
            .sec1Desc1(UPDATED_SEC_1_DESC_1)
            .sec1Logo(UPDATED_SEC_1_LOGO)
            .sec1LogoContentType(UPDATED_SEC_1_LOGO_CONTENT_TYPE)
            .sec1Img3(UPDATED_SEC_1_IMG_3)
            .sec1Img3ContentType(UPDATED_SEC_1_IMG_3_CONTENT_TYPE)
            .sec1Desc3(UPDATED_SEC_1_DESC_3)
            .sec2Titre(UPDATED_SEC_2_TITRE)
            .sec2Img(UPDATED_SEC_2_IMG)
            .sec2ImgContentType(UPDATED_SEC_2_IMG_CONTENT_TYPE)
            .sec3Titre(UPDATED_SEC_3_TITRE)
            .sec4Img(UPDATED_SEC_4_IMG)
            .sec4ImgContentType(UPDATED_SEC_4_IMG_CONTENT_TYPE)
            .sec4Desc4(UPDATED_SEC_4_DESC_4);

        restPageAProposMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageAPropos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageAPropos))
            )
            .andExpect(status().isOk());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeUpdate);
        PageAPropos testPageAPropos = pageAProposList.get(pageAProposList.size() - 1);
        assertThat(testPageAPropos.getSec1Img1()).isEqualTo(DEFAULT_SEC_1_IMG_1);
        assertThat(testPageAPropos.getSec1Img1ContentType()).isEqualTo(DEFAULT_SEC_1_IMG_1_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc1()).isEqualTo(UPDATED_SEC_1_DESC_1);
        assertThat(testPageAPropos.getSec1Logo()).isEqualTo(UPDATED_SEC_1_LOGO);
        assertThat(testPageAPropos.getSec1LogoContentType()).isEqualTo(UPDATED_SEC_1_LOGO_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Img2()).isEqualTo(DEFAULT_SEC_1_IMG_2);
        assertThat(testPageAPropos.getSec1Img2ContentType()).isEqualTo(DEFAULT_SEC_1_IMG_2_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc2()).isEqualTo(DEFAULT_SEC_1_DESC_2);
        assertThat(testPageAPropos.getSec1Img3()).isEqualTo(UPDATED_SEC_1_IMG_3);
        assertThat(testPageAPropos.getSec1Img3ContentType()).isEqualTo(UPDATED_SEC_1_IMG_3_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc3()).isEqualTo(UPDATED_SEC_1_DESC_3);
        assertThat(testPageAPropos.getSec2Titre()).isEqualTo(UPDATED_SEC_2_TITRE);
        assertThat(testPageAPropos.getSec2Img()).isEqualTo(UPDATED_SEC_2_IMG);
        assertThat(testPageAPropos.getSec2ImgContentType()).isEqualTo(UPDATED_SEC_2_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec2SousTitre()).isEqualTo(DEFAULT_SEC_2_SOUS_TITRE);
        assertThat(testPageAPropos.getSec2Text()).isEqualTo(DEFAULT_SEC_2_TEXT);
        assertThat(testPageAPropos.getSec3Titre()).isEqualTo(UPDATED_SEC_3_TITRE);
        assertThat(testPageAPropos.getSec3Img()).isEqualTo(DEFAULT_SEC_3_IMG);
        assertThat(testPageAPropos.getSec3ImgContentType()).isEqualTo(DEFAULT_SEC_3_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec3SousTitre()).isEqualTo(DEFAULT_SEC_3_SOUS_TITRE);
        assertThat(testPageAPropos.getSec3Text()).isEqualTo(DEFAULT_SEC_3_TEXT);
        assertThat(testPageAPropos.getSec4Img()).isEqualTo(UPDATED_SEC_4_IMG);
        assertThat(testPageAPropos.getSec4ImgContentType()).isEqualTo(UPDATED_SEC_4_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec4Desc1()).isEqualTo(DEFAULT_SEC_4_DESC_1);
        assertThat(testPageAPropos.getSec4Desc2()).isEqualTo(DEFAULT_SEC_4_DESC_2);
        assertThat(testPageAPropos.getSec4Desc3()).isEqualTo(DEFAULT_SEC_4_DESC_3);
        assertThat(testPageAPropos.getSec4Desc4()).isEqualTo(UPDATED_SEC_4_DESC_4);
        assertThat(testPageAPropos.getSec5Titre()).isEqualTo(DEFAULT_SEC_5_TITRE);
    }

    @Test
    @Transactional
    void fullUpdatePageAProposWithPatch() throws Exception {
        // Initialize the database
        pageAProposRepository.saveAndFlush(pageAPropos);

        int databaseSizeBeforeUpdate = pageAProposRepository.findAll().size();

        // Update the pageAPropos using partial update
        PageAPropos partialUpdatedPageAPropos = new PageAPropos();
        partialUpdatedPageAPropos.setId(pageAPropos.getId());

        partialUpdatedPageAPropos
            .sec1Img1(UPDATED_SEC_1_IMG_1)
            .sec1Img1ContentType(UPDATED_SEC_1_IMG_1_CONTENT_TYPE)
            .sec1Desc1(UPDATED_SEC_1_DESC_1)
            .sec1Logo(UPDATED_SEC_1_LOGO)
            .sec1LogoContentType(UPDATED_SEC_1_LOGO_CONTENT_TYPE)
            .sec1Img2(UPDATED_SEC_1_IMG_2)
            .sec1Img2ContentType(UPDATED_SEC_1_IMG_2_CONTENT_TYPE)
            .sec1Desc2(UPDATED_SEC_1_DESC_2)
            .sec1Img3(UPDATED_SEC_1_IMG_3)
            .sec1Img3ContentType(UPDATED_SEC_1_IMG_3_CONTENT_TYPE)
            .sec1Desc3(UPDATED_SEC_1_DESC_3)
            .sec2Titre(UPDATED_SEC_2_TITRE)
            .sec2Img(UPDATED_SEC_2_IMG)
            .sec2ImgContentType(UPDATED_SEC_2_IMG_CONTENT_TYPE)
            .sec2SousTitre(UPDATED_SEC_2_SOUS_TITRE)
            .sec2Text(UPDATED_SEC_2_TEXT)
            .sec3Titre(UPDATED_SEC_3_TITRE)
            .sec3Img(UPDATED_SEC_3_IMG)
            .sec3ImgContentType(UPDATED_SEC_3_IMG_CONTENT_TYPE)
            .sec3SousTitre(UPDATED_SEC_3_SOUS_TITRE)
            .sec3Text(UPDATED_SEC_3_TEXT)
            .sec4Img(UPDATED_SEC_4_IMG)
            .sec4ImgContentType(UPDATED_SEC_4_IMG_CONTENT_TYPE)
            .sec4Desc1(UPDATED_SEC_4_DESC_1)
            .sec4Desc2(UPDATED_SEC_4_DESC_2)
            .sec4Desc3(UPDATED_SEC_4_DESC_3)
            .sec4Desc4(UPDATED_SEC_4_DESC_4)
            .sec5Titre(UPDATED_SEC_5_TITRE);

        restPageAProposMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageAPropos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageAPropos))
            )
            .andExpect(status().isOk());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeUpdate);
        PageAPropos testPageAPropos = pageAProposList.get(pageAProposList.size() - 1);
        assertThat(testPageAPropos.getSec1Img1()).isEqualTo(UPDATED_SEC_1_IMG_1);
        assertThat(testPageAPropos.getSec1Img1ContentType()).isEqualTo(UPDATED_SEC_1_IMG_1_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc1()).isEqualTo(UPDATED_SEC_1_DESC_1);
        assertThat(testPageAPropos.getSec1Logo()).isEqualTo(UPDATED_SEC_1_LOGO);
        assertThat(testPageAPropos.getSec1LogoContentType()).isEqualTo(UPDATED_SEC_1_LOGO_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Img2()).isEqualTo(UPDATED_SEC_1_IMG_2);
        assertThat(testPageAPropos.getSec1Img2ContentType()).isEqualTo(UPDATED_SEC_1_IMG_2_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc2()).isEqualTo(UPDATED_SEC_1_DESC_2);
        assertThat(testPageAPropos.getSec1Img3()).isEqualTo(UPDATED_SEC_1_IMG_3);
        assertThat(testPageAPropos.getSec1Img3ContentType()).isEqualTo(UPDATED_SEC_1_IMG_3_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec1Desc3()).isEqualTo(UPDATED_SEC_1_DESC_3);
        assertThat(testPageAPropos.getSec2Titre()).isEqualTo(UPDATED_SEC_2_TITRE);
        assertThat(testPageAPropos.getSec2Img()).isEqualTo(UPDATED_SEC_2_IMG);
        assertThat(testPageAPropos.getSec2ImgContentType()).isEqualTo(UPDATED_SEC_2_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec2SousTitre()).isEqualTo(UPDATED_SEC_2_SOUS_TITRE);
        assertThat(testPageAPropos.getSec2Text()).isEqualTo(UPDATED_SEC_2_TEXT);
        assertThat(testPageAPropos.getSec3Titre()).isEqualTo(UPDATED_SEC_3_TITRE);
        assertThat(testPageAPropos.getSec3Img()).isEqualTo(UPDATED_SEC_3_IMG);
        assertThat(testPageAPropos.getSec3ImgContentType()).isEqualTo(UPDATED_SEC_3_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec3SousTitre()).isEqualTo(UPDATED_SEC_3_SOUS_TITRE);
        assertThat(testPageAPropos.getSec3Text()).isEqualTo(UPDATED_SEC_3_TEXT);
        assertThat(testPageAPropos.getSec4Img()).isEqualTo(UPDATED_SEC_4_IMG);
        assertThat(testPageAPropos.getSec4ImgContentType()).isEqualTo(UPDATED_SEC_4_IMG_CONTENT_TYPE);
        assertThat(testPageAPropos.getSec4Desc1()).isEqualTo(UPDATED_SEC_4_DESC_1);
        assertThat(testPageAPropos.getSec4Desc2()).isEqualTo(UPDATED_SEC_4_DESC_2);
        assertThat(testPageAPropos.getSec4Desc3()).isEqualTo(UPDATED_SEC_4_DESC_3);
        assertThat(testPageAPropos.getSec4Desc4()).isEqualTo(UPDATED_SEC_4_DESC_4);
        assertThat(testPageAPropos.getSec5Titre()).isEqualTo(UPDATED_SEC_5_TITRE);
    }

    @Test
    @Transactional
    void patchNonExistingPageAPropos() throws Exception {
        int databaseSizeBeforeUpdate = pageAProposRepository.findAll().size();
        pageAPropos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageAProposMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageAPropos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageAPropos))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageAPropos() throws Exception {
        int databaseSizeBeforeUpdate = pageAProposRepository.findAll().size();
        pageAPropos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageAProposMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageAPropos))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageAPropos() throws Exception {
        int databaseSizeBeforeUpdate = pageAProposRepository.findAll().size();
        pageAPropos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageAProposMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageAPropos))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageAPropos in the database
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageAPropos() throws Exception {
        // Initialize the database
        pageAProposRepository.saveAndFlush(pageAPropos);

        int databaseSizeBeforeDelete = pageAProposRepository.findAll().size();

        // Delete the pageAPropos
        restPageAProposMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageAPropos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageAPropos> pageAProposList = pageAProposRepository.findAll();
        assertThat(pageAProposList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
