package org.siemac.metamac.statistical.resources.core.task.serviceapi;

import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskServiceFacadeTest extends StatisticalResourcesBaseTest implements TaskServiceFacadeTestBase {

    @Autowired
    protected TaskServiceFacade taskServiceFacade;

    @Override
    public void testExecuteImportationTask() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testMarkTaskAsFailed() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testMarkAllInProgressTaskToFailed() throws Exception {
        // Already checked without test
    }

    @Override
    public void testExecuteRecoveryImportationTask() throws Exception {
        // See integration test in DataManipulateTest
    }
}
