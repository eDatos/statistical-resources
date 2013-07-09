package org.siemac.metamac.statistical.resources.core.task.serviceapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
//@Transactional
public class TaskServiceTest extends StatisticalResourcesBaseTest implements TaskServiceTestBase {

    @Autowired
    protected TaskService taskService;

    @Override
    @Test
    public void testPlannifyImportationDataset() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    @Test
    public void testProcessImportationTask() throws Exception {
        // TODO Auto-generated method stub
    }
}
