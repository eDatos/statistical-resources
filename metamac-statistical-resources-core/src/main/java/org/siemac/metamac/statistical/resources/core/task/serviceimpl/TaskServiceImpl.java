package org.siemac.metamac.statistical.resources.core.task.serviceimpl;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ExceptionHelper;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;
import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraint;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.constraint.api.ConstraintsService;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.TaskStatusTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.NoticesRestInternalService;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.AbstractImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.CustomImportDatasetJobForDbImport;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.DbImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.DuplicationDatasetJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.ImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.ManipulateCsvDataService;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.ManipulatePxDataService;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.ManipulateSdmx21DataCallbackImpl;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.RecoveryImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators.ValidateDataVersusDsd;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeMessage;
import org.siemac.metamac.statistical.resources.core.task.domain.AlternativeEnumeratedRepresentation;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptorResult;
import org.siemac.metamac.statistical.resources.core.task.domain.Task;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskProperties;
import org.siemac.metamac.statistical.resources.core.task.exception.TaskNotFoundException;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.validators.TaskServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.task.utils.JobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.arte.statistic.parser.px.domain.PxModel;
import com.arte.statistic.parser.sdmx.v2_1.Sdmx21Parser;

import es.gobcan.istac.edatos.dataset.repository.domain.DatasetRepositoryExceptionCodeEnum;
import es.gobcan.istac.edatos.dataset.repository.dto.DatasetRepositoryDto;
import es.gobcan.istac.edatos.dataset.repository.dto.InternationalStringDto;
import es.gobcan.istac.edatos.dataset.repository.dto.LocalisedStringDto;
import es.gobcan.istac.edatos.dataset.repository.dto.Mapping;
import es.gobcan.istac.edatos.dataset.repository.service.DatasetRepositoriesServiceFacade;

/**
 * Implementation of TaskService.
 */
