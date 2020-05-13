package com.test.traquer2.repository;

import com.test.traquer2.domain.CloudInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CloudInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudInstanceRepository extends JpaRepository<CloudInstance, Long> {
}
