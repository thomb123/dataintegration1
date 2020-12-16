package com.totient.dataintegration.service;

import com.totient.dataintegration.domain.ProcessGroupTemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ProcessGroupTemplate}.
 */
public interface ProcessGroupTemplateService {

    /**
     * Save a processGroupTemplate.
     *
     * @param processGroupTemplate the entity to save.
     * @return the persisted entity.
     */
    ProcessGroupTemplate save(ProcessGroupTemplate processGroupTemplate);

    /**
     * Get all the processGroupTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessGroupTemplate> findAll(Pageable pageable);


    /**
     * Get the "id" processGroupTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcessGroupTemplate> findOne(Long id);

    /**
     * Delete the "id" processGroupTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
