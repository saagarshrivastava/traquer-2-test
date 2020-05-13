package com.test.traquer2.repository;

import com.test.traquer2.domain.SupportPerson;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupportPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportPersonRepository extends JpaRepository<SupportPerson, Long> {
}
