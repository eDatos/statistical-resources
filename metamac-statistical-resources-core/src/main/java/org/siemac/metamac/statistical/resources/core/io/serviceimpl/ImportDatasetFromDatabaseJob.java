package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.quartz.JobDataMap;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;

public class ImportDatasetFromDatabaseJob extends AbstractImportDatasetJob {

    public static final String                DATABASE_IMPORT_JOB_FLAG                  = "custom_import";
    public static final String                DATABASE_IMPORT_JOB_EXECUTION_DATE        = "import_excution_date";
    public static final String                DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER = "datasource_identifier";

    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade         = null;

    /**
     * Quartz requires a public empty constructor so that the scheduler can instantiate the class whenever it needs.
     */
    public ImportDatasetFromDatabaseJob() {
        // NOTHING TO DO HERE
    }

    @Override
    protected ServiceContext setAdditionalProperties(ServiceContext serviceContext, JobDataMap jobDataMap) {

        serviceContext.setProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_FLAG, isCustomJob(jobDataMap));
        serviceContext.setProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_EXECUTION_DATE, getExecutionDate(jobDataMap));
        serviceContext.setProperty(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER, getDatasourceIdentifier(jobDataMap));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, getAdminUser(serviceContext));

        return serviceContext;
    }

    private MetamacPrincipal getAdminUser(ServiceContext serviceContext) {
        MetamacPrincipalAccess metamacPrincipalAccess = new MetamacPrincipalAccess();
        metamacPrincipalAccess.setApplication(StatisticalResourcesConstants.APPLICATION_ID);
        metamacPrincipalAccess.setRole(StatisticalResourcesRoleEnum.ADMINISTRADOR.getName());

        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(metamacPrincipalAccess);
        return metamacPrincipal;
    }

    private String getDatasourceIdentifier(JobDataMap jobDataMap) {
        return jobDataMap.getString(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_DATASOURCE_IDENTIFIER);
    }

    private DateTime getExecutionDate(JobDataMap jobDataMap) {
        return new DateTime(jobDataMap.getLong(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_EXECUTION_DATE));
    }

    private Boolean isCustomJob(JobDataMap jobDataMap) {
        return jobDataMap.getBoolean(ImportDatasetFromDatabaseJob.DATABASE_IMPORT_JOB_FLAG);
    }

    @Override
    protected void sendSuccessNotification(String fileNames, String user) {
        // This dataset import is invoked from a continuous job, it's not necessary notification to user when the process goes OK
    }

    @Override
    protected void sendErrorNotification(MetamacException metamacException) {
        String statisticalOperationUrn = getData().getString(AbstractImportDatasetJob.STATISTICAL_OPERATION_URN);
        getNoticesRestInternalService().createDbImportErrorBackgroundNotification(statisticalOperationUrn, ServiceNoticeAction.IMPORT_DATASET_JOB, metamacException);
    }

    @Override
    protected void executeImportTask(ServiceContext serviceContext, String jobName, TaskInfoDataset taskInfoDataset) throws MetamacException {

        getTaskServiceFacade().executeDatabaseImportationTask(serviceContext, jobName, taskInfoDataset);

        // String datasetVersionUrn = getData().getString(DATASET_VERSION_ID);
        //
        // DatasetVersionDto datasetVersionDto = retrieveDatasetVersion(serviceContext, datasetVersionUrn);
        //
        // if (ProcStatusEnum.PUBLISHED.equals(datasetVersionDto.getProcStatus())) {
        // executeImportTaskForPublishedDataset(datasetVersionDto, jobName, taskInfoDataset);
        // } else {
        // executeImportTaskForNonPublishedDataset(jobName, taskInfoDataset);
        // }
    }

    private void executeImportTaskForNonPublishedDataset(String jobName, TaskInfoDataset taskInfoDataset) throws MetamacException {
        executeImportationTask(jobName, taskInfoDataset);

        markTaskAsFinished(jobName);
    }

    private void executeImportTaskForPublishedDataset(DatasetVersionDto datasetVersionDto, String jobName, TaskInfoDataset taskInfoDataset) throws MetamacException {

        datasetVersionDto = versioningDatasetVersion(datasetVersionDto);

        // After versioning, it's necessary to update the task info because there is a new version of the dataset. The importation task should be applied to this.
        updateImportationTaskInfo(datasetVersionDto, taskInfoDataset);

        executeImportationTask(jobName, taskInfoDataset);

        // Retrieve dataset again to get it updated after importation task
        datasetVersionDto = retrieveDatasetVersion(serviceContext, datasetVersionDto.getUrn());

        setRequiredMetadataForProductionValidation(datasetVersionDto);

        // It's necessary to save the new metadata of the dataset before continuing transiting it through the life cycle
        datasetVersionDto = updateDatasetVersion(datasetVersionDto);

        datasetVersionDto = sendDatasetVersionToProductionValidation(datasetVersionDto);

        datasetVersionDto = sendDatasetVersionToDiffusionValidation(datasetVersionDto);

        publishDatasetVersion(datasetVersionDto);

        markTaskAsFinished(jobName);
    }

    private DatasetVersionDto retrieveDatasetVersion(ServiceContext serviceContext, String datasetVersionUrn) throws MetamacException {
        logger.debug("Retrieving dataset {}", datasetVersionUrn);
        return getStatisticalResourcesServiceFacade().retrieveDatasetVersionByUrn(serviceContext, datasetVersionUrn);
    }

    private void executeImportationTask(String jobName, TaskInfoDataset taskInfoDataset) throws MetamacException {
        logger.debug("Importing data for {}", taskInfoDataset.getDatasetVersionId());
        getTaskServiceFacade().executeImportationTask(serviceContext, jobName, taskInfoDataset);
    }

    private void markTaskAsFinished(String jobName) throws MetamacException {
        logger.debug("Marking as finished importation task {}", jobName);
        getTaskServiceFacade().markTaskAsFinished(serviceContext, jobName);
    }

    private DatasetVersionDto versioningDatasetVersion(DatasetVersionDto datasetVersionDto) throws MetamacException {
        logger.debug("Versioning dataset {}", datasetVersionDto.getUrn());
        return getStatisticalResourcesServiceFacade().versioningDatasetVersion(serviceContext, datasetVersionDto, VersionTypeEnum.MINOR);
    }

    private void updateImportationTaskInfo(DatasetVersionDto datasetVersionDto, TaskInfoDataset taskInfoDataset) {
        logger.debug("Updating task with the new dataset {}", datasetVersionDto.getUrn());
        taskInfoDataset.setDatasetVersionId(datasetVersionDto.getUrn());
    }

    private void setRequiredMetadataForProductionValidation(DatasetVersionDto datasetVersionDto) {
        logger.debug("Setting required metadata for dataset {}", datasetVersionDto.getUrn());

        // DatabaseDatasetImportUtils.setDatasetVersionVersionRationaleType(datasetVersionDto);

        // TODO METAMAC-2866 How to define these metadata has not been defined yet
        // DatabaseDatasetImportUtils.setDatasetVersionNextVersion(datasetVersionDto);
        // DatabaseDatasetImportUtils.setDatasetVersionNextVersionDate(datasetVersionDto);
        // DatabaseDatasetImportUtils.setDatasetVersionDateNextUpdate(datasetVersionDto);
    }

    private DatasetVersionDto updateDatasetVersion(DatasetVersionDto datasetVersionDto) throws MetamacException {
        logger.debug("Updating required metada for dataset {}", datasetVersionDto.getUrn());
        return getStatisticalResourcesServiceFacade().updateDatasetVersion(serviceContext, datasetVersionDto);
    }

    private DatasetVersionDto sendDatasetVersionToProductionValidation(DatasetVersionDto datasetVersionDto) throws MetamacException {
        logger.debug("Sending to production validation dataset {}", datasetVersionDto.getUrn());
        return getStatisticalResourcesServiceFacade().sendDatasetVersionToProductionValidation(serviceContext, datasetVersionDto);
    }

    private DatasetVersionDto sendDatasetVersionToDiffusionValidation(DatasetVersionDto datasetVersionDto) throws MetamacException {
        logger.debug("Sending to difussion validation dataset {}", datasetVersionDto.getUrn());
        return getStatisticalResourcesServiceFacade().sendDatasetVersionToDiffusionValidation(serviceContext, datasetVersionDto);
    }

    private void publishDatasetVersion(DatasetVersionDto datasetVersionDto) throws MetamacException {
        logger.debug("Publishing dataset {}", datasetVersionDto.getUrn());
        getStatisticalResourcesServiceFacade().publishDatasetVersion(serviceContext, datasetVersionDto);
    }

    private StatisticalResourcesServiceFacade getStatisticalResourcesServiceFacade() {
        if (statisticalResourcesServiceFacade == null) {
            statisticalResourcesServiceFacade = (StatisticalResourcesServiceFacade) ApplicationContextProvider.getApplicationContext().getBean(StatisticalResourcesServiceFacade.BEAN_ID);
        }
        return statisticalResourcesServiceFacade;
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
