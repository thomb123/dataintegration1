package com.totient.dataintegration.web.rest;

import com.totient.dataintegration.domain.DataSource;
import com.totient.dataintegration.service.DataSourceService;
import com.totient.dataintegration.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.totient.dataintegration.domain.DataSource}.
 */
@RestController
@RequestMapping("/api")
public class DataSourceResource {

    private final Logger log = LoggerFactory.getLogger(DataSourceResource.class);

    private static final String ENTITY_NAME = "dataintegration1DataSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataSourceService dataSourceService;

    public DataSourceResource(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    /**
     * {@code POST  /data-sources} : Create a new dataSource.
     *
     * @param dataSource the dataSource to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataSource, or with status {@code 400 (Bad Request)} if the dataSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-sources")
    public ResponseEntity<DataSource> createDataSource(@RequestBody DataSource dataSource) throws URISyntaxException {
        log.debug("REST request to save DataSource : {}", dataSource);
        if (dataSource.getId() != null) {
            throw new BadRequestAlertException("A new dataSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataSource result = dataSourceService.save(dataSource);
        return ResponseEntity.created(new URI("/api/data-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-sources} : Updates an existing dataSource.
     *
     * @param dataSource the dataSource to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataSource,
     * or with status {@code 400 (Bad Request)} if the dataSource is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataSource couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-sources")
    public ResponseEntity<DataSource> updateDataSource(@RequestBody DataSource dataSource) throws URISyntaxException {
        log.debug("REST request to update DataSource : {}", dataSource);
        if (dataSource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataSource result = dataSourceService.save(dataSource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataSource.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-sources} : get all the dataSources.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataSources in body.
     */
    @GetMapping("/data-sources")
    public ResponseEntity<List<DataSource>> getAllDataSources(Pageable pageable) {
        log.debug("REST request to get a page of DataSources");
        Page<DataSource> page = dataSourceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /data-sources/:id} : get the "id" dataSource.
     *
     * @param id the id of the dataSource to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataSource, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-sources/{id}")
    public ResponseEntity<DataSource> getDataSource(@PathVariable Long id) {
        log.debug("REST request to get DataSource : {}", id);
        Optional<DataSource> dataSource = dataSourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataSource);
    }

    /**
     * {@code DELETE  /data-sources/:id} : delete the "id" dataSource.
     *
     * @param id the id of the dataSource to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-sources/{id}")
    public ResponseEntity<Void> deleteDataSource(@PathVariable Long id) {
        log.debug("REST request to delete DataSource : {}", id);
        dataSourceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
