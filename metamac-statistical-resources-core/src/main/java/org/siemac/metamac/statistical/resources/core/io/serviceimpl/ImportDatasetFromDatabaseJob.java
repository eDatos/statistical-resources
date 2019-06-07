package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.quartz.JobDataMap;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;

public class ImportDatasetFromDatabaseJob extends AbstractImportDatasetJob {

    public static final String DATABASE_IMPORT_JOB_FLAG                  = "custom_import";
    public static final String DATABASE_IMPORT_JOB_EXECUTION_DATE        = "import_excution_date";
    public static final String DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER = "datasource_identifier";

    /**
     * Quartz requires a public empty constructor so that the scheduler can instantiate the class whenever it needs.
     */
    public ImportDatasetFromDatabaseJob() {
        // NOTHING TO DO HERE
    }

    @Override
    protected ServiceContext setAdditionalProperties(ServiceContext serviceContext, JobDataMap jobDataMap) {

        serviceContext.setProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_FLAG, isImportDatasetFromDatabaseJob(jobDataMap));
        serviceContext.setProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_EXECUTION_DATE, getExecutionDate(jobDataMap));
        serviceContext.setProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER, getDatasourceIdentifier(jobDataMap));

        return serviceContext;
    }

    private String getDatasourceIdentifier(JobDataMap jobDataMap) {
        return jobDataMap.getString(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER);
    }

    private DateTime getExecutionDate(JobDataMap jobDataMap) {
        return new DateTime(jobDataMap.getLong(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_EXECUTION_DATE));
    }

    private Boolean isImportDatasetFromDatabaseJob(JobDataMap jobDataMap) {
        return jobDataMap.getBoolean(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_FLAG);
    }

    @Override
    protected void sendSuccessNotification(String fileNames, String user) {
        // This dataset import is invoked from a continuous job, it's not necessary notification to user when the process goes OK
    }

    @Override
    protected void sendErrorNotification(MetamacException metamacException) {
        String statisticalOperationUrn = getData().getString(AbstractImportDatasetJob.STATISTICAL_OPERATION_URN);
        getNoticesRestInternalService().createDatabaseImportErrorBackgroundNotification(statisticalOperationUrn, ServiceNoticeAction.IMPORT_DATASET_JOB, metamacException);
    }

    @Override
    protected void executeImportTask(ServiceContext serviceContext, String jobName, TaskInfoDataset taskInfoDataset) throws MetamacException {
        getTaskServiceFacade().executeDatabaseImportationTask(serviceContext, jobName, taskInfoDataset);
    }

    @Override
    protected void processImportJobError(String taskName, String fileNames, MetamacException metamacException) {
        try {
            getTaskServiceFacade().markTaskAsFinished(serviceContext, taskName);
            logger.info("{} marked as finished with error at {}", taskName, new Date());
            metamacException.setPrincipalException(new MetamacExceptionItem(ServiceExceptionType.DB_IMPORT_DATASET_JOB_ERROR, fileNames));
            sendErrorNotification(metamacException);
        } catch (MetamacException e1) {
            logger.error("The importation with key {} has failed and it can't marked as finished with error", taskName, e1);
            metamacException.setPrincipalException(new MetamacExceptionItem(ServiceExceptionType.DB_IMPORT_DATASET_JOB_ERROR_AND_CANT_MARK_AS_FINISHED, fileNames));
            sendErrorNotification(metamacException);
        }
    }
}