@Service("taskService")
public class TaskServiceImpl extends TaskServiceImplBase implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger                     logger                              = LoggerFactory.getLogger(TaskServiceImpl.class);

    public static final String                SCHEDULER_INSTANCE_NAME             = "StatisticalResourcesScheduler";
    public static final String                PREFIX_JOB_IMPORT_DATA              = "job_importdata_";

    public static final String                PREFIX_JOB_DB_IMPORT_DATA           = "job_dbimportdata_";
    public static final String                PREFIX_JOB_RECOVERY_IMPORT_DATA     = "job_recoveryimportdata_";
    public static final String                PREFIX_JOB_DUPLICATION_DATA         = "job_duplicationdata_";
    public static final String                PREFIX_TRIGGER_IMPORT_DATA          = "trigger_importdata_";
    public static final String                PREFIX_TRIGGER_RECOVERY_IMPORT_DATA = "trigger_recoveryimportdata_";
    public static final String                GROUP_IMPORTATION                   = "importation";

    @Autowired
    private TaskServiceInvocationValidator    taskServiceInvocationValidator;

    @Autowired
    private MetamacSdmx2StatRepoMapper        metamac2StatRepoMapper;

    @Autowired
    private SrmRestInternalService            srmRestInternalService;

    @Autowired
    private DatasetRepositoriesServiceFacade  datasetRepositoriesServiceFacade;

    @Autowired
    private ManipulatePxDataService           manipulatePxDataService;

    @Autowired
    private ManipulateCsvDataService          manipulateCsvDataService;

    @Autowired
    private ConstraintsService                constraintsService;

    @Autowired
    private DatasetService                    datasetService;

    @Autowired
    private StatisticalResourcesConfiguration configurationService;

    private SchedulerFactory                  sf                                  = null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (sf != null) {
            return;
        }

        // Quartz Properties
        Properties quartzProps = new Properties();
        quartzProps.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, "StatisticalResourcesScheduler");
        quartzProps.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID, "statisticalResourcesScheduler001");
        quartzProps.put(StdSchedulerFactory.PROP_SCHED_SKIP_UPDATE_CHECK, "true");
        quartzProps.put(StdSchedulerFactory.PROP_JOB_STORE_CLASS, "org.quartz.simpl.RAMJobStore");
        quartzProps.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
        quartzProps.put("org.quartz.threadPool.threadCount", "10");
        quartzProps.put("org.quartz.threadPool.threadPriority", "5");

        try {
            sf = new StdSchedulerFactory(quartzProps);
            Scheduler sched = sf.getScheduler();

            // Start now
            sched.start();

            JobDetail job = newJob(DbImportDatasetJob.class).build();

            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(configurationService.retriveCronExpressionForDbDataImport())).build();

            sched.scheduleJob(job, cronTrigger);
        } catch (SchedulerException | MetamacException e) {
            logger.error("An unexpected error has occurred during quartz initialization", e);
            throw new RuntimeException("An unexpected error has occurred during quartz initialization", e);
        }

    }

    @Override
    public synchronized String planifyImportationDataset(ServiceContext ctx, TaskInfoDataset taskInfoDataset) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkPlanifyImportationDataset(ctx, taskInfoDataset);

        // job keys
        JobKey jobKey = createJobKey(ctx, taskInfoDataset);
        TriggerKey triggerKey = createTriggerKeyForImportationDataset(taskInfoDataset.getDatasetVersionId());

        Task task = null;
        try {
            // Save InputStream (TempFile)
            StringBuilder filePaths = new StringBuilder();
            StringBuilder fileNames = new StringBuilder();
            StringBuilder fileFormats = new StringBuilder();
            StringBuilder alternativeRepresentations = new StringBuilder();
            serializeFilePathsAndNames(taskInfoDataset, filePaths, fileNames, fileFormats);
            serializeAlternativeRepresentations(taskInfoDataset, alternativeRepresentations);

            // Scheduler an importation job
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

            // Validation: There shouldn't be an import processing on this dataset
            if (sched.checkExists(jobKey)) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR_MAX_CURRENT_JOBS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }
            // Validation: There shouldn't be a recovery job in process, please wait
            if (sched.checkExists(createJobKeyForRecoveryImportationResource(taskInfoDataset.getDatasetVersionId()))) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_RECOVERY_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }

            // Validation: There shouldn't be an duplication processing on this dataset
            if (sched.checkExists(createJobKeyForDuplicationResource(taskInfoDataset.getDatasetVersionId()))) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR_MAX_CURRENT_JOBS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }

            // Checking garbage
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Task.class).withProperty(TaskProperties.job()).eq(jobKey.getName()).distinctRoot().build();
            PagedResult<Task> tasks = findTasksByCondition(ctx, conditions, PagingParameter.pageAccess(1, 1));
            if (!tasks.getValues().isEmpty()) {
                task = tasks.getValues().get(0);
            }
            if (task != null) {
                TaskInfoDataset recoveryTaskInfo = new TaskInfoDataset();
                recoveryTaskInfo.setDatasetVersionId(taskInfoDataset.getDatasetVersionId());
                // It's not necessary notify to user because this method is not for application startup recovers
                planifyRecoveryImportDataset(ctx, recoveryTaskInfo, Boolean.FALSE); // Perform a clean recovery
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_RECOVERY_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }

            JobDetail job = createJob(ctx, jobKey, filePaths, fileNames, fileFormats, alternativeRepresentations, taskInfoDataset);

            // No existing Job
            task = new Task(jobKey.getName());
            task.setStatus(TaskStatusTypeEnum.IN_PROGRESS);
            task.setExtensionPoint(taskInfoDataset.getDatasetVersionId() + JobUtil.SERIALIZATION_SEPARATOR + fileNames.toString()); // DatasetId | filename0 | ... @| filenameN
            createTask(ctx, task);
            SimpleTrigger trigger = newTrigger().withIdentity(triggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();
            sched.scheduleJob(job, trigger);

        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(e.getMessage()).withCause(e).withLoggedLevel(ExceptionLevelEnum.ERROR)
                    .build(); // Error
        }

        return jobKey.getName();
    }

    protected JobKey createJobKey(ServiceContext ctx, TaskInfoDataset taskInfoDataset) {
        return (isDbImportJob(ctx) ? createJobKeyForDbImportationResource(taskInfoDataset.getDatasetVersionId()) : createJobKeyForImportationResource(taskInfoDataset.getDatasetVersionId()));
    }

    private JobDetail createJob(ServiceContext serviceContext, JobKey jobKey, StringBuilder filePaths, StringBuilder fileNames, StringBuilder fileFormats, StringBuilder alternativeRepresentations,
            TaskInfoDataset taskInfoDataset) {
        // @formatter:off
        JobBuilder jobBuilder = 
                newJob().withIdentity(jobKey)
                    .usingJobData(AbstractImportDatasetJob.FILE_PATHS, filePaths.toString())
                    .usingJobData(AbstractImportDatasetJob.FILE_FORMATS, fileFormats.toString()).usingJobData(AbstractImportDatasetJob.FILE_NAMES, fileNames.toString())
                    .usingJobData(AbstractImportDatasetJob.ALTERNATIVE_REPRESENTATIONS, alternativeRepresentations.toString())
                    .usingJobData(AbstractImportDatasetJob.STORE_ALTERNATIVE_REPRESENTATIONS, taskInfoDataset.getStoreAlternativeRepresentations())
                    .usingJobData(AbstractImportDatasetJob.DATASET_URN, taskInfoDataset.getDatasetUrn()).usingJobData(AbstractImportDatasetJob.DATA_STRUCTURE_URN, taskInfoDataset.getDataStructureUrn())
                    .usingJobData(AbstractImportDatasetJob.DATASET_VERSION_ID, taskInfoDataset.getDatasetVersionId()).usingJobData(AbstractImportDatasetJob.USER, serviceContext.getUserId());
        // @formatter:on

        if (isDbImportJob(serviceContext)) {
            DateTime dt = (DateTime) serviceContext.getProperty(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_EXECUTION_DATE);

            // @formatter:off
            jobBuilder.ofType(CustomImportDatasetJobForDbImport.class)
                .usingJobData(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_FLAG, Boolean.TRUE)
                .usingJobData(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_EXECUTION_DATE, dt.getMillis())
                .usingJobData(AbstractImportDatasetJob.STATISTICAL_OPERATION_URN, taskInfoDataset.getStatisticalOperationUrn())
                .usingJobData(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_DATASOURCE_IDENTIFIER, (String) serviceContext.getProperty(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_DATASOURCE_IDENTIFIER));
            // @formatter:on
        } else {
            jobBuilder.ofType(ImportDatasetJob.class);
        }

        return jobBuilder.requestRecovery().build();
    }

    protected boolean isDbImportJob(ServiceContext serviceContext) {
        return Boolean.TRUE.equals(serviceContext.getProperty(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_FLAG));
    }

    @Override
    public synchronized String planifyRecoveryImportDataset(ServiceContext ctx, TaskInfoDataset taskInfoDataset, Boolean notifyToUser) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkPlanifyRecoveryImportDataset(ctx, taskInfoDataset, notifyToUser);

        // Job keys
        JobKey recoveryImportJobKey = createJobKeyForRecoveryImportationResource(taskInfoDataset.getDatasetVersionId());
        TriggerKey recoveryImportTriggerKey = createTriggerKeyForRecoveryImportationDataset(taskInfoDataset.getDatasetVersionId());

        // Scheduler an importation job
        Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

        // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
        // @formatter:off
        JobDetail recoveryImportJob = newJob(RecoveryImportDatasetJob.class)
                                        .withIdentity(recoveryImportJobKey)
                                        .usingJobData(RecoveryImportDatasetJob.DATASET_VERSION_ID, taskInfoDataset.getDatasetVersionId())
                                        .usingJobData(RecoveryImportDatasetJob.USER, ctx.getUserId())
                                        .usingJobData(RecoveryImportDatasetJob.NOTIFY_TO_USER, notifyToUser)
                                        .requestRecovery()
                                        .build();
        // @formatter:on

        SimpleTrigger recoveryImportTrigger = newTrigger().withIdentity(recoveryImportTriggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();

        try {
            sched.scheduleJob(recoveryImportJob, recoveryImportTrigger);
        } catch (SchedulerException e) {
            logger.error("PlannifyRecoveryImportDataset: the recovery importation with key " + recoveryImportJobKey.getName() + " has failed", e);
        }

        return recoveryImportJobKey.getName();
    }

    @Override
    public synchronized String planifyDuplicationDataset(ServiceContext ctx, TaskInfoDataset taskInfoDataset, String newDatasetId, List<Mapping> datasourcesMapping) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkPlanifyDuplicationDataset(ctx, taskInfoDataset, newDatasetId, datasourcesMapping);

        String datasetId = taskInfoDataset.getDatasetVersionId();

        // Job keys
        JobKey duplicationJobKey = createJobKeyForDuplicationResource(datasetId);
        TriggerKey duplicationTriggerKey = createTriggerKeyForDuplicationDataset(taskInfoDataset.getDatasetVersionId());

        try {
            // Scheduler an importation job
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

            // Validation: There shouldn't be an duplication processing on this dataset
            if (sched.checkExists(duplicationJobKey)) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR_MAX_CURRENT_JOBS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }

            // Validation: There shouldn't be a importation job in process, please wait
            if (sched.checkExists(createJobKeyForImportationResource(taskInfoDataset.getDatasetVersionId()))) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_IMPORTATION_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }

            // Validation: There shouldn't be a recovery job in process, please wait
            if (sched.checkExists(createJobKeyForRecoveryImportationResource(taskInfoDataset.getDatasetVersionId()))) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_RECOVERY_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
            }

            // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
            HashMap<String, List<Mapping>> jobDataMap = new HashMap<String, List<Mapping>>();
            jobDataMap.put(DuplicationDatasetJob.DATASOURCE_MAPPINGS, datasourcesMapping);
            // @formatter:off
            JobDetail duplicationImportJob = newJob(DuplicationDatasetJob.class).withIdentity(duplicationJobKey)
                    .usingJobData(DuplicationDatasetJob.DATASET_VERSION_ID, taskInfoDataset.getDatasetVersionId())
                    .usingJobData(DuplicationDatasetJob.USER, ctx.getUserId())
                    .usingJobData(DuplicationDatasetJob.NEW_DATASET_VERSION_ID, newDatasetId)
                    .usingJobData(new JobDataMap(jobDataMap))
                    .requestRecovery().build();
            // @formatter:on

            Task task = new Task(duplicationJobKey.getName());
            task.setStatus(TaskStatusTypeEnum.IN_PROGRESS);
            task.setExtensionPoint(newDatasetId + JobUtil.SERIALIZATION_SEPARATOR + datasetId);
            createTask(ctx, task);

            SimpleTrigger duplicationImportTrigger = newTrigger().withIdentity(duplicationTriggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();

            try {
                sched.scheduleJob(duplicationImportJob, duplicationImportTrigger);
            } catch (SchedulerException e) {
                logger.error("PlannifyRecoveryImportDataset: the recovery importation with key " + duplicationJobKey.getName() + " has failed", e);
            }
        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(e.getMessage()).withCause(e).withLoggedLevel(ExceptionLevelEnum.ERROR)
                    .build(); // Error
        }

        return duplicationJobKey.getName();
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

        if (!isDbImportJob(ctx)) {
            markTaskAsFinished(ctx, importationJobKey); // Finish the importation
        }
    }

    @Override
    public void processRollbackImportationTask(ServiceContext ctx, String recoveryJobKey, TaskInfoDataset taskInfoDataset) {
        Task task = null;
        try {
            task = retrieveTaskByJob(ctx, createJobKeyForImportationResource(taskInfoDataset.getDatasetVersionId()).getName());

            String fileNames = task.getExtensionPoint();
            String[] names = fileNames.split("\\" + JobUtil.SERIALIZATION_SEPARATOR);
            for (int i = 1; i < names.length; i++) {
                String dataSourceId = Datasource.generateDataSourceId(names[i], task.getCreatedDate());

                InternationalStringDto internationalStringDto = new InternationalStringDto();
                LocalisedStringDto localisedStringDto = new LocalisedStringDto();
                localisedStringDto.setLabel(dataSourceId);
                localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
                internationalStringDto.addText(localisedStringDto);

                datasetRepositoriesServiceFacade.deleteObservationsByAttributeInstanceValue(taskInfoDataset.getDatasetVersionId(), StatisticalResourcesConstants.ATTRIBUTE_DATA_SOURCE_ID,
                        internationalStringDto);
            }

            // Delete failed entry
            logger.info("Deleting failed task starting");
            getTaskRepository().delete(task);
            logger.info("Deleting failed task finished");
        } catch (ApplicationException e) {
            if (task != null && DatasetRepositoryExceptionCodeEnum.DATASET_NOT_EXISTS.name().equals(e.getErrorCode())) {
                getTaskRepository().delete(task);
            }
            logger.error("Error while perform a recovery in dataset", e);
        } catch (Exception e) {
            logger.error("Error while perform a recovery in dataset", e);
        }

    }

    @Override
    public void processDuplicationTask(ServiceContext ctx, String duplicationJobKey, TaskInfoDataset taskInfoDataset, String newDatasetId, List<Mapping> datasourceMappings) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkProcessDuplicationTask(ctx, duplicationJobKey, taskInfoDataset, newDatasetId, datasourceMappings);

        try {
            datasetRepositoriesServiceFacade.duplicateDatasetRepository(taskInfoDataset.getDatasetVersionId(), newDatasetId, datasourceMappings);
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

        markTaskAsFinished(ctx, duplicationJobKey); // Finish the importation
    }

    private void processRollbackDuplicationTask(ServiceContext ctx, Task task) throws MetamacException {
        markTaskAsFinished(ctx, task.getJob());
    }

    private void processRollbackDuplicationTaskOnApplicationStartup(ServiceContext ctx, Task task) throws MetamacException {
        try {
            String[] datasets = task.getExtensionPoint().split("\\" + JobUtil.SERIALIZATION_SEPARATOR);

            DatasetRepositoryDto datasetRepository = datasetRepositoriesServiceFacade.retrieveDatasetRepository(datasets[0]);

            if (datasetRepository != null) {
                // If it exists, all is correct, the duplicate successfully finished but did not notice the application. So, we have to do it with a success message
                markTaskAsFinished(ctx, task.getJob());

                String datasetVersionId = datasets[1];
                String newDatasetVersionId = datasetRepository.getDatasetId();
                getNoticesRestInternalService().createSuccessBackgroundNotification(task.getCreatedBy(), ServiceNoticeAction.DUPLICATION_DATASET_JOB, ServiceNoticeMessage.DUPLICATION_DATASET_JOB_OK,
                        datasetVersionId, newDatasetVersionId);
            } else {
                // If not, send notification about the failure. No further action is necessary because there is no waste in the repository.
                markTaskAsFinished(ctx, task.getJob()); // Clear the error task
                MetamacException metamacException = MetamacExceptionBuilder.builder().withPrincipalException(ServiceExceptionType.TASKS_ERROR_SERVER_DOWN, task.getJob()).build();
                getNoticesRestInternalService().createErrorBackgroundNotification(task.getCreatedBy(), ServiceNoticeAction.CANCEL_IN_PROGRESS_TASKS_WHILE_SERVER_SHUTDOWN, metamacException);
            }
        } catch (ApplicationException e) {
            logger.error("Unable to connect to the repository", e);
        }
    }

    @Override
    public boolean existsTaskForResource(ServiceContext ctx, String resourceId) throws MetamacException {
        // TODO METAMAC-2866 it's necessary to check for a different type of task?
        taskServiceInvocationValidator.checkExistsTaskForResource(ctx, resourceId);
        return existImportationTaskInResource(ctx, resourceId) || existRecoveryImportationTaskInResource(ctx, resourceId) || existDuplicationTaskInResource(ctx, resourceId);
    }

    @Override
    public boolean existImportationTaskInResource(ServiceContext ctx, String resourceId) throws MetamacException {
        taskServiceInvocationValidator.checkExistImportationTaskInResource(ctx, resourceId);
        try {
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler
            return sched.checkExists(createJobKeyForImportationResource(resourceId));
        } catch (SchedulerException e) {
            throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_SCHEDULER_ERROR).withMessageParameters(e.getMessage()).build();
        }
    }

    @Override
    public boolean existRecoveryImportationTaskInResource(ServiceContext ctx, String resourceId) throws MetamacException {
        taskServiceInvocationValidator.checkExistRecoveryImportationTaskInResource(ctx, resourceId);
        try {
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler
            return sched.checkExists(createJobKeyForRecoveryImportationResource(resourceId));
        } catch (SchedulerException e) {
            throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_SCHEDULER_ERROR).withMessageParameters(e.getMessage()).build();
        }
    }

    @Override
    public boolean existDuplicationTaskInResource(ServiceContext ctx, String resourceId) throws MetamacException {
        taskServiceInvocationValidator.checkExistDuplicationTaskInResource(ctx, resourceId);
        try {
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler
            return sched.checkExists(createJobKeyForDuplicationResource(resourceId));
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
    }

    @Override
    public void markTasksAsFailedOnApplicationStartup(ServiceContext ctx, String jobKey) throws MetamacException {
        Task task = retrieveTaskByJob(ctx, jobKey);
        // Plannify a recovery job
        if (jobKey.startsWith(PREFIX_JOB_IMPORT_DATA)) {
            // Update
            task.setStatus(TaskStatusTypeEnum.FAILED);
            updateTask(ctx, task);

            TaskInfoDataset recoveryTaskInfo = new TaskInfoDataset();
            recoveryTaskInfo.setDatasetVersionId(extractDatasetIdFromJobKeyImportationDataset(jobKey));
            planifyRecoveryImportDataset(ctx, recoveryTaskInfo, Boolean.TRUE);
        } else if (jobKey.startsWith(PREFIX_JOB_DUPLICATION_DATA)) {
            processRollbackDuplicationTaskOnApplicationStartup(ctx, task);
        }
    }

    @Override
    public void markTaskAsFailed(ServiceContext ctx, String jobKey) throws MetamacException {
        Task task = retrieveTaskByJob(ctx, jobKey);
        // Plannify a recovery job
        if (jobKey.startsWith(PREFIX_JOB_IMPORT_DATA)) {
            // Update
            task.setStatus(TaskStatusTypeEnum.FAILED);
            updateTask(ctx, task);

            TaskInfoDataset recoveryTaskInfo = new TaskInfoDataset();
            recoveryTaskInfo.setDatasetVersionId(extractDatasetIdFromJobKeyImportationDataset(jobKey));
            planifyRecoveryImportDataset(ctx, recoveryTaskInfo, Boolean.FALSE);
        } else if (jobKey.startsWith(PREFIX_JOB_DUPLICATION_DATA)) {
            processRollbackDuplicationTask(ctx, task);
        }
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

    /****************************************************************
     * PRIVATES
     ****************************************************************/

    private JobKey createJobKeyForImportationResource(String resourceId) {
        return new JobKey(PREFIX_JOB_IMPORT_DATA + resourceId, GROUP_IMPORTATION);
    }

    private JobKey createJobKeyForDbImportationResource(String resourceId) {
        return new JobKey(PREFIX_JOB_DB_IMPORT_DATA + resourceId, GROUP_IMPORTATION);
    }

    private JobKey createJobKeyForRecoveryImportationResource(String resourceId) {
        return new JobKey(PREFIX_JOB_RECOVERY_IMPORT_DATA + resourceId, GROUP_IMPORTATION);
    }

    private JobKey createJobKeyForDuplicationResource(String resourceId) {
        return new JobKey(PREFIX_JOB_DUPLICATION_DATA + resourceId, GROUP_IMPORTATION);
    }

    private TriggerKey createTriggerKeyForImportationDataset(String datasetId) {
        return new TriggerKey(PREFIX_JOB_IMPORT_DATA + datasetId, GROUP_IMPORTATION);
    }

    private TriggerKey createTriggerKeyForRecoveryImportationDataset(String datasetId) {
        return new TriggerKey(PREFIX_JOB_RECOVERY_IMPORT_DATA + datasetId, GROUP_IMPORTATION);
    }

    private TriggerKey createTriggerKeyForDuplicationDataset(String datasetId) {
        return new TriggerKey(PREFIX_JOB_DUPLICATION_DATA + datasetId, GROUP_IMPORTATION);
    }

    private String extractDatasetIdFromJobKeyImportationDataset(String jobKeyName) {
        if (StringUtils.isEmpty(jobKeyName)) {
            return null;
        }
        return StringUtils.substringAfter(jobKeyName, PREFIX_JOB_IMPORT_DATA);
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

    protected void serializeAlternativeRepresentations(TaskInfoDataset taskInfoDataset, StringBuilder alternativeRepresentations) throws IOException, FileNotFoundException {
        for (AlternativeEnumeratedRepresentation alternativeEnumeratedRepresentation : taskInfoDataset.getAlternativeRepresentations()) {
            if (alternativeRepresentations.length() > 0) {
                alternativeRepresentations.append(JobUtil.SERIALIZATION_SEPARATOR);
            }
            alternativeRepresentations.append(alternativeEnumeratedRepresentation.getComponentId()).append(JobUtil.SERIALIZATION_PAIR_SEPARATOR).append(alternativeEnumeratedRepresentation.getUrn());
        }
    }

    private void processDatasets(ServiceContext ctx, TaskInfoDataset taskInfoDataset, DateTime dateTime) throws Exception {
        DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn(taskInfoDataset.getDataStructureUrn());

        if (BooleanUtils.isTrue(taskInfoDataset.getStoreAlternativeRepresentations())) {
            saveAlternativeEnumeratedRepresetation(ctx, taskInfoDataset);
        }

        // Fetch and marks as final the Dataset's content constraints
        List<ContentConstraint> calculateConstraints = calculateConstraints(ctx, taskInfoDataset.getDatasetVersionId());

        // Validator
        ValidateDataVersusDsd validateDataVersusDsd = new ValidateDataVersusDsd(ctx, dataStructure, srmRestInternalService, calculateConstraints, taskInfoDataset);

        // Callbacks
        ManipulateSdmx21DataCallbackImpl callback = null;

        List<FileDescriptorResult> filesResult = new ArrayList<FileDescriptorResult>(taskInfoDataset.getFiles().size());

        for (FileDescriptor fileDescriptor : taskInfoDataset.getFiles()) {

            validateDataVersusDsd.setCurrentFilename(fileDescriptor.getFileName());

            String dataSourceId = generateDataSourceId(ctx, fileDescriptor.getFileName(), dateTime);

            Date nextUpdate = null;
            if (DatasetFileFormatEnum.SDMX_2_1.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                if (callback == null) {
                    // Create the callback in the first appearance of a SDMX dataset
                    callback = new ManipulateSdmx21DataCallbackImpl(dataStructure, srmRestInternalService, metamac2StatRepoMapper, datasetRepositoriesServiceFacade,
                            taskInfoDataset.getDatasetVersionId(), validateDataVersusDsd);
                }

                callback.setDataSourceID(dataSourceId);
                Sdmx21Parser.parseData(new FileInputStream(fileDescriptor.getFile()), callback); // Parse and import

            } else if (DatasetFileFormatEnum.PX.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                PxModel pxModel = manipulatePxDataService.importPx(ctx, fileDescriptor.getFile(), dataStructure, taskInfoDataset.getDatasetVersionId(), dataSourceId, validateDataVersusDsd);
                nextUpdate = pxModel.getNextUpdate();
            } else if (DatasetFileFormatEnum.CSV.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                manipulateCsvDataService.importCsv(ctx, fileDescriptor.getFile(), dataStructure, taskInfoDataset.getDatasetVersionId(), dataSourceId, validateDataVersusDsd);
            }

            FileDescriptorResult fileDescriptorResult = new FileDescriptorResult();
            fileDescriptorResult.setDatasetFileFormatEnum(fileDescriptor.getDatasetFileFormatEnum());
            fileDescriptorResult.setFile(fileDescriptor.getFile());
            fileDescriptorResult.setFileName(fileDescriptor.getFileName());
            fileDescriptorResult.setDatasourceId(dataSourceId);
            fileDescriptorResult.setNextUpdate(nextUpdate);
            fileDescriptorResult.setStoreDimensionRepresentationMapping(taskInfoDataset.getStoreAlternativeRepresentations());
            fileDescriptorResult.setDimensionRepresentationMapping(validateDataVersusDsd.getAlternativeSourceEnumerationRepresentationMap().get(fileDescriptor.getFileName()));

            filesResult.add(fileDescriptorResult);

            validateDataVersusDsd.setCurrentFilename(null);
        }

        // Callback
        getDatasetService().proccessDatasetFileImportationResult(ctx, taskInfoDataset.getDatasetVersionId(), filesResult);
    }

    private String generateDataSourceId(ServiceContext serviceContext, String fileName, DateTime dateTime) {
        String generateDataSourceId;

        if (isDbImportJob(serviceContext)) {
            generateDataSourceId = (String) serviceContext.getProperty(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_DATASOURCE_IDENTIFIER);
        } else {
            generateDataSourceId = Datasource.generateDataSourceId(fileName, dateTime);
        }

        return generateDataSourceId;
    }

    private NoticesRestInternalService getNoticesRestInternalService() {
        return (NoticesRestInternalService) ApplicationContextProvider.getApplicationContext().getBean(NoticesRestInternalService.BEAN_ID);
    }

    /**
     * Fetch Dataset's content constraints
     *
     * @param ctx
     * @param datasetVersionUrn
     * @return
     * @throws MetamacException
     */
    private List<ContentConstraint> calculateConstraints(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        List<ResourceInternal> contentConstraintsForArtefact = constraintsService.findContentConstraintsForArtefact(ctx, datasetVersionUrn);

        List<ContentConstraint> result = new LinkedList<ContentConstraint>();
        for (ResourceInternal resourceInternal : contentConstraintsForArtefact) {
            // This means, you can come Draft, but in our business, the validation against constraints involves that are at least as marked as final
            ContentConstraint contentConstraint = constraintsService.retrieveContentConstraintByUrn(ctx, resourceInternal.getUrn(), Boolean.TRUE);
            result.add(contentConstraint);

            if (!contentConstraint.isIsFinal()) {
                constraintsService.publishContentConstraint(ctx, datasetVersionUrn, Boolean.FALSE); // mark as final logic, no mark as public
            }
        }

        return result;
    }

    private void saveAlternativeEnumeratedRepresetation(ServiceContext ctx, TaskInfoDataset taskInfoDataset) throws MetamacException {
        DatasetVersion datasetVersion = datasetService.retrieveDatasetVersionByUrn(ctx, taskInfoDataset.getDatasetVersionId());
        for (FileDescriptor fileDescriptor : taskInfoDataset.getFiles()) {
            Map<String, String> mappings = alternativeEnumeratedRepresentationToMap(taskInfoDataset.getAlternativeRepresentations());
            datasetService.saveDimensionRepresentationMapping(ctx, datasetVersion.getDataset(), fileDescriptor.getFileName(), mappings);
        }
    }

    private Map<String, String> alternativeEnumeratedRepresentationToMap(List<AlternativeEnumeratedRepresentation> alternativeEnumeratedRepresentations) {
        Map<String, String> mappings = new LinkedHashMap<String, String>();
        for (AlternativeEnumeratedRepresentation representation : alternativeEnumeratedRepresentations) {
            mappings.put(representation.getComponentId(), representation.getUrn());
        }
        return mappings;
    }

    @Override
    public boolean existDbImportationTaskInResource(ServiceContext ctx, String resourceId) throws MetamacException {
        taskServiceInvocationValidator.checkExistDbImportationTaskInResource(ctx, resourceId);
        try {
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler
            return sched.checkExists(createJobKeyForDbImportationResource(resourceId));
        } catch (SchedulerException e) {
            throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_SCHEDULER_ERROR).withMessageParameters(e.getMessage()).build();
        }
    }

}
