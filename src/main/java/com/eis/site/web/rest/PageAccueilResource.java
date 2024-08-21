package com.eis.site.web.rest;

import com.eis.site.domain.PageAccueil;
import com.eis.site.repository.PageAccueilRepository;
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
 * REST controller for managing {@link com.eis.site.domain.PageAccueil}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PageAccueilResource {

    private final Logger log = LoggerFactory.getLogger(PageAccueilResource.class);

    private static final String ENTITY_NAME = "pageAccueil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageAccueilRepository pageAccueilRepository;

    public PageAccueilResource(PageAccueilRepository pageAccueilRepository) {
        this.pageAccueilRepository = pageAccueilRepository;
    }

    /**
     * {@code POST  /page-accueils} : Create a new pageAccueil.
     *
     * @param pageAccueil the pageAccueil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageAccueil, or with status {@code 400 (Bad Request)} if the pageAccueil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-accueils")
    public ResponseEntity<PageAccueil> createPageAccueil(@RequestBody PageAccueil pageAccueil) throws URISyntaxException {
        log.debug("REST request to save PageAccueil : {}", pageAccueil);
        if (pageAccueil.getId() != null) {
            throw new BadRequestAlertException("A new pageAccueil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageAccueil result = pageAccueilRepository.save(pageAccueil);
        return ResponseEntity
            .created(new URI("/api/page-accueils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-accueils/:id} : Updates an existing pageAccueil.
     *
     * @param id the id of the pageAccueil to save.
     * @param pageAccueil the pageAccueil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageAccueil,
     * or with status {@code 400 (Bad Request)} if the pageAccueil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageAccueil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-accueils/{id}")
    public ResponseEntity<PageAccueil> updatePageAccueil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageAccueil pageAccueil
    ) throws URISyntaxException {
        log.debug("REST request to update PageAccueil : {}, {}", id, pageAccueil);
        if (pageAccueil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageAccueil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageAccueilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PageAccueil result = pageAccueilRepository.save(pageAccueil);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageAccueil.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /page-accueils/:id} : Partial updates given fields of an existing pageAccueil, field will ignore if it is null
     *
     * @param id the id of the pageAccueil to save.
     * @param pageAccueil the pageAccueil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageAccueil,
     * or with status {@code 400 (Bad Request)} if the pageAccueil is not valid,
     * or with status {@code 404 (Not Found)} if the pageAccueil is not found,
     * or with status {@code 500 (Internal Server Error)} if the pageAccueil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/page-accueils/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PageAccueil> partialUpdatePageAccueil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageAccueil pageAccueil
    ) throws URISyntaxException {
        log.debug("REST request to partial update PageAccueil partially : {}, {}", id, pageAccueil);
        if (pageAccueil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageAccueil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageAccueilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PageAccueil> result = pageAccueilRepository
            .findById(pageAccueil.getId())
            .map(existingPageAccueil -> {
                if (pageAccueil.getSec1Titre() != null) {
                    existingPageAccueil.setSec1Titre(pageAccueil.getSec1Titre());
                }
                if (pageAccueil.getSec1Texte() != null) {
                    existingPageAccueil.setSec1Texte(pageAccueil.getSec1Texte());
                }
                if (pageAccueil.getSecImage() != null) {
                    existingPageAccueil.setSecImage(pageAccueil.getSecImage());
                }
                if (pageAccueil.getSecImageContentType() != null) {
                    existingPageAccueil.setSecImageContentType(pageAccueil.getSecImageContentType());
                }
                if (pageAccueil.getSec1Bouton() != null) {
                    existingPageAccueil.setSec1Bouton(pageAccueil.getSec1Bouton());
                }
                if (pageAccueil.getSec2Titre() != null) {
                    existingPageAccueil.setSec2Titre(pageAccueil.getSec2Titre());
                }
                if (pageAccueil.getSec2Text() != null) {
                    existingPageAccueil.setSec2Text(pageAccueil.getSec2Text());
                }
                if (pageAccueil.getSec2Bouton() != null) {
                    existingPageAccueil.setSec2Bouton(pageAccueil.getSec2Bouton());
                }
                if (pageAccueil.getSec3Titre() != null) {
                    existingPageAccueil.setSec3Titre(pageAccueil.getSec3Titre());
                }
                if (pageAccueil.getSec3Blog1Img() != null) {
                    existingPageAccueil.setSec3Blog1Img(pageAccueil.getSec3Blog1Img());
                }
                if (pageAccueil.getSec3Blog1ImgContentType() != null) {
                    existingPageAccueil.setSec3Blog1ImgContentType(pageAccueil.getSec3Blog1ImgContentType());
                }
                if (pageAccueil.getSec3Blog2Img() != null) {
                    existingPageAccueil.setSec3Blog2Img(pageAccueil.getSec3Blog2Img());
                }
                if (pageAccueil.getSec3Blog2ImgContentType() != null) {
                    existingPageAccueil.setSec3Blog2ImgContentType(pageAccueil.getSec3Blog2ImgContentType());
                }
                if (pageAccueil.getSec3Blog3Img() != null) {
                    existingPageAccueil.setSec3Blog3Img(pageAccueil.getSec3Blog3Img());
                }
                if (pageAccueil.getSec3Blog3ImgContentType() != null) {
                    existingPageAccueil.setSec3Blog3ImgContentType(pageAccueil.getSec3Blog3ImgContentType());
                }
                if (pageAccueil.getSec3Blog1SousTitre() != null) {
                    existingPageAccueil.setSec3Blog1SousTitre(pageAccueil.getSec3Blog1SousTitre());
                }
                if (pageAccueil.getSec3Blog2SousTitre() != null) {
                    existingPageAccueil.setSec3Blog2SousTitre(pageAccueil.getSec3Blog2SousTitre());
                }
                if (pageAccueil.getSec3Blog3SousTitre() != null) {
                    existingPageAccueil.setSec3Blog3SousTitre(pageAccueil.getSec3Blog3SousTitre());
                }
                if (pageAccueil.getSec3Blog1Text() != null) {
                    existingPageAccueil.setSec3Blog1Text(pageAccueil.getSec3Blog1Text());
                }
                if (pageAccueil.getSec3Blog2Text() != null) {
                    existingPageAccueil.setSec3Blog2Text(pageAccueil.getSec3Blog2Text());
                }
                if (pageAccueil.getSec3Blog3Text() != null) {
                    existingPageAccueil.setSec3Blog3Text(pageAccueil.getSec3Blog3Text());
                }
                if (pageAccueil.getSec4Titre() != null) {
                    existingPageAccueil.setSec4Titre(pageAccueil.getSec4Titre());
                }
                if (pageAccueil.getSec4Text() != null) {
                    existingPageAccueil.setSec4Text(pageAccueil.getSec4Text());
                }
                if (pageAccueil.getSec4Blog1Img() != null) {
                    existingPageAccueil.setSec4Blog1Img(pageAccueil.getSec4Blog1Img());
                }
                if (pageAccueil.getSec4Blog1ImgContentType() != null) {
                    existingPageAccueil.setSec4Blog1ImgContentType(pageAccueil.getSec4Blog1ImgContentType());
                }
                if (pageAccueil.getSec4Blog1SousTitre() != null) {
                    existingPageAccueil.setSec4Blog1SousTitre(pageAccueil.getSec4Blog1SousTitre());
                }
                if (pageAccueil.getSec4Blog2SousTitre() != null) {
                    existingPageAccueil.setSec4Blog2SousTitre(pageAccueil.getSec4Blog2SousTitre());
                }
                if (pageAccueil.getSec4Blog1Text() != null) {
                    existingPageAccueil.setSec4Blog1Text(pageAccueil.getSec4Blog1Text());
                }
                if (pageAccueil.getSec4Blog2Text() != null) {
                    existingPageAccueil.setSec4Blog2Text(pageAccueil.getSec4Blog2Text());
                }
                if (pageAccueil.getSec5Titre() != null) {
                    existingPageAccueil.setSec5Titre(pageAccueil.getSec5Titre());
                }
                if (pageAccueil.getSec5Blog1Img() != null) {
                    existingPageAccueil.setSec5Blog1Img(pageAccueil.getSec5Blog1Img());
                }
                if (pageAccueil.getSec5Blog1ImgContentType() != null) {
                    existingPageAccueil.setSec5Blog1ImgContentType(pageAccueil.getSec5Blog1ImgContentType());
                }
                if (pageAccueil.getSec5Blog2Img() != null) {
                    existingPageAccueil.setSec5Blog2Img(pageAccueil.getSec5Blog2Img());
                }
                if (pageAccueil.getSec5Blog2ImgContentType() != null) {
                    existingPageAccueil.setSec5Blog2ImgContentType(pageAccueil.getSec5Blog2ImgContentType());
                }
                if (pageAccueil.getSec5Blog3Img() != null) {
                    existingPageAccueil.setSec5Blog3Img(pageAccueil.getSec5Blog3Img());
                }
                if (pageAccueil.getSec5Blog3ImgContentType() != null) {
                    existingPageAccueil.setSec5Blog3ImgContentType(pageAccueil.getSec5Blog3ImgContentType());
                }
                if (pageAccueil.getSec5Blog4Img() != null) {
                    existingPageAccueil.setSec5Blog4Img(pageAccueil.getSec5Blog4Img());
                }
                if (pageAccueil.getSec5Blog4ImgContentType() != null) {
                    existingPageAccueil.setSec5Blog4ImgContentType(pageAccueil.getSec5Blog4ImgContentType());
                }
                if (pageAccueil.getSec5Blog5Img() != null) {
                    existingPageAccueil.setSec5Blog5Img(pageAccueil.getSec5Blog5Img());
                }
                if (pageAccueil.getSec5Blog5ImgContentType() != null) {
                    existingPageAccueil.setSec5Blog5ImgContentType(pageAccueil.getSec5Blog5ImgContentType());
                }
                if (pageAccueil.getSec5Blog6Img() != null) {
                    existingPageAccueil.setSec5Blog6Img(pageAccueil.getSec5Blog6Img());
                }
                if (pageAccueil.getSec5Blog6ImgContentType() != null) {
                    existingPageAccueil.setSec5Blog6ImgContentType(pageAccueil.getSec5Blog6ImgContentType());
                }
                if (pageAccueil.getSec5Blog7Img() != null) {
                    existingPageAccueil.setSec5Blog7Img(pageAccueil.getSec5Blog7Img());
                }
                if (pageAccueil.getSec5Blog7ImgContentType() != null) {
                    existingPageAccueil.setSec5Blog7ImgContentType(pageAccueil.getSec5Blog7ImgContentType());
                }
                if (pageAccueil.getSec5Blog1SousTitre() != null) {
                    existingPageAccueil.setSec5Blog1SousTitre(pageAccueil.getSec5Blog1SousTitre());
                }
                if (pageAccueil.getSec5Blog1SousTitreContentType() != null) {
                    existingPageAccueil.setSec5Blog1SousTitreContentType(pageAccueil.getSec5Blog1SousTitreContentType());
                }
                if (pageAccueil.getSec5Blog2SousTitre() != null) {
                    existingPageAccueil.setSec5Blog2SousTitre(pageAccueil.getSec5Blog2SousTitre());
                }
                if (pageAccueil.getSec5Blog2SousTitreContentType() != null) {
                    existingPageAccueil.setSec5Blog2SousTitreContentType(pageAccueil.getSec5Blog2SousTitreContentType());
                }
                if (pageAccueil.getSec5Blog3SousTitre() != null) {
                    existingPageAccueil.setSec5Blog3SousTitre(pageAccueil.getSec5Blog3SousTitre());
                }
                if (pageAccueil.getSec5Blog3SousTitreContentType() != null) {
                    existingPageAccueil.setSec5Blog3SousTitreContentType(pageAccueil.getSec5Blog3SousTitreContentType());
                }
                if (pageAccueil.getSec5Blog4SousTitre() != null) {
                    existingPageAccueil.setSec5Blog4SousTitre(pageAccueil.getSec5Blog4SousTitre());
                }
                if (pageAccueil.getSec5Blog4SousTitreContentType() != null) {
                    existingPageAccueil.setSec5Blog4SousTitreContentType(pageAccueil.getSec5Blog4SousTitreContentType());
                }
                if (pageAccueil.getSec5Blog5SousTitre() != null) {
                    existingPageAccueil.setSec5Blog5SousTitre(pageAccueil.getSec5Blog5SousTitre());
                }
                if (pageAccueil.getSec5Blog5SousTitreContentType() != null) {
                    existingPageAccueil.setSec5Blog5SousTitreContentType(pageAccueil.getSec5Blog5SousTitreContentType());
                }
                if (pageAccueil.getSec5Blog6SousTitre() != null) {
                    existingPageAccueil.setSec5Blog6SousTitre(pageAccueil.getSec5Blog6SousTitre());
                }
                if (pageAccueil.getSec5Blog6SousTitreContentType() != null) {
                    existingPageAccueil.setSec5Blog6SousTitreContentType(pageAccueil.getSec5Blog6SousTitreContentType());
                }
                if (pageAccueil.getSec5Blog7SousTitre() != null) {
                    existingPageAccueil.setSec5Blog7SousTitre(pageAccueil.getSec5Blog7SousTitre());
                }
                if (pageAccueil.getSec5Blog7SousTitreContentType() != null) {
                    existingPageAccueil.setSec5Blog7SousTitreContentType(pageAccueil.getSec5Blog7SousTitreContentType());
                }
                if (pageAccueil.getSec5Blog1Text() != null) {
                    existingPageAccueil.setSec5Blog1Text(pageAccueil.getSec5Blog1Text());
                }
                if (pageAccueil.getSec5Blog2Text() != null) {
                    existingPageAccueil.setSec5Blog2Text(pageAccueil.getSec5Blog2Text());
                }
                if (pageAccueil.getSec5Blog3Text() != null) {
                    existingPageAccueil.setSec5Blog3Text(pageAccueil.getSec5Blog3Text());
                }
                if (pageAccueil.getSec5Blog4Text() != null) {
                    existingPageAccueil.setSec5Blog4Text(pageAccueil.getSec5Blog4Text());
                }
                if (pageAccueil.getSec5Blog5Text() != null) {
                    existingPageAccueil.setSec5Blog5Text(pageAccueil.getSec5Blog5Text());
                }
                if (pageAccueil.getSec5Blog6Text() != null) {
                    existingPageAccueil.setSec5Blog6Text(pageAccueil.getSec5Blog6Text());
                }
                if (pageAccueil.getSec5Blog7Text() != null) {
                    existingPageAccueil.setSec5Blog7Text(pageAccueil.getSec5Blog7Text());
                }
                if (pageAccueil.getSec6Titre() != null) {
                    existingPageAccueil.setSec6Titre(pageAccueil.getSec6Titre());
                }
                if (pageAccueil.getSec6Img() != null) {
                    existingPageAccueil.setSec6Img(pageAccueil.getSec6Img());
                }
                if (pageAccueil.getSec6ImgContentType() != null) {
                    existingPageAccueil.setSec6ImgContentType(pageAccueil.getSec6ImgContentType());
                }
                if (pageAccueil.getSec6Text() != null) {
                    existingPageAccueil.setSec6Text(pageAccueil.getSec6Text());
                }
                if (pageAccueil.getSec7Titre() != null) {
                    existingPageAccueil.setSec7Titre(pageAccueil.getSec7Titre());
                }
                if (pageAccueil.getSec7Img() != null) {
                    existingPageAccueil.setSec7Img(pageAccueil.getSec7Img());
                }
                if (pageAccueil.getSec7ImgContentType() != null) {
                    existingPageAccueil.setSec7ImgContentType(pageAccueil.getSec7ImgContentType());
                }
                if (pageAccueil.getSec7Text() != null) {
                    existingPageAccueil.setSec7Text(pageAccueil.getSec7Text());
                }
                if (pageAccueil.getSec8Titre() != null) {
                    existingPageAccueil.setSec8Titre(pageAccueil.getSec8Titre());
                }
                if (pageAccueil.getSec8Img() != null) {
                    existingPageAccueil.setSec8Img(pageAccueil.getSec8Img());
                }
                if (pageAccueil.getSec8ImgContentType() != null) {
                    existingPageAccueil.setSec8ImgContentType(pageAccueil.getSec8ImgContentType());
                }
                if (pageAccueil.getSec8Text() != null) {
                    existingPageAccueil.setSec8Text(pageAccueil.getSec8Text());
                }
                if (pageAccueil.getSec9Titre() != null) {
                    existingPageAccueil.setSec9Titre(pageAccueil.getSec9Titre());
                }
                if (pageAccueil.getSec9Img() != null) {
                    existingPageAccueil.setSec9Img(pageAccueil.getSec9Img());
                }
                if (pageAccueil.getSec9ImgContentType() != null) {
                    existingPageAccueil.setSec9ImgContentType(pageAccueil.getSec9ImgContentType());
                }
                if (pageAccueil.getSec9Text() != null) {
                    existingPageAccueil.setSec9Text(pageAccueil.getSec9Text());
                }

                return existingPageAccueil;
            })
            .map(pageAccueilRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageAccueil.getId().toString())
        );
    }

    /**
     * {@code GET  /page-accueils} : get all the pageAccueils.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageAccueils in body.
     */
    @GetMapping("/page-accueils")
    public List<PageAccueil> getAllPageAccueils() {
        log.debug("REST request to get all PageAccueils");
        return pageAccueilRepository.findAll();
    }

    /**
     * {@code GET  /page-accueils/:id} : get the "id" pageAccueil.
     *
     * @param id the id of the pageAccueil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageAccueil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-accueils/{id}")
    public ResponseEntity<PageAccueil> getPageAccueil(@PathVariable Long id) {
        log.debug("REST request to get PageAccueil : {}", id);
        Optional<PageAccueil> pageAccueil = pageAccueilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pageAccueil);
    }

    /**
     * {@code DELETE  /page-accueils/:id} : delete the "id" pageAccueil.
     *
     * @param id the id of the pageAccueil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-accueils/{id}")
    public ResponseEntity<Void> deletePageAccueil(@PathVariable Long id) {
        log.debug("REST request to delete PageAccueil : {}", id);
        pageAccueilRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
