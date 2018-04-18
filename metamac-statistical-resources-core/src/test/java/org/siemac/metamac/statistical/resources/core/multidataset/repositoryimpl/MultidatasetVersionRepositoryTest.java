package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.MultidatasetsAsserts.assertEqualsMultidatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MultidatasetVersionMockFactory.MULTIDATASET_VERSION_01_BASIC_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class MultidatasetVersionRepositoryTest extends StatisticalResourcesBaseTest implements MultidatasetVersionRepositoryTestBase {

    @Autowired
    protected MultidatasetVersionRepository multidatasetVersionRepository;

    @Override
    @Test
    @MetamacMock({MULTIDATASET_VERSION_01_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        // TODO Auto-generated method stub
        MultidatasetVersion actual = multidatasetVersionRepository
                .retrieveByUrn(multidatasetVersionMockFactory.retrieveMock(MULTIDATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsMultidatasetVersion(multidatasetVersionMockFactory.retrieveMock(MULTIDATASET_VERSION_01_BASIC_NAME), actual);
    }

    @Test
    public void testRetrieveByUrnPublished() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveByUrnPublished not implemented");
    }

    @Test
    public void testRetrieveLastVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveLastVersion not implemented");
    }

    @Test
    public void testRetrieveLastPublishedVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveLastPublishedVersion not implemented");
    }

    @Test
    public void testRetrieveByVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveByVersion not implemented");
    }

    @Test
    public void testRetrieveIsReplacedByOnlyLastPublished() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveIsReplacedByOnlyLastPublished not implemented");
    }

    @Test
    public void testRetrieveIsReplacedByOnlyIfPublished() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveIsReplacedByOnlyIfPublished not implemented");
    }

    @Test
    public void testRetrieveIsReplacedBy() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveIsReplacedBy not implemented");
    }
}
