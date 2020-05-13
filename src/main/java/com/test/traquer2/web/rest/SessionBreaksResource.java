package com.test.traquer2.web.rest;

import com.test.traquer2.domain.SessionBreaks;
import com.test.traquer2.repository.SessionBreaksRepository;
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
 * REST controller for managing {@link com.test.traquer2.domain.SessionBreaks}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SessionBreaksResource {

    private final Logger log = LoggerFactory.getLogger(SessionBreaksResource.class);

    private static final String ENTITY_NAME = "sessionBreaks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SessionBreaksRepository sessionBreaksRepository;

    public SessionBreaksResource(SessionBreaksRepository sessionBreaksRepository) {
        this.sessionBreaksRepository = sessionBreaksRepository;
    }

    /**
     * {@code POST  /session-breaks} : Create a new sessionBreaks.
     *
     * @param sessionBreaks the sessionBreaks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionBreaks, or with status {@code 400 (Bad Request)} if the sessionBreaks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/session-breaks")
    public ResponseEntity<SessionBreaks> createSessionBreaks(@RequestBody SessionBreaks sessionBreaks) throws URISyntaxException {
        log.debug("REST request to save SessionBreaks : {}", sessionBreaks);
        if (sessionBreaks.getId() != null) {
            throw new BadRequestAlertException("A new sessionBreaks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SessionBreaks result = sessionBreaksRepository.save(sessionBreaks);
        return ResponseEntity.created(new URI("/api/session-breaks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /session-breaks} : Updates an existing sessionBreaks.
     *
     * @param sessionBreaks the sessionBreaks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionBreaks,
     * or with status {@code 400 (Bad Request)} if the sessionBreaks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sessionBreaks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/session-breaks")
    public ResponseEntity<SessionBreaks> updateSessionBreaks(@RequestBody SessionBreaks sessionBreaks) throws URISyntaxException {
        log.debug("REST request to update SessionBreaks : {}", sessionBreaks);
        if (sessionBreaks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SessionBreaks result = sessionBreaksRepository.save(sessionBreaks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionBreaks.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /session-breaks} : get all the sessionBreaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessionBreaks in body.
     */
    @GetMapping("/session-breaks")
    public List<SessionBreaks> getAllSessionBreaks() {
        log.debug("REST request to get all SessionBreaks");
        return sessionBreaksRepository.findAll();
    }

    /**
     * {@code GET  /session-breaks/:id} : get the "id" sessionBreaks.
     *
     * @param id the id of the sessionBreaks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionBreaks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/session-breaks/{id}")
    public ResponseEntity<SessionBreaks> getSessionBreaks(@PathVariable Long id) {
        log.debug("REST request to get SessionBreaks : {}", id);
        Optional<SessionBreaks> sessionBreaks = sessionBreaksRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sessionBreaks);
    }

    /**
     * {@code DELETE  /session-breaks/:id} : delete the "id" sessionBreaks.
     *
     * @param id the id of the sessionBreaks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/session-breaks/{id}")
    public ResponseEntity<Void> deleteSessionBreaks(@PathVariable Long id) {
        log.debug("REST request to delete SessionBreaks : {}", id);
        sessionBreaksRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
