package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.NoticesRestInternalService;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecoveryImportDatasetJob implements Job {

    private static Logger      logger             = LoggerFactory.getLogger(RecoveryImportDatasetJob.class);

    public static final String USER               = "user";
    public static final String DATASET_VERSION_ID = "datasetVersionId";
    public static final String NOTIFY_TO_USER     = "notifyToUser";
    public static final String DATASET_URN        = "datasetUrn";

    private TaskServiceFacade  taskServiceFacade  = null;

    /**
     * Quartz requires a public empty constructor so that the scheduler can instantiate the class whenever it needs.
     */
    public RecoveryImportDatasetJob() {
    }

    public TaskServiceFacade getTaskServiceFacade() {
        if (taskServiceFacade == null) {
            taskServiceFacade = (TaskServiceFacade) ApplicationContextProvider.getApplicationContext().getBean(TaskServiceFacade.BEAN_ID);
        }

        return taskServiceFacade;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey jobKey = context.getJobDetail().getKey();

        // Parameters
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String user = data.getString(USER);
        String datasetVersionId = data.getString(DATASET_VERSION_ID);
        String datasetUrn = data.getString(DATASET_URN);
        Boolean notifyToUser = data.getBoolean(NOTIFY_TO_USER);

        // Execution
        ServiceContext serviceContext = new ServiceContext(user, context.getFireInstanceId(), "statistical-resources-core");

        try {
            logger.info("RecoveryImportationJob: " + jobKey + " starting at " + new Date());

            TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
            taskInfoDataset.setDatasetVersionId(datasetVersionId);
            taskInfoDataset.setDatasetUrn(datasetUrn);

            getTaskServiceFacade().executeRecoveryImportationTask(serviceContext, jobKey.getName(), taskInfoDataset);
            logger.info("RecoveryImportationJob: " + jobKey + " finished at " + new Date());
        } catch (Exception e) {
            logger.error("RecoveryImportationJob: the importation with key " + jobKey.getName() + " has failed", e);
        } finally {
            if (notifyToUser) {
                MetamacException metamacException = MetamacExceptionBuilder.builder().withPrincipalException(ServiceExceptionType.TASKS_ERROR_SERVER_DOWN, jobKey).build();
                getNoticesRestInternalService().createErrorBackgroundNotification(user, ServiceNoticeAction.CANCEL_IN_PROGRESS_TASKS_WHILE_SERVER_SHUTDOWN, metamacException);
            }
        }
    }

    private NoticesRestInternalService getNoticesRestInternalService() {
        return (NoticesRestInternalService) ApplicationContextProvider.getApplicationContext().getBean(NoticesRestInternalService.BEAN_ID);
    }
}
