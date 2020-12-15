package com.totient.dataintegration.service.impl;

import com.totient.dataintegration.service.DataSourceService;
import com.totient.dataintegration.domain.DataSource;
import com.totient.dataintegration.repository.DataSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DataSource}.
 */
@Service
@Transactional
public class DataSourceServiceImpl implements DataSourceService {

    private final Logger log = LoggerFactory.getLogger(DataSourceServiceImpl.class);

    private final DataSourceRepository dataSourceRepository;

    public DataSourceServiceImpl(DataSourceRepository dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
    }

    @Override
    public DataSource save(DataSource dataSource) {
        log.debug("Request to save DataSource : {}", dataSource);
        return dataSourceRepository.save(dataSource);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DataSource> findAll(Pageable pageable) {
        log.debug("Request to get all DataSources");
        return dataSourceRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DataSource> findOne(Long id) {
        log.debug("Request to get DataSource : {}", id);
        return dataSourceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataSource : {}", id);
        dataSourceRepository.deleteById(id);
    }
}
