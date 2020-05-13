package com.test.traquer2.web.rest;

import com.test.traquer2.domain.Proctor;
import com.test.traquer2.repository.ProctorRepository;
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
 * REST controller for managing {@link com.test.traquer2.domain.Proctor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProctorResource {

    private final Logger log = LoggerFactory.getLogger(ProctorResource.class);

    private static final String ENTITY_NAME = "proctor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProctorRepository proctorRepository;

    public ProctorResource(ProctorRepository proctorRepository) {
        this.proctorRepository = proctorRepository;
    }

    /**
     * {@code POST  /proctors} : Create a new proctor.
     *
     * @param proctor the proctor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proctor, or with status {@code 400 (Bad Request)} if the proctor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proctors")
    public ResponseEntity<Proctor> createProctor(@RequestBody Proctor proctor) throws URISyntaxException {
        log.debug("REST request to save Proctor : {}", proctor);
        if (proctor.getId() != null) {
            throw new BadRequestAlertException("A new proctor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Proctor result = proctorRepository.save(proctor);
        return ResponseEntity.created(new URI("/api/proctors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proctors} : Updates an existing proctor.
     *
     * @param proctor the proctor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proctor,
     * or with status {@code 400 (Bad Request)} if the proctor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proctor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proctors")
    public ResponseEntity<Proctor> updateProctor(@RequestBody Proctor proctor) throws URISyntaxException {
        log.debug("REST request to update Proctor : {}", proctor);
        if (proctor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Proctor result = proctorRepository.save(proctor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proctor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proctors} : get all the proctors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proctors in body.
     */
    @GetMapping("/proctors")
    public List<Proctor> getAllProctors() {
        log.debug("REST request to get all Proctors");
        return proctorRepository.findAll();
    }

    /**
     * {@code GET  /proctors/:id} : get the "id" proctor.
     *
     * @param id the id of the proctor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proctor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proctors/{id}")
    public ResponseEntity<Proctor> getProctor(@PathVariable Long id) {
        log.debug("REST request to get Proctor : {}", id);
        Optional<Proctor> proctor = proctorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(proctor);
    }

    /**
     * {@code DELETE  /proctors/:id} : delete the "id" proctor.
     *
     * @param id the id of the proctor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proctors/{id}")
    public ResponseEntity<Void> deleteProctor(@PathVariable Long id) {
        log.debug("REST request to delete Proctor : {}", id);
        proctorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
