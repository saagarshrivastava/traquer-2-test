package com.test.traquer2.repository;

import com.test.traquer2.domain.FailureStage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FailureStage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FailureStageRepository extends JpaRepository<FailureStage, Long> {
}
