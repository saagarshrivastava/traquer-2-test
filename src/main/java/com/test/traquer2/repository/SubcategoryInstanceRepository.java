package com.test.traquer2.repository;

import com.test.traquer2.domain.SubcategoryInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SubcategoryInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcategoryInstanceRepository extends JpaRepository<SubcategoryInstance, Long> {
}
