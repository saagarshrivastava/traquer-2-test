package com.test.traquer2.web.rest;

import com.test.traquer2.domain.Incident;
import com.test.traquer2.repository.IncidentRepository;
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
 * REST controller for managing {@link com.test.traquer2.domain.Incident}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IncidentResource {

    private final Logger log = LoggerFactory.getLogger(IncidentResource.class);

    private static final String ENTITY_NAME = "incident";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncidentRepository incidentRepository;

    public IncidentResource(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    /**
     * {@code POST  /incidents} : Create a new incident.
     *
     * @param incident the incident to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incident, or with status {@code 400 (Bad Request)} if the incident has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incidents")
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) throws URISyntaxException {
        log.debug("REST request to save Incident : {}", incident);
        if (incident.getId() != null) {
            throw new BadRequestAlertException("A new incident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Incident result = incidentRepository.save(incident);
        return ResponseEntity.created(new URI("/api/incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incidents} : Updates an existing incident.
     *
     * @param incident the incident to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incident,
     * or with status {@code 400 (Bad Request)} if the incident is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incident couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incidents")
    public ResponseEntity<Incident> updateIncident(@RequestBody Incident incident) throws URISyntaxException {
        log.debug("REST request to update Incident : {}", incident);
        if (incident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Incident result = incidentRepository.save(incident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incident.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incidents} : get all the incidents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incidents in body.
     */
    @GetMapping("/incidents")
    public List<Incident> getAllIncidents() {
        log.debug("REST request to get all Incidents");
        return incidentRepository.findAll();
    }

    /**
     * {@code GET  /incidents/:id} : get the "id" incident.
     *
     * @param id the id of the incident to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incident, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incidents/{id}")
    public ResponseEntity<Incident> getIncident(@PathVariable Long id) {
        log.debug("REST request to get Incident : {}", id);
        Optional<Incident> incident = incidentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(incident);
    }

    /**
     * {@code DELETE  /incidents/:id} : delete the "id" incident.
     *
     * @param id the id of the incident to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incidents/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        log.debug("REST request to delete Incident : {}", id);
        incidentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
