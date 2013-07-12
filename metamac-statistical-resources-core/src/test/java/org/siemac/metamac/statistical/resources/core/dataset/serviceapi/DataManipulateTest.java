package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.Metamac2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.dto.task.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.mock.Mocks;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DataManipulateTest extends StatisticalResourcesBaseTest {

    private static Logger                    logger                    = LoggerFactory.getLogger(DataManipulateTest.class);

    @Autowired
    private Metamac2StatRepoMapper           metamac2StatRepoMapper;

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

    @PersistenceContext(unitName = "StatisticalResourcesEntityManagerFactory")
    protected EntityManager                  entityManager;

    private final ServiceContext             serviceContext            = new ServiceContext("system", "123456", "junit");

    public static final String               DATA_GEN_ECB_EXR_RG_XS    = "/sdmx/2_1/dataset/structured/ecb_exr_rg_xs.xml";
    public static final String               URN_DSD_GEN_ECB_EXR_RG_XS = "urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR_RG(1.0)";
    public static final String               DATA_GEN_ECB_EXR_RG_FLAT  = "/sdmx/2_1/dataset/structured/ecb_exr_rg_flat.xml";

    @Before
    public void onBeforeTest() {
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(Mocks.mock_DSD_ECB_EXR_RG());

        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_DECIMALS(1.0)")).thenReturn(Mocks.mock_CL_DECIMALS());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_FREQ(1.0)")).thenReturn(Mocks.mock_CL_FREQ());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_CONF_STATUS(1.0)")).thenReturn(Mocks.mock_CL_CONF_STATUS());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_OBS_STATUS(1.0)")).thenReturn(Mocks.mock_CL_OBS_STATUS());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_UNIT_MULT(1.0)")).thenReturn(Mocks.mock_CL_UNIT_MULT());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_TYPE(1.0)")).thenReturn(Mocks.mock_CL_EXR_TYPE());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_VAR(1.0)")).thenReturn(Mocks.mock_CL_EXR_VAR());
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)")).thenReturn(Mocks.mock_CL_CURRENCY());

        Mockito.when(srmRestInternalService.retrieveConceptSchemeByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=SDMX:CROSS_DOMAIN_CONCEPTS(1.0)")).thenReturn(
                Mocks.mock_CROSS_DOMAIN_CONCEPTS());

        Mockito.when(srmRestInternalService.retrieveConceptSchemeByUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=ECB:ECB_CONCEPTS(1.0)")).thenReturn(Mocks.mock_ECB_CONCEPTS());
    }

    @Test
    public void testImportSdmx21Datasource() throws Exception {

        // ManipulateSdmx21DataCallbackImpl callback = new ManipulateSdmx21DataCallbackImpl(mockDSD_ECB_EXR_RG(), metamac2StatRepoMapper, datasetRepositoriesServiceFacade);
        // InputStream sdmxStream = DataManipulateTest.class.getResourceAsStream(DATA_GEN_ECB_EXR_RG_FLAT);
        //
        // Sdmx21Parser.parseData(sdmxStream, callback);
        //
        // int kaka = 2;
    }

    // @Test
    // @DirtyDatabase
    public void testImport_Sdmx21Datasource() throws Exception {
        // New Transaction: Because the job needs persisted data
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
                    taskInfoDataset.setDataStructureUrn(URN_DSD_GEN_ECB_EXR_RG_XS);
                    taskInfoDataset.setFileName(StringUtils.substringAfterLast(DATA_GEN_ECB_EXR_RG_FLAT, "/"));
                    taskInfoDataset.setRepoDatasetId(UUID.randomUUID().toString());
                    jobKey = taskService.plannifyImportationDataset(serviceContext, DataManipulateTest.class.getResourceAsStream(DATA_GEN_ECB_EXR_RG_FLAT), taskInfoDataset);
                } catch (MetamacException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished();
    }
}
