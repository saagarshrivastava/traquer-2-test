package com.test.traquer2.repository;

import com.test.traquer2.domain.SessionBreaks;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SessionBreaks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SessionBreaksRepository extends JpaRepository<SessionBreaks, Long> {
}
