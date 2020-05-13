package com.test.traquer2.web.rest;

import com.test.traquer2.TraquerTestApp;
import com.test.traquer2.domain.CloudRegion;
import com.test.traquer2.repository.CloudRegionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CloudRegionResource} REST controller.
 */
@SpringBootTest(classes = TraquerTestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CloudRegionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CloudRegionRepository cloudRegionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCloudRegionMockMvc;

    private CloudRegion cloudRegion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CloudRegion createEntity(EntityManager em) {
        CloudRegion cloudRegion = new CloudRegion()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return cloudRegion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CloudRegion createUpdatedEntity(EntityManager em) {
        CloudRegion cloudRegion = new CloudRegion()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return cloudRegion;
    }

    @BeforeEach
    public void initTest() {
        cloudRegion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCloudRegion() throws Exception {
        int databaseSizeBeforeCreate = cloudRegionRepository.findAll().size();

        // Create the CloudRegion
        restCloudRegionMockMvc.perform(post("/api/cloud-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudRegion)))
            .andExpect(status().isCreated());

        // Validate the CloudRegion in the database
        List<CloudRegion> cloudRegionList = cloudRegionRepository.findAll();
        assertThat(cloudRegionList).hasSize(databaseSizeBeforeCreate + 1);
        CloudRegion testCloudRegion = cloudRegionList.get(cloudRegionList.size() - 1);
        assertThat(testCloudRegion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCloudRegion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCloudRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cloudRegionRepository.findAll().size();

        // Create the CloudRegion with an existing ID
        cloudRegion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCloudRegionMockMvc.perform(post("/api/cloud-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudRegion)))
            .andExpect(status().isBadRequest());

        // Validate the CloudRegion in the database
        List<CloudRegion> cloudRegionList = cloudRegionRepository.findAll();
        assertThat(cloudRegionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCloudRegions() throws Exception {
        // Initialize the database
        cloudRegionRepository.saveAndFlush(cloudRegion);

        // Get all the cloudRegionList
        restCloudRegionMockMvc.perform(get("/api/cloud-regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cloudRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCloudRegion() throws Exception {
        // Initialize the database
        cloudRegionRepository.saveAndFlush(cloudRegion);

        // Get the cloudRegion
        restCloudRegionMockMvc.perform(get("/api/cloud-regions/{id}", cloudRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cloudRegion.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingCloudRegion() throws Exception {
        // Get the cloudRegion
        restCloudRegionMockMvc.perform(get("/api/cloud-regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCloudRegion() throws Exception {
        // Initialize the database
        cloudRegionRepository.saveAndFlush(cloudRegion);

        int databaseSizeBeforeUpdate = cloudRegionRepository.findAll().size();

        // Update the cloudRegion
        CloudRegion updatedCloudRegion = cloudRegionRepository.findById(cloudRegion.getId()).get();
        // Disconnect from session so that the updates on updatedCloudRegion are not directly saved in db
        em.detach(updatedCloudRegion);
        updatedCloudRegion
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restCloudRegionMockMvc.perform(put("/api/cloud-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCloudRegion)))
            .andExpect(status().isOk());

        // Validate the CloudRegion in the database
        List<CloudRegion> cloudRegionList = cloudRegionRepository.findAll();
        assertThat(cloudRegionList).hasSize(databaseSizeBeforeUpdate);
        CloudRegion testCloudRegion = cloudRegionList.get(cloudRegionList.size() - 1);
        assertThat(testCloudRegion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCloudRegion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCloudRegion() throws Exception {
        int databaseSizeBeforeUpdate = cloudRegionRepository.findAll().size();

        // Create the CloudRegion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCloudRegionMockMvc.perform(put("/api/cloud-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudRegion)))
            .andExpect(status().isBadRequest());

        // Validate the CloudRegion in the database
        List<CloudRegion> cloudRegionList = cloudRegionRepository.findAll();
        assertThat(cloudRegionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCloudRegion() throws Exception {
        // Initialize the database
        cloudRegionRepository.saveAndFlush(cloudRegion);

        int databaseSizeBeforeDelete = cloudRegionRepository.findAll().size();

        // Delete the cloudRegion
        restCloudRegionMockMvc.perform(delete("/api/cloud-regions/{id}", cloudRegion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CloudRegion> cloudRegionList = cloudRegionRepository.findAll();
        assertThat(cloudRegionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
