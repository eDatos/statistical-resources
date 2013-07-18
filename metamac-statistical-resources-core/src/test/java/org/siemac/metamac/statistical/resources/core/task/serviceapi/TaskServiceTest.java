package org.siemac.metamac.statistical.resources.core.task.serviceapi;

import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
// @TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
// @Transactional
public class TaskServiceTest extends StatisticalResourcesBaseTest implements TaskServiceTestBase {

    @Autowired
    protected TaskService taskService;

    @Override
    // @Test
    public void testPlannifyImportationDataset() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    // @Test
    public void testProcessImportationTask() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    // @Test
    public void testExistTaskInDataset() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void testMarkTaskAsFinished() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void testMarkTaskAsFailed() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void testCreateTask() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void testUpdateTask() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void testRetrieveTaskByJob() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void testFindTasksByCondition() throws Exception {
        // TODO Auto-generated method stub

    }
}
