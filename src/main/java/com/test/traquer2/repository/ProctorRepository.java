package com.test.traquer2.repository;

import com.test.traquer2.domain.Proctor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Proctor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProctorRepository extends JpaRepository<Proctor, Long> {
}
