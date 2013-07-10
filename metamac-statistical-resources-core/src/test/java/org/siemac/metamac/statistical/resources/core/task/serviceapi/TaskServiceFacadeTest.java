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
public class TaskServiceFacadeTest extends StatisticalResourcesBaseTest implements TaskServiceFacadeTestBase {

    @Autowired
    protected TaskServiceFacade taskServiceFacade;

    @Override
    // @Test
    public void testExecuteImportationTask() throws Exception {
        // TODO Auto-generated method stub
    }
}
