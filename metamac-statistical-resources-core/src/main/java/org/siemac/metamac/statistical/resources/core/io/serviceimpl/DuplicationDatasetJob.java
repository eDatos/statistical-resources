package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ExceptionHelper;
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
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplicationDatasetJob implements Job {

    private static Logger      logger                 = LoggerFactory.getLogger(DuplicationDatasetJob.class);

    public static final String USER                   = "user";
    public static final String DATASET_VERSION_ID     = "datasetVersionId";
    public static final String NEW_DATASET_VERSION_ID = "newDatasetVersionId";

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

        // Parameters
        JobDataMap data = context.getJobDetail().getJobDataMap();

        // Execution
        ServiceContext serviceContext = new ServiceContext(data.getString(USER), context.getFireInstanceId(), "statistical-resources-core");

        try {
            logger.info("DuplicationDatasetJob: " + jobKey + " starting at " + new Date());

            TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
            taskInfoDataset.setDatasetVersionId(data.getString(DATASET_VERSION_ID));

            getTaskServiceFacade().executeDuplicationTask(serviceContext, jobKey.getName(), taskInfoDataset, data.getString(NEW_DATASET_VERSION_ID));
            logger.info("DuplicationDatasetJob: " + jobKey + " finished at " + new Date());
        } catch (Exception e) {
            logger.error("DuplicationDatasetJob: the duplication with key " + jobKey.getName() + " has failed", e);

            // Concert parser exception to metamac exception
            MetamacException throwableMetamacException = null;
            if (e instanceof MetamacException) {
                throwableMetamacException = (MetamacException) e;
            } else {
                throwableMetamacException = MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(ExceptionHelper.excMessage(e))
                        .build();
            }
            // Mark as failed
            try {
                getTaskServiceFacade().markTaskAsFailed(serviceContext, jobKey.getName(), throwableMetamacException);
                logger.info("ImportationJob: " + jobKey + " marked as error at " + new Date());
            } catch (MetamacException e1) {
                logger.error("ImportationJob: the importation with key " + jobKey.getName() + " has failed and it can't marked as error", e1);
            }
        }
    }
}
