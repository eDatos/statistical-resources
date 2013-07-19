package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecoveryImportDatasetJob implements Job {

    private static Logger      logger            = LoggerFactory.getLogger(RecoveryImportDatasetJob.class);

    public static final String USER              = "user";
    public static final String REPO_DATASET_ID   = "repoDatasetId";

    private TaskServiceFacade  taskServiceFacade = null;

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

        // Execution
        ServiceContext serviceContext = new ServiceContext(data.getString(USER), context.getFireInstanceId(), "sdmx-srm-core");

        try {
            logger.info("RecoveryImportationJob: " + jobKey + " starting at " + new Date());

            getTaskServiceFacade().executeRecoveryImportationTask(serviceContext, jobKey.getName(), data.getString(REPO_DATASET_ID));
            logger.info("RecoveryImportationJob: " + jobKey + " finished at " + new Date());
        } catch (Exception e) {
            logger.error("RecoveryImportationJob: the importation with key " + jobKey.getName() + " has failed", e);
        }
    }
}
