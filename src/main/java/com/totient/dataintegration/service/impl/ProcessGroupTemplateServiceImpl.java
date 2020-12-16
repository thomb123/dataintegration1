package com.totient.dataintegration.service.impl;

import com.totient.dataintegration.service.ProcessGroupTemplateService;
import com.totient.dataintegration.domain.ProcessGroupTemplate;
import com.totient.dataintegration.repository.ProcessGroupTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessGroupTemplate}.
 */
@Service
@Transactional
public class ProcessGroupTemplateServiceImpl implements ProcessGroupTemplateService {

    private final Logger log = LoggerFactory.getLogger(ProcessGroupTemplateServiceImpl.class);

    private final ProcessGroupTemplateRepository processGroupTemplateRepository;

    public ProcessGroupTemplateServiceImpl(ProcessGroupTemplateRepository processGroupTemplateRepository) {
        this.processGroupTemplateRepository = processGroupTemplateRepository;
    }

    @Override
    public ProcessGroupTemplate save(ProcessGroupTemplate processGroupTemplate) {
        log.debug("Request to save ProcessGroupTemplate : {}", processGroupTemplate);
        return processGroupTemplateRepository.save(processGroupTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessGroupTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessGroupTemplates");
        return processGroupTemplateRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessGroupTemplate> findOne(Long id) {
        log.debug("Request to get ProcessGroupTemplate : {}", id);
        return processGroupTemplateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessGroupTemplate : {}", id);
        processGroupTemplateRepository.deleteById(id);
    }
}
