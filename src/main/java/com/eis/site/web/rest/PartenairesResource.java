package com.eis.site.web.rest;

import com.eis.site.domain.Partenaires;
import com.eis.site.repository.PartenairesRepository;
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
 * REST controller for managing {@link com.eis.site.domain.Partenaires}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartenairesResource {

    private final Logger log = LoggerFactory.getLogger(PartenairesResource.class);

    private static final String ENTITY_NAME = "partenaires";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartenairesRepository partenairesRepository;

    public PartenairesResource(PartenairesRepository partenairesRepository) {
        this.partenairesRepository = partenairesRepository;
    }

    /**
     * {@code POST  /partenaires} : Create a new partenaires.
     *
     * @param partenaires the partenaires to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partenaires, or with status {@code 400 (Bad Request)} if the partenaires has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partenaires")
    public ResponseEntity<Partenaires> createPartenaires(@RequestBody Partenaires partenaires) throws URISyntaxException {
        log.debug("REST request to save Partenaires : {}", partenaires);
        if (partenaires.getId() != null) {
            throw new BadRequestAlertException("A new partenaires cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Partenaires result = partenairesRepository.save(partenaires);
        return ResponseEntity
            .created(new URI("/api/partenaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partenaires/:id} : Updates an existing partenaires.
     *
     * @param id the id of the partenaires to save.
     * @param partenaires the partenaires to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partenaires,
     * or with status {@code 400 (Bad Request)} if the partenaires is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partenaires couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partenaires/{id}")
    public ResponseEntity<Partenaires> updatePartenaires(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Partenaires partenaires
    ) throws URISyntaxException {
        log.debug("REST request to update Partenaires : {}, {}", id, partenaires);
        if (partenaires.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partenaires.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partenairesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Partenaires result = partenairesRepository.save(partenaires);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partenaires.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /partenaires/:id} : Partial updates given fields of an existing partenaires, field will ignore if it is null
     *
     * @param id the id of the partenaires to save.
     * @param partenaires the partenaires to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partenaires,
     * or with status {@code 400 (Bad Request)} if the partenaires is not valid,
     * or with status {@code 404 (Not Found)} if the partenaires is not found,
     * or with status {@code 500 (Internal Server Error)} if the partenaires couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/partenaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Partenaires> partialUpdatePartenaires(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Partenaires partenaires
    ) throws URISyntaxException {
        log.debug("REST request to partial update Partenaires partially : {}, {}", id, partenaires);
        if (partenaires.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partenaires.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partenairesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Partenaires> result = partenairesRepository
            .findById(partenaires.getId())
            .map(existingPartenaires -> {
                if (partenaires.getNom() != null) {
                    existingPartenaires.setNom(partenaires.getNom());
                }
                if (partenaires.getPhoto() != null) {
                    existingPartenaires.setPhoto(partenaires.getPhoto());
                }
                if (partenaires.getPhotoContentType() != null) {
                    existingPartenaires.setPhotoContentType(partenaires.getPhotoContentType());
                }
                if (partenaires.getDescription() != null) {
                    existingPartenaires.setDescription(partenaires.getDescription());
                }
                if (partenaires.getLien() != null) {
                    existingPartenaires.setLien(partenaires.getLien());
                }

                return existingPartenaires;
            })
            .map(partenairesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partenaires.getId().toString())
        );
    }

    /**
     * {@code GET  /partenaires} : get all the partenaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partenaires in body.
     */
    @GetMapping("/partenaires")
    public List<Partenaires> getAllPartenaires() {
        log.debug("REST request to get all Partenaires");
        return partenairesRepository.findAll();
    }

    /**
     * {@code GET  /partenaires/:id} : get the "id" partenaires.
     *
     * @param id the id of the partenaires to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partenaires, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partenaires/{id}")
    public ResponseEntity<Partenaires> getPartenaires(@PathVariable Long id) {
        log.debug("REST request to get Partenaires : {}", id);
        Optional<Partenaires> partenaires = partenairesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partenaires);
    }

    /**
     * {@code DELETE  /partenaires/:id} : delete the "id" partenaires.
     *
     * @param id the id of the partenaires to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partenaires/{id}")
    public ResponseEntity<Void> deletePartenaires(@PathVariable Long id) {
        log.debug("REST request to delete Partenaires : {}", id);
        partenairesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
