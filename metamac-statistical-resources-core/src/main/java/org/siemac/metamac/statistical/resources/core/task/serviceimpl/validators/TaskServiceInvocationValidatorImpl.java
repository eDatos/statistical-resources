package org.siemac.metamac.statistical.resources.core.task.serviceimpl.validators;

import java.io.InputStream;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.dto.task.TaskInfoDatasetDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public class TaskServiceInvocationValidatorImpl {

    public static void checkPlannifyImportationDataset(InputStream inputMessage, TaskInfoDatasetDto taskInfoDataset, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(inputMessage, ServiceExceptionParameters.INPUT_MESSAGE, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(taskInfoDataset, ServiceExceptionParameters.TASK_INFO_DATASET, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getFileName(), ServiceExceptionParameters.TASK_INFO_DATASET_FILE_NAME, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getDataStructureUrn(), ServiceExceptionParameters.TASK_INFO_DATASET_DATA_STRUCTURE_URN, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getRepoDatasetId(), ServiceExceptionParameters.TASK_INFO_DATASET_REPO_DATASET_ID, exceptions);
    }

    public static void checkProcessImportationTask(InputStream inputMessage, TaskInfoDatasetDto taskInfoDataset, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(inputMessage, ServiceExceptionParameters.INPUT_MESSAGE, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(taskInfoDataset, ServiceExceptionParameters.TASK_INFO_DATASET, exceptions);

        StatisticalResourcesValidationUtils.checkParameterRequired(taskInfoDataset.getJobKey(), ServiceExceptionParameters.TASK_INFO_DATASET_JOB_KEY, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(taskInfoDataset.getDataStructureUrn(), ServiceExceptionParameters.TASK_INFO_DATASET_DATA_STRUCTURE_URN, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(taskInfoDataset.getRepoDatasetId(), ServiceExceptionParameters.TASK_INFO_DATASET_REPO_DATASET_ID, exceptions);
    }
}
