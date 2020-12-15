package com.totient.dataintegration.repository;

import com.totient.dataintegration.domain.ProcessGroupTemplate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProcessGroupTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessGroupTemplateRepository extends JpaRepository<ProcessGroupTemplate, Long> {
}
