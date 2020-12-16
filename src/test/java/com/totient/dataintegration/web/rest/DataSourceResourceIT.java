package com.totient.dataintegration.web.rest;

import com.totient.dataintegration.Dataintegration1App;
import com.totient.dataintegration.domain.DataSource;
import com.totient.dataintegration.repository.DataSourceRepository;
import com.totient.dataintegration.service.DataSourceService;

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

import com.totient.dataintegration.domain.enumeration.Status;
import com.totient.dataintegration.domain.enumeration.RecordStatus;
/**
 * Integration tests for the {@link DataSourceResource} REST controller.
 */
@SpringBootTest(classes = Dataintegration1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class DataSourceResourceIT {

    private static final Long DEFAULT_DATASOURCE_ID = 1L;
    private static final Long UPDATED_DATASOURCE_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.SCHEDULING;

    private static final UUID DEFAULT_PROCESS_GROUP_ID = UUID.randomUUID();
    private static final UUID UPDATED_PROCESS_GROUP_ID = UUID.randomUUID();

    private static final String DEFAULT_SCHEDULE = "AAAAAAAAAA";
    private static final String UPDATED_SCHEDULE = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIGURATION = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURATION = "BBBBBBBBBB";

    private static final RecordStatus DEFAULT_RECORD_STATUS = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_RECORD_STATUS = RecordStatus.ARCHIVED;

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataSourceMockMvc;

    private DataSource dataSource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataSource createEntity(EntityManager em) {
        DataSource dataSource = new DataSource()
            .datasourceId(DEFAULT_DATASOURCE_ID)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .processGroupId(DEFAULT_PROCESS_GROUP_ID)
            .schedule(DEFAULT_SCHEDULE)
            .configuration(DEFAULT_CONFIGURATION)
            .recordStatus(DEFAULT_RECORD_STATUS)
            .version(DEFAULT_VERSION);
        return dataSource;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataSource createUpdatedEntity(EntityManager em) {
        DataSource dataSource = new DataSource()
            .datasourceId(UPDATED_DATASOURCE_ID)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .processGroupId(UPDATED_PROCESS_GROUP_ID)
            .schedule(UPDATED_SCHEDULE)
            .configuration(UPDATED_CONFIGURATION)
            .recordStatus(UPDATED_RECORD_STATUS)
            .version(UPDATED_VERSION);
        return dataSource;
    }

    @BeforeEach
    public void initTest() {
        dataSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataSource() throws Exception {
        int databaseSizeBeforeCreate = dataSourceRepository.findAll().size();
        // Create the DataSource
        restDataSourceMockMvc.perform(post("/api/data-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataSource)))
            .andExpect(status().isCreated());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeCreate + 1);
        DataSource testDataSource = dataSourceList.get(dataSourceList.size() - 1);
        assertThat(testDataSource.getDatasourceId()).isEqualTo(DEFAULT_DATASOURCE_ID);
        assertThat(testDataSource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataSource.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDataSource.getProcessGroupId()).isEqualTo(DEFAULT_PROCESS_GROUP_ID);
        assertThat(testDataSource.getSchedule()).isEqualTo(DEFAULT_SCHEDULE);
        assertThat(testDataSource.getConfiguration()).isEqualTo(DEFAULT_CONFIGURATION);
        assertThat(testDataSource.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
        assertThat(testDataSource.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createDataSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataSourceRepository.findAll().size();

        // Create the DataSource with an existing ID
        dataSource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataSourceMockMvc.perform(post("/api/data-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataSource)))
            .andExpect(status().isBadRequest());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceRepository.findAll().size();
        // set the field null
        dataSource.setName(null);

        // Create the DataSource, which fails.


        restDataSourceMockMvc.perform(post("/api/data-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataSource)))
            .andExpect(status().isBadRequest());

        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataSources() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        // Get all the dataSourceList
        restDataSourceMockMvc.perform(get("/api/data-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].datasourceId").value(hasItem(DEFAULT_DATASOURCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].processGroupId").value(hasItem(DEFAULT_PROCESS_GROUP_ID.toString())))
            .andExpect(jsonPath("$.[*].schedule").value(hasItem(DEFAULT_SCHEDULE)))
            .andExpect(jsonPath("$.[*].configuration").value(hasItem(DEFAULT_CONFIGURATION)))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }
    
    @Test
    @Transactional
    public void getDataSource() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        // Get the dataSource
        restDataSourceMockMvc.perform(get("/api/data-sources/{id}", dataSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataSource.getId().intValue()))
            .andExpect(jsonPath("$.datasourceId").value(DEFAULT_DATASOURCE_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.processGroupId").value(DEFAULT_PROCESS_GROUP_ID.toString()))
            .andExpect(jsonPath("$.schedule").value(DEFAULT_SCHEDULE))
            .andExpect(jsonPath("$.configuration").value(DEFAULT_CONFIGURATION))
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }
    @Test
    @Transactional
    public void getNonExistingDataSource() throws Exception {
        // Get the dataSource
        restDataSourceMockMvc.perform(get("/api/data-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataSource() throws Exception {
        // Initialize the database
        dataSourceService.save(dataSource);

        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();

        // Update the dataSource
        DataSource updatedDataSource = dataSourceRepository.findById(dataSource.getId()).get();
        // Disconnect from session so that the updates on updatedDataSource are not directly saved in db
        em.detach(updatedDataSource);
        updatedDataSource
            .datasourceId(UPDATED_DATASOURCE_ID)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .processGroupId(UPDATED_PROCESS_GROUP_ID)
            .schedule(UPDATED_SCHEDULE)
            .configuration(UPDATED_CONFIGURATION)
            .recordStatus(UPDATED_RECORD_STATUS)
            .version(UPDATED_VERSION);

        restDataSourceMockMvc.perform(put("/api/data-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataSource)))
            .andExpect(status().isOk());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
        DataSource testDataSource = dataSourceList.get(dataSourceList.size() - 1);
        assertThat(testDataSource.getDatasourceId()).isEqualTo(UPDATED_DATASOURCE_ID);
        assertThat(testDataSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataSource.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDataSource.getProcessGroupId()).isEqualTo(UPDATED_PROCESS_GROUP_ID);
        assertThat(testDataSource.getSchedule()).isEqualTo(UPDATED_SCHEDULE);
        assertThat(testDataSource.getConfiguration()).isEqualTo(UPDATED_CONFIGURATION);
        assertThat(testDataSource.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testDataSource.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingDataSource() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataSourceMockMvc.perform(put("/api/data-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataSource)))
            .andExpect(status().isBadRequest());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataSource() throws Exception {
        // Initialize the database
        dataSourceService.save(dataSource);

        int databaseSizeBeforeDelete = dataSourceRepository.findAll().size();

        // Delete the dataSource
        restDataSourceMockMvc.perform(delete("/api/data-sources/{id}", dataSource.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
