package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeMessage;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;

public class ImportDatasetJob extends AbstractImportDatasetJob {

    /**
     * Quartz requires a public empty constructor so that the scheduler can instantiate the class whenever it needs.
     */
    public ImportDatasetJob() {
        // NOTHING TO DO HERE
    }

    @Override
    protected ServiceContext setAdditionalProperties(ServiceContext serviceContext, JobDataMap jobDataMap) {
        return serviceContext;
    }

    @Override
    protected void sendSuccessNotification(String fileNames, String user) {
        getNoticesRestInternalService().createSuccessBackgroundNotification(user, ServiceNoticeAction.IMPORT_DATASET_JOB, ServiceNoticeMessage.IMPORT_DATASET_JOB_OK, fileNames);
    }

    @Override
    protected void sendErrorNotification(MetamacException metamacException) {
        String user = getData().getString(USER);
        getNoticesRestInternalService().createErrorBackgroundNotification(user, ServiceNoticeAction.IMPORT_DATASET_JOB, metamacException);
    }

    @Override
    protected void executeImportTask(ServiceContext serviceContext, String jobName, TaskInfoDataset taskInfoDataset) throws MetamacException {
        getTaskServiceFacade().executeImportationTask(serviceContext, jobName, taskInfoDataset);
    }

    @Override
    protected void processImportJobError(JobKey jobKey, String fileNames, MetamacException metamacException) {
        try {
            getTaskServiceFacade().markTaskAsFailed(serviceContext, jobKey.getName(), metamacException);
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
