package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.core.io.utils.AttributeValueDtoBuilder;
import org.siemac.metamac.statistical.resources.core.io.utils.DbImportDatasetUtils;
import org.siemac.metamac.statistical.resources.core.io.utils.DsdAttributeInstanceDtoBuilder;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;

public class CustomImportDatasetJobForDbImport extends AbstractImportDatasetJob {

    public static final String                DB_IMPORT_JOB_FLAG                  = "custom_import";
    public static final String                DB_IMPORT_JOB_EXECUTION_DATE        = "import_excution_date";
    public static final String                DB_IMPORT_JOB_DATASOURCE_IDENTIFIER = "datasource_identifier";

    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade   = null;

    /**
     * Quartz requires a public empty constructor so that the scheduler can instantiate the class whenever it needs.
     */
    public CustomImportDatasetJobForDbImport() {
        // NOTHING TO DO HERE
    }

    @Override
    protected ServiceContext setAdditionalProperties(ServiceContext serviceContext, JobDataMap jobDataMap) {

        serviceContext.setProperty(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_FLAG, isCustomJob(jobDataMap));
        serviceContext.setProperty(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_EXECUTION_DATE, getExecutionDate(jobDataMap));
        serviceContext.setProperty(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_DATASOURCE_IDENTIFIER, getDatasourceIdentifier(jobDataMap));
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
        return jobDataMap.getString(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_DATASOURCE_IDENTIFIER);
    }

    private DateTime getExecutionDate(JobDataMap jobDataMap) {
        return new DateTime(jobDataMap.getLong(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_EXECUTION_DATE));
    }

