package org.siemac.metamac.statistical.resources.core.task.serviceimpl.validators;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public class TaskServiceInvocationValidatorImpl {

    public static void checkPlannifyImportationDataset(TaskInfoDataset taskInfoDataset, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(taskInfoDataset, ServiceExceptionParameters.TASK_INFO_DATASET, exceptions);

        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getFiles(), ServiceExceptionParameters.TASK_INFO_DATASET_FILES, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getDataStructureUrn(), ServiceExceptionParameters.TASK_INFO_DATASET_DSD_URN, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getRepoDatasetId(), ServiceExceptionParameters.TASK_INFO_DATASET_DATASET_ID, exceptions);

        for (FileDescriptor fileDescriptorDto : taskInfoDataset.getFiles()) {
            StatisticalResourcesValidationUtils.checkMetadataRequired(fileDescriptorDto.getInputMessage(), ServiceExceptionParameters.FILE_DESCRIPTOR_INPUT_MESSAGE, exceptions);
            StatisticalResourcesValidationUtils.checkMetadataRequired(fileDescriptorDto.getFileName(), ServiceExceptionParameters.FILE_DESCRIPTOR_FILE_NAME, exceptions);
            StatisticalResourcesValidationUtils.checkMetadataRequired(fileDescriptorDto.getDatasetFileFormatEnum(), ServiceExceptionParameters.FILE_DESCRIPTOR_DATASET_FORMAT, exceptions);
        }
    }

    public static void checkProcessImportationTask(TaskInfoDataset taskInfoDataset, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(taskInfoDataset, ServiceExceptionParameters.TASK_INFO_DATASET, exceptions);

        StatisticalResourcesValidationUtils.checkParameterRequired(taskInfoDataset.getJobKey(), ServiceExceptionParameters.TASK_INFO_DATASET_JOB_KEY, exceptions);

        checkPlannifyImportationDataset(taskInfoDataset, exceptions);

    }

    public static void checkExistTaskInDataset(String datasetId, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetId, ServiceExceptionParameters.TASK_INFO_DATASET_DATASET_ID, exceptions);

    }
}
