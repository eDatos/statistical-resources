package org.siemac.metamac.statistical.resources.core.io.serviceapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;
import org.siemac.metamac.statistical.resources.core.mock.Mocks;
import org.siemac.metamac.statistical.resources.core.task.domain.AlternativeEnumeratedRepresentation;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.Task;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskProperties;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-service-mockito.xml", "classpath:spring/statistical-resources/include/rest-services-mockito.xml",
        "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DataManipulateTest extends StatisticalResourcesBaseTest {

    private static Logger                    logger                              = LoggerFactory.getLogger(DataManipulateTest.class);

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @Autowired
    private SrmRestInternalService           srmRestInternalService;

    @Autowired
    @Qualifier("txManager")
    protected PlatformTransactionManager     transactionManager;

    @Autowired
    private TaskService                      taskService;

    public String                            jobKey;

    private JdbcTemplate                     jdbcTemplateRepository;
    private JdbcTemplate                     jdbcTemplateResources;

    @PersistenceContext(unitName = "StatisticalResourcesEntityManagerFactory")
    protected EntityManager                  entityManager;

    private final ServiceContext             serviceContext                      = new ServiceContext("system", "123456", "junit");

    public static final String               DATA_STR_ECB_EXR_RG_XS              = "/sdmx/2_1/dataset/structured/ecb_exr_rg_xs.xml";
    public static final String               DATA_STR_ECB_EXR_RG_XS_DENORMALIZED = "/sdmx/2_1/dataset/structured/ecb_exr_rg_xs_denormalized.xml";
    public static final String               DATA_GEN_ECB_EXR_RG_FLAT            = "/sdmx/2_1/dataset/generic/ecb_exr_rg_flat.xml";
    public static final String               DATA_GEN_ECB_EXR_RG_FLAT_FAILED     = "/sdmx/2_1/dataset/generic/ecb_exr_rg_flat_failed.xml";

    public static final String               DATA_PX_ECB_EXR_RG                  = "/px/ecb_exr_rg.px";

    public static final String               DATA_TSV_ECB_EXR_RG                 = "/csv/ecb_exr_rg.tsv";

    public static final String               URN_DSD_ECB_EXR_RG                  = "urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR_RG(1.0)";

    @Autowired
    @Qualifier("dataSourceDatasetRepository")
    public void setDataSourceRepository(DataSource dataSource) throws Exception {
        Connection connection = null;
        try {
            this.jdbcTemplateRepository = new JdbcTemplate(dataSource);
            connection = dataSource.getConnection();
            connection.setAutoCommit(true);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Autowired
    @Qualifier("dataSource")
    public void setDataSourceResources(DataSource dataSource) throws Exception {
        Connection connection = null;
        try {
            this.jdbcTemplateResources = new JdbcTemplate(dataSource);
            connection = dataSource.getConnection();
            connection.setAutoCommit(true);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Before
    public void onBefore() throws Exception {
        Mocks.mockRestService(srmRestInternalService);

        clearDataBase(); // Clear dirty database
    }

    private class TableNameResultSetExtractor implements ResultSetExtractor<String> {

        @Override
        public String extractData(ResultSet rs) throws SQLException {
            return rs.getString(1);
        }
    }

    private class TableNameRowMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet rs, int line) throws SQLException {
            TableNameResultSetExtractor extractor = new TableNameResultSetExtractor();
            return extractor.extractData(rs);
        }
    }

    public void clearDataBase() {
        // REPOSITORY
        {
            List<String> tableNames = this.jdbcTemplateRepository.query("select TABLE_NAME from TB_DATASETS", new TableNameRowMapper());

            // Truncate tables
            this.jdbcTemplateRepository.update("truncate table TB_LOCALISED_STRINGS");
            this.jdbcTemplateRepository.update("truncate table TB_DATASET_DIMENSIONS");
            this.jdbcTemplateRepository.update("truncate table TB_ATTRIBUTE_DIMENSIONS");

            // Deletes
            this.jdbcTemplateRepository.update("delete from TB_ATTRIBUTES");
            this.jdbcTemplateRepository.update("delete from TB_DATASETS");
            this.jdbcTemplateRepository.update("delete from TB_INTERNATIONAL_STRINGS");

            // Drop table data
            for (String tableName : tableNames) {
                this.jdbcTemplateRepository.update("drop table " + tableName);
            }
        }

        // RESOURCES
        {
            this.jdbcTemplateResources.update("truncate table TB_TASKS");
        }

    }

    @Test
    public void testImportSdmx21Datasource() throws Exception {
        // New Transaction: Because the job needs persisted data
        createDatasetRepository("TEST_DATA_STR_ECB_EXR_RG", URN_DSD_ECB_EXR_RG);
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_ECB_EXR_RG);
                    taskInfoDataset.setDatasetVersionId("TEST_DATA_STR_ECB_EXR_RG");

                    // File 01
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_STR_ECB_EXR_RG_XS, "/"));
                        fileDescriptorDto.setFile(new File(DataManipulateTest.class.getResource(DATA_STR_ECB_EXR_RG_XS).toURI()));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    // File 02
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_GEN_ECB_EXR_RG_FLAT, "/"));
                        fileDescriptorDto.setFile(new File(DataManipulateTest.class.getResource(DATA_GEN_ECB_EXR_RG_FLAT).toURI()));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    jobKey = taskService.planifyImportationDataset(serviceContext, taskInfoDataset);

                } catch (MetamacException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished(true);

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.retrieveDatasetRepository("TEST_DATA_STR_ECB_EXR_RG");

        assertNotNull(datasetRepositoryDto);
    }

    @Test
    public void testImportSdmx21DatasourceWithTranslation() throws Exception {
        // New Transaction: Because the job needs persisted data
        createDatasetRepository("TEST_DATA_STR_ECB_EXR_RG", URN_DSD_ECB_EXR_RG);
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_ECB_EXR_RG);
                    taskInfoDataset.setDatasetVersionId("TEST_DATA_STR_ECB_EXR_RG");

                    {
                        AlternativeEnumeratedRepresentation alternative = new AlternativeEnumeratedRepresentation();
                        alternative.setComponentId("CURRENCY_DENOM");
                        alternative.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_DEMO(1.0)");
                        taskInfoDataset.getAlternativeRepresentations().add(alternative);
                    }

                    {
                        AlternativeEnumeratedRepresentation alternative = new AlternativeEnumeratedRepresentation();
                        alternative.setComponentId("CURRENCY");
                        alternative.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_DEMO(1.0)");
                        taskInfoDataset.getAlternativeRepresentations().add(alternative);
                    }

                    // File 01
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_STR_ECB_EXR_RG_XS, "/"));
                        fileDescriptorDto.setFile(new File(DataManipulateTest.class.getResource(DATA_STR_ECB_EXR_RG_XS_DENORMALIZED).toURI()));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    jobKey = taskService.planifyImportationDataset(serviceContext, taskInfoDataset);

                } catch (MetamacException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished(true);

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.retrieveDatasetRepository("TEST_DATA_STR_ECB_EXR_RG");

        assertNotNull(datasetRepositoryDto);
    }

    @Test
    public void testImportSdmx21Datasource_FAIL_WITH_RECOVERY() throws Exception {
        // New Transaction: Because the job needs persisted data
        createDatasetRepository("TEST_DATA_STR_ECB_EXR_RG", URN_DSD_ECB_EXR_RG);
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_ECB_EXR_RG);
                    taskInfoDataset.setDatasetVersionId("TEST_DATA_STR_ECB_EXR_RG");

                    // File 01
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_STR_ECB_EXR_RG_XS, "/"));
                        fileDescriptorDto.setFile(new File(DataManipulateTest.class.getResource(DATA_STR_ECB_EXR_RG_XS).toURI()));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    // File 02
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_GEN_ECB_EXR_RG_FLAT_FAILED, "/"));
                        fileDescriptorDto.setFile(new File(DataManipulateTest.class.getResource(DATA_GEN_ECB_EXR_RG_FLAT_FAILED).toURI()));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    jobKey = taskService.planifyImportationDataset(serviceContext, taskInfoDataset);

                } catch (MetamacException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job (importation) is finished
        waitUntilJobFinished(true);
        Thread.sleep(15 * 1000);

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.retrieveDatasetRepository("TEST_DATA_STR_ECB_EXR_RG");
        assertNotNull(datasetRepositoryDto);

        waitUntilJobFinished(false);

        List<ConditionalCriteria> conditionList = ConditionalCriteriaBuilder.criteriaFor(Task.class).withProperty(TaskProperties.job()).eq(jobKey).build();
        PagedResult<Task> pagedResult = taskService.findTasksByCondition(serviceContext, conditionList, PagingParameter.noLimits());
        assertTrue(pagedResult.getValues().isEmpty());
    }

    @Test
    public void testImportPxDatasource() throws Exception {
        // DSD
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(Mocks.mock_DSD_ECB_EXR_RG_for_PX());

        createDatasetRepository("TEST_DATA_STR_ECB_EXR_RG", URN_DSD_ECB_EXR_RG);

        // New Transaction: Because the job needs persisted data
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_ECB_EXR_RG);
                    taskInfoDataset.setDatasetVersionId("TEST_DATA_STR_ECB_EXR_RG");

                    // File 01
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_PX_ECB_EXR_RG, "/"));
                        fileDescriptorDto.setFile(new File(DataManipulateTest.class.getResource(DATA_PX_ECB_EXR_RG).toURI()));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.PX);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    jobKey = taskService.planifyImportationDataset(serviceContext, taskInfoDataset);

                } catch (MetamacException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished(true);

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.retrieveDatasetRepository("TEST_DATA_STR_ECB_EXR_RG");

        assertNotNull(datasetRepositoryDto);
    }

    @Test
    public void testImportCsvDatasource() throws Exception {

        createDatasetRepository("TEST_DATA_STR_ECB_EXR_RG", URN_DSD_ECB_EXR_RG);

        // New Transaction: Because the job needs persisted data
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_ECB_EXR_RG);
                    taskInfoDataset.setDatasetVersionId("TEST_DATA_STR_ECB_EXR_RG");

                    // File 01
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_TSV_ECB_EXR_RG, "/"));
                        fileDescriptorDto.setFile(new File(DataManipulateTest.class.getResource(DATA_TSV_ECB_EXR_RG).toURI()));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.CSV);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    jobKey = taskService.planifyImportationDataset(serviceContext, taskInfoDataset);

                } catch (MetamacException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished(true);

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.retrieveDatasetRepository("TEST_DATA_STR_ECB_EXR_RG");

        assertNotNull(datasetRepositoryDto);
    }

    @Test
    public void testDuplicateDatasource() throws Exception {
        // New Transaction: Because the job needs persisted data

        createDatasetRepository("TEST_DATA_STR_ECB_EXR_RG", URN_DSD_ECB_EXR_RG);

        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_ECB_EXR_RG);
                    taskInfoDataset.setDatasetVersionId("TEST_DATA_STR_ECB_EXR_RG");

                    // File 01
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_TSV_ECB_EXR_RG, "/"));
                        fileDescriptorDto.setFile(new File(DataManipulateTest.class.getResource(DATA_TSV_ECB_EXR_RG).toURI()));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.CSV);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    jobKey = taskService.planifyImportationDataset(serviceContext, taskInfoDataset);

                } catch (MetamacException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished(true);

        // Duplication JOB
        final TransactionTemplate tt2 = new TransactionTemplate(transactionManager);
        tt2.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt2.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_ECB_EXR_RG);
                    taskInfoDataset.setDatasetVersionId("TEST_DATA_STR_ECB_EXR_RG");

                    jobKey = taskService.planifyDuplicationDataset(serviceContext, taskInfoDataset, "TEST_DATA_STR_ECB_EXR_RG_NEW");

                } catch (MetamacException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished(true);

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.retrieveDatasetRepository("TEST_DATA_STR_ECB_EXR_RG_NEW");

        assertNotNull(datasetRepositoryDto);
    }

    private DatasetRepositoryDto createDatasetRepository(String datasetId, String dsdUrn) throws Exception {
        DatasetRepositoryDto datasetRepositoryDto = new DatasetRepositoryDto();
        datasetRepositoryDto.setDatasetId(datasetId);

        DataStructure dsd = srmRestInternalService.retrieveDsdByUrn(dsdUrn);

        List<DsdDimension> dimensions = DsdProcessor.getDimensions(dsd);
        for (DsdDimension dimension : dimensions) {
            datasetRepositoryDto.getDimensions().add(dimension.getComponentId());
        }

        // Attributes
        List<DsdAttribute> attributes = DsdProcessor.getAttributes(dsd);
        datasetRepositoryDto.getAttributes().addAll(ManipulateDataUtils.extractDefinitionOfAttributes(attributes));

        datasetRepositoryDto.setLanguages(Arrays.asList(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE));

        return datasetRepositoriesServiceFacade.createDatasetRepository(datasetRepositoryDto);
    }
}