    private Boolean isCustomJob(JobDataMap jobDataMap) {
        return jobDataMap.getBoolean(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_FLAG);
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
    protected void executeImportTask(ServiceContext serviceContext, String jobName, TaskInfoDataset taskInfoDataset) {
        try {
            logger.debug("ImportationJob: Importation Task in progress for {}", jobName);
            getTaskServiceFacade().executeImportationTask(serviceContext, jobName, taskInfoDataset);

            String datasetVersionUrn = getData().getString(DATASET_VERSION_ID);

            logger.debug("ImportationJob: Retrieving dataset {}", datasetVersionUrn);
            DatasetVersionDto datasetVersionDto = getStatisticalResourcesServiceFacade().retrieveDatasetVersionByUrn(serviceContext, datasetVersionUrn);

            logger.debug("ImportationJob: Setting required metada for dataset {}", datasetVersionUrn);
            setRequiredDataForProductionValidation(datasetVersionDto);

            logger.debug("ImportationJob: Updating required metada for dataset {}", datasetVersionUrn);
            datasetVersionDto = getStatisticalResourcesServiceFacade().updateDatasetVersion(serviceContext, datasetVersionDto);

            logger.debug("ImportationJob: Setting required attribute's data for dataset {}", datasetVersionUrn);
            // @formatter:off
            DsdAttributeInstanceDto dsdAttributeInstanceDto = DsdAttributeInstanceDtoBuilder.builder()
                    .withAttributeId("TITLE")
                    .withValue(AttributeValueDtoBuilder.builder()
                            .withStringValue("PRUEBA")
                            .build())
                    .build();
            // @formatter:on
            getStatisticalResourcesServiceFacade().createAttributeInstance(serviceContext, datasetVersionUrn, dsdAttributeInstanceDto);

            logger.debug("ImportationJob: Sending to production validation dataset {}", datasetVersionUrn);
            datasetVersionDto = getStatisticalResourcesServiceFacade().sendDatasetVersionToProductionValidation(serviceContext, datasetVersionDto);

            logger.debug("ImportationJob: Sending to difussion validation dataset {}", datasetVersionUrn);
            datasetVersionDto = getStatisticalResourcesServiceFacade().sendDatasetVersionToDiffusionValidation(serviceContext, datasetVersionDto);

            logger.debug("ImportationJob: Publishing dataset {}", datasetVersionUrn);
            datasetVersionDto = getStatisticalResourcesServiceFacade().publishDatasetVersion(serviceContext, datasetVersionDto);

            logger.debug("ImportationJob: Versioning dataset {}", datasetVersionUrn);
            getStatisticalResourcesServiceFacade().versioningDatasetVersion(serviceContext, datasetVersionDto, VersionTypeEnum.MINOR);

            logger.debug("ImportationJob: Marking as finished importation task {}", jobName);
            getTaskServiceFacade().markTaskAsFinished(serviceContext, jobName);

        } catch (MetamacException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRequiredDataForProductionValidation(DatasetVersionDto datasetVersionDto) {
        // TODO METAMAC-2866 Warning: dummy data for testing

        /*--------------------------------------------------------------------------*/
        /* Content descriptors */
        /*--------------------------------------------------------------------------*/
        // description
        DbImportDatasetUtils.setDatasetVersionDescription(datasetVersionDto);

        // geographic_granularities
        DbImportDatasetUtils.setDatasetVersionGeographicGranularities(datasetVersionDto);

        // temporal_granularities
        DbImportDatasetUtils.setDatasetVersionTemporalGranularities(datasetVersionDto);

        /*--------------------------------------------------------------------------*/
        /* Common metadata */
        /*--------------------------------------------------------------------------*/
        // common_metadata
        DbImportDatasetUtils.setDatasetVersionCommonMetadata(datasetVersionDto);

        /*--------------------------------------------------------------------------*/
        /* Production descriptors */
        /*--------------------------------------------------------------------------*/
        // creator
        DbImportDatasetUtils.setDatasetVersionCreator(datasetVersionDto);

        /*--------------------------------------------------------------------------*/
        /* Publication descriptors */
        /*--------------------------------------------------------------------------*/
        // publisher
        DbImportDatasetUtils.setDatasetVersionPublisher(datasetVersionDto);

        // statistic_oficiality
        DbImportDatasetUtils.setDatasetVersionStatisticOficiality(datasetVersionDto);

        /*--------------------------------------------------------------------------*/
        /* Version */
        /*--------------------------------------------------------------------------*/
        // next_version
        DbImportDatasetUtils.setDatasetVersionNextVersion(datasetVersionDto);

        // update_frecuency
        DbImportDatasetUtils.setDatasetVersionUpdateFrecuency(datasetVersionDto);

        // next_version_date
        DbImportDatasetUtils.setDatasetVersionNextVersionDate(datasetVersionDto);

        // date_next_update
        DbImportDatasetUtils.setDatasetVersionDateNextUpdate(datasetVersionDto);

        // version_rational_type
        DbImportDatasetUtils.setDatasetVersionVersionRationaleType(datasetVersionDto);
    }

    private StatisticalResourcesServiceFacade getStatisticalResourcesServiceFacade() {
        if (statisticalResourcesServiceFacade == null) {
            statisticalResourcesServiceFacade = (StatisticalResourcesServiceFacade) ApplicationContextProvider.getApplicationContext().getBean(StatisticalResourcesServiceFacade.BEAN_ID);
        }
        return statisticalResourcesServiceFacade;
    }

    @Override
    protected void processImportJobError(JobKey jobKey, String fileNames, MetamacException metamacException) {
        try {
            getTaskServiceFacade().markTaskAsFinished(serviceContext, jobKey.getName());
            logger.info("ImportationJob: {} marked as error at {}", jobKey, new Date());
            metamacException.setPrincipalException(new MetamacExceptionItem(ServiceExceptionType.IMPORT_DATASET_JOB_ERROR, fileNames));
            sendErrorNotification(metamacException);
        } catch (MetamacException e1) {
            logger.error("ImportationJob: the importation with key {} has failed and it can't marked as error", jobKey.getName(), e1);
            metamacException.setPrincipalException(new MetamacExceptionItem(ServiceExceptionType.IMPORT_DATASET_JOB_ERROR_AND_CANT_MARK_AS_ERROR, fileNames));
            sendErrorNotification(metamacException);
        }
    }
}
