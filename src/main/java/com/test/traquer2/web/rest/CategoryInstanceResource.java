package com.test.traquer2.web.rest;

import com.test.traquer2.domain.CategoryInstance;
import com.test.traquer2.repository.CategoryInstanceRepository;
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
 * REST controller for managing {@link com.test.traquer2.domain.CategoryInstance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CategoryInstanceResource {

    private final Logger log = LoggerFactory.getLogger(CategoryInstanceResource.class);

    private static final String ENTITY_NAME = "categoryInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryInstanceRepository categoryInstanceRepository;

    public CategoryInstanceResource(CategoryInstanceRepository categoryInstanceRepository) {
        this.categoryInstanceRepository = categoryInstanceRepository;
    }

    /**
     * {@code POST  /category-instances} : Create a new categoryInstance.
     *
     * @param categoryInstance the categoryInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryInstance, or with status {@code 400 (Bad Request)} if the categoryInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-instances")
    public ResponseEntity<CategoryInstance> createCategoryInstance(@RequestBody CategoryInstance categoryInstance) throws URISyntaxException {
        log.debug("REST request to save CategoryInstance : {}", categoryInstance);
        if (categoryInstance.getId() != null) {
            throw new BadRequestAlertException("A new categoryInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryInstance result = categoryInstanceRepository.save(categoryInstance);
        return ResponseEntity.created(new URI("/api/category-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-instances} : Updates an existing categoryInstance.
     *
     * @param categoryInstance the categoryInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryInstance,
     * or with status {@code 400 (Bad Request)} if the categoryInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-instances")
    public ResponseEntity<CategoryInstance> updateCategoryInstance(@RequestBody CategoryInstance categoryInstance) throws URISyntaxException {
        log.debug("REST request to update CategoryInstance : {}", categoryInstance);
        if (categoryInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryInstance result = categoryInstanceRepository.save(categoryInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoryInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-instances} : get all the categoryInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryInstances in body.
     */
    @GetMapping("/category-instances")
    public List<CategoryInstance> getAllCategoryInstances() {
        log.debug("REST request to get all CategoryInstances");
        return categoryInstanceRepository.findAll();
    }

    /**
     * {@code GET  /category-instances/:id} : get the "id" categoryInstance.
     *
     * @param id the id of the categoryInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-instances/{id}")
    public ResponseEntity<CategoryInstance> getCategoryInstance(@PathVariable Long id) {
        log.debug("REST request to get CategoryInstance : {}", id);
        Optional<CategoryInstance> categoryInstance = categoryInstanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categoryInstance);
    }

    /**
     * {@code DELETE  /category-instances/:id} : delete the "id" categoryInstance.
     *
     * @param id the id of the categoryInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-instances/{id}")
    public ResponseEntity<Void> deleteCategoryInstance(@PathVariable Long id) {
        log.debug("REST request to delete CategoryInstance : {}", id);
        categoryInstanceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
