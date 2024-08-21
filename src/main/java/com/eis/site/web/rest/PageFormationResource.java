package com.eis.site.web.rest;

import com.eis.site.domain.PageFormation;
import com.eis.site.repository.PageFormationRepository;
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
 * REST controller for managing {@link com.eis.site.domain.PageFormation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PageFormationResource {

    private final Logger log = LoggerFactory.getLogger(PageFormationResource.class);

    private static final String ENTITY_NAME = "pageFormation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageFormationRepository pageFormationRepository;

    public PageFormationResource(PageFormationRepository pageFormationRepository) {
        this.pageFormationRepository = pageFormationRepository;
    }

    /**
     * {@code POST  /page-formations} : Create a new pageFormation.
     *
     * @param pageFormation the pageFormation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageFormation, or with status {@code 400 (Bad Request)} if the pageFormation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-formations")
    public ResponseEntity<PageFormation> createPageFormation(@RequestBody PageFormation pageFormation) throws URISyntaxException {
        log.debug("REST request to save PageFormation : {}", pageFormation);
        if (pageFormation.getId() != null) {
            throw new BadRequestAlertException("A new pageFormation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageFormation result = pageFormationRepository.save(pageFormation);
        return ResponseEntity
            .created(new URI("/api/page-formations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-formations/:id} : Updates an existing pageFormation.
     *
     * @param id the id of the pageFormation to save.
     * @param pageFormation the pageFormation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageFormation,
     * or with status {@code 400 (Bad Request)} if the pageFormation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageFormation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-formations/{id}")
    public ResponseEntity<PageFormation> updatePageFormation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageFormation pageFormation
    ) throws URISyntaxException {
        log.debug("REST request to update PageFormation : {}, {}", id, pageFormation);
        if (pageFormation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageFormation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageFormationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PageFormation result = pageFormationRepository.save(pageFormation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageFormation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /page-formations/:id} : Partial updates given fields of an existing pageFormation, field will ignore if it is null
     *
     * @param id the id of the pageFormation to save.
     * @param pageFormation the pageFormation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageFormation,
     * or with status {@code 400 (Bad Request)} if the pageFormation is not valid,
     * or with status {@code 404 (Not Found)} if the pageFormation is not found,
     * or with status {@code 500 (Internal Server Error)} if the pageFormation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/page-formations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PageFormation> partialUpdatePageFormation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageFormation pageFormation
    ) throws URISyntaxException {
        log.debug("REST request to partial update PageFormation partially : {}, {}", id, pageFormation);
        if (pageFormation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageFormation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageFormationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PageFormation> result = pageFormationRepository
            .findById(pageFormation.getId())
            .map(existingPageFormation -> {
                if (pageFormation.getTitre() != null) {
                    existingPageFormation.setTitre(pageFormation.getTitre());
                }
                if (pageFormation.getSousTitre() != null) {
                    existingPageFormation.setSousTitre(pageFormation.getSousTitre());
                }

                return existingPageFormation;
            })
            .map(pageFormationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageFormation.getId().toString())
        );
    }

    /**
     * {@code GET  /page-formations} : get all the pageFormations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageFormations in body.
     */
    @GetMapping("/page-formations")
    public List<PageFormation> getAllPageFormations() {
        log.debug("REST request to get all PageFormations");
        return pageFormationRepository.findAll();
    }

    /**
     * {@code GET  /page-formations/:id} : get the "id" pageFormation.
     *
     * @param id the id of the pageFormation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageFormation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-formations/{id}")
    public ResponseEntity<PageFormation> getPageFormation(@PathVariable Long id) {
        log.debug("REST request to get PageFormation : {}", id);
        Optional<PageFormation> pageFormation = pageFormationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pageFormation);
    }

    /**
     * {@code DELETE  /page-formations/:id} : delete the "id" pageFormation.
     *
     * @param id the id of the pageFormation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-formations/{id}")
    public ResponseEntity<Void> deletePageFormation(@PathVariable Long id) {
        log.debug("REST request to delete PageFormation : {}", id);
        pageFormationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
