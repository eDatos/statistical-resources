package org.siemac.metamac.statistical.resources.core.utils;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.mockito.Mockito;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;

public class TaskMockUtils {

    public static void mockTaskInProgressForDatasetVersion(TaskService taskService, String datasetUrn, boolean status) throws MetamacException {
        Mockito.when(taskService.existsTaskForResource(Mockito.any(ServiceContext.class), Mockito.eq(datasetUrn))).thenReturn(status);
    }

    public static void mockAllTaskInProgressForDatasetVersion(TaskService taskService, boolean status) throws MetamacException {
        Mockito.when(taskService.existsTaskForResource(Mockito.any(ServiceContext.class), Mockito.anyString())).thenReturn(status);
    }
}
