package org.siemac.metamac.statistical.resources.core.task.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.TaskStatusTypeEnum;
import org.siemac.metamac.statistical.resources.core.task.domain.Task;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskProperties;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.dataset.repository.dto.Mapping;

/**
 * Implementation of TaskServiceFacade.
 */
@Service("taskServiceFacade")
public class TaskServiceFacadeImpl extends TaskServiceFacadeImplBase {

    private static Logger logger = LoggerFactory.getLogger(TaskServiceFacadeImpl.class);

    @Autowired
    private TaskService   taskservice;

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
    public void executeDuplicationTask(ServiceContext ctx, String duplicationJobKey, TaskInfoDataset taskInfoDataset, String newDatasetId, List<Mapping> datasourceMappings) throws MetamacException {
        taskservice.processDuplicationTask(ctx, duplicationJobKey, taskInfoDataset, newDatasetId, datasourceMappings);
    }

    @Override
    public void markTaskAsFailed(ServiceContext ctx, String job, Exception exception) throws MetamacException {
        taskservice.markTaskAsFailed(ctx, job);
    }

    @Override
    // This method is only used on application startup
    public void markAllInProgressTaskToFailed(ServiceContext ctx) throws MetamacException {
        // Mark as failed current IN_PROGRESS states, if are available and also FAILED in order to retry the recovery process
        List<ConditionalCriteria> conditionList = ConditionalCriteriaBuilder.criteriaFor(Task.class).withProperty(TaskProperties.status()).eq(TaskStatusTypeEnum.IN_PROGRESS).or()
                .withProperty(TaskProperties.status()).eq(TaskStatusTypeEnum.FAILED).build();
        PagedResult<Task> pagedResult = taskservice.findTasksByCondition(ctx, conditionList, PagingParameter.noLimits());

        for (Task task : pagedResult.getValues()) {
            logger.info("Recovering task " + task.getJob() + " of the user " + task.getCreatedBy());
            taskservice.markTasksAsFailedOnApplicationStartup(ctx, task.getJob());
        }
    }

    @Override
    public void markTaskAsFinished(ServiceContext ctx, String job) throws MetamacException {
        taskservice.markTaskAsFinished(ctx, job);
    }
}
