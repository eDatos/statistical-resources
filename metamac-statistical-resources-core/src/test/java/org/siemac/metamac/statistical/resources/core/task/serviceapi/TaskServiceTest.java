package org.siemac.metamac.statistical.resources.core.task.serviceapi;

import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskServiceTest extends StatisticalResourcesBaseTest implements TaskServiceTestBase {

    @Autowired
    protected TaskService taskService;

    @Override
    public void testPlanifyImportationDataset() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testProcessImportationTask() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testMarkTaskAsFinished() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testMarkTaskAsFailed() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testCreateTask() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testUpdateTask() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testRetrieveTaskByJob() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testFindTasksByCondition() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testPlanifyRecoveryImportDataset() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testProcessRollbackImportationTask() throws Exception {
        // Already checked without test
    }

    @Override
    public void testExistsTaskForResource() throws Exception {
        // See integration test in DataManipulateTest

    }

    @Override
    public void testExistImportationTaskInResource() throws Exception {
        // See integration test in DataManipulateTest

    }

    @Override
    public void testExistRecoveryImportationTaskInResource() throws Exception {
        // See integration test in DataManipulateTest

    }

    @Override
    public void testPlanifyDuplicationDataset() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testProcessDuplicationTask() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testExistDuplicationTaskInResource() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testMarkTasksAsFailedOnApplicationStartup() throws Exception {
        // See integration test in DataManipulateTest
    }

    @Override
    public void testExistDatabaseImportationTaskInResource() throws Exception {
        // No test
    }

    @Override
    public void testProcessDatabaseImportationTask() throws Exception {
        // No test
    }

    @Override
    public void testProcessDatabaseDatasetPollingTask() throws Exception {
        // No test
    }

    @Override
    public void testScheduleDatabaseDatasetPollingJob() throws Exception {
        // No test
    }

    @Override
    public void testSendDatabaseImportationErrorNotification() throws Exception {
        // No test
    }
}
