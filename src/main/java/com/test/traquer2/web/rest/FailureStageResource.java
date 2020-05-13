package com.test.traquer2.web.rest;

import com.test.traquer2.domain.FailureStage;
import com.test.traquer2.repository.FailureStageRepository;
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
 * REST controller for managing {@link com.test.traquer2.domain.FailureStage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FailureStageResource {

    private final Logger log = LoggerFactory.getLogger(FailureStageResource.class);

    private static final String ENTITY_NAME = "failureStage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FailureStageRepository failureStageRepository;

    public FailureStageResource(FailureStageRepository failureStageRepository) {
        this.failureStageRepository = failureStageRepository;
    }

    /**
     * {@code POST  /failure-stages} : Create a new failureStage.
     *
     * @param failureStage the failureStage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new failureStage, or with status {@code 400 (Bad Request)} if the failureStage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/failure-stages")
    public ResponseEntity<FailureStage> createFailureStage(@RequestBody FailureStage failureStage) throws URISyntaxException {
        log.debug("REST request to save FailureStage : {}", failureStage);
        if (failureStage.getId() != null) {
            throw new BadRequestAlertException("A new failureStage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FailureStage result = failureStageRepository.save(failureStage);
        return ResponseEntity.created(new URI("/api/failure-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /failure-stages} : Updates an existing failureStage.
     *
     * @param failureStage the failureStage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated failureStage,
     * or with status {@code 400 (Bad Request)} if the failureStage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the failureStage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/failure-stages")
    public ResponseEntity<FailureStage> updateFailureStage(@RequestBody FailureStage failureStage) throws URISyntaxException {
        log.debug("REST request to update FailureStage : {}", failureStage);
        if (failureStage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FailureStage result = failureStageRepository.save(failureStage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, failureStage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /failure-stages} : get all the failureStages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of failureStages in body.
     */
    @GetMapping("/failure-stages")
    public List<FailureStage> getAllFailureStages() {
        log.debug("REST request to get all FailureStages");
        return failureStageRepository.findAll();
    }

    /**
     * {@code GET  /failure-stages/:id} : get the "id" failureStage.
     *
     * @param id the id of the failureStage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the failureStage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/failure-stages/{id}")
    public ResponseEntity<FailureStage> getFailureStage(@PathVariable Long id) {
        log.debug("REST request to get FailureStage : {}", id);
        Optional<FailureStage> failureStage = failureStageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(failureStage);
    }

    /**
     * {@code DELETE  /failure-stages/:id} : delete the "id" failureStage.
     *
     * @param id the id of the failureStage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/failure-stages/{id}")
    public ResponseEntity<Void> deleteFailureStage(@PathVariable Long id) {
        log.debug("REST request to delete FailureStage : {}", id);
        failureStageRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
