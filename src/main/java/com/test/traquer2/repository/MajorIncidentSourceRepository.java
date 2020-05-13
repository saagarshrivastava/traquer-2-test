package com.test.traquer2.repository;

import com.test.traquer2.domain.MajorIncidentSource;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MajorIncidentSource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MajorIncidentSourceRepository extends JpaRepository<MajorIncidentSource, Long> {
}
