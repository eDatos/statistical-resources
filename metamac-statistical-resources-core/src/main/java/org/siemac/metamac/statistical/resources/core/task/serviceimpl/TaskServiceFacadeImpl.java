package org.siemac.metamac.statistical.resources.core.task.serviceimpl;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.task.TaskInfoDatasetDto;
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
    public void executeImportationTask(ServiceContext ctx, TaskInfoDatasetDto taskInfoDataset) throws MetamacException {
        taskservice.processImportationTask(ctx, taskInfoDataset);
    }
}
