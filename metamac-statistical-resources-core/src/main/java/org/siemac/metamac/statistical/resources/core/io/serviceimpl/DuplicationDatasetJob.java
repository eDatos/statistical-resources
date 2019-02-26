package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.Date;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.NoticesRestInternalService;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeMessage;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.edatos.dataset.repository.dto.Mapping;

public class DuplicationDatasetJob implements Job {

    private static Logger      logger                 = LoggerFactory.getLogger(DuplicationDatasetJob.class);

    public static final String USER                   = "user";
    public static final String DATASET_VERSION_ID     = "datasetVersionId";
    public static final String NEW_DATASET_VERSION_ID = "newDatasetVersionId";
    public static final String DATASOURCE_MAPPINGS    = "datasourceMappings";
    public static final String DATASET_URN            = "datasetUrn";
    public static final String TASK_NAME              = "taskName";

    private TaskServiceFacade  taskServiceFacade      = null;

    /**
     * Quartz requires a public empty constructor so that the scheduler can instantiate the class whenever it needs.
     */
    public DuplicationDatasetJob() {
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
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String datasetVersionId = data.getString(DATASET_VERSION_ID);
        String newDatasetVersionId = data.getString(NEW_DATASET_VERSION_ID);
        String user = data.getString(USER);
        List<Mapping> datasourcesMapping = (List<Mapping>) data.get(DATASOURCE_MAPPINGS);
        String datasetUrn = data.getString(DATASET_URN);
        String taskName = data.getString(TASK_NAME);
        ServiceContext serviceContext = new ServiceContext(user, context.getFireInstanceId(), "statistical-resources-core");

        try {
            logger.info("DuplicationDatasetJob: " + jobKey + " starting at " + new Date());

            TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
            taskInfoDataset.setDatasetVersionId(datasetVersionId);

            getTaskServiceFacade().executeDuplicationTask(serviceContext, taskName, taskInfoDataset, newDatasetVersionId, datasourcesMapping);
            logger.info("DuplicationDatasetJob: " + jobKey + " finished at " + new Date());
            getNoticesRestInternalService().createSuccessBackgroundNotification(user, ServiceNoticeAction.DUPLICATION_DATASET_JOB, ServiceNoticeMessage.DUPLICATION_DATASET_JOB_OK, datasetVersionId,
                    newDatasetVersionId);
        } catch (MetamacException e) {
            logger.error("DuplicationDatasetJob: the duplication with key " + jobKey.getName() + " has failed", e);

            try {
                getTaskServiceFacade().markTaskAsFailed(serviceContext, taskName, datasetVersionId, datasetUrn, e);
                logger.info("ImportationJob: " + jobKey + " marked as error at " + new Date());
                e.setPrincipalException(new MetamacExceptionItem(ServiceExceptionType.DUPLICATION_DATASET_JOB_ERROR, datasetVersionId));
                getNoticesRestInternalService().createErrorBackgroundNotification(user, ServiceNoticeAction.DUPLICATION_DATASET_JOB, e);
            } catch (MetamacException e1) {
                logger.error("ImportationJob: the importation with key " + jobKey.getName() + " has failed and it can't marked as error", e1);
                e.setPrincipalException(new MetamacExceptionItem(ServiceExceptionType.DUPLICATION_DATASET_JOB_ERROR_AND_CANT_MARK_AS_ERROR, datasetVersionId));
                getNoticesRestInternalService().createErrorBackgroundNotification(user, ServiceNoticeAction.DUPLICATION_DATASET_JOB, e1);
            }
        }
    }

    private NoticesRestInternalService getNoticesRestInternalService() {
        return (NoticesRestInternalService) ApplicationContextProvider.getApplicationContext().getBean(NoticesRestInternalService.BEAN_ID);
    }
}
