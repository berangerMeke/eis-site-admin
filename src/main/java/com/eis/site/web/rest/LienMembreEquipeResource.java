package com.eis.site.web.rest;

import com.eis.site.domain.LienMembreEquipe;
import com.eis.site.repository.LienMembreEquipeRepository;
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
 * REST controller for managing {@link com.eis.site.domain.LienMembreEquipe}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LienMembreEquipeResource {

    private final Logger log = LoggerFactory.getLogger(LienMembreEquipeResource.class);

    private static final String ENTITY_NAME = "lienMembreEquipe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LienMembreEquipeRepository lienMembreEquipeRepository;

    public LienMembreEquipeResource(LienMembreEquipeRepository lienMembreEquipeRepository) {
        this.lienMembreEquipeRepository = lienMembreEquipeRepository;
    }

    /**
     * {@code POST  /lien-membre-equipes} : Create a new lienMembreEquipe.
     *
     * @param lienMembreEquipe the lienMembreEquipe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lienMembreEquipe, or with status {@code 400 (Bad Request)} if the lienMembreEquipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lien-membre-equipes")
    public ResponseEntity<LienMembreEquipe> createLienMembreEquipe(@RequestBody LienMembreEquipe lienMembreEquipe)
        throws URISyntaxException {
        log.debug("REST request to save LienMembreEquipe : {}", lienMembreEquipe);
        if (lienMembreEquipe.getId() != null) {
            throw new BadRequestAlertException("A new lienMembreEquipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LienMembreEquipe result = lienMembreEquipeRepository.save(lienMembreEquipe);
        return ResponseEntity
            .created(new URI("/api/lien-membre-equipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lien-membre-equipes/:id} : Updates an existing lienMembreEquipe.
     *
     * @param id the id of the lienMembreEquipe to save.
     * @param lienMembreEquipe the lienMembreEquipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lienMembreEquipe,
     * or with status {@code 400 (Bad Request)} if the lienMembreEquipe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lienMembreEquipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lien-membre-equipes/{id}")
    public ResponseEntity<LienMembreEquipe> updateLienMembreEquipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LienMembreEquipe lienMembreEquipe
    ) throws URISyntaxException {
        log.debug("REST request to update LienMembreEquipe : {}, {}", id, lienMembreEquipe);
        if (lienMembreEquipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lienMembreEquipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lienMembreEquipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LienMembreEquipe result = lienMembreEquipeRepository.save(lienMembreEquipe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lienMembreEquipe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lien-membre-equipes/:id} : Partial updates given fields of an existing lienMembreEquipe, field will ignore if it is null
     *
     * @param id the id of the lienMembreEquipe to save.
     * @param lienMembreEquipe the lienMembreEquipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lienMembreEquipe,
     * or with status {@code 400 (Bad Request)} if the lienMembreEquipe is not valid,
     * or with status {@code 404 (Not Found)} if the lienMembreEquipe is not found,
     * or with status {@code 500 (Internal Server Error)} if the lienMembreEquipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lien-membre-equipes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LienMembreEquipe> partialUpdateLienMembreEquipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LienMembreEquipe lienMembreEquipe
    ) throws URISyntaxException {
        log.debug("REST request to partial update LienMembreEquipe partially : {}, {}", id, lienMembreEquipe);
        if (lienMembreEquipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lienMembreEquipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lienMembreEquipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LienMembreEquipe> result = lienMembreEquipeRepository
            .findById(lienMembreEquipe.getId())
            .map(existingLienMembreEquipe -> {
                if (lienMembreEquipe.getFacebook() != null) {
                    existingLienMembreEquipe.setFacebook(lienMembreEquipe.getFacebook());
                }
                if (lienMembreEquipe.getWhatsapp() != null) {
                    existingLienMembreEquipe.setWhatsapp(lienMembreEquipe.getWhatsapp());
                }
                if (lienMembreEquipe.getLinkedin() != null) {
                    existingLienMembreEquipe.setLinkedin(lienMembreEquipe.getLinkedin());
                }
                if (lienMembreEquipe.getTwitter() != null) {
                    existingLienMembreEquipe.setTwitter(lienMembreEquipe.getTwitter());
                }

                return existingLienMembreEquipe;
            })
            .map(lienMembreEquipeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lienMembreEquipe.getId().toString())
        );
    }

    /**
     * {@code GET  /lien-membre-equipes} : get all the lienMembreEquipes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lienMembreEquipes in body.
     */
    @GetMapping("/lien-membre-equipes")
    public List<LienMembreEquipe> getAllLienMembreEquipes() {
        log.debug("REST request to get all LienMembreEquipes");
        return lienMembreEquipeRepository.findAll();
    }

    /**
     * {@code GET  /lien-membre-equipes/:id} : get the "id" lienMembreEquipe.
     *
     * @param id the id of the lienMembreEquipe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lienMembreEquipe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lien-membre-equipes/{id}")
    public ResponseEntity<LienMembreEquipe> getLienMembreEquipe(@PathVariable Long id) {
        log.debug("REST request to get LienMembreEquipe : {}", id);
        Optional<LienMembreEquipe> lienMembreEquipe = lienMembreEquipeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lienMembreEquipe);
    }

    /**
     * {@code DELETE  /lien-membre-equipes/:id} : delete the "id" lienMembreEquipe.
     *
     * @param id the id of the lienMembreEquipe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lien-membre-equipes/{id}")
    public ResponseEntity<Void> deleteLienMembreEquipe(@PathVariable Long id) {
        log.debug("REST request to delete LienMembreEquipe : {}", id);
        lienMembreEquipeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
