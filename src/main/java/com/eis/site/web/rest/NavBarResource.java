package com.eis.site.web.rest;

import com.eis.site.domain.NavBar;
import com.eis.site.repository.NavBarRepository;
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
 * REST controller for managing {@link com.eis.site.domain.NavBar}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NavBarResource {

    private final Logger log = LoggerFactory.getLogger(NavBarResource.class);

    private static final String ENTITY_NAME = "navBar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NavBarRepository navBarRepository;

    public NavBarResource(NavBarRepository navBarRepository) {
        this.navBarRepository = navBarRepository;
    }

    /**
     * {@code POST  /nav-bars} : Create a new navBar.
     *
     * @param navBar the navBar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new navBar, or with status {@code 400 (Bad Request)} if the navBar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nav-bars")
    public ResponseEntity<NavBar> createNavBar(@RequestBody NavBar navBar) throws URISyntaxException {
        log.debug("REST request to save NavBar : {}", navBar);
        if (navBar.getId() != null) {
            throw new BadRequestAlertException("A new navBar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NavBar result = navBarRepository.save(navBar);
        return ResponseEntity
            .created(new URI("/api/nav-bars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nav-bars/:id} : Updates an existing navBar.
     *
     * @param id the id of the navBar to save.
     * @param navBar the navBar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated navBar,
     * or with status {@code 400 (Bad Request)} if the navBar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the navBar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nav-bars/{id}")
    public ResponseEntity<NavBar> updateNavBar(@PathVariable(value = "id", required = false) final Long id, @RequestBody NavBar navBar)
        throws URISyntaxException {
        log.debug("REST request to update NavBar : {}, {}", id, navBar);
        if (navBar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, navBar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!navBarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NavBar result = navBarRepository.save(navBar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, navBar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nav-bars/:id} : Partial updates given fields of an existing navBar, field will ignore if it is null
     *
     * @param id the id of the navBar to save.
     * @param navBar the navBar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated navBar,
     * or with status {@code 400 (Bad Request)} if the navBar is not valid,
     * or with status {@code 404 (Not Found)} if the navBar is not found,
     * or with status {@code 500 (Internal Server Error)} if the navBar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nav-bars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NavBar> partialUpdateNavBar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NavBar navBar
    ) throws URISyntaxException {
        log.debug("REST request to partial update NavBar partially : {}, {}", id, navBar);
        if (navBar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, navBar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!navBarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NavBar> result = navBarRepository
            .findById(navBar.getId())
            .map(existingNavBar -> {
                if (navBar.getLogo() != null) {
                    existingNavBar.setLogo(navBar.getLogo());
                }
                if (navBar.getLogoContentType() != null) {
                    existingNavBar.setLogoContentType(navBar.getLogoContentType());
                }
                if (navBar.getMenu1() != null) {
                    existingNavBar.setMenu1(navBar.getMenu1());
                }
                if (navBar.getMenu2() != null) {
                    existingNavBar.setMenu2(navBar.getMenu2());
                }
                if (navBar.getMenu3() != null) {
                    existingNavBar.setMenu3(navBar.getMenu3());
                }
                if (navBar.getMenu4() != null) {
                    existingNavBar.setMenu4(navBar.getMenu4());
                }
                if (navBar.getMenu5() != null) {
                    existingNavBar.setMenu5(navBar.getMenu5());
                }
                if (navBar.getMenu6() != null) {
                    existingNavBar.setMenu6(navBar.getMenu6());
                }

                return existingNavBar;
            })
            .map(navBarRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, navBar.getId().toString())
        );
    }

    /**
     * {@code GET  /nav-bars} : get all the navBars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of navBars in body.
     */
    @GetMapping("/nav-bars")
    public List<NavBar> getAllNavBars() {
        log.debug("REST request to get all NavBars");
        return navBarRepository.findAll();
    }

    /**
     * {@code GET  /nav-bars/:id} : get the "id" navBar.
     *
     * @param id the id of the navBar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the navBar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nav-bars/{id}")
    public ResponseEntity<NavBar> getNavBar(@PathVariable Long id) {
        log.debug("REST request to get NavBar : {}", id);
        Optional<NavBar> navBar = navBarRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(navBar);
    }

    /**
     * {@code DELETE  /nav-bars/:id} : delete the "id" navBar.
     *
     * @param id the id of the navBar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nav-bars/{id}")
    public ResponseEntity<Void> deleteNavBar(@PathVariable Long id) {
        log.debug("REST request to delete NavBar : {}", id);
        navBarRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
