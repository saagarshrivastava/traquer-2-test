package com.test.traquer2.web.rest;

import com.test.traquer2.domain.MajorIncident;
import com.test.traquer2.repository.MajorIncidentRepository;
import com.test.traquer2.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.test.traquer2.domain.MajorIncident}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MajorIncidentResource {

    private final Logger log = LoggerFactory.getLogger(MajorIncidentResource.class);

    private static final String ENTITY_NAME = "majorIncident";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MajorIncidentRepository majorIncidentRepository;

    public MajorIncidentResource(MajorIncidentRepository majorIncidentRepository) {
        this.majorIncidentRepository = majorIncidentRepository;
    }

    /**
     * {@code POST  /major-incidents} : Create a new majorIncident.
     *
     * @param majorIncident the majorIncident to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new majorIncident, or with status {@code 400 (Bad Request)} if the majorIncident has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/major-incidents")
    public ResponseEntity<MajorIncident> createMajorIncident(@RequestBody MajorIncident majorIncident) throws URISyntaxException {
        log.debug("REST request to save MajorIncident : {}", majorIncident);
        if (majorIncident.getId() != null) {
            throw new BadRequestAlertException("A new majorIncident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MajorIncident result = majorIncidentRepository.save(majorIncident);
        return ResponseEntity.created(new URI("/api/major-incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /major-incidents} : Updates an existing majorIncident.
     *
     * @param majorIncident the majorIncident to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated majorIncident,
     * or with status {@code 400 (Bad Request)} if the majorIncident is not valid,
     * or with status {@code 500 (Internal Server Error)} if the majorIncident couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/major-incidents")
    public ResponseEntity<MajorIncident> updateMajorIncident(@RequestBody MajorIncident majorIncident) throws URISyntaxException {
        log.debug("REST request to update MajorIncident : {}", majorIncident);
        if (majorIncident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MajorIncident result = majorIncidentRepository.save(majorIncident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, majorIncident.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /major-incidents} : get all the majorIncidents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of majorIncidents in body.
     */
    @GetMapping("/major-incidents")
    public List<MajorIncident> getAllMajorIncidents() {
        log.debug("REST request to get all MajorIncidents");
        return majorIncidentRepository.findAll();
    }

    /**
     * {@code GET  /major-incidents/:id} : get the "id" majorIncident.
     *
     * @param id the id of the majorIncident to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the majorIncident, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/major-incidents/{id}")
    public ResponseEntity<MajorIncident> getMajorIncident(@PathVariable Long id) {
        log.debug("REST request to get MajorIncident : {}", id);
        Optional<MajorIncident> majorIncident = majorIncidentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(majorIncident);
    }

    /**
     * {@code DELETE  /major-incidents/:id} : delete the "id" majorIncident.
     *
     * @param id the id of the majorIncident to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/major-incidents/{id}")
    public ResponseEntity<Void> deleteMajorIncident(@PathVariable Long id) {
        log.debug("REST request to delete MajorIncident : {}", id);
        majorIncidentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
