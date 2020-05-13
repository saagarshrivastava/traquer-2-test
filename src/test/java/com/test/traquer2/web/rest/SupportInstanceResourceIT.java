package com.test.traquer2.web.rest;

import com.test.traquer2.TraquerTestApp;
import com.test.traquer2.domain.SupportInstance;
import com.test.traquer2.repository.SupportInstanceRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SupportInstanceResource} REST controller.
 */
@SpringBootTest(classes = TraquerTestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SupportInstanceResourceIT {

    private static final LocalDate DEFAULT_STARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDTIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CHATLOGS = "AAAAAAAAAA";
    private static final String UPDATED_CHATLOGS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SESSIONID = 1;
    private static final Integer UPDATED_SESSIONID = 2;

    private static final Integer DEFAULT_SUPPORTPERSONID = 1;
    private static final Integer UPDATED_SUPPORTPERSONID = 2;

    @Autowired
    private SupportInstanceRepository supportInstanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupportInstanceMockMvc;

    private SupportInstance supportInstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportInstance createEntity(EntityManager em) {
        SupportInstance supportInstance = new SupportInstance()
            .starttime(DEFAULT_STARTTIME)
            .endtime(DEFAULT_ENDTIME)
            .chatlogs(DEFAULT_CHATLOGS)
            .sessionid(DEFAULT_SESSIONID)
            .supportpersonid(DEFAULT_SUPPORTPERSONID);
        return supportInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportInstance createUpdatedEntity(EntityManager em) {
        SupportInstance supportInstance = new SupportInstance()
            .starttime(UPDATED_STARTTIME)
            .endtime(UPDATED_ENDTIME)
            .chatlogs(UPDATED_CHATLOGS)
            .sessionid(UPDATED_SESSIONID)
            .supportpersonid(UPDATED_SUPPORTPERSONID);
        return supportInstance;
    }

    @BeforeEach
    public void initTest() {
        supportInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupportInstance() throws Exception {
        int databaseSizeBeforeCreate = supportInstanceRepository.findAll().size();

        // Create the SupportInstance
        restSupportInstanceMockMvc.perform(post("/api/support-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportInstance)))
            .andExpect(status().isCreated());

        // Validate the SupportInstance in the database
        List<SupportInstance> supportInstanceList = supportInstanceRepository.findAll();
        assertThat(supportInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        SupportInstance testSupportInstance = supportInstanceList.get(supportInstanceList.size() - 1);
        assertThat(testSupportInstance.getStarttime()).isEqualTo(DEFAULT_STARTTIME);
        assertThat(testSupportInstance.getEndtime()).isEqualTo(DEFAULT_ENDTIME);
        assertThat(testSupportInstance.getChatlogs()).isEqualTo(DEFAULT_CHATLOGS);
        assertThat(testSupportInstance.getSessionid()).isEqualTo(DEFAULT_SESSIONID);
        assertThat(testSupportInstance.getSupportpersonid()).isEqualTo(DEFAULT_SUPPORTPERSONID);
    }

    @Test
    @Transactional
    public void createSupportInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supportInstanceRepository.findAll().size();

        // Create the SupportInstance with an existing ID
        supportInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportInstanceMockMvc.perform(post("/api/support-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportInstance)))
            .andExpect(status().isBadRequest());

        // Validate the SupportInstance in the database
        List<SupportInstance> supportInstanceList = supportInstanceRepository.findAll();
        assertThat(supportInstanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSupportInstances() throws Exception {
        // Initialize the database
        supportInstanceRepository.saveAndFlush(supportInstance);

        // Get all the supportInstanceList
        restSupportInstanceMockMvc.perform(get("/api/support-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].starttime").value(hasItem(DEFAULT_STARTTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].chatlogs").value(hasItem(DEFAULT_CHATLOGS)))
            .andExpect(jsonPath("$.[*].sessionid").value(hasItem(DEFAULT_SESSIONID)))
            .andExpect(jsonPath("$.[*].supportpersonid").value(hasItem(DEFAULT_SUPPORTPERSONID)));
    }
    
    @Test
    @Transactional
    public void getSupportInstance() throws Exception {
        // Initialize the database
        supportInstanceRepository.saveAndFlush(supportInstance);

        // Get the supportInstance
        restSupportInstanceMockMvc.perform(get("/api/support-instances/{id}", supportInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supportInstance.getId().intValue()))
            .andExpect(jsonPath("$.starttime").value(DEFAULT_STARTTIME.toString()))
            .andExpect(jsonPath("$.endtime").value(DEFAULT_ENDTIME.toString()))
            .andExpect(jsonPath("$.chatlogs").value(DEFAULT_CHATLOGS))
            .andExpect(jsonPath("$.sessionid").value(DEFAULT_SESSIONID))
            .andExpect(jsonPath("$.supportpersonid").value(DEFAULT_SUPPORTPERSONID));
    }

    @Test
    @Transactional
    public void getNonExistingSupportInstance() throws Exception {
        // Get the supportInstance
        restSupportInstanceMockMvc.perform(get("/api/support-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupportInstance() throws Exception {
        // Initialize the database
        supportInstanceRepository.saveAndFlush(supportInstance);

        int databaseSizeBeforeUpdate = supportInstanceRepository.findAll().size();

        // Update the supportInstance
        SupportInstance updatedSupportInstance = supportInstanceRepository.findById(supportInstance.getId()).get();
        // Disconnect from session so that the updates on updatedSupportInstance are not directly saved in db
        em.detach(updatedSupportInstance);
        updatedSupportInstance
            .starttime(UPDATED_STARTTIME)
            .endtime(UPDATED_ENDTIME)
            .chatlogs(UPDATED_CHATLOGS)
            .sessionid(UPDATED_SESSIONID)
            .supportpersonid(UPDATED_SUPPORTPERSONID);

        restSupportInstanceMockMvc.perform(put("/api/support-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSupportInstance)))
            .andExpect(status().isOk());

        // Validate the SupportInstance in the database
        List<SupportInstance> supportInstanceList = supportInstanceRepository.findAll();
        assertThat(supportInstanceList).hasSize(databaseSizeBeforeUpdate);
        SupportInstance testSupportInstance = supportInstanceList.get(supportInstanceList.size() - 1);
        assertThat(testSupportInstance.getStarttime()).isEqualTo(UPDATED_STARTTIME);
        assertThat(testSupportInstance.getEndtime()).isEqualTo(UPDATED_ENDTIME);
        assertThat(testSupportInstance.getChatlogs()).isEqualTo(UPDATED_CHATLOGS);
        assertThat(testSupportInstance.getSessionid()).isEqualTo(UPDATED_SESSIONID);
        assertThat(testSupportInstance.getSupportpersonid()).isEqualTo(UPDATED_SUPPORTPERSONID);
    }

    @Test
    @Transactional
    public void updateNonExistingSupportInstance() throws Exception {
        int databaseSizeBeforeUpdate = supportInstanceRepository.findAll().size();

        // Create the SupportInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportInstanceMockMvc.perform(put("/api/support-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportInstance)))
            .andExpect(status().isBadRequest());

        // Validate the SupportInstance in the database
        List<SupportInstance> supportInstanceList = supportInstanceRepository.findAll();
        assertThat(supportInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupportInstance() throws Exception {
        // Initialize the database
        supportInstanceRepository.saveAndFlush(supportInstance);

        int databaseSizeBeforeDelete = supportInstanceRepository.findAll().size();

        // Delete the supportInstance
        restSupportInstanceMockMvc.perform(delete("/api/support-instances/{id}", supportInstance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupportInstance> supportInstanceList = supportInstanceRepository.findAll();
        assertThat(supportInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
