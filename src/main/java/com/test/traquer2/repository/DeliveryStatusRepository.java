package com.test.traquer2.repository;

import com.test.traquer2.domain.DeliveryStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DeliveryStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Long> {
}
