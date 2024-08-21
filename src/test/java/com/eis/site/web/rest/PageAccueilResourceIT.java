package com.eis.site.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eis.site.IntegrationTest;
import com.eis.site.domain.PageAccueil;
import com.eis.site.repository.PageAccueilRepository;
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
 * Integration tests for the {@link PageAccueilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageAccueilResourceIT {

    private static final String DEFAULT_SEC_1_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_1_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_1_TEXTE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_1_TEXTE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_1_BOUTON = "AAAAAAAAAA";
    private static final String UPDATED_SEC_1_BOUTON = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_2_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_2_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_2_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_2_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_2_BOUTON = "AAAAAAAAAA";
    private static final String UPDATED_SEC_2_BOUTON = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_3_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_TITRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_3_BLOG_1_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_3_BLOG_1_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_3_BLOG_1_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_3_BLOG_1_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_3_BLOG_2_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_3_BLOG_2_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_3_BLOG_2_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_3_BLOG_2_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_3_BLOG_3_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_3_BLOG_3_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_3_BLOG_3_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_3_BLOG_3_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_3_BLOG_1_SOUS_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_BLOG_1_SOUS_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_3_BLOG_2_SOUS_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_BLOG_2_SOUS_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_3_BLOG_3_SOUS_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_BLOG_3_SOUS_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_3_BLOG_1_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_BLOG_1_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_3_BLOG_2_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_BLOG_2_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_3_BLOG_3_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_3_BLOG_3_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_4_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_4_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_TEXT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_4_BLOG_1_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_4_BLOG_1_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_4_BLOG_1_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_4_BLOG_1_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_4_BLOG_1_SOUS_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_BLOG_1_SOUS_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_4_BLOG_2_SOUS_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_BLOG_2_SOUS_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_4_BLOG_1_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_BLOG_1_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_4_BLOG_2_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_4_BLOG_2_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_5_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_5_TITRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_5_BLOG_1_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_1_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_1_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_1_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_2_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_2_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_2_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_2_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_3_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_3_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_3_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_3_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_4_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_4_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_4_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_4_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_5_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_5_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_5_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_5_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_6_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_6_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_6_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_6_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_7_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_7_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_7_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_7_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_1_SOUS_TITRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_1_SOUS_TITRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_2_SOUS_TITRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_2_SOUS_TITRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_3_SOUS_TITRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_3_SOUS_TITRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_4_SOUS_TITRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_4_SOUS_TITRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_5_SOUS_TITRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_5_SOUS_TITRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_6_SOUS_TITRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_6_SOUS_TITRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SEC_5_BLOG_7_SOUS_TITRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_5_BLOG_7_SOUS_TITRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_5_BLOG_1_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_5_BLOG_1_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_5_BLOG_2_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_5_BLOG_2_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_5_BLOG_3_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_5_BLOG_3_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_5_BLOG_4_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_5_BLOG_4_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_5_BLOG_5_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_5_BLOG_5_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_5_BLOG_6_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_5_BLOG_6_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_5_BLOG_7_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_5_BLOG_7_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_6_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_6_TITRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_6_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_6_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_6_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_6_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_6_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_6_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_7_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_7_TITRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_7_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_7_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_7_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_7_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_7_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_7_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_8_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_8_TITRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_8_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_8_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_8_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_8_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_8_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_8_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SEC_9_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_SEC_9_TITRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SEC_9_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SEC_9_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SEC_9_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SEC_9_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SEC_9_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEC_9_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/page-accueils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageAccueilRepository pageAccueilRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageAccueilMockMvc;

    private PageAccueil pageAccueil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageAccueil createEntity(EntityManager em) {
        PageAccueil pageAccueil = new PageAccueil()
            .sec1Titre(DEFAULT_SEC_1_TITRE)
            .sec1Texte(DEFAULT_SEC_1_TEXTE)
            .secImage(DEFAULT_SEC_IMAGE)
            .secImageContentType(DEFAULT_SEC_IMAGE_CONTENT_TYPE)
            .sec1Bouton(DEFAULT_SEC_1_BOUTON)
            .sec2Titre(DEFAULT_SEC_2_TITRE)
            .sec2Text(DEFAULT_SEC_2_TEXT)
            .sec2Bouton(DEFAULT_SEC_2_BOUTON)
            .sec3Titre(DEFAULT_SEC_3_TITRE)
            .sec3Blog1Img(DEFAULT_SEC_3_BLOG_1_IMG)
            .sec3Blog1ImgContentType(DEFAULT_SEC_3_BLOG_1_IMG_CONTENT_TYPE)
            .sec3Blog2Img(DEFAULT_SEC_3_BLOG_2_IMG)
            .sec3Blog2ImgContentType(DEFAULT_SEC_3_BLOG_2_IMG_CONTENT_TYPE)
            .sec3Blog3Img(DEFAULT_SEC_3_BLOG_3_IMG)
            .sec3Blog3ImgContentType(DEFAULT_SEC_3_BLOG_3_IMG_CONTENT_TYPE)
            .sec3Blog1SousTitre(DEFAULT_SEC_3_BLOG_1_SOUS_TITRE)
            .sec3Blog2SousTitre(DEFAULT_SEC_3_BLOG_2_SOUS_TITRE)
            .sec3Blog3SousTitre(DEFAULT_SEC_3_BLOG_3_SOUS_TITRE)
            .sec3Blog1Text(DEFAULT_SEC_3_BLOG_1_TEXT)
            .sec3Blog2Text(DEFAULT_SEC_3_BLOG_2_TEXT)
            .sec3Blog3Text(DEFAULT_SEC_3_BLOG_3_TEXT)
            .sec4Titre(DEFAULT_SEC_4_TITRE)
            .sec4Text(DEFAULT_SEC_4_TEXT)
            .sec4Blog1Img(DEFAULT_SEC_4_BLOG_1_IMG)
            .sec4Blog1ImgContentType(DEFAULT_SEC_4_BLOG_1_IMG_CONTENT_TYPE)
            .sec4Blog1SousTitre(DEFAULT_SEC_4_BLOG_1_SOUS_TITRE)
            .sec4Blog2SousTitre(DEFAULT_SEC_4_BLOG_2_SOUS_TITRE)
            .sec4Blog1Text(DEFAULT_SEC_4_BLOG_1_TEXT)
            .sec4Blog2Text(DEFAULT_SEC_4_BLOG_2_TEXT)
            .sec5Titre(DEFAULT_SEC_5_TITRE)
            .sec5Blog1Img(DEFAULT_SEC_5_BLOG_1_IMG)
            .sec5Blog1ImgContentType(DEFAULT_SEC_5_BLOG_1_IMG_CONTENT_TYPE)
            .sec5Blog2Img(DEFAULT_SEC_5_BLOG_2_IMG)
            .sec5Blog2ImgContentType(DEFAULT_SEC_5_BLOG_2_IMG_CONTENT_TYPE)
            .sec5Blog3Img(DEFAULT_SEC_5_BLOG_3_IMG)
            .sec5Blog3ImgContentType(DEFAULT_SEC_5_BLOG_3_IMG_CONTENT_TYPE)
            .sec5Blog4Img(DEFAULT_SEC_5_BLOG_4_IMG)
            .sec5Blog4ImgContentType(DEFAULT_SEC_5_BLOG_4_IMG_CONTENT_TYPE)
            .sec5Blog5Img(DEFAULT_SEC_5_BLOG_5_IMG)
            .sec5Blog5ImgContentType(DEFAULT_SEC_5_BLOG_5_IMG_CONTENT_TYPE)
            .sec5Blog6Img(DEFAULT_SEC_5_BLOG_6_IMG)
            .sec5Blog6ImgContentType(DEFAULT_SEC_5_BLOG_6_IMG_CONTENT_TYPE)
            .sec5Blog7Img(DEFAULT_SEC_5_BLOG_7_IMG)
            .sec5Blog7ImgContentType(DEFAULT_SEC_5_BLOG_7_IMG_CONTENT_TYPE)
            .sec5Blog1SousTitre(DEFAULT_SEC_5_BLOG_1_SOUS_TITRE)
            .sec5Blog1SousTitreContentType(DEFAULT_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog2SousTitre(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE)
            .sec5Blog2SousTitreContentType(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog3SousTitre(DEFAULT_SEC_5_BLOG_3_SOUS_TITRE)
            .sec5Blog3SousTitreContentType(DEFAULT_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog4SousTitre(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE)
            .sec5Blog4SousTitreContentType(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog5SousTitre(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE)
            .sec5Blog5SousTitreContentType(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog6SousTitre(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE)
            .sec5Blog6SousTitreContentType(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog7SousTitre(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE)
            .sec5Blog7SousTitreContentType(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog1Text(DEFAULT_SEC_5_BLOG_1_TEXT)
            .sec5Blog2Text(DEFAULT_SEC_5_BLOG_2_TEXT)
            .sec5Blog3Text(DEFAULT_SEC_5_BLOG_3_TEXT)
            .sec5Blog4Text(DEFAULT_SEC_5_BLOG_4_TEXT)
            .sec5Blog5Text(DEFAULT_SEC_5_BLOG_5_TEXT)
            .sec5Blog6Text(DEFAULT_SEC_5_BLOG_6_TEXT)
            .sec5Blog7Text(DEFAULT_SEC_5_BLOG_7_TEXT)
            .sec6Titre(DEFAULT_SEC_6_TITRE)
            .sec6Img(DEFAULT_SEC_6_IMG)
            .sec6ImgContentType(DEFAULT_SEC_6_IMG_CONTENT_TYPE)
            .sec6Text(DEFAULT_SEC_6_TEXT)
            .sec7Titre(DEFAULT_SEC_7_TITRE)
            .sec7Img(DEFAULT_SEC_7_IMG)
            .sec7ImgContentType(DEFAULT_SEC_7_IMG_CONTENT_TYPE)
            .sec7Text(DEFAULT_SEC_7_TEXT)
            .sec8Titre(DEFAULT_SEC_8_TITRE)
            .sec8Img(DEFAULT_SEC_8_IMG)
            .sec8ImgContentType(DEFAULT_SEC_8_IMG_CONTENT_TYPE)
            .sec8Text(DEFAULT_SEC_8_TEXT)
            .sec9Titre(DEFAULT_SEC_9_TITRE)
            .sec9Img(DEFAULT_SEC_9_IMG)
            .sec9ImgContentType(DEFAULT_SEC_9_IMG_CONTENT_TYPE)
            .sec9Text(DEFAULT_SEC_9_TEXT);
        return pageAccueil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageAccueil createUpdatedEntity(EntityManager em) {
        PageAccueil pageAccueil = new PageAccueil()
            .sec1Titre(UPDATED_SEC_1_TITRE)
            .sec1Texte(UPDATED_SEC_1_TEXTE)
            .secImage(UPDATED_SEC_IMAGE)
            .secImageContentType(UPDATED_SEC_IMAGE_CONTENT_TYPE)
            .sec1Bouton(UPDATED_SEC_1_BOUTON)
            .sec2Titre(UPDATED_SEC_2_TITRE)
            .sec2Text(UPDATED_SEC_2_TEXT)
            .sec2Bouton(UPDATED_SEC_2_BOUTON)
            .sec3Titre(UPDATED_SEC_3_TITRE)
            .sec3Blog1Img(UPDATED_SEC_3_BLOG_1_IMG)
            .sec3Blog1ImgContentType(UPDATED_SEC_3_BLOG_1_IMG_CONTENT_TYPE)
            .sec3Blog2Img(UPDATED_SEC_3_BLOG_2_IMG)
            .sec3Blog2ImgContentType(UPDATED_SEC_3_BLOG_2_IMG_CONTENT_TYPE)
            .sec3Blog3Img(UPDATED_SEC_3_BLOG_3_IMG)
            .sec3Blog3ImgContentType(UPDATED_SEC_3_BLOG_3_IMG_CONTENT_TYPE)
            .sec3Blog1SousTitre(UPDATED_SEC_3_BLOG_1_SOUS_TITRE)
            .sec3Blog2SousTitre(UPDATED_SEC_3_BLOG_2_SOUS_TITRE)
            .sec3Blog3SousTitre(UPDATED_SEC_3_BLOG_3_SOUS_TITRE)
            .sec3Blog1Text(UPDATED_SEC_3_BLOG_1_TEXT)
            .sec3Blog2Text(UPDATED_SEC_3_BLOG_2_TEXT)
            .sec3Blog3Text(UPDATED_SEC_3_BLOG_3_TEXT)
            .sec4Titre(UPDATED_SEC_4_TITRE)
            .sec4Text(UPDATED_SEC_4_TEXT)
            .sec4Blog1Img(UPDATED_SEC_4_BLOG_1_IMG)
            .sec4Blog1ImgContentType(UPDATED_SEC_4_BLOG_1_IMG_CONTENT_TYPE)
            .sec4Blog1SousTitre(UPDATED_SEC_4_BLOG_1_SOUS_TITRE)
            .sec4Blog2SousTitre(UPDATED_SEC_4_BLOG_2_SOUS_TITRE)
            .sec4Blog1Text(UPDATED_SEC_4_BLOG_1_TEXT)
            .sec4Blog2Text(UPDATED_SEC_4_BLOG_2_TEXT)
            .sec5Titre(UPDATED_SEC_5_TITRE)
            .sec5Blog1Img(UPDATED_SEC_5_BLOG_1_IMG)
            .sec5Blog1ImgContentType(UPDATED_SEC_5_BLOG_1_IMG_CONTENT_TYPE)
            .sec5Blog2Img(UPDATED_SEC_5_BLOG_2_IMG)
            .sec5Blog2ImgContentType(UPDATED_SEC_5_BLOG_2_IMG_CONTENT_TYPE)
            .sec5Blog3Img(UPDATED_SEC_5_BLOG_3_IMG)
            .sec5Blog3ImgContentType(UPDATED_SEC_5_BLOG_3_IMG_CONTENT_TYPE)
            .sec5Blog4Img(UPDATED_SEC_5_BLOG_4_IMG)
            .sec5Blog4ImgContentType(UPDATED_SEC_5_BLOG_4_IMG_CONTENT_TYPE)
            .sec5Blog5Img(UPDATED_SEC_5_BLOG_5_IMG)
            .sec5Blog5ImgContentType(UPDATED_SEC_5_BLOG_5_IMG_CONTENT_TYPE)
            .sec5Blog6Img(UPDATED_SEC_5_BLOG_6_IMG)
            .sec5Blog6ImgContentType(UPDATED_SEC_5_BLOG_6_IMG_CONTENT_TYPE)
            .sec5Blog7Img(UPDATED_SEC_5_BLOG_7_IMG)
            .sec5Blog7ImgContentType(UPDATED_SEC_5_BLOG_7_IMG_CONTENT_TYPE)
            .sec5Blog1SousTitre(UPDATED_SEC_5_BLOG_1_SOUS_TITRE)
            .sec5Blog1SousTitreContentType(UPDATED_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog2SousTitre(UPDATED_SEC_5_BLOG_2_SOUS_TITRE)
            .sec5Blog2SousTitreContentType(UPDATED_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog3SousTitre(UPDATED_SEC_5_BLOG_3_SOUS_TITRE)
            .sec5Blog3SousTitreContentType(UPDATED_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog4SousTitre(UPDATED_SEC_5_BLOG_4_SOUS_TITRE)
            .sec5Blog4SousTitreContentType(UPDATED_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog5SousTitre(UPDATED_SEC_5_BLOG_5_SOUS_TITRE)
            .sec5Blog5SousTitreContentType(UPDATED_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog6SousTitre(UPDATED_SEC_5_BLOG_6_SOUS_TITRE)
            .sec5Blog6SousTitreContentType(UPDATED_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog7SousTitre(UPDATED_SEC_5_BLOG_7_SOUS_TITRE)
            .sec5Blog7SousTitreContentType(UPDATED_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog1Text(UPDATED_SEC_5_BLOG_1_TEXT)
            .sec5Blog2Text(UPDATED_SEC_5_BLOG_2_TEXT)
            .sec5Blog3Text(UPDATED_SEC_5_BLOG_3_TEXT)
            .sec5Blog4Text(UPDATED_SEC_5_BLOG_4_TEXT)
            .sec5Blog5Text(UPDATED_SEC_5_BLOG_5_TEXT)
            .sec5Blog6Text(UPDATED_SEC_5_BLOG_6_TEXT)
            .sec5Blog7Text(UPDATED_SEC_5_BLOG_7_TEXT)
            .sec6Titre(UPDATED_SEC_6_TITRE)
            .sec6Img(UPDATED_SEC_6_IMG)
            .sec6ImgContentType(UPDATED_SEC_6_IMG_CONTENT_TYPE)
            .sec6Text(UPDATED_SEC_6_TEXT)
            .sec7Titre(UPDATED_SEC_7_TITRE)
            .sec7Img(UPDATED_SEC_7_IMG)
            .sec7ImgContentType(UPDATED_SEC_7_IMG_CONTENT_TYPE)
            .sec7Text(UPDATED_SEC_7_TEXT)
            .sec8Titre(UPDATED_SEC_8_TITRE)
            .sec8Img(UPDATED_SEC_8_IMG)
            .sec8ImgContentType(UPDATED_SEC_8_IMG_CONTENT_TYPE)
            .sec8Text(UPDATED_SEC_8_TEXT)
            .sec9Titre(UPDATED_SEC_9_TITRE)
            .sec9Img(UPDATED_SEC_9_IMG)
            .sec9ImgContentType(UPDATED_SEC_9_IMG_CONTENT_TYPE)
            .sec9Text(UPDATED_SEC_9_TEXT);
        return pageAccueil;
    }

    @BeforeEach
    public void initTest() {
        pageAccueil = createEntity(em);
    }

    @Test
    @Transactional
    void createPageAccueil() throws Exception {
        int databaseSizeBeforeCreate = pageAccueilRepository.findAll().size();
        // Create the PageAccueil
        restPageAccueilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageAccueil)))
            .andExpect(status().isCreated());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeCreate + 1);
        PageAccueil testPageAccueil = pageAccueilList.get(pageAccueilList.size() - 1);
        assertThat(testPageAccueil.getSec1Titre()).isEqualTo(DEFAULT_SEC_1_TITRE);
        assertThat(testPageAccueil.getSec1Texte()).isEqualTo(DEFAULT_SEC_1_TEXTE);
        assertThat(testPageAccueil.getSecImage()).isEqualTo(DEFAULT_SEC_IMAGE);
        assertThat(testPageAccueil.getSecImageContentType()).isEqualTo(DEFAULT_SEC_IMAGE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec1Bouton()).isEqualTo(DEFAULT_SEC_1_BOUTON);
        assertThat(testPageAccueil.getSec2Titre()).isEqualTo(DEFAULT_SEC_2_TITRE);
        assertThat(testPageAccueil.getSec2Text()).isEqualTo(DEFAULT_SEC_2_TEXT);
        assertThat(testPageAccueil.getSec2Bouton()).isEqualTo(DEFAULT_SEC_2_BOUTON);
        assertThat(testPageAccueil.getSec3Titre()).isEqualTo(DEFAULT_SEC_3_TITRE);
        assertThat(testPageAccueil.getSec3Blog1Img()).isEqualTo(DEFAULT_SEC_3_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec3Blog1ImgContentType()).isEqualTo(DEFAULT_SEC_3_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog2Img()).isEqualTo(DEFAULT_SEC_3_BLOG_2_IMG);
        assertThat(testPageAccueil.getSec3Blog2ImgContentType()).isEqualTo(DEFAULT_SEC_3_BLOG_2_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog3Img()).isEqualTo(DEFAULT_SEC_3_BLOG_3_IMG);
        assertThat(testPageAccueil.getSec3Blog3ImgContentType()).isEqualTo(DEFAULT_SEC_3_BLOG_3_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog1SousTitre()).isEqualTo(DEFAULT_SEC_3_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog2SousTitre()).isEqualTo(DEFAULT_SEC_3_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog3SousTitre()).isEqualTo(DEFAULT_SEC_3_BLOG_3_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog1Text()).isEqualTo(DEFAULT_SEC_3_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec3Blog2Text()).isEqualTo(DEFAULT_SEC_3_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec3Blog3Text()).isEqualTo(DEFAULT_SEC_3_BLOG_3_TEXT);
        assertThat(testPageAccueil.getSec4Titre()).isEqualTo(DEFAULT_SEC_4_TITRE);
        assertThat(testPageAccueil.getSec4Text()).isEqualTo(DEFAULT_SEC_4_TEXT);
        assertThat(testPageAccueil.getSec4Blog1Img()).isEqualTo(DEFAULT_SEC_4_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec4Blog1ImgContentType()).isEqualTo(DEFAULT_SEC_4_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec4Blog1SousTitre()).isEqualTo(DEFAULT_SEC_4_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec4Blog2SousTitre()).isEqualTo(DEFAULT_SEC_4_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec4Blog1Text()).isEqualTo(DEFAULT_SEC_4_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec4Blog2Text()).isEqualTo(DEFAULT_SEC_4_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec5Titre()).isEqualTo(DEFAULT_SEC_5_TITRE);
        assertThat(testPageAccueil.getSec5Blog1Img()).isEqualTo(DEFAULT_SEC_5_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec5Blog1ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog2Img()).isEqualTo(DEFAULT_SEC_5_BLOG_2_IMG);
        assertThat(testPageAccueil.getSec5Blog2ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_2_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog3Img()).isEqualTo(DEFAULT_SEC_5_BLOG_3_IMG);
        assertThat(testPageAccueil.getSec5Blog3ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_3_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog4Img()).isEqualTo(DEFAULT_SEC_5_BLOG_4_IMG);
        assertThat(testPageAccueil.getSec5Blog4ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_4_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog5Img()).isEqualTo(DEFAULT_SEC_5_BLOG_5_IMG);
        assertThat(testPageAccueil.getSec5Blog5ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_5_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog6Img()).isEqualTo(DEFAULT_SEC_5_BLOG_6_IMG);
        assertThat(testPageAccueil.getSec5Blog6ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_6_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog7Img()).isEqualTo(DEFAULT_SEC_5_BLOG_7_IMG);
        assertThat(testPageAccueil.getSec5Blog7ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_7_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog1SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog1SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog2SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog2SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog3SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_3_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog3SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog4SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog4SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog5SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog5SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog6SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog6SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog7SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog7SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog1Text()).isEqualTo(DEFAULT_SEC_5_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec5Blog2Text()).isEqualTo(DEFAULT_SEC_5_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec5Blog3Text()).isEqualTo(DEFAULT_SEC_5_BLOG_3_TEXT);
        assertThat(testPageAccueil.getSec5Blog4Text()).isEqualTo(DEFAULT_SEC_5_BLOG_4_TEXT);
        assertThat(testPageAccueil.getSec5Blog5Text()).isEqualTo(DEFAULT_SEC_5_BLOG_5_TEXT);
        assertThat(testPageAccueil.getSec5Blog6Text()).isEqualTo(DEFAULT_SEC_5_BLOG_6_TEXT);
        assertThat(testPageAccueil.getSec5Blog7Text()).isEqualTo(DEFAULT_SEC_5_BLOG_7_TEXT);
        assertThat(testPageAccueil.getSec6Titre()).isEqualTo(DEFAULT_SEC_6_TITRE);
        assertThat(testPageAccueil.getSec6Img()).isEqualTo(DEFAULT_SEC_6_IMG);
        assertThat(testPageAccueil.getSec6ImgContentType()).isEqualTo(DEFAULT_SEC_6_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec6Text()).isEqualTo(DEFAULT_SEC_6_TEXT);
        assertThat(testPageAccueil.getSec7Titre()).isEqualTo(DEFAULT_SEC_7_TITRE);
        assertThat(testPageAccueil.getSec7Img()).isEqualTo(DEFAULT_SEC_7_IMG);
        assertThat(testPageAccueil.getSec7ImgContentType()).isEqualTo(DEFAULT_SEC_7_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec7Text()).isEqualTo(DEFAULT_SEC_7_TEXT);
        assertThat(testPageAccueil.getSec8Titre()).isEqualTo(DEFAULT_SEC_8_TITRE);
        assertThat(testPageAccueil.getSec8Img()).isEqualTo(DEFAULT_SEC_8_IMG);
        assertThat(testPageAccueil.getSec8ImgContentType()).isEqualTo(DEFAULT_SEC_8_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec8Text()).isEqualTo(DEFAULT_SEC_8_TEXT);
        assertThat(testPageAccueil.getSec9Titre()).isEqualTo(DEFAULT_SEC_9_TITRE);
        assertThat(testPageAccueil.getSec9Img()).isEqualTo(DEFAULT_SEC_9_IMG);
        assertThat(testPageAccueil.getSec9ImgContentType()).isEqualTo(DEFAULT_SEC_9_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec9Text()).isEqualTo(DEFAULT_SEC_9_TEXT);
    }

    @Test
    @Transactional
    void createPageAccueilWithExistingId() throws Exception {
        // Create the PageAccueil with an existing ID
        pageAccueil.setId(1L);

        int databaseSizeBeforeCreate = pageAccueilRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageAccueilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageAccueil)))
            .andExpect(status().isBadRequest());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageAccueils() throws Exception {
        // Initialize the database
        pageAccueilRepository.saveAndFlush(pageAccueil);

        // Get all the pageAccueilList
        restPageAccueilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageAccueil.getId().intValue())))
            .andExpect(jsonPath("$.[*].sec1Titre").value(hasItem(DEFAULT_SEC_1_TITRE)))
            .andExpect(jsonPath("$.[*].sec1Texte").value(hasItem(DEFAULT_SEC_1_TEXTE)))
            .andExpect(jsonPath("$.[*].secImageContentType").value(hasItem(DEFAULT_SEC_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].secImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_IMAGE))))
            .andExpect(jsonPath("$.[*].sec1Bouton").value(hasItem(DEFAULT_SEC_1_BOUTON)))
            .andExpect(jsonPath("$.[*].sec2Titre").value(hasItem(DEFAULT_SEC_2_TITRE)))
            .andExpect(jsonPath("$.[*].sec2Text").value(hasItem(DEFAULT_SEC_2_TEXT)))
            .andExpect(jsonPath("$.[*].sec2Bouton").value(hasItem(DEFAULT_SEC_2_BOUTON)))
            .andExpect(jsonPath("$.[*].sec3Titre").value(hasItem(DEFAULT_SEC_3_TITRE)))
            .andExpect(jsonPath("$.[*].sec3Blog1ImgContentType").value(hasItem(DEFAULT_SEC_3_BLOG_1_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec3Blog1Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_3_BLOG_1_IMG))))
            .andExpect(jsonPath("$.[*].sec3Blog2ImgContentType").value(hasItem(DEFAULT_SEC_3_BLOG_2_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec3Blog2Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_3_BLOG_2_IMG))))
            .andExpect(jsonPath("$.[*].sec3Blog3ImgContentType").value(hasItem(DEFAULT_SEC_3_BLOG_3_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec3Blog3Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_3_BLOG_3_IMG))))
            .andExpect(jsonPath("$.[*].sec3Blog1SousTitre").value(hasItem(DEFAULT_SEC_3_BLOG_1_SOUS_TITRE)))
            .andExpect(jsonPath("$.[*].sec3Blog2SousTitre").value(hasItem(DEFAULT_SEC_3_BLOG_2_SOUS_TITRE)))
            .andExpect(jsonPath("$.[*].sec3Blog3SousTitre").value(hasItem(DEFAULT_SEC_3_BLOG_3_SOUS_TITRE)))
            .andExpect(jsonPath("$.[*].sec3Blog1Text").value(hasItem(DEFAULT_SEC_3_BLOG_1_TEXT)))
            .andExpect(jsonPath("$.[*].sec3Blog2Text").value(hasItem(DEFAULT_SEC_3_BLOG_2_TEXT)))
            .andExpect(jsonPath("$.[*].sec3Blog3Text").value(hasItem(DEFAULT_SEC_3_BLOG_3_TEXT)))
            .andExpect(jsonPath("$.[*].sec4Titre").value(hasItem(DEFAULT_SEC_4_TITRE)))
            .andExpect(jsonPath("$.[*].sec4Text").value(hasItem(DEFAULT_SEC_4_TEXT)))
            .andExpect(jsonPath("$.[*].sec4Blog1ImgContentType").value(hasItem(DEFAULT_SEC_4_BLOG_1_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec4Blog1Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_4_BLOG_1_IMG))))
            .andExpect(jsonPath("$.[*].sec4Blog1SousTitre").value(hasItem(DEFAULT_SEC_4_BLOG_1_SOUS_TITRE)))
            .andExpect(jsonPath("$.[*].sec4Blog2SousTitre").value(hasItem(DEFAULT_SEC_4_BLOG_2_SOUS_TITRE)))
            .andExpect(jsonPath("$.[*].sec4Blog1Text").value(hasItem(DEFAULT_SEC_4_BLOG_1_TEXT)))
            .andExpect(jsonPath("$.[*].sec4Blog2Text").value(hasItem(DEFAULT_SEC_4_BLOG_2_TEXT)))
            .andExpect(jsonPath("$.[*].sec5Titre").value(hasItem(DEFAULT_SEC_5_TITRE)))
            .andExpect(jsonPath("$.[*].sec5Blog1ImgContentType").value(hasItem(DEFAULT_SEC_5_BLOG_1_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog1Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_1_IMG))))
            .andExpect(jsonPath("$.[*].sec5Blog2ImgContentType").value(hasItem(DEFAULT_SEC_5_BLOG_2_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog2Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_2_IMG))))
            .andExpect(jsonPath("$.[*].sec5Blog3ImgContentType").value(hasItem(DEFAULT_SEC_5_BLOG_3_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog3Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_3_IMG))))
            .andExpect(jsonPath("$.[*].sec5Blog4ImgContentType").value(hasItem(DEFAULT_SEC_5_BLOG_4_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog4Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_4_IMG))))
            .andExpect(jsonPath("$.[*].sec5Blog5ImgContentType").value(hasItem(DEFAULT_SEC_5_BLOG_5_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog5Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_5_IMG))))
            .andExpect(jsonPath("$.[*].sec5Blog6ImgContentType").value(hasItem(DEFAULT_SEC_5_BLOG_6_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog6Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_6_IMG))))
            .andExpect(jsonPath("$.[*].sec5Blog7ImgContentType").value(hasItem(DEFAULT_SEC_5_BLOG_7_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog7Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_7_IMG))))
            .andExpect(jsonPath("$.[*].sec5Blog1SousTitreContentType").value(hasItem(DEFAULT_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog1SousTitre").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_1_SOUS_TITRE))))
            .andExpect(jsonPath("$.[*].sec5Blog2SousTitreContentType").value(hasItem(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog2SousTitre").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE))))
            .andExpect(jsonPath("$.[*].sec5Blog3SousTitreContentType").value(hasItem(DEFAULT_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog3SousTitre").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_3_SOUS_TITRE))))
            .andExpect(jsonPath("$.[*].sec5Blog4SousTitreContentType").value(hasItem(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog4SousTitre").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE))))
            .andExpect(jsonPath("$.[*].sec5Blog5SousTitreContentType").value(hasItem(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog5SousTitre").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE))))
            .andExpect(jsonPath("$.[*].sec5Blog6SousTitreContentType").value(hasItem(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog6SousTitre").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE))))
            .andExpect(jsonPath("$.[*].sec5Blog7SousTitreContentType").value(hasItem(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec5Blog7SousTitre").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE))))
            .andExpect(jsonPath("$.[*].sec5Blog1Text").value(hasItem(DEFAULT_SEC_5_BLOG_1_TEXT)))
            .andExpect(jsonPath("$.[*].sec5Blog2Text").value(hasItem(DEFAULT_SEC_5_BLOG_2_TEXT)))
            .andExpect(jsonPath("$.[*].sec5Blog3Text").value(hasItem(DEFAULT_SEC_5_BLOG_3_TEXT)))
            .andExpect(jsonPath("$.[*].sec5Blog4Text").value(hasItem(DEFAULT_SEC_5_BLOG_4_TEXT)))
            .andExpect(jsonPath("$.[*].sec5Blog5Text").value(hasItem(DEFAULT_SEC_5_BLOG_5_TEXT)))
            .andExpect(jsonPath("$.[*].sec5Blog6Text").value(hasItem(DEFAULT_SEC_5_BLOG_6_TEXT)))
            .andExpect(jsonPath("$.[*].sec5Blog7Text").value(hasItem(DEFAULT_SEC_5_BLOG_7_TEXT)))
            .andExpect(jsonPath("$.[*].sec6Titre").value(hasItem(DEFAULT_SEC_6_TITRE)))
            .andExpect(jsonPath("$.[*].sec6ImgContentType").value(hasItem(DEFAULT_SEC_6_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec6Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_6_IMG))))
            .andExpect(jsonPath("$.[*].sec6Text").value(hasItem(DEFAULT_SEC_6_TEXT)))
            .andExpect(jsonPath("$.[*].sec7Titre").value(hasItem(DEFAULT_SEC_7_TITRE)))
            .andExpect(jsonPath("$.[*].sec7ImgContentType").value(hasItem(DEFAULT_SEC_7_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec7Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_7_IMG))))
            .andExpect(jsonPath("$.[*].sec7Text").value(hasItem(DEFAULT_SEC_7_TEXT)))
            .andExpect(jsonPath("$.[*].sec8Titre").value(hasItem(DEFAULT_SEC_8_TITRE)))
            .andExpect(jsonPath("$.[*].sec8ImgContentType").value(hasItem(DEFAULT_SEC_8_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec8Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_8_IMG))))
            .andExpect(jsonPath("$.[*].sec8Text").value(hasItem(DEFAULT_SEC_8_TEXT)))
            .andExpect(jsonPath("$.[*].sec9Titre").value(hasItem(DEFAULT_SEC_9_TITRE)))
            .andExpect(jsonPath("$.[*].sec9ImgContentType").value(hasItem(DEFAULT_SEC_9_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sec9Img").value(hasItem(Base64Utils.encodeToString(DEFAULT_SEC_9_IMG))))
            .andExpect(jsonPath("$.[*].sec9Text").value(hasItem(DEFAULT_SEC_9_TEXT)));
    }

    @Test
    @Transactional
    void getPageAccueil() throws Exception {
        // Initialize the database
        pageAccueilRepository.saveAndFlush(pageAccueil);

        // Get the pageAccueil
        restPageAccueilMockMvc
            .perform(get(ENTITY_API_URL_ID, pageAccueil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageAccueil.getId().intValue()))
            .andExpect(jsonPath("$.sec1Titre").value(DEFAULT_SEC_1_TITRE))
            .andExpect(jsonPath("$.sec1Texte").value(DEFAULT_SEC_1_TEXTE))
            .andExpect(jsonPath("$.secImageContentType").value(DEFAULT_SEC_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.secImage").value(Base64Utils.encodeToString(DEFAULT_SEC_IMAGE)))
            .andExpect(jsonPath("$.sec1Bouton").value(DEFAULT_SEC_1_BOUTON))
            .andExpect(jsonPath("$.sec2Titre").value(DEFAULT_SEC_2_TITRE))
            .andExpect(jsonPath("$.sec2Text").value(DEFAULT_SEC_2_TEXT))
            .andExpect(jsonPath("$.sec2Bouton").value(DEFAULT_SEC_2_BOUTON))
            .andExpect(jsonPath("$.sec3Titre").value(DEFAULT_SEC_3_TITRE))
            .andExpect(jsonPath("$.sec3Blog1ImgContentType").value(DEFAULT_SEC_3_BLOG_1_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec3Blog1Img").value(Base64Utils.encodeToString(DEFAULT_SEC_3_BLOG_1_IMG)))
            .andExpect(jsonPath("$.sec3Blog2ImgContentType").value(DEFAULT_SEC_3_BLOG_2_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec3Blog2Img").value(Base64Utils.encodeToString(DEFAULT_SEC_3_BLOG_2_IMG)))
            .andExpect(jsonPath("$.sec3Blog3ImgContentType").value(DEFAULT_SEC_3_BLOG_3_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec3Blog3Img").value(Base64Utils.encodeToString(DEFAULT_SEC_3_BLOG_3_IMG)))
            .andExpect(jsonPath("$.sec3Blog1SousTitre").value(DEFAULT_SEC_3_BLOG_1_SOUS_TITRE))
            .andExpect(jsonPath("$.sec3Blog2SousTitre").value(DEFAULT_SEC_3_BLOG_2_SOUS_TITRE))
            .andExpect(jsonPath("$.sec3Blog3SousTitre").value(DEFAULT_SEC_3_BLOG_3_SOUS_TITRE))
            .andExpect(jsonPath("$.sec3Blog1Text").value(DEFAULT_SEC_3_BLOG_1_TEXT))
            .andExpect(jsonPath("$.sec3Blog2Text").value(DEFAULT_SEC_3_BLOG_2_TEXT))
            .andExpect(jsonPath("$.sec3Blog3Text").value(DEFAULT_SEC_3_BLOG_3_TEXT))
            .andExpect(jsonPath("$.sec4Titre").value(DEFAULT_SEC_4_TITRE))
            .andExpect(jsonPath("$.sec4Text").value(DEFAULT_SEC_4_TEXT))
            .andExpect(jsonPath("$.sec4Blog1ImgContentType").value(DEFAULT_SEC_4_BLOG_1_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec4Blog1Img").value(Base64Utils.encodeToString(DEFAULT_SEC_4_BLOG_1_IMG)))
            .andExpect(jsonPath("$.sec4Blog1SousTitre").value(DEFAULT_SEC_4_BLOG_1_SOUS_TITRE))
            .andExpect(jsonPath("$.sec4Blog2SousTitre").value(DEFAULT_SEC_4_BLOG_2_SOUS_TITRE))
            .andExpect(jsonPath("$.sec4Blog1Text").value(DEFAULT_SEC_4_BLOG_1_TEXT))
            .andExpect(jsonPath("$.sec4Blog2Text").value(DEFAULT_SEC_4_BLOG_2_TEXT))
            .andExpect(jsonPath("$.sec5Titre").value(DEFAULT_SEC_5_TITRE))
            .andExpect(jsonPath("$.sec5Blog1ImgContentType").value(DEFAULT_SEC_5_BLOG_1_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog1Img").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_1_IMG)))
            .andExpect(jsonPath("$.sec5Blog2ImgContentType").value(DEFAULT_SEC_5_BLOG_2_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog2Img").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_2_IMG)))
            .andExpect(jsonPath("$.sec5Blog3ImgContentType").value(DEFAULT_SEC_5_BLOG_3_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog3Img").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_3_IMG)))
            .andExpect(jsonPath("$.sec5Blog4ImgContentType").value(DEFAULT_SEC_5_BLOG_4_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog4Img").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_4_IMG)))
            .andExpect(jsonPath("$.sec5Blog5ImgContentType").value(DEFAULT_SEC_5_BLOG_5_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog5Img").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_5_IMG)))
            .andExpect(jsonPath("$.sec5Blog6ImgContentType").value(DEFAULT_SEC_5_BLOG_6_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog6Img").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_6_IMG)))
            .andExpect(jsonPath("$.sec5Blog7ImgContentType").value(DEFAULT_SEC_5_BLOG_7_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog7Img").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_7_IMG)))
            .andExpect(jsonPath("$.sec5Blog1SousTitreContentType").value(DEFAULT_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog1SousTitre").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_1_SOUS_TITRE)))
            .andExpect(jsonPath("$.sec5Blog2SousTitreContentType").value(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog2SousTitre").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE)))
            .andExpect(jsonPath("$.sec5Blog3SousTitreContentType").value(DEFAULT_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog3SousTitre").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_3_SOUS_TITRE)))
            .andExpect(jsonPath("$.sec5Blog4SousTitreContentType").value(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog4SousTitre").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE)))
            .andExpect(jsonPath("$.sec5Blog5SousTitreContentType").value(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog5SousTitre").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE)))
            .andExpect(jsonPath("$.sec5Blog6SousTitreContentType").value(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog6SousTitre").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE)))
            .andExpect(jsonPath("$.sec5Blog7SousTitreContentType").value(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec5Blog7SousTitre").value(Base64Utils.encodeToString(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE)))
            .andExpect(jsonPath("$.sec5Blog1Text").value(DEFAULT_SEC_5_BLOG_1_TEXT))
            .andExpect(jsonPath("$.sec5Blog2Text").value(DEFAULT_SEC_5_BLOG_2_TEXT))
            .andExpect(jsonPath("$.sec5Blog3Text").value(DEFAULT_SEC_5_BLOG_3_TEXT))
            .andExpect(jsonPath("$.sec5Blog4Text").value(DEFAULT_SEC_5_BLOG_4_TEXT))
            .andExpect(jsonPath("$.sec5Blog5Text").value(DEFAULT_SEC_5_BLOG_5_TEXT))
            .andExpect(jsonPath("$.sec5Blog6Text").value(DEFAULT_SEC_5_BLOG_6_TEXT))
            .andExpect(jsonPath("$.sec5Blog7Text").value(DEFAULT_SEC_5_BLOG_7_TEXT))
            .andExpect(jsonPath("$.sec6Titre").value(DEFAULT_SEC_6_TITRE))
            .andExpect(jsonPath("$.sec6ImgContentType").value(DEFAULT_SEC_6_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec6Img").value(Base64Utils.encodeToString(DEFAULT_SEC_6_IMG)))
            .andExpect(jsonPath("$.sec6Text").value(DEFAULT_SEC_6_TEXT))
            .andExpect(jsonPath("$.sec7Titre").value(DEFAULT_SEC_7_TITRE))
            .andExpect(jsonPath("$.sec7ImgContentType").value(DEFAULT_SEC_7_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec7Img").value(Base64Utils.encodeToString(DEFAULT_SEC_7_IMG)))
            .andExpect(jsonPath("$.sec7Text").value(DEFAULT_SEC_7_TEXT))
            .andExpect(jsonPath("$.sec8Titre").value(DEFAULT_SEC_8_TITRE))
            .andExpect(jsonPath("$.sec8ImgContentType").value(DEFAULT_SEC_8_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec8Img").value(Base64Utils.encodeToString(DEFAULT_SEC_8_IMG)))
            .andExpect(jsonPath("$.sec8Text").value(DEFAULT_SEC_8_TEXT))
            .andExpect(jsonPath("$.sec9Titre").value(DEFAULT_SEC_9_TITRE))
            .andExpect(jsonPath("$.sec9ImgContentType").value(DEFAULT_SEC_9_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.sec9Img").value(Base64Utils.encodeToString(DEFAULT_SEC_9_IMG)))
            .andExpect(jsonPath("$.sec9Text").value(DEFAULT_SEC_9_TEXT));
    }

    @Test
    @Transactional
    void getNonExistingPageAccueil() throws Exception {
        // Get the pageAccueil
        restPageAccueilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPageAccueil() throws Exception {
        // Initialize the database
        pageAccueilRepository.saveAndFlush(pageAccueil);

        int databaseSizeBeforeUpdate = pageAccueilRepository.findAll().size();

        // Update the pageAccueil
        PageAccueil updatedPageAccueil = pageAccueilRepository.findById(pageAccueil.getId()).get();
        // Disconnect from session so that the updates on updatedPageAccueil are not directly saved in db
        em.detach(updatedPageAccueil);
        updatedPageAccueil
            .sec1Titre(UPDATED_SEC_1_TITRE)
            .sec1Texte(UPDATED_SEC_1_TEXTE)
            .secImage(UPDATED_SEC_IMAGE)
            .secImageContentType(UPDATED_SEC_IMAGE_CONTENT_TYPE)
            .sec1Bouton(UPDATED_SEC_1_BOUTON)
            .sec2Titre(UPDATED_SEC_2_TITRE)
            .sec2Text(UPDATED_SEC_2_TEXT)
            .sec2Bouton(UPDATED_SEC_2_BOUTON)
            .sec3Titre(UPDATED_SEC_3_TITRE)
            .sec3Blog1Img(UPDATED_SEC_3_BLOG_1_IMG)
            .sec3Blog1ImgContentType(UPDATED_SEC_3_BLOG_1_IMG_CONTENT_TYPE)
            .sec3Blog2Img(UPDATED_SEC_3_BLOG_2_IMG)
            .sec3Blog2ImgContentType(UPDATED_SEC_3_BLOG_2_IMG_CONTENT_TYPE)
            .sec3Blog3Img(UPDATED_SEC_3_BLOG_3_IMG)
            .sec3Blog3ImgContentType(UPDATED_SEC_3_BLOG_3_IMG_CONTENT_TYPE)
            .sec3Blog1SousTitre(UPDATED_SEC_3_BLOG_1_SOUS_TITRE)
            .sec3Blog2SousTitre(UPDATED_SEC_3_BLOG_2_SOUS_TITRE)
            .sec3Blog3SousTitre(UPDATED_SEC_3_BLOG_3_SOUS_TITRE)
            .sec3Blog1Text(UPDATED_SEC_3_BLOG_1_TEXT)
            .sec3Blog2Text(UPDATED_SEC_3_BLOG_2_TEXT)
            .sec3Blog3Text(UPDATED_SEC_3_BLOG_3_TEXT)
            .sec4Titre(UPDATED_SEC_4_TITRE)
            .sec4Text(UPDATED_SEC_4_TEXT)
            .sec4Blog1Img(UPDATED_SEC_4_BLOG_1_IMG)
            .sec4Blog1ImgContentType(UPDATED_SEC_4_BLOG_1_IMG_CONTENT_TYPE)
            .sec4Blog1SousTitre(UPDATED_SEC_4_BLOG_1_SOUS_TITRE)
            .sec4Blog2SousTitre(UPDATED_SEC_4_BLOG_2_SOUS_TITRE)
            .sec4Blog1Text(UPDATED_SEC_4_BLOG_1_TEXT)
            .sec4Blog2Text(UPDATED_SEC_4_BLOG_2_TEXT)
            .sec5Titre(UPDATED_SEC_5_TITRE)
            .sec5Blog1Img(UPDATED_SEC_5_BLOG_1_IMG)
            .sec5Blog1ImgContentType(UPDATED_SEC_5_BLOG_1_IMG_CONTENT_TYPE)
            .sec5Blog2Img(UPDATED_SEC_5_BLOG_2_IMG)
            .sec5Blog2ImgContentType(UPDATED_SEC_5_BLOG_2_IMG_CONTENT_TYPE)
            .sec5Blog3Img(UPDATED_SEC_5_BLOG_3_IMG)
            .sec5Blog3ImgContentType(UPDATED_SEC_5_BLOG_3_IMG_CONTENT_TYPE)
            .sec5Blog4Img(UPDATED_SEC_5_BLOG_4_IMG)
            .sec5Blog4ImgContentType(UPDATED_SEC_5_BLOG_4_IMG_CONTENT_TYPE)
            .sec5Blog5Img(UPDATED_SEC_5_BLOG_5_IMG)
            .sec5Blog5ImgContentType(UPDATED_SEC_5_BLOG_5_IMG_CONTENT_TYPE)
            .sec5Blog6Img(UPDATED_SEC_5_BLOG_6_IMG)
            .sec5Blog6ImgContentType(UPDATED_SEC_5_BLOG_6_IMG_CONTENT_TYPE)
            .sec5Blog7Img(UPDATED_SEC_5_BLOG_7_IMG)
            .sec5Blog7ImgContentType(UPDATED_SEC_5_BLOG_7_IMG_CONTENT_TYPE)
            .sec5Blog1SousTitre(UPDATED_SEC_5_BLOG_1_SOUS_TITRE)
            .sec5Blog1SousTitreContentType(UPDATED_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog2SousTitre(UPDATED_SEC_5_BLOG_2_SOUS_TITRE)
            .sec5Blog2SousTitreContentType(UPDATED_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog3SousTitre(UPDATED_SEC_5_BLOG_3_SOUS_TITRE)
            .sec5Blog3SousTitreContentType(UPDATED_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog4SousTitre(UPDATED_SEC_5_BLOG_4_SOUS_TITRE)
            .sec5Blog4SousTitreContentType(UPDATED_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog5SousTitre(UPDATED_SEC_5_BLOG_5_SOUS_TITRE)
            .sec5Blog5SousTitreContentType(UPDATED_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog6SousTitre(UPDATED_SEC_5_BLOG_6_SOUS_TITRE)
            .sec5Blog6SousTitreContentType(UPDATED_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog7SousTitre(UPDATED_SEC_5_BLOG_7_SOUS_TITRE)
            .sec5Blog7SousTitreContentType(UPDATED_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog1Text(UPDATED_SEC_5_BLOG_1_TEXT)
            .sec5Blog2Text(UPDATED_SEC_5_BLOG_2_TEXT)
            .sec5Blog3Text(UPDATED_SEC_5_BLOG_3_TEXT)
            .sec5Blog4Text(UPDATED_SEC_5_BLOG_4_TEXT)
            .sec5Blog5Text(UPDATED_SEC_5_BLOG_5_TEXT)
            .sec5Blog6Text(UPDATED_SEC_5_BLOG_6_TEXT)
            .sec5Blog7Text(UPDATED_SEC_5_BLOG_7_TEXT)
            .sec6Titre(UPDATED_SEC_6_TITRE)
            .sec6Img(UPDATED_SEC_6_IMG)
            .sec6ImgContentType(UPDATED_SEC_6_IMG_CONTENT_TYPE)
            .sec6Text(UPDATED_SEC_6_TEXT)
            .sec7Titre(UPDATED_SEC_7_TITRE)
            .sec7Img(UPDATED_SEC_7_IMG)
            .sec7ImgContentType(UPDATED_SEC_7_IMG_CONTENT_TYPE)
            .sec7Text(UPDATED_SEC_7_TEXT)
            .sec8Titre(UPDATED_SEC_8_TITRE)
            .sec8Img(UPDATED_SEC_8_IMG)
            .sec8ImgContentType(UPDATED_SEC_8_IMG_CONTENT_TYPE)
            .sec8Text(UPDATED_SEC_8_TEXT)
            .sec9Titre(UPDATED_SEC_9_TITRE)
            .sec9Img(UPDATED_SEC_9_IMG)
            .sec9ImgContentType(UPDATED_SEC_9_IMG_CONTENT_TYPE)
            .sec9Text(UPDATED_SEC_9_TEXT);

        restPageAccueilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPageAccueil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPageAccueil))
            )
            .andExpect(status().isOk());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeUpdate);
        PageAccueil testPageAccueil = pageAccueilList.get(pageAccueilList.size() - 1);
        assertThat(testPageAccueil.getSec1Titre()).isEqualTo(UPDATED_SEC_1_TITRE);
        assertThat(testPageAccueil.getSec1Texte()).isEqualTo(UPDATED_SEC_1_TEXTE);
        assertThat(testPageAccueil.getSecImage()).isEqualTo(UPDATED_SEC_IMAGE);
        assertThat(testPageAccueil.getSecImageContentType()).isEqualTo(UPDATED_SEC_IMAGE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec1Bouton()).isEqualTo(UPDATED_SEC_1_BOUTON);
        assertThat(testPageAccueil.getSec2Titre()).isEqualTo(UPDATED_SEC_2_TITRE);
        assertThat(testPageAccueil.getSec2Text()).isEqualTo(UPDATED_SEC_2_TEXT);
        assertThat(testPageAccueil.getSec2Bouton()).isEqualTo(UPDATED_SEC_2_BOUTON);
        assertThat(testPageAccueil.getSec3Titre()).isEqualTo(UPDATED_SEC_3_TITRE);
        assertThat(testPageAccueil.getSec3Blog1Img()).isEqualTo(UPDATED_SEC_3_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec3Blog1ImgContentType()).isEqualTo(UPDATED_SEC_3_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog2Img()).isEqualTo(UPDATED_SEC_3_BLOG_2_IMG);
        assertThat(testPageAccueil.getSec3Blog2ImgContentType()).isEqualTo(UPDATED_SEC_3_BLOG_2_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog3Img()).isEqualTo(UPDATED_SEC_3_BLOG_3_IMG);
        assertThat(testPageAccueil.getSec3Blog3ImgContentType()).isEqualTo(UPDATED_SEC_3_BLOG_3_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog1SousTitre()).isEqualTo(UPDATED_SEC_3_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog2SousTitre()).isEqualTo(UPDATED_SEC_3_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog3SousTitre()).isEqualTo(UPDATED_SEC_3_BLOG_3_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog1Text()).isEqualTo(UPDATED_SEC_3_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec3Blog2Text()).isEqualTo(UPDATED_SEC_3_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec3Blog3Text()).isEqualTo(UPDATED_SEC_3_BLOG_3_TEXT);
        assertThat(testPageAccueil.getSec4Titre()).isEqualTo(UPDATED_SEC_4_TITRE);
        assertThat(testPageAccueil.getSec4Text()).isEqualTo(UPDATED_SEC_4_TEXT);
        assertThat(testPageAccueil.getSec4Blog1Img()).isEqualTo(UPDATED_SEC_4_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec4Blog1ImgContentType()).isEqualTo(UPDATED_SEC_4_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec4Blog1SousTitre()).isEqualTo(UPDATED_SEC_4_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec4Blog2SousTitre()).isEqualTo(UPDATED_SEC_4_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec4Blog1Text()).isEqualTo(UPDATED_SEC_4_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec4Blog2Text()).isEqualTo(UPDATED_SEC_4_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec5Titre()).isEqualTo(UPDATED_SEC_5_TITRE);
        assertThat(testPageAccueil.getSec5Blog1Img()).isEqualTo(UPDATED_SEC_5_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec5Blog1ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog2Img()).isEqualTo(UPDATED_SEC_5_BLOG_2_IMG);
        assertThat(testPageAccueil.getSec5Blog2ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_2_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog3Img()).isEqualTo(UPDATED_SEC_5_BLOG_3_IMG);
        assertThat(testPageAccueil.getSec5Blog3ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_3_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog4Img()).isEqualTo(UPDATED_SEC_5_BLOG_4_IMG);
        assertThat(testPageAccueil.getSec5Blog4ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_4_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog5Img()).isEqualTo(UPDATED_SEC_5_BLOG_5_IMG);
        assertThat(testPageAccueil.getSec5Blog5ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_5_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog6Img()).isEqualTo(UPDATED_SEC_5_BLOG_6_IMG);
        assertThat(testPageAccueil.getSec5Blog6ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_6_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog7Img()).isEqualTo(UPDATED_SEC_5_BLOG_7_IMG);
        assertThat(testPageAccueil.getSec5Blog7ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_7_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog1SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog1SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog2SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog2SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog3SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_3_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog3SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog4SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_4_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog4SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog5SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_5_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog5SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog6SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_6_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog6SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog7SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_7_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog7SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog1Text()).isEqualTo(UPDATED_SEC_5_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec5Blog2Text()).isEqualTo(UPDATED_SEC_5_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec5Blog3Text()).isEqualTo(UPDATED_SEC_5_BLOG_3_TEXT);
        assertThat(testPageAccueil.getSec5Blog4Text()).isEqualTo(UPDATED_SEC_5_BLOG_4_TEXT);
        assertThat(testPageAccueil.getSec5Blog5Text()).isEqualTo(UPDATED_SEC_5_BLOG_5_TEXT);
        assertThat(testPageAccueil.getSec5Blog6Text()).isEqualTo(UPDATED_SEC_5_BLOG_6_TEXT);
        assertThat(testPageAccueil.getSec5Blog7Text()).isEqualTo(UPDATED_SEC_5_BLOG_7_TEXT);
        assertThat(testPageAccueil.getSec6Titre()).isEqualTo(UPDATED_SEC_6_TITRE);
        assertThat(testPageAccueil.getSec6Img()).isEqualTo(UPDATED_SEC_6_IMG);
        assertThat(testPageAccueil.getSec6ImgContentType()).isEqualTo(UPDATED_SEC_6_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec6Text()).isEqualTo(UPDATED_SEC_6_TEXT);
        assertThat(testPageAccueil.getSec7Titre()).isEqualTo(UPDATED_SEC_7_TITRE);
        assertThat(testPageAccueil.getSec7Img()).isEqualTo(UPDATED_SEC_7_IMG);
        assertThat(testPageAccueil.getSec7ImgContentType()).isEqualTo(UPDATED_SEC_7_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec7Text()).isEqualTo(UPDATED_SEC_7_TEXT);
        assertThat(testPageAccueil.getSec8Titre()).isEqualTo(UPDATED_SEC_8_TITRE);
        assertThat(testPageAccueil.getSec8Img()).isEqualTo(UPDATED_SEC_8_IMG);
        assertThat(testPageAccueil.getSec8ImgContentType()).isEqualTo(UPDATED_SEC_8_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec8Text()).isEqualTo(UPDATED_SEC_8_TEXT);
        assertThat(testPageAccueil.getSec9Titre()).isEqualTo(UPDATED_SEC_9_TITRE);
        assertThat(testPageAccueil.getSec9Img()).isEqualTo(UPDATED_SEC_9_IMG);
        assertThat(testPageAccueil.getSec9ImgContentType()).isEqualTo(UPDATED_SEC_9_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec9Text()).isEqualTo(UPDATED_SEC_9_TEXT);
    }

    @Test
    @Transactional
    void putNonExistingPageAccueil() throws Exception {
        int databaseSizeBeforeUpdate = pageAccueilRepository.findAll().size();
        pageAccueil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageAccueilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageAccueil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageAccueil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageAccueil() throws Exception {
        int databaseSizeBeforeUpdate = pageAccueilRepository.findAll().size();
        pageAccueil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageAccueilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageAccueil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageAccueil() throws Exception {
        int databaseSizeBeforeUpdate = pageAccueilRepository.findAll().size();
        pageAccueil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageAccueilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageAccueil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageAccueilWithPatch() throws Exception {
        // Initialize the database
        pageAccueilRepository.saveAndFlush(pageAccueil);

        int databaseSizeBeforeUpdate = pageAccueilRepository.findAll().size();

        // Update the pageAccueil using partial update
        PageAccueil partialUpdatedPageAccueil = new PageAccueil();
        partialUpdatedPageAccueil.setId(pageAccueil.getId());

        partialUpdatedPageAccueil
            .sec1Titre(UPDATED_SEC_1_TITRE)
            .sec1Bouton(UPDATED_SEC_1_BOUTON)
            .sec2Text(UPDATED_SEC_2_TEXT)
            .sec2Bouton(UPDATED_SEC_2_BOUTON)
            .sec3Titre(UPDATED_SEC_3_TITRE)
            .sec3Blog2Img(UPDATED_SEC_3_BLOG_2_IMG)
            .sec3Blog2ImgContentType(UPDATED_SEC_3_BLOG_2_IMG_CONTENT_TYPE)
            .sec3Blog3Img(UPDATED_SEC_3_BLOG_3_IMG)
            .sec3Blog3ImgContentType(UPDATED_SEC_3_BLOG_3_IMG_CONTENT_TYPE)
            .sec3Blog2SousTitre(UPDATED_SEC_3_BLOG_2_SOUS_TITRE)
            .sec4Blog1SousTitre(UPDATED_SEC_4_BLOG_1_SOUS_TITRE)
            .sec4Blog2SousTitre(UPDATED_SEC_4_BLOG_2_SOUS_TITRE)
            .sec4Blog1Text(UPDATED_SEC_4_BLOG_1_TEXT)
            .sec5Titre(UPDATED_SEC_5_TITRE)
            .sec5Blog3Img(UPDATED_SEC_5_BLOG_3_IMG)
            .sec5Blog3ImgContentType(UPDATED_SEC_5_BLOG_3_IMG_CONTENT_TYPE)
            .sec5Blog4Img(UPDATED_SEC_5_BLOG_4_IMG)
            .sec5Blog4ImgContentType(UPDATED_SEC_5_BLOG_4_IMG_CONTENT_TYPE)
            .sec5Blog5Img(UPDATED_SEC_5_BLOG_5_IMG)
            .sec5Blog5ImgContentType(UPDATED_SEC_5_BLOG_5_IMG_CONTENT_TYPE)
            .sec5Blog7Img(UPDATED_SEC_5_BLOG_7_IMG)
            .sec5Blog7ImgContentType(UPDATED_SEC_5_BLOG_7_IMG_CONTENT_TYPE)
            .sec5Blog1SousTitre(UPDATED_SEC_5_BLOG_1_SOUS_TITRE)
            .sec5Blog1SousTitreContentType(UPDATED_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog3SousTitre(UPDATED_SEC_5_BLOG_3_SOUS_TITRE)
            .sec5Blog3SousTitreContentType(UPDATED_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog2Text(UPDATED_SEC_5_BLOG_2_TEXT)
            .sec5Blog7Text(UPDATED_SEC_5_BLOG_7_TEXT)
            .sec7Titre(UPDATED_SEC_7_TITRE)
            .sec7Img(UPDATED_SEC_7_IMG)
            .sec7ImgContentType(UPDATED_SEC_7_IMG_CONTENT_TYPE)
            .sec8Titre(UPDATED_SEC_8_TITRE)
            .sec8Text(UPDATED_SEC_8_TEXT)
            .sec9Img(UPDATED_SEC_9_IMG)
            .sec9ImgContentType(UPDATED_SEC_9_IMG_CONTENT_TYPE)
            .sec9Text(UPDATED_SEC_9_TEXT);

        restPageAccueilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageAccueil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageAccueil))
            )
            .andExpect(status().isOk());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeUpdate);
        PageAccueil testPageAccueil = pageAccueilList.get(pageAccueilList.size() - 1);
        assertThat(testPageAccueil.getSec1Titre()).isEqualTo(UPDATED_SEC_1_TITRE);
        assertThat(testPageAccueil.getSec1Texte()).isEqualTo(DEFAULT_SEC_1_TEXTE);
        assertThat(testPageAccueil.getSecImage()).isEqualTo(DEFAULT_SEC_IMAGE);
        assertThat(testPageAccueil.getSecImageContentType()).isEqualTo(DEFAULT_SEC_IMAGE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec1Bouton()).isEqualTo(UPDATED_SEC_1_BOUTON);
        assertThat(testPageAccueil.getSec2Titre()).isEqualTo(DEFAULT_SEC_2_TITRE);
        assertThat(testPageAccueil.getSec2Text()).isEqualTo(UPDATED_SEC_2_TEXT);
        assertThat(testPageAccueil.getSec2Bouton()).isEqualTo(UPDATED_SEC_2_BOUTON);
        assertThat(testPageAccueil.getSec3Titre()).isEqualTo(UPDATED_SEC_3_TITRE);
        assertThat(testPageAccueil.getSec3Blog1Img()).isEqualTo(DEFAULT_SEC_3_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec3Blog1ImgContentType()).isEqualTo(DEFAULT_SEC_3_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog2Img()).isEqualTo(UPDATED_SEC_3_BLOG_2_IMG);
        assertThat(testPageAccueil.getSec3Blog2ImgContentType()).isEqualTo(UPDATED_SEC_3_BLOG_2_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog3Img()).isEqualTo(UPDATED_SEC_3_BLOG_3_IMG);
        assertThat(testPageAccueil.getSec3Blog3ImgContentType()).isEqualTo(UPDATED_SEC_3_BLOG_3_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog1SousTitre()).isEqualTo(DEFAULT_SEC_3_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog2SousTitre()).isEqualTo(UPDATED_SEC_3_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog3SousTitre()).isEqualTo(DEFAULT_SEC_3_BLOG_3_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog1Text()).isEqualTo(DEFAULT_SEC_3_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec3Blog2Text()).isEqualTo(DEFAULT_SEC_3_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec3Blog3Text()).isEqualTo(DEFAULT_SEC_3_BLOG_3_TEXT);
        assertThat(testPageAccueil.getSec4Titre()).isEqualTo(DEFAULT_SEC_4_TITRE);
        assertThat(testPageAccueil.getSec4Text()).isEqualTo(DEFAULT_SEC_4_TEXT);
        assertThat(testPageAccueil.getSec4Blog1Img()).isEqualTo(DEFAULT_SEC_4_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec4Blog1ImgContentType()).isEqualTo(DEFAULT_SEC_4_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec4Blog1SousTitre()).isEqualTo(UPDATED_SEC_4_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec4Blog2SousTitre()).isEqualTo(UPDATED_SEC_4_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec4Blog1Text()).isEqualTo(UPDATED_SEC_4_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec4Blog2Text()).isEqualTo(DEFAULT_SEC_4_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec5Titre()).isEqualTo(UPDATED_SEC_5_TITRE);
        assertThat(testPageAccueil.getSec5Blog1Img()).isEqualTo(DEFAULT_SEC_5_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec5Blog1ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog2Img()).isEqualTo(DEFAULT_SEC_5_BLOG_2_IMG);
        assertThat(testPageAccueil.getSec5Blog2ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_2_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog3Img()).isEqualTo(UPDATED_SEC_5_BLOG_3_IMG);
        assertThat(testPageAccueil.getSec5Blog3ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_3_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog4Img()).isEqualTo(UPDATED_SEC_5_BLOG_4_IMG);
        assertThat(testPageAccueil.getSec5Blog4ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_4_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog5Img()).isEqualTo(UPDATED_SEC_5_BLOG_5_IMG);
        assertThat(testPageAccueil.getSec5Blog5ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_5_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog6Img()).isEqualTo(DEFAULT_SEC_5_BLOG_6_IMG);
        assertThat(testPageAccueil.getSec5Blog6ImgContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_6_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog7Img()).isEqualTo(UPDATED_SEC_5_BLOG_7_IMG);
        assertThat(testPageAccueil.getSec5Blog7ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_7_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog1SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog1SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog2SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog2SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog3SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_3_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog3SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog4SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog4SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog5SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog5SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog6SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog6SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog7SousTitre()).isEqualTo(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog7SousTitreContentType()).isEqualTo(DEFAULT_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog1Text()).isEqualTo(DEFAULT_SEC_5_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec5Blog2Text()).isEqualTo(UPDATED_SEC_5_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec5Blog3Text()).isEqualTo(DEFAULT_SEC_5_BLOG_3_TEXT);
        assertThat(testPageAccueil.getSec5Blog4Text()).isEqualTo(DEFAULT_SEC_5_BLOG_4_TEXT);
        assertThat(testPageAccueil.getSec5Blog5Text()).isEqualTo(DEFAULT_SEC_5_BLOG_5_TEXT);
        assertThat(testPageAccueil.getSec5Blog6Text()).isEqualTo(DEFAULT_SEC_5_BLOG_6_TEXT);
        assertThat(testPageAccueil.getSec5Blog7Text()).isEqualTo(UPDATED_SEC_5_BLOG_7_TEXT);
        assertThat(testPageAccueil.getSec6Titre()).isEqualTo(DEFAULT_SEC_6_TITRE);
        assertThat(testPageAccueil.getSec6Img()).isEqualTo(DEFAULT_SEC_6_IMG);
        assertThat(testPageAccueil.getSec6ImgContentType()).isEqualTo(DEFAULT_SEC_6_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec6Text()).isEqualTo(DEFAULT_SEC_6_TEXT);
        assertThat(testPageAccueil.getSec7Titre()).isEqualTo(UPDATED_SEC_7_TITRE);
        assertThat(testPageAccueil.getSec7Img()).isEqualTo(UPDATED_SEC_7_IMG);
        assertThat(testPageAccueil.getSec7ImgContentType()).isEqualTo(UPDATED_SEC_7_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec7Text()).isEqualTo(DEFAULT_SEC_7_TEXT);
        assertThat(testPageAccueil.getSec8Titre()).isEqualTo(UPDATED_SEC_8_TITRE);
        assertThat(testPageAccueil.getSec8Img()).isEqualTo(DEFAULT_SEC_8_IMG);
        assertThat(testPageAccueil.getSec8ImgContentType()).isEqualTo(DEFAULT_SEC_8_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec8Text()).isEqualTo(UPDATED_SEC_8_TEXT);
        assertThat(testPageAccueil.getSec9Titre()).isEqualTo(DEFAULT_SEC_9_TITRE);
        assertThat(testPageAccueil.getSec9Img()).isEqualTo(UPDATED_SEC_9_IMG);
        assertThat(testPageAccueil.getSec9ImgContentType()).isEqualTo(UPDATED_SEC_9_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec9Text()).isEqualTo(UPDATED_SEC_9_TEXT);
    }

    @Test
    @Transactional
    void fullUpdatePageAccueilWithPatch() throws Exception {
        // Initialize the database
        pageAccueilRepository.saveAndFlush(pageAccueil);

        int databaseSizeBeforeUpdate = pageAccueilRepository.findAll().size();

        // Update the pageAccueil using partial update
        PageAccueil partialUpdatedPageAccueil = new PageAccueil();
        partialUpdatedPageAccueil.setId(pageAccueil.getId());

        partialUpdatedPageAccueil
            .sec1Titre(UPDATED_SEC_1_TITRE)
            .sec1Texte(UPDATED_SEC_1_TEXTE)
            .secImage(UPDATED_SEC_IMAGE)
            .secImageContentType(UPDATED_SEC_IMAGE_CONTENT_TYPE)
            .sec1Bouton(UPDATED_SEC_1_BOUTON)
            .sec2Titre(UPDATED_SEC_2_TITRE)
            .sec2Text(UPDATED_SEC_2_TEXT)
            .sec2Bouton(UPDATED_SEC_2_BOUTON)
            .sec3Titre(UPDATED_SEC_3_TITRE)
            .sec3Blog1Img(UPDATED_SEC_3_BLOG_1_IMG)
            .sec3Blog1ImgContentType(UPDATED_SEC_3_BLOG_1_IMG_CONTENT_TYPE)
            .sec3Blog2Img(UPDATED_SEC_3_BLOG_2_IMG)
            .sec3Blog2ImgContentType(UPDATED_SEC_3_BLOG_2_IMG_CONTENT_TYPE)
            .sec3Blog3Img(UPDATED_SEC_3_BLOG_3_IMG)
            .sec3Blog3ImgContentType(UPDATED_SEC_3_BLOG_3_IMG_CONTENT_TYPE)
            .sec3Blog1SousTitre(UPDATED_SEC_3_BLOG_1_SOUS_TITRE)
            .sec3Blog2SousTitre(UPDATED_SEC_3_BLOG_2_SOUS_TITRE)
            .sec3Blog3SousTitre(UPDATED_SEC_3_BLOG_3_SOUS_TITRE)
            .sec3Blog1Text(UPDATED_SEC_3_BLOG_1_TEXT)
            .sec3Blog2Text(UPDATED_SEC_3_BLOG_2_TEXT)
            .sec3Blog3Text(UPDATED_SEC_3_BLOG_3_TEXT)
            .sec4Titre(UPDATED_SEC_4_TITRE)
            .sec4Text(UPDATED_SEC_4_TEXT)
            .sec4Blog1Img(UPDATED_SEC_4_BLOG_1_IMG)
            .sec4Blog1ImgContentType(UPDATED_SEC_4_BLOG_1_IMG_CONTENT_TYPE)
            .sec4Blog1SousTitre(UPDATED_SEC_4_BLOG_1_SOUS_TITRE)
            .sec4Blog2SousTitre(UPDATED_SEC_4_BLOG_2_SOUS_TITRE)
            .sec4Blog1Text(UPDATED_SEC_4_BLOG_1_TEXT)
            .sec4Blog2Text(UPDATED_SEC_4_BLOG_2_TEXT)
            .sec5Titre(UPDATED_SEC_5_TITRE)
            .sec5Blog1Img(UPDATED_SEC_5_BLOG_1_IMG)
            .sec5Blog1ImgContentType(UPDATED_SEC_5_BLOG_1_IMG_CONTENT_TYPE)
            .sec5Blog2Img(UPDATED_SEC_5_BLOG_2_IMG)
            .sec5Blog2ImgContentType(UPDATED_SEC_5_BLOG_2_IMG_CONTENT_TYPE)
            .sec5Blog3Img(UPDATED_SEC_5_BLOG_3_IMG)
            .sec5Blog3ImgContentType(UPDATED_SEC_5_BLOG_3_IMG_CONTENT_TYPE)
            .sec5Blog4Img(UPDATED_SEC_5_BLOG_4_IMG)
            .sec5Blog4ImgContentType(UPDATED_SEC_5_BLOG_4_IMG_CONTENT_TYPE)
            .sec5Blog5Img(UPDATED_SEC_5_BLOG_5_IMG)
            .sec5Blog5ImgContentType(UPDATED_SEC_5_BLOG_5_IMG_CONTENT_TYPE)
            .sec5Blog6Img(UPDATED_SEC_5_BLOG_6_IMG)
            .sec5Blog6ImgContentType(UPDATED_SEC_5_BLOG_6_IMG_CONTENT_TYPE)
            .sec5Blog7Img(UPDATED_SEC_5_BLOG_7_IMG)
            .sec5Blog7ImgContentType(UPDATED_SEC_5_BLOG_7_IMG_CONTENT_TYPE)
            .sec5Blog1SousTitre(UPDATED_SEC_5_BLOG_1_SOUS_TITRE)
            .sec5Blog1SousTitreContentType(UPDATED_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog2SousTitre(UPDATED_SEC_5_BLOG_2_SOUS_TITRE)
            .sec5Blog2SousTitreContentType(UPDATED_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog3SousTitre(UPDATED_SEC_5_BLOG_3_SOUS_TITRE)
            .sec5Blog3SousTitreContentType(UPDATED_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog4SousTitre(UPDATED_SEC_5_BLOG_4_SOUS_TITRE)
            .sec5Blog4SousTitreContentType(UPDATED_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog5SousTitre(UPDATED_SEC_5_BLOG_5_SOUS_TITRE)
            .sec5Blog5SousTitreContentType(UPDATED_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog6SousTitre(UPDATED_SEC_5_BLOG_6_SOUS_TITRE)
            .sec5Blog6SousTitreContentType(UPDATED_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog7SousTitre(UPDATED_SEC_5_BLOG_7_SOUS_TITRE)
            .sec5Blog7SousTitreContentType(UPDATED_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE)
            .sec5Blog1Text(UPDATED_SEC_5_BLOG_1_TEXT)
            .sec5Blog2Text(UPDATED_SEC_5_BLOG_2_TEXT)
            .sec5Blog3Text(UPDATED_SEC_5_BLOG_3_TEXT)
            .sec5Blog4Text(UPDATED_SEC_5_BLOG_4_TEXT)
            .sec5Blog5Text(UPDATED_SEC_5_BLOG_5_TEXT)
            .sec5Blog6Text(UPDATED_SEC_5_BLOG_6_TEXT)
            .sec5Blog7Text(UPDATED_SEC_5_BLOG_7_TEXT)
            .sec6Titre(UPDATED_SEC_6_TITRE)
            .sec6Img(UPDATED_SEC_6_IMG)
            .sec6ImgContentType(UPDATED_SEC_6_IMG_CONTENT_TYPE)
            .sec6Text(UPDATED_SEC_6_TEXT)
            .sec7Titre(UPDATED_SEC_7_TITRE)
            .sec7Img(UPDATED_SEC_7_IMG)
            .sec7ImgContentType(UPDATED_SEC_7_IMG_CONTENT_TYPE)
            .sec7Text(UPDATED_SEC_7_TEXT)
            .sec8Titre(UPDATED_SEC_8_TITRE)
            .sec8Img(UPDATED_SEC_8_IMG)
            .sec8ImgContentType(UPDATED_SEC_8_IMG_CONTENT_TYPE)
            .sec8Text(UPDATED_SEC_8_TEXT)
            .sec9Titre(UPDATED_SEC_9_TITRE)
            .sec9Img(UPDATED_SEC_9_IMG)
            .sec9ImgContentType(UPDATED_SEC_9_IMG_CONTENT_TYPE)
            .sec9Text(UPDATED_SEC_9_TEXT);

        restPageAccueilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageAccueil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageAccueil))
            )
            .andExpect(status().isOk());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeUpdate);
        PageAccueil testPageAccueil = pageAccueilList.get(pageAccueilList.size() - 1);
        assertThat(testPageAccueil.getSec1Titre()).isEqualTo(UPDATED_SEC_1_TITRE);
        assertThat(testPageAccueil.getSec1Texte()).isEqualTo(UPDATED_SEC_1_TEXTE);
        assertThat(testPageAccueil.getSecImage()).isEqualTo(UPDATED_SEC_IMAGE);
        assertThat(testPageAccueil.getSecImageContentType()).isEqualTo(UPDATED_SEC_IMAGE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec1Bouton()).isEqualTo(UPDATED_SEC_1_BOUTON);
        assertThat(testPageAccueil.getSec2Titre()).isEqualTo(UPDATED_SEC_2_TITRE);
        assertThat(testPageAccueil.getSec2Text()).isEqualTo(UPDATED_SEC_2_TEXT);
        assertThat(testPageAccueil.getSec2Bouton()).isEqualTo(UPDATED_SEC_2_BOUTON);
        assertThat(testPageAccueil.getSec3Titre()).isEqualTo(UPDATED_SEC_3_TITRE);
        assertThat(testPageAccueil.getSec3Blog1Img()).isEqualTo(UPDATED_SEC_3_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec3Blog1ImgContentType()).isEqualTo(UPDATED_SEC_3_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog2Img()).isEqualTo(UPDATED_SEC_3_BLOG_2_IMG);
        assertThat(testPageAccueil.getSec3Blog2ImgContentType()).isEqualTo(UPDATED_SEC_3_BLOG_2_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog3Img()).isEqualTo(UPDATED_SEC_3_BLOG_3_IMG);
        assertThat(testPageAccueil.getSec3Blog3ImgContentType()).isEqualTo(UPDATED_SEC_3_BLOG_3_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec3Blog1SousTitre()).isEqualTo(UPDATED_SEC_3_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog2SousTitre()).isEqualTo(UPDATED_SEC_3_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog3SousTitre()).isEqualTo(UPDATED_SEC_3_BLOG_3_SOUS_TITRE);
        assertThat(testPageAccueil.getSec3Blog1Text()).isEqualTo(UPDATED_SEC_3_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec3Blog2Text()).isEqualTo(UPDATED_SEC_3_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec3Blog3Text()).isEqualTo(UPDATED_SEC_3_BLOG_3_TEXT);
        assertThat(testPageAccueil.getSec4Titre()).isEqualTo(UPDATED_SEC_4_TITRE);
        assertThat(testPageAccueil.getSec4Text()).isEqualTo(UPDATED_SEC_4_TEXT);
        assertThat(testPageAccueil.getSec4Blog1Img()).isEqualTo(UPDATED_SEC_4_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec4Blog1ImgContentType()).isEqualTo(UPDATED_SEC_4_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec4Blog1SousTitre()).isEqualTo(UPDATED_SEC_4_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec4Blog2SousTitre()).isEqualTo(UPDATED_SEC_4_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec4Blog1Text()).isEqualTo(UPDATED_SEC_4_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec4Blog2Text()).isEqualTo(UPDATED_SEC_4_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec5Titre()).isEqualTo(UPDATED_SEC_5_TITRE);
        assertThat(testPageAccueil.getSec5Blog1Img()).isEqualTo(UPDATED_SEC_5_BLOG_1_IMG);
        assertThat(testPageAccueil.getSec5Blog1ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_1_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog2Img()).isEqualTo(UPDATED_SEC_5_BLOG_2_IMG);
        assertThat(testPageAccueil.getSec5Blog2ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_2_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog3Img()).isEqualTo(UPDATED_SEC_5_BLOG_3_IMG);
        assertThat(testPageAccueil.getSec5Blog3ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_3_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog4Img()).isEqualTo(UPDATED_SEC_5_BLOG_4_IMG);
        assertThat(testPageAccueil.getSec5Blog4ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_4_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog5Img()).isEqualTo(UPDATED_SEC_5_BLOG_5_IMG);
        assertThat(testPageAccueil.getSec5Blog5ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_5_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog6Img()).isEqualTo(UPDATED_SEC_5_BLOG_6_IMG);
        assertThat(testPageAccueil.getSec5Blog6ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_6_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog7Img()).isEqualTo(UPDATED_SEC_5_BLOG_7_IMG);
        assertThat(testPageAccueil.getSec5Blog7ImgContentType()).isEqualTo(UPDATED_SEC_5_BLOG_7_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog1SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_1_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog1SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_1_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog2SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_2_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog2SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_2_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog3SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_3_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog3SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_3_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog4SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_4_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog4SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_4_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog5SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_5_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog5SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_5_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog6SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_6_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog6SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_6_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog7SousTitre()).isEqualTo(UPDATED_SEC_5_BLOG_7_SOUS_TITRE);
        assertThat(testPageAccueil.getSec5Blog7SousTitreContentType()).isEqualTo(UPDATED_SEC_5_BLOG_7_SOUS_TITRE_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec5Blog1Text()).isEqualTo(UPDATED_SEC_5_BLOG_1_TEXT);
        assertThat(testPageAccueil.getSec5Blog2Text()).isEqualTo(UPDATED_SEC_5_BLOG_2_TEXT);
        assertThat(testPageAccueil.getSec5Blog3Text()).isEqualTo(UPDATED_SEC_5_BLOG_3_TEXT);
        assertThat(testPageAccueil.getSec5Blog4Text()).isEqualTo(UPDATED_SEC_5_BLOG_4_TEXT);
        assertThat(testPageAccueil.getSec5Blog5Text()).isEqualTo(UPDATED_SEC_5_BLOG_5_TEXT);
        assertThat(testPageAccueil.getSec5Blog6Text()).isEqualTo(UPDATED_SEC_5_BLOG_6_TEXT);
        assertThat(testPageAccueil.getSec5Blog7Text()).isEqualTo(UPDATED_SEC_5_BLOG_7_TEXT);
        assertThat(testPageAccueil.getSec6Titre()).isEqualTo(UPDATED_SEC_6_TITRE);
        assertThat(testPageAccueil.getSec6Img()).isEqualTo(UPDATED_SEC_6_IMG);
        assertThat(testPageAccueil.getSec6ImgContentType()).isEqualTo(UPDATED_SEC_6_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec6Text()).isEqualTo(UPDATED_SEC_6_TEXT);
        assertThat(testPageAccueil.getSec7Titre()).isEqualTo(UPDATED_SEC_7_TITRE);
        assertThat(testPageAccueil.getSec7Img()).isEqualTo(UPDATED_SEC_7_IMG);
        assertThat(testPageAccueil.getSec7ImgContentType()).isEqualTo(UPDATED_SEC_7_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec7Text()).isEqualTo(UPDATED_SEC_7_TEXT);
        assertThat(testPageAccueil.getSec8Titre()).isEqualTo(UPDATED_SEC_8_TITRE);
        assertThat(testPageAccueil.getSec8Img()).isEqualTo(UPDATED_SEC_8_IMG);
        assertThat(testPageAccueil.getSec8ImgContentType()).isEqualTo(UPDATED_SEC_8_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec8Text()).isEqualTo(UPDATED_SEC_8_TEXT);
        assertThat(testPageAccueil.getSec9Titre()).isEqualTo(UPDATED_SEC_9_TITRE);
        assertThat(testPageAccueil.getSec9Img()).isEqualTo(UPDATED_SEC_9_IMG);
        assertThat(testPageAccueil.getSec9ImgContentType()).isEqualTo(UPDATED_SEC_9_IMG_CONTENT_TYPE);
        assertThat(testPageAccueil.getSec9Text()).isEqualTo(UPDATED_SEC_9_TEXT);
    }

    @Test
    @Transactional
    void patchNonExistingPageAccueil() throws Exception {
        int databaseSizeBeforeUpdate = pageAccueilRepository.findAll().size();
        pageAccueil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageAccueilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageAccueil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageAccueil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageAccueil() throws Exception {
        int databaseSizeBeforeUpdate = pageAccueilRepository.findAll().size();
        pageAccueil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageAccueilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageAccueil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageAccueil() throws Exception {
        int databaseSizeBeforeUpdate = pageAccueilRepository.findAll().size();
        pageAccueil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageAccueilMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageAccueil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageAccueil in the database
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageAccueil() throws Exception {
        // Initialize the database
        pageAccueilRepository.saveAndFlush(pageAccueil);

        int databaseSizeBeforeDelete = pageAccueilRepository.findAll().size();

        // Delete the pageAccueil
        restPageAccueilMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageAccueil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageAccueil> pageAccueilList = pageAccueilRepository.findAll();
        assertThat(pageAccueilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
