package com.eis.site.web.rest;

import com.eis.site.domain.Formation;
import com.eis.site.repository.FormationRepository;
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
 * REST controller for managing {@link com.eis.site.domain.Formation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FormationResource {

    private final Logger log = LoggerFactory.getLogger(FormationResource.class);

    private static final String ENTITY_NAME = "formation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormationRepository formationRepository;

    public FormationResource(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    /**
     * {@code POST  /formations} : Create a new formation.
     *
     * @param formation the formation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formation, or with status {@code 400 (Bad Request)} if the formation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formations")
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) throws URISyntaxException {
        log.debug("REST request to save Formation : {}", formation);
        if (formation.getId() != null) {
            throw new BadRequestAlertException("A new formation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Formation result = formationRepository.save(formation);
        return ResponseEntity
            .created(new URI("/api/formations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formations/:id} : Updates an existing formation.
     *
     * @param id the id of the formation to save.
     * @param formation the formation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formation,
     * or with status {@code 400 (Bad Request)} if the formation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formations/{id}")
    public ResponseEntity<Formation> updateFormation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formation formation
    ) throws URISyntaxException {
        log.debug("REST request to update Formation : {}, {}", id, formation);
        if (formation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Formation result = formationRepository.save(formation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /formations/:id} : Partial updates given fields of an existing formation, field will ignore if it is null
     *
     * @param id the id of the formation to save.
     * @param formation the formation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formation,
     * or with status {@code 400 (Bad Request)} if the formation is not valid,
     * or with status {@code 404 (Not Found)} if the formation is not found,
     * or with status {@code 500 (Internal Server Error)} if the formation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/formations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Formation> partialUpdateFormation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formation formation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formation partially : {}, {}", id, formation);
        if (formation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Formation> result = formationRepository
            .findById(formation.getId())
            .map(existingFormation -> {
                if (formation.getNom() != null) {
                    existingFormation.setNom(formation.getNom());
                }
                if (formation.getPhoto() != null) {
                    existingFormation.setPhoto(formation.getPhoto());
                }
                if (formation.getPhotoContentType() != null) {
                    existingFormation.setPhotoContentType(formation.getPhotoContentType());
                }
                if (formation.getTexte() != null) {
                    existingFormation.setTexte(formation.getTexte());
                }
                if (formation.getCout() != null) {
                    existingFormation.setCout(formation.getCout());
                }
                if (formation.getDuree() != null) {
                    existingFormation.setDuree(formation.getDuree());
                }
                if (formation.getCategorie() != null) {
                    existingFormation.setCategorie(formation.getCategorie());
                }
                if (formation.getLien() != null) {
                    existingFormation.setLien(formation.getLien());
                }

                return existingFormation;
            })
            .map(formationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formation.getId().toString())
        );
    }

    /**
     * {@code GET  /formations} : get all the formations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formations in body.
     */
    @GetMapping("/formations")
    public List<Formation> getAllFormations() {
        log.debug("REST request to get all Formations");
        return formationRepository.findAll();
    }

    /**
     * {@code GET  /formations/:id} : get the "id" formation.
     *
     * @param id the id of the formation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formations/{id}")
    public ResponseEntity<Formation> getFormation(@PathVariable Long id) {
        log.debug("REST request to get Formation : {}", id);
        Optional<Formation> formation = formationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formation);
    }

    /**
     * {@code DELETE  /formations/:id} : delete the "id" formation.
     *
     * @param id the id of the formation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formations/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        log.debug("REST request to delete Formation : {}", id);
        formationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
