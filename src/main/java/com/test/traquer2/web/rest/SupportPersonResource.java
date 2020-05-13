package com.test.traquer2.web.rest;

import com.test.traquer2.domain.SupportPerson;
import com.test.traquer2.repository.SupportPersonRepository;
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
 * REST controller for managing {@link com.test.traquer2.domain.SupportPerson}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SupportPersonResource {

    private final Logger log = LoggerFactory.getLogger(SupportPersonResource.class);

    private static final String ENTITY_NAME = "supportPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupportPersonRepository supportPersonRepository;

    public SupportPersonResource(SupportPersonRepository supportPersonRepository) {
        this.supportPersonRepository = supportPersonRepository;
    }

    /**
     * {@code POST  /support-people} : Create a new supportPerson.
     *
     * @param supportPerson the supportPerson to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportPerson, or with status {@code 400 (Bad Request)} if the supportPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/support-people")
    public ResponseEntity<SupportPerson> createSupportPerson(@RequestBody SupportPerson supportPerson) throws URISyntaxException {
        log.debug("REST request to save SupportPerson : {}", supportPerson);
        if (supportPerson.getId() != null) {
            throw new BadRequestAlertException("A new supportPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupportPerson result = supportPersonRepository.save(supportPerson);
        return ResponseEntity.created(new URI("/api/support-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /support-people} : Updates an existing supportPerson.
     *
     * @param supportPerson the supportPerson to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportPerson,
     * or with status {@code 400 (Bad Request)} if the supportPerson is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportPerson couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/support-people")
    public ResponseEntity<SupportPerson> updateSupportPerson(@RequestBody SupportPerson supportPerson) throws URISyntaxException {
        log.debug("REST request to update SupportPerson : {}", supportPerson);
        if (supportPerson.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupportPerson result = supportPersonRepository.save(supportPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supportPerson.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /support-people} : get all the supportPeople.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supportPeople in body.
     */
    @GetMapping("/support-people")
    public List<SupportPerson> getAllSupportPeople() {
        log.debug("REST request to get all SupportPeople");
        return supportPersonRepository.findAll();
    }

    /**
     * {@code GET  /support-people/:id} : get the "id" supportPerson.
     *
     * @param id the id of the supportPerson to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportPerson, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/support-people/{id}")
    public ResponseEntity<SupportPerson> getSupportPerson(@PathVariable Long id) {
        log.debug("REST request to get SupportPerson : {}", id);
        Optional<SupportPerson> supportPerson = supportPersonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(supportPerson);
    }

    /**
     * {@code DELETE  /support-people/:id} : delete the "id" supportPerson.
     *
     * @param id the id of the supportPerson to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/support-people/{id}")
    public ResponseEntity<Void> deleteSupportPerson(@PathVariable Long id) {
        log.debug("REST request to delete SupportPerson : {}", id);
        supportPersonRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
