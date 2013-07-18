package org.siemac.metamac.statistical.resources.core.task.serviceimpl;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;
import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.Metamac2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.ImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.ManipulateSdmx21DataCallbackImpl;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
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
    public synchronized String plannifyImportationDataset(ServiceContext ctx, TaskInfoDataset taskInfoDataset) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkPlannifyImportationDataset(ctx, taskInfoDataset);

        // job keys
        JobKey jobKey = createJobKeyForDatasetImportation(taskInfoDataset.getRepoDatasetId());
        TriggerKey triggerKey = new TriggerKey("trigger_" + taskInfoDataset.getRepoDatasetId(), "importation");

        OutputStream os = null;
        try {
            // TODO Mark importation in progress

            // Scheduler an importation job
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

            // Validation: There shouldn't be an import processing on this dataset
            if (sched.checkExists(jobKey)) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR_MAX_CURRENT_JOBS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }

            // Save InputStream (TempFile)
            StringBuilder filePaths = new StringBuilder();
            StringBuilder fileNames = new StringBuilder();
            for (FileDescriptor fileDescriptorDto : taskInfoDataset.getFiles()) {
                File file = File.createTempFile("data_", "_" + fileDescriptorDto.getDatasetFileFormatEnum() + ".imp");
                file.deleteOnExit();
                os = new FileOutputStream(file);
                IOUtils.copy(fileDescriptorDto.getInputMessage(), os);

                if (filePaths.length() > 0) {
                    filePaths.append(ImportDatasetJob.SERIALIZATION_SEPARATOR);
                }
                filePaths.append(file.getAbsolutePath());

                if (fileNames.length() > 0) {
                    fileNames.append(ImportDatasetJob.SERIALIZATION_SEPARATOR);
                }
                fileNames.append(fileDescriptorDto.getFileName());
            }

            // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
            JobDetail job = newJob(ImportDatasetJob.class).withIdentity(jobKey).usingJobData(ImportDatasetJob.FILE_PATHS, filePaths.toString())
                    .usingJobData(ImportDatasetJob.FILE_NAMES, fileNames.toString()).usingJobData(ImportDatasetJob.DATA_STRUCTURE_URN, taskInfoDataset.getDataStructureUrn())
                    .usingJobData(ImportDatasetJob.REPO_DATASET_ID, taskInfoDataset.getRepoDatasetId()).usingJobData(ImportDatasetJob.USER, ctx.getUserId()).requestRecovery().build();

            SimpleTrigger trigger = newTrigger().withIdentity(triggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();

            sched.scheduleJob(job, trigger);

        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(e.getMessage()).withCause(e).withLoggedLevel(ExceptionLevelEnum.ERROR)
                    .build(); // Error
        } finally {
            for (FileDescriptor fileDescriptorDto : taskInfoDataset.getFiles()) {
                IOUtils.closeQuietly(fileDescriptorDto.getInputMessage());
            }
            IOUtils.closeQuietly(os);
        }

        taskInfoDataset.setJobKey(jobKey.getName());
        return jobKey.getName();
    }

    @Override
    public void processImportationTask(ServiceContext ctx, TaskInfoDataset taskInfoDataset) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkProcessImportationTask(ctx, taskInfoDataset);

        processDatasetSDMX_21(taskInfoDataset);
    }

    @Override
    public boolean existTaskInDataset(ServiceContext ctx, String datasetId) throws MetamacException {
        try {
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler
            return sched.checkExists(createJobKeyForDatasetImportation(datasetId));
        } catch (SchedulerException e) {
            throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_SCHEDULER_ERROR).withMessageParameters(e.getMessage()).build();
        }
    }

    /****************************************************************
     * PRIVATES
     ****************************************************************/

    private JobKey createJobKeyForDatasetImportation(String datasetId) {
        return new JobKey("job_importdata_" + datasetId, "importation");
    }

    private void processDatasetSDMX_21(TaskInfoDataset taskInfoDataset) throws MetamacException {
        DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn(taskInfoDataset.getDataStructureUrn());

        ManipulateSdmx21DataCallbackImpl callback = new ManipulateSdmx21DataCallbackImpl(dataStructure, srmRestInternalService, metamac2StatRepoMapper, datasetRepositoriesServiceFacade,
                taskInfoDataset.getRepoDatasetId());

        try {
            for (FileDescriptor fileDescriptor : taskInfoDataset.getFiles()) {
                if (DatasetFileFormatEnum.SDMX_2_1.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                    callback.setDataSourceID(generateDataSourceId(fileDescriptor.getFileName()));
                    Sdmx21Parser.parseData(fileDescriptor.getInputMessage(), callback);
                } else if (DatasetFileFormatEnum.PX.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                    throw new UnsupportedOperationException("Import PX");
                } else if (DatasetFileFormatEnum.CSV.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                    throw new UnsupportedOperationException("Import CSV");
                }
            }
        } catch (Exception e) {
            // TODO Lanzar excepcion de metmac importando sdmx y gestión-recuperación de errores.
            e.printStackTrace();
        }
    }

    private String generateDataSourceId(String fileName) {
        return fileName + "_" + new DateTime().toString();
    }

}
