package com.test.traquer2.web.rest;

import com.test.traquer2.TraquerTestApp;
import com.test.traquer2.domain.ExamBackend;
import com.test.traquer2.repository.ExamBackendRepository;

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
 * Integration tests for the {@link ExamBackendResource} REST controller.
 */
@SpringBootTest(classes = TraquerTestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ExamBackendResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ExamBackendRepository examBackendRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamBackendMockMvc;

    private ExamBackend examBackend;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamBackend createEntity(EntityManager em) {
        ExamBackend examBackend = new ExamBackend()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return examBackend;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamBackend createUpdatedEntity(EntityManager em) {
        ExamBackend examBackend = new ExamBackend()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return examBackend;
    }

    @BeforeEach
    public void initTest() {
        examBackend = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamBackend() throws Exception {
        int databaseSizeBeforeCreate = examBackendRepository.findAll().size();

        // Create the ExamBackend
        restExamBackendMockMvc.perform(post("/api/exam-backends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examBackend)))
            .andExpect(status().isCreated());

        // Validate the ExamBackend in the database
        List<ExamBackend> examBackendList = examBackendRepository.findAll();
        assertThat(examBackendList).hasSize(databaseSizeBeforeCreate + 1);
        ExamBackend testExamBackend = examBackendList.get(examBackendList.size() - 1);
        assertThat(testExamBackend.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testExamBackend.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createExamBackendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examBackendRepository.findAll().size();

        // Create the ExamBackend with an existing ID
        examBackend.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamBackendMockMvc.perform(post("/api/exam-backends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examBackend)))
            .andExpect(status().isBadRequest());

        // Validate the ExamBackend in the database
        List<ExamBackend> examBackendList = examBackendRepository.findAll();
        assertThat(examBackendList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExamBackends() throws Exception {
        // Initialize the database
        examBackendRepository.saveAndFlush(examBackend);

        // Get all the examBackendList
        restExamBackendMockMvc.perform(get("/api/exam-backends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examBackend.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getExamBackend() throws Exception {
        // Initialize the database
        examBackendRepository.saveAndFlush(examBackend);

        // Get the examBackend
        restExamBackendMockMvc.perform(get("/api/exam-backends/{id}", examBackend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examBackend.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingExamBackend() throws Exception {
        // Get the examBackend
        restExamBackendMockMvc.perform(get("/api/exam-backends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamBackend() throws Exception {
        // Initialize the database
        examBackendRepository.saveAndFlush(examBackend);

        int databaseSizeBeforeUpdate = examBackendRepository.findAll().size();

        // Update the examBackend
        ExamBackend updatedExamBackend = examBackendRepository.findById(examBackend.getId()).get();
        // Disconnect from session so that the updates on updatedExamBackend are not directly saved in db
        em.detach(updatedExamBackend);
        updatedExamBackend
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restExamBackendMockMvc.perform(put("/api/exam-backends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExamBackend)))
            .andExpect(status().isOk());

        // Validate the ExamBackend in the database
        List<ExamBackend> examBackendList = examBackendRepository.findAll();
        assertThat(examBackendList).hasSize(databaseSizeBeforeUpdate);
        ExamBackend testExamBackend = examBackendList.get(examBackendList.size() - 1);
        assertThat(testExamBackend.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testExamBackend.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingExamBackend() throws Exception {
        int databaseSizeBeforeUpdate = examBackendRepository.findAll().size();

        // Create the ExamBackend

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamBackendMockMvc.perform(put("/api/exam-backends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examBackend)))
            .andExpect(status().isBadRequest());

        // Validate the ExamBackend in the database
        List<ExamBackend> examBackendList = examBackendRepository.findAll();
        assertThat(examBackendList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamBackend() throws Exception {
        // Initialize the database
        examBackendRepository.saveAndFlush(examBackend);

        int databaseSizeBeforeDelete = examBackendRepository.findAll().size();

        // Delete the examBackend
        restExamBackendMockMvc.perform(delete("/api/exam-backends/{id}", examBackend.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExamBackend> examBackendList = examBackendRepository.findAll();
        assertThat(examBackendList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
