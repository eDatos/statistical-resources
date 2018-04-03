package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import static org.junit.Assert.fail;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class MultidatasetVersionRepositoryTest extends AbstractDbUnitJpaTests implements MultidatasetVersionRepositoryTestBase {

    @Autowired
    protected MultidatasetVersionRepository multidatasetVersionRepository;

    @Test
    public void testRetrieveByUrn() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveByUrn not implemented");
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
