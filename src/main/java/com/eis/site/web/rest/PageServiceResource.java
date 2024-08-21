package com.eis.site.web.rest;

import com.eis.site.domain.PageService;
import com.eis.site.repository.PageServiceRepository;
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
 * REST controller for managing {@link com.eis.site.domain.PageService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PageServiceResource {

    private final Logger log = LoggerFactory.getLogger(PageServiceResource.class);

    private static final String ENTITY_NAME = "pageService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageServiceRepository pageServiceRepository;

    public PageServiceResource(PageServiceRepository pageServiceRepository) {
        this.pageServiceRepository = pageServiceRepository;
    }

    /**
     * {@code POST  /page-services} : Create a new pageService.
     *
     * @param pageService the pageService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageService, or with status {@code 400 (Bad Request)} if the pageService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-services")
    public ResponseEntity<PageService> createPageService(@RequestBody PageService pageService) throws URISyntaxException {
        log.debug("REST request to save PageService : {}", pageService);
        if (pageService.getId() != null) {
            throw new BadRequestAlertException("A new pageService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageService result = pageServiceRepository.save(pageService);
        return ResponseEntity
            .created(new URI("/api/page-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-services/:id} : Updates an existing pageService.
     *
     * @param id the id of the pageService to save.
     * @param pageService the pageService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageService,
     * or with status {@code 400 (Bad Request)} if the pageService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-services/{id}")
    public ResponseEntity<PageService> updatePageService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageService pageService
    ) throws URISyntaxException {
        log.debug("REST request to update PageService : {}, {}", id, pageService);
        if (pageService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PageService result = pageServiceRepository.save(pageService);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageService.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /page-services/:id} : Partial updates given fields of an existing pageService, field will ignore if it is null
     *
     * @param id the id of the pageService to save.
     * @param pageService the pageService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageService,
     * or with status {@code 400 (Bad Request)} if the pageService is not valid,
     * or with status {@code 404 (Not Found)} if the pageService is not found,
     * or with status {@code 500 (Internal Server Error)} if the pageService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/page-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PageService> partialUpdatePageService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageService pageService
    ) throws URISyntaxException {
        log.debug("REST request to partial update PageService partially : {}, {}", id, pageService);
        if (pageService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PageService> result = pageServiceRepository
            .findById(pageService.getId())
            .map(existingPageService -> {
                if (pageService.getTitre() != null) {
                    existingPageService.setTitre(pageService.getTitre());
                }
                if (pageService.getSoustitre() != null) {
                    existingPageService.setSoustitre(pageService.getSoustitre());
                }

                return existingPageService;
            })
            .map(pageServiceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageService.getId().toString())
        );
    }

    /**
     * {@code GET  /page-services} : get all the pageServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageServices in body.
     */
    @GetMapping("/page-services")
    public List<PageService> getAllPageServices() {
        log.debug("REST request to get all PageServices");
        return pageServiceRepository.findAll();
    }

    /**
     * {@code GET  /page-services/:id} : get the "id" pageService.
     *
     * @param id the id of the pageService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-services/{id}")
    public ResponseEntity<PageService> getPageService(@PathVariable Long id) {
        log.debug("REST request to get PageService : {}", id);
        Optional<PageService> pageService = pageServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pageService);
    }

    /**
     * {@code DELETE  /page-services/:id} : delete the "id" pageService.
     *
     * @param id the id of the pageService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-services/{id}")
    public ResponseEntity<Void> deletePageService(@PathVariable Long id) {
        log.debug("REST request to delete PageService : {}", id);
        pageServiceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
