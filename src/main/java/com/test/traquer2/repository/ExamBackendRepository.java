package com.test.traquer2.repository;

import com.test.traquer2.domain.ExamBackend;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExamBackend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamBackendRepository extends JpaRepository<ExamBackend, Long> {
}
