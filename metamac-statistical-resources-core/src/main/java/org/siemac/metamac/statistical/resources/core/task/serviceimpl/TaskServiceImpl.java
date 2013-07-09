package org.siemac.metamac.statistical.resources.core.task.serviceimpl;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;
import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.Metamac2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.ImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.ManipulateSdmx21DataCallbackImpl;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.validators.TaskServiceInvocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.Sdmx21Parser;

/**
 * Implementation of TaskService.
 */
@Service("taskService")
public class TaskServiceImpl extends TaskServiceImplBase {

    public static final String               SCHEDULER_INSTANCE_NAME = "StatisticalResourcesScheduler";

    @Autowired
    private TaskServiceInvocationValidator   taskServiceInvocationValidator;

    @Autowired
    private Metamac2StatRepoMapper           metamac2StatRepoMapper;

    @Autowired
    private SrmRestInternalService           srmRestInternalService;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @PostConstruct
    public void afterPropertiesSet() throws Exception {

        // Quartz Properties
        Properties quartzProps = new Properties();
        quartzProps.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, "StatisticalResourcesScheduler");
        quartzProps.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID, "statisticalResourcesScheduler001");
        quartzProps.put(StdSchedulerFactory.PROP_SCHED_SKIP_UPDATE_CHECK, "true");
        quartzProps.put(StdSchedulerFactory.PROP_JOB_STORE_CLASS, "org.quartz.simpl.RAMJobStore");
        quartzProps.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
        quartzProps.put("org.quartz.threadPool.threadCount", "10");
        quartzProps.put("org.quartz.threadPool.threadPriority", "5");

        SchedulerFactory sf = new StdSchedulerFactory(quartzProps);
        Scheduler sched = sf.getScheduler();

        // Start now
        sched.start();

    }

    @Override
    public synchronized String plannifyImportationDataset(ServiceContext ctx, InputStream inputMessage) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkPlannifyImportationDataset(ctx, inputMessage);

        String jobKey = "job_importdata_" + java.util.UUID.randomUUID().toString();

        OutputStream os = null;
        File file = null;
        try {
            // TODO Mark importation in progress

            // Scheduler an importation job
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

            // Validation: There shouldn't be an import processing..
            if (sched.getCurrentlyExecutingJobs().size() != 0) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR_MAX_CURRENT_JOBS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }

            // Save InputStream (TempFile)
            file = File.createTempFile("stat_resources_", ".import");
            file.deleteOnExit();
            os = new FileOutputStream(file);
            IOUtils.copy(inputMessage, os);

            // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
            JobDetail job = newJob(ImportDatasetJob.class).withIdentity(jobKey, "importation").usingJobData(ImportDatasetJob.FILE_PATH, file.getAbsolutePath())
                    .usingJobData(ImportDatasetJob.USER, ctx.getUserId()).requestRecovery().build();

            SimpleTrigger trigger = newTrigger().withIdentity("trigger_" + jobKey, "importation").startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();

            sched.scheduleJob(job, trigger);

        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(e.getMessage()).withCause(e).withLoggedLevel(ExceptionLevelEnum.ERROR)
                    .build(); // Error
        } finally {
            IOUtils.closeQuietly(inputMessage);
            IOUtils.closeQuietly(os);
        }

        return jobKey;
    }

    @Override
    public void processImportationTask(ServiceContext ctx, InputStream inputMessage, String jobKey) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkProcessImportationTask(ctx, inputMessage, jobKey);

        processDatasetSDMX_21(inputMessage);

    }

    private void processDatasetSDMX_21(InputStream inputMessage) throws MetamacException {
        DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn("urn:todo"); // TODO a la espera de que alberto decida como vamos a asociar el dsd.

        ManipulateSdmx21DataCallbackImpl callback = new ManipulateSdmx21DataCallbackImpl(dataStructure, metamac2StatRepoMapper, datasetRepositoriesServiceFacade);

        try {
            Sdmx21Parser.parseData(inputMessage, callback);
        } catch (Exception e) {
            // TODO Lanzar excepcion de metmac importando sdmx y gestión-recuperación de errores.
            e.printStackTrace();
        }
    }

}
