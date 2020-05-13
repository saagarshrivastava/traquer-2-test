package com.test.traquer2.repository;

import com.test.traquer2.domain.MajorIncident;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MajorIncident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MajorIncidentRepository extends JpaRepository<MajorIncident, Long> {
}
