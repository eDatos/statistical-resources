package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.fail;

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
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetServiceTest extends StatisticalResourcesBaseTest implements DatasetServiceTestBase {

    @Autowired
    protected DatasetService datasetService;
    

    @Test
    public void testCreateDatasource() throws Exception {
        fail("not implemented");
        
    }

    @Test
    public void testUpdateDatasource() throws Exception {
        fail("not implemented");
        
    }

    @Test
    public void testRetrieveDatasourceByUrn() throws Exception {
        fail("not implemented");
        
    }

    @Test
    public void testDeleteDatasource() throws Exception {
        fail("not implemented");
        
    }

    @Test
    public void testRetrieveDatasourcesByDatasetVersion() throws Exception {
        fail("not implemented");     
        
    }
    
}
