package com.test.traquer2.web.rest;

import com.test.traquer2.TraquerTestApp;
import com.test.traquer2.domain.OfferType;
import com.test.traquer2.repository.OfferTypeRepository;

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
 * Integration tests for the {@link OfferTypeResource} REST controller.
 */
@SpringBootTest(classes = TraquerTestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class OfferTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OfferTypeRepository offerTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferTypeMockMvc;

    private OfferType offerType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferType createEntity(EntityManager em) {
        OfferType offerType = new OfferType()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return offerType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferType createUpdatedEntity(EntityManager em) {
        OfferType offerType = new OfferType()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return offerType;
    }

    @BeforeEach
    public void initTest() {
        offerType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfferType() throws Exception {
        int databaseSizeBeforeCreate = offerTypeRepository.findAll().size();

        // Create the OfferType
        restOfferTypeMockMvc.perform(post("/api/offer-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerType)))
            .andExpect(status().isCreated());

        // Validate the OfferType in the database
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OfferType testOfferType = offerTypeList.get(offerTypeList.size() - 1);
        assertThat(testOfferType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOfferType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOfferTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerTypeRepository.findAll().size();

        // Create the OfferType with an existing ID
        offerType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferTypeMockMvc.perform(post("/api/offer-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerType)))
            .andExpect(status().isBadRequest());

        // Validate the OfferType in the database
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOfferTypes() throws Exception {
        // Initialize the database
        offerTypeRepository.saveAndFlush(offerType);

        // Get all the offerTypeList
        restOfferTypeMockMvc.perform(get("/api/offer-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getOfferType() throws Exception {
        // Initialize the database
        offerTypeRepository.saveAndFlush(offerType);

        // Get the offerType
        restOfferTypeMockMvc.perform(get("/api/offer-types/{id}", offerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offerType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingOfferType() throws Exception {
        // Get the offerType
        restOfferTypeMockMvc.perform(get("/api/offer-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferType() throws Exception {
        // Initialize the database
        offerTypeRepository.saveAndFlush(offerType);

        int databaseSizeBeforeUpdate = offerTypeRepository.findAll().size();

        // Update the offerType
        OfferType updatedOfferType = offerTypeRepository.findById(offerType.getId()).get();
        // Disconnect from session so that the updates on updatedOfferType are not directly saved in db
        em.detach(updatedOfferType);
        updatedOfferType
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restOfferTypeMockMvc.perform(put("/api/offer-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOfferType)))
            .andExpect(status().isOk());

        // Validate the OfferType in the database
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeUpdate);
        OfferType testOfferType = offerTypeList.get(offerTypeList.size() - 1);
        assertThat(testOfferType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOfferType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOfferType() throws Exception {
        int databaseSizeBeforeUpdate = offerTypeRepository.findAll().size();

        // Create the OfferType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferTypeMockMvc.perform(put("/api/offer-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerType)))
            .andExpect(status().isBadRequest());

        // Validate the OfferType in the database
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfferType() throws Exception {
        // Initialize the database
        offerTypeRepository.saveAndFlush(offerType);

        int databaseSizeBeforeDelete = offerTypeRepository.findAll().size();

        // Delete the offerType
        restOfferTypeMockMvc.perform(delete("/api/offer-types/{id}", offerType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
