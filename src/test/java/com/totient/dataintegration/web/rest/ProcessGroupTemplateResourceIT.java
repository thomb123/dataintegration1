package com.totient.dataintegration.web.rest;

import com.totient.dataintegration.Dataintegration1App;
import com.totient.dataintegration.domain.ProcessGroupTemplate;
import com.totient.dataintegration.repository.ProcessGroupTemplateRepository;
import com.totient.dataintegration.service.ProcessGroupTemplateService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.totient.dataintegration.domain.enumeration.RecordStatus;
/**
 * Integration tests for the {@link ProcessGroupTemplateResource} REST controller.
 */
@SpringBootTest(classes = Dataintegration1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProcessGroupTemplateResourceIT {

    private static final Long DEFAULT_PROCESS_GROUP_TEMPLATE_ID = 1L;
    private static final Long UPDATED_PROCESS_GROUP_TEMPLATE_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_REGISTRY_ID = UUID.randomUUID();
    private static final UUID UPDATED_REGISTRY_ID = UUID.randomUUID();

    private static final UUID DEFAULT_BUCKET_ID = UUID.randomUUID();
    private static final UUID UPDATED_BUCKET_ID = UUID.randomUUID();

    private static final UUID DEFAULT_FLOW_ID = UUID.randomUUID();
    private static final UUID UPDATED_FLOW_ID = UUID.randomUUID();

    private static final Integer DEFAULT_PROCESS_GROUP_VERSION = 1;
    private static final Integer UPDATED_PROCESS_GROUP_VERSION = 2;

    private static final RecordStatus DEFAULT_RECORD_STATUS = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_RECORD_STATUS = RecordStatus.ARCHIVED;

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private ProcessGroupTemplateRepository processGroupTemplateRepository;

    @Autowired
    private ProcessGroupTemplateService processGroupTemplateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessGroupTemplateMockMvc;

    private ProcessGroupTemplate processGroupTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessGroupTemplate createEntity(EntityManager em) {
        ProcessGroupTemplate processGroupTemplate = new ProcessGroupTemplate()
            .processGroupTemplateId(DEFAULT_PROCESS_GROUP_TEMPLATE_ID)
            .name(DEFAULT_NAME)
            .registryId(DEFAULT_REGISTRY_ID)
            .bucketId(DEFAULT_BUCKET_ID)
            .flowId(DEFAULT_FLOW_ID)
            .processGroupVersion(DEFAULT_PROCESS_GROUP_VERSION)
            .recordStatus(DEFAULT_RECORD_STATUS)
            .version(DEFAULT_VERSION);
        return processGroupTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessGroupTemplate createUpdatedEntity(EntityManager em) {
        ProcessGroupTemplate processGroupTemplate = new ProcessGroupTemplate()
            .processGroupTemplateId(UPDATED_PROCESS_GROUP_TEMPLATE_ID)
            .name(UPDATED_NAME)
            .registryId(UPDATED_REGISTRY_ID)
            .bucketId(UPDATED_BUCKET_ID)
            .flowId(UPDATED_FLOW_ID)
            .processGroupVersion(UPDATED_PROCESS_GROUP_VERSION)
            .recordStatus(UPDATED_RECORD_STATUS)
            .version(UPDATED_VERSION);
        return processGroupTemplate;
    }

    @BeforeEach
    public void initTest() {
        processGroupTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessGroupTemplate() throws Exception {
        int databaseSizeBeforeCreate = processGroupTemplateRepository.findAll().size();
        // Create the ProcessGroupTemplate
        restProcessGroupTemplateMockMvc.perform(post("/api/process-group-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processGroupTemplate)))
            .andExpect(status().isCreated());

        // Validate the ProcessGroupTemplate in the database
        List<ProcessGroupTemplate> processGroupTemplateList = processGroupTemplateRepository.findAll();
        assertThat(processGroupTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessGroupTemplate testProcessGroupTemplate = processGroupTemplateList.get(processGroupTemplateList.size() - 1);
        assertThat(testProcessGroupTemplate.getProcessGroupTemplateId()).isEqualTo(DEFAULT_PROCESS_GROUP_TEMPLATE_ID);
        assertThat(testProcessGroupTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProcessGroupTemplate.getRegistryId()).isEqualTo(DEFAULT_REGISTRY_ID);
        assertThat(testProcessGroupTemplate.getBucketId()).isEqualTo(DEFAULT_BUCKET_ID);
        assertThat(testProcessGroupTemplate.getFlowId()).isEqualTo(DEFAULT_FLOW_ID);
        assertThat(testProcessGroupTemplate.getProcessGroupVersion()).isEqualTo(DEFAULT_PROCESS_GROUP_VERSION);
        assertThat(testProcessGroupTemplate.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
        assertThat(testProcessGroupTemplate.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createProcessGroupTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processGroupTemplateRepository.findAll().size();

        // Create the ProcessGroupTemplate with an existing ID
        processGroupTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessGroupTemplateMockMvc.perform(post("/api/process-group-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processGroupTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessGroupTemplate in the database
        List<ProcessGroupTemplate> processGroupTemplateList = processGroupTemplateRepository.findAll();
        assertThat(processGroupTemplateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = processGroupTemplateRepository.findAll().size();
        // set the field null
        processGroupTemplate.setName(null);

        // Create the ProcessGroupTemplate, which fails.


        restProcessGroupTemplateMockMvc.perform(post("/api/process-group-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processGroupTemplate)))
            .andExpect(status().isBadRequest());

        List<ProcessGroupTemplate> processGroupTemplateList = processGroupTemplateRepository.findAll();
        assertThat(processGroupTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = processGroupTemplateRepository.findAll().size();
        // set the field null
        processGroupTemplate.setRegistryId(null);

        // Create the ProcessGroupTemplate, which fails.


        restProcessGroupTemplateMockMvc.perform(post("/api/process-group-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processGroupTemplate)))
            .andExpect(status().isBadRequest());

        List<ProcessGroupTemplate> processGroupTemplateList = processGroupTemplateRepository.findAll();
        assertThat(processGroupTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBucketIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = processGroupTemplateRepository.findAll().size();
        // set the field null
        processGroupTemplate.setBucketId(null);

        // Create the ProcessGroupTemplate, which fails.


        restProcessGroupTemplateMockMvc.perform(post("/api/process-group-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processGroupTemplate)))
            .andExpect(status().isBadRequest());

        List<ProcessGroupTemplate> processGroupTemplateList = processGroupTemplateRepository.findAll();
        assertThat(processGroupTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlowIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = processGroupTemplateRepository.findAll().size();
        // set the field null
        processGroupTemplate.setFlowId(null);

        // Create the ProcessGroupTemplate, which fails.


        restProcessGroupTemplateMockMvc.perform(post("/api/process-group-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processGroupTemplate)))
            .andExpect(status().isBadRequest());

        List<ProcessGroupTemplate> processGroupTemplateList = processGroupTemplateRepository.findAll();
        assertThat(processGroupTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcessGroupTemplates() throws Exception {
        // Initialize the database
        processGroupTemplateRepository.saveAndFlush(processGroupTemplate);

        // Get all the processGroupTemplateList
        restProcessGroupTemplateMockMvc.perform(get("/api/process-group-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processGroupTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].processGroupTemplateId").value(hasItem(DEFAULT_PROCESS_GROUP_TEMPLATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].registryId").value(hasItem(DEFAULT_REGISTRY_ID.toString())))
            .andExpect(jsonPath("$.[*].bucketId").value(hasItem(DEFAULT_BUCKET_ID.toString())))
            .andExpect(jsonPath("$.[*].flowId").value(hasItem(DEFAULT_FLOW_ID.toString())))
            .andExpect(jsonPath("$.[*].processGroupVersion").value(hasItem(DEFAULT_PROCESS_GROUP_VERSION)))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }
    
    @Test
    @Transactional
    public void getProcessGroupTemplate() throws Exception {
        // Initialize the database
        processGroupTemplateRepository.saveAndFlush(processGroupTemplate);

        // Get the processGroupTemplate
        restProcessGroupTemplateMockMvc.perform(get("/api/process-group-templates/{id}", processGroupTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processGroupTemplate.getId().intValue()))
            .andExpect(jsonPath("$.processGroupTemplateId").value(DEFAULT_PROCESS_GROUP_TEMPLATE_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.registryId").value(DEFAULT_REGISTRY_ID.toString()))
            .andExpect(jsonPath("$.bucketId").value(DEFAULT_BUCKET_ID.toString()))
            .andExpect(jsonPath("$.flowId").value(DEFAULT_FLOW_ID.toString()))
            .andExpect(jsonPath("$.processGroupVersion").value(DEFAULT_PROCESS_GROUP_VERSION))
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }
    @Test
    @Transactional
    public void getNonExistingProcessGroupTemplate() throws Exception {
        // Get the processGroupTemplate
        restProcessGroupTemplateMockMvc.perform(get("/api/process-group-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessGroupTemplate() throws Exception {
        // Initialize the database
        processGroupTemplateService.save(processGroupTemplate);

        int databaseSizeBeforeUpdate = processGroupTemplateRepository.findAll().size();

        // Update the processGroupTemplate
        ProcessGroupTemplate updatedProcessGroupTemplate = processGroupTemplateRepository.findById(processGroupTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedProcessGroupTemplate are not directly saved in db
        em.detach(updatedProcessGroupTemplate);
        updatedProcessGroupTemplate
            .processGroupTemplateId(UPDATED_PROCESS_GROUP_TEMPLATE_ID)
            .name(UPDATED_NAME)
            .registryId(UPDATED_REGISTRY_ID)
            .bucketId(UPDATED_BUCKET_ID)
            .flowId(UPDATED_FLOW_ID)
            .processGroupVersion(UPDATED_PROCESS_GROUP_VERSION)
            .recordStatus(UPDATED_RECORD_STATUS)
            .version(UPDATED_VERSION);

        restProcessGroupTemplateMockMvc.perform(put("/api/process-group-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessGroupTemplate)))
            .andExpect(status().isOk());

        // Validate the ProcessGroupTemplate in the database
        List<ProcessGroupTemplate> processGroupTemplateList = processGroupTemplateRepository.findAll();
        assertThat(processGroupTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProcessGroupTemplate testProcessGroupTemplate = processGroupTemplateList.get(processGroupTemplateList.size() - 1);
        assertThat(testProcessGroupTemplate.getProcessGroupTemplateId()).isEqualTo(UPDATED_PROCESS_GROUP_TEMPLATE_ID);
        assertThat(testProcessGroupTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcessGroupTemplate.getRegistryId()).isEqualTo(UPDATED_REGISTRY_ID);
        assertThat(testProcessGroupTemplate.getBucketId()).isEqualTo(UPDATED_BUCKET_ID);
        assertThat(testProcessGroupTemplate.getFlowId()).isEqualTo(UPDATED_FLOW_ID);
        assertThat(testProcessGroupTemplate.getProcessGroupVersion()).isEqualTo(UPDATED_PROCESS_GROUP_VERSION);
        assertThat(testProcessGroupTemplate.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testProcessGroupTemplate.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessGroupTemplate() throws Exception {
        int databaseSizeBeforeUpdate = processGroupTemplateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessGroupTemplateMockMvc.perform(put("/api/process-group-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processGroupTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessGroupTemplate in the database
        List<ProcessGroupTemplate> processGroupTemplateList = processGroupTemplateRepository.findAll();
        assertThat(processGroupTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcessGroupTemplate() throws Exception {
        // Initialize the database
        processGroupTemplateService.save(processGroupTemplate);

        int databaseSizeBeforeDelete = processGroupTemplateRepository.findAll().size();

        // Delete the processGroupTemplate
        restProcessGroupTemplateMockMvc.perform(delete("/api/process-group-templates/{id}", processGroupTemplate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessGroupTemplate> processGroupTemplateList = processGroupTemplateRepository.findAll();
        assertThat(processGroupTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
