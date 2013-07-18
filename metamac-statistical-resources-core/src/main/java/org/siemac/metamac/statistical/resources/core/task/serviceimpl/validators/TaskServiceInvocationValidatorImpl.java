package org.siemac.metamac.statistical.resources.core.task.serviceimpl.validators;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.Task;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public class TaskServiceInvocationValidatorImpl {

    public static void checkPlannifyImportationDataset(TaskInfoDataset taskInfoDataset, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(taskInfoDataset, ServiceExceptionParameters.TASK_INFO_DATASET, exceptions);

        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getFiles(), ServiceExceptionParameters.TASK_INFO_DATASET_FILES, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getDataStructureUrn(), ServiceExceptionParameters.TASK_INFO_DATASET_DSD_URN, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getRepoDatasetId(), ServiceExceptionParameters.TASK_INFO_DATASET_DATASET_ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(taskInfoDataset.getDatasetFileFormatEnum(), ServiceExceptionParameters.TASK_DATASET_FORMAT, exceptions);

        for (FileDescriptor fileDescriptorDto : taskInfoDataset.getFiles()) {
            StatisticalResourcesValidationUtils.checkMetadataRequired(fileDescriptorDto.getInputMessage(), ServiceExceptionParameters.FILE_DESCRIPTOR_INPUT_MESSAGE, exceptions);
            StatisticalResourcesValidationUtils.checkMetadataRequired(fileDescriptorDto.getFileName(), ServiceExceptionParameters.FILE_DESCRIPTOR_FILE_NAME, exceptions);
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

    public static void checkMarkTaskAsFinished(String job, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(job, ServiceExceptionParameters.TASK_INFO_DATASET_JOB_KEY, exceptions);
    }

    public static void checkMarkTaskAsFailed(String job, Exception exception, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(job, ServiceExceptionParameters.TASK_INFO_DATASET_JOB_KEY, exceptions);
    }

    public static void checkCreateTask(Task task, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(task, ServiceExceptionParameters.TASK, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(task.getId(), ServiceExceptionSingleParameters.JOB, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(task.getId(), ServiceExceptionSingleParameters.STATUS, exceptions);
    }

    public static void checkRetrieveTaskByJob(String job, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(job, ServiceExceptionParameters.TASK_INFO_DATASET_JOB_KEY, exceptions);
    }

    public static void checkUpdateTask(Task task, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(task, ServiceExceptionParameters.TASK, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(task.getId(), ServiceExceptionSingleParameters.ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(task.getId(), ServiceExceptionSingleParameters.JOB, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(task.getId(), ServiceExceptionSingleParameters.STATUS, exceptions);
    }

    public static void checkFindTasksByCondition(List<ConditionalCriteria> conditions, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) {
    }

}
