package org.siemac.metamac.statistical.resources.core.task.serviceimpl;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ExceptionHelper;
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
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.ImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.ManipulateCsvDataService;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.ManipulatePxDataService;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.ManipulateSdmx21DataCallbackImpl;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.RecoveryImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators.ValidateDataVersusDsd;
import org.siemac.metamac.statistical.resources.core.dataset.utils.ManipulateDataUtils;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.TaskStatusTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.Task;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskProperties;
import org.siemac.metamac.statistical.resources.core.task.exception.TaskNotFoundException;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.validators.TaskServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.task.utils.JobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.statistic.dataset.repository.dto.AttributeBasicDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.Sdmx21Parser;

/**
 * Implementation of TaskService.
 */
@Service("taskService")
public class TaskServiceImpl extends TaskServiceImplBase {

    private static Logger                    logger                              = LoggerFactory.getLogger(TaskServiceImpl.class);

    public static final String               SCHEDULER_INSTANCE_NAME             = "StatisticalResourcesScheduler";
    public static final String               PREFIX_JOB_IMPORT_DATA              = "job_importdata_";
    public static final String               PREFIX_JOB_RECOVERY_IMPORT_DATA     = "job_recoveryimportdata_";
    public static final String               PREFIX_TRIGGER_IMPORT_DATA          = "trigger_importdata_";
    public static final String               PREFIX_TRIGGER_RECOVERY_IMPORT_DATA = "trigger_recoveryimportdata_";
    public static final String               GROUP_IMPORTATION                   = "importation";

    @Autowired
    private TaskServiceInvocationValidator   taskServiceInvocationValidator;

    @Autowired
    private MetamacSdmx2StatRepoMapper       metamac2StatRepoMapper;

    @Autowired
    private SrmRestInternalService           srmRestInternalService;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @Autowired
    private ManipulatePxDataService          manipulatePxDataService;

    @Autowired
    private ManipulateCsvDataService         manipulateCsvDataService;

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
    public synchronized String planifyImportationDataset(ServiceContext ctx, TaskInfoDataset taskInfoDataset) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkPlanifyImportationDataset(ctx, taskInfoDataset);

        // job keys
        JobKey jobKey = createJobKeyForImportationDataset(taskInfoDataset.getRepoDatasetId());
        TriggerKey triggerKey = createTriggerKeyForImportationDataset(taskInfoDataset.getRepoDatasetId());

        Task task = null;
        try {
            // Save InputStream (TempFile)
            StringBuilder filePaths = new StringBuilder();
            StringBuilder fileNames = new StringBuilder();
            StringBuilder fileFormats = new StringBuilder();
            serializeFilePathsAndNames(taskInfoDataset, filePaths, fileNames, fileFormats);

            // Scheduler an importation job
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

            // Validation: There shouldn't be an import processing on this dataset
            if (sched.checkExists(jobKey)) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR_MAX_CURRENT_JOBS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }

            // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
            JobDetail job = newJob(ImportDatasetJob.class).withIdentity(jobKey).usingJobData(ImportDatasetJob.FILE_PATHS, filePaths.toString())
                    .usingJobData(ImportDatasetJob.FILE_FORMATS, fileFormats.toString()).usingJobData(ImportDatasetJob.FILE_NAMES, fileNames.toString())
                    .usingJobData(ImportDatasetJob.DATA_STRUCTURE_URN, taskInfoDataset.getDataStructureUrn()).usingJobData(ImportDatasetJob.REPO_DATASET_ID, taskInfoDataset.getRepoDatasetId())
                    .usingJobData(ImportDatasetJob.USER, ctx.getUserId()).requestRecovery().build();

