package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
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
public class PublicationVersionRepositoryTest extends StatisticalResourcesBaseTest implements PublicationVersionRepositoryTestBase {

    @Autowired
    protected PublicationVersionRepository publicationVersionRepository;

    @Test
    public void testRetrieveByUrn() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveLastVersion not implemented");
    }

    @Test
    public void testRetrieveLastVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveLastVersion not implemented");
    }

    @Test
    public void testRetrieveByVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testFindByVersion not implemented");
    }
}
