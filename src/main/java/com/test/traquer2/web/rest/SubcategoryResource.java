package com.test.traquer2.web.rest;

import com.test.traquer2.domain.Subcategory;
import com.test.traquer2.repository.SubcategoryRepository;
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
 * REST controller for managing {@link com.test.traquer2.domain.Subcategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubcategoryResource {

    private final Logger log = LoggerFactory.getLogger(SubcategoryResource.class);

    private static final String ENTITY_NAME = "subcategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubcategoryRepository subcategoryRepository;

    public SubcategoryResource(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    /**
     * {@code POST  /subcategories} : Create a new subcategory.
     *
     * @param subcategory the subcategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subcategory, or with status {@code 400 (Bad Request)} if the subcategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subcategories")
    public ResponseEntity<Subcategory> createSubcategory(@RequestBody Subcategory subcategory) throws URISyntaxException {
        log.debug("REST request to save Subcategory : {}", subcategory);
        if (subcategory.getId() != null) {
            throw new BadRequestAlertException("A new subcategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subcategory result = subcategoryRepository.save(subcategory);
        return ResponseEntity.created(new URI("/api/subcategories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subcategories} : Updates an existing subcategory.
     *
     * @param subcategory the subcategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subcategory,
     * or with status {@code 400 (Bad Request)} if the subcategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subcategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subcategories")
    public ResponseEntity<Subcategory> updateSubcategory(@RequestBody Subcategory subcategory) throws URISyntaxException {
        log.debug("REST request to update Subcategory : {}", subcategory);
        if (subcategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Subcategory result = subcategoryRepository.save(subcategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subcategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subcategories} : get all the subcategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subcategories in body.
     */
    @GetMapping("/subcategories")
    public List<Subcategory> getAllSubcategories() {
        log.debug("REST request to get all Subcategories");
        return subcategoryRepository.findAll();
    }

    /**
     * {@code GET  /subcategories/:id} : get the "id" subcategory.
     *
     * @param id the id of the subcategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subcategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subcategories/{id}")
    public ResponseEntity<Subcategory> getSubcategory(@PathVariable Long id) {
        log.debug("REST request to get Subcategory : {}", id);
        Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subcategory);
    }

    /**
     * {@code DELETE  /subcategories/:id} : delete the "id" subcategory.
     *
     * @param id the id of the subcategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subcategories/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
        log.debug("REST request to delete Subcategory : {}", id);
        subcategoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