            // Mark importation in progress
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Task.class).withProperty(TaskProperties.job()).eq(jobKey.getName()).distinctRoot().build();
            PagedResult<Task> tasks = findTasksByCondition(ctx, conditions, PagingParameter.pageAccess(1, 1));
            if (!tasks.getValues().isEmpty()) {
                task = tasks.getValues().get(0);
            }

            if (task == null) {
                // No existing Job
                task = new Task(jobKey.getName());
                task.setStatus(TaskStatusTypeEnum.IN_PROGRESS);
                task.setExtensionPoint(taskInfoDataset.getRepoDatasetId() + JobUtil.SERIALIZATION_SEPARATOR + fileNames.toString()); // DatasetId | filename0 | ... @| filenameN
                createTask(ctx, task);
                SimpleTrigger trigger = newTrigger().withIdentity(triggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();
                sched.scheduleJob(job, trigger);

            } else if (TaskStatusTypeEnum.FAILED.equals(task.getStatus())) {
                if (!sched.checkExists(createJobKeyForRecoveryImportationDataset(taskInfoDataset.getRepoDatasetId()))) {
                    TaskInfoDataset recoveryTaskInfo = new TaskInfoDataset();
                    recoveryTaskInfo.setRepoDatasetId(taskInfoDataset.getRepoDatasetId());
                    planifyRecoveryImportDataset(ctx, recoveryTaskInfo); // Perform a clean recovery
                }

                task.setCreatedDate(new DateTime());
                task.setStatus(TaskStatusTypeEnum.IN_PROGRESS);
                task.setExtensionPoint(taskInfoDataset.getRepoDatasetId() + JobUtil.SERIALIZATION_SEPARATOR + fileNames.toString()); // DatasetId | filename0 | ... @| filenameN
                updateTask(ctx, task);

                sched.addJob(job, false);
            }

        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(e.getMessage()).withCause(e).withLoggedLevel(ExceptionLevelEnum.ERROR)
                    .build(); // Error
        }

        return jobKey.getName();
    }

    @Override
    public void processImportationTask(ServiceContext ctx, String importationJobKey, TaskInfoDataset taskInfoDataset) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkProcessImportationTask(ctx, importationJobKey, taskInfoDataset);

        Task task = retrieveTaskByJob(ctx, importationJobKey);

        try {
            processDatasets(ctx, taskInfoDataset, task.getCreatedDate());
        } catch (Exception e) {
            // Convert parser exception to metamac exception
            MetamacException throwableMetamacException = null;
            if (e instanceof MetamacException) {
                throwableMetamacException = (MetamacException) e;
            } else {
                throwableMetamacException = MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(ExceptionHelper.excMessage(e))
                        .build();
            }
            throw throwableMetamacException;
        }

        markTaskAsFinished(ctx, importationJobKey); // Finish the importation
    }

    @Override
    public boolean existImportationTaskInDataset(ServiceContext ctx, String datasetId) throws MetamacException {
        try {
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler
            return sched.checkExists(createJobKeyForImportationDataset(datasetId));
        } catch (SchedulerException e) {
            throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_SCHEDULER_ERROR).withMessageParameters(e.getMessage()).build();
        }
    }

    @Override
    public Task createTask(ServiceContext ctx, Task task) throws MetamacException {
        // Save version
        task = getTaskRepository().save(task);
        return task;
    }

    @Override
    public Task updateTask(ServiceContext ctx, Task task) throws MetamacException {
        // Save version
        task = getTaskRepository().save(task);
        return task;
    }

    @Override
    public Task retrieveTaskByJob(ServiceContext ctx, String job) throws MetamacException {
        try {
            Task task = getTaskRepository().findByKey(job);
            return task;
        } catch (TaskNotFoundException e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_NOT_FOUND).withMessageParameters(job).build();
        }
    }

    @Override
    public void markTaskAsFinished(ServiceContext ctx, String job) throws MetamacException {
        // Delete complete task
        Task task = retrieveTaskByJob(ctx, job);
        getTaskRepository().delete(task);

        // TODO envio al gestor de avisos que la importación fue correcta
    }

    @Override
    public void markTaskAsFailed(ServiceContext ctx, String jobKey, Exception exception) throws MetamacException {
        // Update
        Task task = retrieveTaskByJob(ctx, jobKey);
        task.setStatus(TaskStatusTypeEnum.FAILED);
        updateTask(ctx, task);

        // Plannify a recovery job
        if (jobKey.startsWith(PREFIX_JOB_IMPORT_DATA)) {
            TaskInfoDataset recoveryTaskInfo = new TaskInfoDataset();
            recoveryTaskInfo.setRepoDatasetId(extractDatasetIdFormJobKeyImportationDataset(jobKey));
            planifyRecoveryImportDataset(ctx, recoveryTaskInfo);
        }
        // TODO envio al gestor de avisos de fallo de importación
    }

    @Override
    public PagedResult<Task> findTasksByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Find
        if (conditions == null) {
            conditions = ConditionalCriteriaBuilder.criteriaFor(Task.class).distinctRoot().build();
        }
        PagedResult<Task> pagedResult = getTaskRepository().findByCondition(conditions, pagingParameter);
        return pagedResult;
    }

    @Override
    public synchronized String planifyRecoveryImportDataset(ServiceContext ctx, TaskInfoDataset taskInfoDataset) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkPlanifyRecoveryImportDataset(ctx, taskInfoDataset);

        // Job keys
        JobKey recoveryImportJobKey = createJobKeyForRecoveryImportationDataset(taskInfoDataset.getRepoDatasetId());
        TriggerKey recoveryImportTriggerKey = createTriggerKeyForRecoveryImportationDataset(taskInfoDataset.getRepoDatasetId());

        // Scheduler an importation job
        Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

        // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
        JobDetail recoveryImportJob = newJob(RecoveryImportDatasetJob.class).withIdentity(recoveryImportJobKey)
                .usingJobData(RecoveryImportDatasetJob.REPO_DATASET_ID, taskInfoDataset.getRepoDatasetId()).usingJobData(RecoveryImportDatasetJob.USER, ctx.getUserId()).requestRecovery().build();

        SimpleTrigger recoveryImportTrigger = newTrigger().withIdentity(recoveryImportTriggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();

        try {
            sched.scheduleJob(recoveryImportJob, recoveryImportTrigger);
        } catch (SchedulerException e) {
            logger.error("PlannifyRecoveryImportDataset: the recovery importation with key " + recoveryImportJobKey.getName() + " has failed", e);
        }

        return recoveryImportJobKey.getName();
    }

    @Override
    public void processRollbackImportationTask(ServiceContext ctx, String recoveryJobKey, TaskInfoDataset taskInfoDataset) {

        try {
            Task task = retrieveTaskByJob(ctx, createJobKeyForImportationDataset(taskInfoDataset.getRepoDatasetId()).getName());

            String fileNames = task.getExtensionPoint();
            String[] names = fileNames.split("\\" + JobUtil.SERIALIZATION_SEPARATOR);
            for (int i = 1; i < names.length; i++) {
                String dataSourceId = generateDataSourceId(names[i], task.getCreatedDate());

                InternationalStringDto internationalStringDto = new InternationalStringDto();
                LocalisedStringDto localisedStringDto = new LocalisedStringDto();
                localisedStringDto.setLabel(dataSourceId);
                localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
                internationalStringDto.addText(localisedStringDto);

                AttributeBasicDto attributeBasicDto = new AttributeBasicDto(ManipulateDataUtils.DATA_SOURCE_ID, internationalStringDto);

                datasetRepositoriesServiceFacade.deleteObservationsByAttributeValue(names[0], 0, attributeBasicDto);
            }

            // Delete failed entry
            getTaskRepository().delete(task);

            // Any
            executeDormantJobsInThisDataset(taskInfoDataset.getRepoDatasetId());
        } catch (Exception e) {
            logger.error("Error while perform a recovery in dataset", e);
        }

    }

    private void executeDormantJobsInThisDataset(String datasetId) {
        // Scheduler an importation job
        Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

        try {
            JobKey importationJobKey = createJobKeyForImportationDataset(datasetId);
            if (sched.checkExists(importationJobKey)) {
                TriggerKey triggerImportationKey = createTriggerKeyForImportationDataset(datasetId);
                SimpleTrigger trigger = newTrigger().withIdentity(triggerImportationKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();
                sched.scheduleJob(sched.getJobDetail(importationJobKey), trigger);
            }
        } catch (SchedulerException e) {
            logger.error("Error while perform a scheduling of dormant jobs", e);
        }
    }

    /****************************************************************
     * PRIVATES
     ****************************************************************/

    private JobKey createJobKeyForImportationDataset(String datasetId) {
        return new JobKey(PREFIX_JOB_IMPORT_DATA + datasetId, GROUP_IMPORTATION);
    }

    private JobKey createJobKeyForRecoveryImportationDataset(String datasetId) {
        return new JobKey(PREFIX_JOB_RECOVERY_IMPORT_DATA + datasetId, GROUP_IMPORTATION);
    }

    private TriggerKey createTriggerKeyForImportationDataset(String datasetId) {
        return new TriggerKey(PREFIX_JOB_IMPORT_DATA + datasetId, GROUP_IMPORTATION);
    }

    private TriggerKey createTriggerKeyForRecoveryImportationDataset(String datasetId) {
        return new TriggerKey(PREFIX_JOB_RECOVERY_IMPORT_DATA + datasetId, GROUP_IMPORTATION);
    }

    private String extractDatasetIdFormJobKeyImportationDataset(String jobImportKeyName) {
        if (StringUtils.isEmpty(jobImportKeyName)) {
            return null;
        }
        return StringUtils.substringAfter(jobImportKeyName, PREFIX_JOB_IMPORT_DATA);
    }

    protected void serializeFilePathsAndNames(TaskInfoDataset taskInfoDataset, StringBuilder filePaths, StringBuilder fileNames, StringBuilder fileFormats) throws IOException, FileNotFoundException {
        for (FileDescriptor fileDescriptorDto : taskInfoDataset.getFiles()) {
            if (filePaths.length() > 0) {
                filePaths.append(JobUtil.SERIALIZATION_SEPARATOR);
            }
            filePaths.append(fileDescriptorDto.getFile().getAbsolutePath());

            if (fileNames.length() > 0) {
                fileNames.append(JobUtil.SERIALIZATION_SEPARATOR);
            }
            fileNames.append(fileDescriptorDto.getFileName());

            if (fileFormats.length() > 0) {
                fileFormats.append(JobUtil.SERIALIZATION_SEPARATOR);
            }
            fileFormats.append(fileDescriptorDto.getDatasetFileFormatEnum());
        }
    }

    private void processDatasets(ServiceContext ctx, TaskInfoDataset taskInfoDataset, DateTime dateTime) throws Exception {
        DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn(taskInfoDataset.getDataStructureUrn());

        // Validator
        ValidateDataVersusDsd validateDataVersusDsd = new ValidateDataVersusDsd(dataStructure, srmRestInternalService);

        // Callbacks
        ManipulateSdmx21DataCallbackImpl callback = null;

        List<String> linkedList = new ArrayList<String>(taskInfoDataset.getFiles().size());

        for (FileDescriptor fileDescriptor : taskInfoDataset.getFiles()) {
            String dataSourceId = generateDataSourceId(fileDescriptor.getFileName(), dateTime);
            if (DatasetFileFormatEnum.SDMX_2_1.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                if (callback == null) {
                    // Create the callback in the first appearance of a SDMX dataset
                    callback = new ManipulateSdmx21DataCallbackImpl(dataStructure, srmRestInternalService, metamac2StatRepoMapper, datasetRepositoriesServiceFacade,
                            taskInfoDataset.getRepoDatasetId(), validateDataVersusDsd);
                }

                callback.setDataSourceID(dataSourceId);
                Sdmx21Parser.parseData(new FileInputStream(fileDescriptor.getFile()), callback); // Parse and import

            } else if (DatasetFileFormatEnum.PX.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                manipulatePxDataService.importPx(ctx, fileDescriptor.getFile(), dataStructure, taskInfoDataset.getRepoDatasetId(), dataSourceId, validateDataVersusDsd);

            } else if (DatasetFileFormatEnum.CSV.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                manipulateCsvDataService.importCsv(ctx, fileDescriptor.getFile(), dataStructure, taskInfoDataset.getRepoDatasetId(), dataSourceId, validateDataVersusDsd);

            }

            linkedList.add(dataSourceId);
        }

        // TODO llamar a un callback (o un metodo de rober) para que haga algo con la lista de datasources que se han importado
    }

    private String generateDataSourceId(String fileName, DateTime dateTime) {
        return fileName + "_" + dateTime.toString();
    }

}
