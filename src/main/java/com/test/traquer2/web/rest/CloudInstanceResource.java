package com.test.traquer2.web.rest;

import com.test.traquer2.domain.CloudInstance;
import com.test.traquer2.repository.CloudInstanceRepository;
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
 * REST controller for managing {@link com.test.traquer2.domain.CloudInstance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CloudInstanceResource {

    private final Logger log = LoggerFactory.getLogger(CloudInstanceResource.class);

    private static final String ENTITY_NAME = "cloudInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CloudInstanceRepository cloudInstanceRepository;

    public CloudInstanceResource(CloudInstanceRepository cloudInstanceRepository) {
        this.cloudInstanceRepository = cloudInstanceRepository;
    }

    /**
     * {@code POST  /cloud-instances} : Create a new cloudInstance.
     *
     * @param cloudInstance the cloudInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cloudInstance, or with status {@code 400 (Bad Request)} if the cloudInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cloud-instances")
    public ResponseEntity<CloudInstance> createCloudInstance(@RequestBody CloudInstance cloudInstance) throws URISyntaxException {
        log.debug("REST request to save CloudInstance : {}", cloudInstance);
        if (cloudInstance.getId() != null) {
            throw new BadRequestAlertException("A new cloudInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CloudInstance result = cloudInstanceRepository.save(cloudInstance);
        return ResponseEntity.created(new URI("/api/cloud-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cloud-instances} : Updates an existing cloudInstance.
     *
     * @param cloudInstance the cloudInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cloudInstance,
     * or with status {@code 400 (Bad Request)} if the cloudInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cloudInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cloud-instances")
    public ResponseEntity<CloudInstance> updateCloudInstance(@RequestBody CloudInstance cloudInstance) throws URISyntaxException {
        log.debug("REST request to update CloudInstance : {}", cloudInstance);
        if (cloudInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CloudInstance result = cloudInstanceRepository.save(cloudInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cloudInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cloud-instances} : get all the cloudInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cloudInstances in body.
     */
    @GetMapping("/cloud-instances")
    public List<CloudInstance> getAllCloudInstances() {
        log.debug("REST request to get all CloudInstances");
        return cloudInstanceRepository.findAll();
    }

    /**
     * {@code GET  /cloud-instances/:id} : get the "id" cloudInstance.
     *
     * @param id the id of the cloudInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cloudInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cloud-instances/{id}")
    public ResponseEntity<CloudInstance> getCloudInstance(@PathVariable Long id) {
        log.debug("REST request to get CloudInstance : {}", id);
        Optional<CloudInstance> cloudInstance = cloudInstanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cloudInstance);
    }

    /**
     * {@code DELETE  /cloud-instances/:id} : delete the "id" cloudInstance.
     *
     * @param id the id of the cloudInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cloud-instances/{id}")
    public ResponseEntity<Void> deleteCloudInstance(@PathVariable Long id) {
        log.debug("REST request to delete CloudInstance : {}", id);
        cloudInstanceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
