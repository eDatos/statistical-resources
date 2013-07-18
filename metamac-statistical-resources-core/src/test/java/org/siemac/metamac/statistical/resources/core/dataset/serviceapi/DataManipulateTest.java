package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.quartz.impl.SchedulerRepository;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.Metamac2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.ImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.mock.Mocks;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.task.serviceimpl.TaskServiceImpl;
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
    public static final String               DATA_GEN_ECB_EXR_RG_FLAT  = "/sdmx/2_1/dataset/generic/ecb_exr_rg_flat.xml";

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

//    @Test
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
                    taskInfoDataset.setRepoDatasetId("TEST_DATA_GEN_ECB_EXR_RG_FLAT");

                    FileDescriptor fileDescriptorDto = new FileDescriptor();
                    fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.SDMX_2_1);
                    fileDescriptorDto.setFileName(StringUtils.substringAfterLast(DATA_GEN_ECB_EXR_RG_FLAT, "/"));
                    fileDescriptorDto.setInputMessage(DataManipulateTest.class.getResourceAsStream(DATA_GEN_ECB_EXR_RG_FLAT));
                    taskInfoDataset.addFile(fileDescriptorDto);

                    jobKey = taskService.plannifyImportationDataset(serviceContext, taskInfoDataset);

                } catch (MetamacException e) {
                    e.printStackTrace();
                }
                logger.info("-- doInTransactionWithoutResult -- expects transaction commit");
            }
        });

        // Wait until the job is finished
        waitUntilJobFinished();
    }

    // @Test
    // @DirtyDatabase
    public void testKaka() throws Exception {

        // Scheduler an importation job
        Scheduler sched = SchedulerRepository.getInstance().lookup(TaskServiceImpl.SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

        JobKey kakajobKey = new JobKey("probando", "importation");
        TriggerKey triggerKey = new TriggerKey("trigger_" + "probando", "importation");
        {
            // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
            JobDetail job = newJob(ImportDatasetJob.class).withIdentity(kakajobKey).requestRecovery().build();

            SimpleTrigger trigger = newTrigger().withIdentity(triggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();

            sched.scheduleJob(job, trigger);
        }

        sched.checkExists(kakajobKey);
        sched.checkExists(triggerKey);

        {
            // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
            JobDetail job = newJob(ImportDatasetJob.class).withIdentity(kakajobKey).requestRecovery().build();

            SimpleTrigger trigger = newTrigger().withIdentity(triggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();

            sched.scheduleJob(job, trigger);
        }

        sched.checkExists(new JobKey("probando", "importation"));

        // Validation: There shouldn't be an import processing..
        if (sched.getCurrentlyExecutingJobs().size() != 0) {
        }

    }
}
