package com.eis.site.web.rest;

import com.eis.site.domain.PageAPropos;
import com.eis.site.repository.PageAProposRepository;
import com.eis.site.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.eis.site.domain.PageAPropos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PageAProposResource {

    private final Logger log = LoggerFactory.getLogger(PageAProposResource.class);

    private static final String ENTITY_NAME = "pageAPropos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageAProposRepository pageAProposRepository;

    public PageAProposResource(PageAProposRepository pageAProposRepository) {
        this.pageAProposRepository = pageAProposRepository;
    }

    /**
     * {@code POST  /page-a-propos} : Create a new pageAPropos.
     *
     * @param pageAPropos the pageAPropos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageAPropos, or with status {@code 400 (Bad Request)} if the pageAPropos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-a-propos")
    public ResponseEntity<PageAPropos> createPageAPropos(@RequestBody PageAPropos pageAPropos) throws URISyntaxException {
        log.debug("REST request to save PageAPropos : {}", pageAPropos);
        if (pageAPropos.getId() != null) {
            throw new BadRequestAlertException("A new pageAPropos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageAPropos result = pageAProposRepository.save(pageAPropos);
        return ResponseEntity
            .created(new URI("/api/page-a-propos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-a-propos/:id} : Updates an existing pageAPropos.
     *
     * @param id the id of the pageAPropos to save.
     * @param pageAPropos the pageAPropos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageAPropos,
     * or with status {@code 400 (Bad Request)} if the pageAPropos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageAPropos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-a-propos/{id}")
    public ResponseEntity<PageAPropos> updatePageAPropos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageAPropos pageAPropos
    ) throws URISyntaxException {
        log.debug("REST request to update PageAPropos : {}, {}", id, pageAPropos);
        if (pageAPropos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageAPropos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageAProposRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PageAPropos result = pageAProposRepository.save(pageAPropos);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageAPropos.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /page-a-propos/:id} : Partial updates given fields of an existing pageAPropos, field will ignore if it is null
     *
     * @param id the id of the pageAPropos to save.
     * @param pageAPropos the pageAPropos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageAPropos,
     * or with status {@code 400 (Bad Request)} if the pageAPropos is not valid,
     * or with status {@code 404 (Not Found)} if the pageAPropos is not found,
     * or with status {@code 500 (Internal Server Error)} if the pageAPropos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/page-a-propos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PageAPropos> partialUpdatePageAPropos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageAPropos pageAPropos
    ) throws URISyntaxException {
        log.debug("REST request to partial update PageAPropos partially : {}, {}", id, pageAPropos);
        if (pageAPropos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageAPropos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageAProposRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PageAPropos> result = pageAProposRepository
            .findById(pageAPropos.getId())
            .map(existingPageAPropos -> {
                if (pageAPropos.getSec1Img1() != null) {
                    existingPageAPropos.setSec1Img1(pageAPropos.getSec1Img1());
                }
                if (pageAPropos.getSec1Img1ContentType() != null) {
                    existingPageAPropos.setSec1Img1ContentType(pageAPropos.getSec1Img1ContentType());
                }
                if (pageAPropos.getSec1Desc1() != null) {
                    existingPageAPropos.setSec1Desc1(pageAPropos.getSec1Desc1());
                }
                if (pageAPropos.getSec1Logo() != null) {
                    existingPageAPropos.setSec1Logo(pageAPropos.getSec1Logo());
                }
                if (pageAPropos.getSec1LogoContentType() != null) {
                    existingPageAPropos.setSec1LogoContentType(pageAPropos.getSec1LogoContentType());
                }
                if (pageAPropos.getSec1Img2() != null) {
                    existingPageAPropos.setSec1Img2(pageAPropos.getSec1Img2());
                }
                if (pageAPropos.getSec1Img2ContentType() != null) {
                    existingPageAPropos.setSec1Img2ContentType(pageAPropos.getSec1Img2ContentType());
                }
                if (pageAPropos.getSec1Desc2() != null) {
                    existingPageAPropos.setSec1Desc2(pageAPropos.getSec1Desc2());
                }
                if (pageAPropos.getSec1Img3() != null) {
                    existingPageAPropos.setSec1Img3(pageAPropos.getSec1Img3());
                }
                if (pageAPropos.getSec1Img3ContentType() != null) {
                    existingPageAPropos.setSec1Img3ContentType(pageAPropos.getSec1Img3ContentType());
                }
                if (pageAPropos.getSec1Desc3() != null) {
                    existingPageAPropos.setSec1Desc3(pageAPropos.getSec1Desc3());
                }
                if (pageAPropos.getSec2Titre() != null) {
                    existingPageAPropos.setSec2Titre(pageAPropos.getSec2Titre());
                }
                if (pageAPropos.getSec2Img() != null) {
                    existingPageAPropos.setSec2Img(pageAPropos.getSec2Img());
                }
                if (pageAPropos.getSec2ImgContentType() != null) {
                    existingPageAPropos.setSec2ImgContentType(pageAPropos.getSec2ImgContentType());
                }
                if (pageAPropos.getSec2SousTitre() != null) {
                    existingPageAPropos.setSec2SousTitre(pageAPropos.getSec2SousTitre());
                }
                if (pageAPropos.getSec2Text() != null) {
                    existingPageAPropos.setSec2Text(pageAPropos.getSec2Text());
                }
                if (pageAPropos.getSec3Titre() != null) {
                    existingPageAPropos.setSec3Titre(pageAPropos.getSec3Titre());
                }
                if (pageAPropos.getSec3Img() != null) {
                    existingPageAPropos.setSec3Img(pageAPropos.getSec3Img());
                }
                if (pageAPropos.getSec3ImgContentType() != null) {
                    existingPageAPropos.setSec3ImgContentType(pageAPropos.getSec3ImgContentType());
                }
                if (pageAPropos.getSec3SousTitre() != null) {
                    existingPageAPropos.setSec3SousTitre(pageAPropos.getSec3SousTitre());
                }
                if (pageAPropos.getSec3Text() != null) {
                    existingPageAPropos.setSec3Text(pageAPropos.getSec3Text());
                }
                if (pageAPropos.getSec4Img() != null) {
                    existingPageAPropos.setSec4Img(pageAPropos.getSec4Img());
                }
                if (pageAPropos.getSec4ImgContentType() != null) {
                    existingPageAPropos.setSec4ImgContentType(pageAPropos.getSec4ImgContentType());
                }
                if (pageAPropos.getSec4Desc1() != null) {
                    existingPageAPropos.setSec4Desc1(pageAPropos.getSec4Desc1());
                }
                if (pageAPropos.getSec4Desc2() != null) {
                    existingPageAPropos.setSec4Desc2(pageAPropos.getSec4Desc2());
                }
                if (pageAPropos.getSec4Desc3() != null) {
                    existingPageAPropos.setSec4Desc3(pageAPropos.getSec4Desc3());
                }
                if (pageAPropos.getSec4Desc4() != null) {
                    existingPageAPropos.setSec4Desc4(pageAPropos.getSec4Desc4());
                }
                if (pageAPropos.getSec5Titre() != null) {
                    existingPageAPropos.setSec5Titre(pageAPropos.getSec5Titre());
                }

                return existingPageAPropos;
            })
            .map(pageAProposRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageAPropos.getId().toString())
        );
    }

    /**
     * {@code GET  /page-a-propos} : get all the pageAPropos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageAPropos in body.
     */
    @GetMapping("/page-a-propos")
    public List<PageAPropos> getAllPageAPropos() {
        log.debug("REST request to get all PageAPropos");
        return pageAProposRepository.findAll();
    }

    /**
     * {@code GET  /page-a-propos/:id} : get the "id" pageAPropos.
     *
     * @param id the id of the pageAPropos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageAPropos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-a-propos/{id}")
    public ResponseEntity<PageAPropos> getPageAPropos(@PathVariable Long id) {
        log.debug("REST request to get PageAPropos : {}", id);
        Optional<PageAPropos> pageAPropos = pageAProposRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pageAPropos);
    }

    /**
     * {@code DELETE  /page-a-propos/:id} : delete the "id" pageAPropos.
     *
     * @param id the id of the pageAPropos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-a-propos/{id}")
    public ResponseEntity<Void> deletePageAPropos(@PathVariable Long id) {
        log.debug("REST request to delete PageAPropos : {}", id);
        pageAProposRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
