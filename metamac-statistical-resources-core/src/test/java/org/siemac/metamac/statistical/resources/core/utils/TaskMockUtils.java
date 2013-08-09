package org.siemac.metamac.statistical.resources.core.utils;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.mockito.Mockito;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;


public class TaskMockUtils {

    private static TaskService taskService;
    
    public static void mockTaskInProgressForDatasetVersion(String datasetVersionUrn, boolean status) throws MetamacException {
        Mockito.when(getTaskService().existImportationTaskInDataset(Mockito.any(ServiceContext.class), Mockito.eq(datasetVersionUrn))).thenReturn(status);
    }
    
    public static void mockAllTaskInProgressForDatasetVersion(boolean status) throws MetamacException {
        Mockito.when(getTaskService().existImportationTaskInDataset(Mockito.any(ServiceContext.class), Mockito.anyString())).thenReturn(status);
    }
    
    private static TaskService getTaskService() {
        if (taskService != null) {
            return taskService;
        }
        return ApplicationContextProvider.getApplicationContext().getBean(TaskService.class);
    }
    
    public static void setTaskService(TaskService taskService) {
        TaskMockUtils.taskService = taskService;
    }
}
