package com.totient.dataintegration.web.rest;

import com.totient.dataintegration.domain.ProcessGroupTemplate;
import com.totient.dataintegration.service.ProcessGroupTemplateService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.totient.dataintegration.domain.ProcessGroupTemplate}.
 */
@RestController
@RequestMapping("/api")
public class ProcessGroupTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ProcessGroupTemplateResource.class);

    private static final String ENTITY_NAME = "dataintegration1ProcessGroupTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessGroupTemplateService processGroupTemplateService;

    public ProcessGroupTemplateResource(ProcessGroupTemplateService processGroupTemplateService) {
        this.processGroupTemplateService = processGroupTemplateService;
    }

    /**
     * {@code POST  /process-group-templates} : Create a new processGroupTemplate.
     *
     * @param processGroupTemplate the processGroupTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processGroupTemplate, or with status {@code 400 (Bad Request)} if the processGroupTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-group-templates")
    public ResponseEntity<ProcessGroupTemplate> createProcessGroupTemplate(@Valid @RequestBody ProcessGroupTemplate processGroupTemplate) throws URISyntaxException {
        log.debug("REST request to save ProcessGroupTemplate : {}", processGroupTemplate);
        if (processGroupTemplate.getId() != null) {
            throw new BadRequestAlertException("A new processGroupTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessGroupTemplate result = processGroupTemplateService.save(processGroupTemplate);
        return ResponseEntity.created(new URI("/api/process-group-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-group-templates} : Updates an existing processGroupTemplate.
     *
     * @param processGroupTemplate the processGroupTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processGroupTemplate,
     * or with status {@code 400 (Bad Request)} if the processGroupTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processGroupTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-group-templates")
    public ResponseEntity<ProcessGroupTemplate> updateProcessGroupTemplate(@Valid @RequestBody ProcessGroupTemplate processGroupTemplate) throws URISyntaxException {
        log.debug("REST request to update ProcessGroupTemplate : {}", processGroupTemplate);
        if (processGroupTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessGroupTemplate result = processGroupTemplateService.save(processGroupTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processGroupTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /process-group-templates} : get all the processGroupTemplates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processGroupTemplates in body.
     */
    @GetMapping("/process-group-templates")
    public ResponseEntity<List<ProcessGroupTemplate>> getAllProcessGroupTemplates(Pageable pageable) {
        log.debug("REST request to get a page of ProcessGroupTemplates");
        Page<ProcessGroupTemplate> page = processGroupTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-group-templates/:id} : get the "id" processGroupTemplate.
     *
     * @param id the id of the processGroupTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processGroupTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-group-templates/{id}")
    public ResponseEntity<ProcessGroupTemplate> getProcessGroupTemplate(@PathVariable Long id) {
        log.debug("REST request to get ProcessGroupTemplate : {}", id);
        Optional<ProcessGroupTemplate> processGroupTemplate = processGroupTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processGroupTemplate);
    }

    /**
     * {@code DELETE  /process-group-templates/:id} : delete the "id" processGroupTemplate.
     *
     * @param id the id of the processGroupTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-group-templates/{id}")
    public ResponseEntity<Void> deleteProcessGroupTemplate(@PathVariable Long id) {
        log.debug("REST request to delete ProcessGroupTemplate : {}", id);
        processGroupTemplateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
