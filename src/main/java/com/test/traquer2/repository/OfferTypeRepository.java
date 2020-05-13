package com.test.traquer2.repository;

import com.test.traquer2.domain.OfferType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OfferType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferTypeRepository extends JpaRepository<OfferType, Long> {
}
