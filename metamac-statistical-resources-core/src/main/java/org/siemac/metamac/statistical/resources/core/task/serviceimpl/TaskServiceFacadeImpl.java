package org.siemac.metamac.statistical.resources.core.task.serviceimpl;

import java.io.InputStream;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
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
    public void executeImportationTask(ServiceContext ctx, InputStream inputMessage, String jobKey) throws MetamacException {
        taskservice.processImportationTask(ctx, inputMessage, jobKey);
    }
}