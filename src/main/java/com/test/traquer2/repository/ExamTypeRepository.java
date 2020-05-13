package com.test.traquer2.repository;

import com.test.traquer2.domain.ExamType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExamType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamTypeRepository extends JpaRepository<ExamType, Long> {
}
