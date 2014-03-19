package org.siemac.metamac.statistical.resources.core.task.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.TaskStatusTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.task.domain.Task;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskProperties;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of TaskServiceFacade.
 */
@Service("taskServiceFacade")
public class TaskServiceFacadeImpl extends TaskServiceFacadeImplBase {

    @Autowired
    private TaskService taskservice;

    public TaskServiceFacadeImpl() {
    }

    @Override
    public void executeImportationTask(ServiceContext ctx, String importationJobKey, TaskInfoDataset taskInfoDataset) throws MetamacException {
        taskservice.processImportationTask(ctx, importationJobKey, taskInfoDataset);
    }

    @Override
    public void executeRecoveryImportationTask(ServiceContext ctx, String recoveryJobKey, TaskInfoDataset taskInfoDataset) throws MetamacException {
        taskservice.processRollbackImportationTask(ctx, recoveryJobKey, taskInfoDataset);
    }

    @Override
    public void executeDuplicationTask(ServiceContext ctx, String duplicationJobKey, TaskInfoDataset taskInfoDataset, String newDatasetId) throws MetamacException {
        taskservice.processDuplicationTask(ctx, duplicationJobKey, taskInfoDataset, newDatasetId);
    }

    @Override
    public void markTaskAsFailed(ServiceContext ctx, String job, Exception exception) throws MetamacException {
        taskservice.markTaskAsFailed(ctx, job, exception);
    }

    @Override
    // This method is only used on application startup
    public void markAllInProgressTaskToFailed(ServiceContext ctx) throws MetamacException {
        // Mark as failed current IN_PROGRESS states, if are available and also FAILED in order to retry the recovery process
        List<ConditionalCriteria> conditionList = ConditionalCriteriaBuilder.criteriaFor(Task.class).withProperty(TaskProperties.status()).eq(TaskStatusTypeEnum.IN_PROGRESS).or()
                .withProperty(TaskProperties.status()).eq(TaskStatusTypeEnum.FAILED).build();
        PagedResult<Task> pagedResult = taskservice.findTasksByCondition(ctx, conditionList, PagingParameter.noLimits());

        for (Task task : pagedResult.getValues()) {
            // Other Exception
            MetamacException metamacException = MetamacExceptionBuilder.builder().withPrincipalException(new MetamacExceptionItem(ServiceExceptionType.TASKS_ERROR_SERVER_DOWN)).build();
            taskservice.markTaskAsFailed(ctx, task.getJob(), metamacException);

            // TODO envio al gestor de avisos de fallo de importación (METAMAC-2113)
        }
    }

}
