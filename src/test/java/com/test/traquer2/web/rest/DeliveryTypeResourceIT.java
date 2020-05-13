package com.test.traquer2.web.rest;

import com.test.traquer2.TraquerTestApp;
import com.test.traquer2.domain.DeliveryType;
import com.test.traquer2.repository.DeliveryTypeRepository;

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
 * Integration tests for the {@link DeliveryTypeResource} REST controller.
 */
@SpringBootTest(classes = TraquerTestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DeliveryTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DeliveryTypeRepository deliveryTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryTypeMockMvc;

    private DeliveryType deliveryType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryType createEntity(EntityManager em) {
        DeliveryType deliveryType = new DeliveryType()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return deliveryType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryType createUpdatedEntity(EntityManager em) {
        DeliveryType deliveryType = new DeliveryType()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return deliveryType;
    }

    @BeforeEach
    public void initTest() {
        deliveryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryType() throws Exception {
        int databaseSizeBeforeCreate = deliveryTypeRepository.findAll().size();

        // Create the DeliveryType
        restDeliveryTypeMockMvc.perform(post("/api/delivery-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryType)))
            .andExpect(status().isCreated());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryType testDeliveryType = deliveryTypeList.get(deliveryTypeList.size() - 1);
        assertThat(testDeliveryType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDeliveryType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDeliveryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryTypeRepository.findAll().size();

        // Create the DeliveryType with an existing ID
        deliveryType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryTypeMockMvc.perform(post("/api/delivery-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryType)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeliveryTypes() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        // Get all the deliveryTypeList
        restDeliveryTypeMockMvc.perform(get("/api/delivery-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getDeliveryType() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        // Get the deliveryType
        restDeliveryTypeMockMvc.perform(get("/api/delivery-types/{id}", deliveryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryType() throws Exception {
        // Get the deliveryType
        restDeliveryTypeMockMvc.perform(get("/api/delivery-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryType() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        int databaseSizeBeforeUpdate = deliveryTypeRepository.findAll().size();

        // Update the deliveryType
        DeliveryType updatedDeliveryType = deliveryTypeRepository.findById(deliveryType.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryType are not directly saved in db
        em.detach(updatedDeliveryType);
        updatedDeliveryType
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restDeliveryTypeMockMvc.perform(put("/api/delivery-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeliveryType)))
            .andExpect(status().isOk());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeUpdate);
        DeliveryType testDeliveryType = deliveryTypeList.get(deliveryTypeList.size() - 1);
        assertThat(testDeliveryType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDeliveryType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryType() throws Exception {
        int databaseSizeBeforeUpdate = deliveryTypeRepository.findAll().size();

        // Create the DeliveryType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryTypeMockMvc.perform(put("/api/delivery-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryType)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryType() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        int databaseSizeBeforeDelete = deliveryTypeRepository.findAll().size();

        // Delete the deliveryType
        restDeliveryTypeMockMvc.perform(delete("/api/delivery-types/{id}", deliveryType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
