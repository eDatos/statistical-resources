package org.siemac.metamac.statistical.resources.core.task.serviceimpl;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.siemac.metamac.statistical.resources.core.task.utils.JobUtil.createJobNameForDatabaseImportationResource;
import static org.siemac.metamac.statistical.resources.core.task.utils.JobUtil.createJobNameForDuplicationResource;
import static org.siemac.metamac.statistical.resources.core.task.utils.JobUtil.createJobNameForImportationResource;
import static org.siemac.metamac.statistical.resources.core.task.utils.JobUtil.createJobNameForRecoveryImportationResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
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
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraint;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.constraint.api.ConstraintsService;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.repository.api.DatabaseImportRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.dataset.domain.DataSourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.TaskStatusTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.NoticesRestInternalService;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.AbstractImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.DatabaseDatasetPollingJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.DuplicationDatasetJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.ImportDatasetFromDatabaseJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.ImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.ManipulateCsvDataService;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.ManipulatePxDataService;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.ManipulateSdmx21DataCallbackImpl;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.RecoveryImportDatasetJob;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators.ValidateDataVersusDsd;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
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
import org.siemac.metamac.statistical.resources.core.utils.DatabaseDatasetImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.arte.statistic.parser.csv.CsvWriter;
import com.arte.statistic.parser.csv.constants.CsvConstants;
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
    public static final String                PREFIX_JOB_DATABASE_IMPORT_DATA     = "job_databaseimportdata_";
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
    private LifecycleService<DatasetVersion>  datasetLifecycleService;

    @Autowired
    private StatisticalResourcesConfiguration configurationService;

    @Autowired
    @Qualifier("txManager")
    private PlatformTransactionManager        platformTransactionManager;

    @Autowired
    private DatasetVersionRepository          datasetVersionRepository;

    @Autowired
    private DatabaseImportRepository          databaseImportRepository;

    private SchedulerFactory                  schedulerFactory                    = null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (schedulerFactory != null) {
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
            schedulerFactory = new StdSchedulerFactory(quartzProps);
            Scheduler sched = schedulerFactory.getScheduler();

            // Start now
            sched.start();

            JobDetail job = newJob(DatabaseDatasetPollingJob.class).build();

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule(configurationService.retriveCronExpressionForDbDataImport()).withMisfireHandlingInstructionDoNothing()).build();

            sched.scheduleJob(job, cronTrigger);
        } catch (SchedulerException | MetamacException e) {
            throw new IllegalStateException("An unexpected error has occurred during quartz initialization", e);
        }

    }

    @Override
    public synchronized String planifyImportationDataset(ServiceContext ctx, TaskInfoDataset taskInfoDataset) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkPlanifyImportationDataset(ctx, taskInfoDataset);

        String datasetUrn = taskInfoDataset.getDatasetUrn();

        JobKey jobKey = createJobKey(ctx, datasetUrn);
        TriggerKey triggerKey = createTriggerKey(ctx, datasetUrn);
        String taskName = createTaskName(ctx, taskInfoDataset.getDatasetVersionId());

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

            checkExistTaskInResource(ctx, sched, jobKey, datasetUrn);

            // Checking garbage
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Task.class).withProperty(TaskProperties.job()).eq(taskName).distinctRoot().build();
            PagedResult<Task> tasks = findTasksByCondition(ctx, conditions, PagingParameter.pageAccess(1, 1));
            if (!tasks.getValues().isEmpty()) {
                task = tasks.getValues().get(0);
            }
            if (task != null) {
                if (createJobKeyForImportationResource(datasetUrn).equals(jobKey)) {
                    TaskInfoDataset recoveryTaskInfo = new TaskInfoDataset();
                    recoveryTaskInfo.setDatasetVersionId(taskInfoDataset.getDatasetVersionId());
                    recoveryTaskInfo.setDatasetUrn(datasetUrn);
                    // It's not necessary notify to user because this method is not for application startup recovers
                    planifyRecoveryImportDataset(ctx, recoveryTaskInfo, Boolean.FALSE); // Perform a clean recovery
                    throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_RECOVERY_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build(); // Error
                } else if (createJobKeyForDatabaseImportationResource(datasetUrn).equals(jobKey)) {
                    // TODO METAMAC-2866 Database import recovery not defined yet, task is deleted in order to not generated side effects but this behavior is temporary until this TODO will be
                    // resolved
                    markTaskAsFinished(ctx, task.getJob());
                    throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_DATABASE_IMPORTATION_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build();
                }
            }

            JobDetail job = createJob(ctx, jobKey, taskName, filePaths, fileNames, fileFormats, alternativeRepresentations, taskInfoDataset);

            // No existing Job
            Task newTask = new Task(taskName);
            newTask.setStatus(TaskStatusTypeEnum.IN_PROGRESS);
            newTask.setExtensionPoint(taskInfoDataset.getDatasetVersionId() + JobUtil.SERIALIZATION_SEPARATOR + fileNames.toString()); // DatasetId | filename0 | ... @| filenameN
            createTask(ctx, newTask);
            SimpleTrigger trigger = newTrigger().withIdentity(triggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();
            sched.scheduleJob(job, trigger);

        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(e.getMessage()).withCause(e).withLoggedLevel(ExceptionLevelEnum.ERROR)
                    .build(); // Error
        }

        return jobKey.getName();
    }

    private String createTaskName(ServiceContext ctx, String datasetVersionId) {
        return (DatabaseDatasetImportUtils.isDatabaseDatasetImportJob(ctx) ? createJobNameForDatabaseImportationResource(datasetVersionId) : createJobNameForImportationResource(datasetVersionId));
    }

    protected JobKey createJobKey(ServiceContext ctx, String datasetUrn) {
        return (DatabaseDatasetImportUtils.isDatabaseDatasetImportJob(ctx) ? createJobKeyForDatabaseImportationResource(datasetUrn) : createJobKeyForImportationResource(datasetUrn));
    }

    protected TriggerKey createTriggerKey(ServiceContext ctx, String datasetUrn) {
        return (DatabaseDatasetImportUtils.isDatabaseDatasetImportJob(ctx) ? createTriggerKeyForDbImportationDataset(datasetUrn) : createTriggerKeyForImportationDataset(datasetUrn));
    }

    private JobDetail createJob(ServiceContext serviceContext, JobKey jobKey, String taskName, StringBuilder filePaths, StringBuilder fileNames, StringBuilder fileFormats,
            StringBuilder alternativeRepresentations, TaskInfoDataset taskInfoDataset) {
        // @formatter:off
        JobBuilder jobBuilder = 
                newJob().withIdentity(jobKey)
                    .usingJobData(AbstractImportDatasetJob.FILE_PATHS, filePaths.toString())
                    .usingJobData(AbstractImportDatasetJob.FILE_FORMATS, fileFormats.toString())
                    .usingJobData(AbstractImportDatasetJob.FILE_NAMES, fileNames.toString())
                    .usingJobData(AbstractImportDatasetJob.ALTERNATIVE_REPRESENTATIONS, alternativeRepresentations.toString())
                    .usingJobData(AbstractImportDatasetJob.STORE_ALTERNATIVE_REPRESENTATIONS, taskInfoDataset.getStoreAlternativeRepresentations())
                    .usingJobData(AbstractImportDatasetJob.DATASET_URN, taskInfoDataset.getDatasetUrn())
                    .usingJobData(AbstractImportDatasetJob.DATA_STRUCTURE_URN, taskInfoDataset.getDataStructureUrn())
                    .usingJobData(AbstractImportDatasetJob.DATASET_VERSION_ID, taskInfoDataset.getDatasetVersionId())
                    .usingJobData(AbstractImportDatasetJob.TASK_NAME, taskName)
                    .usingJobData(AbstractImportDatasetJob.USER, serviceContext.getUserId());
        // @formatter:on

        if (DatabaseDatasetImportUtils.isDatabaseDatasetImportJob(serviceContext)) {
            DateTime dt = (DateTime) serviceContext.getProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_EXECUTION_DATE);

            // @formatter:off
            jobBuilder.ofType(ImportDatasetFromDatabaseJob.class)
                .usingJobData(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_FLAG, Boolean.TRUE)
                .usingJobData(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_EXECUTION_DATE, dt.getMillis())
                .usingJobData(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER, (String) serviceContext.getProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER))
                .usingJobData(ImportDatasetFromDatabaseJob.STATISTICAL_OPERATION_URN, taskInfoDataset.getStatisticalOperationUrn());
            // @formatter:on
        } else {
            jobBuilder.ofType(ImportDatasetJob.class);
        }

        return jobBuilder.requestRecovery().build();
    }

    @Override
    public synchronized String planifyRecoveryImportDataset(ServiceContext ctx, TaskInfoDataset taskInfoDataset, Boolean notifyToUser) throws MetamacException {
        // Validation
        taskServiceInvocationValidator.checkPlanifyRecoveryImportDataset(ctx, taskInfoDataset, notifyToUser);

        String datasetUrn = taskInfoDataset.getDatasetUrn();

        // Job keys
        JobKey recoveryImportJobKey = createJobKeyForRecoveryImportationResource(datasetUrn);
        TriggerKey recoveryImportTriggerKey = createTriggerKeyForRecoveryImportationDataset(datasetUrn);

        // Scheduler an importation job
        Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

        // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
        // @formatter:off
        JobDetail recoveryImportJob = newJob(RecoveryImportDatasetJob.class)
                                        .withIdentity(recoveryImportJobKey)
                                        .usingJobData(RecoveryImportDatasetJob.DATASET_VERSION_ID, taskInfoDataset.getDatasetVersionId())
                                        .usingJobData(RecoveryImportDatasetJob.USER, ctx.getUserId())
                                        .usingJobData(RecoveryImportDatasetJob.NOTIFY_TO_USER, notifyToUser)
                                        .usingJobData(RecoveryImportDatasetJob.DATASET_URN, datasetUrn)
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

        String datasetUrn = taskInfoDataset.getDatasetUrn();
        String taskName = createJobNameForDuplicationResource(taskInfoDataset.getDatasetVersionId());

        // Job keys
        JobKey duplicationJobKey = createJobKeyForDuplicationResource(datasetUrn);
        TriggerKey duplicationTriggerKey = createTriggerKeyForDuplicationDataset(datasetUrn);

        try {
            // Scheduler an importation job
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

            checkExistTaskInResource(ctx, sched, duplicationJobKey, datasetUrn);

            // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
            HashMap<String, List<Mapping>> jobDataMap = new HashMap<String, List<Mapping>>();
            jobDataMap.put(DuplicationDatasetJob.DATASOURCE_MAPPINGS, datasourcesMapping);
            // @formatter:off
            JobDetail duplicationImportJob = newJob(DuplicationDatasetJob.class)
                    .withIdentity(duplicationJobKey)
                    .usingJobData(DuplicationDatasetJob.DATASET_VERSION_ID, taskInfoDataset.getDatasetVersionId())
                    .usingJobData(DuplicationDatasetJob.USER, ctx.getUserId())
                    .usingJobData(DuplicationDatasetJob.NEW_DATASET_VERSION_ID, newDatasetId)
                    .usingJobData(DuplicationDatasetJob.DATASET_URN, datasetUrn)
                    .usingJobData(DuplicationDatasetJob.TASK_NAME, taskName)
                    .usingJobData(new JobDataMap(jobDataMap))
                    .requestRecovery()
                    .build();
            // @formatter:on

            Task task = new Task(taskName);
            task.setStatus(TaskStatusTypeEnum.IN_PROGRESS);
            task.setExtensionPoint(newDatasetId + JobUtil.SERIALIZATION_SEPARATOR + taskInfoDataset.getDatasetVersionId());
            createTask(ctx, task);

            if (!DatabaseDatasetImportUtils.isDatabaseDatasetImportJob(ctx)) {
                SimpleTrigger duplicationImportTrigger = newTrigger().withIdentity(duplicationTriggerKey).startAt(futureDate(10, IntervalUnit.SECOND)).withSchedule(simpleSchedule()).build();

                try {
                    sched.scheduleJob(duplicationImportJob, duplicationImportTrigger);
                } catch (SchedulerException e) {
                    logger.error("PlannifyRecoveryImportDataset: the recovery importation with key " + duplicationJobKey.getName() + " has failed", e);
                }
            } else {
                processDuplicationTask(ctx, taskName, taskInfoDataset, newDatasetId, datasourcesMapping);
            }
        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(e.getMessage()).withCause(e).withLoggedLevel(ExceptionLevelEnum.ERROR)
                    .build(); // Error
        }

        return duplicationJobKey.getName();
    }

    private void checkExistTaskInResource(ServiceContext ctx, Scheduler sched, JobKey jobKey, String datasetUrn) throws SchedulerException, MetamacException {
        checkSameJobNotExists(jobKey);

        checkExistRecoveryImportationTaskInResource(ctx, datasetUrn);

        if (!createJobKeyForImportationResource(datasetUrn).equals(jobKey)) {
            checkExistImportationTaskInResource(ctx, datasetUrn);
        }

        if (!createJobKeyForDatabaseImportationResource(datasetUrn).equals(jobKey)) {
            checkExistDatabaseImportationTaskInResource(ctx, datasetUrn);
        }

        if (!createJobKeyForDuplicationResource(datasetUrn).equals(jobKey)) {
            checkExistDuplicationTaskInResource(ctx, datasetUrn);
        }
    }

    private void checkExistDuplicationTaskInResource(ServiceContext ctx, String datasetUrn) throws MetamacException {
        if (existDuplicationTaskInResource(ctx, datasetUrn)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_DUPLICATION_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build();
        }
    }

    private void checkExistRecoveryImportationTaskInResource(ServiceContext ctx, String datasetUrn) throws MetamacException {
        if (existRecoveryImportationTaskInResource(ctx, datasetUrn)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_RECOVERY_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build();
        }
    }

    private void checkExistDatabaseImportationTaskInResource(ServiceContext ctx, String datasetUrn) throws MetamacException {
        if (existDatabaseImportationTaskInResource(ctx, datasetUrn)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_DATABASE_IMPORTATION_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build();
        }
    }

    private void checkExistImportationTaskInResource(ServiceContext ctx, String datasetUrn) throws MetamacException {
        if (existImportationTaskInResource(ctx, datasetUrn)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_JOB_IMPORTATION_IN_PROCESS).withLoggedLevel(ExceptionLevelEnum.ERROR).build();
        }
    }

    private void checkSameJobNotExists(JobKey jobKey) throws MetamacException {
        try {
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler
            if (sched.checkExists(jobKey)) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TASKS_ERROR_MAX_CURRENT_JOBS).withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        } catch (SchedulerException e) {
            throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_SCHEDULER_ERROR).withMessageParameters(e.getMessage()).build();
        }
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

        if (!DatabaseDatasetImportUtils.isDatabaseDatasetImportJob(ctx)) {
            markTaskAsFinished(ctx, importationJobKey); // Finish the importation
        }
    }

    @Override
    public void processDatabaseImportationTask(ServiceContext ctx, String databaseImportationJobKey, TaskInfoDataset taskInfoDataset) throws MetamacException {
        taskServiceInvocationValidator.checkProcessDatabaseImportationTask(ctx, databaseImportationJobKey, taskInfoDataset);

        String datasetVersionUrn = taskInfoDataset.getDatasetVersionId();
        DatasetVersion datasetVersion = datasetService.retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        if (ProcStatusEnum.PUBLISHED.equals(datasetVersion.getLifeCycleStatisticalResource().getEffectiveProcStatus())) {
            datasetVersionUrn = versioningDatasetVersion(ctx, taskInfoDataset.getDatasetVersionId());

            // After versioning, it's necessary to update the task info because there is a new version of the dataset. The importation task should be applied to this.
            updateImportationTaskInfo(datasetVersionUrn, taskInfoDataset);

            executeImportationTask(ctx, databaseImportationJobKey, taskInfoDataset);

            updateMetadataDatasetVersion(ctx, datasetVersionUrn);

            sendDatasetVersionToProductionValidation(ctx, datasetVersionUrn);

            sendDatasetVersionToDiffusionValidation(ctx, datasetVersionUrn);

            publishDatasetVersion(ctx, datasetVersionUrn);
        } else {
            executeImportationTask(ctx, databaseImportationJobKey, taskInfoDataset);
        }

        markDatabaseImportTaskAsFinished(ctx, databaseImportationJobKey);
    }

    private String versioningDatasetVersion(ServiceContext ctx, String datasetVersionUrn) {
        logger.debug("Versioning dataset {}", datasetVersionUrn);

        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(new TransactionCallback<String>() {

            @Override
            public String doInTransaction(TransactionStatus status) {
                try {
                    DatasetVersion datasetVersion = datasetLifecycleService.versioning(ctx, datasetVersionUrn, VersionTypeEnum.MINOR);
                    return datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
                } catch (MetamacException e) {
                    throw new RuntimeException("Transactional method versioning dataset failed.", e);
                }
            }
        });
    }

    private void updateImportationTaskInfo(String datasetVersionUrn, TaskInfoDataset taskInfoDataset) {
        logger.debug("Updating task with the new dataset {}", datasetVersionUrn);
        taskInfoDataset.setDatasetVersionId(datasetVersionUrn);
    }

    private void executeImportationTask(ServiceContext ctx, String databaseImportationJobKey, TaskInfoDataset taskInfoDataset) {
        logger.debug("Execute importation dataset task {}", taskInfoDataset.getDatasetVersionId());

        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    processImportationTask(ctx, databaseImportationJobKey, taskInfoDataset);
                } catch (MetamacException e) {
                    throw new RuntimeException("Transactional method execute importation dataset dataset task failed.", e);
                }
            }
        });
    }

    private void updateMetadataDatasetVersion(ServiceContext ctx, String datasetVersionUrn) {
        logger.debug("Updating required metada for dataset {}", datasetVersionUrn);

        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    // Retrieve dataset again to get it updated after importation task
                    DatasetVersion datasetVersion = datasetService.retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

                    DatabaseDatasetImportUtils.setRequiredMetadataForDatabaseDatasetImportation(datasetVersion);

                    // It's necessary to save the new metadata of the dataset before continuing transiting it through the life cycle
                    datasetService.updateDatasetVersion(ctx, datasetVersion);
                } catch (MetamacException e) {
                    throw new RuntimeException("Transactional method updating required metada failed.", e);
                }
            }
        });
    }

    private void sendDatasetVersionToProductionValidation(ServiceContext ctx, String datasetVersionUrn) {
        logger.debug("Sending to production validation dataset {}", datasetVersionUrn);

        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    datasetLifecycleService.sendToProductionValidation(ctx, datasetVersionUrn);
                } catch (MetamacException e) {
                    throw new RuntimeException("Transactional method sending to production validation failed.", e);
                }
            }
        });
    }

    private void sendDatasetVersionToDiffusionValidation(ServiceContext ctx, String datasetVersionUrn) {
        logger.debug("Sending to difussion validation dataset {}", datasetVersionUrn);

        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    datasetLifecycleService.sendToDiffusionValidation(ctx, datasetVersionUrn);
                } catch (MetamacException e) {
                    throw new RuntimeException("Transactional method sending to difussion validation failed.", e);
                }
            }
        });
    }

    private void publishDatasetVersion(ServiceContext ctx, String datasetVersionUrn) {
        logger.debug("Publishing dataset {}", datasetVersionUrn);

        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    datasetLifecycleService.sendToPublished(ctx, datasetVersionUrn);
                } catch (MetamacException e) {
                    throw new RuntimeException("Transactional method publishing dataset failed.", e);
                }
            }
        });
    }

    private void markDatabaseImportTaskAsFinished(ServiceContext ctx, String databaseImportationJobKey) {
        logger.debug("Marking databaset task as finished {}", databaseImportationJobKey);

        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    markTaskAsFinished(ctx, databaseImportationJobKey);
                } catch (MetamacException e) {
                    throw new RuntimeException("Transactional method Marking databaset task failed.", e);
                }
            }
        });
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
        taskServiceInvocationValidator.checkExistsTaskForResource(ctx, resourceId);
        return existImportationTaskInResource(ctx, resourceId) || existRecoveryImportationTaskInResource(ctx, resourceId) || existDuplicationTaskInResource(ctx, resourceId)
                || (existDatabaseImportationTaskInResource(ctx, resourceId));
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
    public boolean existDatabaseImportationTaskInResource(ServiceContext ctx, String resourceId) throws MetamacException {
        taskServiceInvocationValidator.checkExistDatabaseImportationTaskInResource(ctx, resourceId);
        try {
            Scheduler sched = SchedulerRepository.getInstance().lookup(SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler
            return !DatabaseDatasetImportUtils.isDatabaseDatasetImportJob(ctx) && sched.checkExists(createJobKeyForDatabaseImportationResource(resourceId));
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

            String datasetVersionId = extractDatasetIdFromJobKeyImportationDataset(jobKey);
            String datasetId = retrieveDatasetId(ctx, datasetVersionId);

            TaskInfoDataset recoveryTaskInfo = new TaskInfoDataset();
            recoveryTaskInfo.setDatasetVersionId(datasetVersionId);
            recoveryTaskInfo.setDatasetUrn(datasetId);
            planifyRecoveryImportDataset(ctx, recoveryTaskInfo, Boolean.TRUE);
        } else if (jobKey.startsWith(PREFIX_JOB_DUPLICATION_DATA)) {
            processRollbackDuplicationTaskOnApplicationStartup(ctx, task);
        } else if (jobKey.startsWith(PREFIX_JOB_DATABASE_IMPORT_DATA)) {
            // TODO METAMAC-2866 Database import recovery not defined yet, task is deleted in order to not generated side effects but this behavior is temporary until this TODO will be resolved
            markTaskAsFinished(ctx, task.getJob());
        }
    }

    private String retrieveDatasetId(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        DatasetVersion datasetVersion = datasetService.retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);
        return datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn();
    }

    @Override
    public void markTaskAsFailed(ServiceContext ctx, String jobKey, String datasetVersionId, String datasetUrn) throws MetamacException {
        Task task = retrieveTaskByJob(ctx, jobKey);
        // Plannify a recovery job
        if (jobKey.startsWith(PREFIX_JOB_IMPORT_DATA)) {
            // Update
            task.setStatus(TaskStatusTypeEnum.FAILED);
            updateTask(ctx, task);

            TaskInfoDataset recoveryTaskInfo = new TaskInfoDataset();
            recoveryTaskInfo.setDatasetVersionId(datasetVersionId);
            recoveryTaskInfo.setDatasetUrn(datasetUrn);
            planifyRecoveryImportDataset(ctx, recoveryTaskInfo, Boolean.FALSE);
        } else if (jobKey.startsWith(PREFIX_JOB_DUPLICATION_DATA)) {
            processRollbackDuplicationTask(ctx, task);
        } else if (jobKey.startsWith(PREFIX_JOB_DATABASE_IMPORT_DATA)) {
            // TODO METAMAC-2866 Database import recovery not defined yet, task is deleted in order to not generated side effects but this behavior is temporary until this TODO will be resolved
            markTaskAsFinished(ctx, task.getJob());
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
        return new JobKey(createJobNameForImportationResource(resourceId), GROUP_IMPORTATION);
    }

    private JobKey createJobKeyForDatabaseImportationResource(String resourceId) {
        return new JobKey(createJobNameForDatabaseImportationResource(resourceId), GROUP_IMPORTATION);
    }

    private JobKey createJobKeyForRecoveryImportationResource(String resourceId) {
        return new JobKey(createJobNameForRecoveryImportationResource(resourceId), GROUP_IMPORTATION);
    }

    private JobKey createJobKeyForDuplicationResource(String resourceId) {
        return new JobKey(createJobNameForDuplicationResource(resourceId), GROUP_IMPORTATION);
    }

    private TriggerKey createTriggerKeyForImportationDataset(String datasetId) {
        return new TriggerKey(createJobNameForImportationResource(datasetId), GROUP_IMPORTATION);
    }

    private TriggerKey createTriggerKeyForDbImportationDataset(String datasetId) {
        return new TriggerKey(createJobNameForDatabaseImportationResource(datasetId), GROUP_IMPORTATION);
    }

    private TriggerKey createTriggerKeyForRecoveryImportationDataset(String datasetId) {
        return new TriggerKey(createJobNameForRecoveryImportationResource(datasetId), GROUP_IMPORTATION);
    }

    private TriggerKey createTriggerKeyForDuplicationDataset(String datasetId) {
        return new TriggerKey(createJobNameForDuplicationResource(datasetId), GROUP_IMPORTATION);
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
        return (DatabaseDatasetImportUtils.isDatabaseDatasetImportJob(serviceContext)
                ? (String) serviceContext.getProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER)
                : Datasource.generateDataSourceId(fileName, dateTime));
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
    public void processDatabaseDatasetPollingTask(ServiceContext ctx) throws MetamacException {
        taskServiceInvocationValidator.checkProcessDatabaseDatasetPollingTask(ctx);

        DateTime executionDate = new DateTime();

        List<DatasetVersion> datasetsVersions = retrieveDatabaseDatasets(ctx);

        if (!CollectionUtils.isEmpty(datasetsVersions)) {
            for (DatasetVersion datasetVersion : datasetsVersions) {
                if (!CollectionUtils.isEmpty(datasetVersion.getDatasources())) {
                    updateDataFromDatasources(ctx, executionDate, datasetVersion);
                } else {
                    logger.debug("There are no datasources configured yet for dataset {}", datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
                }
            }

        } else {
            logger.debug("There are no database datasets configured yet");
        }
    }

    private List<DatasetVersion> retrieveDatabaseDatasets(ServiceContext ctx) throws MetamacException {
        // @formatter:off
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class)
                .withProperty(DatasetVersionProperties.dataSourceType()).eq(DataSourceTypeEnum.DATABASE)
                .and()
                .withProperty(DatasetVersionProperties.datasources()).isNotEmpty()
                .and()
                .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().lastVersion()).eq(Boolean.TRUE)
                .distinctRoot().build();
        // @formatter:off

        List<DatasetVersion> datasetsVersion = datasetVersionRepository.findByCondition(conditions);
        return filterDatasetsWithTaskInProgress(ctx, datasetsVersion);
    }
    
    private List<DatasetVersion> filterDatasetsWithTaskInProgress(ServiceContext ctx, List<DatasetVersion> datasetsVersion) throws MetamacException {
        List<DatasetVersion> dsv = new ArrayList<>();
        if (datasetsVersion != null) {
            for (DatasetVersion datasetVersion : datasetsVersion) {
                if (!existsTaskForResource(ctx, datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn())) {
                    dsv.add(datasetVersion);
                }
            }
        }
        return dsv;
    }
    
    private void updateDataFromDatasources(ServiceContext ctx, DateTime executionDate, DatasetVersion datasetVersion) {
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        for (Datasource datasource : datasetVersion.getDatasources()) {
            try {
                // Get table name from datasource and check if it exists
                String tableName = datasource.getSourceName();

                checkTableExists(tableName, datasetVersionUrn);

                // Get dimensions, attributes and measure columns name from dsd, get filter column name from configuration in common metadata and check if they exists
                DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn(datasetVersion.getRelatedDsd().getUrn());

                List<String> dimensionsColumnsName = getDimensionsColumnsName(dataStructure);

                List<String> attributesColumnsName = getAttibutesColumnsName(dataStructure);

                String measureColumnName = getMeasureColumnName(dataStructure);

                String filterColumnName = getFilterColumnName();

                checkTableHasRequiredColumns(tableName, dimensionsColumnsName, attributesColumnsName, measureColumnName, filterColumnName, datasetVersionUrn);

                List<String> columnsName = getAllQueryColumns(dimensionsColumnsName, attributesColumnsName, measureColumnName);

                // Get de filter column value from last data import
                DateTime filterColumnValue = getFilterColumnValue(datasetVersion);

                // TODO METAMAC-2866 A possible improve could be to do a paginated query to get the observations and write them to file progressively to avoid memory problems derivated from having a
                // huge number of observations in memory
                List<String[]> observations = getObservations(tableName, columnsName, filterColumnName, filterColumnValue);

                if (!observations.isEmpty()) {
                    File csvFile = generateCsvFile(tableName, columnsName, observations);

                    List<URL> fileUrls = new ArrayList<>();
                    fileUrls.add(csvFile.toURI().toURL());

                    logger.info("Planning a database import for dataset {} generated file: {} ", datasetVersionUrn, csvFile.getName());

                    setContextPropertiesForDbImportJob(ctx, executionDate, datasource);
                    importDatabaseDatasourcesInDatasetVersion(ctx, datasetVersionUrn, fileUrls, new HashMap<>(), Boolean.FALSE);

                    logger.info("Planned a database import for dataset {} generated file: {} ", datasetVersionUrn, csvFile.getName());

                } else {
                    logger.debug("There are no new observations in table {} for dataset {}", tableName, datasetVersionUrn);
                }
            } catch (Exception e) {
                logger.error("An unexpected error has occurred trying to do a DB import for dataset {}", datasetVersionUrn, e);
            }
        }
    }
    
    private void checkTableExists(String tableName, String datasetVersionUrn) throws MetamacException {
        if (!databaseImportRepository.checkTableExists(tableName)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.TABLE_NOT_EXIST).withMessageParameters(tableName, datasetVersionUrn).build();
        }
    }
    
    private List<String> getDimensionsColumnsName(DataStructure dataStructure) {
        List<String> dimensionsColumnName = new ArrayList<>();

        for (DimensionBase dimensionBase : dataStructure.getDataStructureComponents().getDimensions().getDimensions()) {
            dimensionsColumnName.add(dimensionBase.getId());
        }

        return dimensionsColumnName;
    }
    
    private List<String> getAttibutesColumnsName(DataStructure dataStructure) throws MetamacException {
        List<String> attibutesColumnName = new ArrayList<>();

        for (AttributeBase attributeBase : dataStructure.getDataStructureComponents().getAttributes().getAttributes()) {
            DsdAttribute dsdAttribute = new DsdAttribute((Attribute) attributeBase);
            if (dsdAttribute.isAttributeAtObservationLevel()) {
                attibutesColumnName.add(attributeBase.getId());
            }
        }

        return attibutesColumnName;
    }
    
    private String getMeasureColumnName(DataStructure dataStructure) {
        return dataStructure.getDataStructureComponents().getMeasure().getPrimaryMeasure().getId();
    }
    
    private String getFilterColumnName() throws MetamacException {
        return configurationService.retriveFilterColumnNameForDbDataImport();
    }
    
    private List<String> getAllQueryColumns(List<String> dimensionsColumnsName, List<String> attributesColumnsName, String measureColumnName) {
        List<String> queryColumns = new ArrayList<>();

        queryColumns.addAll(dimensionsColumnsName);
        queryColumns.add(measureColumnName);
        queryColumns.addAll(attributesColumnsName);

        return queryColumns;
    }
    
    private void checkTableHasRequiredColumns(String tableName, List<String> dimensionsColumnsName, List<String> attributesColumnsName, String measureColumnName, String filterColumnName,
            String datasetVersionUrn) throws MetamacException {
        checkTableHasDimensionColumns(tableName, dimensionsColumnsName, datasetVersionUrn);
        checkTableHasAttibuteColumns(tableName, attributesColumnsName, datasetVersionUrn);
        checkTableHasObservationColumn(tableName, measureColumnName, datasetVersionUrn);
        checkTableHasFilterColumn(tableName, filterColumnName, datasetVersionUrn);
    }
    
    private void checkTableHasDimensionColumns(String tableName, List<String> columnsName, String datasetVersionUrn) throws MetamacException {
        checkTableHasColumns(ServiceExceptionType.DIMENSION_COLUMN_NOT_EXIST, datasetVersionUrn, tableName, columnsName);
    }
    
    private void checkTableHasAttibuteColumns(String tableName, List<String> attributesColumnsName, String datasetVersionUrn) throws MetamacException {
        checkTableHasColumns(ServiceExceptionType.ATTRIBUTE_COLUMN_NOT_EXIST, datasetVersionUrn, tableName, attributesColumnsName);
    }
    
    private void checkTableHasObservationColumn(String tableName, String observationColumnName, String datasetVersionUrn) throws MetamacException {
        checkTableHasColumns(ServiceExceptionType.OBS_COLUMN_NOT_EXIST, datasetVersionUrn, tableName, Arrays.asList(observationColumnName));
    }
    
    private void checkTableHasFilterColumn(String tableName, String filterColumn, String datasetVersionUrn) throws MetamacException {
        checkTableHasColumns(ServiceExceptionType.FILTER_COLUMN_NOT_EXIST, datasetVersionUrn, tableName, Arrays.asList(filterColumn));
    }
    
    private void checkTableHasColumns(CommonServiceExceptionType commonServiceExceptionType, String datasetVersionUrn, String tableName, List<String> columnsName) throws MetamacException {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<>();

        for (String columnName : columnsName) {
            if (!databaseImportRepository.checkTableHasColumn(tableName, columnName)) {
                exceptionItems.add(new MetamacExceptionItem(commonServiceExceptionType, tableName, datasetVersionUrn, columnName));
            }
        }

        if (!exceptionItems.isEmpty()) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(exceptionItems).build();
        }
    }
    
    private DateTime getFilterColumnValue(DatasetVersion datasetVersion) {
        return datasetVersion.getDateLastTimeDataImport();
    }
    
    private List<String[]> getObservations(String tableName, List<String> columnsName, String filterColumnName, DateTime filterColumnValue) throws MetamacException {
        return databaseImportRepository.getObservations(tableName, columnsName, filterColumnName, filterColumnValue);
    }
    
    private File generateCsvFile(String tableName, List<String> columnsName, List<String[]> observations) throws MetamacException {
        return writeTempCsvFile(createTempCsvFile(tableName), observations, columnsName);
    }
    
    private File createTempCsvFile(String tableName) throws MetamacException {
        try {
            return File.createTempFile("dbImport_" + tableName + "_", ".csv");
        } catch (IOException e) {
            throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.IMPORTATION_CSV_FILE_ERROR).withMessageParameters(ExceptionHelper.excMessage(e)).build();
        }
    }
    
    private File writeTempCsvFile(File file, List<String[]> observations, List<String> columnsName) throws MetamacException {
        try (OutputStream os = new FileOutputStream(file); CsvWriter csvWriter = new CsvWriter(os, "UTF-8", CsvConstants.SEPARATOR_TAB)) {
            logger.debug("Temporary csv file created {}", file.getAbsolutePath());
            csvWriter.write(columnsName.toArray(new String[0]), observations);
            return file;
        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.IMPORTATION_CSV_FILE_ERROR).withMessageParameters(ExceptionHelper.excMessage(e)).build();
        }
    }
    
    private void setContextPropertiesForDbImportJob(ServiceContext ctx, DateTime executionDate, Datasource datasource) {
        ctx.setProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER, datasource.getIdentifiableStatisticalResource().getCode());
        ctx.setProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_EXECUTION_DATE, executionDate);
        ctx.setProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_FLAG, Boolean.TRUE);
    }

    public void importDatabaseDatasourcesInDatasetVersion(ServiceContext ctx, String datasetVersionUrn, List<URL> fileUrls, Map<String, String> dimensionRepresentationMapping,
            boolean storeDimensionRepresentationMapping) throws MetamacException {
        datasetService.importDatabaseDatasourcesInDatasetVersion(ctx, datasetVersionUrn, fileUrls, dimensionRepresentationMapping, storeDimensionRepresentationMapping);
    }
}
