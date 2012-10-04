package org.siemac.metamac.statistical.resources.core.serviceapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class StatisticalResourceServiceTest extends StatisticalResourcesBaseTest implements StatisticalResourceServiceTestBase {

    @Autowired
    protected StatisticalResourceService statisticalResourceService;

    @Test
    public void testRetrieveQueryByUrn() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
