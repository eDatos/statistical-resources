package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.io.FileInputStream;
import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.dto.task.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportDatasetJob implements Job {

    private static Logger      logger             = LoggerFactory.getLogger(ImportDatasetJob.class);

    public static final String USER               = "user";
    public static final String FILE_PATH          = "filePath";
    public static final String DATA_STRUCTURE_URN = "dataStructureUrn";
    public static final String FILE_NAME          = "fileName";
    public static final String REPO_DATASET_ID    = "repoDatasetId";

    private TaskServiceFacade  taskServiceFacade  = null;

    /**
     * Quartz requires a public empty constructor so that the scheduler can instantiate the class whenever it needs.
     */
    public ImportDatasetJob() {
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
            logger.info("ImportationJob: " + jobKey + " starting at " + new Date());
            TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
            taskInfoDataset.setDataStructureUrn(data.getString(DATA_STRUCTURE_URN));
            taskInfoDataset.setFileName(data.getString(FILE_NAME));
            taskInfoDataset.setJobKey(jobKey.getName());
            taskInfoDataset.setRepoDatasetId(REPO_DATASET_ID);
            getTaskServiceFacade().executeImportationTask(serviceContext, new FileInputStream(data.getString(FILE_PATH)), taskInfoDataset);
            logger.info("ImportationJob: " + jobKey + " finished at " + new Date());
        } catch (Exception e) {
            logger.error("ImportationJob: the importation with key " + jobKey.getName() + " has failed", e);
            // TODO REcuperación de errores
            // try {
            // getTaskServiceFacade().markTaskAsFailed(serviceContext, jobKey.getName(), e);
            // } catch (MetamacException e1) {
            // logger.error("ImportationJob: the importation with key " + jobKey.getName() + " has failed and it can't marked as error", e1);
            // }
        }
    }
}
