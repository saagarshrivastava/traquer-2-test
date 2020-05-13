package com.test.traquer2.repository;

import com.test.traquer2.domain.SupportInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupportInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportInstanceRepository extends JpaRepository<SupportInstance, Long> {
}
