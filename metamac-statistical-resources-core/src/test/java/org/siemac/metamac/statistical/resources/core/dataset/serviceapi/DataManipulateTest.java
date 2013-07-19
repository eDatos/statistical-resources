package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.mock.Mocks;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
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

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DataManipulateTest extends StatisticalResourcesBaseTest {

    private static Logger                    logger                          = LoggerFactory.getLogger(DataManipulateTest.class);

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

    private final ServiceContext             serviceContext                  = new ServiceContext("system", "123456", "junit");

    public static final String               DATA_STR_ECB_EXR_RG_XS          = "/sdmx/2_1/dataset/structured/ecb_exr_rg_xs.xml";
    public static final String               DATA_GEN_ECB_EXR_RG_FLAT        = "/sdmx/2_1/dataset/generic/ecb_exr_rg_flat.xml";
    public static final String               DATA_GEN_ECB_EXR_RG_FLAT_FAILED = "/sdmx/2_1/dataset/generic/ecb_exr_rg_flat_failed.xml";
    public static final String               URN_DSD_ECB_EXR_RG              = "urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR_RG(1.0)";

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
    public void onBeforeTest() throws Exception {
        // DSD
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(Mocks.mock_DSD_ECB_EXR_RG());

        // CODELIST
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_DECIMALS(1.0)")).thenReturn(Mocks.mock_CL_DECIMALS());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_FREQ(1.0)")).thenReturn(Mocks.mock_CL_FREQ());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_CONF_STATUS(1.0)")).thenReturn(Mocks.mock_CL_CONF_STATUS());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_OBS_STATUS(1.0)")).thenReturn(Mocks.mock_CL_OBS_STATUS());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_UNIT_MULT(1.0)")).thenReturn(Mocks.mock_CL_UNIT_MULT());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_TYPE(1.0)")).thenReturn(Mocks.mock_CL_EXR_TYPE());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_VAR(1.0)")).thenReturn(Mocks.mock_CL_EXR_VAR());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)")).thenReturn(Mocks.mock_CL_CURRENCY());

        // CONCEPT SCHEME
        Mockito.when(srmRestInternalService.retrieveConceptSchemeByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=SDMX:CROSS_DOMAIN_CONCEPTS(1.0)")).thenReturn(
                Mocks.mock_CROSS_DOMAIN_CONCEPTS());

        Mockito.when(srmRestInternalService.retrieveConceptSchemeByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=ECB:ECB_CONCEPTS(1.0)")).thenReturn(Mocks.mock_ECB_CONCEPTS());

        // CONCEPT
        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).FREQ")).thenReturn(
                Mocks.mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_FREQ());

        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).CURRENCY_DENOM")).thenReturn(
                Mocks.mock_ECB_CONCEPTS_1_0_CURRENCY_DENOM());

        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_TYPE"))
                .thenReturn(Mocks.mock_ECB_CONCEPTS_1_0_EXR_TYPE());

        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_VAR")).thenReturn(Mocks.mock_ECB_CONCEPTS_1_0_EXR_VAR());

        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).COLL_METHOD")).thenReturn(
                Mocks.mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_COLL_METHOD());

        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).DECIMALS")).thenReturn(
                Mocks.mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_DECIMALS());

        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).UNIT_MULT")).thenReturn(
                Mocks.mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_UNIT_MULT());

        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).CONF_STATUS")).thenReturn(
                Mocks.mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_CONF_STATUS());

        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_STATUS")).thenReturn(
                Mocks.mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_OBS_STATUS());

        Mockito.when(srmRestInternalService.retrieveConceptByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).TITLE")).thenReturn(
                Mocks.mock_SDMX_CROSS_DOMAIN_1_0_TITLE());

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
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_ECB_EXR_RG);
                    taskInfoDataset.setRepoDatasetId("TEST_DATA_STR_ECB_EXR_RG");

                    // File 01
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_STR_ECB_EXR_RG_XS, "/"));
                        fileDescriptorDto.setInputMessage(DataManipulateTest.class.getResourceAsStream(DATA_STR_ECB_EXR_RG_XS));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    // File 02
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_GEN_ECB_EXR_RG_FLAT, "/"));
                        fileDescriptorDto.setInputMessage(DataManipulateTest.class.getResourceAsStream(DATA_GEN_ECB_EXR_RG_FLAT));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    jobKey = taskService.planifyImportationDataset(serviceContext, taskInfoDataset);

                } catch (MetamacException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished();

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.retrieveDatasetRepository("TEST_DATA_STR_ECB_EXR_RG");

        assertNotNull(datasetRepositoryDto);
    }

    @Test
    public void testImportSdmx21Datasource_FAIL_WITH_RECOVERY() throws Exception {
        // New Transaction: Because the job needs persisted data
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_ECB_EXR_RG);
                    taskInfoDataset.setRepoDatasetId("TEST_DATA_STR_ECB_EXR_RG");

                    // File 01
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_STR_ECB_EXR_RG_XS, "/"));
                        fileDescriptorDto.setInputMessage(DataManipulateTest.class.getResourceAsStream(DATA_STR_ECB_EXR_RG_XS));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    // File 02
                    {
                        FileDescriptor fileDescriptorDto = new FileDescriptor();
                        fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_GEN_ECB_EXR_RG_FLAT_FAILED, "/"));
                        fileDescriptorDto.setInputMessage(DataManipulateTest.class.getResourceAsStream(DATA_GEN_ECB_EXR_RG_FLAT_FAILED));
                        fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                        taskInfoDataset.addFile(fileDescriptorDto);
                    }

                    jobKey = taskService.planifyImportationDataset(serviceContext, taskInfoDataset);

                } catch (MetamacException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished();

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.retrieveDatasetRepository("TEST_DATA_STR_ECB_EXR_RG");

        assertNotNull(datasetRepositoryDto);
    }

}
