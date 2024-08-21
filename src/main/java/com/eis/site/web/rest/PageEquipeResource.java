package com.eis.site.web.rest;

import com.eis.site.domain.PageEquipe;
import com.eis.site.repository.PageEquipeRepository;
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
 * REST controller for managing {@link com.eis.site.domain.PageEquipe}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PageEquipeResource {

    private final Logger log = LoggerFactory.getLogger(PageEquipeResource.class);

    private static final String ENTITY_NAME = "pageEquipe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageEquipeRepository pageEquipeRepository;

    public PageEquipeResource(PageEquipeRepository pageEquipeRepository) {
        this.pageEquipeRepository = pageEquipeRepository;
    }

    /**
     * {@code POST  /page-equipes} : Create a new pageEquipe.
     *
     * @param pageEquipe the pageEquipe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageEquipe, or with status {@code 400 (Bad Request)} if the pageEquipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-equipes")
    public ResponseEntity<PageEquipe> createPageEquipe(@RequestBody PageEquipe pageEquipe) throws URISyntaxException {
        log.debug("REST request to save PageEquipe : {}", pageEquipe);
        if (pageEquipe.getId() != null) {
            throw new BadRequestAlertException("A new pageEquipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageEquipe result = pageEquipeRepository.save(pageEquipe);
        return ResponseEntity
            .created(new URI("/api/page-equipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-equipes/:id} : Updates an existing pageEquipe.
     *
     * @param id the id of the pageEquipe to save.
     * @param pageEquipe the pageEquipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageEquipe,
     * or with status {@code 400 (Bad Request)} if the pageEquipe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageEquipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-equipes/{id}")
    public ResponseEntity<PageEquipe> updatePageEquipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageEquipe pageEquipe
    ) throws URISyntaxException {
        log.debug("REST request to update PageEquipe : {}, {}", id, pageEquipe);
        if (pageEquipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageEquipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageEquipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PageEquipe result = pageEquipeRepository.save(pageEquipe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageEquipe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /page-equipes/:id} : Partial updates given fields of an existing pageEquipe, field will ignore if it is null
     *
     * @param id the id of the pageEquipe to save.
     * @param pageEquipe the pageEquipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageEquipe,
     * or with status {@code 400 (Bad Request)} if the pageEquipe is not valid,
     * or with status {@code 404 (Not Found)} if the pageEquipe is not found,
     * or with status {@code 500 (Internal Server Error)} if the pageEquipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/page-equipes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PageEquipe> partialUpdatePageEquipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageEquipe pageEquipe
    ) throws URISyntaxException {
        log.debug("REST request to partial update PageEquipe partially : {}, {}", id, pageEquipe);
        if (pageEquipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageEquipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageEquipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PageEquipe> result = pageEquipeRepository
            .findById(pageEquipe.getId())
            .map(existingPageEquipe -> {
                if (pageEquipe.getSec1Img() != null) {
                    existingPageEquipe.setSec1Img(pageEquipe.getSec1Img());
                }
                if (pageEquipe.getSec1Titre() != null) {
                    existingPageEquipe.setSec1Titre(pageEquipe.getSec1Titre());
                }
                if (pageEquipe.getSec1Desc() != null) {
                    existingPageEquipe.setSec1Desc(pageEquipe.getSec1Desc());
                }

                return existingPageEquipe;
            })
            .map(pageEquipeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageEquipe.getId().toString())
        );
    }

    /**
     * {@code GET  /page-equipes} : get all the pageEquipes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageEquipes in body.
     */
    @GetMapping("/page-equipes")
    public List<PageEquipe> getAllPageEquipes() {
        log.debug("REST request to get all PageEquipes");
        return pageEquipeRepository.findAll();
    }

    /**
     * {@code GET  /page-equipes/:id} : get the "id" pageEquipe.
     *
     * @param id the id of the pageEquipe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageEquipe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-equipes/{id}")
    public ResponseEntity<PageEquipe> getPageEquipe(@PathVariable Long id) {
        log.debug("REST request to get PageEquipe : {}", id);
        Optional<PageEquipe> pageEquipe = pageEquipeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pageEquipe);
    }

    /**
     * {@code DELETE  /page-equipes/:id} : delete the "id" pageEquipe.
     *
     * @param id the id of the pageEquipe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-equipes/{id}")
    public ResponseEntity<Void> deletePageEquipe(@PathVariable Long id) {
        log.debug("REST request to delete PageEquipe : {}", id);
        pageEquipeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
