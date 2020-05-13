package com.test.traquer2.web.rest;

import com.test.traquer2.TraquerTestApp;
import com.test.traquer2.domain.SubcategoryInstance;
import com.test.traquer2.repository.SubcategoryInstanceRepository;

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
 * Integration tests for the {@link SubcategoryInstanceResource} REST controller.
 */
@SpringBootTest(classes = TraquerTestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SubcategoryInstanceResourceIT {

    private static final Integer DEFAULT_CATEGORYINSTANCEID = 1;
    private static final Integer UPDATED_CATEGORYINSTANCEID = 2;

    private static final Integer DEFAULT_SUBCATEGORYID = 1;
    private static final Integer UPDATED_SUBCATEGORYID = 2;

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;

    @Autowired
    private SubcategoryInstanceRepository subcategoryInstanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubcategoryInstanceMockMvc;

    private SubcategoryInstance subcategoryInstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubcategoryInstance createEntity(EntityManager em) {
        SubcategoryInstance subcategoryInstance = new SubcategoryInstance()
            .categoryinstanceid(DEFAULT_CATEGORYINSTANCEID)
            .subcategoryid(DEFAULT_SUBCATEGORYID)
            .rank(DEFAULT_RANK);
        return subcategoryInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubcategoryInstance createUpdatedEntity(EntityManager em) {
        SubcategoryInstance subcategoryInstance = new SubcategoryInstance()
            .categoryinstanceid(UPDATED_CATEGORYINSTANCEID)
            .subcategoryid(UPDATED_SUBCATEGORYID)
            .rank(UPDATED_RANK);
        return subcategoryInstance;
    }

    @BeforeEach
    public void initTest() {
        subcategoryInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubcategoryInstance() throws Exception {
        int databaseSizeBeforeCreate = subcategoryInstanceRepository.findAll().size();

        // Create the SubcategoryInstance
        restSubcategoryInstanceMockMvc.perform(post("/api/subcategory-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subcategoryInstance)))
            .andExpect(status().isCreated());

        // Validate the SubcategoryInstance in the database
        List<SubcategoryInstance> subcategoryInstanceList = subcategoryInstanceRepository.findAll();
        assertThat(subcategoryInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        SubcategoryInstance testSubcategoryInstance = subcategoryInstanceList.get(subcategoryInstanceList.size() - 1);
        assertThat(testSubcategoryInstance.getCategoryinstanceid()).isEqualTo(DEFAULT_CATEGORYINSTANCEID);
        assertThat(testSubcategoryInstance.getSubcategoryid()).isEqualTo(DEFAULT_SUBCATEGORYID);
        assertThat(testSubcategoryInstance.getRank()).isEqualTo(DEFAULT_RANK);
    }

    @Test
    @Transactional
    public void createSubcategoryInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subcategoryInstanceRepository.findAll().size();

        // Create the SubcategoryInstance with an existing ID
        subcategoryInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubcategoryInstanceMockMvc.perform(post("/api/subcategory-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subcategoryInstance)))
            .andExpect(status().isBadRequest());

        // Validate the SubcategoryInstance in the database
        List<SubcategoryInstance> subcategoryInstanceList = subcategoryInstanceRepository.findAll();
        assertThat(subcategoryInstanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubcategoryInstances() throws Exception {
        // Initialize the database
        subcategoryInstanceRepository.saveAndFlush(subcategoryInstance);

        // Get all the subcategoryInstanceList
        restSubcategoryInstanceMockMvc.perform(get("/api/subcategory-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subcategoryInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryinstanceid").value(hasItem(DEFAULT_CATEGORYINSTANCEID)))
            .andExpect(jsonPath("$.[*].subcategoryid").value(hasItem(DEFAULT_SUBCATEGORYID)))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)));
    }
    
    @Test
    @Transactional
    public void getSubcategoryInstance() throws Exception {
        // Initialize the database
        subcategoryInstanceRepository.saveAndFlush(subcategoryInstance);

        // Get the subcategoryInstance
        restSubcategoryInstanceMockMvc.perform(get("/api/subcategory-instances/{id}", subcategoryInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subcategoryInstance.getId().intValue()))
            .andExpect(jsonPath("$.categoryinstanceid").value(DEFAULT_CATEGORYINSTANCEID))
            .andExpect(jsonPath("$.subcategoryid").value(DEFAULT_SUBCATEGORYID))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK));
    }

    @Test
    @Transactional
    public void getNonExistingSubcategoryInstance() throws Exception {
        // Get the subcategoryInstance
        restSubcategoryInstanceMockMvc.perform(get("/api/subcategory-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubcategoryInstance() throws Exception {
        // Initialize the database
        subcategoryInstanceRepository.saveAndFlush(subcategoryInstance);

        int databaseSizeBeforeUpdate = subcategoryInstanceRepository.findAll().size();

        // Update the subcategoryInstance
        SubcategoryInstance updatedSubcategoryInstance = subcategoryInstanceRepository.findById(subcategoryInstance.getId()).get();
        // Disconnect from session so that the updates on updatedSubcategoryInstance are not directly saved in db
        em.detach(updatedSubcategoryInstance);
        updatedSubcategoryInstance
            .categoryinstanceid(UPDATED_CATEGORYINSTANCEID)
            .subcategoryid(UPDATED_SUBCATEGORYID)
            .rank(UPDATED_RANK);

        restSubcategoryInstanceMockMvc.perform(put("/api/subcategory-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubcategoryInstance)))
            .andExpect(status().isOk());

        // Validate the SubcategoryInstance in the database
        List<SubcategoryInstance> subcategoryInstanceList = subcategoryInstanceRepository.findAll();
        assertThat(subcategoryInstanceList).hasSize(databaseSizeBeforeUpdate);
        SubcategoryInstance testSubcategoryInstance = subcategoryInstanceList.get(subcategoryInstanceList.size() - 1);
        assertThat(testSubcategoryInstance.getCategoryinstanceid()).isEqualTo(UPDATED_CATEGORYINSTANCEID);
        assertThat(testSubcategoryInstance.getSubcategoryid()).isEqualTo(UPDATED_SUBCATEGORYID);
        assertThat(testSubcategoryInstance.getRank()).isEqualTo(UPDATED_RANK);
    }

    @Test
    @Transactional
    public void updateNonExistingSubcategoryInstance() throws Exception {
        int databaseSizeBeforeUpdate = subcategoryInstanceRepository.findAll().size();

        // Create the SubcategoryInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubcategoryInstanceMockMvc.perform(put("/api/subcategory-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subcategoryInstance)))
            .andExpect(status().isBadRequest());

        // Validate the SubcategoryInstance in the database
        List<SubcategoryInstance> subcategoryInstanceList = subcategoryInstanceRepository.findAll();
        assertThat(subcategoryInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubcategoryInstance() throws Exception {
        // Initialize the database
        subcategoryInstanceRepository.saveAndFlush(subcategoryInstance);

        int databaseSizeBeforeDelete = subcategoryInstanceRepository.findAll().size();

        // Delete the subcategoryInstance
        restSubcategoryInstanceMockMvc.perform(delete("/api/subcategory-instances/{id}", subcategoryInstance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubcategoryInstance> subcategoryInstanceList = subcategoryInstanceRepository.findAll();
        assertThat(subcategoryInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
